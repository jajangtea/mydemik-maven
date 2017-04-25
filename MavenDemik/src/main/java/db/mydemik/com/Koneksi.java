/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mydemik.com;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Koneksi {
    MysqlDataSource dataSource=new MysqlDataSource();
 
    public Koneksi() {
        dataSource.setUser("root");
        dataSource.setPassword("");
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("mydemik");
    }
    
    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}