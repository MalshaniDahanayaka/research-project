package todocreate.repository;

import lombok.RequiredArgsConstructor;
import todocreate.db.DatabaseConnection;
import todocreate.model.Todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@RequiredArgsConstructor
public class TodoRepository {
    
    private static TodoRepository instance = null;
    private DatabaseConnection connection = DatabaseConnection.getInstance();
    
    public static TodoRepository getInstance() {
        if (instance == null) {
            instance = new TodoRepository();
        }
        return instance;
    }

    private static int primaryKey = 0;

    public void saveTodo(Todo todo) throws SQLException {

        Connection con = null;
        primaryKey++;
        Todo newTodo = new Todo(primaryKey, todo.getTitle(), todo.getDescription(), todo.isCompleted());
        String query = "INSERT INTO todos (id, title, description, completed) VALUES (?, ?, ?, ?)";
        try {
            con = connection.getConnection();
            if(con != null) {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, newTodo.getId());
                preparedStatement.setString(2, newTodo.getTitle());
                preparedStatement.setString(3, newTodo.getDescription());
                preparedStatement.setBoolean(4, newTodo.isCompleted());
                preparedStatement.executeUpdate();
            }
//            PreparedStatement preparedStatement = con.prepareStatement(query);
//            preparedStatement.setInt(1, newTodo.getId());
//            preparedStatement.setString(2, newTodo.getTitle());
//            preparedStatement.setString(3, newTodo.getDescription());
//            preparedStatement.setBoolean(4, newTodo.isCompleted());
//            preparedStatement.executeUpdate();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
