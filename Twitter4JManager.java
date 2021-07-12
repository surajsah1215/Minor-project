package com;
import twitter4j.*;
import java.util.ArrayList;
import twitter4j.conf.ConfigurationBuilder;
import java.io.*;
import javax.swing.table.DefaultTableModel;
public class Twitter4JManager {
	int LIMIT= 500; //the number of retrieved tweets
	ConfigurationBuilder cb;
	Twitter twitter;
	StringBuilder sb = new StringBuilder();
	static ArrayList<String> list = new ArrayList<String>();
public static void main(String args[])throws Exception{
	readTweets("591974898139987968");
}
public static void readTweets(String query)throws Exception{
	BufferedReader br = new BufferedReader(new FileReader("id.txt"));
	String line = null;
	while((line = br.readLine())!=null){
		list.add(line);
	}
	br.close();
	Twitter4JManager tm = new Twitter4JManager();
	for(int i=0;i<list.size();i++){
		tm.performQuery(list.get(i));
	}
	FileWriter fw = new FileWriter("dataset2.txt");
	fw.write(tm.sb.toString().trim());
	fw.close();
}
public Twitter4JManager() {
	ConfigurationBuilder cb = new ConfigurationBuilder();
	cb.setDebugEnabled(true);
	cb.setOAuthConsumerKey("aKBt8eJagd4PumKz8LGmZw");
	cb.setOAuthConsumerSecret("asFAO5b3Amo8Turjl2RxiUVXyviK6PYe1X6sVVBA");
	cb.setOAuthAccessToken("1914024835-dgZBlP6Tn2zHbmOVOPHIjSiTabp9bVAzRSsKaDX");
	cb.setOAuthAccessTokenSecret("zCgN7F4csr6f3eU5uhX6NZR12O5o6mHWgBALY9U4");
	twitter = new TwitterFactory(cb.build()).getInstance();
}
public void performQuery(String inQuery) throws InterruptedException, IOException {
	try{
		 Status status = twitter.showStatus(Long.parseLong(inQuery));
        if (status == null) { // 
            // don't know if needed - T4J docs are very bad
        } else {
			System.out.println(status.getUser().getScreenName()+"===============");
            sb.append(status.getRetweetCount()+","+status.getUser().getScreenName()+" - "+status.getText()+System.getProperty("line.separator"));
        }
		
	}catch (TwitterException te) {
		System.out.println("Couldn't connect: " + te);
	}
}
}