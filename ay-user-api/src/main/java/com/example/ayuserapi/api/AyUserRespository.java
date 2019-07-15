package com.example.ayuserapi.api;

import com.example.ayuserapi.model.AyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AyUserRespository extends JpaRepository<AyUser, String> {
    /**
     * 描述：通过名字进行查询，参数为name
     * 相当于：select * from ay_user u where u.name = ?1
     */
    List<AyUser> findByName(String name);

    /**
     * 描述：通过名字like进行查询，参数为name
     * 相当于：select * from ay_user u where u.name like ?1
     */
    List<AyUser> findByNameLike(String name);

    /**
     * 描述：通过主键集合id查询，参数为id集合
     * 相当于：select * from ay_user u where u.name in (?,?,?……,?)
     */
    List<AyUser> findByIdIn(List<String> ids);
}
