package com.chinatop.contains.controller;

import com.chinatop.contains.error.BusinessException;
import com.chinatop.contains.model.AyUser;
import com.chinatop.contains.service.AyUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：用户控制层
 *
 * @author Ay
 * @date 2017/10/28.
 */
@Controller
@RequestMapping("/ayUser")
public class AyUserController {

    @Resource
    private AyUserService ayUserService;

    @RequestMapping("/test")
    public String test(Model model) {
        List<AyUser> ayUsers = ayUserService.findAll();
        model.addAttribute("users", ayUsers);
        return "ayUser";
    }

    @RequestMapping("/findAll")
    public String findAll(Model model) {
        List<AyUser> ayUserList = ayUserService.findAll();
        model.addAttribute("users", ayUserList);
        throw new BusinessException("业务异常！");
    }

    @RequestMapping("/findByNameAndPasswordRetry")
    public String findByNameAndPasswordRetry(Model model) {
        AyUser ayUser = ayUserService.findByNameAndPasswordRetry("阿毅", "123456");
        model.addAttribute("users", ayUser);
        return "success";
    }

}
