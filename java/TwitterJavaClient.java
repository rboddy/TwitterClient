package twitterjavaclient;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterJavaClient extends JFrame{

    public TwitterJavaClient(){
        //create widgets
        final JButton tweetBtn = new JButton("Get Tweets");
        JButton clrBtn = new JButton("Clear Tweets");
        final JTextArea tweets = new JTextArea("Tweets will display below: ");
        final JScrollPane tweetArea = new JScrollPane(tweets);
        
        //set the application icon:
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("images/TweetClientIcon.png"));

        
        //set the hints for the buttons
        tweetBtn.setToolTipText("Click here to retrieve tweets");
        clrBtn.setToolTipText("Click here to clear the tweets and start over");
        
        //set the window that contains tweets to read only
        tweets.setEditable(false);
        
        //disable auto scroll. This is totally preferential.
        DefaultCaret caret = (DefaultCaret)tweets.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        
        //create a Jpanel to contain the buttons
        JPanel buttonBar = new JPanel(new GridLayout(2,1));
        buttonBar.add(tweetBtn);
        buttonBar.add(clrBtn);
        
        //Create a panel to contain the button bar and the text area
        JPanel p1 = new JPanel(new BorderLayout());
        add(p1,BorderLayout.EAST);
        add(buttonBar,BorderLayout.WEST);
        add(tweetArea);
        
        //create action listener to trigger when tweet button is clicked
        tweetBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                //code to get tweets using a dummy developer accounts keys
                ConfigurationBuilder confBuild = new ConfigurationBuilder();
        
        confBuild.setDebugEnabled(true)
                .setOAuthConsumerKey("63xT6GWeLKhnrIzndzMmNUWVa")
                .setOAuthConsumerSecret("SuDtwYr3mYgk7YHgyHsla0dAsf3UNQQ523v8Au7nJg05ggy418")
                .setOAuthAccessToken("730147162210893824-Kt6arL773alm6MbVxqP4o3sGZkLrfnb")
                .setOAuthAccessTokenSecret("iMc33qX1B2TzDJ91EWqzl3kcwAQPUnlTt1AkN0RHB18dA");
        
        //Create instance of the twitter using the aforementioned keys.
        TwitterFactory twitFact = new TwitterFactory(confBuild.build());
        
        twitter4j.Twitter twitter = twitFact.getInstance();
        
        //create searches for each hashtag
        Query iOSQuery = new Query("#iOS");
        Query azureQuery = new Query("#azure");

        //get the results from those searches
        QueryResult iOSResult = null;
                try {
                    iOSResult = twitter.search(iOSQuery);
                } catch (TwitterException ex) {
                    java.util.logging.Logger.getLogger(TwitterJavaClient.class.getName()).log(Level.SEVERE, null, ex);
                }
        QueryResult azureResult = null;
                try {
                    azureResult = twitter.search(azureQuery);
                } catch (TwitterException ex) {
                    java.util.logging.Logger.getLogger(TwitterJavaClient.class.getName()).log(Level.SEVERE, null, ex);
                }

        //iterate over every search result for iOS
        
        for(Status iOSTweets : iOSResult.getTweets()){
            //print out iOS tweets
            tweets.append(" \n \n Twitter user: "+iOSTweets.getUser().getScreenName() + " tweeted: " + iOSTweets.getText());
            
        }
        
        //iterare over every search result for azure
        for(Status azureTweets : azureResult.getTweets()){
            //print out azure tweets
            tweets.append(" \n \n Twitter user: "+azureTweets.getUser().getScreenName() + " tweeted: " + azureTweets.getText());
        }
        
        //change the button text to display more tweets in the log
        tweetBtn.setText("Get More Tweets");
        
            }
        });
        
        //create the action listener for the clear button to create functionality
        clrBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tweets.setText("Tweets will display below: ");
                tweetBtn.setText("Get Tweets");
            }
        });

         
        
    }//end of constructor
    
    
    public static void main(String[] args) {
        // create an instance of the JFrame, set its properties
        TwitterJavaClient client = new TwitterJavaClient();
        client.setTitle("iOS/Azure Hashtag Fetcher");
        client.setSize(1200, 700);
        client.setLocationRelativeTo(null);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.setVisible(true);
    }//end of main
    
}
