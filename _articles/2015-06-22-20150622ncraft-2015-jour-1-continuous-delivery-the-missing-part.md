---
ID: 502
post_title: 'NCraft 2015, jour 1 : Continuous delivery : the missing parts'
author: lclaisse
post_date: 2015-06-22 14:00:00
post_excerpt: |
  <p>En tant qu'acteur du mouvement Software Craftsmanship, Zenika se devait d'être présent à la conférence NCrafts 2015. Nous ne l'avons pas regretté, les interventions étaient vraiment d'excellente qualité&nbsp;! Nous proposons un compte-rendu détaillé à ceux qui n'ont pas eu la chance de s'y rendre.</p> <p><img src="/public/_Claisse/ncraft2015/nscrafts2015.png" alt="nscrafts2015.png" style="display:block; margin:0 auto;" /></p>
layout: post
permalink: http://blog.zenika-offres.com/?p=502
published: true
slide_template:
  - default
---
En tant qu'acteur du mouvement Software Craftsmanship, Zenika se devait d'être présent à la conférence NCrafts 2015. Nous ne l'avons pas regretté, les interventions étaient vraiment d'excellente qualité ! Nous proposons un compte-rendu détaillé à ceux qui n'ont pas eu la chance de s'y rendre.

<!--more-->
<h3><img class=" aligncenter" src="/wp-content/uploads/2015/07/nscrafts2015.png" alt="nscrafts2015.png" /></h3>
<h3>Continuous delivery : the missing parts</h3>
Paul Stack voulait montrer qu'il ne suffit pas de déployer en prod depuis Jenkins pour dire qu'on "fait du Continuous delivery". Il présente le contexte du CD en montrant ses liens avec d'autres méthodologies modernes, puis liste les briques permettant d'atteindre une méthode cohérente avec les objectifs de ces méthodologies.

De prime abord, le CD se caractérise par la volonté de builder, tester, et déployer plus fréquemment pour raccourcir les boucles de feedback. Mais en fait cet objectif est déjà exprimé par le mouvement Agile dont le manifeste préconise des livraisons précoces et fréquentes. Cette définition est donc insuffisante pour caractériser le CD.
<h4>Rappel des principes du CD</h4>
Rappelons quand même les principes du CD; plus précisément, le CD est défini par 8 principes :

<img src="/wp-content/uploads/2015/07/cd-whats-missing.png" alt="cd-whats-missing" />
<ul>
	<li>1 : Le cycle de déploiement doit être reproductible, il doit être un non-événement.</li>
	<li>2 : Corollaire, il doit être complètement automatisé. Un déploiement manuel ne satisfait en effet jamais la condition 1.</li>
	<li>3 : Il doit être fréquent : les points de douleur du cycle de développement doivent être répétés et non évités, à nouveau afin qu'ils soient des non-événements.</li>
	<li>4 : Tout (y compris les scripts de lancement, de migration,..) doit être versionné.</li>
	<li>5 : La "définition de Done" au sens de Agile doit inclure le déploiement. Sinon les mauvaises surprises vont se manifester...</li>
	<li>6 : Le projet doit inclure des métriques de qualité calculées automatiquement. Si la politique du chiffre est une plaie des grandes organisations, l'utilisation raisonnée des métriques permet de détecter rapidement des régressions de qualité.</li>
	<li>7 : Le déploiement doit être une préoccupation partagée. Le déploiement n'est pas la patate chaude à laisser aux opérationnels, que développeurs/fonctionnels/managers considèrent comme quantité négligeable et acquise. Il est un point crucial du cycle de développement sans lequel le code ne produit aucune valeur.</li>
	<li>8 : L'amélioration continue. Un bon processus de déploiement permet de révéler des axes d'amélioration de l'application, en particulier s'il inclut des métriques et du monitoring.</li>
</ul>
et par 4 pratiques:

<img src="/wp-content/uploads/2015/07/cd-whats-missing-2.png" alt="cd-whats-missing-2" />
<ul>
	<li>1 : Builder les binaires une seule fois : on a tous vécu le cas du binaire de prod subtilement différent de celui des autres environnements, ce qui invalide complètement les validations faites dans les environnements de qualification.</li>
	<li>2 : Utiliser le même mécanisme de déploiement dans tous les environnements.</li>
	<li>3 : Faire un smoke test manuel du déploiement automatique. Un smoke test est un test rudimentaire et rapide consistant à mettre en marche un équipement pour s'assurer qu'au moins il ne dégage pas de fumée.</li>
	<li>4 : Tout échec doit entraîner l'interruption du pipeline de build. Il n'existe pas d'"erreurs normales" pour un build, sinon l'automatisation perd toute valeur car elle ne garantit plus rien.</li>
</ul>
<h4>Le CD en rapport avec les autres méthodologies modernes</h4>
On a déjà évoqué le recouvrement des préconisations CD/Agile. Il existe aussi des liens avec les méthodes de management comme Lean : un des enseignements essentiels de ce dernier est que le blocage du Value Stream dû à l'attente entre les phases de la production d'une modification logicielle constitue une énorme perte (4 jours minimum entre le codage/QA/.../release).
<h4>Idées reçues sur le CD</h4>
Pourtant, bien que les préconisations du CD soient comme on l'a vu bien cohérentes avec celles d'autres méthodes modernes, le CD continue d'être parfois victime de malentendus, dont parmi les plus fréquents :
<ul>
	<li>"Le CD, ça marche que pour les startups." Netflix n'est pas vraiment une startup, et leur CD est l'un des plus impitoyables.</li>
	<li>"Il suffit de se payer un consultant CD." Le processus de CD est unique à chaque fois, sa mise au point nécessite une compréhension des particularités de chaque entreprise.</li>
	<li>"Il suffit de se payer un outil." Les outils de CD sont aussi nombreux que les étoiles, beaucoup prétendant être la pierre philosophale.</li>
	<li><strong>ET SURTOUT</strong> "Le CD c'est de déployer de Jenkins vers Docker/de Github vers Azure". La dernière partie explique en détail ce qui manque à cette description trop simpliste.</li>
</ul>
<h4>Le CD au sens plein</h4>
Maintenant, voyons quelles sont concrètement les briques permettant d'arriver à un CD au sens plein.
<ul>
	<li>1 : Infrastructure immutable: les serveurs de productions doivent être traités comme du bétail et non comme des animaux de compagnie. Autrement dit, un serveur est un serveur ; il ne doit jamais nécessiter d'avoir reçu un traitement particulier (manuel, à posteriori de l'installation automatique) pour remplir son office. Un autre nom encore plus brutal de ce pattern est l'infrastructure jetable : on ne doit pas craindre d'"abattre" un serveur, puisqu'on sait qu'il pourra être remplacé par un autre strictement équivalent. Si cette condition n'est pas remplie, on ne peut plus avoir confiance dans les processus automatisés, et le disaster recovery sera impossible.</li>
	<li>2 : Gestion de configuration : il existe depuis quelques années de nombreux outils permettant de simplifier et de standardiser l'automatisation de l'installation des serveurs. En utilisant une approche déclarative plutôt qu'impérative : avec Puppet/Chef/Ansible/.. on déclare la configuration à laquelle on veut aboutir, et non les commandes concrètes à exécuter. Cette approche rend l'automatisation beaucoup plus maintenable et versionnable.</li>
	<li>3 : Tout logger : les logs de productions permettent d'extraire des informations précieuses sur le fonctionnement technique et l'utilisation des fonctionnalités de l'application. Ceci permet au côté Ops de fournir un feedback au côté Dev de l'application.</li>
	<li>4 : Relever des métriques : dans la même optique il est très utile de fournir des relevés d'utilisation CPU/mémoire/IO des différents serveurs, par exemple pour que les Devs sachent qu'un seul des nœuds est en réalité exploité par le programme. Ce point et le précédent montrent que l'approche CD doit être intégrée aux processus Agile et Devops.</li>
	<li>5 : Orchestration : les mises à jour de sécurité et autres interventions devant être effectuées sur plusieurs serveurs doivent être automatisées par un outil d'orchestration.</li>
	<li>6 : "Data center as code": des outils tels que Terraform permettent, toujours de façon déclarative (DSL), d'étendre l'approche "Configuration as code" à tout un data center.</li>
	<li>7 : Disaster recovery : le principe est encore de répéter les opérations douloureuses plutôt que de les éviter, seul moyen d'acquérir une confiance fondée dans la capacité à surmonter un incident majeur. On peut s'inspirer de la méthode Netflix du "Simian Army" consistant à perturber de façon aléatoire l'infrastructure de prod au moyen d'agents d'agressivité croissante (du "chaos monkey" qui éteint un serveur, au "chaos gorilla" qui éteint un data center, ..).</li>
</ul>
On voit donc qu'un CD au sens plein ne peut être atteint qu'en suivant aussi les approches Devops et Agile.