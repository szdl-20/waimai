package com.sky.service;

import java.util.List;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

public interface DishServices {
    public void saveWithFlavor(DishDTO dishDTO);

    public PageResult pageQuey(DishPageQueryDTO dishPageQueryDTO);

    

    public void deleteBatch(List<Long> ids);

    public DishVO get_by_idWithFlavor(Long id);

    public void updateWithFlavor(DishDTO dishDTO);

    public List<Dish> get_by_fenlei_id(Long categoryId);

    public void setDishStatus(Dish dish);
}
