package com.example.ayuserservice.apiImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.ayuserapi.api.AyUserService;
import com.example.ayuserapi.model.AyUser;

@Service(version = "1.0")
public class AyUserDubboServiceImpl implements AyUserService {

    @Override
    public AyUser findByNameAndPassword(String name, String password) {
        AyUser ayUser = new AyUser();
        ayUser.setId("1");
        ayUser.setName("阿毅");
        ayUser.setPassword("123456");
        return ayUser;
    }
}
