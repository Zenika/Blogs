package com.zenika.blog.mybatis;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Session {
	private Long idFormation = null;
	private String nomCours = null;
	private String nomFormateur = null;
	private Set<Participant> participants = new HashSet<>();
	
	public Long getIdFormation() {
		return idFormation;
	}

	public void setIdFormation(Long idFormation) {
		this.idFormation = idFormation;
	}

	public String getNomCours() {
		return nomCours;
	}

	public void setNomCours(String nomCours) {
		this.nomCours = nomCours;
	}

	public String getNomFormateur() {
		return nomFormateur;
	}
	
	public void setNomFormateur(String nomFormateur) {
		this.nomFormateur = nomFormateur;
	}

	public Set<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<Participant> participants) {
		this.participants = participants;
	}

	@Override
	public int hashCode() {
		return  Objects.hash(idFormation, nomCours, nomFormateur, participants);
	}

	@Override
	public boolean equals(Object obj) {
		if ( !Session.class.isInstance(obj) ) {
			return false;
		}
		Session other = (Session) obj;
		return Objects.equals(idFormation, other.idFormation)
				&& Objects.equals(nomCours, other.nomCours)
				&& Objects.equals(nomFormateur, other.nomFormateur)
				&& Objects.equals(participants, other.participants);
	}

	@Override
	public String toString() {
		return MessageFormat.format("Session {0} animée par {1} pour {2}", nomCours, nomFormateur, participants);
	}
}