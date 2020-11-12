package com.patrick.dorm.controller;

import com.patrick.dorm.entity.Menu;
import com.patrick.dorm.entity.Permission;
import com.patrick.dorm.entity.User;
import com.patrick.dorm.result.Result;
import com.patrick.dorm.result.ResultFactory;
import com.patrick.dorm.service.PermissionService;
import com.patrick.dorm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api")
public class LoginController {
    UserService userService;
    PermissionService permissionService;

    @Autowired
    public LoginController(UserService userService,PermissionService permissionService){
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @CrossOrigin
    @PostMapping(value = "/login")
    public Result login(@RequestBody User user){
        String username = user.getUsername();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, user.getPassword());
        try {
            subject.login(usernamePasswordToken);
            return ResultFactory.buildSuccessResult(username);
        } catch (AuthenticationException e) {
            String message = "账号或密码错误";
            return ResultFactory.buildFailResult(message);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/register")
    public Result register(@RequestBody User user){
        if(userService.addUser(user)){
            return ResultFactory.buildSuccessResult("注册成功");
        }
        return ResultFactory.buildFailResult("用户名已存在");
    }

    @CrossOrigin
    @GetMapping("/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping("/menu")
    public Result menu(){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        List<Menu> menus = userService.listAllMenus(username);
        return ResultFactory.buildSuccessResult(menus);
    }
}
