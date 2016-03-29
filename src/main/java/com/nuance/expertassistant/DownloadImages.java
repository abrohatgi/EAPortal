/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nuance.expertassistant;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;

public class DownloadImages {

    //The url of the website. This is just an example

    //The path of the folder that you want to save the images to
    private static String folderPath = "/Users/abhishek_rohatgi/AudiImages";

    
    public static void main (String[] args)
    {
        File fin = new File("/Users/abhishek_rohatgi/NetBeansProjects/ParseHTML/audi_manual_web_new.txt");
        
        try{
            readFile1(fin);
        }
        catch(Exception e)
        {
            System.out.println(" Exception");
        }
                
    }
    
    public static void updateFolderPath (String path)
    {
        folderPath = path;
    }
    
    private static void readFile1(File fin) throws IOException {
	FileInputStream fis = new FileInputStream(fin);
 
	//Construct BufferedReader from InputStreamReader
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
 
	String line = null;
	while ((line = br.readLine()) != null) {
            
           int indexOfHttp = line.lastIndexOf("http");
            
            String URL = line.substring(indexOfHttp);
            
		System.out.println("Executing URL :" + URL);
                executedownload(URL);
	}
 
	br.close();
}
    
    public static void executedownload(String URL) {

        try {

            
            
            
            //Connect to the website and get the html
            Document doc = Jsoup.connect(URL).get();

            //Get all elements with img tag ,
            Elements img = doc.getElementsByTag("img");

            for (Element el : img) {

                //for each element get the srs url
                String src = el.absUrl("src");

                System.out.println("Image Found!");
                System.out.println("src attribute is : "+src);

                getImages(src);

            }

        } catch (IOException ex) {
            System.err.println("There was an error");
            Logger.getLogger(DownloadImages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void getImages(String src) throws IOException {

       

        //Exctract the name of the image from the src attribute
        int indexname = src.lastIndexOf("/");

        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }

        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname, src.length());

        System.out.println(name);

        //Open a URL Stream
        URL url = new URL(src);
        InputStream in = url.openStream();
        
        
       System.out.println("This is the src : " + src );
       String FolderPath = "";
       
       if(src.contains("/images/"))
       {
       int indexofimages = src.indexOf("/images/");
        FolderPath =  src.substring(indexofimages, indexname);
       }
       else
       {
           FolderPath = "/images/";
       }
       
       
        System.out.println("The folderpath is : " + FolderPath );
        
        File tmp = new File (folderPath+ FolderPath + name);
        tmp.getParentFile().mkdirs();

        OutputStream out = new BufferedOutputStream(new FileOutputStream( tmp));

        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();

    }
}