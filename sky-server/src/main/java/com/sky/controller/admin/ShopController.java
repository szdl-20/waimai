package com.sky.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.result.Result;
import com.sky.service.ShopServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController("adminController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺营业状态")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    public static final String KEY = "SHOP STATUS";




    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")

    public Result<Integer> getStatus(){
        
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer status = (Integer)valueOperations.get(KEY);
        log.info("当前店铺营业状态为{}",status==1? "营业中":"关门状态" );
        
        

        return Result.success(status);

    }
    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺营业状态为{}",status==1? "营业中":"关门状态" );
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(KEY, status);
        


        return Result.success();

    }


    
}
