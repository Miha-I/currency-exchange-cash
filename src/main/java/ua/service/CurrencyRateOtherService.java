package ua.service;

import ua.model.CurrencyRate;
import ua.parser.CurrencyParser;

import java.util.List;

public interface CurrencyRateOtherService {

    List<CurrencyRate> getAllNBU();

    List<CurrencyRate> getAllOther();

    void setCurrencyParser(CurrencyParser currencyParser);
}
