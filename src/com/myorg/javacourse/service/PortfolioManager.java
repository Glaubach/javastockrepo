package com.myorg.javacourse.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myorg.javacourse.exception.BalanceException;
import com.myorg.javacourse.exception.NotEnoughStocksException;
import com.myorg.javacourse.exception.PortfolioFullException;
import com.myorg.javacourse.exception.StockAlreadyExistsException;
import com.myorg.javacourse.exception.StockNotExistException;
import com.myorg.javacourse.model.Portfolio;
import com.myorg.javacourse.model.Stock;
import com.myorg.javacourse.model.Portfolio.ALGO_RECOMMENDATION;

import org.algo.dto.PortfolioDto;
import org.algo.dto.PortfolioTotalStatus;
import org.algo.dto.StockDto;
import org.algo.exception.PortfolioException;
import org.algo.exception.SymbolNotFoundInNasdaq;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;
import org.algo.service.DatastoreService;
import org.algo.service.MarketService;
import org.algo.service.PortfolioManagerInterface;
import org.algo.service.ServiceManager;

/**
 * A class to manage the online portfolio and it's data
 */
public class PortfolioManager implements PortfolioManagerInterface{
	public enum OPERATION {BUY, SELL, REMOVE, HOLD }
	
	private DatastoreService datastoreService = ServiceManager.datastoreService();

	/**
	 * This method is used to retrieve the online portfolio
	 */
	public PortfolioInterface getPortfolio() {
		PortfolioDto portfolioDto = datastoreService.getPortfolilo();
		return fromDto(portfolioDto);
	}
	
	/**
	 * Update online portfolio with stocks
	 */
	@Override
	public void update() {
		StockInterface[] stocks = getPortfolio().getStocks();
		List<String> symbols = new ArrayList<>(Portfolio.getMaxSize());
		for (StockInterface si : stocks) {
			symbols.add(si.getSymbol());
		}

		List<Stock> update = new ArrayList<>(Portfolio.getMaxSize());
		List<Stock> currentStocksList = new ArrayList<Stock>();
		try {
			List<StockDto> stocksList = MarketService.getInstance().getStocks(symbols);
			for (StockDto stockDto : stocksList) {
				Stock stock = fromDto(stockDto);
				currentStocksList.add(stock);
			}

			for (Stock stock : currentStocksList) {
				update.add(new Stock(stock));
			}

			datastoreService.saveToDataStore(toDtoList(update));

		} catch (SymbolNotFoundInNasdaq e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	* Set portfolio title
	*/
	@Override
	public void setTitle(String title) {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.setTitle(title);
		flush(portfolio);
	}
	
	/**
	* update portfolio balance
	* @param amount  The amount to add/subtract to balance
	* @throws PortfolioException
	*/
	@Override
	public void updateBalance(float amount) throws PortfolioException{
		Portfolio portfolio = (Portfolio) getPortfolio();
		try {
			portfolio.updateBalance(amount);
		} catch (BalanceException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		flush(portfolio);	
	}

	/**
	* get portfolio totals
	*/
		@Override
	public PortfolioTotalStatus[] getPortfolioTotalStatus () {
		Portfolio portfolio = (Portfolio) getPortfolio();
		Map<Date, Float> map = new HashMap<>();
		//get stock status from db.
		StockInterface[] stocks = portfolio.getStocks();
		for (int i = 0; i < stocks.length; i++) {
			StockInterface stock = stocks[i];
			if(stock != null) {
				List<StockDto> stockHistory = null;
				try {
					stockHistory = datastoreService.getStockHistory(stock.getSymbol(),30);
				} catch (Exception e) {
					return null;
				  }
				for (StockDto stockDto : stockHistory) {
					Stock stockStatus = fromDto(stockDto);
					float value = stockStatus.getBid()*stockStatus.getStockQuantity();
					Date date = stockStatus.getDate();
					Float total = map.get(date);
					if(total == null) {
						total = value;
					}else {
						total += value;
					}
					map.put(date, value);
				}
			}
		}
		PortfolioTotalStatus[] ret = new PortfolioTotalStatus[map.size()];
		int index = 0;
		//create dto objects
		for (Date date : map.keySet()) {
			ret[index] = new PortfolioTotalStatus(date, map.get(date));
			index++;
		}
		//sort by date ascending.
		Arrays.sort(ret);
		return ret;
	}
	
	/**
	* Add stock to portfolio 
	* @param symbol The name of the Stock
	* @throws PortfolioException
	*/
		@Override
	public void addStock(String symbol) throws PortfolioException{
		Portfolio portfolio = (Portfolio) getPortfolio();
		try {
			StockDto stockDto = ServiceManager.marketService().getStock(symbol);
			//get current symbol values from nasdaq.
			Stock stock = fromDto(stockDto);
				
			//first thing, add it to portfolio.
			try {
				portfolio.addStock(stock);
			} catch (StockAlreadyExistsException e) {
				System.out.println(e.getMessage());
				throw e;
			} catch (PortfolioFullException e) {
				System.out.println(e.getMessage());
				throw e;
			}   
			//or:
			//portfolio.addStock(stock);   

			//second thing, save the new stock to the database.
			datastoreService.saveStock(toDto(portfolio.findStock(symbol)));
				
			flush(portfolio);
		} catch (SymbolNotFoundInNasdaq e) {
			System.out.println("Stock Not Exists: "+symbol);
		}
	}

	/**
	* Buy stock
	* @param  symbol    The name of the stock
	* @param  quantity  Number of shares of this stock
	* @throws PortfolioException
    */
		@Override
	public void buyStock(String symbol, int quantity) throws PortfolioException{
		try {
			Portfolio portfolio = (Portfolio) getPortfolio();
				
			Stock stock = (Stock) portfolio.findStock(symbol);
			if(stock == null) {
				stock = fromDto(ServiceManager.marketService().getStock(symbol));				
			}
				
			portfolio.buyStock(stock, quantity);
			flush(portfolio);
		}catch (BalanceException e) {
			System.out.println(e.getMessage());
			throw e;
		}catch (Exception e) {
			System.out.println("Exception: "+e);
		}
	}

    /**
    * Sell stock
    * @param  symbol    The name of the stock
	* @param  quantity  Number of shares of this stock
	* @throws PortfolioException
    */
	@Override
	public void sellStock(String symbol, int quantity) throws PortfolioException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		try {
			portfolio.sellStock(symbol, quantity);
		}catch (NotEnoughStocksException e) {
			System.out.println(e.getMessage());
			throw e;
		} 
		catch (StockNotExistException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		flush(portfolio);
	}

	/**
	* Remove stock
	* @param  symbol    The name of the stock
	* @throws PortfolioException
	*/
	@Override
	public void removeStock(String symbol) throws PortfolioException{ 
		Portfolio portfolio = (Portfolio) getPortfolio();
		try {
			portfolio.removeStock(symbol);
		} catch (NotEnoughStocksException e) {
			System.out.println(e.getMessage());
			throw e;
		} catch (StockNotExistException e) {
			System.out.println(e.getMessage());
			throw e;
		} 		
		flush(portfolio);
	}

	/**
	 * This method is used to convert a stock from the online portfolio to a stock object
	 * @param   stockDto   Stock in DataStore
	 * @return  stock	   Stock object
	 */
	private Stock fromDto(StockDto stockDto) {
		Stock newStock = new Stock();

		newStock.setSymbol(stockDto.getSymbol());
		newStock.setAsk(stockDto.getAsk());
		newStock.setBid(stockDto.getBid());
		newStock.setDate(stockDto.getDate());
		newStock.setStockQuantity(stockDto.getQuantity());
		if(stockDto.getRecommendation() != null) newStock.setRecommendation(ALGO_RECOMMENDATION.valueOf(stockDto.getRecommendation()));
		else
			newStock.setRecommendation(ALGO_RECOMMENDATION.valueOf("HOLD"));
		
		return newStock;
	}
	
	/**
	 * fromDto - converts portfolioDto to Portfolio
	 * @param  dto        DataStore portfolio
	 * @return portfolio  Portfolio object
	 */
	private Portfolio fromDto(PortfolioDto dto) {
		StockDto[] stocks = dto.getStocks();
		Portfolio ret;
		if(stocks == null) {
			ret = new Portfolio();			
		}else {
			List<Stock> stockList = new ArrayList<Stock>();
			for (StockDto stockDto : stocks) {
				stockList.add(fromDto(stockDto));
			}

			Stock[] stockArray = stockList.toArray(new Stock[stockList.size()]);
			ret = new Portfolio(stockArray,stockList.size());
		}

		ret.setTitle(dto.getTitle());
		try {
			ret.updateBalance(dto.getBalance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * update database with new portfolio's data
	 * @param portfolio   portfolio object
	 */
	private void flush(Portfolio portfolio) {
		datastoreService.updatePortfolio(toDto(portfolio));
	}
	
	/**
	 * toDtoList - convert List of Stocks to list of Stock DTO
	 * @param  stocks    List of stocks in portfolio object
	 * @return stockDto  List of stocks for DataStore portfolio
	 */
	private List<StockDto> toDtoList(List<Stock> stocks) {

		List<StockDto> ret = new ArrayList<StockDto>();

		for (Stock stockStatus : stocks) {
			ret.add(toDto(stockStatus));
		}

		return ret;
	}
	
	/**
	 * toDto - convert Stock to Stock DTO
	 * @param  stocks    stock object
	 * @return StockDto  DataStore stock
	 */
	private StockDto toDto(StockInterface stocks) {
		if (stocks == null) {
			return null;
		}
		
		Stock stock = (Stock) stocks;
		return new StockDto(stock.getSymbol(), stock.getAsk(), stock.getBid(), stock.getDate(), stock.getStockQuantity(), stock.getRecommendation().name());
	}

	/**
	 * toDto - converts Portfolio to Portfolio DTO
	 * @param   portfolio      portfolio object
	 * @return  PortfolioDto   DataStore portfolio
	 */
	private PortfolioDto toDto(Portfolio portfolio) {
		StockDto[] array = null;
		StockInterface[] stocks = portfolio.getStocks();
		if(stocks != null) {
			array = new StockDto[stocks.length];
			for (int i = 0; i < stocks.length; i++) {
				array[i] = toDto(stocks[i]);
			}
		}
		return new PortfolioDto(portfolio.getTitle(), portfolio.getBalance(), array);
	}
	
}
