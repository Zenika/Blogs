package com.zenika.demo.mobile.gwt.client.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.zenika.demo.mobile.gwt.shared.Formation;

public class FormationClickHandler implements ClickHandler {

	Formation formation;

	public FormationClickHandler(Formation formation) {
		this.formation = formation;
	}

	@Override
	public void onClick(ClickEvent event) {
		fillFormationDetail();
	}

	private void fillFormationDetail() {
		RootPanel formationDetailTitle = RootPanel.get("formationdetailtitle");
		formationDetailTitle.clear();
		formationDetailTitle.add(new Label(formation.getTitle()));

		RootPanel formationDetailPanel = RootPanel.get("formationdetailpanel");
		formationDetailPanel.clear();

		FlexTable table = new FlexTable();
		int row = -1;
		table.setText(++row, 0, formation.getTitle());
		table.getFlexCellFormatter().setColSpan(row, 0, 2);
		table.getCellFormatter().addStyleName(row, 0, "rowTitle");

		table.setText(++row, 0, formation.getSubtitle());
		table.getFlexCellFormatter().setColSpan(row, 0, 2);
		table.getCellFormatter().addStyleName(row, 0, "rowTitle");

		table.setText(++row, 0, "Date");
		table.setText(row, 1, formation.getDate());
		table.getCellFormatter().addStyleName(row, 0, "rowTitle");

		table.setText(++row, 0, "Lieu");
		table.setText(row, 1, formation.getPlace());
		table.getCellFormatter().addStyleName(row, 0, "rowTitle");

		table.setText(++row, 0, "Durée");
		table.setText(row, 1, formation.getDuration() + " jour(s)");
		table.getCellFormatter().addStyleName(row, 0, "rowTitle");

		table.setText(++row, 0, formation.getDescription());
		table.getFlexCellFormatter().setColSpan(row, 0, 2);

		formationDetailPanel.add(table);
	}

}
