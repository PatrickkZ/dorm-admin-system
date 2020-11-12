package com.patrick.dorm.mapper;

import com.patrick.dorm.entity.Menu;
import com.patrick.dorm.entity.Student;
import com.patrick.dorm.entity.User;
import com.patrick.dorm.service.StudentService;
import com.patrick.dorm.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTester {
    @Autowired
    StudentService studentService;
    @Test
    public void test(){
        studentService.listAllStudent().stream().forEach(student -> System.out.println(student.getStudentNum()));
    }
}

