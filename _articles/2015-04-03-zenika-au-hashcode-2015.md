---
ID: 386
post_title: Zenika au HashCode 2015
author: Zenika-HashCode
post_date: 2015-04-03 12:56:00
post_excerpt: |
  <p>Le Hash Code est une compétition de code et d'algorithmique organisée par Google France. Cette année, nous y avons participé sous le nom d’équipe “Serenity” (en référence au vaisseau de Firefly). <img src="/public/Billet_0553/logo.png" alt="logo.png" style="display:block; margin:0 auto;" /></p>
layout: post
permalink: >
  http://blog.zenika-offres.com/zenika-au-hashcode-2015/
published: true
slide_template:
  - default
---
Le Hash Code est une compétition de code et d'algorithmique organisée par Google France. Cette année, nous y avons participé sous le nom d’équipe “Serenity” (en référence au vaisseau de Firefly).

<!--more-->
<h3>Tour de qualification</h3>
Le Hash Code a débuté par une étape de qualification ouverte à tous le 12 mars 2015, en soirée. Il était possible d’y participer dans des hubs où les développeurs se retrouvaient en équipe. Ces hubs étaient notamment organisés par les <a href="https://developers.google.com/groups/pulse/France">GDGs</a> et différentes écoles (<a href="http://www.epitech.eu/nantes/ecole-informatique-nantes.aspx">Epitech Nantes</a> dans notre cas).

A 19h30 précise, nous avons reçu le fameux sujet : placer des serveurs dans un data center de manière optimale. Un fichier d’entrée décrivait un data center en grille de 16 sur 100 emplacements, dont certains indisponibles, puis une liste de serveurs de divers tailles et performances. L’objectif était de placer les serveurs sur la grille, puis de les ventiler dans 45 groupes (pools) afin de maximiser la performance du plus lent des groupes en cas de panne d’une des lignes du data center. Le sujet complet est disponible <a href="https://docs.google.com/file/d/0B8xe9y1rGAQXRXZjSXl6THBDZXc/edit">ici</a>.

Nous sommes parti de l’idée de placer les serveurs par ordre de densité (rapport performance / taille), en commençant par remplir les lignes les plus encombrées, puis d’affecter chaque serveur à un groupe de façon à lisser les différences de performances.

A l’issue de cette première étape, nous avons terminé premier de notre hub, avec 359 points, soit 65ème sur les 230 équipes qui avaient soumis un résultat. La qualification a rassemblé environ 500 équipes et 1500 participants. A noter que le système de soumission du code et des résultats, hébergé sur Google App Engine, a eu quelques difficultés à tenir la charge des soumissions de dernière minute.

Grâce à ce bon résultat, nous avons été conviés à la finale dans les locaux de Google France.
<h3>Finale</h3>
La finale s’est déroulée le vendredi 27 et samedi 28 mars à Paris. Nous avons été reçus par toute l’équipe organisatrice. Arrivés rue de Londres, Google avait ouvert ses portes en grand pour accueillir les 200 participants invités à la finale avec une pose photo pour chaque équipe. Voici notre équipe Zenika "Serenity" :

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.image04_m.jpg" alt="image04.jpg" />

<strong>Vendredi - 18h00</strong> La soirée a débuté par des présentations de différents produits Google par des ingénieurs travaillant sur ces produits :
<ul>
	<li>Youtube et la définition d’une vidéo “populaire” (trending) en fonction des pics de vues et du temps.</li>
	<li>Cardboard avec les étapes clés de son succès depuis la création d’un prototype jusqu’à la distribution de 5000 cardboards au I/O 2014.</li>
	<li>Chrome on Android et quelques exemples d’optimisation nécessaires à son usage mobile.</li>
</ul>
<strong>Vendredi - 20h30</strong> Un tour d’essai a été organisé dès le soir pour chauffer les équipes pendant 2 heures. Le but était de découper une pizza tout en minimisant les restes, la découpe des parts devant respecter les règles suivantes :
<ul>
	<li>Une part doit avoir un nombre minimal de morceaux de jambon</li>
	<li>Une part a une taille maximale</li>
	<li>Une part est rectangulaire</li>
</ul>
Le sujet complet est disponible <a href="https://docs.google.com/file/d/0BxBrRUut57mXM1dlLU9sWkVjN0U/edit">ici</a>. Le problème a bien évidemment été accompagné d’un repas à base de pizzas !

<strong>Samedi - 11h00</strong> Jour J, la grande finale. Nous nous sommes installés dans un coin de la salle pour ne pas avoir trop de bruit. Nous avons reçu l’énoncé, et surprise, cela parlait du projet <a href="http://www.google.com/loon/">Google X “Loon”</a>, qui vise à offrir une large couverture d’accès à Internet à l’aide de ballons évoluant au gré des vents dans la stratosphère. Dès la réception des sujets, l’ambiance dans la cafétéria de Google est devenu très studieuse.

D’une base de départ située en Afrique du Sud, nous devions lancer 53 ballons pour couvrir le plus de zones habitées possibles entre les parallèles 15° sud et 60° sud. Le seul moyen dont les ballons disposent pour se diriger est le contrôle de leur altitude. Le reste dépend des vents. Il nous fallait donc, à partir d’une carte des vents, fournir des instructions simples (monter, descendre, conserver l’altitude) aux ballons dans le but de maximiser leur couverture. Le sujet complet est disponible <a href="https://docs.google.com/file/d/0B8xe9y1rGAQXa01vNXQxbDhwbVU/edit">ici</a>.

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.image00_m.jpg" alt="image00.png" />

<strong>Samedi - 11h58</strong> Après un peu de temps passé à écrire le code gérant les fichiers d’entrée et de sortie, nous avons soumis notre premier algorithme, extrêmement simpliste : les ballons décollent tous en même temps puis zigzaguent tous ensemble entre les altitudes 1 et 2. Comme ils ne se séparent jamais, ils couvrent les mêmes zones, et accumulent autant de points qu’un unique ballon. Le score obtenu est de 1064 points.

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.image03_m.jpg" alt="image03.jpg" />

<strong>Samedi - 12h08</strong> Les ballons ajustent maintenant leur altitude de manière aléatoire, en se déplaçant au gré des vents et sans se diriger particulièrement vers les zones à couvrir. Le score augmente drastiquement pour atteindre 167 549 points, et nous nous classons parmi les 20 premières équipes. Nous avons ensuite déjeuné tout en réfléchissant aux itérations suivantes.

<strong>Samedi - entre 13h et 14h45</strong> Nous avons perdu beaucoup de temps à essayer d’empêcher les ballons de s’évader de la carte par le nord ou le sud, en analysant les vents à un coup d’avance. D’une part, un coup d’avance ne suffisait pas, et d’autre part, l’itération suivante allait régler ce problème bien plus efficacement.

<strong>Samedi - 15h29</strong> Le calcul d’une heatmap (carte de chaleur) nous a permis d’associer à chaque position du monde un score indiquant la proximité immédiate des cibles à couvrir, et ainsi de calculer les meilleurs chemins empruntés par nos ballons. Nous avons donc laissé nos ballons tracer des routes aléatoires avant de sélectionner les meilleures et de les écrire dans le fichier de sortie. Notre nouveau score était de 340 763.

<strong>Samedi - 16h16</strong> Après quelques ajustement, nous avons atteint le score de 347 738. Cela est resté notre meilleure soumission jusqu’à la fin. Par la suite, nous avons essayé en vain de modifier quelques paramètres ou certaines fonctions d’évaluation, sans succès.

<strong>Samedi - 17h15</strong> Une fois le système de juge clos, nous avons été invités à la cérémonie de clôture avec la remise des prix. Nous sommes fiers d’avoir terminé 18ème de cette finale. Nous avons été appelés sur scène pour avoir obtenu le meilleur score du hub nantais lors du tour de qualification. Nous avons chacun gagné une clé Chromecast et un certificat attestant de notre résultat.

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.image02_m.jpg" alt="image02.jpg" />
<h3>Conclusion</h3>
Le Hash Code a été pour nous une bonne dose de fun et une occasion de découvrir les beaux locaux de Google Paris. Dans les jours qui ont suivi, nous avons continué à explorer des évolutions de notre solution : amélioration du calcul de la heatmap, parallélisation, déploiement dans le cloud.

Nous ne manquerons pas de retenter notre chance l’année prochaine !