---
ID: 3249
post_title: Article Docker 1.6
author: Guillaume Gerbaud
post_date: 2015-07-03 14:30:00
post_excerpt: ""
layout: post
permalink: >
  http://blog.zenika-offres.com/article-docker-1-6/
published: true
slide_template:
  - default
---
Le 28 octobre dernier, nous avions parlé de la sortie de Docker 1.3, des évolutions entre la version 1 et cette dernière et de son écosystème. Je vous propose de remettre ça, bientôt 6 mois après, avec un peu le même plan : les principales nouveautés entre la version 1.3 et 1.6 (et il y en a <code>;-)</code>), l'évolution de l'écosystème qui gravite autour et un peu de <em>social</em> avec les meetups et évènements qui se sont passés depuis.

<!--more-->

Rappel <em>ultra</em> rapide, <strong>Docker est une plate-forme ouverte à destination des développeurs et administrateurs systèmes visant à faciliter la construction et le déploiement d'applications distribuées</strong>. De manière moins marketing, l'idée derrière Docker est d'automatiser le déploiement d'environnements sous forme de conteneurs légers, portables et auto-suffisants ; les conteneurs permettant d'isoler l'exécution des applications dans des contextes d'exécution. Pour ce faire, Docker, écrit en Go, reprend les bases de LXC, utilise les fonctionnalités du noyau Linux (CGroups, Namespaces, …) et se base initialement sur un système de fichier "en oignons" AUFS ; D'autres backends sont supportés également comme BTRFS ou devicemapper (LVM).

Ovelay filesystem storage driver (1.4.0)

La release 1.4.0 de Docker (et la 1.3.3 en parallèle) a surtout été une gigantesque <em>bugfix party</em>, histoire de rendre les fonctionnalités arrivées auparavant plus stable — la release note se trouve <a href="https://github.com/docker/docker/blob/master/CHANGELOG.md#140-2014-12-11">ici</a>.

La principale nouveautée de cette version est l'apparation d'un nouveau <em>storage driver</em>, il s'agit d'<strong>OverlayFs</strong>. Il s'agit d'un mécanisme de montage permettant de superposer dans un répertoire le contenu de plusieurs autres répertoires.

Initialement Docker est basé <strong><a href="http://en.wikipedia.org/wiki/Aufs">Aufs</a></strong> qui fait, pour simplifier, la même chose. Le problème avec aufs est qu'il n'est pas intégré dans le noyau Linux (i.e. dans les sources officielles), contrairement à OverlayFS qui a fait son apparition avec le noyau <a href="https://git.kernel.org/cgit/linux/kernel/git/torvalds/linux.git/commit/?id=e9be9d5e76e34872f0c37d72e25bc27fe9e2c54c">3.18</a>. Il était donc nécessaire de disposer d'un noyau patché ; Le noyau Linux de Debian et de sess dérivés (Ubuntu, etc.) ont ce <em>patchset</em> aufs de base mais ce n'est pas le cas de toutes les distributions. L'idée de cette intégration est assez simple : supporter le maximum de distributions en se basant sur une <em>feature</em> du noyau. C'est donc bien évidemment le <em>driver</em> d'avenir pour Docker ; attention cependant, la peinture est encore un peu fraîche <code>;-P</code>.

Les adresses IPv4 commencent a se faire <a href="http://en.wikipedia.org/wiki/IPv4_address_exhaustion">rare</a>, il est donc important que Docker supporte IPv6. C'est désormais le cas avec la version 1.5.0, même si ce n'est pas activé par défaut. Pour activer le support de l'IPv6 (en plus de l'IPv4), il faut ajouter le <em>flag</em> <code>--ipv6</code> au daemon. Docker va donc mettre en place le <em>bridge</em> <code>docker0</code> avec en plus un IPv6 en mode local, avec l'adresse <code>fe80::1</code>.

Par défaut les containers qui seront créés n'auront qu'une adresse locale. Pour avoir une adresse IPv6 routable à votre conteneur, il est nécessaire de lui préciser un <em>sous-réseau</em> (subnet) dans lequel il va piocher son adresse. Cela se fait grâce à l'argument <code>--fixed-cidr-v6</code>.
<div class="org-src-container">
<pre class="src src-sh"><span style="color: #783778;">docker</span> <span style="color: #43783f;">-d</span> <span style="color: #374478;">--ipv6</span> <span style="color: #78683f;">--fixed-cidr-v6</span>=<span style="color: #008000;">"2001:db8:1::/64"</span></pre>
</div>
Comme je ne suis pas un pro de l'IPv6, pour plus d'information, et si l'anglais ne vous fait pas peur, c'est dans la <a href="https://docs.docker.com/articles/networking/#ipv6">documentation "networking"</a> de Docker.
<h2>Conteneurs en lecture seule (1.5.0)</h2>
Une autre fonctionnalité assez sympathique qui est arrivé avec cette version 1.5.0 est les conteneurs en lecture seule — c'est Michael Crosby qui s'est occupé d'<a href="https://github.com/docker/docker/pull/10093">implémenter ça</a>. L'intérêt des conteneurs en lecture seule est de permettre de <strong>contrôler où l'application</strong> à l'intérieur de votre conteneur <strong>peut écrire ou modifier des fichiers</strong>. En combinant ceci avec les volumes, vous pouvez vous assurez des emplacements dans lesquels votre conteneur va persister des états ou données (le/les volumes), puisqu'il ne sera pas possible d'écrire ailleurs de toute façon.

Pour activer cette fonctionnalité, c'est l'argument <code>--read-only</code>.
<pre class="src src-sh"><span style="color: #783778;">docker</span> <span style="color: #513f78;">run</span> <span style="color: #43783f;">--read-only</span> <span style="color: #5e7837;">-v</span> <span style="color: #5e7837;">/volume/writable</span> <span style="color: #3f7178;">busybox</span> <span style="color: #783778;">touch</span> <span style="color: #5e7837;">/volume/writable</span></pre>
Une autre utilisation des conteneurs en lecture seule est que cela donne la possibilité de faire du debug <em>post-mortem</em> d'un conteneur (en production par exemple). Cela nous permet de redémarrer un conteneur qui aurait planté, en lecture seule avec le système de fichier dans l'état du crash.
<h2>Les labels pour le « daemon », les images et les conteneurs (1.6.0)</h2>
<blockquote>One Meta Data to Rule Them All</blockquote>
Une des deux fonctionnalités très attendue de la récente version 1.6.0 sont les labels. En un mot, et pour le faire « à-la » <em>le seigneur des anneaux</em>, les labels peuvent se résumer en "<strong>Une metadata pour les gouverner tous</strong>" (ça le fait vachement mieux en anglais en fait).

Les labels s'appliquent sur le <em>daemon</em>, les images et les conteneurs. C'est un peu un mélange entre des tags et des variables d'environnements puisque il s'agit d'un couple <strong>clé/valeur</strong>.

L'ajout de label sur le <em>daemon</em> se fait grâce à l'argument — roulement de tambour — <code>--label</code> (<code>o/</code>). La principale utilité pour l'instant est son utilisation conjointe avec Swarm dont nous parlerons un peu plus bas ; mais en deux mots, cela permet de filtrer les <em>engines</em> sur lesquels on va <em>taper</em>.
<pre class="src src-sh"><span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">Souvent, c'est dans DOCKER_OPTS du fichier /etc/default/docker</span> <span style="color: #783778;">docker</span> <span style="color: #43783f;">-d</span> <span style="color: #784437;">-H</span> <span style="color: #4f5c7e;">unix://var/run/docker.sock</span> <span style="color: #7a4f7e;">--label</span> <span style="color: #ba36a5;">storage</span>=<span style="color: #5e7837;">ssd</span> <span style="color: #7a4f7e;">--label</span> <span style="color: #ba36a5;">type</span>=<span style="color: #5e7837;">laptop</span></pre>
L'ajout d'un label sur une image se fait dans le fichier <code>Dockerfile</code>, et l'ajout d'un label sur un conteneur, grâce à l'argument <code>--label</code> pour rester cohérent. Construisons une image inutile mais en lui appliquant un label :
<div class="org-src-container">
<pre class="src src-sh"><span style="color: #37785e;">FROM</span> <span style="color: #3f7178;">busybox</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">Support du multi-line pour LABEL</span> <span style="color: #783778;">LABEL</span> <span style="color: #ba36a5;">vendor</span>=<span style="color: #78683f;">zenika</span>  <span style="color: #784437;">com.zenika.lang</span>=<span style="color: #374478;">golang</span>  <span style="color: #78683f;">com.zenika.version</span>=<span style="color: #43783f;">0.1</span> <span style="color: #7a4f7e;">CMD</span> [<span style="color: #008000;">"echo"</span><span style="color: #7e544f;">,</span> <span style="color: #008000;">"zenika"</span>]</pre>
Nous allons maintenant construire cette image et lancer un conteneur à partir de cette dernière avec un autre label.
<div class="org-src-container">
<pre class="src src-sh">$ <span style="color: #783778;">docker</span> <span style="color: #7e544f;">build</span> -t <span style="color: #4f7e67;">zenikaapp</span> <span style="color: #374478;">.</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">[…]</span> $ <span style="color: #783778;">docker</span> <span style="color: #513f78;">run</span> <span style="color: #4f5c7e;">--name</span> <span style="color: #783f5a;">test</span> <span style="color: #7a4f7e;">--label</span> <span style="color: #783f5a;">com.zenika.foo</span>=<span style="color: #784437;">bar</span> <span style="color: #4f7e67;">zenikaapp</span> <span style="color: #78683f;">zenika</span></pre>
</div>
L'idée c'est que maintenant, lorsque l'on va regarder la liste d'images ou de conteneurs à disposition sur notre <em>engine</em>, nous allons pouvoir <strong>filtrer</strong> par label, comme suit :
<div class="org-src-container">
<pre class="src src-sh">$ <span style="color: #783778;">docker</span> <span style="color: #707e4f;">images</span> <span style="color: #4f5c7e;">--filter</span> <span style="color: #008000;">"label=vendor=zenika"</span> <span style="color: #4f5c7e;">--filter</span> <span style="color: #008000;">"label=com.zenika.lang=golang"</span> <span style="color: #784437;">REPOSITORY</span>          <span style="color: #3f7178;">TAG</span>                 <span style="color: #707e4f;">IMAGE</span> <span style="color: #707e4f;">ID</span>            <span style="color: #7e544f;">CREATED</span>             <span style="color: #784437;">VIRTUAL</span> <span style="color: #4f7e67;">SIZE</span> <span style="color: #4f7e67;">zenikaapp</span>           <span style="color: #7a4f7e;">latest</span>              <span style="color: #784437;">66ffda023118</span>        <span style="color: #5e7837;">43</span> <span style="color: #7e544f;">seconds</span> <span style="color: #43783f;">ago</span>      <span style="color: #783778;">2.433</span> <span style="color: #7e544f;">MB</span> $ <span style="color: #783778;">docker</span> <span style="color: #43783f;">ps</span> <span style="color: #784437;">-a</span> <span style="color: #4f5c7e;">--filter</span> <span style="color: #008000;">"label=com.zenika.foo=bar"</span> <span style="color: #374478;">CONTAINER</span> <span style="color: #707e4f;">ID</span>        <span style="color: #707e4f;">IMAGE</span>               <span style="color: #78683f;">COMMAND</span>             <span style="color: #7e544f;">CREATED</span>              [<span style="color: #374478;">…</span>]   <span style="color: #707e4f;">NAMES</span> <span style="color: #513f78;">37e9a37caf57</span>        <span style="color: #513f78;">zenikaapp:latest</span>    <span style="color: #008000;">"echo zenika"</span>       <span style="color: #43783f;">About</span> <span style="color: #78683f;">a</span> <span style="color: #37785e;">minute</span> <span style="color: #43783f;">ago</span>   [<span style="color: #374478;">…</span>]   <span style="color: #783f5a;">test</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">On peut également regarder les labels avec inspect</span> $ <span style="color: #783778;">docker</span> <span style="color: #374478;">inspect</span> <span style="color: #3f7178;">-f</span> <span style="color: #008000;">"{{json .ContainerConfig.Labels }}"</span> <span style="color: #783f5a;">zenikaap</span> {<span style="color: #008000;">"com.zenika.lang"</span><span style="color: #707e4f;">:</span><span style="color: #008000;">"golang"</span><span style="color: #7e544f;">,</span><span style="color: #008000;">"com.zenika.version"</span><span style="color: #707e4f;">:</span><span style="color: #008000;">"0.1"</span><span style="color: #7e544f;">,</span><span style="color: #008000;">"vendor"</span><span style="color: #707e4f;">:</span><span style="color: #008000;">"zenika"</span>} $ <span style="color: #783778;">docker</span> <span style="color: #374478;">inspect</span> <span style="color: #3f7178;">-f</span> <span style="color: #008000;">"{{json .Config.Labels }}"</span> <span style="color: #783f5a;">test</span> {<span style="color: #008000;">"com.zenika.foo"</span><span style="color: #707e4f;">:</span><span style="color: #008000;">"bar"</span><span style="color: #7e544f;">,</span><span style="color: #008000;">"com.zenika.lang"</span><span style="color: #707e4f;">:</span><span style="color: #008000;">"golang"</span><span style="color: #7e544f;">,</span><span style="color: #008000;">"com.zenika.version"</span><span style="color: #707e4f;">:</span><span style="color: #008000;">"0.1"</span><span style="color: #7e544f;">,</span><span style="color: #008000;">"vendor"</span><span style="color: #707e4f;">:</span><span style="color: #008000;">"zenika"</span>}</pre>
</div>
On peut imaginer beaucoup d'usage de ces labels. Par example, avec <a href="http://rancher.com">Rancher</a>, ils sont utilisés pour faciliter la configuration du load-balancer (<a href="http://rancher.com/docker-labels/">ici</a>) — ils utilisent un label <code>io.rancher.service.provides</code> qui permettra à ce dernier de trouver automatiquement ces petits. Je vous laisse imaginer vos propres <em>use-cases</em>.

Il y a une partie de la documentation qui parle exclusivement des labels, avec une petite partie sur les <em>best-practice</em> de nommage des labels, c'est <a href="https://docs.docker.com/userguide/labels-custom-metadata/">ici</a>.

</div>
<div id="outline-container-sec-6" class="outline-2">
<h2 id="sec-6">Logging drivers o/ (1.6.0)</h2>
<div id="text-6" class="outline-text-2">

Un <em>gros reproche</em> qui était fait à Docker était sa gestion très <strong>simpliste</strong> des logs des conteneurs. Plusieurs critiques étaient faites :
<ol class="org-ol">
	<li>Tout faire sortir sur <code>stdout</code> et <code>stderr</code> n'est pas vraiment une habitude de nos jours, surtout dans des langages comme Java où l'utilisation de <em>logger</em> (Log4j, Slf4j, …) est très répandue. Cela rend le portage vers docker de certaines applications un peu plus fastidieux.</li>
	<li>Il n'y avait aucun mécanisme de <em>rotation</em> de logs — et comme en plus le dossier dans lequel les logs étaient écris est un peu enfoui dans <code>/var/lib/docker</code>, cela pouvait poser quelques problème si des conteneurs étaient un peu trop bavards..</li>
	<li>La solution utilisée pour sauvegarder ces logs et pourquoi pas les centraliser (avec ELK par exemple), était d'utiliser un volume, souvent partagé entre applications, et de démarrer un conteneur pour gérer cette analyse, centralisation, …. Non seulement ce n'est pas très optimal, mais cela nécessitait de configurer chaque application (donc chaque conteneur) — et <code>docker logs</code> perdait tout son intérêt.</li>
</ol>
Avec la version 1.6.0, les <em>logging driver</em> permettent une gestion des logs un peu plus optimale, ou au moins plus flexible. Il est donc maintenant possible de préciser le <em>logging driver</em> à utiliser. Ils en existent 3 pour l'instant :
<ol class="org-ol">
	<li><code>json-file</code> correspond au comportement par défaut de Docker avant la 1.6 et reste la valeur par défaut</li>
	<li><code>syslog</code> qui permet de connecter les logs de nos conteneurs dans notre vénérable syslog (ou en tout cas quelqu'un qui parl le syslog).</li>
	<li><code>none</code> qui est le magicien puisqu'il nous permet de faire taire complètement un conteneur <code>o/</code>.</li>
</ol>
Il est possible de définir le logging driver à deux endroits :
<ol class="org-ol">
	<li>sur le <strong>daemon</strong> pour la valeur par défaut de tous les conteneurs.</li>
</ol>
<div class="org-src-container">
<pre class="src src-sh">$ <span style="color: #783778;">docker</span> <span style="color: #43783f;">-d</span> <span style="color: #5e7837;">--log-driver</span>=<span style="color: #008000;">"json-file"</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">Pour faire taire les conteneurs par défaut</span> $ <span style="color: #783778;">docker</span> <span style="color: #43783f;">-d</span> <span style="color: #5e7837;">--log-driver</span>=<span style="color: #008000;">"none"</span></pre>
</div>
<ol class="org-ol">
	<li>en option de la command <strong>run</strong> (ou de la commande <strong>create</strong>).</li>
</ol>
<div class="org-src-container">
<pre class="src src-sh">$ <span style="color: #783778;">docker</span> <span style="color: #513f78;">run</span> <span style="color: #5e7837;">--log-driver</span>=<span style="color: #3f7178;">syslog</span> <span style="color: #3f7178;">ubuntu</span>  <span style="color: #37785e;">/bin/bash</span> <span style="color: #784437;">-c</span> <span style="color: #008000;">'while true; do echo "Hello"; sleep1; done'</span> $ <span style="color: #707e4f;">tail</span> <span style="color: #3f7178;">-f</span> <span style="color: #784437;">/var/log/syslog</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">[…]</span> <span style="color: #374478;">May</span> <span style="color: #3f7178;">28</span> <span style="color: #3f7178;">17:39:01</span> <span style="color: #7e544f;">dev1</span> <span style="color: #783778;">docker</span>[<span style="color: #5e7837;">116314</span>]<span style="color: #707e4f;">:</span> <span style="color: #513f78;">0e5b67244c00:</span> <span style="color: #4f7e67;">Hello</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">[…]</span></pre>
</div>
Une option <code>--log-opts</code> est également présente pour passer des options additionnelles au driver si celui-ci les supporte. Notons également qu'un driver pour <code>systemd</code> devrait arriver avec la version 1.7.

La pull-request ayant permis d'intégrer cette fonctionnalité se trouve <a href="https://github.com/docker/docker/pull/10568">ici</a>.

</div>
</div>
<div id="outline-container-sec-7" class="outline-2">
<h2 id="sec-7">Client Windows natif (1.6.0)</h2>
<div id="text-7" class="outline-text-2">

Enfin, on s'en doutait un peu après l'annonce du partenariat entre Docker Microsoft, ça bosse beaucoup pour porter Docker vers Windows. La première étape était de fournir un client natif pour Windows. C'est chose faite avec cette version 1.6. Maintenant beaucoup de travail est effectué pour rendre le <em>engine</em> plus portable, il n'y a qu'à suivre un peu les pull-request avec un tag <code>os/windows</code> ou encore cette très récente pull-request avec un titre plutôt évocateur : « <a href="https://github.com/docker/docker/pull/13554">Windows: The real Windows exec driver is here</a> ».

</div>
</div>
<div id="outline-container-sec-8" class="outline-2">
<h2 id="sec-8">Divers</h2>
<div id="text-8" class="outline-text-2">

Il y a pas mal d'autres options qui sont arrivées depuis la version 1.3.0, nous allons en parcourir certaines rapidement — parce que sinon cet article va faire 100 pages ;-p :
<ul class="org-ul">
	<li><strong>Stats</strong> (1.5.0) : une commande <code>stats</code> (et donc une API derrière) permet de récupérer quelques statistiques par conteneur, c'est simple pour l'instant.</li>
	<li>Depuis la version 1.5.0 il est possible de spécifier le ficher Dockerfile grâce à l'option <code>-f</code> de la commande build — jusqu'à maintenant docker regardait uniquement le dossier spécifié et cherchait le fichier <code>Dockerfile</code>. Cela permet donc, par exemple, d'avoir plusieurs <code>Dockerfile</code> dans un dossier.</li>
</ul>
<div class="org-src-container">
<pre class="src src-sh">$ <span style="color: #783778;">docker</span> <span style="color: #7e544f;">build</span> <span style="color: #7a4f7e;">-t</span> <span style="color: #5e7837;">monimage</span> <span style="color: #3f7178;">-f</span> <span style="color: #43783f;">backend.Dockerfile</span> <span style="color: #374478;">.</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">[…] Build the backend</span> $ <span style="color: #783778;">docker</span> <span style="color: #7e544f;">build</span> <span style="color: #7a4f7e;">-t</span> <span style="color: #5e7837;">monimage</span> <span style="color: #3f7178;">-f</span> <span style="color: #4f7e67;">frontend.Dockerfile</span> <span style="color: #374478;">.</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">[…] Build the frontend</span></pre>
</div>
<ul class="org-ul">
	<li>Le <em>registry</em> voit son API passer en V2, principalement pour améliorer les transferts. L'implémentation officielle a été réécrite en Go (à la place de Python) et se nomme maintenant <a href="https://github.com/docker/distribution">distribution</a>.</li>
	<li>La commande <code>commit est dotée, depuis la version 1.6.0, d'une option =--change</code> qui permet d'appliquer une instruction supportée par les <code>Dockerfile</code> — voir <a href="https://docs.docker.com/reference/commandline/cli/#commit">ici</a>.</li>
	<li>Docker a publié un petit document « <a href="https://github.com/docker/docker/blob/master/image/spec/v1.md">Docker Image Specification</a> » qui a pour but de définir le format des images utilisées par Docker, permettant à d'autres notamment des potentiels conccurents, d'implémenter des images qui seraient compatible.</li>
</ul>
</div>
</div>
<div id="outline-container-sec-9" class="outline-2">
<h2 id="sec-9">À venir</h2>
<div id="text-9" class="outline-text-2">

L'une des principales nouveautés qui devrait arriver avec la version 1.7 de Docker, c'est l'intégration d'une <strong>nouvelle stack réseau</strong> avec l'intégration du projet <a href="https://github.com/docker/libnetwork">libnetwork</a>, si tout se passe bien. On pourra noter également de nouveaux <em>logging driver</em>, avec notamment un <code>rollover</code> driver ou encore le <code>systemd</code> driver. On peut noter également l'arrivée d'un <em>filesystem driver</em> pour ZFS (voici la <a href="https://github.com/docker/docker/pull/9411">pull-request</a>). Le Docker Birthday étant passé par là, beaucoup de corrections de bugs, de nouvelles petites fonctionnalités, une meilleure couverture de code par les tests unitaires (<code>o/</code>).

La RC1 est disponible depuis le 28 mai, <a href="https://github.com/docker/docker/releases/tag/v1.7.0-rc1">ici</a> et la <a href="https://github.com/docker/docker/pull/13528">pull-request</a> associée, donc à vos tests !

</div>
</div>
<div id="outline-container-sec-10" class="outline-2">
<h2 id="sec-10">Écosystème</h2>
<div id="text-10" class="outline-text-2">

Trois projets « Docker » sont apparus depuis le dernier article : Compose, Swarm et Machine. Présentons les très rapidement.

</div>
<div id="outline-container-sec-10-1" class="outline-3">
<h4 id="sec-10-1">Compose</h4>
<div id="text-10-1" class="outline-text-3">

Compose est le nouveau nom de Fig. Fig était développé par Orchard qui a été racheté par Docker. Pour rappel, l'idée est de définir son environnement via un fichier YAML, que ce soit pour le code sur lequel nous travaillons mais également les services externes desquels notre application dépend (Base de données, ''Message queue'', etc.).
<div class="org-src-container">
<pre class="src src-yaml"><span style="color: #ba36a5;">web</span>: <span style="color: #ba36a5;">build</span>: . <span style="color: #ba36a5;">command</span>: lein run <span style="color: #ba36a5;">links</span>: - db <span style="color: #ba36a5;">ports</span>: - <span style="color: #008000;">"8000:8000"</span> <span style="color: #ba36a5;">db</span>: <span style="color: #ba36a5;">image</span>: postgres</pre>
</div>
Compose est en version 1.2 — depuis la version 1.0, la majorité des modifications sont des corrections de bugs et des ajouts pour suivre les modifications et nouvelles fonctionnalités de Docker (<code>env-file</code>, <code>dns_search</code>, <code>add_host</code>, <code>restart</code>, <code>volumes_from</code>, <code>net</code>, …). La commande est maintenant <code>docker-compose</code> à la place de <code>fig de&gt; et le fichier <code>docker-compose.yml</code> à la place de <code>fig.yml</code> — pour des raisons de rétro-compatibilité, Compose continue de lire les <code>fig.yml</code>. </code>

</div>
</div>
<div id="outline-container-sec-10-2" class="outline-3">
<h4 id="sec-10-2">Swarm &amp; Machine</h4>
<div id="text-10-2" class="outline-text-3">

Deux nouveaux projets ont fait leur apparition dans l'escarcelle de Docker Inc. : Swarm et Machine. Swarm est le <em>clustering</em> à moyenne échelle vu par Docker. Machine permet de provisionner Docker sur différents providers : amazon aws, google compute engine, azure, Virtualbox pour ne citer qu'eux — mais beaucoup d'autres sont déjà supportés.

Voilà ce que Jérôme Petazzoni dit à propos de Swarm :
<blockquote>Un système de cluster utilisant l’API Docker et compatible avec tous les outils de l’écosystème maison. On peut utiliser les commandes classiques Docker pour piloter le cluster</blockquote>
L'idée principale de Swarm est <strong>Je veux parler à mon cluster Docker de la même façon que je parle avec mon daemon Docker</strong>. Cela se traduit assez simplement par : <strong>Swarm expose la même API que docker</strong>. C'est une idée simple et terriblement puissante puisque cela veut dire que je peux administrer mon cluster avec les mêmes commandes que j'utilise quand je travaille en local. Swarm a pour but de piloter des clusters d'une taille relativement petite (moins de 1000 machines). Pour les clusters de plus grande taille, il existe de très bonnes solutions, comme <a href="http://mesos.apache.org/">Mesos</a>, et ce n'est pas le but de Docker Inc. de venir les concurrencer, bien au contraire.

Pour faire simple, swarm c'est un <strong>manager</strong> et des <strong>agents</strong> (un par engine) — les agents s'enregistrent auprès du master par le biais d'un <em>service discovery</em>. Swarm dispose d'un petit service de discovery mais qui n'est là que pour <em>la démo</em> ; il est possible et conseillé de le connecter à des solutions existantes, pour l'instant <a href="https://github.com/hashicorp/consul">consul</a> et <a href="https://github.com/coreos/etcd">etcd</a>.

Un bout de code vaut mieux qu'un long discours, voici comment <em>bootstraper</em> un cluster Swarm, avec l'aide de Machine pour être <em>funky</em>.
<div class="org-src-container">
<pre class="src src-sh"><span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">On crée un cluster simple avec son id</span> $ <span style="color: #7a4f7e;">swarm</span> <span style="color: #783f5a;">create</span> <span style="color: #3f7178;">50862dcedd53c2f584adfb00e85bac4b</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">On démarre des agents</span> $ <span style="color: #43783f;">docker-machine</span> <span style="color: #783f5a;">create</span> <span style="color: #43783f;">-d</span> <span style="color: #374478;">azure</span> <span style="color: #4f5c7e;">--swarm</span> <span style="color: #4f5c7e;">--swarm-discovery</span> <span style="color: #4f7e67;">token://50862dcedd53c2f584adfb00e85bac4b</span> <span style="color: #43783f;">node1</span> <span style="color: #3f7178;">INFO</span>[<span style="color: #7a4f7e;">0000</span>] <span style="color: #5e7837;">Creating</span> <span style="color: #513f78;">SSH</span> <span style="color: #5e7837;">key...</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">[…]</span> $ <span style="color: #43783f;">docker-machine</span> <span style="color: #783f5a;">create</span> <span style="color: #43783f;">-d</span> <span style="color: #7e544f;">digitalocean</span> <span style="color: #4f5c7e;">--swarm</span> <span style="color: #4f5c7e;">--swarm-discovery</span> <span style="color: #4f7e67;">token://50862dcedd53c2f584adfb00e85bac4b</span> <span style="color: #783f5a;">node2</span> <span style="color: #3f7178;">INFO</span>[<span style="color: #7a4f7e;">0000</span>] <span style="color: #5e7837;">Creating</span> <span style="color: #513f78;">SSH</span> <span style="color: #5e7837;">key...</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">[…]</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">On démarre le master</span> $ <span style="color: #43783f;">docker-machine</span> <span style="color: #783f5a;">create</span> <span style="color: #43783f;">-d</span> <span style="color: #78683f;">virtualbox</span> <span style="color: #4f5c7e;">--swarm</span> <span style="color: #707e4f;">--swarm-master</span> <span style="color: #4f5c7e;">--swarm-discovery</span> <span style="color: #4f7e67;">token://50862dcedd53c2f584adfb00e85bac4b</span> <span style="color: #374478;">manager</span></pre>
</div>
Maintenant que l'on dispose d'un petit cluster, en pointant dessus (merci Machine) on va pouvoir lancer des commandes docker.
<div class="org-src-container">
<pre class="src src-sh">$ $(<span style="color: #43783f;">docker-machine</span> <span style="color: #7a4f7e;">env</span> <span style="color: #4f5c7e;">--swarm</span> <span style="color: #374478;">manager</span>) <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">Le bon vieux info</span> $ <span style="color: #783778;">docker</span> <span style="color: #784437;">info</span> <span style="color: #3f7178;">Containers:</span> <span style="color: #4f5c7e;">4</span> <span style="color: #4f5c7e;">Nodes:</span> <span style="color: #7e544f;">3</span> <span style="color: #374478;">manager:</span> <span style="color: #7e544f;">192.168.99.103:2376</span> <span style="color: #78683f;">└</span> <span style="color: #3f7178;">Containers:</span> <span style="color: #513f78;">2</span> <span style="color: #78683f;">└</span> <span style="color: #4f7e67;">Reserved</span> <span style="color: #4f5c7e;">CPUs:</span> <span style="color: #784437;">0</span> <span style="color: #783778;">/</span> <span style="color: #4f5c7e;">4</span> <span style="color: #78683f;">└</span> <span style="color: #4f7e67;">Reserved</span> <span style="color: #7a4f7e;">Memory:</span> <span style="color: #784437;">0</span> <span style="color: #4f7e67;">B</span> <span style="color: #783778;">/</span> <span style="color: #707e4f;">999.9</span> <span style="color: #783f5a;">MiB</span> <span style="color: #78683f;">node1:</span> <span style="color: #513f78;">45.55.160.223:2376</span> <span style="color: #78683f;">└</span> <span style="color: #3f7178;">Containers:</span> <span style="color: #78683f;">1</span> <span style="color: #78683f;">└</span> <span style="color: #4f7e67;">Reserved</span> <span style="color: #4f5c7e;">CPUs:</span> <span style="color: #784437;">0</span> <span style="color: #783778;">/</span> <span style="color: #78683f;">1</span> <span style="color: #78683f;">└</span> <span style="color: #4f7e67;">Reserved</span> <span style="color: #7a4f7e;">Memory:</span> <span style="color: #784437;">0</span> <span style="color: #4f7e67;">B</span> <span style="color: #783778;">/</span> <span style="color: #4f7e67;">490</span> <span style="color: #783f5a;">MiB</span> <span style="color: #513f78;">node2:</span> <span style="color: #707e4f;">swarm-nwde2.cloudapp.net:2376</span> <span style="color: #78683f;">└</span> <span style="color: #3f7178;">Containers:</span> <span style="color: #78683f;">1</span> <span style="color: #78683f;">└</span> <span style="color: #4f7e67;">Reserved</span> <span style="color: #4f5c7e;">CPUs:</span> <span style="color: #784437;">0</span> <span style="color: #783778;">/</span> <span style="color: #78683f;">1</span> <span style="color: #78683f;">└</span> <span style="color: #4f7e67;">Reserved</span> <span style="color: #7a4f7e;">Memory:</span> <span style="color: #784437;">0</span> <span style="color: #4f7e67;">B</span> <span style="color: #783778;">/</span> <span style="color: #7e544f;">1.639</span> <span style="color: #3f7178;">GiB</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">On démarre des nginx</span> $ <span style="color: #4f7e67;">for</span> <span style="color: #7a4f7e;">i</span><span style="color: #0000ff;"> in</span> <span style="color: #ff1493;">`seq 1 3`</span>; <span style="color: #0000ff;">do</span> <span style="color: #783778;">docker</span> <span style="color: #513f78;">run</span> <span style="color: #43783f;">-d</span> <span style="color: #783f5a;">-p</span> <span style="color: #7a4f7e;">80:80</span> <span style="color: #783778;">nginx</span>; <span style="color: #0000ff;">done</span> $ <span style="color: #783778;">docker</span> <span style="color: #43783f;">ps</span> <span style="color: #374478;">CONTAINER</span> <span style="color: #707e4f;">ID</span>    <span style="color: #707e4f;">IMAGE</span>       <span style="color: #78683f;">COMMAND</span>                <span style="color: #78683f;">...</span> <span style="color: #3f7178;">PORTS</span>                                <span style="color: #707e4f;">NAMES</span> <span style="color: #374478;">9bff07d8ee18</span>    <span style="color: #78683f;">nginx:1.7</span>   <span style="color: #008000;">"nginx -g 'daemon of   ... 443/tcp, 104.210.33.180:80-&gt;80/tcp   node1/loving_torvalds</span> <span style="color: #008000;">457ed59c9bb3    nginx:1.7   "</span><span style="color: #783778;">nginx</span> <span style="color: #78683f;">-g</span> <span style="color: #008000;">'daemon of   ... 443/tcp, 45.55.160.223:80-&gt;80/tcp    node2/drunk_swartz</span> <span style="color: #008000;">6013be18cdbc    nginx:1.7   "nginx -g '</span><span style="color: #3f7178;">daemon</span> <span style="color: #5e7837;">of</span>   <span style="color: #78683f;">...</span> <span style="color: #784437;">443/tcp,</span> <span style="color: #4f7e67;">192.168.99.103:80-</span>&gt;<span style="color: #5e7837;">80/tcp</span>   <span style="color: #707e4f;">manager/condescending_galileo</span></pre>
</div>
On voit qu'on a démarré nginx sur les 3 nœuds. Swarm a quelques stratégies pour démarrer un conteneur sur un nœud ou l'autre :
<ul class="org-ul">
	<li><code>spread</code> va éparpiller les conteneurs pour que chaque nœud en ait le moins possible (répartis).</li>
	<li><code>binpack</code> va faire l'inverse (tout sur le même nœud jusqu'à ce que ses ressources soient épuisés).</li>
	<li><code>random</code> qui fait <em>au pif</em>.</li>
</ul>
Il est également possible de mettre des contraintes lors du lancement d'un conteneur, en utilisant le flag <code>-e</code> de <code>docker run</code> (<code>-e</code> = variables d'environnement).
<div class="org-src-container">
<pre class="src src-sh"><span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">Démarrer postgres sur un host qui a le label storage=ssd</span> $ <span style="color: #783778;">docker</span> <span style="color: #513f78;">run</span> <span style="color: #43783f;">-d</span> <span style="color: #707e4f;">-e</span> <span style="color: #4f5c7e;">constraint:</span><span style="color: #4f5c7e;">storage</span>==<span style="color: #5e7837;">ssd</span> <span style="color: #4f5c7e;">--name</span> <span style="color: #4f7e67;">postgres</span> <span style="color: #4f7e67;">postgres</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">Démarrer redis à coté du conteneur dont le nom est postgres</span> $ <span style="color: #783778;">docker</span> <span style="color: #513f78;">run</span> <span style="color: #43783f;">-d</span> <span style="color: #707e4f;">-e</span> <span style="color: #4f5c7e;">affinity:</span><span style="color: #4f5c7e;">container</span>==<span style="color: #4f7e67;">postgres</span> <span style="color: #4f5c7e;">--name</span> <span style="color: #7e544f;">redis</span> <span style="color: #7e544f;">redis</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">Démarer backend où tu veux, mais comme les links sont des contraintes</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">implicites il démarrera sur le même host que postgres ET redis</span> <span style="color: #8d8d84;"># </span><span style="color: #8d8d84; font-style: italic;">(ou ne démarrera pas ces derniers ne sont pas au même endroit)</span> $ <span style="color: #783778;">docker</span> <span style="color: #513f78;">run</span> <span style="color: #43783f;">-d</span> <span style="color: #374478;">--link</span> <span style="color: #3f7178;">redis:redis</span> <span style="color: #374478;">--link</span> <span style="color: #784437;">postgres:db</span> <span style="color: #4f5c7e;">--name</span> <span style="color: #78683f;">backend</span> <span style="color: #78683f;">backend</span></pre>
</div>
On pourrait faire un article dédié à Swarm (ce qui sera probablement le cas dans un avenir assez proche) donc je vous laisse le découvrir via <a href="https://github.com/docker/swarm/">github</a>.

</div>
</div>
</div>
<div id="outline-container-sec-11" class="outline-2">
<h2 id="sec-11">Levées de fonds et acquisitons</h2>
<div id="text-11" class="outline-text-2">

Docker est sur toutes les lèvres en ce moment. Il est donc normal que cela attire également des capitaux. Le 14 avril dernier, Docker annonçait une nouvelle levée de fonds de <strong>95 millions</strong> de dollars. Après celle de <em>40 millions</em> en Septembre 2014, on peut se dire que Docker Inc. a de beaux jours à venir.

Docker Inc. « mange » aussi quelques startups, puisque après Orchard, qui éditait fig (devenu docker-compose), ils ont fait l'acquisition de <a href="http://socketplane.io/">Socketplane</a> et <a href="https://kitematic.com/">Kitematic</a>. Kitematic est un outil <em>desktop</em> qui permet de facilement utiliser Docker sous Mac OS X, une belle application, un peu « clickodrome » <code>;-P</code>. <a href="http://socketplane.io/">Socketplane</a> est une solution réseau qui connectait Open vSwitch avec Docker — nulle doute que la récente libnetwork vient de là.

</div>
</div>
<div id="outline-container-sec-12" class="outline-2">
<h2 id="sec-12">Évènements</h2>
<div id="text-12" class="outline-text-2">

Nous allons finir avec une liste non-exhaustive et un peu orientée des évènements <em>marquants</em> qui se sont passés ces derniers mois :
<ul class="org-ul">
	<li>Le <a href="https://blog.docker.com/2014/11/docker-tour-de-france/">Docker Tour de France</a>, avec notament un <a href="http://www.meetup.com/Docker-Paris/events/218767688/">hackathon</a> organisé à l'<a href="http://www.epitech.eu/paris/ecole-informatique-paris.aspx">Epitech</a>, où <a href="https://twitter.com/mariolet">Mario Loriedo</a> a bootstrapé son projet Sublime-docker avec <a href="https://github.com/mjbright">Mike Bright</a> et à du coup gagné sa place à la DockerCon de 2015.</li>
	<li>Les <a href="http://blog.zenika.com/index.php?post/2015/02/19/NightClazz-Docker-Avance">Nightclazz</a> <a href="http://zenika.github.io/NC-Docker-Decouverte/">découverte</a> et <a href="http://zenika.github.io/NC-Docker-Avance/#/">avancée</a> hébergé par Zenika, présenté par Mario Loriedo et moi-même ;-).</li>
	<li>La DockerCon Europe.</li>
	<li>Le <a href="http://docker.party/">Docker Birthday</a>, gigantesque <em>Open-source-athon</em> tout autour du monde — une véritable réussite, tant au niveau de l'organisation (des évènements, la préparation en amont des <em>issues</em>, etc.) que de ce qu'il en est <a href="https://blog.docker.com/2015/05/dockers-2nd-birthday-by-the-numbers/">ressorti</a>.</li>
</ul>
</div>
</div>