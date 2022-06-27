package com.fastcampus.ch3;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
class UserDaoImplTest {

    @Autowired
    UserDao userDao;
    @Test
    void deleteUser() {
    }

    @Test
    void selectUser() {
    }

    @Test
    void insertUser() {
    }

    @Test
    void updateUser() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2021,1,1);

        User user = new User("qwer","1234","abc","aaa@naver.com",new Date(cal.getTimeInMillis()),"fb",new Date());
        int rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt==1);

        user.setPwd("4321");
        user.setEmail("bbb@naver.com");
        rowCnt = userDao.updateUser(user);
        assertTrue(rowCnt==1);

        User user2 = userDao.selectUser(user.getId());
        System.out.println(user);
        System.out.println(user2);
        assertTrue(user.equals(user2));

    }

    @Test
    void deleteAll() {
    }
}