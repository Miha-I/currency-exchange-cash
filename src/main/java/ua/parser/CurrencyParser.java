package ua.parser;

import ua.model.CurrencyRate;

import java.util.List;
import java.util.Set;

public interface CurrencyParser {

    void setAvailableCurrencyRate(Set<String> availableCurrencyRate);

    Set<String> getAvailableCurrencyRate();

    List<CurrencyRate> getCurrencyRateList();
}
