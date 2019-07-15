package com.chinatop.contains.mail.impl;

import com.chinatop.contains.mail.SendJunkMailService;
import com.chinatop.contains.model.AyUser;
import com.chinatop.contains.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SendJunkMailServiceImpl implements SendJunkMailService {
    public static final Logger logger = LogManager.getLogger(SendJunkMailServiceImpl.class);
    @Resource
    JavaMailSender mailSender;
    @Resource
    private AyUserService ayUserService;
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendJunkMail(List<AyUser> ayUserList) {
//        try {
//            if (ayUserList==null||ayUserList.size()<=0)
//            {
//                return Boolean.FALSE;
//            }
//            for (AyUser ayUser:ayUserList)
//            {
//                MimeMessage mimeMessage = this.mailSender.createMimeMessage();
//                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//    //            邮件发送方
//                message.setFrom(from);
//    //            邮件主题
//                message.setSubject("地瓜今日特卖");
//    //            邮件接收方
//                message.setTo("745487524@qq.com");
//    //            邮件内容
//                message.setText(ayUser.getName()+"你知道吗？厦门地瓜今日特卖，一斤只卖9元");
//    //            发送邮件
//                this.mailSender.send(mimeMessage);
//            }
//        } catch (MessagingException e) {
//            logger.error("sendJunkMail error and ayUser=%s",ayUserList,e);
//            return Boolean.FALSE;
//        }
        return Boolean.TRUE;
    }
}
