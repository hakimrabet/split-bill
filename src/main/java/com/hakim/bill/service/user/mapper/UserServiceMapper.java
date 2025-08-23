package com.hakim.bill.service.user.mapper;

import java.util.List;

import com.hakim.bill.model.user.User;
import com.hakim.bill.service.user.model.AddUserModel;
import com.hakim.bill.service.user.model.UserInfo;
import com.hakim.bill.service.user.model.UserResult;
import com.hakim.bill.service.user.model.UsersResult;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserServiceMapper {

	default UsersResult toUsersResult(List<UserInfo> users) {
		var result = new UsersResult();
		result.setUsers(users);
		return result;
	}

	@Mapping(target = "user", source = "user")
	UserResult toUserResult(User user);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "name", source = "name")
	@Mapping(target = "username", source = "username")
	UserInfo toUserInfo(User user);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "name", source = "model.name")
	@Mapping(target = "username", source = "model.username")
	@Mapping(target = "password", source = "password")
	User toUser(AddUserModel model, String password);

}
