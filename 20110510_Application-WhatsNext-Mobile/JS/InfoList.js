/**
 * 
 */
whatsnext.InfoList = Ext.extend(Ext.Panel, {
	
	layout: 'card',
	initComponent: function() {
		
		this.list = new Ext.List({
            itemTpl: '{name}',
            ui: 'round',
            store: whatsnext.InfoStore,            	
            /*new Ext.data.Store({
                fields: ['name'],
                data: [{name: 'About'},{name: 'Sponsors'}, {name: 'Partners'}, {name: 'Contact'}]
            }),*/
            listeners: {
                selectionchange: {fn: this.onInfoSelect, scope: this}
            }
        });
		
		this.listpanel = new Ext.Panel({
			layout: 'fit',
            items: this.list,
            dockedItems: [{
                xtype: 'toolbar',
                title: 'Infos',
                ui: 'red'
            }],
            listeners: {
                activate: { fn: function(){
                    this.list.getSelectionModel().deselectAll();
                    Ext.repaint();
                }, scope: this }
            }
        });
        
        this.items = this.listpanel;
		
		whatsnext.InfoList.superclass.initComponent.call(this);
	},
	
	onInfoSelect: function(model, records){
		if (records[0] !== undefined) {
	        var infoCard = new whatsnext.InfoCard({
	            prevCard: this.listpanel,
	            record: records[0]
	        });
	        
	        this.setActiveItem(infoCard, 'slide');
	    }
	}
	
});

Ext.reg('infos', whatsnext.InfoList);