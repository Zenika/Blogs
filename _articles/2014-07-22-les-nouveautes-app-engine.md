---
ID: 202
post_title: Les nouveautés App Engine
author: Raphaël Martignoni
post_date: 2014-07-22 14:00:00
post_excerpt: "<p>Depuis 2009 App Engine met les serveurs de Google à la disposition de ses utilisateurs, afin d'y héberger leurs sites web. Cette solution permet de libérer les développeurs des problématiques de déploiement et de scalabilité. Ces dernières sont gérées entièrement par Google, la mise à l’échelle se faisant par la création automatique de nouvelles instances. Le modèle, qui permet aux usagers de la plateforme de payer proportionnellement aux capacités utilisées dans l’infrastructure, a déjà séduit beaucoup d’entreprises. Mais le service met surtout à disposition une plateforme de développement. Quatre langages d'exécution sont disponibles (Java, Python, Go, PHP), et de nombreux services sont accessibles &nbsp;: inutile par exemple de maintenir son propre serveur mail ou de base de données, App Engine fournit ces services et bien d'autres.&nbsp;</p>"
layout: post
permalink: >
  http://blog.zenika-offres.com/les-nouveautes-app-engine/
published: true
slide_template:
  - default
---
Depuis 2009 App Engine met les serveurs de Google à la disposition de ses utilisateurs, afin d'y héberger leurs sites web. Cette solution permet de libérer les développeurs des problématiques de déploiement et de scalabilité. Ces dernières sont gérées entièrement par Google, la mise à l’échelle se faisant par la création automatique de nouvelles instances.
Le modèle, qui permet aux usagers de la plateforme de payer proportionnellement aux capacités utilisées dans l’infrastructure, a déjà séduit beaucoup d’entreprises.

<!--more-->
Mais le service met surtout à disposition une plateforme de développement. Quatre langages d'exécution sont disponibles (Java, Python, Go, PHP), et de nombreux services sont accessibles : inutile par exemple de maintenir son propre serveur mail ou de base de données, App Engine fournit ces services et bien d'autres.

Les fonctionnalités les plus récentes d’App Engine vont plus loin dans la philosophie du paiement à l'utilisation, en permettant par exemple de rendre des portions d’applications scalables. De nouvelles API sont également développées pour étendre les possibilités d’App Engine et le SDK supporte plusieurs langages (Python, Java, PHP et Go) et frameworks (Django, Drupal). La version 1.9.6 d'App Engine, sortie début juin 2014 apporte d'ailleurs des améliorations au SDK Python, avec une mise à jour vers les versions 1.4.13 et 1.5.8 du framework Django. Enfin, la plateforme offre maintenant plus de liberté sur les VMs utilisées pour héberger ses applications. Jusqu'à présent, App Engine permettait de faire tourner des applications dans une "sandbox", composée d'un webserver, du code de l'application, et d'un langage d’exécution. Les Managed VMs offrent maintenant plus de liberté à l'utilisateur en lui permettant de configurer le type de machines virtuelles utilisées pour héberger son application.
<h3>Les managed VMs et Docker</h3>
Avant les managed VMs, le code d'une application App Engine était exécuté dans la "sandbox". L'utilisateur n'avait donc pas le choix de la VM utilisée, ni du langage d'exécution dont certaines librairies étaient restreintes (comme celles permettant l'écriture de fichiers). Les managed VMs offrent plus de liberté à l'utilisateur, ce dernier peut choisir des VMs configurables parmi celles proposées par App Engine, et les langages d'exécution ne sont plus soumis à restrictions.

Les utilisateurs de Docker pourront également utiliser ce système de conteneur <a href="http://techcrunch.com/2014/06/10/google-bets-big-on-docker-with-app-engine-integration-open-source-container-management-tool/">avec App Engine</a>. Docker permet d'empaqueter le code d'une application avec l'ensemble de ses librairies dans une image, et de l'exécuter sur n'importe quel type de système d'exploitation. Il est maintenant possible d'exécuter une image Docker sur une managed VM. L'association Docker - App Engine permet donc de profiter d'un plus grand choix sur la machine virtuelle utilisée, tout en gardant une grande simplicité d'utilisation lors du déploiement. En effet l'utilisateur peut se concentrer sur son code applicatif, Docker se chargera de créer un fichier qui pourra être déployé sur n'importe laquelle des machines virtuelles d'App Engine. En d'autres termes, App Engine offre plus de marge de manœuvre au développeur tandis que Docker maintient la simplicité du déploiement, essentielle à la philosophie d'App Engine.
<h3>Dart côté serveur</h3>
<div>L'utilisation de Dart côté serveur sur App Engine a été annoncée au Google I/O. Dart peut maintenant être utilisé comme langage commun entre le client et le serveur, tout en profitant des APIs fournies par App Engine.  Parmi les services d'ores et déjà utilisables : le cloud datastore, l'API memcache, User et Logging. Pour l'interaction avec le cloud  datastore, une api bas niveau est fournie, ainsi qu'une couche de mapping.</div>
<div><img style="margin: 50px auto 0; display: block;" title="dartserver" src="/wp-content/uploads/2015/07/dart_server_.png" alt="dartserver" />
<p style="text-align: center;">Image 1.1 : code d'un serveur dart</p>

</div>
<div><img style="margin: 50px auto 0; display: block;" title="modeldart" src="/wp-content/uploads/2015/07/model_dart_.png" alt="modeldart" />
<div>
<p style="text-align: center;">Image 1.2 : la couche de mapping (1)</p>

</div>
</div>
<div><img style="margin: 50px auto 0; display: block;" title="testmapping" src="/wp-content/uploads/2015/07/tests_.png" alt="testmapping" />
<p style="text-align: center;">Image 1.3 : la couche de mapping (2)</p>

<h3>Les Modules</h3>
Ils permettent de construire une application segmentée en composants. Les modules sont maintenant à utiliser en remplacement des Backends, dépréciés depuis mars 2014. Ces derniers étaient utilisés pour des applications nécessitant de meilleures performances de calcul, plus de mémoire, ou faisant tourner des processus assez longs (plus long que la deadline imposée à la réponse HTTP). Il est donc recommandé de mettre à jour les applications utilisant des Backends avec la nouvelle API Modules. <a href="https://developers.google.com/appengine/docs/java/modules/converting">La documentation développeur</a> explique comment passer de l’un à l’autre. Les modules ont des avantages supplémentaires, comme la possibilité d’être versionnés, et sont scalables automatiquement, contrairement aux Backends dont il faut obligatoirement spécifier le nombre d’instances à utiliser à l’avance.
<h5><em>Pourquoi utiliser des modules ?</em></h5>
Chaque module dispose d’au moins une instance, tout comme une application classique (sans modules) dispose d’une ou plusieurs instances. Le modèle de pricing se base sur le nombre d'instances de modules utilisées, qu’il est possible de faire gérer par Google de façon automatique. Il est alors plus facile d’optimiser une application en la découpant en modules, selon que ceux-ci sont plus ou moins utilisés, afin que le nombre d'instances allouées soit adapté à leur fréquence d'utilisation. De cette façon les coûts liés au fonctionnement des instances seront moins importants. La gestion du nombre d'instances peut se faire de trois façons :

- automatiquement, selon un certain nombre de métriques telles que le nombre de requêtes utilisateurs, et le temps de latence des réponses

- manuellement : l'utilisateur décide du nombre d'instances à allouer au module

- le "basic scaling" : les instances sont démarrées lorsque l'application reçoit des requêtes utilisateur, et arrêtées lorsque l'application est inutilisée.
<p style="text-align: center;"><img title="image1" src="/wp-content/uploads/2015/07/.image1_m.jpg" alt="image1" /></p>
<p style="text-align: center;">Image 2. Architecture d’un projet organisé en module</p>
Chaque module est composé de code source et de fichiers de configuration. La structure générale d'une application utilisant des modules est celle d'un fichier EAR (<span style="background-color: #ffffff; color: #222222; font-family: Roboto, sans-serif; font-size: 14px; line-height: 22.399999618530273px;">Java Enterprise Archive</span>) décompressé. Il est possible de communiquer des informations entre les modules, via le service URLFetch, et de faire collaborer plusieurs modules en assignant des tâches via l’API TaskQueue.

Le nombre de modules par application est limité et varie selon les comptes payants ou gratuits :

<img style="display: block; margin: 0 auto;" title="image2" src="/wp-content/uploads/2015/07/.image2_m.jpg" alt="image2" />
<p style="text-align: center;">Image 3.2 Limitation du nombre de modules et de versions</p>
<p style="text-align: center;"><img style="display: block; margin: 0 auto;" title="image3" src="/wp-content/uploads/2015/07/.image3_m.jpg" alt="image3" /> Image 3.3 Limitation du nombre d’instances</p>

<h3>Les nouvelles API</h3>
Chaque fonctionnalité de Google App Engine est classée selon l'un de ces trois statuts :
<ul>
	<li>celui de G.A. (Generally Available feature), qui signifie que la fonctionnalité est publiquement accessible et est couverte par la politique de SLA de Google. Cela signifie que son implémentation est stable, et que toute modification de l'API sera rétrocompatible.</li>
	<li>les fonctionnalités en preview ne sont pas couvertes par la politique de dépréciation de Google, des changements rétro-incompatibles peuvent être apportés. Ces fonctionnalités sont amenées à passer au statut de G.A..</li>
	<li>les API expérimentales ont pour seule différence avec les previews de ne pas être nécessairement amenées à passer au statut de G.A., et donc d'être un jour couvertes par la politique de dépréciation de Google.</li>
</ul>
Outre les Modules, de nouvelles API ont vu le jour ou ont quitté leur statut expérimental ou de pre
view. Parmi les nouvelles G.A. le Dedicated Memcache permet d’avoir accès à une API de cache qui soit spécifiquement dédiée à l’application (à la différence de l’API de cache partagé, utilisée par l’ensemble des applications d’App Engine). Le temps de réponse de l’application est alors plus facilement prévisible. Cette fonctionnalité est disponible en Europe depuis avril. Du côté des previews, Google Cloud Storage Client Library permet d’intéragir avec Google Cloud Storage, le service App Engine permettant de stocker de gros volumes de données. L’API Map Reduce permet de réaliser des calculs impliquant de très grands volumes de données en utilisant l’algorithme éponyme de Google (en preview pour java, expérimental pour Python). Un exemple simple d’utilisation <a href="https://developers.google.com/appengine/docs/java/dataprocessing/mapreduce_example">ici</a>.
<p style="text-align: center;"><img style="display: block; margin: 0 auto;" title="image4" src="/wp-content/uploads/2015/07/.image4_m.jpg" alt="image4" /> Image 4 : différences entre le cache dédié et partagé</p>
Voici un tableau récapitulatif listant la plupart des fonctionnalités passées récemment à l’état de GA ou de preview :
<p style="text-align: center;"><img style="display: block; margin: 0 auto;" title="image5" src="/wp-content/uploads/2015/07/image5.png" alt="image5" /> Image 5 : Tableau récapitulatif des passages en preview ou GA</p>
Du côté des fonctionnalités expérimentales, on constate qu’il y a moins de changement : il n’y a pas de nouvelle API expérimentale récente et les changements dans les API préexistantes sont rares :
<p style="text-align: center;"><img style="display: block; margin: 0 auto;" title="image6" src="/wp-content/uploads/2015/07/image6.png" alt="image6" /> Image 6 : Fonctionnalités expérimentales d’App Engine</p>

<h3>App Engine Go et PHP :</h3>
Le SDK d’App Engine est disponible pour 4 langages : Java, Python, PHP et Go. Pour ces deux derniers langages, les SDK sont cependant à l’état “preview” et “expérimental” respectivement.

Bien qu’expérimental, le SDK Go possède la plupart des fonctionnalités disponibles avec les autres langages : fonctionnalités de stockage de données (cache, datastore, blobstore), de communication (envoi de mail, requêtage HTTP), de gestion de l’application (api Users pour se connecter avec un compte Google). Seules les API MapReduce, Cloud Endpoints et Prospective Search, ainsi que les services proposés par les partenaires de Google ne sont pas disponibles.

Pour PHP en revanche, de nombreuses fonctionnalités sont encore manquantes : pour n'en citer que quelques unes, les API Images (manipulation d’image) et Search sont par exemple absentes, et il n’existe pas d’API native pour enregistrer ses données dans le datastore. Malgré cela le SDK évolue beaucoup : Les API Sockets et Modules ont été ajoutées ces derniers mois et la version 1.9 assure maintenant le chargement automatique des librairies, il en résulte un temps de chargement de l’application moins long.
<h3>Aller plus loin</h3>
Pour plus d'informations sur la façon dont app engine permet de minimiser l'utilisation des ressources, vous pouvez consulter cet <a href="https://developers.google.com/appengine/docs/managing-resources">article officiel</a>. Des conseils y sont donnés pour diminuer le nombre d'instances créées, en diminuant le temps de latence des instances ou en ajustant manuellement le nombre maximum d'instances inutilisées, ainsi que sur la façon de gérer la bande passante et l'utilisation du datastore, le système de base de données d'App Engine.

Nous avions également évoqué l'apparition des modules pour diminuer les coûts de l'application, ce <a href="https://developers.google.com/appengine/docs/java/modules/">lien</a> explique leur intérêt et leur utilisation.

Enfin, si vous souhaitez en savoir plus sur l'ensemble des fonctionnalités proposées par App Engine, ce <a href="https://developers.google.com/appengine/features/">lien</a> en donne la liste et la description.

</div>