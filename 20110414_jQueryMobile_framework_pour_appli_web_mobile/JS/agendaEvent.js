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

var ul;

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
	nameValue = $('#name').val();
	startDValue = $('#start').val();
	startTValue = $('#startT').val();
	endDValue = $('#end').val();
	endTValue = $('#endT').val();
	addressValue = $('#location').val();
	dailyValue = $('#daily').val();
	alarmValue = $('#alarm').val();
	emailValue = $('#email').val();
	if(!nameValue){
		alert("Enter the event's name");
	}else{		
		saveLocalStorage();//enregistre en local
		displayEvent();//ajoute evenement sur la page d'accueil		
		displayEventDetails();//afficher detail dans page evenement		
		indice++;
		alert("your event has been saved !");
		
	}	
}

function initList(){
	ul = document.createElement("ul");
	ul.setAttribute("data-role","listview");
	ul.setAttribute("data-theme", "g");
	ul.setAttribute("id", "eventslist");
}

function displayList(){
	$('#events').append(ul);
	$('#events').page();	
}

function displayEvent(){	
	var li= document.createElement("li");
	li.setAttribute("role", "option");
	var a = document.createElement("a");
	var lien = "#event"+indice;
	a.setAttribute("href", lien);
	var text= document.createTextNode(nameValue);
	a.appendChild(text);
	li.appendChild(a);
	ul.appendChild(li);	
	$('#eventslist').listview('refresh');
}

function displayEventDetails(){
	var div = document.createElement("div");
	div.setAttribute("id", "event"+indice);
	div.setAttribute("data-role", "page");
	div.setAttribute("data-url", "event"+indice);
	div.setAttribute("data-theme","b");
	
	var toolbar = document.createElement("div");
	toolbar.setAttribute("data-role", "header");
		var title = document.createElement("h1"); 
		title.appendChild(document.createTextNode(nameValue));
		var home = document.createElement("a");
		home.setAttribute("href", "#home");
		home.setAttribute("data-role", "button");
		home.setAttribute("data-icon", "home");
		home.setAttribute("class", "ui-btn-right");
		home.appendChild(document.createTextNode("home"));
	toolbar.appendChild(title);
	toolbar.appendChild(home);
	
	div.appendChild(toolbar);
	
	//contenu de l'evenement
	var content = document.createElement("div");
	content.setAttribute("data-role", "content");
	
	var ul = document.createElement("ul");
	ul.setAttribute("data-role", "listview");
	
	//date et heure de début
	var s = document.createElement("li");
	s.setAttribute("data-role","list-divider");
	s.appendChild(document.createTextNode("Start Date"));
	var start = document.createElement("li");
	start.appendChild(document.createTextNode(startDValue+ " "+ startTValue));
	
	//date et heure de fin
	var e = document.createElement("li");
	e.setAttribute("data-role","list-divider");
	e.appendChild(document.createTextNode("End Date"));
	var end = document.createElement("li");
	end.appendChild(document.createTextNode(endDValue+ " "+ endTValue));
	
	//emplacement
	var loc = document.createElement("li");
	loc.setAttribute("data-role","list-divider");
	loc.appendChild(document.createTextNode("Location"));
	var location = document.createElement("li");
	var ad = "address"+indice;
	location.setAttribute("id", ad);
	location.appendChild(document.createTextNode(addressValue));
	
	//Find button
	var find = document.createElement("a");
	find.setAttribute("id","findMap"+indice);
	find.setAttribute("data-role","button");
	//find.setAttribute("data-inline","true");
	find.setAttribute("href", "#map");
	find.appendChild(document.createTextNode("Find On Map"));
	
	ul.appendChild(s);
	ul.appendChild(start);
	ul.appendChild(e);
	ul.appendChild(end);
	ul.appendChild(loc);
	ul.appendChild(location);
	
	content.appendChild(ul);
	content.appendChild(document.createElement("br"));
	content.appendChild(find);
	
	div.appendChild(content);
	
	$("#event"+indice).page();
	
	$('body').append(div);
	var addresse = addressValue;
	var findB = "#findMap"+indice;
	$(findB).bind(clickEvent, function(e){
		findOnMap(addresse);
	});
}
