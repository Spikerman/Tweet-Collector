import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileWriter;


/**
 * Author: Spikerman < mail4spikerman@gmail.com >
 * Created Date: 10/28/17
 */
public class StreamCrawler {
    final static Logger logger = Logger.getLogger(StreamCrawler.class);
    FileWriter file;
    private TwitterStream twitterStream;
    private String fileName = "tweets.txt";

    public StreamCrawler() {
        try {
            file = new FileWriter(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("W1wlfbPOIsHWM7HAZijxYNPW6")
                .setOAuthConsumerSecret("K2sEP5PLAHdkupiXCLdV8cjbwOoPAyDGMe0kLQ27OnhHoM8pJ8")
                .setOAuthAccessToken("3232249921-Czg0p4c5ca14t7UdkIZfEy6mOtQOkmN7tZnNZmU")
                .setOAuthAccessTokenSecret("caB9Kwmx2p04cJIGflXLMpmOzHb1IkqCDLtUkq2e8s11V");
        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                if (status.getGeoLocation() != null) {
                    handleState(status);
                }
            }

            @Override
            public void onException(Exception ex) {
                logger.info("network error");
                System.exit(-1);
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {

            }

            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub
                System.out.println(arg0);
            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub
                System.out.println(arg0);
            }

        };
        twitterStream.addListener(listener);
    }

    public static void main(String args[]) {
        StreamCrawler streamCrawler = new StreamCrawler();
        streamCrawler.start();
    }

    //construct tweet object from respond content
    private void handleState(Status status) {
        TweetInfo tweet = new TweetInfo();
        StringBuilder sb = new StringBuilder();
        tweet.id = status.getId();
        sb.append(tweet.id).append("\t");
        tweet.latitude = status.getGeoLocation().getLatitude();
        sb.append(tweet.latitude).append("\t");

        tweet.longitude = status.getGeoLocation().getLongitude();
        sb.append(tweet.longitude).append("\t");

        tweet.createdDate = status.getCreatedAt();
        sb.append(tweet.createdDate.toString()).append("\t");

        tweet.text = status.getText().replace("\n", "").replace("\r", "");
        sb.append(tweet.text).append("\t");

        tweet.userId = status.getUser().getId();
        sb.append(tweet.userId).append("\n");
        exportToFile(sb.toString());


        //System.out.println(tweet.id + " " + tweet.latitude + " " + tweet.longitude + " " + tweet.createdDate + " " + tweet.text);

    }

    private void start() {
        FilterQuery filterQuery = new FilterQuery();
        double[][] locations = {{-74, 40}, {-73, 41}}; //those are the boundary from New York City
        filterQuery.locations(locations);
        twitterStream.filter(filterQuery);
    }

    private void exportToFile(String s) {
        try {
            file.write(s);
            file.flush();// make sure buffered data is written into the disk
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
