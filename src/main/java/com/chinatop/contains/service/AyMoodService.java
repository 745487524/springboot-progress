package com.chinatop.contains.service;

import com.chinatop.contains.model.AyMood;

public interface AyMoodService {
    AyMood save(AyMood ayMood);

    String asyncSave(AyMood ayMood);
}
