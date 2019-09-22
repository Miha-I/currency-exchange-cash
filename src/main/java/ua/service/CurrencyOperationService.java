package ua.service;

import ua.model.CurrencyOperation;
import ua.model.TotalAmountBuyAndSale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CurrencyOperationService {

    List<CurrencyOperation> getAll();

    Map<LocalDate, Map<String, Map<String, TotalAmountBuyAndSale>>> getAllByUsername(String username, String groupBy);

    void addOperationBuy(String codeCurrency, BigDecimal amountCurrency);

    void addOperationSell(String codeCurrency, BigDecimal amountCurrency);

    void delete(int id);
}
