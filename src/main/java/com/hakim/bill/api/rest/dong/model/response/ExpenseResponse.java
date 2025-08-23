package com.hakim.bill.api.rest.dong.model.response;

import com.hakim.bill.common.ResponseService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class ExpenseResponse extends ResponseService {

	private ExpenseDto expense;

}