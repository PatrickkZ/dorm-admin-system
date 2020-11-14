package com.patrick.dorm.controller;

import com.patrick.dorm.entity.Student;
import com.patrick.dorm.entity.User;
import com.patrick.dorm.result.Result;
import com.patrick.dorm.result.ResultFactory;
import com.patrick.dorm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public Result getAllUsers(){
        return ResultFactory.buildSuccessResult(userService.listAllUsers());
    }

    @GetMapping("/role")
    public Result getAllRole(){
        return ResultFactory.buildSuccessResult(userService.getAllRoleInDb());
    }

    @PutMapping(value = "/user/role")
    public Result modifyRole(@RequestBody User user){
        userService.modifyUserRole(user);
        return ResultFactory.buildSuccessResult(null);
    }

    //管理员重置密码
    @PutMapping(value = "/user/password")
    public Result resetPassword(@RequestBody User user){
        userService.resetPassword(user);
        return ResultFactory.buildSuccessResult(null);
    }

    @PutMapping(value = "/user/modifyPassword")
    public Result modifyPassword(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String curUser = SecurityUtils.getSubject().getPrincipal().toString();
        if(username.equals(curUser)){
            if(userService.modifyPassword(map)){
                return ResultFactory.buildSuccessResult(null);
            }else {
                return ResultFactory.buildFailResult("原始密码错误");
            }
        }else {
            return ResultFactory.buildFailResult("当前用户信息不匹配");
        }
    }
}
