package com.bill.user.service.dong;

import com.bill.user.service.dong.model.EditExpenseModel;
import com.bill.user.service.dong.model.ExpenseModel;
import com.bill.user.service.dong.model.ExpenseResult;
import com.bill.user.service.dong.model.GetAllExpenseResults;
import com.bill.user.service.dong.model.SettleResult;
import com.bill.user.service.dong.model.UserGroupBalanceModel;
import com.bill.user.service.dong.model.UserGroupBalanceResult;

public interface ExpenseService {

	ExpenseResult addExpense(ExpenseModel model);

	ExpenseResult editExpense(EditExpenseModel model);

	GetAllExpenseResults getExpenseByGroupId(String groupId);

	UserGroupBalanceResult getUserBalance(UserGroupBalanceModel model);

	ExpenseResult getExpenseByExpenseId(String expenseId);

	void deleteExpenseById(String expenseId);

	SettleResult settleGroup(String groupId);

}