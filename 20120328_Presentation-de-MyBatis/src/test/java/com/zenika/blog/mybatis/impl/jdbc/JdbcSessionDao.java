package com.zenika.blog.mybatis.impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.JdbcUtils;

import com.zenika.blog.mybatis.Participant;
import com.zenika.blog.mybatis.Session;
import com.zenika.blog.mybatis.SessionDao;


public class JdbcSessionDao implements SessionDao {

	private static final String FIND_ALL = 
			"select fo.id as id_formation, co.nom as nom_cours, fe.nom as nom_formateur, st.id as id_stagiaire, st.nom as nom_stagiaire " +
			"from formation fo, cours co, formateur fe, stagiaire st " +
			"where co.id = fo.id_cours and fe.id = fo.id_formateur and fo.id = st.id_formation " +
			"order by id_formation";
	
	@Override
	public List<Session> findAll() {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection("jdbc:h2:mem:dataSource;DB_CLOSE_DELAY=-1", "sa", "");
			statement = connection.createStatement();
			resultSet = statement.executeQuery(FIND_ALL);
			
			Session session = null;
			List<Session> sessions = new ArrayList<>();
			
			while ( resultSet.next() ) {
				Long idFormation = resultSet.getLong("id_formation");
				
				// pour mapper la session il faut vérifier le changement d'id
				if ( isNewSession(session, idFormation) ) {
					session = new Session();
					session.setIdFormation(idFormation);
					session.setNomCours( resultSet.getString("nom_cours") );
					session.setNomFormateur( resultSet.getString("nom_formateur") );
					sessions.add(session);
				}
				
				// dans le cas des participants pas besoin de vérifier (d'après la requête)
				Participant participant = new Participant();
				participant.setId( resultSet.getLong("id_stagiaire") );
				participant.setNom( resultSet.getString("nom_stagiaire") );
				session.getParticipants().add(participant);
			}
			
			return sessions;
		}
		catch (SQLException e) {
			throw new IllegalStateException("Echec de récupération des formations", e);
		}
		finally {
			JdbcUtils.closeResultSet(resultSet);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(connection);
		}
	}

	private boolean isNewSession(Session session, Long idFormation) {
		return session == null || !session.getIdFormation().equals(idFormation);
	}
	
}
