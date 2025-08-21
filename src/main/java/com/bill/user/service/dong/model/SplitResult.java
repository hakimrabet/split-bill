package com.bill.user.service.dong.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SplitResult {

	private String expenseId;

	private String userId;

	private Long creditAmount;

	private Long debtAmount;

}