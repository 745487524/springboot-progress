package com.chinatop.contains.service;

import com.chinatop.contains.model.AyUserRoleRel;

import java.util.List;

public interface AyUserRoleRelService {

    List<AyUserRoleRel> findByUserId(String userId);
}
