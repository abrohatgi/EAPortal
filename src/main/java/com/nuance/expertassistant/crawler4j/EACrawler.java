/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant.crawler4j;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author abhishek_rohatgi
 */
public class EACrawler extends WebCrawler{
    
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");
    
    private static String containWebSite = "";
     private static String projectID  = "";
    
    public void setConfig(String webSite , String projID)
    {
        containWebSite = webSite;
        projectID = projID;
    }

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         //System.out.println(" The referring HREF is :" + referringPage.getWebURL().getURL());
         //System.out.println(" The new HREF is :" + href);
         return !FILTERS.matcher(href).matches()
                && href.contains(containWebSite);
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("CRAWLING URL - [" + url +"]");

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             String title = htmlParseData.getTitle();
             Set<WebURL> links = htmlParseData.getOutgoingUrls();

             //System.out.println("Text length: " + text.length());
             //System.out.println("Html length: " + html.length());
            
             WriteHTMLtoFile wfile = new WriteHTMLtoFile();
             
             wfile.writetofile(projectID,title, html);
             
             //System.out.println("Number of outgoing links: " + links.size());
         }
    }
    
}
