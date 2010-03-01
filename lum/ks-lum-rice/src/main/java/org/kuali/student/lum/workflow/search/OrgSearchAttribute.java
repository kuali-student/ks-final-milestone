/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.workflow.search;

import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.docsearch.DocumentSearchContext;
import org.kuali.rice.kew.docsearch.SearchableAttributeValue;
import org.kuali.rice.kew.doctype.service.impl.DocumentTypeServiceImpl;
import org.kuali.rice.kns.workflow.attribute.KualiXmlSearchableAttributeImpl;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;

/**
 * Extension for CluCreditCourse documents searches that converts Organization ID to the Organization Long Name
 * @author lindholm
 *
 */
public class OrgSearchAttribute extends KualiXmlSearchableAttributeImpl {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DocumentTypeServiceImpl.class);

    private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<SearchableAttributeValue> getSearchStorageValues(DocumentSearchContext documentSearchContext) {
		OrganizationService orgService = null;
		List<SearchableAttributeValue> attributeValues = (List<SearchableAttributeValue>)super.getSearchStorageValues(documentSearchContext);
		for (SearchableAttributeValue value : attributeValues) {
			String orgId = (String)value.getSearchableAttributeValue();
			if (orgId != null) {
				try {
					if (orgService == null) {
						orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization","OrganizationService"));
					}
					OrgInfo orgInfo = orgService.getOrganization(orgId);
					value.setupAttributeValue(orgInfo.getShortName());
				} catch (DoesNotExistException e) {
					LOG.error(e);
					throw new RuntimeException(e);
				} catch (InvalidParameterException e) {
					LOG.error(e);
					throw new RuntimeException(e);
				} catch (MissingParameterException e) {
					LOG.error(e);
					throw new RuntimeException(e);
				} catch (OperationFailedException e) {
					LOG.error(e);
					throw new RuntimeException(e);
				} catch (PermissionDeniedException e) {
					LOG.error(e);
					throw new RuntimeException(e);
				}
			}
		}
		return attributeValues;
	}
}
