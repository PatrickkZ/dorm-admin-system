package com.patrick.dorm.controller;

import com.patrick.dorm.entity.Student;
import com.patrick.dorm.result.Result;
import com.patrick.dorm.result.ResultFactory;
import com.patrick.dorm.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return ResultFactory.buildSuccessResult(studentService.listAllStudent());
    }

    @PutMapping(value = "/student")
    public Result updateStudent(@RequestBody Student student){
        logger.info(student.getStudentNum());
        logger.info(student.getMajor());
        studentService.updateStudentInfo(student);
        return ResultFactory.buildSuccessResult(null);
    }

    @PutMapping(value = "/student/password")
    public Result updatePassword(@RequestBody Student student){
        logger.info(student.getStudentNum());
        logger.info(student.getPassword());
        studentService.updatePassword(student);
        return ResultFactory.buildSuccessResult(null);
    }

    @PostMapping(value = "/student/add")
    public Result addStudent(@RequestBody Student student){
        studentService.insertStudent(student);
        return ResultFactory.buildSuccessResult(null);
    }
}
