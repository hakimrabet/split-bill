package com.bill.user.model.dong.dao;

import java.util.List;
import java.util.Optional;

import com.bill.user.model.dong.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseDao extends JpaRepository<Expense, Long> {

	List<Expense> findAllByGroupGroupId(String groupId);

	Optional<Expense> findByExpenseId(String expenseId);

	void deleteByExpenseId(String expenseId);

}
