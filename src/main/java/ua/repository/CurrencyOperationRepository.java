package ua.repository;

import ua.model.CurrencyOperation;

import java.util.List;

public interface CurrencyOperationRepository {

    List<CurrencyOperation> findAllByStatus(String status);

    List<CurrencyOperation> findAllByUsernameAndStatus(String username, String... statuses);

    CurrencyOperation findByIdAndStatus(int id, String status);

    void save(CurrencyOperation currencyOperation);

    void updateStatus(CurrencyOperation currencyOperation, String status);
}
