import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


/**
 * Author: Spikerman < mail4spikerman@gmail.com >
 * Created Date: 10/28/17
 */
public class StreamCrawler {
    int count = 0;
    private TwitterStream twitterStream;
    private StatusListener listener;

    public StreamCrawler() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("W1wlfbPOIsHWM7HAZijxYNPW6")
                .setOAuthConsumerSecret("K2sEP5PLAHdkupiXCLdV8cjbwOoPAyDGMe0kLQ27OnhHoM8pJ8")
                .setOAuthAccessToken("3232249921-Czg0p4c5ca14t7UdkIZfEy6mOtQOkmN7tZnNZmU")
                .setOAuthAccessTokenSecret("caB9Kwmx2p04cJIGflXLMpmOzHb1IkqCDLtUkq2e8s11V");
        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        System.out.println("connect");

        listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                //here you do whatever you want with the tweet
                System.out.println(count + " " + status.getGeoLocation());
                count++;
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
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

    public void start() {
        FilterQuery filterQuery = new FilterQuery();
        double[][] locations = {{-74, 40}, {-73, 41}}; //those are the boundary from New York City
        filterQuery.locations(locations);
        twitterStream.filter(filterQuery);
    }
}
