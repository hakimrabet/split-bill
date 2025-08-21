package com.bill.user.service.dong.mapper;

import java.util.List;

import com.bill.user.model.dong.Group;
import com.bill.user.model.user.User;
import com.bill.user.service.dong.model.EditGroupModel;
import com.bill.user.service.dong.model.GetAllGroupResult;
import com.bill.user.service.dong.model.GroupModel;
import com.bill.user.service.dong.model.GroupResult;
import com.bill.user.util.TrackingCodeProvider;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", imports = { TrackingCodeProvider.class })
public interface GroupServiceMapper {

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "name", source = "model.name")
	@Mapping(target = "description", source = "model.description")
	@Mapping(target = "icon", source = "model.icon")
	@Mapping(target = "members", source = "users")
	@Mapping(target = "groupId", expression = "java(TrackingCodeProvider.generate())")
	@Mapping(target = "createdBy", source = "createdBy")
	Group toGroup(GroupModel model, List<User> users, User createdBy);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "name", source = "name")
	@Mapping(target = "description", source = "description")
	@Mapping(target = "icon", source = "icon")
	@Mapping(target = "members", source = "members")
	@Mapping(target = "groupId", source = "groupId")
	@Mapping(target = "createdBy", source = "createdBy")
	@Mapping(target = "creationDate", source = "creationDate")
	GroupResult toGroupResult(Group savedGroup);

	default GetAllGroupResult toGetAllGroupResult(List<Group> groups) {
		List<GroupResult> groupResults = groups.stream()
				.map(this::toGroupResult)
				.toList();
		GetAllGroupResult result = new GetAllGroupResult();
		result.setGroupResults(groupResults);
		return result;
	}

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "name", source = "group.name")
	@Mapping(target = "description", source = "group.description")
	@Mapping(target = "icon", source = "group.icon")
	@Mapping(target = "members", source = "members")
	void toGroupUpdate(@MappingTarget Group groupDb, EditGroupModel group, List<User> members);

}
