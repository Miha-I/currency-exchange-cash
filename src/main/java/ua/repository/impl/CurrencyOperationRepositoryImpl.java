package ua.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.model.CurrencyOperation;
import ua.repository.CurrencyOperationRepository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CurrencyOperationRepositoryImpl implements CurrencyOperationRepository {

    private final DataSource dataSource;

    @Autowired
    public CurrencyOperationRepositoryImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<CurrencyOperation> findAllByStatus(String status) {
        String query = "select * from currency_operation where status = ?";
        List<CurrencyOperation> currencyOperationList = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                CurrencyOperation currencyOperation = createCurrencyOperation(resultSet);
                currencyOperationList.add(currencyOperation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencyOperationList;
    }

    @Override
    public List<CurrencyOperation> findAllByUsernameAndStatus(String username, String... statuses){
        String query = "select * from currency_operation where username = ? and status = ? ";
        List<CurrencyOperation> currencyOperationList = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for(String status : statuses){
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, status);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    CurrencyOperation currencyOperation = createCurrencyOperation(resultSet);
                    currencyOperationList.add(currencyOperation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencyOperationList;
    }

    @Override
    public CurrencyOperation findByIdAndStatus(int id, String status){
        String query = "select * from currency_operation where id = ? and status = ?";
        CurrencyOperation currencyOperation = null;
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                currencyOperation = createCurrencyOperation(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencyOperation;
    }

    private CurrencyOperation createCurrencyOperation(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String currencyCode = resultSet.getString("currency_code");
        String typeOperation = resultSet.getString("type_operation");
        BigDecimal amount = resultSet.getBigDecimal("amount");
        BigDecimal totalAmount = resultSet.getBigDecimal("total_amount");
        String username = resultSet.getString("username");
        String status = resultSet.getString("status");
        LocalDate dateOperation = resultSet.getDate("date_operation").toLocalDate();
        return new CurrencyOperation(id, currencyCode, typeOperation, amount, totalAmount, username, status, dateOperation);
    }

    @Override
    public void save(CurrencyOperation currencyOperation) {
        //String query = "insert into currency_operation values(?, ?, ?, ?, ?, ?, ?)";

        String query = "insert into currency_operation(currency_code, type_operation, amount, total_amount, " +
                "username, status, date_operation) values(?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currencyOperation.getCurrencyCode());
            preparedStatement.setString(2, currencyOperation.getTypeOperation());
            preparedStatement.setBigDecimal(3, currencyOperation.getAmount());
            preparedStatement.setBigDecimal(4, currencyOperation.getTotalAmount());
            preparedStatement.setString(5, currencyOperation.getUsername());
            preparedStatement.setString(6, currencyOperation.getStatus());
            preparedStatement.setDate(7, Date.valueOf(currencyOperation.getDateOperation()));
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(CurrencyOperation currencyOperation, String status) {
        String query = "UPDATE currency_operation SET status = ? WHERE id = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, currencyOperation.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
