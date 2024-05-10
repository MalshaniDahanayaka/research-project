package todoretrieve.service;

import lombok.RequiredArgsConstructor;
import todoretrieve.model.Todo;
import todoretrieve.repository.TodoRepository;

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

    public List<Todo> getAllTodos() throws SQLException {
         return todoRepository.getAllTodos();
    }


}
