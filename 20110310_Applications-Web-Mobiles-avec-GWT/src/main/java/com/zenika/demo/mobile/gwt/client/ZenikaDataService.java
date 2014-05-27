package com.zenika.demo.mobile.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zenika.demo.mobile.gwt.shared.Conference;
import com.zenika.demo.mobile.gwt.shared.Expert;
import com.zenika.demo.mobile.gwt.shared.Formation;

@RemoteServiceRelativePath("zenikadata")
public interface ZenikaDataService extends RemoteService {

	List<Formation> getAllFormations();

	List<Conference> getAllConferences();

	List<Expert> getAllExperts();
}
