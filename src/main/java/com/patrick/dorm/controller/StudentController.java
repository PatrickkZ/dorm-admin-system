package com.patrick.dorm.controller;

import com.patrick.dorm.result.Result;
import com.patrick.dorm.result.ResultFactory;
import com.patrick.dorm.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin")
public class StudentController {
    private final static Logger logger = LoggerFactory.getLogger(StudentController.class);
    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @CrossOrigin
    @GetMapping(value = "/student")
    public Result getAllStudent(){
        logger.info(studentService.listAllStudent().toString());
        return ResultFactory.buildSuccessResult(studentService.listAllStudent());
    }
}
