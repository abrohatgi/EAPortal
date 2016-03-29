/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant.crawler4j;

import com.nuance.expertassistant.LoadConfig;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.io.File;

/**
 *
 * @author abhishek_rohatgi
 */
public class EAController {
    
    
    public static void main(String args[]) {
        crawlWebSite("optus", "http://www.optus.com.au/shop/mobile", " http://www.optus.com.au/shop/mobile", 3000, 10);
         
    }

    public static synchronized void crawlWebSite(String projectID, String seedURL, String containsStr, int maxPages, int depth) {
        CrawlController controller = null;
        try {
            
            String htmlTemp = "/Users/abhishek_rohatgi/Desktop/data_files/macquarie";
            
            String crawlStorageFolder = htmlTemp+"/temp/";
            
             

            File files = new File(htmlTemp+"/"+projectID);
            if (!files.exists()) {
                if (files.mkdirs()) {
                    System.out.println("Multiple directories are created!");
                } else {
                    System.out.println("Failed to create multiple directories!");
                }
            }

            int numberOfCrawlers = 7;

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            config.setMaxDepthOfCrawling(depth);
            config.setMaxPagesToFetch(maxPages);
            config.setIncludeHttpsPages(true);
            System.out.println("Follow Redirects ? :" + config.isFollowRedirects());
            config.setUserAgentString("myCrawler");

            /*
             * Instantiate the controller for this crawl.
             */
            PageFetcher pageFetcher = new PageFetcher(config);

            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            robotstxtConfig.setEnabled(false);
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

            controller = new CrawlController(config, pageFetcher, robotstxtServer);

            EACrawler eac = new EACrawler();
            eac.setConfig(seedURL, projectID);

            /*
             * For each crawl, you need to add some seed urls. These are the first
             * URLs that are fetched and then the crawler starts following links
             * which are found in these pages
             */
            controller.addSeed(seedURL);
            //controller.addSeed("http://www.ics.uci.edu/~welling/");
            //controller.addSeed("http://www.ics.uci.edu/");

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            long start = System.currentTimeMillis();
            long end = start + 10 * 1000; // 60 seconds * 1000 ms/sec

            
            controller.start(EACrawler.class, numberOfCrawlers);
            

            controller.shutdown();
            controller.getPageFetcher().shutDown();

           

        } catch (Exception e) {

            System.out.println("Crawling Done - However Exception was thrown -" + e.getMessage());
            controller.shutdown();
            controller.getPageFetcher().shutDown();
        }
    }
}
