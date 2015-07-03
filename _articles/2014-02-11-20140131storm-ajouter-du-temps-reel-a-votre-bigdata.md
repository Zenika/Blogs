---
ID: 201
post_title: 'Storm &#8211; Ajouter du temps réel à votre BigData'
author: Florian Hussonois
post_date: 2014-02-11 10:45:00
post_excerpt: '<h2>Partie 1 - Introduction aux topologies, mécanismes et API</h2> <p><q><em>Par principe les traitements par batchs sont trop lents et la vision qu’ils nous donnent de nos données est dépassée de la réalité.</em></q></p> <p>Storm est un système de calculs temps réel distribué et tolérant aux pannes. Développé à l’origine par la société BackType, le projet est devenu open-source après l’acquisition de la société par Twitter. Il est disponible sous licence Eclipse Public License 1.0. De plus, Storm est entré depuis quelques mois dans le processus d’incubation de la fondation Apache.</p> <p>L’objectif de ce premier billet est de vous introduire les différents concepts et mécanismes mis en œuvres dans Storm.</p>'
layout: post
permalink: http://blog.zenika-offres.com/?p=201
published: true
slide_template:
  - default
---
<h2>Partie 1 - Introduction aux topologies, mécanismes et API</h2>
<q><em>Par principe les traitements par batchs sont trop lents et la vision qu’ils nous donnent de nos données est dépassée de la réalité.</em></q>

Storm est un système de calculs temps réel distribué et tolérant aux pannes. Développé à l’origine par la société BackType, le projet est devenu open-source après l’acquisition de la société par Twitter. Il est disponible sous licence Eclipse Public License 1.0. De plus, Storm est entré depuis quelques mois dans le processus d’incubation de la fondation Apache.

L’objectif de ce premier billet est de vous introduire les différents concepts et mécanismes mis en œuvres dans Storm.

<!--more-->
<h3>Introduction aux topologies</h3>
<h4>Les concepts</h4>
Pour traiter en continu un ou plusieurs flux de données, Storm repose sur la définition d’une topologie. Une topologie prend la forme d’un graphe orienté acyclique dans lequel :
<ul>
	<li>Les <strong>Streams</strong>, symbolisés par les arcs, sont des séquences illimitées de <strong>Tuples</strong>. Un tuple est une liste de valeurs nommées qui représente le modèle de données utilisé par Storm.</li>
</ul>
<ul>
	<li>Les <strong>Spouts</strong>, nœuds racine du graphe, désignent les sources de streams. Il peut s’agir par exemple d’une séquence de tweets émis via l’API Twitter, d’un flux de logs ou encore de données lues directement depuis une base de données.</li>
</ul>
<ul>
	<li>Et enfin, les <strong>Bolts</strong> sont les nœuds qui consomment ces séquences de tuples émis par un ou plusieurs nœuds. Ils ont pour rôle de réaliser différentes opérations (filtres, agrégations, jointures, lecture/écriture vers et depuis une base de données, etc.) et si besoin d'émettre à leur tour une nouvelle séquence de tuples.</li>
</ul>
<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.figure-1-topology-example_m.jpg" alt="figure-1-topology-example.png" /> La figure, ci-dessus, illustre une topologie qui indexe dans Elasticsearch des tweets récupérés via l’API Twitter, calcule le nombre unique et total d’utilisateurs ainsi que le nombre de tweets émis par langue. Enfin, les statistiques sont stockées dans Redis.

Ainsi, chaque fois qu’un nouveau tweet est reçu il est immédiatement émis par le Spout vers chacun des Bolts ayant souscrit à son flux de sortie (stream). Le réseau ainsi formé par l'association de Spouts et de Bolts forme une topologie qui sera ensuite soumise au cluster Storm et exécutée sans interruption.
<h4>Regroupements de flux</h4>
Le regroupement de flux répond à la question suivante: lorsqu’un tuple est émis, vers quels bolts doit-il être dirigé ? En d’autres termes, il s’agit de spécifier la manière dont les flux sont partitionnés entre les différentes instances d’un même composant spout ou bolt. Pour cela, Storm fournit un ensemble de regroupements prédéfinis, dont voici les principales définitions : <img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.figure-2-storm-stream-grouping_s.jpg" alt="figure-2-storm-stream-grouping.png" />
<ol>
	<li><strong>Shuffle grouping</strong> : Les tuples sont distribués aléatoirement vers les différentes instances bolts de manière à ce que chacune reçoive un nombre égal de tuples.</li>
	<li><strong>Fields grouping</strong> : Le flux est partitionné en fonction d’un ou plusieurs champs.</li>
	<li><strong>All grouping</strong>: Le flux est répliqué vers l’ensemble des instances. Cette méthode est à utiliser avec précaution puisqu'elle génère autant de flux qu’il y a d’instances.</li>
	<li><strong>Global grouping</strong>: L’ensemble du flux est redirigé vers une même instance. Dans le cas, où il y en a plusieurs pour un même bolt, le flux est alors redirigé vers celle ayant le plus petit identifiant.</li>
</ol>
Storm fournit d’autres regroupements ainsi qu’une interface permettant d’implémenter ses propres regroupements.
<h3>Parallélisme d’une topologie</h3>
<h4>Mécanisme de parallélisation</h4>
Lorsqu’une topologie est soumise à Storm, celui-ci répartit l’ensemble des traitements implémentés par vos composants à travers le cluster. Chaque composant est alors exécuté en parallèle sur une ou plusieurs machines.

La figure suivante illustre la manière dont Storm exécute une topologie composée d’un Spout et de deux Bolts. <img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.figure-3-storm-parallelism_m.jpg" alt="figure-3-storm-parallelism.png" /> Pour chaque topologie, Storm gère un ensemble d’entités distinctes:
<ul>
	<li>Un « <strong>worker process</strong> » est une JVM s’exécutant sur une machine du cluster. Il a pour rôle de coordonner l'exécution d'un ou plusieurs composants (spouts ou bolts) appartenant à une même topologie. (NB: Le nombre de workers associés à une topologie peut changer au cours du temps.)</li>
</ul>
<ul>
	<li>Un « <strong>executor</strong> » est un thread lancé par un « worker process ». Il est chargé d'exécuter une ou plusieurs « task » pour un bolt ou spout spécifique. (NB: Le nombre d’exécuteurs associés à un composant peut changer au cours du temps.)</li>
</ul>
<ul>
	<li>Les « <strong>tasks</strong> » effectuent les traitements à appliquer sur les données. Chaque task représente une instance unique d'un bolt ou d’un spout.</li>
</ul>
Contrairement au nombre d’exécuteurs, le nombre de tâches pour un composant ne peut pas être modifié pendant toute la durée de vie d’une topologie.

Par défaut, Storm instanciera autant de tâches qu’il y a d’exécuteurs. C’est à dire, qu’une seule tâche sera alors exécutée par thread. Cette répartition correspond normalement au comportement recherché.

Cependant, il est possible de configurer un plus grand nombre de tâches. Dans ce cas, plusieurs tâches seront exécutées en série par un même thread. Le fait de provisionner plus d’instances, pour un même composant, offre par la suite la possibilité d’étendre le parallélisme d’une topologie sans l’arrêter mais aussi de garantir son bon fonctionnement en cas de perte d'un worker; on établit de manière générale que <strong>#threads &lt;= #tasks</strong>.

Regardons maintenant comment configurer notre topologie.
<h4>Configuration</h4>
Storm expose un ensemble de paramètres pour configurer votre cluster ainsi que les topologies associées. Certains de ces paramètres sont des configurations système et ne peuvent pas être modifiés par une topologie. D’autres spécifient le comportement des topologies exécutées et sont donc modifiables par topologie. Chacun de ces paramètres possède une valeur par défaut définie dans un fichier <code>config/default.yaml</code>; ce dernier est inclus de base dans toute distribution Storm.

Tout d’abord, il est possible de surcharger ces valeurs en créant un fichier config/storm.yaml. Vous pouvez créer un fichier différent pour chaque machine et ainsi ajuster vos paramètres en fonction de la puissance de calculs dont vous disposez.

Storm offre ensuite la possibilité de définir une configuration par topologie au moyen de la classe backtype.storm.Config disponible depuis l’API Java. La configuration est alors soumise au cluster en même temps que la topologie. Remarquez que seuls les paramètres préfixés par TOPOLOGY peuvent être surchargés par une topologie.

Voici quelques options qu’il est possible de modifier:
<ul>
	<li><strong>TOPOLOGY_WORKERS</strong> : Définit le nombre de workers à créer pour une topologie</li>
	<li><strong>TOPOLOGY_DEBUG</strong> : Indique à Storm de logger chaque message émis.</li>
</ul>
Pour chacune de ces options il est possible d’utiliser directement les méthodes Config#setNumWorkers et Config#setDebug

Enfin, l’API Java permet de spécifier une configuration par composant afin de paramétrer leur niveau de parallélisme (parallelism hint) des spouts/bolts. Ce paramétrage peut se faire des deux façons suivantes:
<ul>
	<li><strong>Interne</strong>: En surchargeant dans votre Spout ou Bolt la méthode <code>getComponentConfiguration</code> puis de retourner la Map correspondant à votre configuration.</li>
	<li><strong>Externe</strong>: En utilisant les méthodes exposées par les implémentations de l’interface <code>ComponentConfigurationDelacarer</code> retournées par les méthodes <code>setBolt</code> et <code>setSpout</code> du <code>TopologyBuilder</code>.</li>
</ul>
Pour finir, Storm prendra par ordre de préférence les configurations suivantes: <em>default
s.yaml &lt; storm.yaml &lt; topologie &lt; composant interne &lt; composant externe</em>
<h3>Une API Java simple garante du traitement des messages.</h3>
Storm fournit une API Java simple non seulement pour implémenter les composants nécessaires à l’exécution de la logique métier d’une topologie. Mais aussi pour s’assurer que tous les tuples émis seront aux moins traités une fois.
<h4>Les interfaces des différents composants</h4>
Le diagramme ci-dessous représente l’arborescence des principales interfaces/classes permettant de modéliser une topologie.

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/figure-4-storm-java-api.png" alt="figure-4-storm-java-api.png" />
Pour commencer, IRichBolt et IRichSpout sont les deux principales interfaces utilisées pour implémenter des Spouts/Bolts en Java.
<h5>IComponent</h5>
L’interface <code>IComponent</code> expose les méthodes nécessaires à l’utilisation de l’API Java. Ces dernières sont utilisées directement par l’API Storm lors de la construction et configuration d’une topologie depuis une instance <code>TopologyBuilder</code>.
<pre class="java code java" style="font-family: inherit;"><span style="color: #000000; font-weight: bold;">public</span> <span style="color: #000000; font-weight: bold;">interface</span> IComponent <span style="color: #000000; font-weight: bold;">extends</span> <span style="color: #003399;">Serializable</span> <span style="color: #009900;">{</span>     <span style="color: #000066; font-weight: bold;">void</span> declareOutputFields<span style="color: #009900;">(</span>OutputFieldsDeclarer outputFieldsDeclarer<span style="color: #009900;">)</span><span style="color: #339933;">;</span>     Map<span style="color: #339933;">&lt;</span>String, Object<span style="color: #339933;">&gt;</span> getComponentConfiguration<span style="color: #009900;">(</span><span style="color: #009900;">)</span><span style="color: #339933;">;</span> <span style="color: #009900;">}</span></pre>
<ol>
	<li>D’une part, chaque composant doit déclarer le schéma des tuples émis depuis l’objet <code>OutputFieldDeclarer</code>, passé en argument de la méthode <code>declareOutputFields</code>. D’autre part, il est possible de définir des paramètres spécifiques en surchargeant la méthode <code>getComponentConfiguration</code>.</li>
</ol>
<h5>ISpout</h5>
Les méthodes héritées des interfaces <code>ISpout</code> et <code>IBolt</code> sont, quant à elles, invoquées directement par Storm après soumission de la topologie au cluster. Elles exposent l’ensemble des méthodes appelées au cours du cycle de vie de notre topologie.
<pre class="java code java" style="font-family: inherit;"><span style="color: #000000; font-weight: bold;">public</span> <span style="color: #000000; font-weight: bold;">interface</span> ISpout <span style="color: #000000; font-weight: bold;">extends</span> <span style="color: #003399;">Serializable</span> <span style="color: #009900;">{</span>    <span style="color: #000066; font-weight: bold;">void</span> open<span style="color: #009900;">(</span><span style="color: #003399;">Map</span> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector<span style="color: #009900;">)</span><span style="color: #339933;">;</span>     <span style="color: #000066; font-weight: bold;">void</span> close<span style="color: #009900;">(</span><span style="color: #009900;">)</span><span style="color: #339933;">;</span>     <span style="color: #000066; font-weight: bold;">void</span> nextTuple<span style="color: #009900;">(</span><span style="color: #009900;">)</span><span style="color: #339933;">;</span>     <span style="color: #000066; font-weight: bold;">void</span> ack<span style="color: #009900;">(</span><span style="color: #003399;">Object</span> o<span style="color: #009900;">)</span><span style="color: #339933;">;</span>     <span style="color: #000066; font-weight: bold;">void</span> fail<span style="color: #009900;">(</span><span style="color: #003399;">Object</span> o<span style="color: #009900;">)</span><span style="color: #339933;">;</span> <span style="color: #009900;">}</span></pre>
<ol>
	<li>Storm commence par appeler la méthode <code>open</code> pour initialiser une instance de spout. Cette méthode permet notamment de récupérer l’instance <code>SpoutOutputCollector</code> utilisée ensuite pour émettre des tuples.</li>
	<li>Il invoque ensuite en continu la méthode <code>nextTuple</code> pour ordonner à la tâche d’émettre son prochain tuple au moyen du <code>SpoutOutputCollector</code>. Pour ne pas surcharger la consommation CPU, il est d’usage d’endormir le thread (quelques millisecondes) lorsqu’il n’y aucun tuple à émettre.</li>
	<li>Et enfin, la méthode <code>close</code> est invoquée lorsqu'un Spout est sur le point d’être arrêté. En production, il n’y a cependant aucune garantie qu’elle soit appelée, notamment dans le cas où le worker exécutant la tâche est tué (kill -9). Néanmoins, elle est principalement utile lorsque vous arrêtez une topologie exécutée avec un cluster en mode local.</li>
</ol>
<h5>IBolt</h5>
<pre class="java code java" style="font-family: inherit;"><span style="color: #000000; font-weight: bold;">public</span> <span style="color: #000000; font-weight: bold;">interface</span> IBolt <span style="color: #000000; font-weight: bold;">extends</span> <span style="color: #003399;">Serializable</span> <span style="color: #009900;">{</span>     <span style="color: #000066; font-weight: bold;">void</span> prepare<span style="color: #009900;">(</span><span style="color: #003399;">Map</span> map, TopologyContext topologyContext, OutputCollector outputCollector<span style="color: #009900;">)</span><span style="color: #339933;">;</span>     <span style="color: #000066; font-weight: bold;">void</span> execute<span style="color: #009900;">(</span>Tuple tuple<span style="color: #009900;">)</span><span style="color: #339933;">;</span>     <span style="color: #000066; font-weight: bold;">void</span> cleanup<span style="color: #009900;">(</span><span style="color: #009900;">)</span><span style="color: #339933;">;</span> <span style="color: #009900;">}</span></pre>
<ol>
	<li>L’initialisation d’une instance associée à un Bolt s’effectue via la méthode <code>prepare</code>. De la même manière que pour un Spout, Storm fournit un <code>OutputCollector</code> pour émettre des tuples.</li>
	<li>La méthode <code>execute</code> permet ensuite de traiter les tuples reçus en entrée. De manière générale, la logique métier de notre bolt sera exposée via cette méthode. Par ailleurs, il n’est pas obligatoire qu’un tuple soit immédiatement traité après réception.</li>
	<li>Enfin, la méthode <code>cleanup</code> est identique à la méthode <code>close</code> de l’interface <code>ISpout</code>.</li>
</ol>
<h4>Fiabilité du traitement des messages</h4>
<h5>Quand un tuple est-il complètement traité ?</h5>
Un tuple émis par un spout a de grandes chances d'entraîner l’émission de plusieurs nouveaux tuples dès lors qu’il est traité par une tâche bolt. Par exemple, un tuple A peut entraîner la création d'un tuple B et C qui entraînerons à leur tour la création d’un tuple D et E. Le tuple racine et ses tuples fils forment alors un arbre de messages. <img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.figure-4-storm-tuple-tree_s.jpg" alt="figure-4-storm-tuple-tree.jpg" />
L’API Storm garantit que chaque tuple émis depuis un spout sera au moins une fois<em> « complètement traité »</em>. Storm désigne un tuple racine comme étant « complètement traité » dès lors que les n-tuples qui composent son arbre de messages ont été traités avec succès. A l’inverse, il sera considéré en échec s’il n’est pas traité dans un laps de temps imparti. Par défaut, ce timeout est défini à 30 secondes et peut être modifié spécifiquement pour une topologie.

Pour indiquer à storm qu'un tuple doit être traqué, il faut lui associer un identifiant lors de son émission :
<pre class="java code java" style="font-family: inherit;">spoutOutputCollector.<span style="color: #006633;">emit</span><span style="color: #009900;">(</span> <span style="color: #000000; font-weight: bold;">new</span> Values<span style="color: #009900;">(</span><span style="color: #0000ff;">"field1"</span>, “field2<span style="color: #009900;">)</span>, messageId<span style="color: #009900;">)</span><span style="color: #339933;">;</span></pre>
Storm se chargera ensuite de suivre l’arbre de messages qui sera créé. Lorsque le tuple sera identifié comme étant <em>« complètement traité »</em>, la méthode <code>ack</code> de la tâche spout d’origine sera invoquée et l'identifiant du message sera passé en argument. En règle générale, une implémentation de cette méthode a pour rôle de supprimer le message d’origine de la source de données afin de ne pas l’émettre à nouveau. Au contraire si le traitement du tuple échoue alors la méthode <code>fail</code> sera appelée. Le spout pourra alors réémettre le tuple.
<h4>Ancrage et acquittement d’un tuple</h4>
Afin de bénéficier de ce mécanisme, il est nécessaire dans un premier temps d'indiquer à Storm lorsqu'une nouvelle branche est créée dans l’arbre de tuples. Cette première étape, appelée ancrage, consiste à spécifier au sein d’une tâche bolt le ou les tuples entrants auxquels le tuple émis doit être rattaché. Ainsi, si le tuple émis échoue, vous serez en mesure de rejouer le tuple racine.
<pre class="java code java" style="font-family: inherit;"><span style="color: #000000; font-weight: bold;">this</span>.<span style="color: #006633;">outputCollector</span>.<span style="color: #006633;">emit</span><span style="color: #009900;">(</span>inputTuple, <span style="color: #000000; font-weight: bold;">new</span> Values<span style="color: #009900;">(</span><span style="color: #0000ff;">"field1"</span>, <span style="color: #0000ff;">"field2"</span><span style="color: #009900;">)</span><span style="color: #009900;">)</span><span style="color: #339933;">;</span></pre>
A l’inverse, il est possible d'émettre un tuple sans ancrage selon le niveau de garantie attendu dans votre topologie.
<pre class="java code java" style="font-family: inherit;"><span style="color: #000000; font-weight: bold;">this</span>.<span style="color: #006633;">outputCollector</span>.<span style="color: #006633;">emit</span><span style="color: #009900;">(</span><span style="color: #000000; font-weight: bold;">new</span> Values<span style="color: #009900;">(</span><span style="color: #0000ff;">"field1"</span>, <span style="color: #0000ff;">"field2"</span><span style="color: #009900;">)</span><span style="color: #009900;">)</span><span style="color: #339933;">;</span></pre>
Cependant, si par la suite le tuple échoue alors il ne sera pas possible de rejouer le tuple racine puisque storm ne notifiera pas le spout d’origine via sa méthode ack.

Dans un second et dernier temps, il est nécessaire d’indiquer à storm quand un tuple a été traité individuellement; cette dernière étape est appelée acquittement. Pour ce faire, nous utilisons les méthodes <code>ack</code> et <code>fail</code> de l’OutputCollector. Pour rappel, storm nous fournit cette instance lors de l’appel à la méthode <code>prepare</code> d’un bolt.
<pre class="java code java" style="font-family: inherit;"><span style="color: #000000; font-weight: bold;">this</span>.<span style="color: #006633;">outputCollector</span>.<span style="color: #006633;">ack</span><span style="color: #009900;">(</span>tuple<span style="color: #009900;">)</span><span style="color: #339933;">;</span> 	ou 	<span style="color: #000000; font-weight: bold;">this</span>.<span style="color: #006633;">outputCollector</span>.<span style="color: #006633;">fail</span><span style="color: #009900;">(</span>tuple<span style="color: #009900;">)</span><span style="color: #339933;">;</span></pre>
Lorsqu’un tuple n’est pas acquitté il est automatiquement considéré en échec après 30 secondes et le tuple racine est alors rejoué. Mais il peut être utile dans certains cas, par exemple après avoir attrapé une exception, de rejouer immédiatement le tuple d’origine. Pour cela, il est possible d’utiliser la méthode <code>fail</code>.

Pour mieux comprendre le mécanisme d’ancrage et d’acquittement intéressons- nous au cycle de vie d'un tuple.
<h5>Les Ackers</h5>
Pour traquer la complétion des tuples émis depuis les tâches spout, storm instancie pour chaque topologie un ensemble de tâches appelées ackers. Par défaut, le nombre d’ackers est fixé à 1 mais peut être modifié pour les topologies générant un grand nombre de messages.

Le schéma ci-dessous illustre le rôle que jouent les ackers dans l’algorithme mis en place par storm.
<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.figure-5-storm-tuple-ack_m.jpg" alt="figure-5-storm-tuple-ack.png" />
La soumission d’un tuple depuis un spout ou un bolt se traduit dans storm par l’envoi d’un message associé à un identifiant aléatoire de 64 bits <strong>(1)</strong>. Ces identifiants sont ensuite utilisés par les ackers pour suivre l’état de chaque arbre de tuples.

<strong>(2)</strong> Lors de l’émission d’un nouveau tuple depuis un bolt, les identifiants des tuples racines sont recopiés depuis le tuple ancré vers le nouveau tuple. Si bien que, chaque tuple connaît l’ensemble des identifiants des tuples racines de leur arbre de messages.
Puis, lorsqu’un tuple est acquitté, un nouveau message est envoyé à l’acker avec les informations liées aux modifications de l’arbre <strong>(3)</strong>; c’est à dire les identifiants des nouveaux tuples et des tuples traités. Storm prend alors connaissance de son état et détermine s'il est complété ou non. Puisque l'état de l'arbre n'est connu qu’à l'acquittement il ne peut donc pas être prématurément complété.

Pour autant, toute la force de l'algorithme de Storm repose dans le fait que l’état de chaque arbre n’est pas stocké à proprement parlé par les ackers. Au lieu de cela, et pour ne pas accentuer la consommation mémoire, un acker maintient une map associant l’identifiant d’un tuple spout à une paire de valeurs. La première valeur correspond à l’identifiant de la tâche spout ayant soumis le tuple et est utilisée pour envoyer le message de complétion. <strong>(4)</strong> La deuxième valeur appelée <code>« ack val »</code> est le résultat d’un XOR sur les identifiants des tuples créés et acquittés de l’arbre. Ainsi quand la <code>« ack val »</code> passe à 0 l’arbre est identifié comme étant complètement traité<strong> (7)</strong>.
<h5>Cas d’erreurs système</h5>
Pour finir, intéressons-nous maintenant aux différents cas d’erreurs qui peuvent survenir lors de l’exécution d’une topologie et comment les mécanismes de storm permettent qu’aucune donnée ne soit perdue.

On distingue les trois cas d’erreurs suivants:
<ul>
	<li><strong>Une tâche bolt s’arrête avant d’avoir pu acker un tuple</strong> : Aucun ack ne sera réalisé avant le timeout, le tuple sera alors automatiquement réémis.</li>
	<li><strong> La tâche acker s’arrête</strong> : L’ensemble des tuples gérés par cet acker sera alors mis en échec.</li>
	<li><strong> La tâche spout s’arrête</strong> : Dans ce dernier cas, storm ne fournit pas nativement de solution. La source de données utilisée par le spout est alors responsable de rejouer le message.</li>
</ul>
<h3>Architecture d’un cluster</h3>
<h4>Cycle de vie d’une topologie</h4>
<h5>Cluster mode</h5>
Une topologie est soumise au cluster via <strong>Nimbus</strong>. Nimbus est le nom donné au nœud maître d’un cluster storm. Il est responsable de distribuer le code à travers le cluster, d’assigner les tâches aux workers nodes et enfin de monitorer les erreurs. Ensuite, les workers nodes sont chargés d'exécuter des daemons appelées <strong>Supervisors</strong>. Chaque supervisor écoute les tâches assignées à sa machine puis démarre et stoppe des « worker processes » en conséquence. Enfin, comme nous l’avons vu précédemment chaque « worker process » exécute un sous-ensemble d’une topologie.

Storm s’appuie ensuite sur <a title="zookeeper" href="http://zookeeper.apache.org/">Apache Zookeeper</a> pour coordonner les échanges entres nimbus et les supervisors ainsi que pour stocker l’état du cluster afin de rendre les daemons stateless et fail-fast. De cette manière, si le daemon Nimbus ou un des Supervisors venait à s’arrêter il serait automatiquement redémarré par l’instance Zookeeper.
<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.figure-6-storm-architecture_m.jpg" alt="figure-6-storm-architecture.png" />
Storm utilise nativement la librairie <strong><a title="0MQ" href="http://zeromq.org/">ZeroMQ</a></strong> pour la communication inter-worker, c’est à dire de nœud-à-nœud. Depuis la version <a title="storm090-released" href="http://storm-project.net/2013/12/08/storm090-released.html" hreflang="en">0.9.0</a> il est possible d'utiliser <strong><a title="netty.io" href="http://netty.io/index.html">Netty</a></strong> comme implémentation offrant ainsi une alternative purement Java à 0MQ. Puis la librairie <strong><a title="lmax-disruptor" href="http://lmax-exchange.github.io/disruptor/">LMAX Disruptor</a></strong> pour les échanges intra-worker; c’est à dire de thread à thread au sein d’un même nœud. Enfin, Storm ne fournit aucun mécanisme pour faire communiquer plusieurs topologies ensemble.

Pour exécuter une topologie en mode cluster (production), il est nécessaire de passer par le client storm, d’une part pour démarrer les daemons de la manière suivante:
<ul>
	<li><code>$ storm/bin/storm nimbus</code></li>
	<li><code>$ storm/bin/storm supervisor</code></li>
</ul>
Et d’autre part pour soumettre la topologie en spécifiant le chemin vers notre jar, la classe main ainsi que les éventuels arguments.

<code>$ storm/bin/stormstorm jar path/to/allmycode.jar org.me.MyTopology arg1 arg2 arg3</code>

A l’exécution de la commande storm jar le code est distribué à travers le cluster.

Pour ensuite arrêter une topologie, il suffit d’exécuter la commande storm kill en passant en argument le nom de notre topologie.
<h5>Local mode</h5>
Pour faciliter le développement et les tests, il est possible d’exécuter une topologie en mode local. Le fonctionnement est identique au mode cluster.
<h4>Storm UI</h4>
Enfin, il vous est possible de monitorer votre cluster via Storm UI en exécutant la commande <code>storm ui</code>. L’application est ensuite disponible à l’adresse <a title="http://localhost:8080" href="http://localhost:8080">http://localhost:8080</a>.
Cette interface est un bon point d'entrée pour tester votre configuration et analyser les effets positifs ou négatifs d’une configuration sur le cluster. Elle fournit des informations sur les erreurs provoquées par les tâches, le débit ainsi que les performances de latence de chaque composant de chaque topologie en cours d’exécution.
<h3>Conclusion</h3>
Pour finir je vous invite à visiter le site <a title="Storm Wiki" href="https://github.com/nathanmarz/storm/wiki" hreflang="en">Storm Wiki</a> ainsi que le <a title="Tutorial" href="https://github.com/nathanmarz/storm/wiki/Tutorial" hreflang="en">Tutorial</a> officiel sur Github pour de plus amples informations.

Dans le prochain article nous verrons, entre autre, comment implémenter la topologie illustrée dans ce premier billet. Merci à vous et n'hésitez pas à me faire part de vos remarques.
<h3>Références</h3>
<a title="Storm Tutorial" href="https://github.com/nathanmarz/storm/wiki/Tutorial" hreflang="en">Storm Tutorial</a>
<a title="Blog Michael. G. Noll" href="http://www.michael-noll.com/blog/categories/storm/" hreflang="en">Blog Michael. G. Noll</a>
<a title="Slideshare" href="http://fr.slideshare.net/Hadoop_Summit/realtime-analytics-with-storm" hreflang="en">Slideshare</a>
<strong>Storm Real-Time Processing Cookbook</strong> de Quinton Anderson