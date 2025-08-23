package com.hakim.bill.api.rest.dong;

import com.hakim.bill.AbstractContainerBaseTest;
import com.hakim.bill.api.rest.dong.model.request.AddGroupMemberRequest;
import com.hakim.bill.api.rest.dong.model.request.AddGroupRequest;
import com.hakim.bill.api.rest.dong.model.request.EditGroupRequest;
import com.hakim.bill.api.rest.dong.model.response.GetAllGroupResponse;
import com.hakim.bill.api.rest.dong.model.response.GroupResponse;
import com.hakim.bill.common.GeneralResponse;
import com.hakim.bill.common.ResultStatus;
import com.hakim.bill.model.dong.Group;
import com.hakim.bill.model.dong.dao.GroupDao;
import com.hakim.bill.model.user.User;
import com.hakim.bill.model.user.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GroupControllerIT extends AbstractContainerBaseTest {

    @Autowired
    GroupDao groupDao;

    @Autowired
    private UserDao userDao;

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
        createGroup();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<GetAllGroupResponse> response = restTemplate.exchange("/split/groups",
                HttpMethod.GET, entity, GetAllGroupResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody()
                .getResult()
                .getTitle()).isEqualTo(ResultStatus.SUCCESS);

        assertThat(response.getBody()
                .getGroups()).hasSize(1);
        assertThat(response.getBody()
                .getGroups()
                .getFirst()
                .getName()).isEqualTo("group-name");
        assertThat(response.getBody()
                .getGroups()
                .getFirst()
                .getMembers()).contains(1L, 2L);
        assertThat(response.getBody()
                .getGroups()
                .getFirst()
                .getIcon()).isEqualTo("icon");
        assertThat(response.getBody()
                .getGroups()
                .getFirst()
                .getGroupId()).isEqualTo("group-id");
        assertThat(response.getBody()
                .getGroups()
                .getFirst()
                .getCreatedBy()).isEqualTo(1L);
        assertThat(response.getBody()
                .getGroups()
                .getFirst()
                .getDescription()).isEqualTo("group desc");
    }


    @Test
    void getByGroupId() {
        createGroup();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<GroupResponse> response = restTemplate.exchange("/split/groups/group-id",
                HttpMethod.GET, entity, GroupResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody()
                .getResult()
                .getTitle()).isEqualTo(ResultStatus.SUCCESS);

        assertThat(response.getBody()
                .getGroup()
                .getName()).isEqualTo("group-name");
        assertThat(response.getBody()
                .getGroup()
                .getMembers()).contains(1L, 2L);
        assertThat(response.getBody()
                .getGroup()
                .getIcon()).isEqualTo("icon");
        assertThat(response.getBody()
                .getGroup()
                .getGroupId()).isEqualTo("group-id");
        assertThat(response.getBody()
                .getGroup()
                .getCreatedBy()).isEqualTo(1L);
        assertThat(response.getBody()
                .getGroup()
                .getDescription()).isEqualTo("group desc");
    }

    @Test
    void addMembers() {
        createGroup();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");

        AddGroupMemberRequest request = new AddGroupMemberRequest();
        request.setMembers(List.of(3L));

        HttpEntity<Object> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GroupResponse> response = restTemplate.exchange("/split/groups/group-id/members",
                HttpMethod.POST, entity, GroupResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody()
                .getResult()
                .getTitle()).isEqualTo(ResultStatus.SUCCESS);

        assertThat(response.getBody()
                .getGroup()
                .getName()).isEqualTo("group-name");
        assertThat(response.getBody()
                .getGroup()
                .getMembers()).contains(1L, 2L, 3L);
        assertThat(response.getBody()
                .getGroup()
                .getIcon()).isEqualTo("icon");
        assertThat(response.getBody()
                .getGroup()
                .getGroupId()).isEqualTo("group-id");
        assertThat(response.getBody()
                .getGroup()
                .getCreatedBy()).isEqualTo(1L);
        assertThat(response.getBody()
                .getGroup()
                .getDescription()).isEqualTo("group desc");
    }

    @Test
    void deleteGroup() {
        createGroup();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<GeneralResponse> response = restTemplate.exchange("/split/groups/group-id",
                HttpMethod.DELETE, entity, GeneralResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody()
                .getResult()
                .getTitle()).isEqualTo(ResultStatus.SUCCESS);

        List<Group> groups = groupDao.findAll();
        assertThat(groups).isEmpty();
    }

    @Test
    void edit() {
        createGroup();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");

        EditGroupRequest request = new EditGroupRequest();
        request.setDescription("new desc");
        request.setName("new name");

        HttpEntity<Object> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GroupResponse> response = restTemplate.exchange("/split/groups/group-id",
                HttpMethod.PUT, entity, GroupResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getBody()
                .getResult()
                .getTitle()).isEqualTo(ResultStatus.SUCCESS);

        assertThat(response.getBody().getGroup()
                .getName()).isEqualTo("new name");
        assertThat(response.getBody().getGroup()
                .getDescription()).isEqualTo("new desc");

    }


    private void createGroup() {
        List<User> users = userDao.findAllByIdIn(List.of(1L, 2L));

        Group group = new Group(null, "group-id", "group-name", users.getFirst(), users,
                null, "icon", "group desc", null,
                null, null);

        groupDao.save(group);
    }
}