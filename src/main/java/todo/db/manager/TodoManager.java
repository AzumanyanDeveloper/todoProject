package todo.db.manager;

import todo.db.DBConnectionProvider;
import todo.model.Todo;
import todo.model.TodoStatus;

import java.sql.*;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


public class TodoManager {
    private final Connection connection;

    public TodoManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addTodo(Todo todo) throws SQLException, ParseException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into todo(name,deadline,user_id) Values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, todo.getName());
        preparedStatement.setDate(2, todo.getDeadline());
        preparedStatement.setInt(3, todo.getUserId());
        connection.createStatement();
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            todo.setId(id);
        }

    }

    public void printAllTodos(int user_id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT* FROM todo WHERE user_id = ?");
        statement.setInt(1, user_id);
        ResultSet resultSet = statement.executeQuery();
        List<Todo> todo = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo1 = new Todo();
            todo1.setId(resultSet.getInt("id"));
            todo1.setName(resultSet.getString("name"));
            todo1.setCreatedDate(resultSet.getTimestamp("createdDate"));
            todo1.setDeadline(resultSet.getDate("deadline"));
            todo1.setStatus(resultSet.getString("status"));
            todo1.setUserId(resultSet.getInt("user_id"));
            todo.add(todo1);
            for (Todo todo2 : todo) {
                System.out.println(todo2);
            }
        }
    }

    public void printTodosByStatusInProgress(int user_id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM todo WHERE STATUS = ? AND user_id = ?");
        statement.setString(1, String.valueOf(TodoStatus.IN_PROGRESS));
        statement.setInt(2, user_id);
        ResultSet resultSet = statement.executeQuery();
        List<Todo> todos = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreatedDate(resultSet.getTimestamp("createdDate"));
            todo.setDeadline(resultSet.getDate("deadline"));
            todo.setStatus(resultSet.getString("status"));
            todo.setUserId(resultSet.getInt("user_id"));
            todos.add(todo);
            for (Todo todo2 : todos) {
                System.out.println(todo2);
            }
        }
    }

    public void printTodosByStatusInFinished(int user_id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM todo WHERE STATUS = 'FINISHED' AND user_id = ?");
        statement.setInt(1, user_id);
        ResultSet resultSet = statement.executeQuery();
        List<Todo> todos = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreatedDate(resultSet.getTimestamp("createdDate"));
            todo.setDeadline(resultSet.getDate("deadline"));
            todo.setStatus(resultSet.getString("status"));
            todo.setUserId(resultSet.getInt("user_id"));
            todos.add(todo);
            for (Todo todo2 : todos) {
                System.out.println(todo2);
            }
        }
    }

    public void changeTodoStatus(int id, String status, int user_id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE todo SET status = ? WHERE id = ? AND user_id = ?");
        statement.setString(1, status);
        statement.setInt(2, id);
        statement.setInt(3, user_id);
        statement.executeUpdate();
    }

    public void deleteTodoById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM todo WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}




