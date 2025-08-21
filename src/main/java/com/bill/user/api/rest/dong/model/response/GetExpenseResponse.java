package com.bill.user.api.rest.dong.model.response;

import java.util.ArrayList;
import java.util.List;

import com.bill.user.api.rest.dong.model.request.SplitDto;
import com.bill.user.common.ResponseService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class GetExpenseResponse extends ResponseService {

	private String expenseId;

	private String groupId;

	private Long amount;

	private String description;

	private Type type;

	private List<SplitDto> splits = new ArrayList<>();

	private Long creationDate;

}