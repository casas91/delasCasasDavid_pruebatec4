package com.hackaboss.agenciaTurismo.repository;

import com.hackaboss.agenciaTurismo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByPassport(String passport);
}
