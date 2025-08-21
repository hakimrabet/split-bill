package com.bill.user.service.user;

import com.bill.user.exception.UserAlreadyExistsException;
import com.bill.user.exception.UserNotFoundException;
import com.bill.user.service.user.model.AddUserModel;
import com.bill.user.service.user.model.LoginModel;
import com.bill.user.service.user.model.UserResult;
import com.bill.user.service.user.model.UsersResult;

public interface UserService {

	UserResult registerUser(AddUserModel model) throws UserAlreadyExistsException;

	UserResult getUserById(Long userId) throws UserNotFoundException;

	UsersResult getAllUsers();

	String login(LoginModel model);

}
