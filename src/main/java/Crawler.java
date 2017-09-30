import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Crawler {

    private List<String> tweets = new ArrayList<>();
    private Twitter twitter;
    private String fileName = "tweets.txt";

    public Crawler() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("W1wlfbPOIsHWM7HAZijxYNPW6")
                .setOAuthConsumerSecret("K2sEP5PLAHdkupiXCLdV8cjbwOoPAyDGMe0kLQ27OnhHoM8pJ8")
                .setOAuthAccessToken("3232249921-Czg0p4c5ca14t7UdkIZfEy6mOtQOkmN7tZnNZmU")
                .setOAuthAccessTokenSecret("caB9Kwmx2p04cJIGflXLMpmOzHb1IkqCDLtUkq2e8s11V");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        System.out.println("connect");
    }

    public static void main(String args[]) {
        Crawler crawler = new Crawler();
        try {
            String s = "#hadoop";
            crawler.searchtweets(s);
            crawler.exportToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchtweets(String s) {
        try {
            Query query = new Query(s);
            query.setCount(100);
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {
                String tweet = "@" + status.getUser().getScreenName() + ":" + status.getText();
                tweets.add(tweet);
                System.out.println(tweet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void exportToFile() {
        Path file = Paths.get(fileName);
        try {
            Files.write(file, tweets, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
