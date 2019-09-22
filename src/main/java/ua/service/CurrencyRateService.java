package ua.service;

import ua.model.CurrencyRate;

import java.util.List;

public interface CurrencyRateService {

    List<CurrencyRate> getAll();

    void saveAll(List<CurrencyRate> currencyRateList);
}
