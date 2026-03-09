package com.sky.controller.user;

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

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺营业状态")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;




    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")

    public Result<Integer> getStatus(){
        
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer status = (Integer)valueOperations.get("SHOP STATUS");
        log.info("当前店铺营业状态为{}",status==1? "营业中":"关门状态" );
        
        

        return Result.success(status);

    }



    
}
