---
ID: 500
post_title: 'Craftsmanship : Zenika derrière le rideau'
author: team-legat-detant
post_date: 2015-01-26 14:00:00
post_excerpt: "<p>Cela fait quelques années déjà que des communautés se forment autour des <strong>coding dojos</strong> ou assimilables. Au delà de l'aspect technique, ces sessions présentent l'intérêt de pouvoir mener des expérimentations.</p> <p>Elles offrent ainsi l'opportunité à des développeurs aux pratiques différentes, parfois même opposées, de se rencontrer ainsi que de mettre à l'essai des méthodologies de travail alternatives.</p>"
layout: post
permalink: http://blog.zenika-offres.com/?p=500
published: true
slide_template:
  - default
---
Cela fait quelques années déjà que des communautés se forment autour des <strong>coding dojos</strong> ou assimilables. Au delà de l'aspect technique, ces sessions présentent l'intérêt de pouvoir mener des expérimentations.

Elles offrent ainsi l'opportunité à des développeurs aux pratiques différentes, parfois même opposées, de se rencontrer ainsi que de mettre à l'essai des méthodologies de travail alternatives.

<!--more-->

Dans ce cadre, les discussions qui s'engagent à la fin de ces séances sont essentielles: elles permettent de remettre en cause librement les axiomes sur lesquels reposent les méthodologies mises en œuvre au cours du dojo. L'objectif est d'en favoriser l'appropriation par les participants tout en encourageant une application rationnelle et non dogmatique. Naturellement, les organisateurs de dojo bénéficient aussi de ces remarques : elles peuvent les aider à en perfectionner l'organisation comme nous le verrons plus bas.

La <strong>communauté Zenika</strong> encourage donc ces expériences et souhaite apporter sa contribution en ouvrant à tous certaines de nos <a href="http://www.meetup.com/Dev4Fun-Paris/">sessions</a> ainsi qu’en rendant publiques nos réflexions sur le sujet. C'est l'objet de cet article. Nous allons pour cela faire un retour sur deux sessions réalisée au sein de Zenika.
<h5>Session du 1er octobre</h5>
Le <strong>Kata</strong> utilisé lors de cette session était le célèbre kata «<a href="http://butunclebob.com/ArticleS.UncleBob.TheBowlingGameKata">bowling</a>» d’Uncle Bob. Le but est de calculer le score à la fin d’une partie de bowling en tenant compte des strikes et des spares. Pour cette session, deux contraintes ont été posées :
<ul>
	<li>Faire du <a href="http://c2.com/cgi/wiki?PairProgrammingPingPongPattern">ping pong pair programming</a> (PPPP)</li>
	<li>Faire du <a href="http://www.butunclebob.com/ArticleS.UncleBob.TheThreeRulesOfTdd">TDD</a> à la lettre</li>
</ul>
Pour rappel, les règles du <strong>TDD</strong> sont :
<ul>
	<li>On ne peut pas écrire de code de production à moins que ce ne soit pour faire passer un test qui est en échec</li>
	<li>On ne peut pas écrire plus qu’un test qui est en échec; les erreurs de compilations sont des échecs.</li>
	<li>On ne peut pas écrire plus de code de production qu’il est nécessaire pour faire passer le test</li>
</ul>
<h5>Session du 5 novembre</h5>
Le Kata utilisé lors de cette session était le <strong>«minesweeper»</strong> aussi connu sous le nom de <a href="http://codingdojo.org/cgi-bin/index.pl?KataMinesweeper">démineur</a>. Le but est de lire en entrée un démineur «masqué» et de le retourner «dévoilé». Lors de cette session, et afin d’apprendre de nos erreurs, nous avons ajouté aux deux précédentes contraintes, PPPP et TDD, les <a href="https://github.com/TheLadders/object-calisthenics">object calisthenics</a>. Ces contraintes particulières feront l’objet d’un prochain article sur ce blog. Nous en présentons ici les grandes lignes : il s’agit de 9 règles qui doivent toutes être respectées afin de tendre vers un design orienté objet aussi “pur” que possible. Ces règles sont les suivantes :
<ul>
	<li>Un seul niveau d’indentation par méthode</li>
	<li>Ne pas utiliser le mot clé ELSE</li>
	<li>Wrapper toutes les primitives et les strings dans des objets</li>
	<li>Wrapper toutes les collections dans des objets</li>
	<li>Un seul point par ligne</li>
	<li>Pas d’abréviation</li>
	<li>Conserver les entités petites (nous avons décidé de ne pas dépasser 10 lignes par méthodes et 100 lignes par classes)</li>
	<li>Pas de classes avec plus de deux variables d’instances</li>
	<li>Pas de getters ni de setters</li>
</ul>
<h3>Retour d’expérience</h3>
Les retours de ces deux dojo nous ont permis de mettre en lumière les difficultés auxquelles ont été confrontés les participants. Voici quelques uns des ressentis qui nous ont été exprimés ainsi que les réponses que nous nous proposons d’apporter.

La pratique du <strong>TDD</strong> impose aux développeurs de résister à un automatisme fortement ancré dans leur pratique : celui consistant à se focaliser le plus tôt possible sur l’exercice à résoudre dans sa globalité. Lutter contre un tel réflexe est contre-intuitif et c’est précisément pourquoi ce point est d’importance. En effet, un développeur ne résistant pas à cet automatisme aura tendance à coder dés le départ un test couvrant de nombreuses règles fonctionnelles. Il obligera ainsi son partenaire à les implémenter en une passe, rompant ainsi avec les principes du TDD. Il est d’autant plus facile de dériver que le coût d’écriture d’un tel test est le plus souvent nettement inférieur au coût d’écriture du code le validant. L’auteur ne réalise donc pas toujours que son test n’est pas approprié.

Naturellement, cette maîtrise ne concerne pas seulement le développeur chargé de l’écriture du test. Celui qui doit le résoudre est également soumis à la même contrainte et peut être gagné par un sentiment de frustration : s'il est facile de jouer le jeu au début, l'empilement de fonctionnalités au cas par cas pose rapidement un problème de lisibilité et de compréhension du code. A ce stade, chaque nouvelle implémentation alourdit et déséquilibre un peu plus le tout. Le sentiment d'être artificiellement contraint par une méthodologie contre productive peut alors survenir. A ce stade, l'abandon n'est pas loin et les organisateurs du dojo peuvent être confrontés à une forme de rébellion. Pourquoi imposer des contraintes aussi peut naturelles ? Appliquer cette méthodologie à la lettre a-t-il vraiment du sens ?

C’est à ce moment que les organisateurs doivent jouer pleinement leur rôle en rappelant quelques fondamentaux.

Le premier est que la pratique du TDD ne fait que mettre au jour un aspect présent dans la plupart des projets informatique: l'évolution des besoins n'est pas toujours prédictible et il arrive fréquemment qu'il faille remettre l'ouvrage sur le métier. Imposer cette règle permet donc de représenter plus fidèlement les problématiques auxquelles nous sommes tous amenés à faire face.

Ensuite, la pratique du pair programming est le plus efficace lorsque les deux développeurs restent concentrés sur le code. Or quiconque a pratiqué le pair programming de manière improvisée sait que, dans cette configuration, l’un des deux développeurs finit toujours par monopoliser le clavier au point que le second cesse peu à peu de se préoccuper de ce qui s’écrit.

Pour cette raison, le pair programming tient toutes ces promesses grâce au ping pong, et le ping pong ne saurait exister sans TDD.

Au delà du rappel de ces fondamentaux, les organisateurs peuvent aider les participants à éviter les quelques pièges que nous avons évoqué. Ainsi, l'émergence d’un point de blocage dans l’implémentation d’une solution est très certainement le signe que les auteurs n'ont pas suffisamment entrepris de refactorer leur code. Le ping pong explique peut être cet oubli : en accélérant le rythme de l’alternance entre les développeurs, elle les plonge dans un univers où le temps est raccourci et où il est parfois difficile de prendre du recul. Il est du ressort des organisateurs de rappeler que le ping pong n'exclut pas le refactoring et que cette étape reste incontournable de par la définition même du TDD.

Enfin, il est possible de réduire la<strong> complexité des tests</strong> en rappelant que l’écriture d’un test, comme l’écriture du code de production, est un travail conjoint et que ce choix doit faire l’objet d’une négociation entre les membres du groupe. Comme tout bon développeur se doit d’être feignant, on peut faire confiance au co-pilote pour limiter au maximum la quantité de travail qu’il devra réaliser. Ceci aura pour conséquence directe de forcer le développement par petit incréments.
<h3>Conclusion</h3>
Les méthodologies que nous avons évoqués ici, bien que connues et reconnues pour leur efficacité, ne sont pas pour autant facilement appréhendables. Leur maîtrise par les développeurs nécessite donc un temps d’adaptation non négligeable.

Ce temps d’adaptation ne devrait pas être sous-estimé. Nous ne saurions trop déconseiller d’imposer cette méthodologie dans un projet sans l’accompagnement d
’une personne rodée à ce genre d’exercice.