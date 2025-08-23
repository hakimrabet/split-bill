package com.hakim.bill.api.rest.dong.model.response;

import java.util.ArrayList;
import java.util.List;

import com.hakim.bill.api.rest.dong.model.request.SplitDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class ExpenseDto {

	private String expenseId;

	private String groupId;

	private Long amount;

	private String description;

	private Type type;

	private List<SplitDto> splits = new ArrayList<>();

	private Long creationDate;

}
