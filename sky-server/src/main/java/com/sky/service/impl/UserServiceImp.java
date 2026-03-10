package com.sky.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class UserServiceImp implements UserService{



    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String authorization_code = "authorization_code";
    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User userLogin(UserLoginDTO userLoginDTO) {

        Map<String,String> HashMap = new HashMap<>();

        HashMap.put("appid",weChatProperties.getAppid());
        HashMap.put("secret",weChatProperties.getSecret());
        HashMap.put("js_code",userLoginDTO.getCode());
        HashMap.put("grant_type",authorization_code);


        // TODO Auto-generated method stub

        

        String result = HttpClientUtil.doGet(WX_LOGIN, HashMap);
        JSONObject jsonObject = JSON.parseObject(result);

        String openId = jsonObject.getString("openid");
        

        if( openId == null){
            throw new LoginFailedException(jsonObject.getString(MessageConstant.LOGIN_FAILED));
        }

        User user = userMapper.select(openId);

        if( user == null){
            user = User.builder()
                    .openid(openId)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);

        }
 


        return user;

        
    }
    
}
