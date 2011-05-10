/**
 * 
 */
var backButton1 = new Ext.Button({
	ui : 'back',
	text : 'Back'
});

var homeButton1 = new Ext.Button({
	ui : 'action',
	text : 'Home',
	iconMask : true,
	iconCls : 'home'
});

var saveButton = new Ext.Button({
	ui : 'action',
	text : 'Save',
	iconMask : true,
	iconCls : 'add'
});

var cancelButton = new Ext.Button({
	ui : 'decline',
	text : 'Cancel',
	iconMask : true,
	iconCls : 'delete'
});

cancelButton.on('tap', function() {
	pastItem = currentItem;
	currentItem = 0;
	p.setActiveItem(0);
	console.log(pastItem);
});
homeButton1.on('tap', function() {
	pastItem = currentItem;
	currentItem = 0;
	p.setActiveItem(0);
	console.log(pastItem);
});
backButton1.on('tap', function() {
	if (currentItem > 0) {
		pastItem = currentItem;
		currentItem--;
	}
	console.log(pastItem);
	p.setActiveItem(currentItem);
});

var myform = new Ext.form.FormPanel({
	cls : 'form',
	scroll : 'vertical',
	items : [ {
		xtype : "textfield",
		name : "name",
		label : "Name",
		placeHolder : "Enter the event's name"
	}, {
		xtype : "datepickerfield",
		name : "startDate",
		label : "Start Date",
		picker : {
			yearFrom : 2011,
			yearTo : 2020
		}
	}, {
		xtype : "datepickerfield",
		name : "endDate",
		label : "End Date",
		picker : {
			yearFrom : 2011,
			yearTo : 2020
		}
	}, {
		xtype : "textfield",
		name : "location",
		label : "Location",
		placeHolder : "Enter the event's address"
	}, {
		xtype : 'selectfield',
		name : 'Repetition',
		label : 'Repetition',
		options : [ {
			text : 'None',
			value : 'none'
		}, {
			text : 'Daily',
			value : 'daily'
		}, {
			text : 'Weekly',
			value : 'weekly'
		}, {
			text : 'Monthly',
			value : 'monthly'
		} ]
	}, {
		xtype : 'togglefield',
		name : 'alert',
		label : 'Alert'
	}, {
		xtype : "emailfield",
		name : "email",
		label : "Email",
		placeHolder : "you@example.com"
	}, ]

});

newEventPanel = new Ext.Panel({
	fullscreen : true,
	layout : {
		type : 'hbox',
		align : 'center',
		pack : 'justify'
	},
	dockedItems : [ {
		xtype : 'toolbar',
		items : [ backButton1, {
			xtype : 'spacer'
		}, homeButton1 ],
		title : 'New Event',
		dock : 'top'
	}, {
		xtype : 'toolbar',
		items : [ cancelButton, {
			xtype : 'spacer'
		}, saveButton ],
		dock : 'bottom'
	} ],
	items : [ myform ]
});