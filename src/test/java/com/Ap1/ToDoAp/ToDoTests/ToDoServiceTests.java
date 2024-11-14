package com.Ap1.ToDoAp.ToDoTests;

import com.Ap1.ToDoAp.ToDo.ToDo;
import com.Ap1.ToDoAp.ToDo.ToDoRepository;
import com.Ap1.ToDoAp.ToDo.ToDoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ToDoServiceTests {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoServiceImpl toDoService;

    private ToDo tacheExemple;

    @BeforeEach
    void setUp() {
        tacheExemple = new ToDo(null, "Tâche Exemple", "Compléter cette tâche", false);
    }

    @Test
    void testCreateToDo() {

        when(toDoRepository.save(any(ToDo.class))).thenReturn(tacheExemple);


        ToDo createdToDo = toDoService.createToDo(tacheExemple);


        assertNotNull(createdToDo);
        assertEquals("Tâche Exemple", createdToDo.getNom());
        assertEquals("Compléter cette tâche", createdToDo.getContenu());
        assertFalse(createdToDo.isStatut());

        verify(toDoRepository, times(1)).save(any(ToDo.class));
    }

    @Test
    void testGetAllToDos() {

        List<ToDo> toDoList = List.of(tacheExemple);
        when(toDoRepository.findAll()).thenReturn(toDoList);

        List<ToDo> result = toDoService.getAllToDos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Tâche Exemple", result.get(0).getNom());

        verify(toDoRepository, times(1)).findAll();
    }

    @Test
    void testGetToDoById_found() {
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(tacheExemple));

        ToDo foundToDo = toDoService.getToDoById(1L);

        assertNotNull(foundToDo);
        assertEquals("Tâche Exemple", foundToDo.getNom());

        verify(toDoRepository, times(1)).findById(1L);
    }

    @Test
    void testGetToDoById_notFound() {
        when(toDoRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            toDoService.getToDoById(999L);
        });

        assertEquals("ToDo non trouvé", exception.getMessage());

        verify(toDoRepository, times(1)).findById(999L);
    }

    @Test
    void testUpdateToDoById() {

        ToDo updatedToDo = new ToDo(1L, "Tâche mise à jour", "Contenu mis à jour", true);
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(tacheExemple));
        when(toDoRepository.save(any(ToDo.class))).thenReturn(updatedToDo);

        // Agir : Appeler la méthode updateToDoById
        ToDo result = toDoService.updateToDoById(1L, updatedToDo);

        // Vérifier : S'assurer que la tâche a bien été mise à jour
        assertNotNull(result);
        assertEquals("Tâche mise à jour", result.getNom());
        assertEquals("Contenu mis à jour", result.getContenu());
        assertTrue(result.isStatut());

        verify(toDoRepository, times(1)).findById(1L);
        verify(toDoRepository, times(1)).save(any(ToDo.class));
    }

    @Test
    void testDeleteToDoById() {

        when(toDoRepository.existsById(1L)).thenReturn(true);
        toDoService.deleteToDoById(1L);

        verify(toDoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteToDoById_notFound() {

        when(toDoRepository.existsById(999L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            toDoService.deleteToDoById(999L);
        });

        assertEquals("ToDo non trouvé", exception.getMessage());

        verify(toDoRepository, times(1)).existsById(999L);
    }
}
