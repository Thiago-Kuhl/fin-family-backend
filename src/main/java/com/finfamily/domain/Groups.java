package com.finfamily.domain;

import javax.persistence.*;

@Entity
@Embeddable
@Table(name = "groups")
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "group_name")
    private String groupName;

    public Groups (int id, String groupName){
        this.id = id;
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Groups() {
    }
}
