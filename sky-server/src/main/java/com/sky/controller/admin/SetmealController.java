package com.sky.controller.admin;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j

public class SetmealController {
    @Autowired

    private SetmealService setmealService;


    @PutMapping
    @ApiOperation("修改套餐")
    public Result update( @RequestBody SetmealDTO setmealDTO){
        log.info("当前修改套餐{}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();            
    }

    @PostMapping
    @ApiOperation("新增套餐")
    public Result insert(@RequestBody SetmealDTO setmealDTO){
        log.info("当前新增套餐dto为{}",setmealDTO);

        setmealService.insert(setmealDTO);

        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> get_by_page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("当前进行套餐分页查询{}",setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);



        return Result.success(pageResult)  ; 
    }
    @DeleteMapping
    @ApiOperation("删除套餐")
    public Result deleteSetMeal( @RequestParam List<Long> ids){
        log.info("当前删除套餐为{}",ids);

        setmealService.delete( ids);



        return Result.success();
    }
    // @GetMapping
    // @ApiOperation("根据套餐id查村套餐")
    // public Setmeal getById(@PathVariable Long setmealId){

    //     Setmeal setmeal = setmealService.getById(setmealId);
    //     return setmeal;

    // }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id){
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);

        return Result.success(setmealVO);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("套餐的起售和停售")
    public Result startOrStop(@PathVariable Integer status,@RequestParam Long id){
        setmealService.startOrStop(status,id);



        return Result.success();
    }
 




}
