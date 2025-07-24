package joshua.geven.pharmapartners_casus.service;

import joshua.geven.pharmapartners_casus.model.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CurrencyService {
    Page<Currency> findAll(Pageable pageable);
    Currency findById(Long currency_id);
    Currency save(Currency currency);
    boolean delete(Long currencyId);
    Currency update(Long currencyId, Currency updatedCurrency);
}
