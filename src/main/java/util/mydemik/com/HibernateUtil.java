/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.mydemik.com;

import form.mydemik.com.DBSetting;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author jajangtea
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() 
    {
        String dbUrl = null,user = null,pass = "";
        try 
        {
            try 
            {
                
                File file = new File("D:/file.txt");
                if(file.exists()) 
                { 
                    FileReader fileReader = new FileReader(file);
                    dbUrl = (String) FileUtils.readLines(file).get(0);
                    user = (String) FileUtils.readLines(file).get(1);
                    pass = (String) FileUtils.readLines(file).get(2);
                    fileReader.close();
                    System.out.println(user.toString());
                }
                else
                {
                    file.createNewFile();
                    DBSetting db = new DBSetting();
                    db.setVisible(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            AnnotationConfiguration conf = new AnnotationConfiguration().configure();
            // <!-- Database connection settings -->
            conf.setProperty("hibernate.connection.url", dbUrl);
            conf.setProperty("hibernate.connection.username", user);
            conf.setProperty("connection.password", pass);
            sessionFactory=conf.buildSessionFactory();
        } catch (Throwable ex) 
        {
          // Log exception!
            throw new ExceptionInInitializerError(ex);
        }
        return sessionFactory;
    }
    
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
}
