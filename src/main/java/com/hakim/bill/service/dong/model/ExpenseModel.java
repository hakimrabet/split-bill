package com.hakim.bill.service.dong.model;

import java.util.ArrayList;
import java.util.List;

import com.hakim.bill.model.dong.Type;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class ExpenseModel {

	private String expenseId;

	private String groupId;

	private Long amount;

	private String description;

	private Type type;

	private List<SplitResult> splits = new ArrayList<>();

}
