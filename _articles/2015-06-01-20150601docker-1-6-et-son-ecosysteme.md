---
ID: 496
post_title: Docker 1.6 et son écosystème
author: vdemeester
post_date: 2015-06-01 21:47:00
post_excerpt: |
  <p> Le 28 octobre dernier, nous avions parlé de la sortie de Docker 1.3, des évolutions entre la version 1 et cette dernière et de son écosystème. Je vous propose de remettre ça, bientôt 6 mois après, avec un peu le même plan : les principales nouveautés entre la version 1.3 et 1.6 (et il y en a <code>;-)</code>), l'évolution de l'écosystème qui gravite autour et un peu de <em>social</em> avec les meetups et évènements qui se sont passés depuis. </p> <div class="figure"> <p><img src="http://blog.zenika.com/public/Billet_0511/docker_container_engine_logo.png" alt="docker_container_engine_logo.png" /> </p> </div> <p> Rappel <em>ultra</em> rapide, <strong>Docker est une plate-forme ouverte à destination des développeurs et administrateurs systèmes visant à faciliter la construction et le déploiement d'applications distribuées</strong>. De manière moins marketing, l'idée derrière Docker est d'automatiser le déploiement d'environnements sous forme de conteneurs légers, portables et auto-suffisants ; les conteneurs permettant d'isoler l'exécution des applications dans des contextes d'exécution. Pour ce faire, Docker, écrit en Go, reprend les bases de LXC, utilise les fonctionnalités du noyau Linux (CGroups, Namespaces, …) et se base initialement sur un système de fichier "en oignons" AUFS ; D'autres backends sont supportés également comme BTRFS ou devicemapper (LVM). </p>
layout: post
permalink: http://blog.zenika-offres.com/?p=496
published: true
slide_template:
  - default
---
