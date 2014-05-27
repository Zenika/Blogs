package com.zenika.demo.mobile.gwt.client.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.zenika.demo.mobile.gwt.shared.Expert;

public class ExpertClickHandler implements ClickHandler {

	Expert expert;

	public ExpertClickHandler(Expert expert) {
		this.expert = expert;
	}

	@Override
	public void onClick(ClickEvent event) {
		fillExpertDetail();
	}

	private void fillExpertDetail() {
		RootPanel formationDetailTitle = RootPanel.get("expertdetailtitle");
		formationDetailTitle.clear();
		formationDetailTitle.add(new Label(expert.getName()));

		RootPanel formationDetailPanel = RootPanel.get("expertdetailpanel");
		formationDetailPanel.clear();

		FlexTable table = new FlexTable();
		int row = -1;
		table.setText(++row, 0, "Nom");
		table.setText(row, 1, expert.getName());
		table.getCellFormatter().addStyleName(row, 0, "rowTitle");

		table.setText(++row, 0, expert.getDescription());
		table.getFlexCellFormatter().setColSpan(row, 0, 2);
		formationDetailPanel.add(table);
	}

}
