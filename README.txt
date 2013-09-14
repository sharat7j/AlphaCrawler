The crawler is designed to take following inputs-
1) A string of urls separated by comma that the user wants to be crawled
2) Choose priority of wheather the the crawler must crawl based on depth from initial url or last modified time of the url.
3) The number of pages that need to be crawled.

My idea was to use the last modified date of a url as a parameter to consider while assigning a priority to the url considered for crawling and adding it to the queue.
The idea of using the crawler to crawl based on last modified time of the url is used in order to get only the latest modified urls which could be important when building a search engine whose functionality depends on this parameter.

There have been certain filters added using regex to make sure that urls referring to media type or zip files are not included while crawling.

Requirements to run code-
1) Maven 3.0.4
2) Java,jre version 1.6 vendor-sun
3) OS-windows 7 64 bit

Dowload the zip file and unzip the folder.
Using command line go to the downloaded directory
Using maven execute the following steps on command line-
1)mvn clean
2)mvn compile
3)mvn package (runs a test) to see if the program works for a sample input parameters

Finally to run the main program Controller.java use the following command-
mvn -e exec:java -Dexec.mainClass =com.become.Controller

Example run-
enter seed urls separated by ,(example:http://dir.yahoo.com)
http://dir.yahoo.com/science

select priority for crawl options:
Priority based on depth from seed url-Enter 1
 or  Priority based on last modified time of url-Enter 2
 1
 
 Enter the number of pages to add to queue
 100
 
 After the limit is reached result of the crawled pages are stored in a folder called mycrawlerCrawlerOutput in a serialized file called Hashmap.ser where the crawled url's doc id is mapped to the url itself  in a hashmap.
 
 
 
 