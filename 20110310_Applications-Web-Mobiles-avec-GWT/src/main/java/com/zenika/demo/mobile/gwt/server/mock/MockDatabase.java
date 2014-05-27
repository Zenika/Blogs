package com.zenika.demo.mobile.gwt.server.mock;

import java.util.ArrayList;
import java.util.List;

import com.zenika.demo.mobile.gwt.shared.Conference;
import com.zenika.demo.mobile.gwt.shared.Expert;
import com.zenika.demo.mobile.gwt.shared.Formation;

public class MockDatabase {

	private static MockDatabase instance;

	private List<Formation> formations;

	private List<Conference> conferences;

	private List<Expert> experts;

	private MockDatabase() {
		init();
	}

	private void init() {

		experts = new ArrayList<Expert>();

		Expert e1 = new Expert();
		e1.setDescription("Arnaud Cogoluègnes est architecte Java EE et expert Spring. Il dispense pour SpringSource les formations Core Spring et Enterprise Integration. Arnaud est co-auteur de la seconde édition de Spring par la Pratique Spring, de Spring Dynamic Modules in Action et travaille actuellement sur le livre Spring Batch in Action.");
		e1.setName("Arnaud Cogoluègnes");

		Expert e2 = new Expert();
		e2.setDescription("Grégory Boissinot est un spécialiste des outils de build et des problématiques d'intégration continue qu'il a mis en place à grande échelle. Grégory possède une expérience industrielle grand compte de ces problématiques et contribue activement à la communauté Open Source dans ce domaine en étant l'un des principaux commiteurs Hudson mondiaux.");
		e2.setName("Grégory Boissinot");

		Expert e3 = new Expert();
		e3.setDescription("Olivier Croisier est expert Java / Spring / Terracotta / Web. Il est certifié Java 5.0 avec 100%, certifié Spring 2.5 avec 86% et est formateur certifié Terracotta. Il est l'auteur du blog The Coder's Breakfast. De plus Olivier a publié plus de 200 articles sur différents blogs techniques et dans la presse spécialisée.");
		e3.setName("Olivier Croisier");

		experts.add(e1);
		experts.add(e2);
		experts.add(e3);

		formations = new ArrayList<Formation>();

		Formation f1 = new Formation();
		f1.setDate("25 Janvier 2011");
		f1.setDescription("Spring Intégration en entreprise est un cours de 4 jours de formation dirigé par des ingénieurs SpringSource couvrant des sujets et des technologies les plus importants d'aujourd'hui liées à l'intégration d'applications d'entreprise. Cette formation pratique enseigne comment appliquer Spring pour résoudre des problèmes d'intégration que les développeurs rencontrent côté serveur.");
		f1.setDuration(4);
		f1.setFormateur(e1);
		f1.setPlace("Lyon");
		f1.setSubtitle("Résoudre les problématiques d'intégration d'entreprise avec Spring");
		f1.setTitle("Enterprise Integration avec Spring");

		Formation f2 = new Formation();
		f2.setDate("12 Janvier 2011");
		f2.setDescription("Zenika débute sur la technologie GWT (Google Web Toolkit) une semaine après l'annonce de mise à disposition du produit en avril 2006. L'approche totalement novatrice de ce Framework ainsi que son intégration au sein d'une application JEE utilisant Spring/Hibernate ont pu être validés sur des projets d'envergure. Nous participons à la communauté Open Source en fournissant certains composants en licence LGPL : ZenBinding et un Date Picker");
		f2.setDuration(3);
		f2.setFormateur(null);
		f2.setPlace("Paris");
		f2.setSubtitle("Développement d'applications riches avec Google Web Toolkit (GWT)");
		f2.setTitle("Google Web Toolkit (GWT)");

		Formation f3 = new Formation();
		f3.setDate("07 février");
		f3.setDescription("La Formation Intensive pour Spécialistes Java est mise au point et animée par Heinz Kabutz, auteur de la newsletter The Java Specialists. Elle incorpore (sous licence) des éléments pédagogiques fournis par JavaPerformanceTuning. \"Cette formation concentre toute mon expertise Java et l'expérience acquise grâce à la publication de plus de 170 articles spécialisés, l'animation de centaines de séminaires et bien sûr l'écriture de centaines de milliers de lignes de code Java. Heinz Kabutz, auteur de la newsletter \"The Java Specialists\" Au cours des 4 jours de la formation, nous étudierons plus de 1000 slides couvrant les sujets les plus avancés. A la fin de chaque section, des exercices pratiques permettront la mise pratique des notions étudiées. Même le programmeur Java le plus aguerri y trouvera un certain challenge !");
		f3.setDuration(4);
		f3.setFormateur(e3);
		f3.setPlace("Paris");
		f3.setSubtitle("Le complément indispensable au programmeur Java professionnel");
		f3.setTitle("Java Spécialiste");

		formations.add(f1);
		formations.add(f2);
		formations.add(f3);

		conferences = new ArrayList<Conference>();

		Conference c1 = new Conference();
		c1.setDate("Jeudi 9 décembre 2010");
		c1.setDescription("This presentation leads the audience through the minefield of concurrent and distributed computing starting with the basics of Java concurrency, and ending with global patterns for distributed applications. The basic principles of design for such applications are explored and it is shown how using various features of Spring (e.g. task management, scheduling, POJO adapters) can take the pain out of implementing them in many cases.");
		c1.setEnded(false);
		c1.setSpeakerName("Dr David Syer");
		c1.setTitle("Concurrent programming and distributed applications with Spring");

		Conference c2 = new Conference();
		c2.setDate("Lundi 8 novembre 2010");
		c2.setDescription("L'Application Cache est une nouvelle fonctionnalité HTML 5 permettant la création d'application hors ligne ainsi que le préchargement de ressources. Venez en apprendre davantage sur cette passionnante fonctionnalité...");
		c2.setEnded(false);
		c2.setSpeakerName("Peter Lubbers");
		c2.setTitle("Tout ce que vous avez toujours voulu savoir sur les applications Web hors ligne HTML 5");

		Conference c3 = new Conference();
		c3.setDate("Mardi 23 Novembre 2010");
		c3.setDescription("Jason Weathersby vous présentera en premier lieu les éléments qui composent le projet BIRT et vous montrera comment utiliser BIRT designer, afin de créer des rapports intéractifs de haute qualité, puis se concentrera sur les nouvelles fonctionnalités BIRT disponibles dans la version Helios d'Eclipse");
		c3.setEnded(true);
		c3.setSpeakerName("Jason Weathersby");
		c3.setTitle("BIRT 2.6 par Jason Weathersby");

		conferences.add(c1);
		conferences.add(c2);
		conferences.add(c3);
	}

	public static synchronized MockDatabase getInstance() {
		if (instance == null) {
			instance = new MockDatabase();
		}
		return instance;
	}

	public List<Formation> getAllFormations() {
		return formations;
	}

	public List<Expert> getAllExperts() {
		return experts;
	}

	public List<Conference> getAllConferences() {
		return conferences;
	}
}
