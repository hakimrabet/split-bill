package com.hakim.bill.api.rest.dong.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SplitDto {

	private String expenseId;

	private Long userId;

	private Long creditAmount;

	private Long debtAmount;

}
