package todo.db.manager;

import todo.db.DBConnectionProvider;
import todo.model.Gender;
import todo.model.User;

import java.sql.*;

public class UserManager {
    private final Connection connection;

    public UserManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into user(name,surname,gender,age,phoneNumber,password) Values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, String.valueOf(user.getGender()));
        preparedStatement.setInt(4, user.getAge());
        preparedStatement.setString(5, user.getPhone());
        preparedStatement.setString(6, user.getPassword());
        Statement statement = connection.createStatement();
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            user.setId(id);
        }
    }

    public User getUserByPhoneNumber(String phoneNumber) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USER WHERE phoneNumber =" + phoneNumber);
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setGender(Gender.valueOf(resultSet.getString("gender")));
            user.setPhone(resultSet.getString("phoneNumber"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
        return null;
    }
}

