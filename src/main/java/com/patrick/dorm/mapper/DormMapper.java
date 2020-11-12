package com.patrick.dorm.mapper;

import com.patrick.dorm.entity.Dorm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DormMapper {
    Dorm getById(Integer id);
    Dorm getByRoomNum(String roomNum);
    void increaseRemain(String roomNum);
    void decreaseRemain(String roomNum);
    List<Dorm> listAll();
}
