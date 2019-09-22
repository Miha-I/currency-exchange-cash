package ua.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyOperation {

    public static final String TYPE_BUY = "buy";
    public static final String TYPE_SELL = "sell";

    public static final String STATUS_NEW = "new";
    public static final String STATUS_CANCEL = "cancel";
    public static final String STATUS_CLOSE = "close";

    private int id;
    private String currencyCode;
    private String typeOperation;
    private BigDecimal amount;
    private BigDecimal totalAmount;
    private String username;
    private String status;
    private LocalDate dateOperation;

    public CurrencyOperation(){}

    public CurrencyOperation(String currencyCode, String typeOperation, BigDecimal amount,
                             BigDecimal totalAmount, String username, String status, LocalDate dateOperation){
        this(0, currencyCode, typeOperation, amount, totalAmount, username, status, dateOperation);
    }

    public CurrencyOperation(int id, String currencyCode, String typeOperation, BigDecimal amount,
                             BigDecimal totalAmount, String username, String status, LocalDate dateOperation){
        this.id = id;
        this.currencyCode = currencyCode;
        this.typeOperation = typeOperation;
        this.amount = amount;
        this.totalAmount = totalAmount;
        this.username = username;
        this.status = status;
        this.dateOperation = dateOperation;
    }

    public int getId(){
        return id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getUsername(){
        return username;
    }

    public String getStatus(){
        return status;
    }

    public LocalDate getDateOperation(){
        return dateOperation;
    }
}
