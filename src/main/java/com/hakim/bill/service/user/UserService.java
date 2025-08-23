package com.hakim.bill.service.user;

import com.hakim.bill.exception.UserAlreadyExistsException;
import com.hakim.bill.exception.UserNotFoundException;
import com.hakim.bill.service.user.model.AddUserModel;
import com.hakim.bill.service.user.model.LoginModel;
import com.hakim.bill.service.user.model.UserResult;
import com.hakim.bill.service.user.model.UsersResult;

public interface UserService {

	UserResult registerUser(AddUserModel model) throws UserAlreadyExistsException;

	UserResult getUserById(Long userId) throws UserNotFoundException;

	UsersResult getAllUsers();

	String login(LoginModel model);

}
