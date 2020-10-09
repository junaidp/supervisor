package com.wbc.supervisor.database.jooq;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Jim on 3/29/14.
 */
public class JooqUtil {

    /*
        if this fails, conn will be null and sb will contain the error
     */
    public  static Connection getConnection( String user, String pw, String dbName, String ipAddress, StringBuilder sb ) {

        Connection conn = null;
        String host = "localhost";
        if (ipAddress != null && ipAddress.length()> 4 ) {
            host = ipAddress;
        }
        String url = "jdbc:mysql://" + host + ":3306/" + dbName + "?autoReconnect=true";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, pw);
        } catch (InstantiationException e) {
            sb.append( "InstantiationException for  jdbc driver");
        } catch (IllegalAccessException e) {
            sb.append( "IllegalAccessException for  jdbc driver");
        } catch (ClassNotFoundException e) {
            sb.append( "ClassNotFoundException for  jdbc driver");
        } catch (SQLException e) {
            sb.append( "SQLException failed to get connection to remote database " + dbName + "  " + e.getMessage());
        }
        return conn;
    }


    public static DSLContext getKpiDsl(StringBuilder sb ) {
        Connection conn = getConnection("netvue", "netvue", "kpi", "127.0.0.1", sb );
        return DSL.using(conn, SQLDialect.MYSQL);
    }

    public static DSLContext getIntravueDsl(String ip, StringBuilder sb ) {
        Connection conn = getConnection("netvue", "netvue", "intravue", ip, sb );
        return DSL.using(conn, SQLDialect.MYSQL);
    }

    public static DSLContext getDatabaseDsl(String hostip, String user, String pw, String kpiDbName) throws Exception {
        StringBuilder sb = new StringBuilder();
        Connection conn = JooqUtil.getConnection( user, pw, kpiDbName, hostip, sb);
        return DSL.using(conn, SQLDialect.MYSQL );
    }


}
