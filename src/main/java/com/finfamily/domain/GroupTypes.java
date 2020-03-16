package com.finfamily.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "group_types")
public class GroupTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

//    @OneToMany(mappedBy = "groupTypes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Set<Groups> groups;

    public GroupTypes(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
