/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant;

import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author abhishek_rohatgi
 */
public class NavigateToItem {

    static ArrayList<String> potentialProductList = new ArrayList<String>();

    static int currentdepth = 1;
    static int maxdepth = Integer.parseInt(LoadConfig.getValue4Key("MaxDepth"));
    static String URLProductPattern = LoadConfig.getValue4Key("Product_URL_Pattern");
    static String CrawlURLPattern = LoadConfig.getValue4Key("Crawl_URL_Pattern");
    static int Num_Products = Integer.parseInt(LoadConfig.getValue4Key("Number_Of_Products"));
    
    
    
    public static void crawl(String URL) {

        ArrayList<String> URLList1 = new ArrayList<String>();
        URLList1.addAll(listURLs(URL, 1));
        
        if(maxdepth == 1) return;

        ArrayList<String> URLList2 = new ArrayList<String>();
        
        for (int i = 0; i < URLList1.size(); i++) {
            URLList2.addAll(listURLs(URLList1.get(i), 2));
        }
        
        if(maxdepth == 2) return;
        
        ArrayList<String> URLList3 = new ArrayList<String>();
        
        for (int i = 0; i < URLList2.size(); i++) {
            URLList3.addAll(listURLs(URLList2.get(i), 3));
        }
        
        if(maxdepth == 3) return;
        
        ArrayList<String> URLList4 = new ArrayList<String>();
        
        for (int i = 0; i < URLList3.size(); i++) {
            URLList4.addAll(listURLs(URLList3.get(i), 4));
        }
        
        if(maxdepth == 4) return;
        
        ArrayList<String> URLList5 = new ArrayList<String>();
        
        for (int i = 0; i < URLList4.size(); i++) {
            URLList5.addAll(listURLs(URLList4.get(i), 5));
        }
        
        if(maxdepth == 5) return;
        
        return;

    }

    public static  ArrayList<String> listURLs(String StartUrl, int depth) {

        ArrayList<String> tempURLs = new ArrayList<String>();

        try {
            Document doc = Jsoup.connect(StartUrl).get();
            Elements links = doc.select("a");

            for (Element link : links) {

                String absLink = link.attr("abs:href");
                
                 if (absLink.contains(URLProductPattern)) {
                        if (!potentialProductList.contains(absLink)) {
                            System.out.println(" PRODUCT LINK : [" + absLink + "]");
                            potentialProductList.add(absLink);
                            ProductCrawler.scrapePage(absLink);
                        }

                    }

                if (absLink.contains(CrawlURLPattern)) {

                    
                    if(LoadConfig.getValue4Key("Filter_Name").equalsIgnoreCase("Walmart_Filter"))
                    {
                    if (Walmart_filter(link, depth) != null) { 
                        if (!tempURLs.contains(absLink)) {
                            tempURLs.add(absLink);
                        }
                    }
                    }
                    
                    if(LoadConfig.getValue4Key("Filter_Name").equalsIgnoreCase("CNET_Filter"))
                    {
                    if (CNET_filter(link, depth) != null) { 
                        if (!tempURLs.contains(absLink)) {
                            tempURLs.add(absLink);
                        }
                    }
                    }

                }

            }
        } catch (Exception e) {

        }

        return tempURLs;

    }

    public static Element Walmart_filter(Element link, int depth) {

        if (depth == 1) {
            if (link.parent().parent().parent().toString().contains("category-tree stacked-border mobile-hide marg-l-0-t marg-r-0-t")) {
                return link;

            }

        } else {

            if (link.parent().parent().parent().parent().parent().toString().contains("category-tree stacked-border")
                    && link.parent().parent().parent().parent().parent().toString().contains("Browse Sub-categories")
                    && link.parent().parent().parent().toString().contains("li class=\"selected\"")
                    && link.parent().parent().toString().contains("ul class=\"l-2\"")) {

                if (!link.parent().parent().parent().parent().toString().contains("Select category filters")) {
                    return link;

                }

            }

        }
        return null;

    }
    
    public static Element CNET_filter(Element link, int depth) {
        
        
       return link; 
    }


}
