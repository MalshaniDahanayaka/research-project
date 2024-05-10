package todoupdate.repository;

import lombok.RequiredArgsConstructor;
import todoupdate.db.DatabaseConnection;
import todoupdate.model.Todo;

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

    public void updateTodo(Todo todoToUpdate) throws SQLException {
        String query = "UPDATE todos SET title = ?, description = ?, completed = ? WHERE id = ?";

        try (Connection con = connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, todoToUpdate.getTitle());
            preparedStatement.setString(2, todoToUpdate.getDescription());
            preparedStatement.setBoolean(3, todoToUpdate.isCompleted());
            preparedStatement.setInt(4, todoToUpdate.getId());

            preparedStatement.executeUpdate();
        }
    }

}
