package com.zenika.blog.rpm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

public class TwitterClient {
	
	private final HttpURLConnection connection;
	private final Appendable appendable;
	
	public static void appendTweets(HttpURLConnection connection, Appendable appendable) throws IOException {
		if (connection == null || appendable == null) {
			throw new NullPointerException();
		}
		new TwitterClient(connection, appendable).appendTweets();
	}

	private TwitterClient(HttpURLConnection connection, Appendable appendable) {
		this.connection = connection;
		this.appendable = appendable;
	}
	
	private void appendTweets() throws IOException {
		InputStream input = null;
		JsonParser parser = null;
		
		try {
			connection.connect();
			input = connection.getInputStream();
			parser = new ObjectMapper().getJsonFactory().createJsonParser(input);
			
			Iterator<JsonNode> nodes = parser.readValueAsTree().getElements();
			while ( nodes.hasNext() ) {
				JsonNode node = nodes.next();
				appendable.append("> ").append( node.path("text").getTextValue() ).append('\n');
			}
		}
		catch (FileNotFoundException e) {
			throw new UserNotFoundException(e);
		}
		finally {
			connection.disconnect();
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(parser);
		}
	}
	
}
