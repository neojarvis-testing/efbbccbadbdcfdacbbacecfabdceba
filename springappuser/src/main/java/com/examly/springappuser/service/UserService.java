package com.examly.springappuser.service;

import com.examly.springappuser.model.LoginDTO;
import com.examly.springappuser.model.User;

public interface UserService {
    String loginUser(LoginDTO loginDTO);
    User registerUser(User user);
}
