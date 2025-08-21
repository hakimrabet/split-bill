package com.bill.user.api.rest.dong.mapper;

import com.bill.user.api.rest.dong.model.request.AddExpenseRequest;
import com.bill.user.api.rest.dong.model.request.EditExpenseRequest;
import com.bill.user.api.rest.dong.model.request.SplitDto;
import com.bill.user.api.rest.dong.model.response.ExpenseDto;
import com.bill.user.api.rest.dong.model.response.ExpenseResponse;
import com.bill.user.api.rest.dong.model.response.GetAllExpenseResponse;
import com.bill.user.api.rest.dong.model.response.UserGroupBalanceResponse;
import com.bill.user.common.ResultStatus;
import com.bill.user.service.dong.model.EditExpenseModel;
import com.bill.user.service.dong.model.ExpenseModel;
import com.bill.user.service.dong.model.ExpenseResult;
import com.bill.user.service.dong.model.GetAllExpenseResults;
import com.bill.user.service.dong.model.SplitResult;
import com.bill.user.service.dong.model.UserGroupBalanceModel;
import com.bill.user.service.dong.model.UserGroupBalanceResult;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", imports = { ResultStatus.class })
public interface ExpenseResourceMapper {

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "description", source = "description")
	@Mapping(target = "type", source = "type")
	@Mapping(target = "amount", source = "amount")
	@Mapping(target = "groupId", source = "groupId")
	@Mapping(target = "splits", source = "splits")
	ExpenseModel toExpenseModel(AddExpenseRequest request);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "expense", source = "result")
	@Mapping(target = "result", expression = "java(ResultStatus.SUCCESS)")
	ExpenseResponse toExpenseResponse(ExpenseResult result);

	@Mapping(target = "expenseId", source = "expenseId")
	@Mapping(target = "description", source = "request.description")
	@Mapping(target = "type", source = "request.type")
	@Mapping(target = "amount", source = "request.amount")
	@Mapping(target = "splits", source = "request.splits")
	EditExpenseModel toEditExpenseModel(String expenseId, EditExpenseRequest request);

	@Mapping(target = "expenses", source = "expenses")
	@Mapping(target = "result", expression = "java(ResultStatus.SUCCESS)")
	GetAllExpenseResponse toGetAllExpenseResponse(GetAllExpenseResults results);

	@Mapping(target = "description", source = "description")
	@Mapping(target = "type", source = "type")
	@Mapping(target = "amount", source = "amount")
	@Mapping(target = "groupId", source = "groupId")
	@Mapping(target = "splits", source = "splits")
	@Mapping(target = "creationDate", source = "creationDate")
	@Mapping(target = "expenseId", source = "expenseId")
	ExpenseDto toExpenseDto(ExpenseResult result);

	@Mapping(target = "groupId", source = "groupId")
	@Mapping(target = "userId", source = "userId")
	UserGroupBalanceModel toUserGroupBalanceModel(String userId, String groupId);

	@Mapping(target = "balance", source = "balance")
	@Mapping(target = "result", expression = "java(ResultStatus.SUCCESS)")
	UserGroupBalanceResponse toUserGroupBalanceResponse(UserGroupBalanceResult result);

	@Mapping(target = "expenseId", source = "expenseId")
	@Mapping(target = "userId", source = "userId")
	@Mapping(target = "debtAmount", source = "debtAmount")
	@Mapping(target = "creditAmount", source = "creditAmount")
	SplitDto toSplitDto(SplitResult result);

}
