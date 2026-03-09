package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.DishFlavor;

@Mapper
public interface DishFlavorMapper {

    void insertBatch(List<DishFlavor> flavors);

    @Delete(" delete from dish_flavor where dish_id = #{id}")

    void deleteById(Long id);

    void deleteByIds(List<Long> ids);

    DishFlavor get_by_id(Long id);
    @Select("select  * from dish_flavor where dish_id = #{id}")

    List<DishFlavor> get_by_dishId(Long id);
    
}
