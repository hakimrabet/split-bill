package com.hakim.bill.service.dong;

import com.hakim.bill.service.dong.model.EditExpenseModel;
import com.hakim.bill.service.dong.model.ExpenseModel;
import com.hakim.bill.service.dong.model.ExpenseResult;
import com.hakim.bill.service.dong.model.GetAllExpenseResults;
import com.hakim.bill.service.dong.model.SettleResult;
import com.hakim.bill.service.dong.model.UserGroupBalanceModel;
import com.hakim.bill.service.dong.model.UserGroupBalanceResult;

public interface ExpenseService {

	ExpenseResult addExpense(ExpenseModel model);

	ExpenseResult editExpense(EditExpenseModel model);

	GetAllExpenseResults getExpenseByGroupId(String groupId);

	UserGroupBalanceResult getUserBalance(UserGroupBalanceModel model);

	ExpenseResult getExpenseByExpenseId(String expenseId);

	void deleteExpenseById(String expenseId);

	SettleResult settleGroup(String groupId);

}