var Jndi;
(function(Jndi) {
	/**
	 * JDNI Controller
	 * @param {type} $scope
	 * @param {type} $routeParams
	 * @param {Jndi.JndiService} jndiService
	 * @returns {undefined}
	 */
	Jndi.JndiController = function($scope, $routeParams, jndiService) {
		var context = {id: $routeParams.name};
		if (!context.id) {
			return;
		}
		var mBean=jndiService.getMBean(context.id, $scope);
		$scope.context = context;
		mBean.getContextName().then(function(value){
			$scope.context.name = value;
		});
		var getTypeDescription=function(node) {
			var description = node.className;
			if (node.typeNames && node.typeNames.length>0 && (node.typeNames.length>1 || node.typeNames[0]!==node.className)) {
				description += " (";
				angular.forEach(node.typeNames, function(typeName, index) {
					if (index>0) {
						description += ", ";						
					}
					description += typeName;
				});				
				description += ")";
			}
			return description;
		}
		var initNodes=function(nodes) {
			angular.forEach(nodes, function(node) {
				var context=node.typeNames && node.typeNames.indexOf("javax.naming.Context")>=0;
				node.leaf = !context;
				node.typeDescription=getTypeDescription(node);
				if (context) {
					node.expanded=false;
				}
			});
			return nodes;
		};
		mBean.list(null).then(function(value){
			$scope.context.nodes = initNodes(value);
		});
		$scope.toggleExpanded=function(node) {
			if (node.expanded) {
				node.nodes=[];
				node.expanded=false;
			} else {
				mBean.list(node.fullName).then(function(value){
					node.nodes=initNodes(value);
					node.expanded=true;
				});
			}			
		};
	};
}(Jndi || (Jndi = {})));