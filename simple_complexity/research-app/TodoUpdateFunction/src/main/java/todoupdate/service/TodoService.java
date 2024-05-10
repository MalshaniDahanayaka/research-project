package todoupdate.service;

import lombok.RequiredArgsConstructor;
import todoupdate.model.Todo;
import todoupdate.repository.TodoRepository;

import java.sql.SQLException;
import java.util.List;


@RequiredArgsConstructor
public class TodoService {

    private static TodoService instance;
    private final TodoRepository todoRepository = TodoRepository.getInstance();


    public static TodoService getInstance() {
        if (instance == null) {
            instance = new TodoService();
        }
        return instance;
    }

    public String updateTodo(Todo todo) throws SQLException {
        try {
            todoRepository.updateTodo(todo);
        }catch (SQLException e){
            throw new SQLException("Error updating todo");
        }

        return "Todo updated successfully";

    }


}
