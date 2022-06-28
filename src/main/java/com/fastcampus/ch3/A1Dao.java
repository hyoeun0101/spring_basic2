package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class A1Dao {
    @Autowired
    DataSource ds;

    public int insert(int key, int value) throws Exception{
        Connection conn = null;
        PreparedStatement p = null;

        try {

            conn = ds.getConnection();
            p = conn.prepareStatement("insert into a1 values (?,?)");
            p.setInt(1,key);
            p.setInt(2,value);

            return p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
             close(conn,p);
        }
    }


    private void close(AutoCloseable... acs) {
        for(AutoCloseable ac :acs)
            try { if(ac!=null) ac.close(); } catch(Exception e) { e.printStackTrace(); }
    }
}
