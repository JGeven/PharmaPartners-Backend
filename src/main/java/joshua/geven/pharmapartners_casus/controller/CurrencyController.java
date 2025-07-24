package joshua.geven.pharmapartners_casus.controller;

import joshua.geven.pharmapartners_casus.model.Currency;
import joshua.geven.pharmapartners_casus.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<Page<Currency>> findAll(
            @PageableDefault(size = 10, sort = "ticker") Pageable pageable) {
        logger.info("Finding all currencies");
        return ResponseEntity.ok(currencyService.findAll(pageable));
    }


    @GetMapping("/{currency_id}")
    public ResponseEntity<Currency> findById(@PathVariable Long currency_id) {
        Currency dbCurrency = currencyService.findById(currency_id);
        logger.info("Finding currency with id " + currency_id);
        return new ResponseEntity<>(dbCurrency, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Currency> saveCurrency(@RequestBody Currency currency) {
        Currency dbCurrency = Currency.builder()
                .name(currency.getName())
                .ticker(currency.getTicker())
                .marketCap(currency.getMarketCap())
                .numberOfCoins(currency.getNumberOfCoins())
                .build();

        Currency savedCurrency = currencyService.save(dbCurrency);
        logger.info("Saving currency with id " + dbCurrency.getId());
        return new ResponseEntity<>(savedCurrency, HttpStatus.CREATED);
    }

    @DeleteMapping("/{currency_id}")
    public ResponseEntity<Currency> deleteCurrency(@PathVariable Long currency_id) {
        currencyService.delete(currency_id);
        logger.info("Deleting currency with id " + currency_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{currency_id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable Long currency_id, @RequestBody Currency currency) {
        Currency dbCurrency = currencyService.update(currency_id, currency);
        logger.info("Updating currency with id " + currency_id);
        return new ResponseEntity<>(dbCurrency, HttpStatus.OK);
    }
}
