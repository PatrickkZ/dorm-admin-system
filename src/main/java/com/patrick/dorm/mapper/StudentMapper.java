package com.patrick.dorm.mapper;

import com.patrick.dorm.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {
    List<Student> listAll();
    Student getByStuNum(String studentNum);
    void updateDorm(@Param("studentNum")String studentNum,@Param("roomNum")String roomNum);
}
