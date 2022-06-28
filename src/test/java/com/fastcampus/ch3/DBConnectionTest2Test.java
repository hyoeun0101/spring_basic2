package com.fastcampus.ch3;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired
    DataSource ds;

public User selectUser(String id) throws Exception{
        Connection conn = ds.getConnection();

        String sql = "select * from user_info where id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery(); //select
        if(rs.next()){
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getDate(7).getTime()));
            return user;
        }
        return null;
    }
    @Test
    public void selectUserTest() throws Exception{
        deleteAll();
        User user = new User("bbbb", "1234", "eun1", "aaa@naver.com", new Date(), "facebook", new Date());
        int rowCnt = insertUser(user);
        User user2 = selectUser("bbbb");
        assertTrue(user.getId().equals("bbbb"));
    }

    public int insertUser(User user) throws Exception {
            Connection conn = ds.getConnection();

            String sql = "insert into user_info values (?,?,?,?,?,?,now());";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPwd());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
            pstmt.setString(6, user.getSns());

            int rowCnt = pstmt.executeUpdate();
            return rowCnt;
    }
    @Test
    public void insertUserTest() throws Exception {
        User user = new User("aaaa", "1234", "eun1", "aaa@naver.com", new Date(), "facebook", new Date());
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt == 1);
    }
    @Test
    public void transactionTest() throws Exception{
        Connection conn=null;
        try {
            deleteAll();
            conn = ds.getConnection();
            conn.setAutoCommit(false);


            String sql = "insert into user_info values (?,?,?,?,?,?,now());";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "qwerty");
            pstmt.setString(2, "123456");
            pstmt.setString(3, "aaa");
            pstmt.setString(4, "aaa@naver.com");
            pstmt.setDate(5, new java.sql.Date(new Date().getTime()));
            pstmt.setString(6, "fb");

            int rowCnt = pstmt.executeUpdate();

            pstmt.setString(1,"qwerty");
            rowCnt = pstmt.executeUpdate();//두번 insert!!

            conn.commit();

        } catch (Exception e) {
            //예외 발생하면 롤백.
            conn.rollback();
            e.printStackTrace();
        } finally {
        }
    }

    public int deletUser(String id)throws Exception{
        Connection conn = ds.getConnection();

        String sql = "delete from user_info where id = ?";

        PreparedStatement p = conn.prepareStatement(sql);
        p.setString(1, id);
        int rowCnt = p.executeUpdate();
        return rowCnt;
    }

    @Test
    public void deleteUserTest() throws Exception{
        deleteAll();
        int rowCnt = deletUser("aaaa");
        assertTrue(rowCnt == 0);

        User user = new User("bbbb", "1234", "eun1", "aaa@naver.com", new Date(), "facebook", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt == 1);

        rowCnt = deletUser(user.getId());
        assertTrue(rowCnt ==1);

        assertTrue(selectUser(user.getId())==null);

    }

    private void deleteAll() throws Exception {
        Connection conn = ds.getConnection();
        String sql = "delete from user_info";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.executeUpdate();
    }

    public int updateUser(User user) throws Exception{
        Connection conn = ds.getConnection();
        String sql = "update user_info set pwd=?, name=?, email=?,birth=?,sns=?,reg_date=? where id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getPwd());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(5,user.getSns());
        pstmt.setTimestamp(6, new java.sql.Timestamp(user.getReg_date().getTime()));
        pstmt.setString(7, user.getId());

        int rowCnt = pstmt.executeUpdate();
        return rowCnt;
    }

    @Test
    public void updateUserTest() throws Exception{
        deleteAll();
        User user1 = new User("bbbb", "1234", "eun1", "aaa@naver.com", new Date(), "facebook", new Date());
        insertUser(user1);

        User user2 = new User("bbbb", "4321", "eun", "bbb@naver.com", new Date(), "facebook", new Date());
        int rowCnt = updateUser(user2);

        assertTrue(rowCnt==1);

        User result = selectUser("bbbb");
        assertTrue(user2.getPwd().equals(result.getPwd()));
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