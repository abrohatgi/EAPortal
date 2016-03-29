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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author abhishek_rohatgi
 */
public class HTMLtoXML {

    static int sectionlevel = 0;

    static PrintWriter writer = null;
    static ArrayList<String> URLList = new ArrayList<String>();
    static ArrayList<String> IgnoreList = new ArrayList<String>();
    static Boolean shouldAddDescription = false;

    public static void main(String[] args) throws IOException {
        writer = new PrintWriter("CNETReviewstry.xml", "UTF-8");
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<document xmlns:fo=\"http://www.w3.org/1999/XSL/Format\"\n"
                + "          xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n"
                + "          xmlns:fn=\"http://www.w3.org/2005/xpath-functions\"\n"
                + "          xmlns:xdt=\"http://www.w3.org/2005/xpath-datatypes\"\n"
                + "          docid=\"a382247e\"\n"
                + "          title=\"CNET REVIEWS\">");
        parseURL("http://www.cnet.com/reviews/");
        writer.println("</document>");
        writer.close();

    }

    public static void parseURL(String URL) {

        try {

            
            
            //DownloadImages.executedownload(URL);    
            String url = URL;
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
            Elements elements = doc.select("*");

           // Elements divElements = doc.select("div.catHead");
            for (Element element : elements) {

                /*if(element.tagName().equalsIgnoreCase("p"))
                 {
                 print("<para>");
                 print(element.text());
                 print("</para>");
                 }*/
                if (element.tagName().equalsIgnoreCase("a")) {

                    if (element.parent().toString().contains("catHead\">") || element.parent().toString().contains("topic\">") || element.parent().toString().contains(" prodInfo\">")) {

                        if (element.parent().toString().contains(" prodInfo\">"))
                        {
                        shouldAddDescription = true;
                        }
                        else
                        {
                        shouldAddDescription = false; 
                        }
                        
                        
                        if (!URLList.contains(element.attr("abs:href"))) {

                          //System.out.println("=============================>>");
                            //System.out.println(element.parent().toString());
                            //System.out.println("<<===============================");

                            String title = element.text().replaceAll("\'", "");
                            title = title.replaceAll("\"", "");
                            title = title.replaceAll("&", "and");

                            print("<section id =\"{}\" title =\"" + title + "\">");
                            URLList.add(element.attr("abs:href"));
                            //System.out.println(" URL TEXT --> " + element.text() + "["+ element.attr("abs:href") +"]");
                            parseURL(element.attr("abs:href"));
                        }

                    }
                }
                
                 if(element.tagName().equalsIgnoreCase("p") && shouldAddDescription == true && !URL.contains("http://www.cnet.com/reviews/"))
                 {
                 print("<para>");
                 
                 String title = element.text().replaceAll("\'", "");
                            title = title.replaceAll("\"", "");
                            title = title.replaceAll("&", "and");
                 print(title);
                 print("</para>");
                 }
                
                /*

                if (element.tagName().equalsIgnoreCase("h3")) {

                    String title = element.text().replaceAll("\'", "");
                    title = title.replaceAll("\"", "");
                    title = title.replaceAll("&", "and");
                    print("</section>");
                    print("<section id =\"{}\" title =\"" + title + "\">");
                }
                
                */

            }
            /*
             for (Element element : elements) {

             if(element.parent().tagName().equalsIgnoreCase("a"))
             {
             if(URLList.contains(element.parent().attr("abs:href")))
             {
             // System.out.println(" Already Exists : " + element.parent().attr("abs:href"));
             }
             else if (!element.parent().attr("abs:href").isEmpty() && element.parent().attr("abs:href")!=null && !element.parent().text().isEmpty())
             {
                   
             System.out.println("--> " + element.parent().text() + "[" +element.parent().attr("abs:href") +"]");
             URLList.add(element.parent().attr("abs:href"));
             parseURL(element.parent().attr("abs:href"));
                   
             } 
             }

             }
             */
            
            
         print("</section>");

        } catch (Exception e) {
            print("<parseerror>" + URL + "</parseerror>");
            //e.printStackTrace();
        }

    }

    private static void print(String msg) {

        System.out.println(msg);
        try {
            writer.println(msg);
        } catch (Exception e) {
            System.out.println("Exception cause while writing to file");
        }
    }

    private static String getElement(String Elem) {
        Map<String, String> map = new HashMap<String, String>();

        map.put("sectionLevel1", "div|catHead");
        map.put("sectionLevel2", "div|topic");
        map.put("sectionLevel3", "div|prodInfo");
        map.put("sectionLevel4", "tag|H3");
        map.put("para", "tag|p");

        return map.get(Elem);

    }

}
