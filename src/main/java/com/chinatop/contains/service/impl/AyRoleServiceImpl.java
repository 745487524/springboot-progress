package com.chinatop.contains.service.impl;

import com.chinatop.contains.model.AyRole;
import com.chinatop.contains.repository.AyRoleRepository;
import com.chinatop.contains.service.AyRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AyRoleServiceImpl implements AyRoleService {
    @Resource
    private AyRoleRepository ayRoleRepository;

    @Override
    public AyRole find(String id) {
        return ayRoleRepository.findById(id).get();
    }
}
