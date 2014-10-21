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
package org.kuali.student.ui.admin.atp;


import java.util.Map;
import javax.xml.namespace.QName;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;


public class AtpInfoAdminInquirableImpl extends InquirableImpl
{
	private transient AtpService atpService;
	private final static String PRIMARY_KEY = "id";
    private static final long serialVersionUID = 1L;
	@Override
	public AtpInfo retrieveDataObject(Map<String, String> parameters)
	{
		String key = parameters.get(PRIMARY_KEY);
		try
		{
            return this.getAtpService().getAtp(key, getContextInfo());
		}
		catch (Exception ex) {
		    throw new RuntimeException(ex);
		}
	}

	public void setAtpService(AtpService atpService)
	{
		    this.atpService = atpService;
	}

	public AtpService getAtpService()
	{
		if (atpService == null)
		{
			QName qname = new QName(AtpServiceConstants.NAMESPACE,AtpServiceConstants.SERVICE_NAME_LOCAL_PART);
			atpService = (AtpService) GlobalResourceLoader.getService(qname);
		}
		return this.atpService;
	}

	private ContextInfo getContextInfo() {
	    return ContextBuilder.loadContextInfo();
	}
}

