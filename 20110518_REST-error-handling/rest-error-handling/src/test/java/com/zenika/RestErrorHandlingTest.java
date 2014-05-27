/**
 * 
 */
package com.zenika;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.zenika.domain.Contact;

/**
 * @author Arnaud Cogoluègnes
 *
 */
public class RestErrorHandlingTest {
	
	private String url = "http://localhost:8081/rest/contacts/{id}";
	
	private RestTemplate restTemplate = new RestTemplate();

	@Test public void getContactOk() {
		Contact contact = restTemplate.getForObject(url, Contact.class, "1");
		assertNotNull(contact);
		assertEquals(1L, contact.getId().longValue());
	}
	
	@Test public void getContactNotFound() {
		try {
			restTemplate.getForObject(url, Contact.class, "2");
			fail("no contact with this id");
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		} catch(Exception e) {
			fail("this isn't the expected exception: "+e.getMessage());
		}
	}
	
	private static Server server;
	
	@BeforeClass public static void init() throws Exception {
		server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8081);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/rest");
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);
		server.start();

	}
	
	@AfterClass public static void destroy() throws Exception {
		server.stop();
	}
	
}
