package com.chinatop.contains.service.impl;

import com.chinatop.contains.model.AyUserRoleRel;
import com.chinatop.contains.repository.AyUserRoleRelRepository;
import com.chinatop.contains.service.AyUserRoleRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AyUserRoleRelServiceImpl implements AyUserRoleRelService {
    @Resource
    private AyUserRoleRelRepository ayUserRoleRelRepository;

    @Override
    public List<AyUserRoleRel> findByUserId(String userId) {
        return ayUserRoleRelRepository.findByUserId(userId);
    }
}
