package com.finfamily.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "expense_category")
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String type;

    @OneToMany(mappedBy = "expenseCategory", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<PersonalWallet> personalWallets;

    @OneToMany(mappedBy = "expenseCategory", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<GroupWallet> groupWallets;

    public ExpenseCategory (int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public ExpenseCategory (){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
