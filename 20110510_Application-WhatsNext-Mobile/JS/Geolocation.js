/**
 * 
 */

function findDirection(myPosition, eventPosition, map) {
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
			console.log("OK");
		}
	});
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map);
}

function startGeolocation(mapComp){
	var geo = new Ext.util.GeoLocation({
		autoUpdate : false,
		listeners : {
			locationupdate : function(geo) {
				myPosition = new google.maps.LatLng(geo.latitude,
						geo.longitude);
				mapComp.mapView.update(myPosition);
				var marker = new google.maps.Marker({
					position : myPosition,
					map : mapComp.mapView.map,
					title : "Me!"
				});
				findDirection(myPosition, mapComp.position, mapComp.mapView.map);
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
	geo.updateLocation(); 	
}
