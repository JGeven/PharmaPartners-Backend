package joshua.geven.pharmapartners_casus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joshua.geven.pharmapartners_casus.model.Currency;
import joshua.geven.pharmapartners_casus.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyService currencyService;

    @TestConfiguration
    static class CurrencyTestConfig {
        @Bean
        public CurrencyService currencyService() {
            return Mockito.mock(CurrencyService.class);
        }
    }

    @Autowired
    private ObjectMapper objectMapper;

    private Currency getMockCurrency() {
        return Currency.builder()
                .id(1L)
                .ticker("BTC")
                .name("Bitcoin")
                .numberOfCoins(16770000L)
                .marketCap(189580000000L)
                .build();
    }

    @Test
    void testFindAll() throws Exception {
        Currency currency = getMockCurrency();
        Page<Currency> currencyPage = new PageImpl<>(Collections.singletonList(currency));
        Mockito.when(currencyService.findAll(any(Pageable.class))).thenReturn(currencyPage);

        mockMvc.perform(get("/currencies?page=0&size=10&sort=ticker"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].ticker").value("BTC"));
    }

    @Test
    void testFindCurrencyById() throws Exception {
        Currency currency = getMockCurrency();
        Mockito.when(currencyService.findById(1L)).thenReturn(currency);

        mockMvc.perform(get("/currencies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticker").value("BTC"));
    }

    @Test
    void testSaveCurrency() throws Exception {
        Currency currency = getMockCurrency();
        Mockito.when(currencyService.save(any(Currency.class))).thenReturn(currency);

        mockMvc.perform(post("/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currency)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Bitcoin"));
    }

    @Test
    void testDeleteCurrency() throws Exception {
        Currency currency = getMockCurrency();

        mockMvc.perform(delete("/currencies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currency)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateCurrency() throws Exception {
        Currency currency = getMockCurrency();
        Mockito.when(currencyService.update(eq(1L), any(Currency.class))).thenReturn(currency);

        mockMvc.perform(put("/currencies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currency)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticker").value("BTC"));
    }
}
