/**
 * 
 */
var userAgent = navigator.userAgent.toLowerCase();
var isiPhone = (userAgent.indexOf('iphone') != -1 || userAgent.indexOf('ipod') != -1) ? true : false;
clickEvent = isiPhone ? 'tap' : 'click';

$(document).ready(function(){
	$("#saveButton").bind('tap', function(e){
	  save();
	});
	$("#traceButton").bind('tap', function(e){
		  startGeolocation();
		});
	initList();
	getData();
	displayList();
	initialize();
});
