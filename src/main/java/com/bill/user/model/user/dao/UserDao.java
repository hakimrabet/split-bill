package com.bill.user.model.user.dao;


import com.bill.user.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);

	User findByUsername(String userName);
}
