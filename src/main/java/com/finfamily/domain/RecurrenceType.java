package com.finfamily.domain;

import javax.persistence.*;

@Entity
@Table(name = "recurrence_type")
public class RecurrenceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public RecurrenceType (int id, String name) {
        this.id = id;
        this.name = name;
    }

    public RecurrenceType(){}

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
