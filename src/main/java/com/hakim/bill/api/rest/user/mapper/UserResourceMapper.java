package com.hakim.bill.api.rest.user.mapper;

import com.hakim.bill.api.rest.user.model.request.LoginUserRequest;
import com.hakim.bill.api.rest.user.model.request.RegisterUserRequest;
import com.hakim.bill.api.rest.user.model.response.UserDto;
import com.hakim.bill.api.rest.user.model.response.UserResponse;
import com.hakim.bill.api.rest.user.model.response.UsersResponse;
import com.hakim.bill.common.ResultStatus;
import com.hakim.bill.service.user.model.AddUserModel;
import com.hakim.bill.service.user.model.LoginModel;
import com.hakim.bill.service.user.model.UserInfo;
import com.hakim.bill.service.user.model.UserResult;
import com.hakim.bill.service.user.model.UsersResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { ResultStatus.class })
public interface UserResourceMapper {

	@Mapping(target = "users", source = "users")
	@Mapping(target = "result", expression = "java(ResultStatus.SUCCESS)")
	UsersResponse toUsersResponse(UsersResult result);

	@Mapping(target = "user", source = "user")
	@Mapping(target = "result", expression = "java(ResultStatus.SUCCESS)")
	UserResponse toUserResponse(UserResult result);

	@Mapping(target = "id", source = "id")
	@Mapping(target = "name", source = "name")
	@Mapping(target = "username", source = "username")
	UserDto toUserDto(UserInfo userinfo);

	@Mapping(target = "name", source = "name")
	@Mapping(target = "username", source = "username")
	@Mapping(target = "password", source = "password")
	AddUserModel toAddUserModel(RegisterUserRequest request);

	@Mapping(target = "username", source = "username")
	@Mapping(target = "password", source = "password")
	LoginModel toLoginModel(LoginUserRequest request);

}
