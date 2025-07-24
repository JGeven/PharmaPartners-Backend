package joshua.geven.pharmapartners_casus.service;

import joshua.geven.pharmapartners_casus.model.Currency;
import joshua.geven.pharmapartners_casus.repository.CurrencyRepository;
import joshua.geven.pharmapartners_casus.service.impl.CurrencyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {

    @Test
    void findAllCurrencies_Test() {
        // Arrange
        CurrencyRepository mockRepository = mock(CurrencyRepository.class);
        CurrencyServiceImpl service = new CurrencyServiceImpl(mockRepository);

        Currency currency = Currency.builder()
                .id(1L)
                .ticker("BTC")
                .name("Bitcoin")
                .numberOfCoins(16770000L)
                .marketCap(189580000000L)
                .build();

        Page<Currency> expectedPage = new PageImpl<>(Collections.singletonList(currency));
        when(mockRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<Currency> actualPage = service.findAll(Pageable.unpaged());

        // Assert
        assertEquals(expectedPage, actualPage);
        verify(mockRepository).findAll(any(Pageable.class));
    }

    @Test
    void findByIdCurrency_Test() {
        // Arrange
        CurrencyRepository mockRepository = mock(CurrencyRepository.class);
        CurrencyServiceImpl service = new CurrencyServiceImpl(mockRepository);

        Currency expectedCurrency = Currency.builder()
                .id(1L)
                .ticker("BTC")
                .name("Bitcoin")
                .numberOfCoins(16770000L)
                .marketCap(189580000000L)
                .build();

        when(mockRepository.findById(1L)).thenReturn(Optional.of(expectedCurrency));

        // Act
        Currency actualCurrency = service.findById(1L);

        // Assert
        assertEquals(expectedCurrency, actualCurrency);
        verify(mockRepository).findById(1L);
    }

    @Test
    void saveCurrency_Test() {
        // Arrange
        CurrencyRepository mockRepository = mock(CurrencyRepository.class);
        CurrencyServiceImpl service = new CurrencyServiceImpl(mockRepository);

        Currency inputCurrency = Currency.builder()
                .ticker("ETH")
                .name("Ethereum")
                .numberOfCoins(96710000L)
                .marketCap(69280000000L)
                .build();

        Currency savedCurrency = Currency.builder()
                .id(2L)
                .ticker("ETH")
                .name("Ethereum")
                .numberOfCoins(96710000L)
                .marketCap(69280000000L)
                .build();

        when(mockRepository.save(any(Currency.class))).thenReturn(savedCurrency);

        // Act
        Currency actualCurrency = service.save(inputCurrency);

        // Assert
        assertEquals(savedCurrency, actualCurrency);
        verify(mockRepository).save(any(Currency.class));
    }

    @Test
    void deleteCurrency_Test() {
        // Arrange
        CurrencyRepository mockRepository = mock(CurrencyRepository.class);
        CurrencyServiceImpl service = new CurrencyServiceImpl(mockRepository);

        Currency currencyToDelete = Currency.builder()
                .id(1L)
                .ticker("BTC")
                .name("Bitcoin")
                .numberOfCoins(16770000L)
                .marketCap(189580000000L)
                .build();

        when(mockRepository.findById(1L)).thenReturn(Optional.of(currencyToDelete));
        doNothing().when(mockRepository).delete(currencyToDelete);

        // Act
        boolean deleted = service.delete(1L);

        // Assert
        assertTrue(deleted);
        verify(mockRepository).findById(1L);
        verify(mockRepository).delete(currencyToDelete);
    }


    @Test
    void updateCurrency_Test() {
        // Arrange
        CurrencyRepository mockRepository = mock(CurrencyRepository.class);
        CurrencyServiceImpl service = new CurrencyServiceImpl(mockRepository);

        Currency existingCurrency = Currency.builder()
                .id(1L)
                .ticker("BTC")
                .name("Bitcoin")
                .numberOfCoins(16770000L)
                .marketCap(189580000000L)
                .build();

        Currency updatedCurrency = Currency.builder()
                .name("Bitcoin Updated")
                .ticker("BTCU")
                .numberOfCoins(17000000L)
                .marketCap(190000000000L)
                .build();

        Currency savedCurrency = Currency.builder()
                .id(1L)
                .name("Bitcoin Updated")
                .ticker("BTCU")
                .numberOfCoins(17000000L)
                .marketCap(190000000000L)
                .build();

        when(mockRepository.findById(1L)).thenReturn(Optional.of(existingCurrency));
        when(mockRepository.save(any(Currency.class))).thenReturn(savedCurrency);

        // Act
        Currency actualCurrency = service.update(1L, updatedCurrency);

        // Assert
        assertEquals(savedCurrency, actualCurrency);
        verify(mockRepository).findById(1L);
        verify(mockRepository).save(any(Currency.class));
    }
}
