package com.bill.user.model.dong;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenses", indexes = {@Index(name = "ux_expenses_expense_id", columnList = "expenseId", unique = true),
        @Index(name = "ix_expenses_group_id", columnList = "group_id")})
public class Expense implements Serializable {

    @Serial
    private static final long serialVersionUID = -8152521988572821259L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String expenseId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    @JsonBackReference
    private Group group;

    @Column(nullable = false)
    private Long amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Split> splits = new ArrayList<>();

    @CreatedDate
    @Column(name = "creation_date")
    private Long creationDate;

    @LastModifiedDate
    @Column(name = "last_modification_date")
    private Long lastModificationDate;

    @Version
    private Long version;

    public void addSplit(Split split) {
        this.splits.add(split);
    }

}
