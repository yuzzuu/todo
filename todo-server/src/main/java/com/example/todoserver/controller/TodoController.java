package com.example.todoserver.controller;

import lombok.AllArgsConstructor;
import com.example.todoserver.model.TodoEntity;
import com.example.todoserver.model.TodoRequest;
import com.example.todoserver.model.TodoResponse;
import com.example.todoserver.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/todo/")
public class TodoController {

    private final TodoService service;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {

        if (ObjectUtils.isEmpty(request.getTitle()))
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getCompleted()))
            request.setCompleted(false);

        TodoEntity result = this.service.add(request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        List<TodoEntity> list = this.service.searchAll();
        List<TodoResponse> response = list.stream().map(TodoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
        TodoEntity result = this.service.searchById(id);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @PutMapping("{id}")
    public ResponseEntity<TodoEntity> update(@PathVariable Long id, @RequestBody TodoRequest request) {
        TodoEntity result = this.service.updateById(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        this.service.deleteById(id);
        return  ResponseEntity.ok().build();
    }

}
