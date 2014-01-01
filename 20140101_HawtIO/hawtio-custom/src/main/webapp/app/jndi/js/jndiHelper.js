var Jndi;
(function(Jndi) {
	/**
	 * Handle Jolokia success callaback with $q promise
	 * @param {scope} $scope $scope used for $apply
	 * @param {type} deferred $q promise
	 * @returns {function} function callback
	 */
	var deferredOnSuccess=function($scope,deferred) {
		return onSuccess(function(value) {
			$scope.$apply(function(){
				deferred.resolve(value);				
			});
		});
	};	
	/**
	 * JndiMBean client stub constructor
	 * @param {type} $q $q used to create promise
	 * @param {type} jolokia Jolokia
	 * @param {type} $scope $scope used to $apply
	 * @param {type} id MBean id
	 */
	Jndi.JndiMBean=function($q,jolokia,$scope,id) {
		this.$q = $q;
		this.jolokia=jolokia;
		this.$scope=$scope;
		this.mBean="hawtio:type=JndiFacade,name=" + id;
	};
	/**
	 * Get the JNDI context name or path
	 * @return {Promise} $q promise
	 */
	Jndi.JndiMBean.prototype.getContextName=function() {
		var deferred=this.$q.defer();
		this.jolokia.getAttribute(this.mBean, "ContextName", deferredOnSuccess(this.$scope,deferred));
		return deferred.promise;
	};
	/**
	 * List the JNDI context content at specified path
	 * @param {path} path or null for root
	 * @return {Promise} $q promise
	 */
	Jndi.JndiMBean.prototype.list=function(path) {
		var deferred=this.$q.defer();
		this.jolokia.execute(this.mBean, "list", path, deferredOnSuccess(this.$scope, deferred));
		return deferred.promise;
	};
	
	/**
	 * JndiService constructor
	 * @param {type} $q $q used to create promise
	 * @param {type} jolokia Jolokia
	 */
	Jndi.JndiService = function($q,jolokia) {
		this.$q = $q;
		this.jolokia=jolokia;
	};
	/**
	 * Create a new MBean client stub
	 * @param {Scope} $scope
	 * @param {string} id Context id (initial, java)
	 * @return {Jndi.JndiMBean} MBean client stub
	 */
	Jndi.JndiService.prototype.getMBean=function(id, $scope) {
		return new Jndi.JndiMBean(this.$q, this.jolokia, $scope, id);
	};
}(Jndi || (Jndi = {})));