package com.bill.user.api.rest.dong;

import java.util.List;

import com.bill.user.AbstractContainerBaseTest;
import com.bill.user.api.rest.dong.model.request.AddGroupRequest;
import com.bill.user.api.rest.dong.model.response.GroupResponse;
import com.bill.user.common.ResultStatus;
import com.bill.user.model.dong.dao.GroupDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GroupControllerIT extends AbstractContainerBaseTest {

	@Autowired
	GroupDao groupDao;

	@BeforeEach
	void setUp() {
		groupDao.deleteAll();
	}

	@Test
	void addGroup() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-User-Id", "1");

		AddGroupRequest request = new AddGroupRequest();
		request.setName("group name");
		request.setDescription("group desc");
		request.setIcon("group-icon");
		request.setMembers(List.of(1L, 2L, 3L));

		HttpEntity<Object> entity = new HttpEntity<>(request, headers);

		ResponseEntity<GroupResponse> response = restTemplate.exchange("/split/groups",
				HttpMethod.POST, entity, GroupResponse.class);

		assertThat(response).isNotNull();
		assertThat(response.getBody()
				.getResult()
				.getTitle()).isEqualTo(ResultStatus.SUCCESS);
		assertThat(response.getBody()
				.getGroup()
				.getCreatedBy()).isEqualTo(1L);
		assertThat(response.getBody()
				.getGroup()
				.getCreationDate()).isNotNull();
		assertThat(response.getBody()
				.getGroup()
				.getDescription()).isEqualTo("group desc");
		assertThat(response.getBody()
				.getGroup()
				.getGroupId()).isNotNull();
		assertThat(response.getBody()
				.getGroup()
				.getIcon()).isEqualTo("group-icon");
		assertThat(response.getBody()
				.getGroup()
				.getMembers()).contains(1L, 2L, 3L);
		assertThat(response.getBody()
				.getGroup()
				.getName()).isEqualTo("group name");
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