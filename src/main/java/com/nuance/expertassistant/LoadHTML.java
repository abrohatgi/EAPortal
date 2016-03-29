/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant;

import static com.nuance.expertassistant.DownloadAndParse.writer;
import com.nuance.expertassistant.DownloadImages;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author abhishek_rohatgi
 */
public class LoadHTML {

    public static ArrayList webUrlList;
    //public static String Dirname;
    public static String URLName;
    public static HashMap URLFileMapper;
    public static int TempFileName;

    public static void main(String[] args) {
        
       
        downloadHTMLImages("http://www.audihelp.com/");
        
        
    }
    
    public static void downloadHTMLImages(String URL)
    {
        
        
        createURL(URL);
        DownloadImages.executedownload(URL);
    }
    

    public static void createURL(String URL) {

        URLName = URL;

        File theDir = new File("AudiHelp");

        

        // if the directory does not exist, create it
        if (!theDir.exists()) {
           
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                //handle it
            }
            if (result) {
                System.out.println("DIR created");
            }
        }

        webUrlList = new ArrayList<String>();
        processPage(URL);

    }

    public static void processPage(String URL) {
        
        

        try {

            System.out.println("The URL is : " + URL);
            webUrlList.add(URL);

            Document doc = Jsoup.connect(URL).get();

            
            print(URL, doc.html());

            //get all links and recursively call the processPage method
            Elements questions = doc.select("a[href]");
            for (Element link : questions) {
                if (!webUrlList.contains(link.attr("abs:href")) && link.attr("abs:href").contains(URLName) ) { 
                    processPage(link.attr("abs:href"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void print(String fileName, String content) {
       

        try {
            

            System.out.println("=========>  The file Name received is :" + fileName);
            
            String FileName = "";
            
            
            
            if(fileName.equalsIgnoreCase("http://www.audihelp.com/"))
            {
                FileName = "index.html";
            }
            else
            {     
            FileName = fileName.substring(fileName.lastIndexOf("/")+1) ;
            }
          
           
            FileWriter fileWriter = null;
           
            File newTextFile = new File("AudiHelp"+"/"+FileName );
            
            System.out.println("=========>  The file Name is :" + FileName);
            
            fileWriter = new FileWriter(newTextFile);
            fileWriter.write(content);
            fileWriter.close();
            

        } catch (Exception e) {
            System.out.println("Exception cause while writing for URL " + fileName);
            e.printStackTrace();
            e.getCause();
        }
    }

}
