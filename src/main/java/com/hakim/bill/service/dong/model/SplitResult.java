package com.hakim.bill.service.dong.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SplitResult {

	private String expenseId;

	private Long userId;

	private Long creditAmount;

	private Long debtAmount;

}