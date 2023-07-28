package com.example.department.service;

import com.example.department.model.Slogan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
public class TestUseAbstrarctClass extends Slogan {

    @Override
    public String talkSlogan(String nameSlogan) {
        return nameSlogan;
    }
}
