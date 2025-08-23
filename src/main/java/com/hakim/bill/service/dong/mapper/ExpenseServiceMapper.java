package com.hakim.bill.service.dong.mapper;

import com.hakim.bill.api.rest.dong.model.request.SplitDto;
import com.hakim.bill.common.ResultStatus;
import com.hakim.bill.model.dong.Expense;
import com.hakim.bill.model.dong.Group;
import com.hakim.bill.model.dong.Split;
import com.hakim.bill.model.user.User;
import com.hakim.bill.service.dong.model.*;
import com.hakim.bill.util.TrackingCodeProvider;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", imports = {ResultStatus.class, TrackingCodeProvider.class})
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

    @Mapping(target = "userId", source = "user.id")
    SplitResult toSplitResult(Split split);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "description", source = "model.description")
    @Mapping(target = "type", source = "model.type")
    @Mapping(target = "amount", source = "model.amount")
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

    @Mapping(target = "settles", source = "instructions")
    SettleResult toSettleResult(List<SettleDto> instructions, Object dummy);
}
