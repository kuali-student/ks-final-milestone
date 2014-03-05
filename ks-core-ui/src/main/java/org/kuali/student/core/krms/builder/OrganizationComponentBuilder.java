/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.krms.builder;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.student.core.krms.dto.KSPropositionEditor;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;

import javax.xml.namespace.QName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class OrganizationComponentBuilder implements ComponentBuilder<KSPropositionEditor> {

    private OrganizationService organizationService;

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(KSPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String orgId = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_ORGANIZATION_KEY);
        if (orgId != null) {
            try {
                OrgInfo orgInfo = this.getOrganizationService().getOrg(orgId, ContextUtils.getContextInfo());
                propositionEditor.setOrgInfo(orgInfo);
            } catch (Exception e) {
                throw new RuntimeException("Could not load orgarnization", e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(KSPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getOrgInfo() != null){
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_ORGANIZATION_KEY, propositionEditor.getOrgInfo().getId());
        }
        return termParameters;
    }

    @Override
    public void onSubmit(KSPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void validate(KSPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return organizationService;
    }

}
