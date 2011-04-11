/*****************************************************************************************
 *******************************		 Geolocation 		******************************
 *****************************************************************************************/

var eventPosition;
var maPosition;
var map;
/*
 * initialize a map
 */
function initialize() {
	var myLatlng = new google.maps.LatLng(0, 0);
	var myOptions = {
		zoom : 1,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("map_canvas"),
			myOptions);
}
/*
 * call Geolocation API to find the user's location
 */
function startGeolocation() {		
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(findPosition, handleError);
	} else {
		updateStatus("HTML5 Geolocation is not supported in your browser.");
	}		
}
/*
 * Geolocation : success function
 */
function findPosition(position) {
	var latitude = position.coords.latitude;
	var longitude = position.coords.longitude;
	var accuracy = position.coords.accuracy;
	maPosition = new google.maps.LatLng(latitude, longitude);
	map.setZoom(15);
	map.setCenter(maPosition);
	var marker = new google.maps.Marker({
		position : maPosition,
		map : map,
		title : "Me!"
	});
	findDirection();
}
/*
 * Geolocation : error function
 */
function handleError(error) {
	switch (error.code) {
	case 0:
		alert("There was an error while retrieving your location: "
				+ error.message);
		break;
	case 1:
		alert("The user prevented this page from retrieving a location.");
		break;
	case 2:
		alert("The browser was unable to determine your location: "
				+ error.message);
		break;
	case 3:
		alert("The browser timed out before retrieving the location.");
		break;
	}
}

/*
 * find latitude and longitude from address
 */
function codeAddress(address) {
	initialize();
	var geocoder = new google.maps.Geocoder();
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
    	eventPosition = results[0].geometry.location;
        map.setCenter(results[0].geometry.location);
        map.setZoom(15);
        var marker = new google.maps.Marker({
            map: map, 
            position: results[0].geometry.location
        });
      } else {
        alert("Geocode was not successful for the following reason: " + status);
      }
    });
}

/*
 * find direction from user's location to the event
 */
function findDirection() {
	var directionsService = new google.maps.DirectionsService();
	var start = maPosition;
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
	directionsDisplay.setMap(map);
}

function findOnMap(address){
	codeAddress(address);
}