/**
 * 
 */

Ext.setup({
	phoneStartupScreen : 'phone_startup.png',
	tabletStartupScreen : 'tablet_startup.pgn',
	icon : 'icon_agenda.jpg',
	glossOnIcon : true,
	onReady : function() {
		/**
		 * Geolocation
		 */
		var geo = new Ext.util.GeoLocation({
			autoUpdate : false,
			listeners : {
				locationupdate : function(geo) {
					myPosition = new google.maps.LatLng(geo.latitude,
							geo.longitude);
					map.update(myPosition);
					var marker = new google.maps.Marker({
						position : myPosition,
						map : map.map,
						title : "Me!"
					});
					findDirection(myPosition);
				},
				locationerror : function(geo, bTimeout, bPermissionDenied,
						bLocationUnavailable, message) {
					if (bTimeout) {
						alert('Timeout occurred.');
					} else if (bPermissionDenied) {
						alert('permission denied');
					} else if (bLocationUnavailable) {
						alert('Location Unavailable');
					} else {
						alert('Error occurred.');
					}
				}
			}
		});
		goToEventButton.on('tap', function() {
			geo.updateLocation();
		});
		/**
		 * Evenements 
		 */
		Ext.regModel('Event', {
			fields : [ {
				name : 'name',
				type : 'string'
			}, {
				name : 'startDate',
				type : 'date'
			}, {
				name : 'endDate',
				type : 'date'
			}, {
				name : 'location',
				type : 'string'
			}, {
				name : 'repetition',
				type : 'string'
			}, {
				name : 'alert',
				type : 'boolean'
			}, {
				name : 'email',
				type : 'string'
			} ],
			proxy : {
				type : 'localstorage',
				id : 'EventStore'
			}
		});

		var store = new Ext.data.Store({
			model : 'Event'
		});

		saveButton.on('tap', function() {
			var event = Ext.ModelMgr.create({}, 'Event');
			store.add(event);
			myform.updateRecord(event, true);			
			event.save();
			store.sync();
			pastItem = currentItem;
			currentItem = 0;
			p.setActiveItem(currentItem);
		});

		store.load(function(records, operation, success) {
			for ( var i = 0; i < records.length; i++) {
				console.log(records[i].data);    			
			}
			displayList(store);
		});

		currentItem = 0;
		pastItem = 0;

		p = new Ext.Container({
			fullscreen : true,
			layout : 'card',
			cardSwitchAnimation : {
				type : 'fade',
				duration : 600
			},
			items : [ homePanel, newEventPanel, evtPanel, mapPanel ]
		});
		p.setActiveItem(currentItem);
	}
});