package com.bill.user.api.rest.dong.model.response;

import java.util.List;

import com.bill.user.common.ResponseService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class GetAllExpenseResponse extends ResponseService {

	private List<ExpenseDto> expenses;

}