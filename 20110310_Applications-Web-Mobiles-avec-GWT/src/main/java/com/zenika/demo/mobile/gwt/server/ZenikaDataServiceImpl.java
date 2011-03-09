package com.zenika.demo.mobile.gwt.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zenika.demo.mobile.gwt.client.ZenikaDataService;
import com.zenika.demo.mobile.gwt.server.mock.MockDatabase;
import com.zenika.demo.mobile.gwt.shared.Conference;
import com.zenika.demo.mobile.gwt.shared.Expert;
import com.zenika.demo.mobile.gwt.shared.Formation;

public class ZenikaDataServiceImpl extends RemoteServiceServlet implements ZenikaDataService {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Formation> getAllFormations() {
		return MockDatabase.getInstance().getAllFormations();
	}

	@Override
	public List<Conference> getAllConferences() {
		return MockDatabase.getInstance().getAllConferences();
	}

	@Override
	public List<Expert> getAllExperts() {
		return MockDatabase.getInstance().getAllExperts();
	}

}
