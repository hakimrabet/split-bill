package com.bill.user.model.dong.dao;

import java.util.List;
import java.util.Optional;

import com.bill.user.model.dong.Group;
import com.bill.user.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {

	Group findByGroupId(String groupId);

	@Query("SELECT g FROM Group g JOIN g.members m WHERE m.id = :id")
	List<Group> findAllByMembers_Id(Long id);

	Optional<Group> findByGroupIdAndMembersContains(String groupId, User member);

	Optional<Group> findByGroupIdAndCreatedBy(String groupId, User user);

}
