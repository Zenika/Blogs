create table formateur(
	id number(10) not null,
	nom varchar(50) not null,
	constraint id_formateur_pk primary key(id)
);

create table cours(
	id number(10) not null,
	nom varchar(50) not null,
	duree number(1) not null,
	constraint id_cours_pk primary key(id),
);

create table formation(
	id number(10) not null,
	date_debut date not null,
	id_formateur number(10) not null,
	id_cours number(10) not null,
	constraint id_formation_pk primary key(id),
	constraint id_formateur_fk foreign key(id_formateur) references formateur(id),
	constraint id_cours_fk foreign key(id_cours) references cours(id)
);

create table stagiaire(
	id number(10) not null,
	nom varchar(50) not null,
	id_formation number(10) not null,
	constraint id_stagiaire_pk primary key(id),
	constraint id_formation_fk foreign key(id_formation) references formation(id)
);

