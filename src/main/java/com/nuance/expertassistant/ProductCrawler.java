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
import static java.lang.System.exit;
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
public class ProductCrawler {

    

    static PrintWriter writer = null;
    static ArrayList<String> URLList = new ArrayList<String>();
    
    static final int maxdepth = 5;
    static int currentdepth = 1;
    static final int maxProducts = 50;
    static int currentProduct = 0;
    static ArrayList<Map<String, String>> productInfoList = new ArrayList<Map<String, String>>();
   

    public static void main(String[] args) throws IOException {
        writer = new PrintWriter(LoadConfig.getValue4Key("XML_FileName"), "UTF-8");
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<document xmlns:fo=\"http://www.w3.org/1999/XSL/Format\"\n"
                + "          xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n"
                + "          xmlns:fn=\"http://www.w3.org/2005/xpath-functions\"\n"
                + "          xmlns:xdt=\"http://www.w3.org/2005/xpath-datatypes\"\n"
                + "          docid=\"a382247e\"\n"
                + "          title=\""+LoadConfig.getValue4Key("XMLTitle")+"\">");
        crawlWebSite(LoadConfig.getValue4Key("WebPage_URL"));
        writer.println("</document>");
        writer.close();

    }
    
    public static void crawlWebSite(String URL)
    {
       NavigateToItem.crawl(URL);
    }
    
   

    public static void scrapePage(String URL) {

        try {
            
            String url = URL;
            Document doc = Jsoup.connect(url).get();
            
            String productName = doc.select(LoadConfig.getValue4Key("Product_Name")).text();
            
            if(productName == null || productName.equalsIgnoreCase(""))
            {
               System.out.println("No Product Name Found for URL ===>" + URL);
               return;
            }else
            {
                System.out.println("Product Name Found for URL ==> " + URL);
                
            }
            
            
            System.out.println(" [productname] :" + doc.select(LoadConfig.getValue4Key("Product_Name")).text());
            
            Map<String, String> productMap = new HashMap<String, String>();
            
            productMap.put("productname", doc.select(LoadConfig.getValue4Key("Product_Name")).text());
            productMap.put("productprice", doc.select(LoadConfig.getValue4Key("Product_Price")).text());
            
            productMap.put("proddesc1", doc.select(LoadConfig.getValue4Key("Product_Description_1")).text());
            productMap.put("proddesc2", doc.select(LoadConfig.getValue4Key("Product_Description_2")).text());
            productMap.put("proddesc3", doc.select(LoadConfig.getValue4Key("Product_Description_3")).text());
            productMap.put("proddesc4", doc.select(LoadConfig.getValue4Key("Product_Description_4")).text());
            
            productMap.put("supercategory", doc.select(LoadConfig.getValue4Key("Product_Category_Super")).text());
            productMap.put("topcategory", doc.select(LoadConfig.getValue4Key("Product_Category_Sub")).text());
            productMap.put("subcategory", doc.select(LoadConfig.getValue4Key("Product_Category_Current")).text());
            

            productInfoList.add(productMap);
            
            currentProduct = currentProduct + 1;
            
            if(currentProduct>maxProducts)
            {
                generateXML4Map();
                exit(0);
            }

        } catch (Exception e) {
            
            //e.printStackTrace();
        }

    }
    
    private static void generateXML4Map() {

        for(int i=0 ; i<productInfoList.size() ;i++ )
        { 
          generateXML4TopSection(productInfoList.get(i).get("topcategory"));
        }

    }
    
    private static void generateXML4TopSection(String topCategory)
    {
        printSection(topCategory);
        
        
        for(int i=0 ; i<productInfoList.size() ;i++ )
        {
            if(productInfoList.get(i).get("topcategory").equalsIgnoreCase(topCategory))
            {
                generateXML4SubSection(productInfoList.get(i).get("subcategory"));
                i=0;
            }

        }
        
        printEndSection();
        
 
    }
    
    private static void generateXML4SubSection(String SubCategory)
    {
       printSection(SubCategory);

        for(int i=0 ; i<productInfoList.size() ;i++ )
        {
            if( productInfoList.get(i).get("subcategory").equalsIgnoreCase(SubCategory))
            {
                printSection(productInfoList.get(i).get("productname"));
                printPara(productInfoList.get(i).get("productprice"));
                printPara(productInfoList.get(i).get("proddesc1"));
                printPara(productInfoList.get(i).get("proddesc2"));
                printPara(productInfoList.get(i).get("proddesc3"));
                printPara(productInfoList.get(i).get("proddesc4"));
                
                printEndSection();
                productInfoList.remove(i);
            }

        }

       printEndSection();
    }
  
    
    private static void printSection(String title)
    {
        String title2 = title.replaceAll("\'", "");
                            title2 = title2.replaceAll("\"", "");
                            title2 = title2.replaceAll("&", "and");
        
         print("<section id =\"{}\" title =\"" + title2 + "\">");
    }
    
    private static void printEndSection()
    {
        print("</section>");
    }
    
    private static void printPara(String para) {
        print("<para>");
        String filteredPara = para.replaceAll("\'", "");
                            filteredPara = filteredPara.replaceAll("\"", "");
                            filteredPara = filteredPara.replaceAll("&", "and");
        print(filteredPara);
        print("</para>");
    }

    private static void print(String msg) {

        System.out.println(msg);
        try {
            writer.println(msg);
        } catch (Exception e) {
            System.out.println("Exception cause while writing to file");
        }
    }

}
