/**
 * 
 */

Ext.regModel('Tweet', {
    fields: ['text', 'to_user_id', 'from_user', 'created_at', 'profile_image_url', 'id_str']
});

whatsnext.TweetView = Ext.extend(Ext.Panel, {
	
	initComponent: function(){
		
		this.refreshButton = new Ext.Button({
			ui : 'mask',
			iconCls : 'refresh',
			iconMask : true,
			scope : this,
			handler : this.loadTwitter
		});

		this.dockedItems = [{
			xtype: 'toolbar',
			ui: 'red',
			title: 'WsN_Paris',
			items: this.refreshButton
		}];
		
		this.list = new Ext.List({
            itemTpl: new Ext.XTemplate('<div class="avatar"<tpl if="profile_image_url"> style="background-image: url({profile_image_url})"</tpl>></div> <div>{from_user}<br /><p class="TweetText">{text}<br/><span style="font-size: .7em">{created_at}</span></p></div>'),
            loadingText: false,
            store: new Ext.data.Store({
                model: 'Tweet',
                proxy: {
                    type: 'scripttag',
                    url : 'http://search.twitter.com/search.json',
                    reader: {
                        type: 'json',
                        root: 'results'
                    }
                }
            }),
            listeners: {
                selectionchange: { fn: this.selectTweet, scope: this }
            }
        });
		
		this.layout = 'fit';
		
		this.items = this.list;
        
        this.list.on('afterrender', this.loadTwitter, this);
        
        whatsnext.TweetView.superclass.initComponent.call(this);
	},
	
	loadTwitter: function(){
		this.list.store.read({
            params: { q: '#WsN_Paris OR WsN_Paris OR to:WsN_Paris OR from:WsN_Paris', rpp: 50, result_type: 'mixed'}
        });		
	},
	
	selectTweet: function(model, records){
        if (records[0]) {
            Ext.Msg.confirm('External Link', 'Open tweet in Twitter?', function(res){
                if (res == 'yes') {
                    window.location = 'http://twitter.com/' + records[0].data.from_user + '/status/' + records[0].data.id_str;
                }                
                model.deselectAll();
            }, this);
        }
    }		
});

Ext.reg('twitter', whatsnext.TweetView);