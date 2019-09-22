package ua.model;

import java.math.BigDecimal;

public class CurrencyRate {

    public static final String BASE_CCY = "UAH";

    private String currencyCode;
    private BigDecimal buyRate;
    private BigDecimal sellRate;

    public CurrencyRate(){}

    public CurrencyRate(String currencyCode, BigDecimal buyRate, BigDecimal sellRate) {
        this.currencyCode = currencyCode;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }
}
