var Jndi;
(function(Jndi) {
	angular.module('Jndi', ['hawtioCore'])
			.config(function($routeProvider) {
				$routeProvider
						.when('/jndi', {templateUrl: 'app/jndi/html/jndi.html'})
						.when('/jndi/:name', {templateUrl: 'app/jndi/html/jndi.html'});
			})
			.run(function(workspace, viewRegistry, helpRegistry) {
				viewRegistry["jndi"] = "app/jndi/html/layoutJndiTabs.html";
				helpRegistry.addUserDoc("jndi", 'app/jndi/doc/help.md', function() {
					return workspace.treeContainsDomainAndProperties('hawtio', {type: 'JndiFacade'});
				});
				workspace.topLevelTabs.push({
					id: "jndi",
					content: "JNDI",
					title: "Browse JNDI registry",
					isValid: function(workspace) {
						return workspace.treeContainsDomainAndProperties('hawtio', {type: 'JndiFacade'});
					},
					href: function() {
						return "#/jndi";
					}
				});
				workspace.subLevelTabs.push({
					content: '<i class="icon-list-alt"></i> Initial',
					title: "Initial Context",
					isValid: function(workspace) {
						return workspace.hasDomainAndProperties('hawtio', {type: 'JndiFacade', name: "initial"});
					},
					href: function() {
						return "#/jndi/initial";
					}
				});
				workspace.subLevelTabs.push({
					content: '<i class="icon-list-alt"></i> Java:',
					title: "Java: Context",
					isValid: function(workspace) {
						return workspace.hasDomainAndProperties('hawtio', {type: 'JndiFacade', name: "java"});
					},
					href: function() {
						return "#/jndi/java";
					}
				});
			})
			.service("jndiService", Jndi.JndiService)
			.controller("jndiController", Jndi.JndiController);
	hawtioPluginLoader.addModule('Jndi');
}(Jndi || (Jndi = {})));