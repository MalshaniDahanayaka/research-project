package tododelete.repository;

import lombok.RequiredArgsConstructor;
import tododelete.db.DatabaseConnection;

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

    public void deleteTodo(int id) throws SQLException {
        Connection con = null;
        String query = "DELETE FROM todos WHERE id = ?";
        try {
            con = connection.getConnection();
            if (con != null) {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

}
