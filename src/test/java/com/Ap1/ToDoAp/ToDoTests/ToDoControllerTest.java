package com.Ap1.ToDoAp.ToDoTests;

import com.Ap1.ToDoAp.ToDo.ToDo;
import com.Ap1.ToDoAp.ToDo.ToDoController;
import com.Ap1.ToDoAp.ToDo.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ToDoControllerTests {

    @Mock
    private ToDoService toDoService;

    @InjectMocks
    private ToDoController toDoController;

    private MockMvc mockMvc;
    private ToDo tacheExemple;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(toDoController).build();
        tacheExemple = new ToDo(1L, "Tâche Exemple", "Compléter cette tâche", false);
    }

    @Test
    void testGetAllToDos() throws Exception {
        when(toDoService.getAllToDos()).thenReturn(List.of(tacheExemple));

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Tâche Exemple"))
                .andExpect(jsonPath("$[0].contenu").value("Compléter cette tâche"))
                .andExpect(jsonPath("$[0].statut").value(false));

        verify(toDoService, times(1)).getAllToDos();
    }

    @Test
    void testGetToDoById_found() throws Exception {
        when(toDoService.getToDoById(1L)).thenReturn(tacheExemple);

        mockMvc.perform(get("/todos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Tâche Exemple"))
                .andExpect(jsonPath("$.contenu").value("Compléter cette tâche"))
                .andExpect(jsonPath("$.statut").value(false));

        verify(toDoService, times(1)).getToDoById(1L);
    }

    @Test
    void testGetToDoById_notFound() throws Exception {
        when(toDoService.getToDoById(999L)).thenReturn(null);

        mockMvc.perform(get("/todos/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(toDoService, times(1)).getToDoById(999L);
    }

    @Test
    void testCreateToDo() throws Exception {
        ToDo newToDo = new ToDo(null, "Nouvelle tâche", "Compléter cette nouvelle tâche", false);
        when(toDoService.createToDo(any(ToDo.class))).thenReturn(newToDo);

        mockMvc.perform(post("/todos")
                        .contentType("application/json")
                        .content("{\"nom\":\"Nouvelle tâche\", \"contenu\":\"Compléter cette nouvelle tâche\", \"statut\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Nouvelle tâche"))
                .andExpect(jsonPath("$.contenu").value("Compléter cette nouvelle tâche"))
                .andExpect(jsonPath("$.statut").value(false));

        verify(toDoService, times(1)).createToDo(any(ToDo.class));
    }

    @Test
    void testUpdateToDoById_found() throws Exception {
        ToDo updatedToDo = new ToDo(1L, "Tâche mise à jour", "Contenu mis à jour", true);
        when(toDoService.updateToDoById(1L, updatedToDo)).thenReturn(updatedToDo);

        mockMvc.perform(put("/todos/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"nom\":\"Tâche mise à jour\", \"contenu\":\"Contenu mis à jour\", \"statut\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Tâche mise à jour"))
                .andExpect(jsonPath("$.contenu").value("Contenu mis à jour"))
                .andExpect(jsonPath("$.statut").value(true));

        verify(toDoService, times(1)).updateToDoById(1L, updatedToDo);
    }

    @Test
    void testUpdateToDoById_notFound() throws Exception {
        ToDo updatedToDo = new ToDo(999L, "Tâche non existante", "Ce contenu ne devrait pas exister", true);
        when(toDoService.updateToDoById(999L, updatedToDo)).thenReturn(null);

        mockMvc.perform(put("/todos/{id}", 999L)
                        .contentType("application/json")
                        .content("{\"nom\":\"Tâche non existante\", \"contenu\":\"Ce contenu ne devrait pas exister\", \"statut\":true}"))
                .andExpect(status().isNotFound());

        verify(toDoService, times(1)).updateToDoById(999L, updatedToDo);
    }

    @Test
    void testDeleteToDoById() throws Exception {
        doNothing().when(toDoService).deleteToDoById(1L);

        mockMvc.perform(delete("/todos/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(toDoService, times(1)).deleteToDoById(1L);
    }

    @Test
    void testDeleteToDoById_notFound() throws Exception {
        doThrow(new RuntimeException("ToDo non trouvé")).when(toDoService).deleteToDoById(999L);

        mockMvc.perform(delete("/todos/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(toDoService, times(1)).deleteToDoById(999L);
    }
}