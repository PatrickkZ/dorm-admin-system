package com.patrick.dorm.mapper;

import com.patrick.dorm.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User getByUsername(String username);
    User getUserWithRole(String username);
    List<User> getAllUserWithRole();
    void save(User user);
    void deleteCurUserRole(Integer userId);
    void insertCurUserRole(@Param("userId")Integer userId,@Param("roleId")Integer roleId);
    void updatePassword(User user);
}
