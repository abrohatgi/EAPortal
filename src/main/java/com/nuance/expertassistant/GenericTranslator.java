/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nuance.expertassistant;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
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
public class GenericTranslator {
    
     static PrintWriter writer = null;
    
    public static void main (String args[])
    {
       createFileandTranslate();
    }
    
    private static void createFileandTranslate()
    {
        
         
         try {
             writer = new PrintWriter("AudiHelpTry1.xml", "UTF-8");
         } catch (FileNotFoundException ex) {
             Logger.getLogger(GenericTranslator.class.getName()).log(Level.SEVERE, null, ex);
         } catch (UnsupportedEncodingException ex) {
             Logger.getLogger(GenericTranslator.class.getName()).log(Level.SEVERE, null, ex);
         }
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<document xmlns:fo=\"http://www.w3.org/1999/XSL/Format\"\n" +
"          xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
"          xmlns:fn=\"http://www.w3.org/2005/xpath-functions\"\n" +
"          xmlns:xdt=\"http://www.w3.org/2005/xpath-datatypes\"\n" +
"          docid=\"a382247e\"\n" +
"          title=\"Audi A4\">");
      
        
         print("<sections>");
        translateURL("http://www.audihelp.com/");
         print("</sections>");
        
        writer.println("</document>");
        writer.close();
    }
    
    private static void translateURL (String URL)
    {
         try
      {
          if(URL==null || URL.isEmpty() || URL.equalsIgnoreCase(""))
          {
              return;
          }
        
        Document doc = Jsoup.connect(URL).get();
        Elements linkTag = doc.select("h2");
        Elements elements = doc.select("*");

        for (Element element : linkTag) {
            
           printlinkText(doc);

            if (element.hasText())
            {                 
                 print("<section id =\"{}\" title =\"" + element.text() + "\">");
            }

            if (element.parent().toString().contains("</a>"))
            {
                
                translateURL(element.parent().attr("abs:href"));
               
            }
            
         print("</section>");  
        }
        
        
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
    }
    
    
    private static void printlinkText(Document doc)
    {
        Elements e = doc.body().select("*");
        
        boolean didSubSectionStart = false;
       boolean didListStart = false;
        
        for(Element element : e)
        {
            
       if (element.tagName().contains("h4") && element.text() != null && !element.text().isEmpty()) {
                if (didListStart) {
                    print("</list>");
                    didListStart = false;
                }
                if (didSubSectionStart) {
                    print("</section>");
                    didSubSectionStart = false;
                }
                print("<section id =\"{}\" title =\"" + element.text() + "\">");
                didSubSectionStart = true;

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
                    if(element.text().contains("Home | Top | Sitemap | Contacts"))
                    {
                        
                    }
                    else
                    {
                    print("<para>");
                    print(element.text());
                    print("</para>");
                    }
                }

            }

            
            if (element.tagName().contains("li") && element.text() != null && !element.text().isEmpty()) {
               
                if(element.parent().tagName().contains("ol"))
                {
                if (!didListStart) {
                    print("<list>");
                    didListStart = true;
                } 
                print("     <item><para>" + element.text() + "</para></item>");
                }
            
            
            }
            /*
            if (element.tagName().contains("img")) {

                print("<img src=\"" + element.attr("src") + "\"></img>");
            }*/
 
        }
        
        
         if (didListStart) {
                        print("</list>");
                        didSubSectionStart = false;
                    }
        
         if (didSubSectionStart) {
                        print("</section>");
                        didSubSectionStart = false;
                    }
   
    }
    
    
      private static String getElement(String Elem) {
        Map<String, String> map = new HashMap<String, String>();

        map.put("section_links", "tag_parent_h2");
        map.put("section_nodes", "tag_b|tag_h4");
        map.put("list_nodes", "tag_li");
        map.put("data_nodes", "tag_p");
        map.put("ignore_tags", "");
        map.put("ignore_section_title","");
        

        return map.get(Elem);

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
      
}
