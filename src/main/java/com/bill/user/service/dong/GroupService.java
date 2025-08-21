package com.bill.user.service.dong;


import com.bill.user.service.dong.model.AddGroupMemberModel;
import com.bill.user.service.dong.model.DeleteGroupModel;
import com.bill.user.service.dong.model.EditGroupModel;
import com.bill.user.service.dong.model.GetAllGroupResult;
import com.bill.user.service.dong.model.GroupModel;
import com.bill.user.service.dong.model.GroupResult;

public interface GroupService {

	GroupResult addGroup(GroupModel groupModel);

	GetAllGroupResult getGroup(Long userId);

	GroupResult getGroupByGroupId(Long userId, String groupId);

	GroupResult addGroupMember(AddGroupMemberModel model);

	void deleteGroup(DeleteGroupModel model);

	GroupResult editGroup(EditGroupModel group);

}
