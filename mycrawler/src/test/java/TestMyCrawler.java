/**
 * Created with IntelliJ IDEA.
 * User: sjagannath
 * Date: 6/30/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */

import com.become.CrawlerParameters;
import com.become.Mycrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TestMyCrawler {
    @Test
    public void testMyCrawler() throws Exception {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);
        String workingDirectory = System.getProperty("user.dir");
        String crawlStorageFolder = workingDirectory + "TestCrawlerOutput";
        int numberOfCrawlers = 10;
        System.out.println("enter seed urls separated by ,(example:http://dir.yahoo.com)");
        CrawlerParameters.urlStrings ="http://dir.yahoo.com/science";
        StringTokenizer stringTokenizer = new StringTokenizer(CrawlerParameters.urlStrings, ",");
        System.out.println("select priority for crawl options:");
        System.out.println("Priority based on depth from seed url-Enter 1");
        System.out.println((" or  Priority based on last modified time of url-Enter 2"));
        String priorityOption = "1";
        CrawlerParameters.selectedPriorityOption = CrawlerParameters.priorityOption.fromString(priorityOption);
        System.out.println("Enter the number of pages to add to queue");
        CrawlerParameters.queueSize = "50";
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
        while (stringTokenizer.hasMoreElements()) {
            String s = stringTokenizer.nextToken();
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

