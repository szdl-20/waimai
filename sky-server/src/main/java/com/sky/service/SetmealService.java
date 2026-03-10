package com.sky.service;

import java.util.List;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

public interface SetmealService {

    void update(SetmealDTO setmealDTO);

    void insert(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void delete(List<Long> ids);

    Setmeal getById(Long setmealId);

    SetmealVO getByIdWithDish(Long id);

    void startOrStop(Integer status, Long id);
    List<Setmeal> list(Setmeal setmeal);
    List<DishItemVO> getDishItemById(Long id);
    
}
