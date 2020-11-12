package com.patrick.dorm.service;

import com.patrick.dorm.entity.Dorm;
import com.patrick.dorm.entity.Student;
import com.patrick.dorm.mapper.DormMapper;
import com.patrick.dorm.mapper.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormService {
    private final static Logger logger = LoggerFactory.getLogger(DormService.class);
    DormMapper dormMapper;
    StudentMapper studentMapper;

    @Autowired
    public DormService(DormMapper dormMapper,StudentMapper studentMapper){
        this.dormMapper = dormMapper;
        this.studentMapper = studentMapper;
    }

    public List<Dorm> listAllDorms(){
        return dormMapper.listAll();
    }

    public boolean assignDorm(String studentNum,String roomNum){
        Dorm room = dormMapper.getByRoomNum(roomNum);
        Student student = studentMapper.getByStuNum(studentNum);
        if(room.getRemain()>0 && student.getGender().equals(room.getType())){
            dormMapper.decreaseRemain(roomNum);
            studentMapper.updateDorm(studentNum,roomNum);
            logger.info("学号"+studentNum+"分配到宿舍"+roomNum);
            return true;
        }else {
            return false;
        }
    }
}
