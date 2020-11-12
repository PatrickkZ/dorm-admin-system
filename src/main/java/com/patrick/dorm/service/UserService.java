package com.patrick.dorm.service;

import com.patrick.dorm.entity.Menu;
import com.patrick.dorm.entity.Permission;
import com.patrick.dorm.entity.Role;
import com.patrick.dorm.entity.User;
import com.patrick.dorm.mapper.MenuMapper;
import com.patrick.dorm.mapper.PermissionMapper;
import com.patrick.dorm.mapper.UserMapper;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserMapper userMapper;
    PermissionMapper permissionMapper;
    MenuMapper menuMapper;

    @Autowired
    public UserService(UserMapper userMapper,PermissionMapper permissionMapper,MenuMapper menuMapper){
        this.userMapper = userMapper;
        this.permissionMapper = permissionMapper;
        this.menuMapper = menuMapper;
    }

    public User findByName(String username){
        return userMapper.getByUsername(username);
    }

    public boolean addUser(User user){
        // html转义
        String username = HtmlUtils.htmlEscape(user.getUsername());
        String password = user.getPassword();
        user.setUsername(username);
        User userInDb = userMapper.getByUsername(username);
        //用户名存在
        if(userInDb !=null){
            return false;
        }else {
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            // 设置 hash 算法迭代次数
            int times = 2;
            // 得到 hash 后的密码
            String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
            // 存储用户信息，包括 salt 与 hash 后的密码
            user.setSalt(salt);
            user.setPassword(encodedPassword);
            userMapper.save(user);
            return true;
        }
    }

    public List<Menu> listAllMenus(String username){
        User user = userMapper.getUserWithRole(username);
        List<Integer> rids = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        return menuMapper.getByRoleIds(rids);
    }

    public List<User> listAllUsers(){
        return userMapper.getAllUserWithRole();
    }
}
