package com.bill.user.service.dong.model;

import java.util.ArrayList;
import java.util.List;

import com.bill.user.model.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GroupResult {

	private String groupId;

	private String name;

	private User createdBy;

	private List<User> members = new ArrayList<>();

	private String icon;

	private String description;

	private Long creationDate;

}
