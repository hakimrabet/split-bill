package com.bill.user.service.dong.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.bill.user.model.dong.Expense;
import com.bill.user.model.dong.Group;
import com.bill.user.model.dong.Split;
import com.bill.user.model.dong.dao.ExpenseDao;
import com.bill.user.model.dong.dao.GroupDao;
import com.bill.user.model.user.User;
import com.bill.user.model.user.dao.UserDao;
import com.bill.user.service.dong.ExpenseService;
import com.bill.user.service.dong.mapper.ExpenseServiceMapper;
import com.bill.user.service.dong.model.EditExpenseModel;
import com.bill.user.service.dong.model.ExpenseModel;
import com.bill.user.service.dong.model.ExpenseResult;
import com.bill.user.service.dong.model.GetAllExpenseResults;
import com.bill.user.service.dong.model.SettleDto;
import com.bill.user.service.dong.model.SettleResult;
import com.bill.user.service.dong.model.SplitResult;
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

	private final UserDao userDao;

	@Override
	public ExpenseResult addExpense(ExpenseModel model) {
		Group group = groupDao.findByGroupId(model.getGroupId());
		Expense expense = mapper.toExpense(model, group);
		for (SplitResult s : model.getSplits()) {
			User user = userDao.findById(s.getUserId())
					.orElseThrow(() -> new IllegalArgumentException("group id not valid"));
			Split split = mapper.toSplit(s, user);
			split.setExpense(expense);
			expense.addSplit(split);
		}
		Expense expenseFromDb = expenseDao.saveAndFlush(expense);
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

	@Transactional(readOnly = true)
	public SettleResult settleGroup(String groupId) {
		List<Expense> expenses = expenseDao.findAllByGroupGroupId(groupId);

		Map<Long, Long> balances = new HashMap<>();
		for (Expense expense : expenses) {
			for (Split split : expense.getSplits()) {
				Long userId = split.getUser()
						.getId();
				Long credit = split.getCreditAmount() == null ? 0L : split.getCreditAmount();
				Long debt = split.getDebtAmount() == null ? 0L : split.getDebtAmount();
				balances.merge(userId, credit - debt, Long::sum);
			}
		}

		Queue<Map.Entry<Long, Long>> debtors = new LinkedList<>();
		Queue<Map.Entry<Long, Long>> creditors = new LinkedList<>();

//		Queue<Pair<Long, Long>> debtors = new LinkedList<>();
//		Queue<Pair<Long, Long>> creditors = new LinkedList<>();

		for (Map.Entry<Long, Long> e : balances.entrySet()) {
			if (e.getValue() < 0) {
				debtors.add(Map.entry(e.getKey(), -e.getValue()));
			} else if (e.getValue() > 0) {
				creditors.add(Map.entry(e.getKey(), e.getValue()));
			}
		}

		List<SettleDto> instructions = new ArrayList<>();

		while (!debtors.isEmpty() && !creditors.isEmpty()) {
			Map.Entry<Long, Long> debtor = debtors.poll();
			Map.Entry<Long, Long> creditor = creditors.poll();

			Long payAmount = Math.min(debtor.getValue(), creditor.getValue());

			instructions.add(new SettleDto(
					debtor.getKey(), creditor.getKey(), payAmount
			));

			if (debtor.getValue() > payAmount) {
				debtors.add(Map.entry(debtor.getKey(), debtor.getValue() - payAmount));
			}
			if (creditor.getValue() > payAmount) {
				creditors.add(Map.entry(creditor.getKey(), creditor.getValue() - payAmount));
			}
		}

		return mapper.toSettleResult(instructions, null);
	}

}