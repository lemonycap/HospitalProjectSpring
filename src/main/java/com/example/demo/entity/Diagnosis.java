package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "diagnosis_table")
public class Diagnosis {
    @Id
    @GeneratedValue
    @Column (name = "id_diagnosis")
    private int id;

    @Column (name = "diagnosis_name")
    private String name;

    @Column (name = "diagnosis_difficulty")
    private String difficulty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
