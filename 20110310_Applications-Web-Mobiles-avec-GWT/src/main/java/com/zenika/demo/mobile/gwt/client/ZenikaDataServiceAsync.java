package com.zenika.demo.mobile.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zenika.demo.mobile.gwt.shared.Conference;
import com.zenika.demo.mobile.gwt.shared.Expert;
import com.zenika.demo.mobile.gwt.shared.Formation;

public interface ZenikaDataServiceAsync {

	void getAllFormations(AsyncCallback<List<Formation>> callback);

	void getAllConferences(AsyncCallback<List<Conference>> callback);

	void getAllExperts(AsyncCallback<List<Expert>> callback);

}
