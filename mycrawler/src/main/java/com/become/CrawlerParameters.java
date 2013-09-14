package com.become;

/**
 * Created with IntelliJ IDEA.
 * User: sjagannath
 * Date: 6/27/13
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrawlerParameters {
    public static String urlStrings;
    public static String queueSize;

    public static enum priorityOption{
        DEPTHCRAWL("1"),LAST_MODIFIED_TIME_CRAWL("2");
        private String text;

        priorityOption(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
        public static priorityOption fromString(String text) {
            if (text != null) {
                for (priorityOption po : priorityOption.values()) {
                    if (text.equalsIgnoreCase(po.text)) {
                        return po;
                    }
                }
            }
            return null;
        }
    };
    public static priorityOption selectedPriorityOption;



}
