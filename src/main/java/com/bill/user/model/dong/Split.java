package com.bill.user.model.dong;

import java.io.Serializable;

import com.bill.user.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "splits", indexes = { @Index(name = "ix_splits_expense_id", columnList = "expense_id_fk"),
		@Index(name = "ix_splits_user_id", columnList = "user_id_fk") })
public class Split implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String splitId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id_fk", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_id_fk", nullable = false)
	private Expense expense;

	private Long creditAmount;

	private Long debtAmount;

}
