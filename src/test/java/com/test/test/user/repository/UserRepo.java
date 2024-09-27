package com.test.test.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.test.user.model.User;

public interface UserRepo extends JpaRepository <User, Long> {    
    User findByUserName(String userName);
}
