package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;

// @Mapper
// public interface DishMapper {

//     /**
//      * 根据分类id查询菜品数量
//      * @param categoryId
//      * @return
//      */
//     @Select("select count(id) from dish where category_id = #{categoryId}")
//     Integer countByCategoryId(Long categoryId);

// }
@Mapper
public interface DishMapper {

    @Select("select count(id) from dish where category_id=#{categoryId}") 
    Integer countByCategoryId(Long categoryId);

    @AutoFill( value = OperationType.INSERT)

    Boolean insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);


    @Select("select * from dish where dish.id = #{ id}")

    Dish get_by_id(Long id);

    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    void deleteByIds(List<Long> ids);
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    
    List<Dish> get_by_fenlei_id(Dish dish);
    @Select(" select d.* from setmeal_dish as s left join  dish as d on s.dish_id = d.id where s.setmeal_id = #{id}")
    List<Dish> getDishBySetmealId(Long id);


}