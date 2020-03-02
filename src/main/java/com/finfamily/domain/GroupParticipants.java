package com.finfamily.domain;

import javax.persistence.*;

@Entity
@Table(name = "group_participants")
public class GroupParticipants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "group_id")
    private Groups groups;

    private boolean permission_type;

    public GroupParticipants(int id, Users users, Groups groups){
        this.id = id;
        this.users = users;
        this.groups = groups;
    }

    public GroupParticipants() {
    }

    public int getId() {
        return id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public boolean isPermission_type() {
        return permission_type;
    }

    public void setPermission_type(boolean permission_type) {
        this.permission_type = permission_type;
    }
}
