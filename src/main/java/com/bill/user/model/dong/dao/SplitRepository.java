package com.bill.user.model.dong.dao;

import java.util.List;

import com.bill.user.model.dong.Split;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SplitRepository extends JpaRepository<Split, Long> {

	List<Split> findAllByExpense_ExpenseId(String expenseId);

}