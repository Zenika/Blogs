/**
 * 
 */

var eventPosition;

function codeAddress(address) {
	var geocoder = new google.maps.Geocoder();
	geocoder.geocode({
		'address' : address
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			eventPosition = results[0].geometry.location;
			map.map.setCenter(results[0].geometry.location);
			var marker = new google.maps.Marker({
				map : map.map,
				position : results[0].geometry.location
			});
		} else {
			alert("Geocode was not successful for the following reason: "
					+ status);
		}
	});
}

function findDirection(myPosition) {
	var directionsService = new google.maps.DirectionsService();
	var start = myPosition;
	var end = eventPosition;
	var request = {
		origin : start,
		destination : end,
		travelMode : google.maps.DirectionsTravelMode.DRIVING
	};
	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}
	});
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map.map);
}

var map = new Ext.Map({
	mapOptions : {
		zoom : 15
	}
});

var backButton = new Ext.Button({
	ui : 'back',
	text : 'Back'

});

var homeButton = new Ext.Button({
	ui : 'action',
	text : 'Home',
	iconMask : true,
	iconCls : 'home'

});

var goToEventButton = new Ext.Button({
	ui : 'action',
	text : 'Go to my event',
	iconMask : true,
	iconCls : 'locate'
});

homeButton.on('tap', function() {
	pastItem = currentItem;
	currentItem = 0;
	p.setActiveItem(0);
});
backButton.on('tap', function() {
	if (currentItem > 0) {
		var item = pastItem;
		currentItem = pastItem;
		pastItem = item;
	}
	p.setActiveItem(currentItem);
});

var mapPanel = new Ext.Panel({
	fullscreen : true,
	animation : 'slide',
	dockedItems : [ {
		xtype : 'toolbar',
		items : [ backButton, {
			xtype : 'spacer'
		}, homeButton ],
		title : 'Map',
		dock : 'top'
	}, {
		xtype : 'toolbar',
		items : [ {
			xtype : 'spacer'
		}, goToEventButton, {xtype : 'spacer'} ],
		dock : 'bottom'
	} ],
	items : [ map ]
});