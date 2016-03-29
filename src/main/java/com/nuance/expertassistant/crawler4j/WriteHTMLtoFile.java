/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant.crawler4j;

import com.nuance.expertassistant.LoadConfig;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author abhishek_rohatgi
 */
public class WriteHTMLtoFile {
    
   

    public void writetofile(String projectID ,String fileName, String html) {
        
        String htmlTempPath = "/Users/abhishek_rohatgi/Desktop/data_files";

        File file = new File(htmlTempPath+"/" + projectID + "/" + fileName );

        String content = html;

        try {
            FileOutputStream fop = new FileOutputStream(file);

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

        } catch (Exception e) {
            System.out.println("Exception Writing HTML to File :" + e.getMessage());
            
        }
    }

}
