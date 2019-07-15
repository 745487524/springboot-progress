package com.example.ayuserapi.api;

import com.example.ayuserapi.model.AyUser;

public interface AyUserService {
    AyUser findByNameAndPassword(String name, String password);
}
