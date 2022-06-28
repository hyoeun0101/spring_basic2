package com.fastcampus.ch3;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
class A1DaoTest {
    @Autowired
    A1Dao a1Dao;

    @Test
    void insertTest() throws Exception {
        a1Dao.insert(1,100);
        a1Dao.insert(2,200);
    }
}