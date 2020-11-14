package com.patrick.dorm.service;

import com.patrick.dorm.entity.Student;
import com.patrick.dorm.mapper.StudentMapper;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
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

    public void updateStudentInfo(Student student){
        studentMapper.updateStudentInfo(student);
    }

    public void updatePassword(Student student){
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 hash 算法迭代次数
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", student.getPassword(), salt, times).toString();
        // 存储用户信息，包括 salt 与 hash 后的密码
        student.setSalt(salt);
        student.setPassword(encodedPassword);
        studentMapper.updatePassword(student);
    }

    public void  insertStudent(Student student){
        studentMapper.insertStudent(student);
    }
}
