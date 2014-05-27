/**
 * sql.js
 */

var db = openDatabase('db2', '1.0', 'my first database', 2 * 1024 * 1024);
/**
 * variables dans lesquels on va stoquer les valeurs entrées dans le formulaire
 */
var lastname;
var firstname;
var birth;
var phone;
var email;
var website;

function handleSubmit(){
	//recupération des entrées
	lastname = document.forms["myform"].lastName.value;
	firstname = document.forms["myform"].firstName.value;
	birth = document.forms["myform"].birth.value;
	phone = document.forms["myform"].phone.value;
	email = document.forms["myform"].mail.value;
	website = document.forms["myform"].webSite.value;
	if(!lastname || !firstname){
		alert("les champs nom et prenom sont obligatoires");
	}else{
		insertData();
	}	
}	

function display(lastname, firstname, birth, phone, email, website) {
    var row = document.createElement("tr");
    var lastnameCell = document.createElement("td");
    var firstnameCell = document.createElement("td");
    var birthCell = document.createElement("td");
    var phoneCell = document.createElement("td");
    var emailCell = document.createElement("td");
    var websiteCell = document.createElement("td");
    
    lastnameCell.textContent = lastname;
    firstnameCell.textContent = firstname;
    birthCell.textContent = birth;
    phoneCell.textContent = phone;
    emailCell.textContent = email;
    websiteCell.textContent = website;

    row.appendChild(lastnameCell);
    row.appendChild(firstnameCell);
    row.appendChild(birthCell);
    row.appendChild(phoneCell);
    row.appendChild(emailCell);
    row.appendChild(websiteCell);
    
    document.getElementById("liste").appendChild(row);
}
	
function selectQuery() {
    db.transaction(function (tx) {
            tx.executeSql('SELECT * from Persons', [], function(tx, result) {
                // log SQL result set
                for (var i=0; i<result.rows.length; i++) {
                    var item = result.rows.item(i);
                    display(item.lastname, item.firstname, item.dateofbirth, item.phone, item.email, item.website);
                }
            });
        });
}

function initDatabase() {	
    db.transaction(function (tx) {
            tx.executeSql('CREATE TABLE IF NOT EXISTS Persons (id integer primary key autoincrement, lastname, firstname, dateofbirth, phone, email, website)');
    });
}

function insertData(){
	 db.transaction(function (tx) {
		 tx.executeSql('INSERT INTO Persons (lastname, firstname, dateofbirth, phone, email, website) VALUES (?, ?, ?, ?, ?, ?)', 
				 [lastname, firstname, birth, phone, email, website]
		 );
	 },  function (error){
		 alert("le formulaire n'a pas été correctement validé !");
	 });
	 document.location="liste.html";
}
