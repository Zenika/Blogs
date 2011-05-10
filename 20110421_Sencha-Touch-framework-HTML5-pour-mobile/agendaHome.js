/**
 * 
 */
var newEventButton = new Ext.Button(
    	        {
    	        	ui: 'normal' ,
    	            text: 'New',
    	            iconMask: true,
    	            iconCls: 'compose'
    	        }
    	    );

function displayList(storeName){
	list = new Ext.List({
		fullscreen:true,
		itemTpl : '{name}',
		indexBar: true,
	    
		store: storeName ,
		listeners: {
	    	itemtap : function(list, index, item, e){
	    		evt = storeName.getAt(index);
	    		displayEventPanel(evt);
	    	}
	    }	    
	});
	homePanel.add(list);
}

homePanel = new Ext.Panel({
	    		fullscreen: true,
	    		animation: 'slide',
	            dockedItems: [{
	    	 		xtype: 'toolbar',
	    	 		items: [{xtype: 'spacer'}, newEventButton],
	    	 		title: 'Agenda',
	    	 		dock: 'top'
	    	 	}]
	    	});

newEventButton.on('tap', function(){pastItem=currentItem; currentItem=1; p.setActiveItem(currentItem);});