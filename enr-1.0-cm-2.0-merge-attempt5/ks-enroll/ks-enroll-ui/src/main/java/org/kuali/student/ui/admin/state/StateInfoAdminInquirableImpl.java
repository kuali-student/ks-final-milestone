/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.ui.admin.state;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.StateServiceConstants;


public class StateInfoAdminInquirableImpl extends InquirableImpl
{
	private static final Logger LOG = Logger.getLogger(StateInfoAdminInquirableImpl.class);
	private transient StateService stateService;
	private final static String PRIMARY_KEY = "key";
    private static final long serialVersionUID = 1L;
	@Override
	public StateInfo retrieveDataObject(Map<String, String> parameters)
	{
		String key = parameters.get(PRIMARY_KEY);
		try
		{
			StateInfo info = this.getStateService().getState(key, getContextInfo());
			return info;
		}
		catch (Exception ex) {
		    throw new RuntimeException(ex);
		}
	}

	public void setStateService(StateService stateService)
	{
		    this.stateService = stateService;
	}

	public StateService getStateService()
	{
		if (stateService == null)
		{
			QName qname = new QName(StateServiceConstants.NAMESPACE,StateServiceConstants.SERVICE_NAME_LOCAL_PART);
			stateService = (StateService) GlobalResourceLoader.getService(qname);
		}
		return this.stateService;
	}

	private ContextInfo getContextInfo() {
	    return ContextBuilder.loadContextInfo();
	}
}

