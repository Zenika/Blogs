/**
 * 
 */

whatsnext.InfoCard = Ext.extend(Ext.Panel, {
	scroll: 'vertical',
	cls: 'infoCard',
	initComponent: function() {
		this.dockedItems = [{
			xtype: 'toolbar',
			ui: 'red',
            title: this.record.data.name,
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
			tpl: new Ext.XTemplate('{content}'),
			data: this.record.data
			
		}];		
		whatsnext.InfoCard.superclass.initComponent.call(this);		
	}
});

Ext.reg('infocard', whatsnext.InfoCard);