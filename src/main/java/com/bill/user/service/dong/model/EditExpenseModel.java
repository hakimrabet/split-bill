package com.bill.user.service.dong.model;

import java.util.ArrayList;
import java.util.List;

import com.bill.user.model.dong.Type;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class EditExpenseModel {

	private String expenseId;

	private Long amount;

	private String description;

	private Type type;

	private List<SplitResult> splits = new ArrayList<>();

}
