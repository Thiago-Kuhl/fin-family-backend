package com.finfamily.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Embeddable
@Table(name = "groups")
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "group_name")
    private String groupName;

    @OneToMany(mappedBy = "groups", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<GroupParticipants> groupParticipants;

    @OneToOne(mappedBy = "groups", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private GroupWallet groupWallet;

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
