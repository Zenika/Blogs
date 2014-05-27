/**
 * the main TabPanel with the cards : talks, speakers, location, tweets, info
 */

whatsnext.App = Ext.extend(Ext.TabPanel, {
	fullscreen: true,
	tabBar: {
        dock: 'bottom',
        ui: 'darkgray',
        layout: { pack: 'center' }
    },
    cardSwitchAnimation: false,
    initComponent: function() {
    	this.items = [{
	    	title: 'Talks',
	    	iconCls: 'time',
	    	xtype: 'talkslist'	    	
	    }, {
	    	title: 'Speakers',
            iconCls: 'team',
            xtype: 'speakerlist'
	    }, {
	    	title: 'Location',
            iconCls: 'locate',
            xtype: 'location',
            id: 'map'               
	    }, {
	    	title: 'Tweets',
            iconCls: 'twitter2',
            xtype: 'twitter'      
	    }, {
	    	title: 'Infos',
            iconCls: 'info',
            xtype: 'infos'      
	    }];    	
    	whatsnext.App.superclass.initComponent.call(this);
    }    
});

Ext.reg('app', whatsnext.App);