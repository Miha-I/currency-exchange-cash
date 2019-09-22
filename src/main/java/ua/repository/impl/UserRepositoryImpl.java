package ua.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.model.User;
import ua.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public User findByUsername(String name) {
        String query = "select * from user_db where username = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            return new User(username, password, role);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
