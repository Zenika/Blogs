package com.zenika.blog.mybatis.impl.jpa;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="formation")
public class Formation {
	
	@Id
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_cours")
	private Cours cours;

	@ManyToOne
	@JoinColumn(name="id_formateur")
	private Formateur formateur;
	
	@Column(name="date_debut")
	private Date dateDebut;
	
	@OneToMany
	@JoinColumn(name="id_formation")
	private Set<Stagiaire> stagiaires;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}

	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Set<Stagiaire> getStagiaires() {
		return stagiaires;
	}

	public void setStagiaires(Set<Stagiaire> stagiaires) {
		this.stagiaires = stagiaires;
	}
}
