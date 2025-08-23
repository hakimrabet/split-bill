package com.hakim.bill.service.dong.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EditGroupModel {

	private Long userId;

	private String groupId;

	private String name;

	private List<Long> members = new ArrayList<>();

	private String icon;

	private String description;

}
