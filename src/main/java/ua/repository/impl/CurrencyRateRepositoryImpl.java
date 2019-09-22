package ua.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.model.CurrencyRate;
import ua.repository.CurrencyRateRepository;

import java.util.List;

@Repository
public class CurrencyRateRepositoryImpl implements CurrencyRateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CurrencyRateRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CurrencyRate> findAll() {
        String query = "select * from currency_rate";
        List<CurrencyRate> currencyRateList =  jdbcTemplate.query(query, (rs, rowNum) ->
            new CurrencyRate(rs.getString("currency_code"),
                    rs.getBigDecimal("buy_rate"),
                    rs.getBigDecimal("sell_rate"))
        );
        return currencyRateList;
    }

    @Override
    public void saveAll(List<CurrencyRate> currencyRateList){
        String query = "update currency_rate set buy_rate = ?, sell_rate = ? where currency_code = ?";
        for (CurrencyRate currencyRate : currencyRateList)
            jdbcTemplate.update(query, currencyRate.getBuyRate(), currencyRate.getSellRate(), currencyRate.getCurrencyCode());
    }
}
