package com.Ap1.ToDoAp.ToDo;

import java.util.List;
import java.util.Optional;

public interface ToDoService {



    ToDo createToDo(ToDo toDo);


    List<ToDo> getAllToDos();


    ToDo getToDoById(Long id);


    ToDo updateToDoById(Long id, ToDo toDoDetails);


    void deleteToDoById(Long id);
}
