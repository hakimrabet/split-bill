package com.hakim.bill.service.user.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UsersResult {

	private List<UserInfo> users;

}
