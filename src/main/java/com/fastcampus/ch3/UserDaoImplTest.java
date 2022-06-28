package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UserDaoImplTest {

    @Autowired
    UserDao userDao;

    @Test
    public void deleteUser() throws Exception {
        userDao.deleteAll();
        int rowCnt = userDao.deleteUser("aaaa");
        assertTrue(rowCnt==0);

        User user = new User("aaa","111","a","aaa@naver.com",new Date(),"fb",new Date());
        rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt==1);

        rowCnt = userDao.deleteUser(user.getId());
        assertTrue(rowCnt==1);

    }

    @Test
    public void selectUser() throws Exception {
        userDao.deleteAll();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2022,1,1);

        User user = new User("aaa","111","a","aaa@naver.com",new Date(cal.getTimeInMillis()),"fb",new Date());
        int rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt==1);

        User result = userDao.selectUser(user.getId());
        assertTrue(user.equals(result));

    }

    @Test
    public void insertUser() throws Exception {
        userDao.deleteAll();
        User user = new User("aaa","111","a","aaa@naver.com",new Date(),"fb",new Date());

        int rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt==1);
    }

    @Test
    public void updateUser() throws Exception {
        userDao.deleteAll();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2000,1,1);

        User user = new User("aaa","111","a","aaa@naver.com",new Date(cal.getTimeInMillis()),"fb",new Date());
        int rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt==1);

        user.setPwd("222");
        user.setEmail("bbb@naver.com");
        rowCnt = userDao.updateUser(user);
        assertTrue(rowCnt==1);

        User result = userDao.selectUser(user.getId());
        assertTrue(user.equals(result));
    }

    @Test
    public void deleteAll() throws Exception {
        userDao.deleteAll();
        User user = new User("aaa","111","a","aaa@naver.com",new Date(),"fb",new Date());
        int rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt==1);

        userDao.deleteAll();
        User result = userDao.selectUser(user.getId());
        assertTrue(result == null);
    }
}