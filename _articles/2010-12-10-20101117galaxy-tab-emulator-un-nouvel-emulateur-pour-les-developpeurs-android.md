---
ID: 424
post_title: 'Galaxy Tab Emulator : Un nouvel émulateur pour les développeurs Android'
author: hpierart
post_date: 2010-12-10 11:13:00
post_excerpt: |
  <p>Ce nouvel émulateur développé par Samsung, sorti début novembre, offre une option supplémentaire pour tester vos applications Android, et sera probablement un casse tête de plus si vous n'êtes pas encore familier avec la gestion d'applications<a href="http://developer.android.com/guide/practices/screens_support.html"> supportant des résolutions très différentes</a>. En effet, à l'heure actuelle, les résolutions d'écran des terminaux Android s'étendent de 320x240 à 1024x600... Avec de tels écarts, on peut effectivement être heureux d'avoir enfin un émulateur jouant dans la partie haute de cette fourchette.<br />
  Cet émulateur n'est pas disponible directement sur le site des packages Android de Google mais par le biais des Add-on.<br />
  J'ai eu l'occasion de le tester à sa sortie avec l'<a href="http://www.zenika.com/zenika-on-android?fg=50007">application Zenika</a>, lisez la suite pour plus d'informations sur l'installation et les premières critiques et impressions que nous en avons retirés.</p>
layout: post
permalink: http://blog.zenika-offres.com/?p=424
published: true
---
<p>Ce nouvel émulateur développé par Samsung, sorti début novembre, offre une option supplémentaire pour tester vos applications Android, et sera probablement un casse tête de plus si vous n'êtes pas encore familier avec la gestion d'applications<a href="http://developer.android.com/guide/practices/screens_support.html"> supportant des résolutions très différentes</a>. En effet, à l'heure actuelle, les résolutions d'écran des terminaux Android s'étendent de 320x240 à 1024x600... Avec de tels écarts, on peut effectivement être heureux d'avoir enfin un émulateur jouant dans la partie haute de cette fourchette.<br />
Cet émulateur n'est pas disponible directement sur le site des packages Android de Google mais par le biais des Add-on.<br />
J'ai eu l'occasion de le tester à sa sortie avec l'<a href="http://www.zenika.com/zenika-on-android?fg=50007">application Zenika</a>, lisez la suite pour plus d'informations sur l'installation et les premières critiques et impressions que nous en avons retirés.</p>
<!--more-->
<h2>Installation de l'émulateur</h2> <p><br /><br />Ce poste étant principalement destiné aux initiés Android, voici les étapes importantes pour ajouter cet émulateur :<br />
- Accédez à l'écran "Android SDK and AVD Manager" sous Eclipse.<br />
- Cliquez le bouton "Add-on" dans "Available Packages", Vous devez ajouter cet url pour télécharger le package&nbsp;: <br />
<a href="http://innovator.samsungmobile.com/android/repository/srepository.xml">http://innovator.samsungmobile.com/android/repository/srepository.xml</a><br /></p> <p>Vous obtenez cet écran :<br />
<br />
<img src="/wp-content/uploads/2015/07/.installation_galaxy_tab_emulateur_m.jpg" alt="installation package" title="installation package" /><br />
<br />
<br />
- Après sélection du package, Eclipse télécharge le package (~58Mo) et vous demande de redémarrer l'ADB, acceptez.<br /></p> <p>- Ceci étant fait, vous devriez pouvoir créer de nouvelles instances d'émulateurs utilisant le package "Galaxy Tab". Attention, la résolution étant 1024x600, si votre écran a une hauteur inférieur ou égale à 600 px, vous aurez une partie de l'émulateur hors de l'écran, donc pensez à ajouter le paramètre "scale" lors du lancement de l'émulateur. Vous aller maintenant pouvoir lancer vos applications sur cet émulateur. <br /></p> <p>Après démarrage, vous aurez ceci :<br />
<br />
<img src="/wp-content/uploads/2015/07/.galaxy_tab_emulator_m.jpg" alt="galaxytabemulator" title="galaxytabemulator" /><br /><br /></p> <h2>Pourquoi ajouter encore un émulateur Android? Quels avantages cela apporte-t-il?</h2> <p><br /><br />Actuellement, environ une 10aine de tablettes Android sont disponibles sur le marché ou le seront prochainement. Dans de telles conditions, l'éventail des terminaux Android sur lesquels une application peut être déployée ne fait que s'étendre un peu plus, avec des terminaux ayant des définitions bien différentes de celles trouvées communément sur les smartphones. Dans le cas de la Galaxy Tab par exemple, la résolution est de 1024*600 (contre 854*480 pour les meilleurs téléphones). Ceci a bien entendu un impact direct sur les applications et leur affichage.<br />
Ce nouvel émulateur apporte donc un moyen supplémentaire de tester son application dans un contexte différent de résolution, mais également avec un hardware éventuellement moins riche que dans les smartphones.</p> <h2>Quelques pistes d'amélioration&nbsp;:</h2> <p><br /><br />- Premièrement, je n'ai pas pu lancer l'émulateur depuis Eclipse une fois que celui-ci était installé. Cet outil étant tout nouveau, aucune information ou rapport d'erreur ne semble être disponible pour aider à résoudre ceci. L'émulateur est créé mais lorsque je fais "start", le chargement commence puis s'interrompt avec le message "emulator.exe a cessé de fonctionner" et pas plus d'Eclipse. Du coup, je le lance en mode debug par ligne de commande pour trouver le problème&nbsp;: <br />
<em>emulator -avd emulator_name -scale 0.6 -debug-all</em><br />
Et là étonnamment, ca marche... (démarrer sans le debug all fonctionne aussi, donc le problème est a priori lié à Eclipse...)<br />
<br />
- L'émulateur n'est pas particulièrement long à démarrer en revanche, l'exécution d'application est vraiment lent. Tout semble s'exécuter au ralenti...Approximativement 1s de délai entre chaque action... (Cela dépendant probablement du matériel mais il semble que je ne sois pas le seul à remonter ce problème...)<br />
<br />
- Un dernier point non des moindres&nbsp;: A ce jour, l'émulateur Galaxy Tab ne supporte pas explicitement l'API Google Maps, donc il est impossible de lancer dessus une application incluant ce package depuis Eclipse. Mais, le passage par ligne de commande résout de nouveau le problème de l'installation&nbsp;: <br />
"avd -e install "chemin complet jusqu'à votre .apk"</p> <h2>En conclusion&nbsp;:</h2> <p><br /><br />Ce nouvel outil apporte une pierre intéressante au monde du développement Android, bien que l'outil soit encore jeune et nécessite certainement encore quelques ajustements.<br />
Merci à Samsung pour ce nouvel émulateur. Pour plus d'informations sur l'installation, vous pouvez consulter <a href="http://innovator.samsungmobile.com/galaxyTab.do">la page officielle.</a></p>