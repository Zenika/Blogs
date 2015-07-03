---
ID: 385
post_title: Retour sur le NoSQL matters Paris 2015
author: Gérald Quintana
post_date: 2015-04-01 11:00:00
post_excerpt: |
  <p>La conférence NoSQL matters Paris 2015, dont <a href="http://blog.zenika.com/index.php?post/2015/02/23/Zenika-Conference-Partner-du-NoSQL-matters-Paris-2015">Zenika était partenaire</a> s'est tenue les 26 et 27 mars derniers. Après une première journée consacrée à des ateliers pratiques, la seconde journée était dédiée à des présentations dont vous trouverez un résumé dans cet article.<br /></p> <p><img src="/public/Al/NoSQLmatters/Nosqlmatters.png" alt="Nosqlmatters.png" style="display:block; margin:0 auto;" /></p>
layout: post
permalink: >
  http://blog.zenika-offres.com/retour-sur-le-nosql-matters-paris-2015/
published: true
slide_template:
  - default
---
La conférence NoSQL matters Paris 2015, dont <a href="http://blog.zenika.com/index.php?post/2015/02/23/Zenika-Conference-Partner-du-NoSQL-matters-Paris-2015">Zenika était partenaire</a> s'est tenue les 26 et 27 mars derniers. Après une première journée consacrée à des ateliers pratiques, la seconde journée était dédiée à des présentations dont vous trouverez un résumé dans cet article.

<!--more-->
<h3>NoSQL : The Good, the Bad and the Ugly</h3>
<strong>Par Rob Harrop (Bamboo) <a href="http://t.co/YdtQjmBcmY">Slides</a></strong>

Tout commence par une redéfinition du terme "NoSQL". Puis Rob, revient sur les caractéristiques : modèles de données (K/V, document...), langages de requêtage (JSON, SQL-like, search...), systèmes de stockage et de consistance, modèle d'opérationnel (SAAS). Enfin, il donnera les avantages et les inconvénients des bases NoSQL.

Rob a donné quelques références de personnes à suivre :
<ul>
	<li>Kyle Kingsbury et son <a href="https://aphyr.com/">blog</a> (<a href="http://www.infoq.com/fr/articles/jepsen">cf un article en français sur la résistance au "morcellement" de bases de données</a>)</li>
	<li>Adrian Colyer et son <a href="http://blog.acolyer.org/">morning paper</a></li>
</ul>
<strong>A retenir :</strong> NoSQL se définit finalement par son modèle de données en opposition avec le modèle relationnel. La grande qualité des bases de données NoSQL est leur capacité à innover et l'opportunité pour les développeurs d'apprendre des concepts nouveaux.
<h3>Polyglot Persistence &amp; Multi-Model NoSQL Databases</h3>
<strong>Par Michael Hackstein (ArangoDB) <a href="http://t.co/92EhHjBF9B">Slides</a></strong>

Le point de départ est une application de e-Commerce dans laquelle, si l'on se fie aux préceptes NoSQL, chaque domaine fonctionnel serait adressé par un type de base de données différent (clé/valeur pour le panier, document pour le catalogue...). Être polyglotte devient alors coûteux aussi bien sur le plan développement que opérationnel. C'est là qu'intervient ArangoDB, une base de données multi-modèle : orientée graphe ou document, éventuellement transactionnelle et distribuée (master-slave) et donc très polyvalente.

<strong>A retenir :</strong> le langage de requêtage AQL mêle les concepts des différents modèles et semble très astucieux. Elle inclut une sorte d'ExpressJS nommé Foxx pour exposer les requêtes AQL derrière du REST. A essayer.
<h3>Duid at Criteo</h3>
<strong>Par Grégory Letribot (Criteo) <a href="http://t.co/6We3MVvym2">Slides</a></strong>

Cas d'utilisation de <a href="http://druid.io/">Druid</a> chez Criteo. Druid est un environnement conçu pour des traitements analytiques sur d'énormes quantités de données de type timeseries. Il est construit sur une architecture distribuée permettant d'avoir de la haute-disponibilité et de très bonnes performances (notamment pour des traitements temps-réels). Pour des détails sur l'architecture, cf. ce <a href="http://static.druid.io/docs/druid.pdf">white paper</a>.

<strong>A retenir :</strong> Druid mérite d'être suivi, cela semble très intéressant à mettre en oeuvre dans certains contextes, typés analyses temps réel de grosses quantités de données.
<h3>From SQL to NoSQL in less than 40min</h3>
<strong>Par Tugdual Grall (MongoDB) <a href="http://t.co/iLCboTEUDS">Slides</a></strong>

Il était une fois une vieille application de catalogue produit basée sur MySQL. Chaque type d'article possède des caractéristiques différentes. Le prince MongoDB donna une nouvelle jeunesse à cette application.

<strong>Au final:</strong> le modèle orienté de document de MongoDB est très adapté pour ce modèle de données très hiérarchique (relations 1-N) et à vocation très schémaless. L'écriture des accès MongoDB en NodeJS est très fluide.
<h3>Advanced search for your legacy search</h3>
<strong>Par David Pilato (Elastic) <a href="http://t.co/k1L5EgoBjE">Slides</a></strong>

Le propos est de montrer comment Elasticsearch peut être introduit dans l'architecture d'une application legacy pour compléter l'existant. David Pilato a expliqué que l'approche River doit être oublié pour privilégier une alimentation faite par l'application elle-même qui met à jour les données à la fois dans la base et dans ES.

<strong>A retenir :</strong> Elasticsearch est devenu Elastic. Les rivers ne sont pas encore officiellement abandonnés, elles devraient l'être dans l'année (version 1.6 ou 2.0). Elastic va pousser à l'utilisation de Logstash.
<h3>Hadoop real time for dummies</h3>
<strong>Par Bruno Guedes (Zenika)</strong>

Avant de rentrer dans le vif du sujet, on commence par quelques rappels sur les composants Hadoop HDFS et MapReduce. Ensuite, Bruno présente les composants spécifiques à la distribution Hadoop Pivotal XD : Hawq et PXF. Hawq est un moteur de requêtage semblable à Hive, Pig ou Impala, sauf qu'il s'appuie sur les organes de Postgres (query parser, optimizer...). Utilisé dans les query executor, PXF est un framework qui permet d'abstraire le stockage, Hawq peut ainsi requêter sur HBase, HDFS, Cassandra ou autre.

<strong>A retenir :</strong> inclus à ODP Core (une base commune pour toutes les distributions Hadoop) Hawq est un outil de requêtage capable d'interpréter l'ensemble de la syntaxe SQL92 contrairement à Hive.
<h3>NoSQL Developer Experience</h3>
<strong>Par J. Randall Hunt (Amazon)</strong>

L'objectif est de comparer quelques bases NoSQL (Cassandra, MongoDB et DynamoDB) du point de vue du développeur. Les critères de comparaison sont : pertinence des recherches Google sur la doc, facilité d'installation, écriture de 3-4 requêtes classiques et les courbes de tendances (Google, StackOverflow...).

<strong>A retenir :</strong> moins connue que ses consœurs Cassandra et MongoDB, DynamoDB s'en sort remarquablement bien sur tous les plans. Le match était-il truqué?
<h3>Make any app realtime with Firebase</h3>
<strong>Par Sara Robinson (Firebase) <a href="https://slidee.firebaseapp.com/presentations/nosql-matters">Slides</a></strong> <a href="https://nosql-matters.firebaseio-demo.com/">La base de données utilisées pour la démo</a>

Pour commencer, Sara présente les fonctionnalités clef de Firebase : push des données du serveur vers les terminaux mobiles ou web et synchronisation au sens large, modèle de données arborescent, sécurité... Puis elle attaque le live-coding d'une application de chat à laquelle tout le monde peut se connecter. Cette démo rudement bien maîtrisée tombe pile dans le cas d'usage des applications de collaboration qui siéent si bien à Firebase.

A retenir : les fonctionnalités de cette base de données 100% cloud sont épatantes, même si le périmètre d'utilisation est peut-être limité. On sent au travers de la démo tout le soin qui a été apporté dans l'outillage pour rendre le développeur productif.
<h3>Back to the future SQL 92 for Elasticsearch ?</h3>
<strong>Par Lucian Precup (Adelean) <a href="http://t.co/SbLQVkR2Cc">Slides</a></strong>

Non, il n'existe pas de plugin SQL pour Elasticsearch. Une spécification SQL-92 commune à tous les produits contre maintes façons de requêter : Lucien se livre à la comparaison SQL contre NoSQL de la mécanique utilisée pour rechercher de données (parsing, optimisation, routage, exécution). Puis il tente de répondre à la question, comment traduire dans le monde Elasticsearch tel ou tel concept relationnel : pagination, types numériques, fonctions (concat, substring par exemple), agrégations (group by et having), jointures.

<strong>A retenir :</strong> les base de données NoSQL requièrent plus de travail au niveau applicatif pour traiter les données en entrée et en sortie. Lucian résume sa vision par :
<ul>
	<li>SQL/RDBMS -&gt; Power to the DBA</li>
	<li>NoSQL -&gt; Power to the developer (complété avec "with great power comes great responsibility" !)</li>
</ul>
<h3>Using Riak for Events storage and analysis</h3>
<strong>Par Damien Krochine (Booking.com)</strong>

Il s'agit d'un retour d'expérience sur la collecte et le stockage d'événements. Un événement est une structure de données schemaless horodatées émis par différent sous-systèmes fonctionnels (mobile, web...) ou techniques (cache, load balancer...).
Le volume encaissé par Riak avec brio donne le vertige : 15000 événements/s, 1,25 milliards événements/j, 100Go/h sur 16 nœuds. L'équipe a poussé l'utilisation de Riak assez loin : écriture de jobs MapReduce ou post-commit hook en Erlang...

<strong>A retenir :</strong> l'architecture distribuée et master-less de Riak lui permet de traiter l'afflux massif d'événements tout en conservant de bonnes performances en lecture. Riak en un mot : "robustesse".

<em>Article co-écrit par Bruno Bonnin et Gérald Quintana</em>.