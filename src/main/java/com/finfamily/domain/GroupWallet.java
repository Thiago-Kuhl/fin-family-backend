package com.finfamily.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "group_wallet")
public class GroupWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden=true)
    private int id;

    private String name;

    private String description;

    private double value;

    private Date pay_date;

    private boolean is_recorrent;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_recurrence_type", nullable = true)
    private RecurrenceType recurrenceType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_transaction_type", nullable = false)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_receipe_category", nullable = true)
    private ReceipeCategory receipeCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_expense_category", nullable = true)
    private ExpenseCategory expenseCategory;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

}
