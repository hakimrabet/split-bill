package com.hakim.bill.api.rest.dong;

import com.hakim.bill.api.rest.dong.mapper.GroupResourceMapper;
import com.hakim.bill.api.rest.dong.model.request.AddGroupMemberRequest;
import com.hakim.bill.api.rest.dong.model.request.AddGroupRequest;
import com.hakim.bill.api.rest.dong.model.request.EditGroupRequest;
import com.hakim.bill.api.rest.dong.model.response.GetAllGroupResponse;
import com.hakim.bill.api.rest.dong.model.response.GroupResponse;
import com.hakim.bill.common.GeneralResponse;
import com.hakim.bill.service.dong.GroupService;
import com.hakim.bill.service.dong.model.AddGroupMemberModel;
import com.hakim.bill.service.dong.model.DeleteGroupModel;
import com.hakim.bill.service.dong.model.EditGroupModel;
import com.hakim.bill.service.dong.model.GetAllGroupResult;
import com.hakim.bill.service.dong.model.GroupModel;
import com.hakim.bill.service.dong.model.GroupResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/split/groups")
public class GroupController {

	private final GroupService groupService;

	private final GroupResourceMapper mapper;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupResponse> addGroup(@RequestHeader("X-User-Id") Long userId, @RequestBody AddGroupRequest request) {
		GroupModel model = mapper.toGroupModel(userId, request);
		GroupResponse groupResponse = mapper.toGroupResponse(groupService.addGroup(model));
		return ResponseEntity.ok(groupResponse);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetAllGroupResponse> getAllGroupByUserId(@RequestHeader("X-User-Id") Long userId) {
		GetAllGroupResult groups = groupService.getGroup(userId);
		return ResponseEntity.ok(mapper.toGetAllGroupResponse(groups));
	}

	@GetMapping(path = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupResponse> getByGroupId(@RequestHeader("X-User-Id") Long userId, @PathVariable(name = "groupId") String groupId) {
		GroupResult result = groupService.getGroupByGroupId(userId, groupId);
		return ResponseEntity.ok(mapper.toGroupResponse(result));
	}

	@PostMapping(path = "/{groupId}/members", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupResponse> addMembers(@RequestHeader("X-User-Id") Long userId, @PathVariable(name = "groupId") String groupId, @RequestBody AddGroupMemberRequest request) {
		AddGroupMemberModel model = mapper.toAddGroupMemberModel(userId, groupId, request);
		GroupResult result = groupService.addGroupMember(model);
		return ResponseEntity.ok(mapper.toGroupResponse(result));
	}

	@DeleteMapping(path = "/{groupId}")
	public ResponseEntity<GeneralResponse> deleteGroup(@RequestHeader("X-User-Id") Long userId, @PathVariable(name = "groupId") String groupId) {
		DeleteGroupModel model = mapper.toDeleteGroupModel(userId, groupId);
		groupService.deleteGroup(model);
		return ResponseEntity.ok(GeneralResponse.success());
	}

	@PutMapping(path = "/{groupId}", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupResponse> edit(@RequestHeader("X-User-Id") Long userId,
			@PathVariable(name = "groupId") String groupId, @Valid @RequestBody EditGroupRequest request) {
		EditGroupModel model = mapper.EditGroupModel(userId, groupId, request);
		GroupResult result = groupService.editGroup(model);
		return ResponseEntity.ok(mapper.toGroupResponse(result));
	}

}
