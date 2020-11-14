package com.patrick.dorm.mapper;

import com.patrick.dorm.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    Role getById(Integer id);
    Role getRoleWithPermission(Integer id);
    List<Role> listAll();
    List<Role> getByUserId(Integer userId);
    void insertDefaultRole(Integer uid);
}
