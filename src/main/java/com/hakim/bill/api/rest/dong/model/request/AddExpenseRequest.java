package com.hakim.bill.api.rest.dong.model.request;

import java.util.ArrayList;
import java.util.List;

import com.hakim.bill.api.rest.dong.model.response.Type;
import com.hakim.bill.common.RequestService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class AddExpenseRequest extends RequestService {

	private String groupId;

	private Long amount;

	private String description;

	private Type type;

	private List<SplitDto> splits = new ArrayList<>();

}