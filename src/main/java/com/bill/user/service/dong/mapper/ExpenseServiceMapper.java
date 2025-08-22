package com.bill.user.service.dong.mapper;

import java.util.List;

import com.bill.user.api.rest.dong.model.request.SplitDto;
import com.bill.user.common.ResultStatus;
import com.bill.user.model.dong.Expense;
import com.bill.user.model.dong.Group;
import com.bill.user.model.dong.Split;
import com.bill.user.model.user.User;
import com.bill.user.service.dong.model.EditExpenseModel;
import com.bill.user.service.dong.model.ExpenseModel;
import com.bill.user.service.dong.model.ExpenseResult;
import com.bill.user.service.dong.model.GetAllExpenseResults;
import com.bill.user.service.dong.model.SplitResult;
import com.bill.user.util.TrackingCodeProvider;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = { ResultStatus.class, TrackingCodeProvider.class })
public interface ExpenseServiceMapper {

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "description", source = "model.description")
	@Mapping(target = "type", source = "model.type")
	@Mapping(target = "amount", source = "model.amount")
	@Mapping(target = "group", source = "group")
	@Mapping(target = "expenseId", expression = "java(TrackingCodeProvider.generate())")
	Expense toExpense(ExpenseModel model, Group group);

	@Mapping(target = "groupId", source = "group.groupId")
	ExpenseResult toExpenseResult(Expense expense);

	@Mapping(target = "description", source = "model.description")
	@Mapping(target = "type", source = "model.type")
	@Mapping(target = "amount", source = "model.amount")
	@Mapping(target = "splits", source = "model.splits")
	void toExpense(@MappingTarget Expense expense, EditExpenseModel model);

	default GetAllExpenseResults toGetAllExpenseResults(List<Expense> expenses) {
		List<ExpenseResult> expenseResults = expenses.stream()
				.map(this::toExpenseResult)
				.toList();
		GetAllExpenseResults result = new GetAllExpenseResults();
		result.setExpenses(expenseResults);
		return result;
	}

	@Mapping(target = "splitId", expression = "java(TrackingCodeProvider.generate())")
	Split toSplit(SplitDto splitDto);


	default Split toSplit(SplitResult s, User user) {
		Split split = new Split();
		split.setUser(user);
		split.setCreditAmount(s.getCreditAmount() != null ? s.getCreditAmount() : 0L);
		split.setDebtAmount(s.getDebtAmount() != null ? s.getDebtAmount() : 0L);
		split.setSplitId(TrackingCodeProvider.generate());
		return split;
	}
}
