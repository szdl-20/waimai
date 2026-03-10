package com.sky.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;


@Service
public class SetmealServiceImpl implements SetmealService{
    @Autowired

    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        //首先修改setmeal表

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setmealMapper.update(setmeal);
        Long setmealId = setmealDTO.getId();
        

        setmealDishMapper.deleteBatch(Arrays.asList(setmealId));

        //在修改setmealdish表
        List<SetmealDish> setmealDishs = setmealDTO.getSetmealDishes();
        for( SetmealDish setmealDish : setmealDishs){
            setmealDish.setSetmealId(setmealId);
        }
        setmealDishMapper.insertBatch(setmealDishs);





        
    }
    @Transactional
    public void insert( SetmealDTO setmealDTO){
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);
        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishs = setmealDTO.getSetmealDishes();
        for( SetmealDish setmealDish : setmealDishs){
            setmealDish.setSetmealId(setmealId);
        }
        setmealDishMapper.insertBatch(setmealDishs);
    }
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        // TODO Auto-generated method stub
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    
    @Transactional
    @Override
    public void delete(List<Long> ids) {
              for( Long id : ids){
            if( setmealMapper.getById(id).getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }

        }

        setmealMapper.delete(ids);
                        
  
        //删除套餐表中的数据

        setmealDishMapper.deleteBatch(ids);
     
        //删除套餐菜品关系表中的数据
    
    
}
    @Override
    public Setmeal getById(Long setmealId) {
        return setmealMapper.getById(setmealId);
    }
    @Override
    public SetmealVO getByIdWithDish(Long id) {

        Setmeal setmeal = setmealMapper.getById(id);

        List<SetmealDish> setmealDishs = setmealDishMapper.getsetmealDishById(id);

        SetmealVO setmealVO = new SetmealVO();
        
        BeanUtils.copyProperties(setmeal, setmealVO);

        setmealVO.setSetmealDishes(setmealDishs);
        return setmealVO;


        
    }
    @Override
    @Transactional
    public void startOrStop(Integer status, Long id) {
        //起售套餐时，判断套餐内是否有停售菜品，有停售菜品提示"套餐内包含未启售菜品，无法启售"
        if ( status == StatusConstant.ENABLE){
            List<Dish> dishs = dishMapper.getDishBySetmealId(id);
            if( dishs != null && dishs.size() > 0){
                                    for( Dish dish : dishs){
            if( dish.getStatus() == StatusConstant.DISABLE){
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        }

            }

                //             List<SetmealDish> setmealDishs = setmealDishMapper.getsetmealDishById(id);

        // for( SetmealDish setmealDish : setmealDishs){
        //     Long dishId = setmealDish.getDishId();
        //     Dish dish = dishMapper.get_by_id(dishId);

        //     if( dish.getStatus() == StatusConstant.DISABLE){
        //         throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
        //     }
        // 
        }



        

        Setmeal setmeal = setmealMapper.getById(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);
    }

        /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
    
}