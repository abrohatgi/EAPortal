/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
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
public class WebSiteDownloader {

    private ArrayList<String> visitedURLs = new ArrayList<String>();
    private String URLCrawlPattern = null;
    private int PageLimit = 0;
    private String WebSitePath = "/usr/local/Nuance/MultiModalFramework/apache-tomcat-7.0.16/webapps/ExpertAssistant/WebPages/";
    private Boolean MaxPageReached = false;
    private File WebSiteDir = null;
    private String SiteHostName = "";
    private String WebsiteBasePath = "http://mtl-blade20-vm55/ExpertAssistant/WebPages/";
    private Boolean isDone = false;
    private String url4QACore = "";
    private String crawlPattern4QACore = "";

    public static void main(String[] args) {

        WebSiteDownloader wsdl = new WebSiteDownloader();
        wsdl.initDownloading("bestbuylaptops", "http://www.bestbuy.ca/en-CA/category/laptops/36711.aspx", "http://www.bestbuy.ca/en-CA/product/", 1, 20);
       
        
        /* InvokeQACoreAPI.createNewProject("bestbuylaptops", "bestbuylaptops", "WEB", "http://mtl-blade20-vm55/ExpertAssistant/bestbuylaptops/en-CA/category/laptops/36711.aspx", "http://mtl-blade20-vm55/ExpertAssistant/bestbuylaptops/en-CA/product/", "1", "100");

        //System.out.println("URL for QACORE :" + wsdl.getURL4QACore());
        
        String Stage = InvokeQACoreAPI.getProjectStatus("bestbuylaptops");

        while (Stage!="READY")
        {
        Stage = InvokeQACoreAPI.getProjectStatus("bestbuylaptops");
        System.out.println("At the stage :" + Stage);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(WebSiteDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }*/

    }

    public ArrayList<String> getVisitedURLs() {
        return visitedURLs;
    }

    public Boolean isDownloadingDone() {
        return isDone;
    }

    public String getCrawlPattern4QACore() {
        return crawlPattern4QACore;
    }

    public String getURL4QACore() {
        return url4QACore;
    }

   
    
    public void initDownloading(String projectID, String URL, String crawlPattern, int MaxDepth, int maxPages) {

        WebSitePath = WebSitePath + projectID;
        WebSiteDir = new File(WebSitePath);
        System.out.println(" The path for Dir is :" + WebSitePath);
 
        if (WebSiteDir.exists()) {
            System.out.println("No Need to Download again !");
            
            if (crawlPattern == null || crawlPattern.equalsIgnoreCase("")) {
                URLCrawlPattern = SiteHostName;
            } else {
                URLCrawlPattern = crawlPattern;
            }

            PageLimit = maxPages;
            WebsiteBasePath = WebsiteBasePath + projectID + "/";
            isDone = true;
            url4QACore = URL.replace(SiteHostName, WebsiteBasePath);
            crawlPattern4QACore = crawlPattern.replace(SiteHostName, WebsiteBasePath);
            return;
        }
        
        WebSiteDir.mkdirs();
        URL url = null;
        try {
            url = new URL(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SiteHostName = url.getProtocol() + "://" + url.getHost() + "/";

        if (crawlPattern == null || crawlPattern.equalsIgnoreCase("")) {
            URLCrawlPattern = SiteHostName;
        } else {
            URLCrawlPattern = crawlPattern;
        }

        PageLimit = maxPages;

        WebsiteBasePath = WebsiteBasePath + projectID + "/";

        crawl(URL, MaxDepth);

        isDone = true;

        url4QACore = URL.replace(SiteHostName, WebsiteBasePath);
        crawlPattern4QACore = crawlPattern.replace(SiteHostName, WebsiteBasePath);

    }

    public void crawl(String URL, int maxdepth) {

        createFile(URL);

        final ArrayList<String> URLList1 = new ArrayList<String>();
        URLList1.addAll(listURLs(URL, 1));

        if (maxdepth == 1 || MaxPageReached == true) {
            return;
        }

        final ArrayList<String> URLList2 = new ArrayList<String>();

        for (int i = 0; i < URLList1.size(); i++) {
            URLList2.addAll(listURLs(URLList1.get(i), 2));
            if (MaxPageReached) {
                return;
            }
        }

        if (maxdepth == 2 || MaxPageReached == true) {
            return;
        }

        final ArrayList<String> URLList3 = new ArrayList<String>();

        for (int i = 0; i < URLList2.size(); i++) {
            URLList3.addAll(listURLs(URLList2.get(i), 3));
            if (MaxPageReached) {
                return;
            }
        }

        if (maxdepth == 3 || MaxPageReached == true) {
            return;
        }

        final ArrayList<String> URLList4 = new ArrayList<String>();

        for (int i = 0; i < URLList3.size(); i++) {
            URLList4.addAll(listURLs(URLList3.get(i), 4));
            if (MaxPageReached) {
                return;
            }
        }

        if (maxdepth == 4 || MaxPageReached == true) {
            return;
        }

        final ArrayList<String> URLList5 = new ArrayList<String>();

        for (int i = 0; i < URLList4.size(); i++) {
            URLList5.addAll(listURLs(URLList4.get(i), 5));
            if (MaxPageReached) {
                return;
            }
        }

        if (maxdepth == 5 || MaxPageReached == true) {
            return;
        }

        return;

    }

    public ArrayList<String> listURLs(String StartUrl, int depth) {

        System.out.println("************ OPERATING at DEPTH : ["
                + depth + "] *****************");

        final ArrayList<String> tempURLs = new ArrayList<String>();

        try {
            final Document doc = Jsoup.connect(StartUrl).timeout(0).get();
            final Elements links = doc.select("a");

            for (final Element link : links) {
                final String absLink = link.attr("abs:href");
                if (!visitedURLs.contains(absLink)
                        && absLink.contains(URLCrawlPattern)) {
                    visitedURLs.add(absLink);
                    if (visitedURLs.size() > PageLimit) {

                        System.out
                                .println(" Max URL Limit Reached - [Stopping ....] ");
                        System.out.println(" [Stopped] ");
                        MaxPageReached = true;
                        return tempURLs;

                    }
                    tempURLs.add(absLink);

                    System.out.println(" Creating File Number : [" + visitedURLs.size() + "] at Depth :[" + depth + "]  Name : [" + absLink
                            + "]");
                    createFile(absLink);

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }

        return tempURLs;

    }

    public void createFile(String URL) {
        try {

            URL url = new URL(URL);
            String Query = url.getQuery();

            String newURL = "";

            if (Query != null && !Query.isEmpty()) {
                newURL = URL.replace(Query, "");
                if (newURL.endsWith("?")) {
                    newURL = newURL.substring(0, newURL.length() - 1);
                }
            } else {
                newURL = URL;
            }

            url = new URL(newURL);

            final File targetFile = new File(WebSiteDir, url.getFile());
            final File targetDir = targetFile.getParentFile();
            if (!targetDir.exists() && !targetDir.mkdirs()) {
                System.out.println("Failed to create parent directory " + targetDir);
            }

            FileWriter writer = new FileWriter(targetFile);
            Document doc = Jsoup.connect(URL).timeout(0).get();

            Elements urls = doc.select("a[href]");

            for (Element urlElement : urls) {
                urlElement.attr("href", urlElement.absUrl("href").replaceAll(SiteHostName, WebsiteBasePath));

            }

            writer.write(doc.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
