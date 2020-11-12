package com.patrick.dorm.service;

import com.patrick.dorm.entity.Permission;
import com.patrick.dorm.entity.Role;
import com.patrick.dorm.entity.User;
import com.patrick.dorm.mapper.PermissionMapper;
import com.patrick.dorm.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    UserMapper userMapper;
    PermissionMapper permissionMapper;

    @Autowired
    public PermissionService(UserMapper userMapper,PermissionMapper permissionMapper){
        this.userMapper = userMapper;
        this.permissionMapper = permissionMapper;
    }

    public List<Permission> listAllPermissions(String username){
        // 用户名查角色
        User user = userMapper.getUserWithRole(username);
        List<Integer> rids = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        return permissionMapper.getByRoleIds(rids);
    }

    public boolean needFilter(String requestAPI){
        List<Permission> permissions = permissionMapper.findAll();
        for (Permission p:permissions){
            if(requestAPI.startsWith(p.getUrl())){
                return true;
            }
        }
        return false;
    }
}
