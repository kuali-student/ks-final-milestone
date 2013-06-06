/**
 * Copyright 2005-2012 The Kuali Foundation
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
package org.kuali.student.enrollment.class1.krms.builder;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.AcademicRecordServiceConstants;
import org.kuali.student.r2.common.util.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdministeringOrgComponentBuilder implements ComponentBuilder<EnrolPropositionEditor> {

    private OrganizationService organizationService;

    private static final String ORGANIZATION_KEY = "kuali.term.parameter.type.org.id";

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(EnrolPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String orgId = termParameters.get(ORGANIZATION_KEY);
        if (orgId != null) {
            try {
                OrgInfo orgInfo = this.getOrganizationService().getOrg(orgId, ContextUtils.getContextInfo());
                propositionEditor.setOrgInfo(orgInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(EnrolPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getOrgInfo() != null){
            termParameters.put(ORGANIZATION_KEY, propositionEditor.getOrgInfo().getId());
        }
        return termParameters;
    }

    @Override
    public void onSubmit(EnrolPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return organizationService;
    }

}
