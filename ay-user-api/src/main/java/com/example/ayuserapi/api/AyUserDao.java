package com.example.ayuserapi.api;

import com.example.ayuserapi.model.AyUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AyUserDao {

    AyUser findByNameAndPassword(String name, String password);

    AyUser findByUserName(String name);
}
