/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.api.document.DocumentWithContent;
import org.kuali.rice.kew.api.document.attribute.DocumentAttribute;
import org.kuali.rice.kew.api.extension.ExtensionDefinition;
import org.kuali.rice.kew.docsearch.SearchableAttributeValue;
import org.kuali.rice.kew.doctype.service.impl.DocumentTypeServiceImpl;
import org.kuali.rice.krad.workflow.attribute.KualiXmlSearchableAttributeImpl;
import org.kuali.student.common.exceptions.*;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * Extension for CluCreditCourse documents searches that converts Organization ID to the Organization Long Name
 * @author lindholm
 *
 */
public class OrgSearchAttribute extends KualiXmlSearchableAttributeImpl {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DocumentTypeServiceImpl.class);

    private static final long serialVersionUID = 1L;

    // TODO: RICE=M9 UPGRADE check that replacing getDocumentAttributes with extractDocumentAttributes is really the intended behavior
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentAttribute> extractDocumentAttributes(ExtensionDefinition extensionDefinition, DocumentWithContent documentSearchContext) {
		OrganizationService orgService = null;
		List<DocumentAttribute> attributeValues = super.extractDocumentAttributes(extensionDefinition, documentSearchContext);
		for (DocumentAttribute value : attributeValues) {
			String orgId = (String)value.getValue();
			if (orgId != null) {
				try {
					if (orgService == null) {
						orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization","OrganizationService"));
					}
					OrgInfo orgInfo = orgService.getOrganization(orgId);
            //        TODO: RICE-M7 UPGRADE I think this is correct but I'm not sure
					((SearchableAttributeValue)value).setupAttributeValue(orgInfo.getShortName());
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
