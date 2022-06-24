package com.fastcampus.ch3;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired
    DataSource ds;

    public int insertUser(User user) throws Exception{
        Connection conn = ds.getConnection();
        String sql = "insert into user_info values (?,?,?,?,?,?,now());";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getId());
        pstmt.setString(2,user.getPwd());
        pstmt.setString(3,user.getName());
        pstmt.setString(4,user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6,user.getSns());

        pstmt.executeUpdate();


        return 0;


    }

    @Test
    public void springJbdcConnectionTest() throws Exception {
        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
        DataSource ds = ac.getBean(DataSource.class);
        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn != null);
    }
}