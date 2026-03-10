package com.sky.service.impl;

import java.beans.beancontext.BeanContext;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class DishServiceImpl implements DishService{


    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO){

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);


        dishMapper.insert(dish);

        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if( flavors != null && flavors.size() > 0 ){
            for( DishFlavor dishFlavor : flavors){
                dishFlavor.setDishId(dishId);
            }
            dishFlavorMapper.insertBatch(flavors);

        }

    }
    public PageResult pageQuey(DishPageQueryDTO dishPageQueryDTO){
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        
        for( Long id : ids){
            Dish dish = dishMapper.get_by_id(id);
            if( dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
            

        }
        List<Long> setmealDishIds = setmealDishMapper.getSetmealIdByDishid(ids);
        if( setmealDishIds != null && setmealDishIds.size() > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);

        }
        for( Long id : ids){
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteById(id);

        }
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByIds(ids);

    }
    @Override
    public DishVO get_by_idWithFlavor(Long id) {

        Dish dish = dishMapper.get_by_id(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.get_by_dishId(id);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);

        dishVO.setFlavors(dishFlavors);



        return dishVO;
        
    }
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        dishFlavorMapper.deleteById(dishDTO.getId());

        List<DishFlavor> flavors = dishDTO.getFlavors();

        if( flavors != null && flavors.size() > 0 ){
            for( DishFlavor dishFlavor : flavors){
                dishFlavor.setDishId(dishDTO.getId());
            }
            dishFlavorMapper.insertBatch(flavors);

        }
    }
    @Override
    public List<Dish> get_by_fenlei_id(Long categoryId) {
        Dish dish = Dish.builder()
                    .categoryId(categoryId)
                    .status(StatusConstant.ENABLE)
                    .build();

        
        
        return dishMapper.get_by_fenlei_id(dish);
    }
    @Override
    public void setDishStatus(Dish dish) {
        // TODO Auto-generated method stub
        dishMapper.update(dish);
    }
        public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.get_by_fenlei_id(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.get_by_dishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }
    
}
