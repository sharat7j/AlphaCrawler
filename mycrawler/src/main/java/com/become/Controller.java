package com.become;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Controller {
    public static void main(String[] args) throws Exception {
        InputStreamReader in=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(in) ;
        String workingDirectory=System.getProperty("user.dir");
        String crawlStorageFolder = workingDirectory+"CrawlerOutput";
        int numberOfCrawlers = 50;
        System.out.println("enter seed urls separated by ,(example:http://dir.yahoo.com)");

        br=new BufferedReader(in);
        CrawlerParameters.urlStrings=br.readLine();
        StringTokenizer stringTokenizer=new StringTokenizer(CrawlerParameters.urlStrings,",");
        System.out.println("select priority for crawl options:");
        System.out.println("Priority based on depth from seed url-Enter 1");
        System.out.println((" or  Priority based on last modified time of url-Enter 2"));
        br=new BufferedReader(in);
        String priorityOption=br.readLine();
        if(!priorityOption.equals("1") && !priorityOption.equals("2")){
        while(!priorityOption.equals("1") && !priorityOption.equals("2") )
        {
            System.out.println("please enter 1 or 2");
            br=new BufferedReader(in);
            priorityOption=br.readLine();
        }
        }
        CrawlerParameters.selectedPriorityOption=CrawlerParameters.priorityOption.fromString(priorityOption);
        System.out.println("Enter the number of pages to add to queue");
        br=new BufferedReader(in);
        CrawlerParameters.queueSize=br.readLine();
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

                /*
                 * Instantiate the controller for this crawl.
                 */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

                /*
                 * For each crawl, you need to add some seed urls. These are the first
                 * URLs that are fetched and then the crawler starts following links
                 * which are found in these pages
                 */
        while(stringTokenizer.hasMoreElements()){
            String  s=stringTokenizer.nextToken();
            System.out.println(s);
            controller.addSeed(s);
        }

                /*
                 * Start the crawl. This is a blocking operation, meaning that your code
                 * will reach the line after this only when crawling is finished.
                 */
        controller.start(Mycrawler.class, numberOfCrawlers);
    }
}