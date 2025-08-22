package com.bill.user.api.rest.dong;

import com.bill.user.AbstractContainerBaseTest;
import com.bill.user.model.dong.dao.GroupDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

class GroupControllerIT extends AbstractContainerBaseTest {

	@Autowired
	GroupDao groupDao;

	@BeforeEach
	void setUp() {
		groupDao.deleteAll();
	}

	@Test
	void addGroup() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("X-User-Id", "1");
//
//		AddGroupRequest request = new AddGroupRequest();
//		request.setName("group name");
//		request.setDescription("group desc");
//		request.setIcon("group-icon");
//		request.setMembers(List.of(1L, 2L, 3L));
//
//		HttpEntity<Object> entity = new HttpEntity<>(request, headers);
//
//		ResponseEntity<GroupResponse> response = restTemplate.exchange("/split/groups",
//				HttpMethod.POST, entity, GroupResponse.class);
//
//		assertThat(response).isNotNull();
//		assertThat(response.getBody()
//				.getResult()
//				.getTitle()).isEqualTo(ResultStatus.SUCCESS);
	}

	@Test
	void getAllGroupByUserId() {
	}

	@Test
	void getByGroupId() {
	}

	@Test
	void addMembers() {
	}

	@Test
	void deleteGroup() {
	}

	@Test
	void edit() {
	}
}