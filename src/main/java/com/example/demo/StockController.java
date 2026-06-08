package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class StockController {

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockService stockService;

    @PostMapping("/stock")
    public Stock save(@RequestBody Stock stock) {
        return repository.save(stock);
    }

    @GetMapping("/quote/{symbol}")
    public String getQuote(@PathVariable String symbol) {
        return stockService.getStockQuote(symbol);
    }

    @GetMapping("/save/{symbol}")
    public String saveStock(@PathVariable String symbol) {

        Stock stock = new Stock();

        stock.setSymbol(symbol);

        Quote quote = stockService.getQuoteObject(symbol);

        stock.setCurrentPrice(quote.getC());
        stock.setChangePrice(quote.getD());
        stock.setChangePercent(quote.getDp());

        repository.save(stock);

        return "Saved Successfully";
    }

    @GetMapping("/load")
    public String loadStocks() {

        String[] symbols = {
            "AAPL","MSFT","NVDA","AMZN","GOOGL","META","TSLA","AVGO","JPM","WMT",
            "V","MA","NFLX","COST","ORCL","HD","BAC","KO","PEP","ABBV",
            "CRM","ADBE","CSCO","AMD","TMO","ACN","MCD","LIN","ABT","INTU",
            "QCOM","TXN","AMGN","IBM","CAT","GE","GS","NOW","ISRG","BKNG",
            "PLTR","UBER","MU","PANW","LRCX","KLAC","ADI","ANET","CRWD","SNPS",
            
        };

        for (String symbol : symbols) {

            Quote quote = stockService.getQuoteObject(symbol);

            Stock stock = new Stock();

            stock.setSymbol(symbol);
            stock.setCurrentPrice(quote.getC());
            stock.setChangePrice(quote.getD());
            stock.setChangePercent(quote.getDp());

            repository.save(stock);
        }

        return "Stocks Loaded Successfully";
    }

    @GetMapping("/summary")
    public String summary() {

        long up3 = repository.findAll()
                .stream()
                .filter(s -> s.getChangePercent() > 3)
                .count();

        long down3 = repository.findAll()
                .stream()
                .filter(s -> s.getChangePercent() < -3)
                .count();

        long up5 = repository.findAll()
                .stream()
                .filter(s -> s.getChangePercent() > 5)
                .count();

        long down5 = repository.findAll()
                .stream()
                .filter(s -> s.getChangePercent() < -5)
                .count();

        long up10 = repository.findAll()
                .stream()
                .filter(s -> s.getChangePercent() > 10)
                .count();

        long down10 = repository.findAll()
                .stream()
                .filter(s -> s.getChangePercent() < -10)
                .count();

        long up15 = repository.findAll()
                .stream()
                .filter(s -> s.getChangePercent() > 15)
                .count();

        long down15 = repository.findAll()
                .stream()
                .filter(s -> s.getChangePercent() < -15)
                .count();

        long up20 = repository.findAll()
                .stream()
                .filter(s -> s.getChangePercent() > 20)
                .count();

        long down20 = repository.findAll()
                .stream()
                .filter(s -> s.getChangePercent() < -20)
                .count();

        return "Up3%: " + up3 +
                ", Down3%: " + down3 +
                ", Up5%: " + up5 +
                ", Down5%: " + down5 +
                ", Up10%: " + up10 +
                ", Down10%: " + down10 +
                ", Up15%: " + up15 +
                ", Down15%: " + down15 +
                ", Up20%: " + up20 +
                ", Down20%: " + down20;
    }@GetMapping("/stocks")
    public List<Stock> getAllStocks() {
        return repository.findAll();
    }

}