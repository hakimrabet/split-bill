package com.bill.user.api.rest.user.mapper;

import com.bill.user.api.rest.user.model.request.LoginUserRequest;
import com.bill.user.api.rest.user.model.request.RegisterUserRequest;
import com.bill.user.api.rest.user.model.response.UserDto;
import com.bill.user.api.rest.user.model.response.UserResponse;
import com.bill.user.api.rest.user.model.response.UsersResponse;
import com.bill.user.common.ResultStatus;
import com.bill.user.service.user.model.AddUserModel;
import com.bill.user.service.user.model.LoginModel;
import com.bill.user.service.user.model.UserInfo;
import com.bill.user.service.user.model.UserResult;
import com.bill.user.service.user.model.UsersResult;
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
