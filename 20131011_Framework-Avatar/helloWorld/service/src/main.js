var avatar = require("org/glassfish/avatar");
 
var getTime = function() {
	var result = new Date();
	return {
		time: result.toISOString(),
		hour: result.getHours(), 
		minute: result.getMinutes(), 
		second: result.getSeconds(),
		day: result.getDate(),
		Month: result.getMonth()+1,//getMonth() returns 0-11
		year: result.getFullYear()
	};
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
