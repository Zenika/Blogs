/*****************************************************************************************
 * ******************** Affichage et enregistrement des evenements ***********************
 *****************************************************************************************/
var indice = 0; 

var nameValue;
var startDValue;
var startTValue;
var endDValue;
var endTValue;
var addressValue;
var dailyValue;
var alarmValue;
var emailValue;

function saveLocalStorage(){
	var key = nameValue;
	var value = startDValue + ";" + startTValue + ";" + endDValue  + ";" + endTValue + ";"
		+ addressValue + ";" + dailyValue + ";" + alarmValue + ";" + emailValue;
	if(key){
	if(window.localStorage){
		window.localStorage.setItem(key, value);
		}
	}
}
/*
 * get data from local Storage
 */
function getData(){
	if(window.localStorage){
		for(var i=0; i<window.localStorage.length;i++){
			var keyLocal = window.localStorage.key(i);
			var valueLocal = window.localStorage.getItem(keyLocal);
			nameValue  = keyLocal;
			var data = valueLocal.split(';');
			startDValue = data[0];
			startTValue = data[1];
			endDValue = data[2];
			endTValue = data[3];
			addressValue = data[4];
			displayEvent();
			displayEventDetails();
			indice++;
		}
	}
}

function save(){
	//afficher evenement dans home
	nameValue = $('#name').val();
	startDValue = $('#start').val();
	startTValue = $('#startT').val();
	endDValue = $('#end').val();
	endTValue = $('#endT').val();
	addressValue = $('#location').val();
	dailyValue = $('#daily').val();
	alarmValue = $('#alarm').val();
	emailValue = $('#email').val();
	if(nameValue){
		saveLocalStorage();//enregistre en local		
		displayEvent();//affiche l'evenement sur la page d'accueil		
		displayEventDetails();//affiche les infos dans page evenement				
		indice++;
		//alert("your event has been saved !");
	}else {
		alert("Enter the name of your event");
	}
}
/*
 * display the event's name on the home panel
 */
function displayEvent(){
	var li= document.createElement("li");	
	li.setAttribute("class", "arrow");
	var a = document.createElement("a");
	var lien = "#event"+indice;
	a.setAttribute("href", lien);
	var text= document.createTextNode(nameValue);
	a.appendChild(text);
	li.appendChild(a);
	$('#events').append(li);	
}
/*
 * create panel to display event's details
 */
function displayEventDetails(){
	
	var div = document.createElement("div");
	div.setAttribute("id", "event"+indice);
	
	var toolbar = document.createElement("div");
	toolbar.setAttribute("class", "toolbar");
		var title = document.createElement("h1"); 
		var back = document.createElement("a");
		back.setAttribute("href", "#");
		back.setAttribute("class", "back");
		back.appendChild(document.createTextNode("back"));
	toolbar.appendChild(title);
	toolbar.appendChild(back);
	
	div.appendChild(toolbar);
	
	//contenu de l'evenement
	var ul = document.createElement("ul");
	ul.setAttribute("class", "edgetoedge");
	
	//date et heure de début
	var s = document.createElement("li");
	s.setAttribute("class","sep");
	s.appendChild(document.createTextNode("Start Date"));
	var start = document.createElement("li");
	start.appendChild(document.createTextNode(startDValue+ " "+ startTValue));
	
	//date et heure de fin
	var e = document.createElement("li");
	e.setAttribute("class","sep");
	e.appendChild(document.createTextNode("End Date"));
	var end = document.createElement("li");
	end.appendChild(document.createTextNode(endDValue+ " "+ endTValue));
	
	//emplacement
	var loc = document.createElement("li");
	loc.setAttribute("class","sep");
	loc.appendChild(document.createTextNode("Location"));
	var location = document.createElement("li");
	var ad = "address"+indice;
	location.setAttribute("id", ad);
	var find = document.createElement("a");
	find.setAttribute("href","#map");
	find.setAttribute("id","findMap"+indice);
	var small = document.createElement("small");
	small.appendChild(document.createTextNode("Find"));
	find.appendChild(small);
	
	location.appendChild(document.createTextNode(addressValue));
	location.appendChild(find);

	ul.appendChild(s);
	ul.appendChild(start);
	ul.appendChild(e);
	ul.appendChild(end);
	ul.appendChild(loc);
	ul.appendChild(location);
	
	div.appendChild(ul);
	$('body').append(div);
	var findB = "#findMap"+indice;
	var addresse = addressValue;
	$(findB).bind(clickEvent,function(e){
		findOnMap(addresse);
		console.log(addresse);
	});
}
