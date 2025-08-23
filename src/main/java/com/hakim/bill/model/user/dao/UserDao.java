package com.hakim.bill.model.user.dao;

import java.util.List;

import com.hakim.bill.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	boolean existsByUsername(String username);

	User findByUsername(String userName);

	List<User> findAllByIdIn(List<Long> Ids);
}
