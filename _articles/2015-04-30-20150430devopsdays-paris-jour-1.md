---
ID: 495
post_title: Devopsdays Paris, Jour 1
author: devopsday
post_date: 2015-04-30 11:41:00
post_excerpt: |
  <p>Zenika était présent (et partenaire) les 14 et 15 avril aux <a href="http://www.google.com/url?q=http%3A%2F%2Fwww.devopsdays.org%2Fevents%2F2015-paris%2Fprogram%2F&amp;sa=D&amp;sntz=1&amp;usg=AFQjCNEqdljhhqSq91pL2Z3hR3NMIL6ftA">DevopsDays</a> (<a href="https://twitter.com/devopsdaysparis">twitter</a>) à Paris au MAS.</p> <p>Côté organisation, une salle de présentation pour accueillir les différentes <strong>keynotes</strong>. Il y avait également un village partenaire avec notamment&nbsp;: Normation (Rudder), Puppet Labs, OpsCode (Chef), IBM, Microsoft, Xebia Labs... Le matin des conférences entrecoupées de petits pitchs de 90 secondes des partenaires de l’événement. L'après midi commence avec des «&nbsp;<strong>Ignites</strong> », ce sont des talks de 5 minutes avec défilement automatique des slides. Puis viennent les "<strong>open space</strong>", lieux de discussion et de partage permettant d'avoir plus de participation. Pour ces «&nbsp;open space », toute personne dans l'assemblée peut proposer un sujet via un post-it sur un tableau blanc. Chacun vient ensuite voter pour les sujets qui l'intéresse. Les sujets ayant reçu le plus de votes sont répartis pour discussion dans différentes salles.</p> <p>Cet article tente de retracer au travers de flashbacks le déroulé de cette première journée de conférence. Toutefois il est possible que certains messages ne soient pas retranscrits fidèlement et pour cela nous nous excusons auprès des speakers, des <em>poneys</em> et des <em>chats</em>.</p>
layout: post
permalink: http://blog.zenika-offres.com/?p=495
published: true
slide_template:
  - default
---
Zenika était présent (et partenaire) les 14 et 15 avril aux <a href="http://www.google.com/url?q=http%3A%2F%2Fwww.devopsdays.org%2Fevents%2F2015-paris%2Fprogram%2F&amp;sa=D&amp;sntz=1&amp;usg=AFQjCNEqdljhhqSq91pL2Z3hR3NMIL6ftA">DevopsDays</a> (<a href="https://twitter.com/devopsdaysparis">twitter</a>) à Paris au MAS.

Côté organisation, une salle de présentation pour accueillir les différentes <strong>keynotes</strong>. Il y avait également un village partenaire avec notamment : Normation (Rudder), Puppet Labs, OpsCode (Chef), IBM, Microsoft, Xebia Labs...

<!--more-->

Le matin des conférences entrecoupées de petits pitchs de 90 secondes des partenaires de l’événement. L'après midi commence avec des « <strong>Ignites</strong> », ce sont des talks de 5 minutes avec défilement automatique des slides. Puis viennent les "<strong>open space</strong>", lieux de discussion et de partage permettant d'avoir plus de participation. Pour ces « open space », toute personne dans l'assemblée peut proposer un sujet via un post-it sur un tableau blanc. Chacun vient ensuite voter pour les sujets qui l'intéresse. Les sujets ayant reçu le plus de votes sont répartis pour discussion dans différentes salles.

Cet article tente de retracer au travers de flashbacks le déroulé de cette première journée de conférence. Toutefois il est possible que certains messages ne soient pas retranscrits fidèlement et pour cela nous nous excusons auprès des speakers, des <em>poneys</em> et des <em>chats</em>.

<!--more-->
<h3>Keynotes</h3>
<h2>John Willis - Guns, Germs and Microservices</h2>
<img style="float: right; margin: 0 0 1em 1em;" src="/wp-content/uploads/2015/07/john-willis.avatar.png" alt="john-willis.png" /> John Willis ouvre ces DevopsDays Paris avec un talk intitulé "Guns, Germs and Microservices". Le titre est inspiré du livre "<a href="https://fr.wikipedia.org/wiki/De_l'inégalité_parmi_les_sociétés">Guns, Germs and Steel</a>".

John a <a href="http://blog.docker.com/2015/03/why-i-love-docker-and-why-youll-love-it-too/">intégré récemment</a> Docker Inc. suite au rachat par ce dernier de la société SocketPlane.

John commence par une analogie entre le secteur des technologies informatiques et la bataille de Cajamarca où les Conquistadors ont vaincu les Incas avec l'aide de quelques fusils, tuant plusieurs milliers de personnes. Il se demande pourquoi les Incas étaient si peu préparés à l'arrivée des Espagnols, et pourquoi les Espagnols étaient si bien préparés ? La réponse pour lui est simple : à cause de la "feedback loop" !!! Les Inca n'avaient pas de<strong> feedback loop</strong> assez courte sur la géographie, l'agriculture, la civilisation, les outils, ni entre chacun de ces aspects. John nous propose d'êtres les nouveaux conquistadors, sans le côté armes et génocide. Pour revenir à notre sujet, John nous explique que nous avons besoin d'aller vite et de pivoter rapidement et pour cela nous avons besoin d'avoir les bons outils et d'avoir un cycle de feedback court pour corriger le tir et s'aligner plus rapidement.

John revient ensuite sur le concept de <strong>Data Gravity</strong> exposé par Dave McCrory en <a href="http://blog.mccrory.me/2010/12/07/data-gravity-in-the-clouds">2010</a>. Voici globalement ce que décrit le concept de Data Gravity : nous constatons que plus les données grossissent, plus elles attirent les applications autour d'elles leur offrant accélération (rapidité d'accès aux données, réduction d'utilisation de bande passante). Ces données que nous triturons via nos applications, nous les exposons en partie via des services. Cette exposition va, elle, réduire la vitesse de la donnée.

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/data-gravity.png" alt="data-gravity.png" />

Avec l'IoT, la production de données s'accélère très très très rapidement, les données deviennent ainsi trop lourdes à déplacer. Il faut donc changer notre vision d'un monde où la donnée va au programme vers un monde où le programme va à la donnée.
<blockquote>"Moving from a world of Data to compute to moving compute to data".</blockquote>
Tout comme James Bond pour qui le monde ne suffit pas, la donnée à elle seule ne suffit pas. En ajoutant un contexte et de la sémantique à la donnée nous obtenons de l'information. Grâce à nos précédentes boucle de feedback - à notre connaissance historique de l'information - l'information devient de la connaissance et à partir de cette connaissance nous sommes capable de prendre des actions. Ces actions vont apporter des informations en retour et ainsi nous amener à adapter notre position vis à vis de notre connaissance et donc de nos informations. Ainsi nous avons une boucle de feedback de la data représentée par le schéma ci-dessous.

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.data-feedback-loop_m.jpg" alt="data-feedback-loop.png" />

Ces boucles de feedback visent bien sûr à réduire le temps entre la production de données et les actions que nous allons prendre. John enchaîne avec Docker qui naturellement devient le conteneur des applications gravitant autour des données. John décrit les trois grands apports de <strong>Docker</strong> :
<ul>
	<li>Un développement plus efficient : le développeur travaille désormais dans un environnement de production et voit son environnement démarrer dans la seconde.</li>
	<li>Une intégration continue et un déploiement ou delivery continue facilités grâce notamment au layering avec le copy on write de Docker.</li>
	<li>La traçabilité grâce au layering.</li>
</ul>
Pour terminer son talk John aborde rapidement les principes des <strong>microservices</strong> (il faut se concentrer sur le bounded context) et nous parle d'architecture immutable et pour ce dernier point il insiste vraiment – mais vraiment – pour que nous regardions la vidéo de <a href="https://www.youtube.com/watch?v=GaHzdqFithc">Michael Bryzeck à la Docker Con de 2014</a>.

Pour John Willis, <strong>le concept de Data gravity, Docker et les architectures Microservices sont le nouveau monde</strong>. Les <ins>nouveaux conquistadors</ins> seront ceux qui non seulement <ins>maîtriseront ces principes</ins> et outils mais vont <ins>accélérer leur convergence</ins>.

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/data-gravity-docker-microservice.png" alt="data-gravity-docker-microservice.png" />

C’est <a href="http://t.co/MBkI0rTik2">ici</a> pour la vidéo du talk.
<h2>Steve Pereira - What happens without traction</h2>
<img style="float: right; margin: 0 0 1em 1em;" src="/wp-content/uploads/2015/07/steve-pereira.avatar.png" alt="steve-pereira.png" />

Dans le deuxième talk Steve Pereira nous questionne sur ce qu'est le Devops et quelle <strong>attitude</strong> adopter vis à vis de cette culture et nous donne quelques trucs et astuces pour la mettre en place, même sans le support du "<em>board</em>". A travers ses expériences Steve nous parle de changement en opposition à la stabilité, de partage, de mesure et de ses « victoires faciles ».

Steve aborde d’abord ses premiers challenges chez Fed Ex où pour délivrer une modification en production il fallait une semaine qu'il a su réduire à deux jours. Sur un autre projet, il avait une fenêtre de 2 jours pour réaliser les merges et cela amenait évidemment son lot de problèmes. Dans un autre exemple Steve nous parle d'un build qui prenait deux jours (sic) qu'il a su réduire à 10 minutes. Une volonté d'industrialisation. Steve nous raconte qu'il a toujours essayé d'optimiser son travail et un jour il a levé la tête et a regardé autour de lui et n'a pas trouvé cela bien. Son cerveau industriel y a vu l'opportunité d'optimiser les organisations. Et c'est tout naturellement qu'il s'est tourné vers la culture Devops.

Pour Steve, il y a un changement de paradigm : «avant» - ou encore aujourd'hui - il fallait gravir les échelons pour avoir une meilleure vision du fonctionnement d'une organisation, <strong>aujourd'hui les outils changent la donne</strong>. Steve nous explique qu'il est difficile de démontrer qu'un changement marche, cela implique beaucoup de discussions et d'énergie. Pour nous aider à convaincre du bien fondé d'une transformation, il est bien sûr nécessaire de <strong>comprendre son environnement, le “Why” de l'organisation</strong>. Cela nous aide à savoir si l'organisation est prête pour ce changement.

Mais ce n'est pas parce que l'organisation souhaite changer qu'elle va y arriver; l’inertie, notamment dans les grandes organisations, est un des facteurs d'échecs. Pour cela Steve préconise d'<ins>identifier les leaders</ins>, les suiveurs et les sceptiques. Et quand nous arrivons à nous mettre en marche allons-nous dans la bonne direction? Il faut se coller à l'objectif de l'organisation et mettre en place les bonnes métriques pour <ins>mesurer</ins> notre avancement. La mise en place d'une culture Devops n'est pas une fin en soi et l'objectif de la <strong>mise en place d'une culture Devops doit entrer en résonance avec les objectifs de l'organisation</strong>.

Steve conclut sur le fait qu'il est important de se connaître avant d'essayer de convaincre les autres.
<blockquote>In DevOps nothing happens without sharing. — Steve Pereira</blockquote>
Vous pouvez retrouver les ressources du talk de Steve <a href="https://gist.github.com/stevepereira&lt;br /&gt; /aa2b0ab9edbdf9f0693e">ici</a>.
<h2>Boris Feld - The importance of Why in DevOps</h2>
<img style="float: right; margin: 0 0 1em 1em;" src="/wp-content/uploads/2015/07/boris-feld.avatar.png" alt="boris-feld.png" />

Dans beaucoup de présentations, trop de monde se concentre sur le «<em>How</em> to do DevOps ?». Suis-je bien Devops? Mais peu mettent en avant une question plus importante : « <strong>Pourquoi</strong> faire du Devops ? ». Ne pas se poser la question du « Why ? » peut amener à du « Bad devops ».

Boris Feld a travaillé dans deux startups Parisiennes. Dans la première startup, ils ont mis en place une démarche Devops avec des résultats assez positifs. Pour Boris le <ins>Devops n'est pas obligatoire</ins> mais se demande tout de même pourquoi alors souhaiter faire du CAMS (<strong>C</strong>ulture, <strong>A</strong>utomatisation, <strong>M</strong>esure and <strong>S</strong>haring). Pour lui la réponse est simple ; parce que nous sommes des humains pardi ! Et qu'en tant qu'être humain nous <ins>adorons faire des erreurs</ins>. Mais qui, dans une entreprise saine d'esprit (oui, c'est vrai que l'entreprise est un peu un monde de fous), voudrait que des humains mettent leurs sales pattes sur les éléments les plus critiques ? En plus de vouloir sécuriser leurs éléments critiques, les entreprises saines d'esprit veulent aller vite et cela sans erreur. La réponse est simple : l'<strong>A</strong>utomatisation. Voilà pour le A de cAms.

Vient alors la question de « Pourquoi automatiser ? ». Il y a plusieurs réponses : <strong>réduire le stress</strong>, passer moins de temps à faire des choses ennuyeuses et <strong>se concentrer sur ce qui nous fait vibrer</strong>..

Boris nous demande pourquoi nous souhaitons mesurer ? Et bien parce que l'on souhaite écouter, apprendre: avoir du <strong>feedback</strong>. Nous souhaitons passer moins de temps à chercher nos données et l'origine de nos problèmes. On veut que les données et les applications nous parlent.

Et puis on veut partager aussi, parce que quand nous aimons quelque chose nous aimons bien le partager (attention ! j'ai dit « quelque chose »). Il insiste également sur l’importance du partage en prenant exemple sur le problème de largeur des nouveaux train de la SNCF en 2014. Et le partage apporte aussi du feedback. Alors partageons tout, partageons nos outils, nos victoires et nos échecs pour construire notre Culture.

Pour ce talk je retiendrai beaucoup de choses notamment cette citation
<blockquote>Company culture is what happens when the boss is not around.</blockquote>
La conclusion de cette keynote complète sa premiere phrase : « <strong>Devops is not mandatory, but will be</strong> ».
<h2>Sabine Bernecker-Bendixen - Bizdevops</h2>
<img style="float: right; margin: 0 0 1em 1em;" src="/wp-content/uploads/2015/07/sabine-bernecker-bendixen.avatar.png" alt="sabine-bernecker-bendixen.png" />

Sabine Bernecker-Bendixen est venue nous parler de <strong>respect</strong>, de <strong>diversité</strong> et de <strong>tolérance</strong>. Dans le Devops on parle souvent des deux tribus que sont les Dev et les Ops, c'est normal Devops est la contraction de ces deux termes. Mais nous oublions peut-être que le Devops implique beaucoup plus de populations d'individus. Sabine, elle, parle de “<strong>Bizdevops</strong>” où le Biz ce sont les <ins>utilisateurs</ins>, les <ins>clients</ins>, ceux qui paient les factures.

Il est important de savoir qu'il y a des différences entres ces populations ; elles ne parlent pas le même langage, elles n'ont pas le même point de vue, elles ne travaillent pas de la même façon, elles ne mangent peu être pas les mêmes choses. La <strong>communication</strong> est donc un axe important pour faire se rapprocher et se comprendre ces populations. Il faut traduire avec <ins>empathie</ins>, sans condescendance. Il faut surtout communiquer à un même niveau ; si je communique mes sentiments et qu'en face on me communique de la technologie, l'échange ne sera pas efficient.

Le milieu de l'entreprise nécessite d'adopter une communication adaptée et dans le contexte de transformation d'entreprise ou de culture d'entreprise de type Devops, où l'on sort de notre de zone de confort, il devient nécessaire d'avoir la bonne attitude ainsi que les bonnes personnes aux bons endroits.
<h2>BlaBlaCar REX avec Nicolas Blanc &amp; Muriel Lusseau</h2>
<img style="float: right; margin: 0 0 1em 1em;" src="/wp-content/uploads/2015/07/muriel-lusseau.avatar.png" alt="muriel-lusseau.png" />

Chez blablacar, les devs sont des chats et les ops sont des poneys, j'imagine que ça ne doit pas sentir bon tous les jours chez eux... Une chose particulière chez Blablacar, c'est que les développeurs ont une très bonne connaissance du hardware et du système. Donc naturellement les devs sont des ops. Grace à cela ils sont capables de faire 10 déploiements par jour et par 10 personnes différentes et cela dans 10 positions différentes.

<img style="float: left; margin: 0 1em 1em 0;" src="/wp-content/uploads/2015/07/nicolas-blanc.avatar.png" alt="nicolas-blanc.png" />

Contrairement aux organisations régies par le « touche pas à ça ptit con », chez Blablacar, les <ins>devs ont le droit de faire les mises en production</ins> puisqu'ils savent faire des installations, faire des rollbacks ou patcher une installation. Ils ont aussi montré leur maîtrise des tests (3600 tests après un commit pour la principale application) et par <em>contagion positive</em> cela a donné des idées aux Ops qui testent leur infrastructure. Les devs et les ops ont maintenant un outil commun qui est Opscode Chef. L'organisation du delivery a changé au gré des besoins et du contexte. Au départ l'équipe, petite, était managée par une seule personne et utilisait Scrum. Mais avec l'agrandissement des équipes, les dailys meeting devenait trop longs. L'équipe a donc été coupée en deux. Il était cependant difficile d'avoir les experts de la donnée, du métier, des ops et des devs en même temps. Une équipe s'est donc réorganisée en Product team (la Team Pink). Scrum ne leur permettait pas d'avoir le niveau de partage suffisant de l'avancement de chacun. La Team Pink a donc mis en place <ins>Kanban</ins>.

Aujourd'hui les feature team utilisent Kanban et les équipes plus « pérennes » utilisent Scrum.

Chez Blabalcar la communication, le partage et la culture sont importants et à ce titre ils organisent
<ul>
	<li>des <strong>événements</strong> de Team building</li>
	<li>des <strong>apéros</strong> une fois par semaine pour toute l'entreprise</li>
	<li>des <strong>Weekly free speech</strong> ; lieu de partage sur les technologies</li>
</ul>
Les next steps chez Blablacar seraient d'avoir
<ul>
	<li>un Ops dans chaque équipe</li>
	<li>plus de feature teams</li>
</ul>
<h3>Ignites</h3>
Pour reprendre en douceur l’après midi, après un superbe buffet, on nous propose des Ignites. Ce sont ces petites sessions de 5 minutes environ où vos slides défilent tout seuls. Et pour les speaker il faut tenir le rythme. Ces ignites sont ouverts à tous.

Parmis les Ignites j’ai retenus Nicolas Charles (<a title="Twitter" href="https://twitter.com/nico_charles">Twitter</a>) de <a title="Normation" href="https://www.normation.com/">Normation</a> qui édite Rudder. Nicolas nous a fait une superbe analogie entre la dance et le monde de la production logicielle. Andy Burgin (https://twitter.com/andyburgin) est revenu sur les différentes étapes pour créer une communauté et a insisté sur la facilité à invité des rockstars de l’informatique. Andy a lancé le Leeds DevOps.
<h3>Open Spaces</h3>
Les sessions des Open Space étaient répaties dans 4 salles. Chaque sujet pouvait être discuté pendant 30 minutes dans ces salles. Voici les sujets

De 16h10 à 16h40
<ul>
	<li>Devops in
large organisations</li>
	<li>Monitoring as Code</li>
	<li>Docker 101</li>
	<li>How to explain to management and customers why Devops is awesome</li>
</ul>
De 16h40 à 17h10
<ul>
	<li>Why Ansible or Chef or Puppet…</li>
	<li>Code Review</li>
	<li>Leaders expected attitude in a devops journey</li>
	<li>How to make test automation reliable, reusable, robust and costless</li>
</ul>
De 17h10 à 17h40
<ul>
	<li>Micro services a support nightmare</li>
	<li>Immutable Infrastructure</li>
	<li>How to set up an automated deployment pipeline on a legacy process</li>
	<li>Value stream mapping, how ?</li>
</ul>
<h2>Organisateurs &amp; Sponsors</h2>
Merci aux organisateurs et aux sponsors : http://www.devopsdays.org/events/2015-paris/