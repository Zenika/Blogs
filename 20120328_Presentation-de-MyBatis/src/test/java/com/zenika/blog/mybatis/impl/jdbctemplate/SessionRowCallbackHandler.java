package com.zenika.blog.mybatis.impl.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.zenika.blog.mybatis.Participant;
import com.zenika.blog.mybatis.Session;


class SessionRowCallbackHandler implements RowCallbackHandler {

	private Session session = null;
	private List<Session> sessions = new ArrayList<>();
	
	public void processRow(ResultSet resultSet) throws SQLException {
		Long idFormation = resultSet.getLong("id_formation");
		
		// pour mapper la session il faut vérifier le changement d'id
		if ( isNewSession(idFormation) ) {
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
	
	private boolean isNewSession(Long idFormation) {
		return session == null || !session.getIdFormation().equals(idFormation);
	}

	List<Session> getSessions() {
		return sessions;
	}
}
