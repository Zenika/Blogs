---
ID: 152
post_title: 'ZenTimestamp : Gérer le timestamp des builds Hudson'
author: Grégory Boissinot
post_date: 2008-08-16 16:19:00
post_excerpt: '<p><a href="https://hudson.dev.java.net/">Hudson</a> est un moteur d’intégration de plus en plus utilisé en entreprise. Bénéficiant d’une très grande flexibilité en proposant un modèle basé sur des <a href="http://hudson.gotdns.com/wiki/display/HUDSON/Extension+points">points d’extensions</a>, il est très simple à étendre.</p>'
layout: post
permalink: http://blog.zenika-offres.com/?p=152
published: true
---
<p><a href="https://hudson.dev.java.net/">Hudson</a> est un moteur d’intégration de plus en plus utilisé en entreprise. Bénéficiant d’une très grande flexibilité en proposant un modèle basé sur des <a href="http://hudson.gotdns.com/wiki/display/HUDSON/Extension+points">points d’extensions</a>, il est très simple à étendre.</p>
<!--more-->
<p>Nous avons crée le plugin Hudson <a href="http://hudson.gotdns.com/wiki/display/HUDSON/ZenTimestamp+Plugin">ZenTimestamp</a> permettant de manipuler le format de la variable Hudson <code>BUILD_ID</code>. <br />
Cette variable représente le timestamp du build d’un job lancé par Hudson.</p> <p>Le pattern utilisé par Hudson est "<code>YYYY-MM-DD_hh-mm-ss</code>". Il est souvent utile d’avoir besoin de changer ce format comme par exemple dans le cas du <a href="http://help.eclipse.org/stable/index.jsp?topic=/org.eclipse.pde.doc.user/tasks/pde_feature_generating_antcommandline.htm">build d’un plugin Eclipse RCP avec PDE</a>. On souhaite que le paramètre <code>forceContextQualifier</code> requière le format suivant "<code><a href="http://help.eclipse.org/stable/index.jsp?topic=/org.eclipse.pde.doc.user/tasks/pde_version_qualifiers.htm">YYYYMMDDHHMM</a></code>". Au lieu d’ajouter un script de formatage dans la chaine de build qui serait utilisé uniquement par Hudson, vous pouvez désormais utiliser le plugin <a href="https://hudson.dev.java.net/servlets/ProjectDocumentList?folderID=9714&amp;expandFolder=9714&amp;folderID=0">ZenTimestamp</a>.</p>