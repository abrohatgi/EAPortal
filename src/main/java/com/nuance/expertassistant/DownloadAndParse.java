/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author abhishek_rohatgi
 */
public class DownloadAndParse {

    static PrintWriter writer = null;
    
    public static void main(String[] args) throws IOException {
        writer = new PrintWriter("AudiHelp-1.xml", "UTF-8");
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<document xmlns:fo=\"http://www.w3.org/1999/XSL/Format\"\n" +
"          xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
"          xmlns:fn=\"http://www.w3.org/2005/xpath-functions\"\n" +
"          xmlns:xdt=\"http://www.w3.org/2005/xpath-datatypes\"\n" +
"          docid=\"a382247e\"\n" +
"          title=\"Audi A4\">");
       // parseURL("http://www.audihelp.com/auda-5-instruments_and_warning_indicator_lamps.html");
        
         print("<sections>");
        extractURL("http://www.audihelp.com/");
         print("</sections>");
        
        writer.println("</document>");
        writer.close();
        
    }
    
    public static void extractURL(String URL)
    {
      try
      {
        Document doc = Jsoup.connect(URL).get();
        Elements h3Tags = doc.select("h3");
        //System.out.println(" The tags " + h3Tags);
        
          print("<section>");
        
        for (Element element : h3Tags) {
            
           
            if (element.hasText())
            {
                 print("</section>");
                 print("<section id =\"{}\" title =\"" + element.text() + "\">");
            }
            
            if (element.toString().contains("</a>"))
            {
                parseURL(element.child(0).attr("abs:href"));
               
            }
            
           
        }
        
        
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
 
    }
    
    public static void parseURL (String URL)
    {
        


        try
        {
            
       // DownloadImages.executedownload(URL);    
        
        String url = URL;
        boolean didSubSectionStart = false;
        boolean didListStart = false;

        Document doc = Jsoup.connect(url).get();
        //Elements links = doc.select("a[href]");
        //Elements media = doc.select("[src]");
        //Elements imports = doc.select("link[href]");
        //Elements heading1 = doc.select("h2");
        //Elements heading2 = doc.select("h3");
        //Elements heading3 = doc.select("h4");

        //Elements hTags = doc.select("h1, h2, h3, h4, h5, h6");
        //Elements h1Tags = hTags.select("h1");
        //Elements h2Tags = hTags.select("h2");
        //Elements h3Tags = hTags.select("h3");
        Elements elements = doc.body().select("*");
        
         print("<section>");

        for (Element element : elements) {
            // print(element.ownText());

            if (element.tagName().contains("h1") && element.text() != null && !element.text().isEmpty()) {
                if (didListStart) {
                    print("</list>");
                    didListStart = false;
                }
                if (didSubSectionStart) {
                    print("</section>");
                    didSubSectionStart = false;
                }
                print("</section>");
                print("<section id =\"{}\" title =\"" + element.text() + "\">");

            }

            if (element.tagName().contains("h2") && element.text() != null && !element.text().isEmpty()) {
                if (didListStart) {
                    print("</list>");
                    didListStart = false;
                }
                if (didSubSectionStart) {
                    print("</section>");
                    didSubSectionStart = false;
                }
                print("</section>");
                print("<section id =\"{}\" title =\"" + element.text() + "\">");
                
                if(element.parent().tagName().equalsIgnoreCase("a"))
                {
                   parseURL(element.parent().attr("abs:href"));
                }
                

            }

            if (element.tagName().contains("h3") && element.text() != null && !element.text().isEmpty()) {
                if (didListStart) {
                    print("</list>");
                    didListStart = false;
                }
                if (didSubSectionStart) {
                    print("</section>");
                    didSubSectionStart = false;
                }
                print("</section>");
                print("<section id =\"{}\" title =\"" + element.text() + "\">");

            }

            if (element.tagName().contains("h4") && element.text() != null && !element.text().isEmpty()) {
                if (didListStart) {
                    print("</list>");
                    didListStart = false;
                }
                if (didSubSectionStart) {
                    print("</section>");
                    didSubSectionStart = false;
                }
                print("</section>");
                print("<section id =\"{}\" title =\"" + element.text() + "\">");

            }

            if (element.tagName().contains("p") && element.text() != null && !element.text().isEmpty()) {

                if (didListStart) {
                    print("</list>");
                    didListStart = false;
                }

                if (element.toString().endsWith("</b></p>")) {
                    if (didSubSectionStart) {
                        print("</section>");
                        didSubSectionStart = false;
                    }

                    print("<section id =\"{}\" title =\"" + element.text() + "\">");
                    didSubSectionStart = true;
                } else {
                    print("<para>");
                    print(element.text());
                    print("</para>");
                }

            }

            if (element.tagName().contains("li") && element.text() != null && !element.text().isEmpty()) {
                if (!didListStart) {
                    print("<list>");
                    didListStart = true;
                }

                print("     <item><para>" + element.text() + "</para></item>");
            }

            if (element.tagName().contains("img")) {

                print("<img src=\"" + element.attr("src") + "\"></img>");
            }

        }
        if (didListStart) {
            print("</list>");
            didListStart = false;
        }
        if (didSubSectionStart) {
            print("</section>");
            didSubSectionStart = false;
        }
        print("</section>");
        
        }
        catch(Exception e)
        {
            print("<parseerror>" + URL +"</parseerror>");
            e.printStackTrace();
        }
        
    }

    private static void print(String msg) {
        
       System.out.println(msg);
        try
        {
        writer.println(msg);
        }
        catch (Exception e)
        {
         System.out.println("Exception cause while writing to file");
        }
    }

    private static String trim(String s, int width) {
        if (s.length() > width) {
            return s.substring(0, width - 1) + ".";
        } else {
            return s;
        }
    }
}
