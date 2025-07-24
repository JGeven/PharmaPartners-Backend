package joshua.geven.pharmapartners_casus.service.impl;

import joshua.geven.pharmapartners_casus.model.Currency;
import joshua.geven.pharmapartners_casus.repository.CurrencyRepository;
import joshua.geven.pharmapartners_casus.service.CurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    // Functions
    @Override
    public Page<Currency> findAll(Pageable pageable) {
        return currencyRepository.findAll(pageable);
    }


    public Currency findById(Long currency_id) {
        return currencyRepository.findById(currency_id).orElseThrow(() -> new RuntimeException("Currency not found"));
    }

    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    public boolean delete(Long currencyId) {
        Currency dbCurrency = currencyRepository.findById(currencyId).orElseThrow(() -> new RuntimeException("Currency not found"));
        currencyRepository.delete(dbCurrency);
        return true;
    }

    public Currency update(Long currencyId, Currency updatedCurrency) {
        return currencyRepository.findById(currencyId)
                .map(existingCurrency -> {
                    existingCurrency.setName(updatedCurrency.getName());
                    existingCurrency.setTicker(updatedCurrency.getTicker());
                    existingCurrency.setMarketCap(updatedCurrency.getMarketCap());
                    existingCurrency.setNumberOfCoins(updatedCurrency.getNumberOfCoins());
                    return currencyRepository.save(existingCurrency);
                })
                .orElseThrow(() -> new RuntimeException("Currency not found"));
    }
}
