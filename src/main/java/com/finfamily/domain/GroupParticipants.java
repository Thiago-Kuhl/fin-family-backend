package com.finfamily.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "group_participants")
public class GroupParticipants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden=true)
    private int id;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "user_id")
//    private Users users;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "group_id")
//    private Groups groups;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "group_id")
    private int groupId;

    @Column(name = "is_manager")
    private boolean isManager;

    public GroupParticipants(int id, int userId, int groupId, boolean isManager){
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.isManager = isManager;
    }

    public GroupParticipants() {
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setIsManager(boolean manager) {
        isManager = manager;
    }
}
