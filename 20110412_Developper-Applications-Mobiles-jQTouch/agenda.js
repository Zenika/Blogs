/**
 * 
 */

var jQT = new $.jQTouch({
    icon: 'jqtouch.png',
    addGlossToIcon: true,
    startupScreen: 'jqt_startup.png',
    statusBar: 'black',
    useAnimations: true,
    fullScreen: true
});

var userAgent = navigator.userAgent.toLowerCase();
var isiPhone = (userAgent.indexOf('iphone') != -1 || userAgent.indexOf('ipod') != -1) ? true : false;
clickEvent = isiPhone ? 'tap' : 'click';

$(document).ready(function(){ 
	$("#saveButton").tap( function(e){
	  save(); //save event in LocalStorage
	});
	$("#traceButton").tap( function(e){
		  startGeolocation(); 
		});
	getData(); //get data from localStorage 
	initialize(); //initialize a Google Map
});
