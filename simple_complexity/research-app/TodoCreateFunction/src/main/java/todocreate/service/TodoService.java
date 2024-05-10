package todocreate.service;

import lombok.RequiredArgsConstructor;
import todocreate.model.Todo;
import todocreate.repository.TodoRepository;

import java.io.IOException;
import java.sql.SQLException;


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

    public String saveTodo(Todo todo) throws SQLException {
         todoRepository.saveTodo(todo);
         return "Todo saved successfully";
    }


}
