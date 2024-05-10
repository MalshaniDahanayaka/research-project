package tododelete.service;

import lombok.RequiredArgsConstructor;
import tododelete.repository.TodoRepository;


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

    public String deleteTodo(String id) throws SQLException {
         todoRepository.deleteTodo(Integer.parseInt(id));
         return "Todo delete successfully";
    }


}
