package com.patrick.dorm.controller;

import com.patrick.dorm.result.Result;
import com.patrick.dorm.result.ResultFactory;
import com.patrick.dorm.service.DormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin")
public class DormController {
    private final static Logger logger = LoggerFactory.getLogger(DormController.class);
    DormService dormService;

    @Autowired
    public DormController(DormService dormService){
        this.dormService = dormService;
    }

    @GetMapping(value = "/dorm")
    public Result getAllDorms(){
        return ResultFactory.buildSuccessResult(dormService.listAllDorms());
    }

    @PostMapping(value = "/assign")
    public Result assignDorm(@RequestBody Map<String,String> map){
        if(dormService.assignDorm(map.get("studentNum"),map.get("roomNum"))){
            return ResultFactory.buildSuccessResult(null);
        }else {
            return ResultFactory.buildFailResult("宿舍已满或性别不匹配,请确认后再进行分配");
        }
    }
}
