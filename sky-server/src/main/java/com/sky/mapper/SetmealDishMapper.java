package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.SetmealDish;

@Mapper
public interface SetmealDishMapper {
    
    List<Long> getSetmealIdByDishid(List<Long> dishIds);

    

    void insertBatch(List<SetmealDish> setmealDishs);



    void deleteBatch(List<Long> ids);


@Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getsetmealDishById(Long id);
    
}
