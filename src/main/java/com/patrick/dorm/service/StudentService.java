package com.patrick.dorm.service;

import com.patrick.dorm.entity.Student;
import com.patrick.dorm.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentMapper studentMapper){
        this.studentMapper = studentMapper;
    }

    public List<Student> listAllStudent(){
        return studentMapper.listAll();
    }
}
