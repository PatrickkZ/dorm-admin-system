package com.patrick.dorm.controller;

import com.patrick.dorm.entity.Dorm;
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

    @PostMapping(value = "/checkout")
    public Result checkDorm(@RequestBody Map<String,String> map){
        String studentNum = map.get("studentNum");
        if(dormService.checkoutDorm(studentNum)){
            return ResultFactory.buildSuccessResult(null);
        }else {
            return ResultFactory.buildFailResult("该学生已退宿");
        }
    }

    @PostMapping(value = "/switch")
    public Result switchDorm(@RequestBody Map<String,String> map){
        String studentNum = map.get("studentNum");
        String toRoomNum = map.get("toRoomNum");
        int state = dormService.switchDorm(studentNum,toRoomNum);
        switch (state){
            case 0:
                return ResultFactory.buildFailResult("该学生还未分配宿舍");
            case 1:
                return ResultFactory.buildFailResult("宿舍已满或类型不匹配");
            case 2:
                return ResultFactory.buildSuccessResult(null);
            default:
                return ResultFactory.buildFailResult("unknown exception");
        }
    }

    @GetMapping(value = "/statistic")
    public Result getDormStatistic(){
        return ResultFactory.buildSuccessResult(dormService.getDormStatistic());
    }

    @PutMapping(value = "/dorm/update")
    public Result updateDormInfo(@RequestBody Dorm dorm){
        dormService.updateDormInfo(dorm);
        return ResultFactory.buildSuccessResult(null);
    }

    @PostMapping(value = "/dorm/add")
    public Result addDorm(@RequestBody Dorm dorm){
        dormService.addDorm(dorm);
        return ResultFactory.buildSuccessResult(null);
    }
}
