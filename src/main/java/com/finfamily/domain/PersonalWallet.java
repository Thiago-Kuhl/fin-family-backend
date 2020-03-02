package com.finfamily.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.math.BigDecimal;

@Entity
@Table(name = "personal_wallet")
public class PersonalWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private double value;

    private Date pay_date;

    private boolean is_recorrent;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

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

    public PersonalWallet() {
    }
}
