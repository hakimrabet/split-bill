package com.hakim.bill.api.rest.dong.mapper;

import java.util.List;

import com.hakim.bill.api.rest.dong.model.request.AddGroupMemberRequest;
import com.hakim.bill.api.rest.dong.model.request.AddGroupRequest;
import com.hakim.bill.api.rest.dong.model.request.EditGroupRequest;
import com.hakim.bill.api.rest.dong.model.response.GetAllGroupResponse;
import com.hakim.bill.api.rest.dong.model.response.GroupDto;
import com.hakim.bill.api.rest.dong.model.response.GroupResponse;
import com.hakim.bill.common.ResultStatus;
import com.hakim.bill.model.user.User;
import com.hakim.bill.service.dong.model.AddGroupMemberModel;
import com.hakim.bill.service.dong.model.DeleteGroupModel;
import com.hakim.bill.service.dong.model.EditGroupModel;
import com.hakim.bill.service.dong.model.GetAllGroupResult;
import com.hakim.bill.service.dong.model.GroupModel;
import com.hakim.bill.service.dong.model.GroupResult;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", imports = { ResultStatus.class })
public interface GroupResourceMapper {

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "name", source = "request.name")
	@Mapping(target = "description", source = "request.description")
	@Mapping(target = "createdBy", source = "userId")
	@Mapping(target = "icon", source = "request.icon")
	@Mapping(target = "members", source = "request.members")
	GroupModel toGroupModel(Long userId, AddGroupRequest request);

	@Mapping(target = "result", expression = "java(ResultStatus.SUCCESS)")
	@Mapping(target = "group", source = "group")
	GroupResponse toGroupResponse(GroupResult group);

	@Mapping(target = "createdBy", source = "group.createdBy.id")
	@Mapping(target = "members", source = "group.members")
	GroupDto toGroupDto(GroupResult group);

	default List<Long> usersToIds(List<User> users) {
		if (users == null) {
			return null;
		}
		return users.stream()
				.map(User::getId)
				.toList();
	}

	@Mapping(target = "result", expression = "java(ResultStatus.SUCCESS)")
	@Mapping(target = "groups", source = "groupResults")
	GetAllGroupResponse toGetAllGroupResponse(GetAllGroupResult results);

	@Mapping(target = "userId", source = "userId")
	@Mapping(target = "groupId", source = "groupId")
	@Mapping(target = "members", source = "request.members")
	AddGroupMemberModel toAddGroupMemberModel(Long userId, String groupId, AddGroupMemberRequest request);

	@Mapping(target = "groupId", source = "groupId")
	@Mapping(target = "userId", source = "userId")
	DeleteGroupModel toDeleteGroupModel(Long userId, String groupId);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "members", source = "request.members")
	@Mapping(target = "icon", source = "request.icon")
	@Mapping(target = "name", source = "request.name")
	@Mapping(target = "description", source = "request.description")
	@Mapping(target = "userId", source = "userId")
	@Mapping(target = "groupId", source = "groupId")
	EditGroupModel EditGroupModel(Long userId, String groupId, EditGroupRequest request);

}
