# HawtIO, un peu plus

### Les composants de base 

<table>
	<tr>
		<td>UploadServlet, UploadManagerMBean</td>
		<td>Permet au frontend de lire/écrire des fichiers sur le backend pour
			par exemple conserver de la configuration</td>
	</tr>
	<tr>
		<td>AuthenticationFilter LoginServlet LogoutServlet</td>
		<td>Restreint l'accès à l'application par une page de login. 
			L'authentification est déléguée à un LoginModule JAAS décrit
			dans le jaas.config.
			Utile surtout pour Jetty.</td>
	</tr>
	<tr>
		<td>CORSFilter ProxyServlet</td>
		<td>Atténue ou contourne les contraintes de sécurité <em>same origin policy</em>
			du navigateur:
			Le frontend peut accéder à plusieurs backends, le backend peut être
			accédé par plusieurs frontends.</td>
	</tr>
	<tr>
		<td>HawtioPluginMBean PluginServlet</td>
		<td>Décrit un plugin (méta-information) et découvre les plugins
			disponibles.</td>
	</tr>
	<tr>
		<td>GitFacadeMXBean GitContextListener</td>
		<td>Historise la configuration avec Git.
			Le frontend peut conserver des données historisées.
			Voir <a href="https://github.com/hawtio/hawtio-config">
				https://github.com/hawtio/hawtio-config</a></td>
	</tr>
</table>
