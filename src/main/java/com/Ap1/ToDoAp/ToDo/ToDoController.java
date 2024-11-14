package com.Ap1.ToDoAp.ToDo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/todos")
public class ToDoController {

    private  ToDoService toDoService; //final??


    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }


    @GetMapping
    public List<ToDo> getAllToDos() {
        return toDoService.getAllToDos();
    }



}