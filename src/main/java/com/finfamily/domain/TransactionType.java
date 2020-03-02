package com.finfamily.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "transaction_type")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    @OneToMany(mappedBy = "transactionType", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<PersonalWallet> personalWallets;

    @OneToMany(mappedBy = "transactionType", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<GroupWallet> groupWallets;

    public TransactionType(int id, String type){
        this.id = id;
        this.type = type;
    }

    public TransactionType(){}

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
