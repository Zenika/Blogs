---
ID: 69
post_title: Les ressources cachées des Properties
author: ohuber
post_date: 2009-02-09 14:00:00
post_excerpt: |
  <p>En ouvrant le fichier ".properties" fourni par défaut avec Hibernate, j'ai été surpris par la syntaxe suivante&nbsp;:</p> <pre> hibernate.query.substitutions yes 'Y', no 'N' </pre> <p>En effet, j'avais toujours renseigné mes fichiers de propriétés en java, que ce soit en Hibernate ou autre, de la forme "standard"&nbsp;:</p> <pre> log4j.appender.stdout=org.apache.log4j.ConsoleAppender </pre>
layout: post
permalink: http://blog.zenika-offres.com/?p=69
published: true
---
<p>En ouvrant le fichier ".properties" fourni par défaut avec Hibernate, j'ai été surpris par la syntaxe suivante&nbsp;:</p> <pre> hibernate.query.substitutions yes 'Y', no 'N' </pre> <p>En effet, j'avais toujours renseigné mes fichiers de propriétés en java, que ce soit en Hibernate ou autre, de la forme "standard"&nbsp;:</p> <pre> log4j.appender.stdout=org.apache.log4j.ConsoleAppender </pre>
<!--more-->
<p>Pensant que ces fichiers étaient lus par Hibernate avec son propre <em>parser</em> j'ai tout de même vérifié dans la javadoc ce qu'il en était.<br />
La réponse se trouve dans <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/util/Properties.html#load(java.io.InputStream)">la méthode load de la classe java.util.Properties</a></p> <p>La javadoc nous apprend qu'il est donc est effectivement possible, en plus du séparateur =, d'avoir ceci&nbsp;:</p> <pre> clef             valeur </pre> <p>le deux-points ":" est également accepté</p> <pre> clef:valeur clef                  :valeur </pre> <p>(A titre de comparaison, certains langages de sérialisation de données utilisent exclusivement le deux-points pour le couple clef:valeur comme <a href="http://www.yaml.org/start.html">YAML (YAML Ain't Markup Language)</a>)</p> <p>Alors, lequel de ces séparateurs utiliser&nbsp;? Est-ce une simple questions d'habitude, ou de lisibilité ?<br />
Les fichiers de configuration font partie du code, qui reste la première source de documentation pour le développeur.<br />
A votre avis, laquelle de ces lignes est la plus lisible&nbsp;?</p> <pre> hibernate.query.substitutions yes 'Y', no 'N' hibernate.dialect  : org.hibernate.dialect.IngresDialect hibernate.connection.driver_class = com.ingres.jdbc.IngresDriver </pre> <p>Le format des fichiers Properties possède également d'autres particularités.</p> <p>Par exemple, il est possible d'utiliser les caractères "espace", ":" ou même "=" au sein de la clef&nbsp;: il suffit alors de les échapper avec un antislash ""</p> <pre> cl:=ef = valeur </pre> <p>Et la ligne suivante&nbsp;:</p> <pre> clef </pre> <p>associe la chaine vide "" à "clef"</p> <p>Il est également possible de répartir la valeur sur plusieurs lignes. Il suffit pour cela de placer un "" en bout de ligne&nbsp;:</p> <pre> fruits                          apple, banana, pear,                                   cantaloupe, watermelon,                                   kiwi, mango </pre> <p>Force est de constater que même une classe aussi anodine et bien connu (...ou pas) que Properties recèle bien des surprises.</p>