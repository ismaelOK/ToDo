package com.Ap1.ToDoAp.ToDo;

import com.Ap1.ToDoAp.ToDoList.ToDoList;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String contenu;
    private boolean statut;

    @ManyToOne
    private ToDoList toDos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ToDoList getToDos() {
        return toDos;
    }
    public void setToDos(ToDoList toDos) {
        this.toDos = toDos;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }



    public ToDo(Long id, String nom, String contenu, boolean statut) {
        this.id = id;
        this.nom = nom;
        this.contenu = contenu;
        this.statut = statut;
    }
}
