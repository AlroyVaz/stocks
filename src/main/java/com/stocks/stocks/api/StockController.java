package com.stocks.stocks.api;

import com.stocks.stocks.model.Stock;
import com.stocks.stocks.model.StockPriceHistory;
import com.stocks.stocks.model.StockWrapper;
import com.stocks.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("stock")
@RestController
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/price/tickerSymbol/{t}")
    public Stock getCurrentStockPriceFromTickerSymbol(@PathVariable("t") String tickerSymbol, HttpServletRequest request) {
        return stockService.getCurrentStockPriceFromTickerSymbol(tickerSymbol, request);
    }

    @GetMapping("/price/company/{c}")
    public List<Stock> getCurrentStockPriceFromCompany(@PathVariable("c") String company, HttpServletRequest request) {
        return stockService.getCurrentStockPriceFromCompany(company, request);
    }

    @GetMapping("/price/tickerSymbol/{t}/history")
    public StockPriceHistory getStockPriceHistoryFromTickerSymbol(@PathVariable("t") String tickerSymbol, HttpServletRequest request) {
        return stockService.getStockPriceHistoryFromTickerSymbol(tickerSymbol, request);
    }

    @PostMapping("/buy")
    public void buyStocks(@RequestBody StockWrapper stockWrapper, HttpServletRequest request) {
        stockService.buyStocks(stockWrapper.getStockList(), request);
    }

    @PostMapping("/sell")
    public void sellStocks(@RequestBody StockWrapper stockWrapper, HttpServletRequest request) {
        stockService.sellStocks(stockWrapper.getStockList(), request);
    }
}
