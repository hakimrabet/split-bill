package com.hakim.bill.service.dong.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetAllGroupResult {

	private List<GroupResult> groupResults = new ArrayList<>();

}
