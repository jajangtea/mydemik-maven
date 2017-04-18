/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.mydemik.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author jajangtea
 */
public class Bacafile {
    public static void main(String[] args) throws IOException {
        try 
        {
			
			//BufferedReader bufferedReader = new BufferedReader(fileReader);
			//StringBuffer stringBuffer = new StringBuffer();
			//String line;
            //int count=1;
//			while ((line = bufferedReader.readLine()) != null) {
//				stringBuffer.append(count+"."+line);
//				stringBuffer.append("\n");
//                count++;
//			}
            File file = new File("D:/file.txt");
                FileReader fileReader = new FileReader(file);
            String dbUrl = (String) FileUtils.readLines(file).get(0);
            String user = (String) FileUtils.readLines(file).get(1);
            String pass = (String) FileUtils.readLines(file).get(2);
			fileReader.close();
			System.out.println(dbUrl.toString());
            System.out.println(user.toString());
            System.out.println(pass.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
