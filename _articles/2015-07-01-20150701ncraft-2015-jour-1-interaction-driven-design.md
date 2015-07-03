---
ID: 503
post_title: 'NCrafts 2015, jour 1 : Interaction driven design'
author: lclaisse
post_date: 2015-07-01 10:15:00
post_excerpt: |
  <p>En tant qu'acteur du mouvement Software Craftsmanship, Zenika se devait d'être présent à la conférence NCrafts 2015. Nous ne l'avons pas regretté, les interventions étaient vraiment d'excellente qualité! Nous proposons un compte-rendu détaillé à ceux qui n'ont pas eu la chance de s'y rendre.</p> <p><img src="/public/_Claisse/ncraft2015/nscrafts2015.png" alt="nscrafts2015.png" style="display:block; margin:0 auto;" /></p>
layout: post
permalink: >
  http://blog.zenika-offres.com/20150701ncraft-2015-jour-1-interaction-driven-design/
published: true
slide_template:
  - default
---
En tant qu'acteur du mouvement Software Craftsmanship, Zenika se devait d'être présent à la conférence NCrafts 2015. Nous ne l'avons pas regretté, les interventions étaient vraiment d'excellente qualité ! Nous proposons un compte-rendu détaillé à ceux qui n'ont pas eu la chance de s'y rendre.

<!--more-->

<img src="/wp-content/uploads/2015/06/Ncrafts.jpg" alt="nscrafts2015.png" />
<h3>Interaction driven design</h3>
J'avais déjà vu cette présentation de Sandro Mancuso (une des figures les plus connues du Software Craftmanship), mais il l'a entre-temps modifiée en utilisant (après hésitation) le terme de IDD qui met l'accent sur un aspect jusque là implicite de sa vision du DDD.

Mais reprenons au début, comme si on n'avait pas vu ses présentations précédentes.
<h4>Les antipatterns de conception non-DDD</h4>
Le DDD proposant une approche de conception, commençons par les problèmes de conceptions les plus fréquemment (presque toujours) vus quand on fait un audit de code.
<h5>L'antipattern "packages techniques"</h5>
Un des plus frustrants et le mauvais design des packages/assemblies: les packages nommés suivant des patterns de conception (adapters, builders) ou d'architecture(controllers, services, domain) sont une très mauvaise idée:

<img src="/wp-content/uploads/2015/07/crafted-design-pkgs.jpg" alt="crafted-design-pkgs" />
<ul>
	<li>Ils sont à la fois faiblement cohérents (ils agrègent des classes qui n'interagissent pas) et fortement couplés (une classe est obligée d'interagir avec d'autres packages pour remplir une fonctionnalité)</li>
	<li>Quand on déplie l'arborescence du projet, il est impossible de discerner ce que fait l'application. Ceci va à l'encontre à la fois de l'objectif de code auto-documenté (Agile), et de celui d'incarner les concepts métier dans le code (DDD).</li>
</ul>
La fréquence de cette erreur de conception s'explique en grande partie par la profusion de mauvais exemples autant dans les tutoriaux que dans les applications rencontrées dans le travail. Elle est d'autant plus grave que les classes et les méthodes ont une granularité trop fine pour modulariser l'application.
<h5>Un antipattern lié: "struct + procédures + gros contrôleur"</h5>
Pourtant, si un effort de refactor est souvent apporté aux classes et méthodes, c'est rarement le cas des packages. Une conséquence sournoise est un fort couplage entre l'architecture (framework) et les concepts du domaine, qui ne sont que des sous-packages des concepts architecturaux (ex: MVC).

Penchons-nous sur le cas fréquent d'une application utilisant une des variations de MVC; on voit souvent la structure suivante:

<img src="/wp-content/uploads/2015/07/crafted-design-bad-mvc.jpg" alt="crafted-design-bad-mvc" />
<ul>
	<li>Un domaine anémique: les entités sont en réalités des structs sans aucun comportement, dans lesquelles des getters/setters (en Java) ou properties (en C#) ont été générés pour chacun des champs. Ceci ruine complètement les idées essentielles OO d'encapsulation et de colocalisation des traitements et des données. Ces structs sont typiquement accédées et itérées par des classes procédures suffixées par helper/manager/..</li>
	<li>Des controllers épais, embarquant une logique métier non-triviale, ce qui viole à la fois le principe de séparation des préoccupations et l'intention des frameworks MVC, et conduit à un couplage fort entre le framework et le domaine.</li>
</ul>
<h4>La solution de l'architecture hexagonale</h4>
<h5>L'architecture hexagonale en général</h5>
Comment remédier à cette situation? En adoptant une acception particulière de MVC, qui favorise les concepts DDD. Quelle que soit la variation de MVC considérée (Model 2/MVP/MVVM/..), on considère le V et le C comme des "delivery mechanisms" propres au framework utilisé.

<img src="/wp-content/uploads/2015/07/crafted-design-delivery-meca.jpg" alt="crafted-design-delivery-meca" />

Le V et le C de MVC ne contiendront pas de logique métier. Le coeur de l'application sera le M, pris au sens DDD de Modèle du Domaine. Jusque là rien de nouveau dans le DDD, c'est déjà ce que décrit l'"architecture hexagonale" de Cockburn, où le modèle du domaine ne dépend pas de l'architecture d'IHM (ni des autres services techniques) grâce à l'inversion de dépendance (DIP, Bob Martin):

<img src="/wp-content/uploads/2015/07/idd-hexagonal-architecture.png" alt="idd-hexagonal-architecture" />
<h5>L'architecture hexagonale variation Mancuso</h5>
L'originalité du DDD à la sauce Mancuso tient dans l'importance qu'il donne aux composants de type services par opposition aux entités. Ce point de vue a l'avantage de mettre l'accent sur les verbes du domaine et pas seulement sur les noms (ce qui est un point faible de design OO). En particulier il rebaptise du nom plus parlant d'Actions les composants traditionnellement appelés "Application Services", qui correspondent à des actions métier et sont directement appelés par le contrôleur MVC:

<img src="/wp-content/uploads/2015/07/idd-actions-pts-entree.png" alt="idd-exec-et-design-flow" />

Le terme Interaction driven design désigne cette approche du DDD, dans laquelle la conception commence par les Actions, car ces verbes correspondent directement aux cas d'utilisations à implémenter. On peut faire le rapprochement avec la programmation "outside-in" du TDD, ou on écrit d'abord l'appelant avant l'appelé. C'est aussi un principe général de programmation: on code d'abord l'appelant de l'API, pour être sûr que celle-ci conviendra, plutôt que d'essayer a posteriori de tordre de client pour coller à l'API. L'IDE nous aide à créer le squelette des fonctions manquantes de l'API. Enfin cet ordre est aussi le même que l'ordre de la stack d'exécution:

<img src="/wp-content/uploads/2015/07/idd-exec-et-design-flow2.png" alt="idd-exec-et-design-flow2" />

Cependant, attention d'un point de vue pédagogique: expliquer qu'il faut commencer par les Actions risque d'être interprété comme un encouragement aux "fat services"; Cette approche nécessite donc un premier niveau de maturité en DDD.
<h5>Domain Services, CQRS, et inversion de dépendance</h5>
En aval des Actions, les Domain Services coordonnent l'utilisation de plusieurs instances d'Entities. Un design qui place plus de responsabilités dans les Domain Services (VS dans les Entities): <img src="/wp-content/uploads/2015/07/crafted-design-lotsa-services.png" alt="crafted-design-lotsa-services" /> a l'avantage de se prêter plus facilement à une division des tâches basées sur CQRS.

Il suffit d'écrire un service d'écriture et un de lecture, alors qu'avec une approche "Entity-centrique" on se heurte souvent aux problèmes de connectivité du graphe d'objet, de lazy loading, de mapping ORM complexe, .. Dans les applications persistantes, il est parfois plus simple de se dispenser d'ORM.

Toutefois le Domain Service ne doit pas directement dépendre de la persistance concrète (JDBC, JOOQ, ..), on préfèrera le faire dépendre de l'interface d'un Infrastructure Service, dont l'implémentation dépendra elle directement de la technologie utilisée (DIP):

<img src="/wp-content/uploads/2015/07/crafted-design-dip.jpg" alt="crafted-design-dip" />

Le service d'écriture est plus simple que celui de lecture qui doit peut-être lire des agrégats, une implémentation simple de ce dernier utilise une vue matérialisée.

Dans d'autres cas, il vaut mieux opter pour une séparation du modèle d'écriture et de celui de lecture avec notification des changements par événements:

<img src="/wp-content/uploads/2015/07/crafted-design-cqrs.jpg" alt="crafted-design-cqrs" />
<h5>Librairies VS frameworks</h5>
Utiliser un ORM n'est pas toujours souhaitable et complique la tâche dans bien des cas (qui ne s'est jamais retrouvé devant un mapping plus gros que les entités, et des problèmes de lazy loading inextricable?).

De façon générale, Sandro nous rappelle qu'il vaut souvent mieux utiliser des librairies que des frameworks, car ceux-ci contraignent très fortement la conception et sont difficilement composables. Toutefois ces axes de réflexion ne sont pas des tables de la loi, et dans certains cas un ORM (par exemple) est imbattable (pas de requêtes, seulement du chargement par ID suivi d'une navigation du graphe à l'intérieur d'une transaction).
<h4>Conclusion</h4>
Quel que soit le choix de persistance, le DDD a complètement changé notre design. En particulier, cette structure de packages n'est-elle pas bien meilleure?

<img src="/wp-content/uploads/2015/07/crafted-design-pkgs-bien.jpg" alt="crafted-design-pkgs-bien" /> On discerne immédiatement les responsabilités, les packages sont visiblement cohérents et peu couplés. Vous aussi, pensez à designer vos packages et pas seulement vos classes!