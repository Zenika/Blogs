/**
 * 
 */

whatsnext.SpeakerDetails = Ext.extend(Ext.Panel, {
	scroll: 'vertical',
	initComponent: function() {
		this.dockedItems = [{
			xtype: 'toolbar',
			ui: 'red',
            title: this.record.data.lastName,
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
			tpl: new Ext.XTemplate('<div class="speakerHeader"><img src="{photo}" height="60" width="60" class="photo"/> <span class="speakerName">{firstName} {lastName}</span> <br/> <span class="speakerPosition"> {position} </span></div> <div class="biography"> {description} </div>'),
			data: this.record.data
		}];
		
		if(this.storeTalk != undefined){
			this.TalkList = new Ext.List({
				grouped: false,
				indexBar: false,
				itemTpl: '<div> {title} </div>',
				store: this.storeTalk,
				listeners: {
			    	selectionchange : {fn: this.onTalkSelect, scope: this}		    				    		
			    }
			});
			this.items.push({
				xtype: 'toolbar',
	            title: 'Talk',
	            cls: 'small_toolbar'
			});
			this.items.push(this.TalkList);			
		}
		
		this.listeners = {
            activate: { fn: function(){
                if (this.TalkList) {
                    this.TalkList.getSelectionModel().deselectAll();
                }
            }, scope: this }
        };
		
		whatsnext.SpeakerDetails.superclass.initComponent.call(this);
	},
	
	onTalkSelect : function (model, records){
		if (records[0] !== undefined) {
	        
	        var talkCard = new whatsnext.TalksDetails({
	            prevCard: this,
	            record: records[0]
	        });
	        
		this.ownerCt.setActiveItem(talkCard, 'slide');
		}
	}
});

Ext.reg('speakerdetails', whatsnext.SpeakerDetails);