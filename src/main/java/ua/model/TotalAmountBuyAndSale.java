package ua.model;

import java.math.BigDecimal;

public class TotalAmountBuyAndSale {

    private BigDecimal totalAmount = BigDecimal.ZERO;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void addCurrencyOperation(CurrencyOperation currencyOperation){
        this.totalAmount = totalAmount.add(currencyOperation.getTotalAmount());
    }
    public void addTotalAmountBuyAndSale(TotalAmountBuyAndSale totalAmountBuyAndSale){
        this.totalAmount = totalAmount.add(totalAmountBuyAndSale.totalAmount);
    }

    @Override
    public String toString(){
        return totalAmount.toString();
    }
}
