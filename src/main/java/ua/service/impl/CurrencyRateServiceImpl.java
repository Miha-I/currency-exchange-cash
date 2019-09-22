package ua.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.model.CurrencyRate;
import ua.repository.CurrencyRateRepository;
import ua.service.CurrencyRateService;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final CurrencyRateRepository currencyRateRepository;

    @Autowired
    public CurrencyRateServiceImpl(CurrencyRateRepository currencyRateRepository){
        this.currencyRateRepository = currencyRateRepository;
    }

    @Override
    public List<CurrencyRate> getAll() {
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        currencyRateList = currencyRateList.stream()
                .sorted(Comparator.comparing(CurrencyRate::getCurrencyCode))
                .collect(Collectors.toList());
        return currencyRateList;
    }

    @Override
    @Transactional
    public void saveAll(List<CurrencyRate> currencyRateList){
        List<CurrencyRate> currencyRateListOld = getAll();
        Set<String> currencyRateSet = currencyRateListOld.stream()
                .map(CurrencyRate::getCurrencyCode)
                .collect(Collectors.toSet());

        checkCurrencyLists(currencyRateList, currencyRateSet);
        currencyRateRepository.saveAll(currencyRateList);
    }

    private void checkCurrencyLists(List<CurrencyRate> currencyRateList, Set<String> currencyRateSet){
        for (CurrencyRate currencyRate : currencyRateList){
            if(!currencyRateSet.contains(currencyRate.getCurrencyCode()))
                throw new IllegalArgumentException("Invalid parameter - " + currencyRate.getCurrencyCode());

            BigDecimal sellRate = currencyRate.getSellRate();
            // Сравнение можно производить с нулём <= 0, в данном случае ноль означает что в обменнике валюты нет
            if(sellRate == null || sellRate.compareTo(BigDecimal.ZERO) < 0)
                throw new IllegalArgumentException("Invalid parameter - " + currencyRate.getSellRate());

            BigDecimal buyRate = currencyRate.getBuyRate();
            if(buyRate == null || buyRate.compareTo(BigDecimal.ZERO) < 0)
                throw new IllegalArgumentException("Invalid parameter - " + currencyRate.getSellRate());
        }
    }
}
