package todoretrieve.repository;

import lombok.RequiredArgsConstructor;
import todoretrieve.db.DatabaseConnection;
import todoretrieve.model.Todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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

    public List<Todo> getAllTodos() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        String query = "SELECT * FROM todos";

        try (Connection con = connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                boolean completed = resultSet.getBoolean("completed");

                Todo todo = new Todo(id, title, description, completed);
                todos.add(todo);
            }
        }

        return todos;
    }
}
