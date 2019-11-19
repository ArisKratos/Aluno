package com.example.alunotcc.Modelo;

import androidx.annotation.NonNull;

public class Curso {

    private String id;
    private String curso;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }


    @NonNull
    @Override
    public String toString() {
        return curso;
    }
}
