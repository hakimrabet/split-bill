package com.bill.user.api.rest.dong.model.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GroupDto {

	private String groupId;

	private String name;

	private Long createdBy;

	private List<Long> members = new ArrayList<>();

	private String icon;

	private String description;

	private Long creationDate;

}
