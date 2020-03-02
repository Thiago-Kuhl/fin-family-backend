package com.finfamily.domain;

import javax.persistence.*;

@Entity
@Table(name = "receipe_category")
public class ReceipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public ReceipeCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ReceipeCategory() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
