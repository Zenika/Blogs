package org.springframework.democache.model;

import java.io.Serializable;

public class Reponse implements Serializable{
	
	private Long id;
	private String valeur;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	public Reponse(Long id, String valeur) {
		super();
		this.id = id;
		this.valeur = valeur;
	}
	public Reponse() {
		super();
	}
	
	

}
