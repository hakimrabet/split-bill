package com.bill.user.service.dong.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GetAllExpenseResults {

	private List<ExpenseResult> expenses = new ArrayList<>();

}
