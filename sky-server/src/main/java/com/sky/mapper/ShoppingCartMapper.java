package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;


@Mapper
public interface ShoppingCartMapper {

    List<ShoppingCart> select(ShoppingCart shoppingCart);
    @Update("update shopping_cart as s set s.number = #{number} where s.id = #{id}")
    void update(ShoppingCart shoppingCart2);
    void insert(ShoppingCart shoppingCart);
    

    
}
