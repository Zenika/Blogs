package com.zenika.blog.mybatis.impl.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.zenika.blog.mybatis.Participant;
import com.zenika.blog.mybatis.Session;
import com.zenika.blog.mybatis.SessionDao;


public class HibernateSessionDao implements SessionDao {

	private EntityManager entityManager;

	public HibernateSessionDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Session> findAll() {
		@SuppressWarnings("unchecked")
		List<Formation> formations = entityManager.createQuery("from Formation").getResultList();
		List<Session> sessions = new ArrayList<>();
		
		for (Formation formation : formations) {
			Session session = new Session();
			session.setIdFormation( formation.getId() );
			session.setNomCours( formation.getCours().getNom() );
			session.setNomFormateur( formation.getFormateur().getNom() );
			for (Stagiaire stagiaire : formation.getStagiaires()) {
				Participant participant = new Participant();
				participant.setId( stagiaire.getId() );
				participant.setNom( stagiaire.getNom() );
				session.getParticipants().add(participant);
			}
			sessions.add(session);
		}
		
		return sessions;
	}

}
