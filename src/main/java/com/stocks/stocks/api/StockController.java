package com.stocks.stocks.api;

import com.stocks.stocks.model.Schedule;
import com.stocks.stocks.model.Stock;
import com.stocks.stocks.model.StockPriceHistory;
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

    @GetMapping("/search/{property}/{value}")
    public List<Stock> getStocks(@PathVariable("property") String property, @PathVariable("value") String value, HttpServletRequest request) {
        return stockService.getStocks(property, value, request);
    }

    @GetMapping("/price/{stockId}")
    public double getCurrentStockPrice(@PathVariable("id") String stockId, HttpServletRequest request) {
        return stockService.getCurrentStockPrice(stockId, request);
    }

    @GetMapping("/price/{stockId}/history")
    public StockPriceHistory getStockPriceHistory(@PathVariable("stockId") String stockId, HttpServletRequest request) {
        return stockService.getStockPriceHistory(stockId, request);
    }

    @PostMapping("/buy")
    public void buyStocks(@RequestBody List<String> stockIdList, HttpServletRequest request) {
        stockService.buyStocks(stockIdList, request);
    }

    @PostMapping("/sell")
    public void sellStocks(@RequestBody List<String> stockIdList, HttpServletRequest request) {
        stockService.sellStocks(stockIdList, request);
    }

    @GetMapping("/summary")
    public List<Stock> getAllStocks(HttpServletRequest request) {
        return stockService.getAllStocks(request);
    }
}
