/**
 * 
 */

whatsnext.TalksDetails = Ext.extend(Ext.Panel, {
	scroll: 'vertical',
	initComponent: function() {
		this.dockedItems = [{
			xtype: 'toolbar',
			ui: 'red',
            items: [{
                ui: 'back',
                text: 'Back',
                scope: this,
                handler: function(){
                    this.ownerCt.setActiveItem(this.prevCard, {
                        type: 'slide',
                        reverse: true,
                        scope: this,
                        after: function(){
                            this.destroy();
                        }
                    });
                }
            }]
		}];
		
		this.items = [{
			styleHtmlContent: true,
			tpl: new Ext.XTemplate('<h3>{title}</h3> {description}'),
			data: this.record.data
		}];
		
		if (this.storeSpeaker != undefined){
			this.speakerList = new Ext.List({
				grouped: false,
				indexBar: false,
				itemTpl: '<div class="speakerHeader"><img src="{photo}" height="60" width="60" class="photo"/> <span class="speakerName">{firstName} {lastName}</span> <br/> <span class="speakerPosition"> {position} </span></div>',
				store: this.storeSpeaker,
				listeners: {
			    	selectionchange : {fn: this.onSpeakerSelect, scope: this}		    				    		
			    }
			});
			this.items.push({
				xtype: 'toolbar',
	            title: 'Speaker',
	            cls: 'small_toolbar'
			});
			this.items.push(this.speakerList);
		}
		
		this.listeners = {
            activate: { fn: function(){
                if (this.speakerList) {
                    this.speakerList.getSelectionModel().deselectAll();
                }
            }, scope: this }
        };
		
		whatsnext.TalksDetails.superclass.initComponent.call(this);
	},

	onSpeakerSelect : function (model, records){
		if (records[0] !== undefined) {
	        
	        var speakerCard = new whatsnext.SpeakerDetails({
	            prevCard: this,
	            record: records[0]
	        });	        
		this.ownerCt.setActiveItem(speakerCard, 'slide');
		}
	}
});

Ext.reg('talksdetails', whatsnext.TalksDetails);