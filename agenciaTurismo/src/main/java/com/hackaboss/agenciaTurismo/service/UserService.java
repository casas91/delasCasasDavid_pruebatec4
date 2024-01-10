package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.model.User;
import com.hackaboss.agenciaTurismo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRep;
    @Override
    public User createUser(User user) {

        return userRep.save(user);
    }

    @Override
    public User findByPassport(String passport) {
        return userRep.findByPassport(passport);
    }


}
