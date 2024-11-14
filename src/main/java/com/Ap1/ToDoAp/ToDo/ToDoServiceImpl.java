package com.Ap1.ToDoAp.ToDo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Override
    public ToDo createToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }


    @Override
    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }


    @Override
    public ToDo getToDoById(Long id) {
        ToDo toDo = toDoRepository.findById(id).orElse(null);
        if (toDo == null) {
            throw new RuntimeException("ToDo non trouvé");
        }
        return toDo;
    }


    @Override
    public ToDo updateToDoById(Long id, ToDo toDoDetails) {
        ToDo toDo = toDoRepository.findById(id).orElse(null);
        if (toDo == null) {
            throw new RuntimeException("ToDo non trouvé");
        }

        toDo.setNom(toDoDetails.getNom());
        toDo.setContenu(toDoDetails.getContenu());
        toDo.setStatut(toDoDetails.isStatut());

        return toDoRepository.save(toDo);
    }


    @Override
    public void deleteToDoById(Long id) {
        if (!toDoRepository.existsById(id)) {
            throw new RuntimeException("ToDo non trouvé");
        }
        toDoRepository.deleteById(id);
    }
}
