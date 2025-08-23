package com.hakim.bill.service.dong;


import com.hakim.bill.service.dong.model.AddGroupMemberModel;
import com.hakim.bill.service.dong.model.DeleteGroupModel;
import com.hakim.bill.service.dong.model.EditGroupModel;
import com.hakim.bill.service.dong.model.GetAllGroupResult;
import com.hakim.bill.service.dong.model.GroupModel;
import com.hakim.bill.service.dong.model.GroupResult;

public interface GroupService {

	GroupResult addGroup(GroupModel groupModel);

	GetAllGroupResult getGroup(Long userId);

	GroupResult getGroupByGroupId(Long userId, String groupId);

	GroupResult addGroupMember(AddGroupMemberModel model);

	void deleteGroup(DeleteGroupModel model);

	GroupResult editGroup(EditGroupModel group);

}
