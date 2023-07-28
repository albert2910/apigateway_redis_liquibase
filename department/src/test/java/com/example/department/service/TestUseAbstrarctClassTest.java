package com.example.department.service;

import com.example.department.model.Slogan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TestUseAbstrarctClassTest {
    @InjectMocks
    TestUseAbstrarctClass testUseAbstrarctClass;

    @Test
    void talkSlogan() {
//        Mockito.when(testUseAbstrarctClass.talkSlogan("hello")).thenReturn("hello");
        Assertions.assertEquals("hello",testUseAbstrarctClass.talkSlogan("hello"));
    }
}