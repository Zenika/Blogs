package com.zenika.demo.mobile.gwt.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.zenika.demo.mobile.gwt.client.handler.ConferenceClickHandler;
import com.zenika.demo.mobile.gwt.client.handler.ExpertClickHandler;
import com.zenika.demo.mobile.gwt.client.handler.FormationClickHandler;
import com.zenika.demo.mobile.gwt.client.widget.Row;
import com.zenika.demo.mobile.gwt.client.widget.RowList;
import com.zenika.demo.mobile.gwt.shared.Conference;
import com.zenika.demo.mobile.gwt.shared.Expert;
import com.zenika.demo.mobile.gwt.shared.Formation;

public class ZenikaMobile implements EntryPoint {

	ZenikaDataServiceAsync dataService = GWT.create(ZenikaDataService.class);

	@Override
	public void onModuleLoad() {
		initialize();
	}

	private void initialize() {
		initializeConferences();
		initializeFormations();
		initializeExperts();
	}

	private void initializeExperts() {
		dataService.getAllExperts(new AsyncCallback<List<Expert>>() {

			@Override
			public void onSuccess(List<Expert> result) {
				fillExperts(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Impossible de récupérer la liste des experts");
			}
		});
	}

	protected void fillExperts(List<Expert> result) {
		RootPanel expertPanel = RootPanel.get("expertres");
		expertPanel.clear();

		if (result == null || result.size() == 0) {
			expertPanel.add(new Label("Aucun expert correspondant"));
		} else {
			RowList list = new RowList();
			Row row;
			Anchor a;
			for (Expert e : result) {
				row = new Row();
				row.addStyleName("arrow");
				a = new Anchor(e.getName());
				a.setHref("#expertdetail");
				row.add(a);
				row.addClickHandler(new ExpertClickHandler(e));
				list.add(row);
			}
			expertPanel.add(list);
		}
	}

	private void initializeFormations() {
		dataService.getAllFormations(new AsyncCallback<List<Formation>>() {

			@Override
			public void onSuccess(List<Formation> result) {
				fillFormations(result);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	protected void fillFormations(List<Formation> result) {
		RootPanel formationPanel = RootPanel.get("formationres");
		formationPanel.clear();

		if (result == null || result.size() == 0) {
			formationPanel.add(new Label("Aucune formation correspondante"));
		} else {
			RowList list = new RowList();
			Row row;
			Anchor a;
			for (Formation f : result) {
				row = new Row();
				row.addStyleName("arrow");
				a = new Anchor(f.getTitle());
				a.setHref("#formationdetail");
				row.add(a);
				row.addClickHandler(new FormationClickHandler(f));
				list.add(row);
			}
			formationPanel.add(list);
		}
	}

	private void initializeConferences() {
		dataService.getAllConferences(new AsyncCallback<List<Conference>>() {

			@Override
			public void onSuccess(List<Conference> result) {
				fillConferences(result);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	protected void fillConferences(List<Conference> result) {
		RootPanel conferencesAVenirPanel = RootPanel.get("conferencesavenirres");
		conferencesAVenirPanel.clear();
		RootPanel conferencesPasseesPanel = RootPanel.get("conferencesprecres");
		conferencesPasseesPanel.clear();

		if (result == null || result.size() == 0) {
			conferencesAVenirPanel.add(new Label("Aucune formation correspondante"));
			conferencesPasseesPanel.add(new Label("Aucune formation correspondante"));
		} else {
			RowList aVenirList = new RowList();
			RowList passeesList = new RowList();
			Row row;
			Anchor a;
			for (Conference c : result) {
				row = new Row();
				row.addStyleName("arrow");
				a = new Anchor(c.getTitle());
				a.setHref("#conferencedetail");
				row.add(a);
				row.addClickHandler(new ConferenceClickHandler(c));
				if (c.getEnded()) {
					passeesList.add(row);
				} else {
					aVenirList.add(row);
				}
			}
			if (aVenirList.getWidgetCount() == 0) {
				conferencesAVenirPanel.add(new Label("Aucune formation correspondante"));
			} else {
				conferencesAVenirPanel.add(aVenirList);
			}

			if (passeesList.getWidgetCount() == 0) {
				conferencesPasseesPanel.add(new Label("Aucune formation correspondante"));
			} else {
				conferencesPasseesPanel.add(passeesList);
			}
		}

	}
}
