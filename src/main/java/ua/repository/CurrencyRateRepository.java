package ua.repository;

import ua.model.CurrencyRate;

import java.util.List;

public interface CurrencyRateRepository {

    List<CurrencyRate> findAll();

    void saveAll(List<CurrencyRate> currencyRateList);
}
