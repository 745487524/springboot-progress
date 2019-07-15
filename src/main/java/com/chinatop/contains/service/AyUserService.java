package com.chinatop.contains.service;

import com.chinatop.contains.model.AyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.Future;

public interface AyUserService {
    AyUser findById(String id);

    List<AyUser> findAll();

    AyUser save(AyUser ayUser);

    void delete(String id);

    //分页
    Page<AyUser> findAll(Pageable pageable);

    List<AyUser> findByName(String name);

    AyUser findByUserName(String name);

    List<AyUser> findByNameLike(String name);

    List<AyUser> fingByIdIn(List<String> ids);

    AyUser findByNameAndPassword(String name, String password);

    Future<List<AyUser>> findAsyncAll();

    AyUser findByNameAndPasswordRetry(String name, String password);
}
