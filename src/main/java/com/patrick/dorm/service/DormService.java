package com.patrick.dorm.service;

import com.patrick.dorm.entity.Dorm;
import com.patrick.dorm.entity.Student;
import com.patrick.dorm.mapper.DormMapper;
import com.patrick.dorm.mapper.StudentMapper;
import com.patrick.dorm.vo.DormInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
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

    public boolean checkoutDorm(String studentNum){
        Student student = studentMapper.getByStuNum(studentNum);
        Dorm curDorm = student.getDorm();
        if(curDorm==null){
            return false;
        }else {
            String roomNum = curDorm.getRoomNum();
            dormMapper.increaseRemain(roomNum);
            studentMapper.checkoutByStudentNum(studentNum);
            return true;
        }
    }

    public int switchDorm(String studentNum,String toRoomNum){
        //先退宿再分配
        if(checkoutDorm(studentNum)){
            //退宿成功
            if(assignDorm(studentNum,toRoomNum)){
                return 2;
            }else {
                //分配宿舍失败，说明房间满了或类型不匹配
                return 1;
            }
        }else {
            //退宿失败，说明已经退宿
            return 0;
        }
    }

    public List<DormInfoVo> getDormStatistic(){
        return dormMapper.getStatistics();
    }

    public void updateDormInfo(Dorm dorm){
        dormMapper.updateDorm(dorm);
    }

    public void addDorm(Dorm dorm){
        dormMapper.insertDorm(dorm);
    }
}
