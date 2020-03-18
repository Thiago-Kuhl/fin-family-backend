package com.finfamily.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Embeddable
@Table(name = "groups")
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true , required = false)
    private int id;

    @Column(name = "group_name")
    private String groupName;

//    @OneToMany(mappedBy = "groups", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Set<GroupParticipants> groupParticipants;

    @OneToOne(mappedBy = "groups", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private GroupWallet groupWallet;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "group_type")
//    private GroupTypes groupTypes;

    @Column(name = "group_type")
    private int groupType;

    @Column(name = "group_owner")
    private int groupOwner;

    public Groups (int id, String groupName, int groupType, int groupOwner){
        this.id = id;
        this.groupName = groupName;
        this.groupType = groupType;
        this.groupOwner = groupOwner;
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

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public int getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(int groupOwner) {
        this.groupOwner = groupOwner;
    }

    public Groups() {
    }
}
