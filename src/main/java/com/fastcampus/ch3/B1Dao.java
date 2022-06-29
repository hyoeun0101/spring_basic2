package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class B1Dao {
    @Autowired
    DataSource ds;

    public int insert(int key, int value) throws Exception{
        Connection conn = null;
        PreparedStatement p = null;

        try {
            //하나의 커넥션으로 실행
            conn = DataSourceUtils.getConnection(ds);

            System.out.println("c0nn == "+conn);
            String sql = "insert into a1 values(?,?)";
            p = conn.prepareStatement(sql);
            p.setInt(1,key);
            p.setInt(2,value);

            return p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;//예외 발생.시키기
        } finally {
            close(p);
            DataSourceUtils.releaseConnection(conn,ds);
        }

    }

    public void deleteAll() throws SQLException {
        //트랜잭션과 별개로 동작하기 때문에 아래와 같이 작성.
        Connection conn = ds.getConnection();
        PreparedStatement p = conn.prepareStatement("delete from a1");
        p.executeUpdate();
        close(p);
    }

    private void close(AutoCloseable... acs) {
        for(AutoCloseable ac :acs)
            try { if(ac!=null) ac.close(); } catch(Exception e) { e.printStackTrace(); }
    }
}
