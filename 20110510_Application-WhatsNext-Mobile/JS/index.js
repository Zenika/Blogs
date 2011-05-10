/**
 * 
 */
Ext.ns('whatsnext');

Ext.setup({
    statusBarStyle: 'black',
    phoneStartupScreen: 'accueil_phone5.png',
    onReady: function() {
    	
    	var appPanel = new whatsnext.App({});
    	
    	//geolocation
    	appPanel.getComponent('map').mapView.on('maprender', function(){
    		appPanel.getComponent('map').goButton.on('tap', function(){
        		startGeolocation(appPanel.getComponent('map'));
        	});
    	});
    	
    }
});