package com.become;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Mycrawler extends WebCrawler {
    static Map<Integer,String> docIdMap=new HashMap<Integer,String>();
    public static final Integer NUMBER_OF_FILES=250;
    final static  String workingDir=System.getProperty("user.dir");

    public static final String LINKS_TXT = "Links.txt";
    public static final String HASH_MAP_SER = "HashMap.ser";

    static Integer count=0;
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
//        System.out.println("hello "+url.getURL());
//        System.out.println("hi " + url.getSubDomain());

        return !FILTERS.matcher(href).matches() && href.startsWith("http://"+url.getURL().substring(7,url.getURL().length()));
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        checkDocId(page);
    }
    private static synchronized boolean checkDocId(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println(url);
        logger.debug("URL: " + url);


        if (count >= Integer.valueOf(CrawlerParameters.queueSize)){
            System.out.println("Limit Reached. Going to interrupt");
//            System.out.println(docIdMap);
            File filename = new File(workingDir+"CrawlerOutput", "HashMap.ser");
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                System.out.println(filename.getAbsolutePath());
                fos = new FileOutputStream(filename);
                out = new ObjectOutputStream(fos);
                out.writeObject(docIdMap);
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.exit(0);
        }else
            if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            List<WebURL> links = htmlParseData.getOutgoingUrls();

            logger.debug("Text length: " + text.length());
            logger.debug("Html length: " + html.length());
            logger.debug("Number of outgoing links: " + links.size());
                System.out.println("Text length: " + text.length());

            Integer docId = page.getWebURL().getDocid();
            File file = new File(workingDir+"CrawlerOutput", docId + "");

            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
                writer.write(text);
                docIdMap.put(docId, url);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }


            File linkTextFile = new File(workingDir+"CrawlerOutput", LINKS_TXT);

            writer = null;
            try {
                writer = new PrintWriter(new FileWriter(linkTextFile, true));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block

                logger.error(e);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                logger.error(e);
            } finally {
                if (writer != null) {
                    writer.close();
                }

                count++;

//            logger.debug("Completed Writing to file=" + count + "-->" + url);

            }

    }
        return true;

    }
}