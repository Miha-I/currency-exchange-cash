package ua.form;

import ua.model.CurrencyRate;

import java.util.List;

public class CurrencyRateForm {

    private List<CurrencyRate> currencyRateList;

    public void setCurrencyRateList(List<CurrencyRate> currencyRateList) {
        this.currencyRateList = currencyRateList;
    }

    public List<CurrencyRate> getCurrencyRateList() {
        return currencyRateList;
    }
}
