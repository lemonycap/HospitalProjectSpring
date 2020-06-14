package com.example.demo.entity;


import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class Role {
    @Id
    @GeneratedValue
    @Column (name = "role_id")
    private int id;

    @Column (name = "name")
    private String name;

   /* @OneToOne(mappedBy = "role")
    private AppUser user;*/

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
}
