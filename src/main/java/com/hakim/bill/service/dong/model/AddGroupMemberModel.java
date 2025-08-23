package com.hakim.bill.service.dong.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AddGroupMemberModel {

	private Long userId;

	private String groupId;

	private List<Long> members = new ArrayList<>();

}
