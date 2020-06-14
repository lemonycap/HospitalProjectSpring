package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "medicine_list")
public class MedicineList {
    @Id
    @GeneratedValue
    @Column (name = "medicine_id")
    private int id;

    @Column(name = "medicine_name")
    private String name;

    @Column (name = "medicine_amount")
    private int amount;


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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
