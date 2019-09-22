package ua.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.model.CurrencyOperation;
import ua.model.CurrencyRate;
import ua.model.TotalAmountBuyAndSale;
import ua.repository.CurrencyOperationRepository;
import ua.service.CurrencyOperationService;
import ua.service.CurrencyRateService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CurrencyOperationServiceImpl implements CurrencyOperationService {

    private final CurrencyOperationRepository currencyOperationRepository;

    private final CurrencyRateService currencyRateService;

    @Autowired
    public CurrencyOperationServiceImpl(CurrencyOperationRepository currencyOperationRepository,
                                        CurrencyRateService currencyRateService){
        this.currencyOperationRepository = currencyOperationRepository;
        this.currencyRateService = currencyRateService;
    }

    @Override
    public List<CurrencyOperation> getAll(){
        List<CurrencyOperation> currencyOperationList =
                currencyOperationRepository.findAllByStatus(CurrencyOperation.STATUS_NEW);

        currencyOperationList = currencyOperationList.stream()
                .sorted(Comparator.comparing(CurrencyOperation::getDateOperation))
                .collect(Collectors.toList());

        return currencyOperationList;
    }

    @Override
    @SuppressWarnings("UnnecessaryLocalVariable")
    public Map<LocalDate, Map<String, Map<String, TotalAmountBuyAndSale>>> getAllByUsername(String username,
                                                                                            String groupBy){
        List<CurrencyOperation> currencyOperationList = currencyOperationRepository.findAllByUsernameAndStatus(username,
                CurrencyOperation.STATUS_NEW, CurrencyOperation.STATUS_CLOSE);

        TemporalAdjuster adjuster = getAdjuster(groupBy);

        Comparator<LocalDate> comparator = (d1, d2) -> d2.compareTo(d1);

        Map<LocalDate, Map<String, Map<String, TotalAmountBuyAndSale>>> groupCurrencyOperation =
                currencyOperationList.stream()
                .collect(Collectors.groupingBy(
                        co -> co.getDateOperation().with(adjuster),
                        () -> new TreeMap<>(comparator),
                        Collectors.groupingBy(
                                CurrencyOperation::getCurrencyCode,
                                Collectors.groupingBy(
                                        CurrencyOperation::getTypeOperation,
                                        Collector.of(
                                                TotalAmountBuyAndSale::new,
                                                TotalAmountBuyAndSale::addCurrencyOperation,
                                                (left, right) -> { left.addTotalAmountBuyAndSale(right); return left;
                                                })))));
        return groupCurrencyOperation;
    }

    private TemporalAdjuster getAdjuster(String groupBy){
        if(groupBy == null) return TemporalAdjusters.ofDateAdjuster(d -> d);
        switch (groupBy){
            case "day": return TemporalAdjusters.ofDateAdjuster(d -> d);
            case "month": return TemporalAdjusters.firstDayOfMonth();
            case "year": return TemporalAdjusters.firstDayOfYear();
            default: throw new IllegalArgumentException("Invalid parameter - " + groupBy);
        }
    }

    @Override
    public void addOperationBuy(String currencyCode, BigDecimal amountCurrency) {
        checkAmountCurrency(amountCurrency);
        CurrencyRate currencyRate = getCurrencyRateByCode(currencyCode);
        BigDecimal totalAmount = amountCurrency.multiply(currencyRate.getSellRate());
        String username =
                ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        CurrencyOperation currencyOperation = new CurrencyOperation(currencyRate.getCurrencyCode(),
                CurrencyOperation.TYPE_BUY, amountCurrency, totalAmount, username,
                CurrencyOperation.STATUS_NEW, LocalDate.now());

        currencyOperationRepository.save(currencyOperation);
    }

    @Override
    public void addOperationSell(String currencyCode, BigDecimal amountCurrency) {
        checkAmountCurrency(amountCurrency);
        CurrencyRate currencyRate = getCurrencyRateByCode(currencyCode);
        BigDecimal totalAmount = amountCurrency.multiply(currencyRate.getBuyRate());
        String username =
                ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        CurrencyOperation currencyOperation = new CurrencyOperation(currencyRate.getCurrencyCode(),
                CurrencyOperation.TYPE_SELL, amountCurrency, totalAmount, username,
                CurrencyOperation.STATUS_NEW, LocalDate.now());

        currencyOperationRepository.save(currencyOperation);
    }

    private void checkAmountCurrency(BigDecimal amountCurrency) {
        if(amountCurrency == null || amountCurrency.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("invalid parameter - " + amountCurrency);
    }

    private CurrencyRate getCurrencyRateByCode(String currencyCode){
        List<CurrencyRate> currencyRateList = currencyRateService.getAll();
        CurrencyRate currencyRate = currencyRateList.stream()
                .filter(cr -> cr.getCurrencyCode().equals(currencyCode))
                .findFirst()
                .orElse(null);

        if(currencyRate == null)
            throw new IllegalArgumentException("Invalid parameter - " + currencyCode);

        return currencyRate;
    }

    @Override
    public void delete(int id){
        CurrencyOperation currencyOperation =
                currencyOperationRepository.findByIdAndStatus(id, CurrencyOperation.STATUS_NEW);

        if(currencyOperation == null)
            throw new IllegalArgumentException("Invalid parameter" + id);

        currencyOperationRepository.updateStatus(currencyOperation, CurrencyOperation.STATUS_CLOSE);
    }
}
