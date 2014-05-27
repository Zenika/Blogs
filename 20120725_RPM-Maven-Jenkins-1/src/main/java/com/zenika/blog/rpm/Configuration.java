package com.zenika.blog.rpm;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Properties;

public class Configuration {
	
	private static final String CONFIG_SYSTEM_PROPERTY = "sirkuttaa.config.file";
	
	private static final int DEFAULT_LIMIT = 10;
	private static final int DEFAULT_TIMEOUT = 2000;
	
	private static final String URL_TEMPLATE =
			"http://api.twitter.com/1/statuses/user_timeline.json?screen_name={0}&count={1}&include_rts=1";

	private int limit = DEFAULT_LIMIT;
	private int timeout = DEFAULT_TIMEOUT;
	
	public static Configuration create() throws IOException {
		Configuration configuration = new Configuration();
		
		if ( System.getProperties().containsKey(CONFIG_SYSTEM_PROPERTY) ) {
			configuration.loadConfigurationFile( System.getProperty(CONFIG_SYSTEM_PROPERTY) );
		}
		
		return configuration;
	}
	
	public void loadConfigurationFile(String path) throws IOException {
		if (path == null) {
			throw new NullPointerException();
		}
		
		InputStream input = null;
		
		try {
			input = new FileInputStream(path);
			Properties properties = new Properties();
			properties.load(input);
			updateConfiguration(properties);
		}
		finally {
			IOUtils.closeQuietly(input);
		}
	}

	private void updateConfiguration(Properties properties) throws IOException {
		assert properties != null;
		
		try {
			if (properties.containsKey("limit")) {
				setLimit( Integer.parseInt( properties.getProperty("limit") ) );
			}
			
			if (properties.containsKey("timeout")) {
				setTimeout( Integer.parseInt( properties.getProperty("timeout") ) );
			}
		}
		catch (RuntimeException e) {
			throw new IOException(e);
		}
	}

	public HttpURLConnection openConnection(String name) throws IOException {
		if (name == null) {
			throw new NullPointerException();
		}
		
		String url = MessageFormat.format(URL_TEMPLATE, URLEncoder.encode(name, "UTF-8"), getLimit());
		
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setConnectTimeout(getTimeout());
		connection.setReadTimeout(getTimeout());
		
		return connection;
	}

	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		if (limit <= 0) {
			throw new IllegalArgumentException("Invalid limit : " + limit);
		}
		this.limit = limit;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		if (timeout <= 0) {
			throw new IllegalArgumentException("Invalid timeout : " + timeout);
		}
		this.timeout = timeout;
	}
}

