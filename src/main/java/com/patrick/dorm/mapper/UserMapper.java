package com.patrick.dorm.mapper;

import com.patrick.dorm.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User getByUsername(String username);
    User getUserWithRole(String username);
    List<User> getAllUserWithRole();
    /**
     *不存在则保存，存在则更新
     */
    void save(User user);
}
