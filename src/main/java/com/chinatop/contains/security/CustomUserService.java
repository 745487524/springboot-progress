package com.chinatop.contains.security;

import com.chinatop.contains.error.BusinessException;
import com.chinatop.contains.model.AyUser;
import com.chinatop.contains.model.AyUserRoleRel;
import com.chinatop.contains.service.AyRoleService;
import com.chinatop.contains.service.AyUserRoleRelService;
import com.chinatop.contains.service.AyUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：自定义用户服务类
 *
 * @author Ay
 * @date 2017/12/10.
 */
@Service
public class CustomUserService implements UserDetailsService {

    private static final String ACCESS_PREFIX = "ROLE_";
    @Resource
    private AyUserService ayUserService;
    @Resource
    private AyUserRoleRelService ayUserRoleRelService;
    @Resource
    private AyRoleService ayRoleService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        AyUser ayUser = ayUserService.findByUserName(name);
        if (ayUser == null) {
            throw new BusinessException("用户不存在");
        }
        System.out.println("检测到用户存在……");
        //获取用户所有的关联角色
        List<AyUserRoleRel> ayRoleList = ayUserRoleRelService.findByUserId(ayUser.getId());
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        if (ayRoleList != null && ayRoleList.size() > 0) {
            for (AyUserRoleRel rel : ayRoleList) {
                //获取用户关联角色名称
                String roleName = ayRoleService.find(rel.getRoleId()).getName();
                authorityList.add(new SimpleGrantedAuthority(ACCESS_PREFIX + roleName));
            }
        }
        User user = new User(ayUser.getName(), new BCryptPasswordEncoder().encode(ayUser.getPassword()), authorityList);
        return user;
    }
}


