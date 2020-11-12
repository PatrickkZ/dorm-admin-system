package com.patrick.dorm.mapper;

import com.patrick.dorm.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {
    List<Menu> getByRoleIds(List<Integer> roleIds);
}
