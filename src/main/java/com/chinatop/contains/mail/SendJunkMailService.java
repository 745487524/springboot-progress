package com.chinatop.contains.mail;

import com.chinatop.contains.model.AyUser;

import java.util.List;

public interface SendJunkMailService {
    boolean sendJunkMail(List<AyUser> ayUserList);
}
