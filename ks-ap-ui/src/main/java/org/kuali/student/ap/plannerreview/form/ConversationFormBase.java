package org.kuali.student.ap.plannerreview.form;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;
import org.kuali.student.ap.plannerreview.dto.ConversationAdvisorInfo;

import java.util.HashMap;
import java.util.Map;

public class ConversationFormBase extends UifFormBase {

	private static final long serialVersionUID = 6362312801092393144L;
	
	private static final Logger LOG = Logger
			.getLogger(ConversationFormBase.class);

	/**
	 * Map needs to have a key that is an advisorId combined with the role like so:
	 * advisorId:role (000123456:ADVR)
	 */
	private Map<String, ConversationAdvisorInfo> advisorMap = new HashMap<String, ConversationAdvisorInfo>();
	
	public Map<String, ConversationAdvisorInfo> getAdvisorMap() {
		return advisorMap;
	}
	
	public void setAdvisorMap(Map<String, ConversationAdvisorInfo> advisorMap) {
		this.advisorMap = advisorMap;
	}
	
	public ConversationAdvisor getAdvisorInfo(String advisorId, String advisorRole) {
		String key = advisorId + ":" + advisorRole;
		if (advisorMap != null && advisorMap.containsKey(key)) {
			return advisorMap.get(key);
		}
		LOG.error("Unable to find advisor in map with the key: " + key);
		return null;
	}

	
	
	/*
	 * Some stuff that is for development only
	 */
	
	/**
	 * Flag indicating that we want to use mock data
	 */
	private boolean mockData = false;
	
	public boolean isMockData() {
		return mockData;
	}

	public void setMockData(boolean mockData) {
		this.mockData = mockData;
	}

}
