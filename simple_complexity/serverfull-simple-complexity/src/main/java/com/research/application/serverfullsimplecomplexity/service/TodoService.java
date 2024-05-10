package com.research.application.serverfullsimplecomplexity.service;


import com.research.application.serverfullsimplecomplexity.dto.TodoRequest;
import com.research.application.serverfullsimplecomplexity.model.Todo;
import com.research.application.serverfullsimplecomplexity.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo createTodo(TodoRequest todoRequest) {
        Todo todo = new Todo();
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setCompleted(todoRequest.isCompleted());
        return todoRepository.save(todo);
    }

    public Optional<Todo> updateTodoById(int todoId, TodoRequest updatedTodo) {
        Optional<Todo> todo = todoRepository.findById(todoId);
        if (todo.isPresent()) {
            Todo todoToUpdate = todo.get();
            todoToUpdate.setTitle(updatedTodo.getTitle());
            todoToUpdate.setDescription(updatedTodo.getDescription());
            todoToUpdate.setCompleted(updatedTodo.isCompleted());

            todoRepository.saveAndFlush(todoToUpdate);
        }
        return todo;
    }

    public String deleteTodoById(int todoId) {
        todoRepository.deleteById(todoId);
        return "Todo deleted successfully!";
    }

}
