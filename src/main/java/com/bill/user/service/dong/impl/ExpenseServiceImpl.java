package com.bill.user.service.dong.impl;

import java.util.List;

import com.bill.user.model.dong.Expense;
import com.bill.user.model.dong.Group;
import com.bill.user.model.dong.Split;
import com.bill.user.model.dong.dao.ExpenseDao;
import com.bill.user.model.dong.dao.GroupDao;
import com.bill.user.service.dong.ExpenseService;
import com.bill.user.service.dong.mapper.ExpenseServiceMapper;
import com.bill.user.service.dong.model.EditExpenseModel;
import com.bill.user.service.dong.model.ExpenseModel;
import com.bill.user.service.dong.model.ExpenseResult;
import com.bill.user.service.dong.model.GetAllExpenseResults;
import com.bill.user.service.dong.model.UserGroupBalanceModel;
import com.bill.user.service.dong.model.UserGroupBalanceResult;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

	private final ExpenseDao expenseDao;

	private final GroupDao groupDao;

	private final ExpenseServiceMapper mapper;

	@Override
	public ExpenseResult addExpense(ExpenseModel model) {
		Group group = groupDao.findByGroupId(model.getGroupId());
		Expense expense = mapper.toExpense(model, group);
		Expense expenseFromDb = expenseDao.save(expense);
		return mapper.toExpenseResult(expenseFromDb);
	}

	@Override
	public ExpenseResult editExpense(EditExpenseModel model) {
		Expense expense = expenseDao.findByExpenseId(model.getExpenseId())
				.orElseThrow(() -> new IllegalArgumentException("invalid expense id"));
		mapper.toExpense(expense, model);
		return mapper.toExpenseResult(expense);
	}

	@Override
	@Transactional(readOnly = true)
	public GetAllExpenseResults getExpenseByGroupId(String groupId) {
		List<Expense> expenses = expenseDao.findAllByGroupGroupId(groupId);
		return mapper.toGetAllExpenseResults(expenses);
	}

	@Override
	@Transactional(readOnly = true)
	public UserGroupBalanceResult getUserBalance(UserGroupBalanceModel model) {
		List<Expense> expenses = expenseDao.findAllByGroupGroupId(model.getGroupId());

		Long totalCreditMap = 0L;
		Long totalDebtMap = 0L;

		for (Expense expense : expenses) {
			for (Split split : expense.getSplits()) {
				if (split.getUser()
						.getId() == model.getUserId()) {
					Long creditAmount = split.getCreditAmount();
					Long debtAmount = split.getDebtAmount();

					totalCreditMap += creditAmount;
					totalDebtMap += debtAmount;
				}
			}
		}

		Long balance = totalCreditMap - totalDebtMap;
		UserGroupBalanceResult result = new UserGroupBalanceResult();
		result.setBalance(balance);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public ExpenseResult getExpenseByExpenseId(String expenseId) {
		Expense expense = expenseDao.findByExpenseId(expenseId)
				.orElseThrow(() -> new IllegalArgumentException("expense id not found"));
		return mapper.toExpenseResult(expense);
	}

	@Override
	public void deleteExpenseById(String expenseId) {
		expenseDao.deleteByExpenseId(expenseId);
	}

}