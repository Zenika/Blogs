var avatar = require("org/glassfish/avatar");
 
var getTime = function() {
	var result = new Date();
	return {
		time: result.toISOString(),
		h: result.getHours(), 
		m: result.getMinutes(), 
		s: result.getSeconds(),
		d: result.getDate(),
		M: result.getMonth()+1,//getMonth() returns 0-11
		y: result.getFullYear()};
};
 
 
avatar.registerRestService({
	url: "data/name",
	methods: ["GET", "PUT"]}, 
	function() {
		this.$onGet = function(request, response) {
			return response.$send({name:"World"});
		};
		this.$onPut = function(request, response) {
			return response.$send({name:request.data.name});
		};
	}
);
 
avatar.registerPushService({
	url: "push/time"},
	function() {
		this.$onOpen = this.$onTimeout = function(context) {
			context.$setTimeout(1000);
			return context.$sendMessage(getTime());
		};
	}
);
