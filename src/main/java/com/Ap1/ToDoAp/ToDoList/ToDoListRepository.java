package com.Ap1.ToDoAp.ToDoList;

import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Id> {
}
