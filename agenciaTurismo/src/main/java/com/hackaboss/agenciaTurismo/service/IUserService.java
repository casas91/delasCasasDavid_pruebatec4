package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.model.User;

public interface IUserService {

    User createUser(User user);
    User findByPassport(String passport);
}