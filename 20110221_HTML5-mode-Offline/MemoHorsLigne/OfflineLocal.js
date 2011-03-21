/**
 * 
 */

var indice;
	
function updateStatus(message){
	document.getElementById("status").innerHTML=message;
}

function saveData(){
	var texte = document.getElementById("data").value;
	var key=indice;
	if(texte){
		if(window.localStorage){
		window.localStorage.setItem(key, texte);
		}
		displayData(texte);
	}
	indice++;
}
function displayData(data){
	var puce = document.createElement("li");
	var contenu = document.createTextNode(data);
	puce.appendChild(contenu);
	document.getElementById("liste").appendChild(puce);
}

function getData(){
	if(window.localStorage){
		for(var i=0; i<window.localStorage.length;i++){
			var key = window.localStorage.key(i);
			var value = window.localStorage.getItem(key);
			displayData(value);
		}
	}
}
function onload(){
	if(!window.applicationCache){
		updateStatus("your browser does not support Application Cache");
	}
	if(!window.localStorage){
		updateStatus("your browser does not support LocalStorage");
	}else{
		indice=window.localStorage.length;
	}
	getData();
}