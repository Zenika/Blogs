/**
 * 
 */
whatsnext.LocationView = Ext.extend(Ext.Panel, {
	
	initComponent: function() {
		
		this.position = new google.maps.LatLng(48.8706923, 2.3477454);
		
		this.goButton = new Ext.Button({
			text: 'Go',
    		iconCls: 'compass3',
    		iconMask: true
		});
			
		this.dockedItems = [{
			xtype: 'toolbar',
			ui: 'red',
			title: 'Location',
			items: [this.goButton]
		}];
		
		var infowindow = new google.maps.InfoWindow({
			content: '<h1>Le Grand Rex</h1><small>1, Boulevard Poissonniere <br/> 75002 Paris</small>'
		});				
		
		this.mapView = new Ext.Map({			
			mapOptions : {
				zoom: 14,
				center: new google.maps.LatLng(48.8706923, 2.3477454),
				mapTypeId : google.maps.MapTypeId.ROADMAP				
			}, 
			listeners: {
                maprender : function(comp, map){
                	var marker = new google.maps.Marker({
            			map : map,
            			position : new google.maps.LatLng(48.8706923, 2.3477454),
            			title: "What's Next"
            		});                	
                	infowindow.open(map, marker);                	
                }
			}
		});
		
		this.items = this.mapView;
		
		whatsnext.LocationView.superclass.initComponent.call(this);
	}
});

Ext.reg('location', whatsnext.LocationView);