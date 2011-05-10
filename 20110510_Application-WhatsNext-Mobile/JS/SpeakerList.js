/**
 * 
 */

whatsnext.SpeakerList = Ext.extend(Ext.Panel, {
	layout: 'card',
	initComponent: function() {
		this.list = new Ext.List({
			grouped: true,
	        indexBar: true,
	        itemTpl : '<div><img src="{photo}" height="50" width="50" class="photo"/> {firstName} {lastName} <br/> <span class="speakerPosition"> {position} </span></div>',
	        store: whatsnext.SpeakerStore,
	        listeners: {
		    	selectionchange : {fn: this.onSpeakerSelect, scope: this}
		    }
		});
		
		this.listpanel = new Ext.Panel({
			layout: 'fit',
            items: this.list,
            dockedItems: [{
                xtype: 'toolbar',
                title: 'Speakers',
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
		
		whatsnext.SpeakerList.superclass.initComponent.call(this);
	},

	onSpeakerSelect: function(model, records){
	if (records[0] !== undefined) {
		whatsnext.TalkStore.clearFilter();
        var speaker = Ext.ModelMgr.create(records[0].data,'Speaker');
        talks = speaker.talks();
        index = whatsnext.TalkStore.findExact('speaker', records[0].data.id);
        if(index != -1){
        	talks.add(whatsnext.TalkStore.getAt(index));
        }
        var speakerCard = new whatsnext.SpeakerDetails({
            prevCard: this.listpanel,
            record: records[0],
            storeTalk: talks
        });        
        this.setActiveItem(speakerCard, 'slide');
    }
}
});

Ext.reg('speakerlist', whatsnext.SpeakerList);