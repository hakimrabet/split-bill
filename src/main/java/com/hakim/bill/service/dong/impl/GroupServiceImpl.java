package com.hakim.bill.service.dong.impl;

import java.util.List;
import java.util.Optional;

import com.hakim.bill.model.dong.Group;
import com.hakim.bill.model.dong.dao.GroupDao;
import com.hakim.bill.model.user.User;
import com.hakim.bill.model.user.dao.UserDao;
import com.hakim.bill.service.dong.GroupService;
import com.hakim.bill.service.dong.mapper.GroupServiceMapper;
import com.hakim.bill.service.dong.model.AddGroupMemberModel;
import com.hakim.bill.service.dong.model.DeleteGroupModel;
import com.hakim.bill.service.dong.model.EditGroupModel;
import com.hakim.bill.service.dong.model.GetAllGroupResult;
import com.hakim.bill.service.dong.model.GroupModel;
import com.hakim.bill.service.dong.model.GroupResult;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {

	private final GroupDao groupDao;

	private final UserDao userRepo;

	private final GroupServiceMapper mapper;

	@Override
	public GroupResult addGroup(GroupModel model) {
		List<User> members = userRepo.findAllByIdIn(model.getMembers());
		User createdBy = userRepo.findById(model.getCreatedBy())
				.orElseThrow(
						() -> new IllegalArgumentException("group id not valid")
				);
		Group group = mapper.toGroup(model, members, createdBy);
		Group savedGroup = groupDao.save(group);
		return mapper.toGroupResult(savedGroup);
	}

	@Override
	@Transactional(readOnly = true)
	public GetAllGroupResult getGroup(Long userId) {
		List<Group> groups = groupDao.findAllByMembers_Id(userId);
		return mapper.toGetAllGroupResult(groups);
	}

	@Override
	@Transactional(readOnly = true)
	public GroupResult getGroupByGroupId(Long userId, String groupId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("group id not valid"));
		Group group = groupDao.findByGroupIdAndMembersContains(groupId, user)
				.orElseThrow(() -> new IllegalArgumentException("group id not valid"));
		return mapper.toGroupResult(group);
	}

	@Override
	public GroupResult addGroupMember(AddGroupMemberModel model) {
		User user = userRepo.findById(model.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("group id not valid"));

		Group group = groupDao.findByGroupIdAndMembersContains(model.getGroupId(), user)
				.orElseThrow(() -> new IllegalArgumentException("group id not valid"));

		List<User> members = userRepo.findAllByIdIn(model.getMembers());
		group.addMembers(members);

		Group saved = groupDao.save(group);
		return mapper.toGroupResult(saved);
	}

	@Override
	public void deleteGroup(DeleteGroupModel model) {
		User user = userRepo.findById(model.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("group id not valid"));

		Optional<Group> maybeGroup = groupDao.findByGroupIdAndCreatedBy(model.getGroupId(), user);
		maybeGroup.ifPresent(groupDao::delete);
	}

	@Override
	public GroupResult editGroup(EditGroupModel model) {
		User user = userRepo.findById(model.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("group id not valid"));

		Group groupDb = groupDao.findByGroupIdAndCreatedBy(model.getGroupId(), user)
				.orElseThrow(() -> new IllegalArgumentException("group id not valid"));

		List<User> members = userRepo.findAllByIdIn(model.getMembers());

		mapper.toGroupUpdate(groupDb, model, members);
		Group group = groupDao.save(groupDb);
		return mapper.toGroupResult(group);
	}

}