package com.bill.user.service.user.mapper;

import java.util.List;

import com.bill.user.model.user.User;
import com.bill.user.service.user.model.AddUserModel;
import com.bill.user.service.user.model.UserInfo;
import com.bill.user.service.user.model.UserResult;
import com.bill.user.service.user.model.UsersResult;
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
