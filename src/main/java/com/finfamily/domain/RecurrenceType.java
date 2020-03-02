package com.finfamily.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "recurrence_type")
public class RecurrenceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "recurrenceType", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<PersonalWallet> personalWallets;

    @OneToMany(mappedBy = "recurrenceType", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<GroupWallet> groupWallets;


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
