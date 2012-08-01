package com.zenika.blog.rpm;

import java.io.IOException;
import java.net.HttpURLConnection;



public class Sirkuttaa {
	
	public static void main(final String... args) {
		
		if (args.length != 1) {
			System.err.println("Usage: sirkuttaa {name}");
			System.exit(1);
		}
		
		final String name = args[0];
		
		try {
			Configuration configuration = Configuration.create();
			HttpURLConnection connection = configuration.openConnection(name);
			TwitterClient.appendTweets(connection, System.out);
		}
		catch (UserNotFoundException e) {
			System.err.println("User not found : " + name);
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Error : " + e.getMessage());
			System.exit(1);
		}
	}
}
