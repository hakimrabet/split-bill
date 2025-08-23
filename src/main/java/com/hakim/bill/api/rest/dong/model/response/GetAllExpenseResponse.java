package com.hakim.bill.api.rest.dong.model.response;

import java.util.List;

import com.hakim.bill.common.ResponseService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class GetAllExpenseResponse extends ResponseService {

	private List<ExpenseDto> expenses;

}