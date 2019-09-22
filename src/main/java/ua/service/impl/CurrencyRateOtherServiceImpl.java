package ua.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.config.AppConfig;
import ua.model.CurrencyRate;
import ua.parser.CurrencyParser;
import ua.parser.impl.CurrencyParserPB;
import ua.repository.CurrencyRateRepository;
import ua.service.CurrencyRateOtherService;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CurrencyRateOtherServiceImpl implements CurrencyRateOtherService {

    private final CurrencyRateRepository currencyRateRepository;

    private final CurrencyParser currencyParserNbu;

    private CurrencyParser currencyParser;

    @Autowired
    public CurrencyRateOtherServiceImpl(CurrencyRateRepository currencyRateRepository, CurrencyParser currencyParserNbu){
        this.currencyRateRepository = currencyRateRepository;
        this.currencyParserNbu = currencyParserNbu;
    }

    @Override
    public List<CurrencyRate> getAllNBU() {
       return parseCurrency(currencyParserNbu);
    }

    // Временно сделал так, но лучше название парсера передавать через контроллер
    @Override
    public List<CurrencyRate> getAllOther(){
        if(currencyParser == null)
            currencyParser = new CurrencyParserPB(AppConfig.PB_URL);
        return parseCurrency(currencyParser);
    }

    private List<CurrencyRate> parseCurrency(CurrencyParser parser){
        List<CurrencyRate> currencyRateListOld = currencyRateRepository.findAll();
        Set<String> currencyRateSet = currencyRateListOld.stream()
                .map(CurrencyRate::getCurrencyCode)
                .collect(Collectors.toSet());
        parser.setAvailableCurrencyRate(currencyRateSet);
        List<CurrencyRate> currencyRateList = parser.getCurrencyRateList();
        currencyRateList = currencyRateList.stream()
                .sorted(Comparator.comparing(CurrencyRate::getCurrencyCode))
                .collect(Collectors.toList());
        return currencyRateList;
    }

    @Override
    public void setCurrencyParser(CurrencyParser currencyParser){
        this.currencyParser = currencyParser;
    }
}
