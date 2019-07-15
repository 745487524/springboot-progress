package com.chinatop.contains.actuator;


import com.chinatop.contains.service.AyUserService;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Endpoint(id = "ay-user-endpoints")
public class AyUserEndPoints {

    @Resource
    private AyUserService ayUserService;

    @ReadOperation
    public Map<String, Object> invoke() {
        Map<String, Object> map = new HashMap<>();
        map.put("current_time", new Date());
        map.put("user_num", ayUserService.findAll().size());
        return map;
    }

}
