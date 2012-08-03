package com.zenika.blog.mybatis;

import java.util.Objects;

public class Participant {
	private Long id = null;
	private String nom = null;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nom);
	}

	@Override
	public boolean equals(Object obj) {
		if ( !Participant.class.isInstance(obj) ) {
			return false;
		}
		Participant other = (Participant) obj;
		return Objects.equals(id, other.id) && Objects.equals(nom, other.nom);
	}

	@Override
	public String toString() {
		return id + ":" + nom;
	}
}
