package com.patrick.dorm.mapper;

import com.patrick.dorm.entity.Dorm;
import com.patrick.dorm.vo.DormInfoVo;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface DormMapper {
    Dorm getById(Integer id);
    Dorm getByRoomNum(String roomNum);
    void increaseRemain(String roomNum);
    void decreaseRemain(String roomNum);
    List<Dorm> listAll();
    List<DormInfoVo> getStatistics();
    void updateDorm(Dorm dorm);
    void insertDorm(Dorm dorm);
}
