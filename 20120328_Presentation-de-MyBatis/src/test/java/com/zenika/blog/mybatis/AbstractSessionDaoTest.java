package com.zenika.blog.mybatis;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring.xml")
public abstract class AbstractSessionDaoTest {
	
	private static List<Session> expectedSessions = Arrays.asList(
			newSession(1L, "MyBatis", "Dridi Boukelmoune", "Hugo", "Benjamin", "Louis"),
			newSession(2L, "Scala", "Lucien Pereira", "Leo", "Axel", "Lucas")
	);
	
	@Autowired
	private ApplicationContext context;
	
	@Before
	public void setupDatabase() throws Exception {
		DataSource dataSource = context.getBean(DataSource.class);
		IDatabaseConnection connection = new DatabaseDataSourceConnection(dataSource);
		Resource resource = context.getResource("classpath:/dataset.xml");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build( resource.getFile() );
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	protected void checkSessions(List<Session> sessions) {
		assertEquals(expectedSessions, sessions);
	}
	
	// méthode utilitaire pour construire le résultat attendu
	private static long idInscription;
	private static Session newSession(Long idSession, String nomSession, String nomFormateur, String... nomStagaires) {
		Session session = new Session();
		session.setIdFormation(idSession);
		session.setNomCours(nomSession);
		session.setNomFormateur(nomFormateur);
		Set<Participant> participants = new HashSet<Participant>();
		for (String nomStagiaire : nomStagaires) {
			Participant participant = new Participant();
			participant.setId(++idInscription);
			participant.setNom(nomStagiaire);
			participants.add(participant);
		}
		session.setParticipants(participants);
		return session;
	}
}
