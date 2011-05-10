/**
 * 
 */

whatsnext.TalksList = Ext.extend(Ext.Panel, {
	layout: 'card',
	initComponent: function() {		
		this.listTalks = new Ext.List({
			grouped: true,
			indexBar: false,
			scroll: true,
	        itemTpl : '<div> {title} </div>',
	        store: whatsnext.TalkStore,
	        listeners: {
		    	selectionchange : {fn: this.onTalkSelect, scope: this}		    				    		
		    }
		});
		
		this.listTalks.on('beforerender', this.onListLoad, this);
		
		this.dayOption = new Ext.SegmentedButton({
		    allowMultiple: true,
		    items: [
		        {
		        	text: '26 May',
		        	pressed: true,
            		listeners:{
            			tap: {fn: this.onButtonOne, scope: this}
            		} 
		        },
		        {
		        	xtype: 'button',
            		text: '27 May',
            		listeners:{
            			tap: {fn: this.onButtonTwo, scope: this}
            		} 
		        }
		    ]
		});
		
		this.panel = new Ext.Panel({
			fullscreen : true,
			layout: 'fit',
            dockedItems: [{
                xtype: 'toolbar',
                title: 'What\'s Next',
                ui: 'red'
            }, {
            	xtype: 'toolbar',
            	layout: { pack: 'center' },
            	items: [this.dayOption]
            }],
            items: this.listTalks ,
            listeners: {
                activate: { fn: function(){
                    this.listTalks.getSelectionModel().deselectAll();
                    Ext.repaint();
                }, scope: this }
            }
        });
		
        this.items = this.panel;
		
        this.on('activate', this.onListLoad, this);
        
        whatsnext.TalksList.superclass.initComponent.call(this);        
	},
	
	onListLoad: function() {
		 this.listTalks.store.clearFilter();
		 this.listTalks.store.filter('day', '1');
		 this.dayOption.setPressed(0, true, false);
		 this.dayOption.setPressed(1, false, false);
	},
	onButtonOne : function () {		
		this.listTalks.store.clearFilter();
        this.listTalks.store.filter('day', '1');
        this.dayOption.setPressed(1, false, false);
        this.dayOption.setPressed(0, true, false);
        this.listTalks.scroller.scrollTo({y: 0}, false);	        
	},	
	onButtonTwo : function () {
		this.listTalks.store.clearFilter();
        this.listTalks.store.filter('day', '2');
        this.dayOption.setPressed(0, false, false);
        this.dayOption.setPressed(1, true, false);
        this.listTalks.scroller.scrollTo({x: 0, y: 0});
	},	
	onTalkSelect: function(model, records){
		if (records[0] !== undefined) {
			var talk = Ext.ModelMgr.create(records[0].data,'Talks');
	        speakers = talk.speakers();
	        speakers.add(whatsnext.SpeakerStore.getById(records[0].data.speaker));
	        var talkCard = new whatsnext.TalksDetails({
	            prevCard: this.panel,
	            record: records[0],
	            storeSpeaker: speakers
	        });	        
	        this.setActiveItem(talkCard, 'slide');
	    }
	}	
});

Ext.reg('talkslist', whatsnext.TalksList);