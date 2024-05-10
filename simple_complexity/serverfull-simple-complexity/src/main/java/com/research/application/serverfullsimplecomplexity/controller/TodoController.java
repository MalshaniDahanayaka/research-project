package com.research.application.serverfullsimplecomplexity.controller;

import com.research.application.serverfullsimplecomplexity.dto.TodoRequest;
import com.research.application.serverfullsimplecomplexity.model.Todo;
import com.research.application.serverfullsimplecomplexity.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping("/")
    public Todo createTodo(@RequestBody TodoRequest todoRequest) {
        return todoService.createTodo(todoRequest);
    }

    @PostMapping("/{id}")
    public Optional<Todo> updateTodo(@PathVariable int id, @RequestBody TodoRequest todoRequest) {
        return todoService.updateTodoById(id, todoRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteTodoById(@PathVariable int id) {
        return todoService.deleteTodoById(id);
    }

}