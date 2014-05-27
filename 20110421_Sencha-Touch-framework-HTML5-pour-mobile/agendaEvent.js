/**
 * 
 */
var nameEvent;
var startDateEvent=new Date();
var endDateEvent;
var locationEvent;

function displayEventPanel(ev) {
	startDateEvent = ev.data.startDate;
	endDateEvent = ev.data.endDate;
	console.log(ev.data);
	locationEvent = ev.data.location;
	nameEvent = ev.data.name;
	myToolbar.setTitle(nameEvent);
	evtPanel.remove(comp);
	comp = new Ext.Component({
		html : '<ul><li id="title">Start Date </li><li>' + startDateEvent
				+ '</li>' + '<li id="title">End Date </li><li>' + endDateEvent
				+ '</li>' + '<li id="title">Location </li><li>' + locationEvent
				+ '</li></ul>'
				+ '<style> .event #title {color: white; background: ' + color
				+ ';font-weight: bold;}'
	});
	evtPanel.add(comp);
	evtPanel.doLayout();
	pastItem = currentItem;
	currentItem = 2;
	p.setActiveItem(currentItem);

}

comp = new Ext.Component({
	html : 'details'
});

var backButton2 = new Ext.Button({
	ui : 'back',
	text : 'Back'
});

var homeButton2 = new Ext.Button({
	ui : 'action',
	iconMask : true,
	iconCls : 'home'

});

var goToMapButton = new Ext.Button({
	ui : 'action',
	text : 'Find on Map',
	iconMask : true,
	iconCls : 'locate'
});

/**
 * Buttons Listeners
 */
homeButton2.on('tap', function() {
	pastItem = currentItem;
	currentItem = 0;
	p.setActiveItem(0);
});
backButton2.on('tap', function() {
	if (currentItem > 0) {
		var item = pastItem;
		currentItem = pastItem;
		pastItem = item;
	}
	p.setActiveItem(currentItem);
});
goToMapButton.on('tap', function() {
	pastItem = currentItem;
	currentItem = 3;
	p.setActiveItem(currentItem);
	mapPanel.doLayout();
	codeAddress(locationEvent);
});

var myToolbar = new Ext.Toolbar({
	dock : 'top',
	items : [ backButton2, {
		xtype : 'spacer'
	}, homeButton2 ]
});

evtPanel = new Ext.Panel({
	fullscreen : true,
	cls : 'event',
	animation : 'slide',
	dockedItems : [ myToolbar, {
		xtype : 'toolbar',
		items : [ {
			xtype : 'spacer'
		}, goToMapButton, {
			xtype : 'spacer'
		} ],
		dock : 'bottom'
	} ],
	items : [ comp ]
});