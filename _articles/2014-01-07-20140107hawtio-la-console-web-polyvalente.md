---
ID: 199
post_title: HawtIO, la console web polyvalente
author: Gérald Quintana
post_date: 2014-01-07 10:00:00
post_excerpt: |
  <p><a href="http://hawt.io">HawtIO</a> est développé chez RedHat par l'équipe qui a produit ActiveMQ, Camel, ServiceMix, Karaf, etc. <br />
  Ce projet est probablement né de la volonté d'avoir une console web commune pour administrer et monitorer tous ces produits. Mais finalement HawtIO va plus loin que ça et permet de monitorer n'importe quelle type d'application ou de serveur.</p>
layout: post
permalink: http://blog.zenika-offres.com/?p=199
published: true
slide_template:
  - default
---
<a href="http://hawt.io">HawtIO</a> est développé chez RedHat par l'équipe qui a produit ActiveMQ, Camel, ServiceMix, Karaf, etc.
Ce projet est probablement né de la volonté d'avoir une console web commune pour administrer et monitorer tous ces produits. Mais finalement HawtIO va plus loin que ça et permet de monitorer n'importe quelle type d'application ou de serveur.

<!--more-->

HawtIO vient de souffler sa première bougie et de sortir une nouvelle version 1.2: c'est l'occasion idéale de présenter l'outil.
<h3>Utilisation</h3>
<h2>Quelques exemples</h2>
<a title="HawtIO JVM" href="/wp-content/uploads/2015/07/hawtio-jvm.png"><img style="float: left; margin: 0 1em 1em 0;" title="HawtIO JVM" src="/wp-content/uploads/2015/07/.hawtio-jvm_t.jpg" alt="HawtIO JVM" /></a> Il n'y a pas de console d'administration sans affichage des métriques classiques de la JVM: occupation de la heap, nombre de threads, charge CPU... HawtIO ne déroge pas à la règle

<a title="HawtIO ActiveMQ" href="/wp-content/uploads/2015/07/hawtio-activemq.png"><img style="float: right; margin: 0 0 1em 1em;" title="HawtIO ActiveMQ" src="/wp-content/uploads/2015/07/.hawtio-activemq_t.jpg" alt="HawtIO ActiveMQ" /></a> Depuis la dernière version 5.9 d'ActiveMQ, HawtIO est intégré dans le broker JMS/AMQP/STOMP et permet d'administrer les queues: envoyer des messages, monitorer leur remplissage, rejouer les messages des DLQ (Dead Letter Queues)...

Dès que l'outil aura gagné en maturité, il est fort probable que l'ancienne console disparaîtra. Cette <a href="http://vimeo.com/74332231" hreflang="en">Vidéo</a> montre l'utilisation d'HawtIO pour administrer ActiveMQ.

<a title="HawtIO Camel" href="/wp-content/uploads/2015/07/hawtio-camel.png"><img style="float: left; margin: 0 1em 1em 0;" title="HawtIO Camel" src="/wp-content/uploads/2015/07/.hawtio-camel_t.jpg" alt="HawtIO JVM" /></a> Jusqu'ici, pour administrer Apache Camel, il fallait passer par JMX. Aujourd'hui, HawtIO permet de démarrer/arrêter les routes, monitorer les flux de communication depuis un navigateur. Il est même possible d'avoir un représentation graphique des routes voire, sur ServiceMix, de les modifier.

<a title="HawtIO Tomcat" href="/wp-content/uploads/2015/07/hawtio-tomcat.png"><img style="float: right; margin: 0 0 1em 1em;" title="HawtIO Tomcat" src="/wp-content/uploads/2015/07/.hawtio-tomcat_t.jpg" alt="HawtIO Tomcat" /></a> Pour Apache Tomcat, on peut piloter les applications déployées, observer les sessions HTTP. Je regrette qu'il manque le monitoring des DataSources, mais c'est un bon début... et peut-être la fin du manager actuel?

Pour aller plus loin, HawtIO s'intègre aussi avec:
<ul>
	<li>ElasticSearch pour monitorer les indexes,</li>
	<li>Kibana 3 pour fouiller dans ses logs,</li>
	<li>Maven et Nexus pour retrouver les sources,</li>
	<li>Jetty, JBoss/WildFly pour faire comme Tomcat</li>
	<li>Infinispan, pour visualiser les caches</li>
	<li>Quartz pour planifier des tâches</li>
	<li>Etc.</li>
</ul>
<h2>Tableau de bord personnalisé</h2>
L'outil permet de se constituer ses propres tableaux de bord. Voici, par exemple la recette pour tracer le graphique d'une ou plusieurs métriques JMX dans un écran dédié:
<ol>
	<li>Ajouter un écran: Dans l'onglet Dashboard,
<ol>
	<li>cliquer sur Manage,</li>
	<li>puis sur Create</li>
</ol>
</li>
	<li>Ajouter les métriques: Dans l'onglet JMX,
<ol>
	<li>naviguer dans l'arbre JMX et chercher le MBean comme dans VisualVM ou JConsole</li>
	<li>cliquer sur Chart, puis Edit Chart</li>
	<li>sélectionner les propriétés intéressantes</li>
	<li>cliquer sur la flèche (Add this view to Dashboard)</li>
</ol>
</li>
	<li>De retour dans l'onglet Dashboard,
<ol>
	<li>selectionner l'écran cible</li>
	<li>cliquer cliquer sur Add View to Dashboard</li>
</ol>
</li>
</ol>
On peut aussi ramener dans un écran les attributs ou les opérations d'un MBean et se constituer ainsi un portfolio.
<h3>Configuration</h3>
<h2>Anatomie du projet</h2>
<img style="float: left; margin: 0 1em 1em 0;" title="HawtIO Structure" src="/wp-content/uploads/2015/07/hawtio-struct.png" alt="HawtIO Structure" /> Au commencement, il y avait des MBeans JMX exposés au niveau JVM, un MBean est constitué de propriétés (métriques, informations, paramétrage) et des opérations (démarrer, arrêter). Ces MBeans sont rendus accessibles sur HTTP grâce à la librairie <a href="http://jolokia.org">Jolokia</a>. Autrement dit une API REST permet d'accéder aux métriques présentées dans JMX par les outils comme la JVM, ActiveMQ ou Camel.

Pour l'affichage, le frontend est développé dans l'écosystème Angular JS (Grunt, Angular UI, Twitter Bootstrap, D3 JS...). Il accède naturellement au backend par l'API REST Jolokia. L'essentiel du code est écrit en TypeScript, un espèce de CoffeeScript made in Microsoft, mais ce n'est pas imposé.

Il est possible de sauvegarder la configuration des Dashboards côté backend. Les modifications peuvent même être historisées dans un référentiel Git (JGit pour être précis). Le même principe de sauvegarde s'applique aux routes Camel déployées dans JBoss Fuse et à l'aide en ligne stockée sous forme de Wiki.

HawtIO intègre un proxy HTTP qui permet d'accéder à des métriques situées sur d'autres serveurs. Evidemment, il faudra que les serveurs cibles exposent leurs MBean sur HTTP via Jolokia.

L'application est décomposée en un ensemble de <a href="http://hawt.io/plugins/index.html">plugins</a> (environ un par technologie serveur) qui s'enrichit rapidement: JBoss, ElasticSearch, Wiki... Les plugins côté frontend s'activent ou non, en fonction de la présence ou non des MBeans appropriés dans le backend.
<h2>Lancement</h2>
HawtIO se présente sous plusieurs formes. Pour commencer, le plus simple est une application autoportée qui démarre ainsi:
<pre> 	java -jar hawtio-app-1.2.1.jar</pre>
Il y a aussi un plugin Maven qui pourra venir épauler le plugin Maven pour Camel par exemple:
<pre> 	mvn io.hawt:hawtio-maven-plugin:1.2.1:camel</pre>
Si vous utilisez l'ESB Apache ServiceMix, ou sa variante JBoss Fuse, il faudra installer une feature Karaf:
<pre> 	features:addurl mvn:io.hawt/hawtio-karaf/1.2.1/xml/features 	features:install hawtio</pre>
Au final, toutes ces méthodes de lancement ont pour objectif d'amener un Jetty à exécuter un War. Cette application pourra être déployée dans n'importe quel conteneur de Servlet 2.4 ou plus.
<h2>Personnalisation</h2>
Cette application web (War) est personnalisable. Pour cela, on crée une nouvelle application Web JEE avec <a href="https://github.com/hawtio/hawtio/blob/master/sample/pom.xml">Maven</a>, et on s'appuie sur les War Overlays:
<pre class="xml code xml" style="font-family: inherit;"><span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;artifactId<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>hawtio-custom<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/artifactId<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;packaging<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>war<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/packaging<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>1.0.0-SNAPSHOT<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;dependencies<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 		<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;dependency<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 			<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;groupId<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>io.hawt<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/groupId<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>
	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;artifactId<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>hawtio-default<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/artifactId<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 			<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;version<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>1.2.0<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/version<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 			<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;type<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>war<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/type<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 		<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/dependency<span style="color: #000000; font-weight: bold;">&gt;</span></span></span></pre>
A partir de là, on a carte blanche pour ajouter du code aussi bien côté backend Java que côté frontend JS.
<h2>Sécurisation</h2>
Pour sécuriser l'accès à l'application, il y a plusieurs façons de procéder mais au final toutes passent par la dérivation de l'application web et la personnalisation du web.xml.

Dans la sphère Apache ActiveMQ/ServiceMix, la personnalisation de HawtIO utilise un filtre et des servlets:
<pre class="xml code xml" style="font-family: inherit;"><span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;filter<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>       <span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;filter-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>AuthenticationFilter<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/filter-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>       <span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;filter-class<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>io.hawt.web.AuthenticationFilter<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/filter-class<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>     <span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/filter<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;servlet<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	  <span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;servlet-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>login<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/servlet-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	  <span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;servlet-class<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>io.hawt.web.LoginServlet<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/servlet-class<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/servlet<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;servlet<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	  <span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;servlet-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>logout<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/servlet-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	  <span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;servlet-class<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>io.hawt.web.LogoutServlet<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/servlet-class<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/servlet<span style="color: #000000; font-weight: bold;">&gt;</span></span></span></pre>
Ces classes Java s'appuient sur la mécanique JAAS de Realm/LoginModule qui sont configurées dans un fichier login.config...
<pre> 	activemq { 		org.apache.activemq.jaas.PropertiesLoginModule required 			org.apache.activemq.jaas.properties.user="users.properties" 			org.apache.activemq.jaas.properties.group="groups.properties"; 	};</pre>
... ainsi que quelques propriétés d'environnement
<pre> 	-Djava.security.auth.login.config=$ACTIVEMQ_CONF/login.config 	-Dhawtio.realm=activemq 	-Dhawtio.role=admins 	-Dhawtio.rolePrincipalClasses=org.apache.activemq.jaas.GroupPrincipal</pre>
On aurait pu choisir une autre implémentation de LoginModule comme [celles incluses à Java|http://docs.oracle.com/javase/7/docs/jre/api/security/jaas/spec/com/sun/security/auth/module/package-summary.html].

Un autre façon de faire, plus traditionnelle est de s'appuyer sur les capacités du conteneur JEE, on ajoute quelques restrictions d'accès dans le <code>web.xml</code>:
<pre class="xml code xml" style="font-family: inherit;"><span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;security-constraint<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 		<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;web-resource-collection<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 			<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;web-resource-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>hawtio<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/web-resource-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 			<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;url-pattern<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>/auth/*<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/url-pattern<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 			<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;url-pattern<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>/jolokia/*<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/url-pattern<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 			<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;url-pattern<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>/upload/*<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/url-pattern<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 		<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/web-resource-collection<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 		<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;auth-constraint<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 			<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;role-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>hawtio<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/role-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 		<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/auth-constraint<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/security-constraint<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;security-role<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 		<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;role-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>hawtio<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/role-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/security-role<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;login-config<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 		<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;auth-method<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>BASIC<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/auth-method<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 		<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;realm-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span>hawtio<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/realm-name<span style="color: #000000; font-weight: bold;">&gt;</span></span></span> 	<span style="color: #009900;"><span style="color: #000000; font-weight: bold;">&lt;/login-config<span style="color: #000000; font-weight: bold;">&gt;</span></span></span></pre>
<h3>Conclusion</h3>
Plus qu'un outil de monitoring, HawtIO est un framework léger pour construire sa propre application de surveillance. Pour l'heure, il manque à mon humble avis quelques éléments essentiels comme l'agrégation temporelle (pour une analyse post mortem par exemple), l'agrégation horizontale (tous les noeuds d'un cluster) et l'agrégation verticale (quelques métriques système). Le fait que cet outil, certes encore jeune, soit extensible et basé sur les technologies courantes en 2013 (JMX, Rest, Angular JS, Git) devrait lui assurer un bel avenir.

Quelques exemples d'utilisation:
<ul>
	<li><a href="http://vimeo.com/68442425" hreflang="en">Vidéo HawtIO dans le Cloud</a></li>
	<li><a href="http://vimeo.com/80625940" hreflang="en">Vidéo HawtIO sur JBoss Fuse</a></li>
</ul>