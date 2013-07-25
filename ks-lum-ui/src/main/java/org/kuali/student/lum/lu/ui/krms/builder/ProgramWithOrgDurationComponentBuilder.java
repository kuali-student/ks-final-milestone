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
package org.kuali.student.lum.lu.ui.krms.builder;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.builder.ComponentBuilderUtils;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.dto.ProgramCluSetInformation;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class ProgramWithOrgDurationComponentBuilder extends ProgramComponentBuilder {

    private OrganizationService organizationService;
    @Override
    public List<String> getComponentIds() {
        return null;
    }

    @Override
    public void resolveTermParameters(LUPropositionEditor propositionEditor, Map<String, String> termParameters) {
        super.resolveTermParameters(propositionEditor,termParameters);
        String orgId = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_ORGANIZATION_KEY);
        String durationType = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_TYPE_KEY);
        String duration = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_KEY);
        if (durationType != null) {
            propositionEditor.setDurationType(durationType);
        }
        if (duration != null) {
            propositionEditor.setDuration(Integer.parseInt(duration));
        }
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
    public Map<String, String> buildTermParameters(LUPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        termParameters = super.buildTermParameters(propositionEditor);
        termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_TYPE_KEY, propositionEditor.getDurationType());
        termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_KEY, propositionEditor.getDuration().toString());
        if (propositionEditor.getOrgInfo() != null){
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_ORGANIZATION_KEY, propositionEditor.getOrgInfo().getId());
        }
        return termParameters;


    }

    @Override
    public void onSubmit(LUPropositionEditor propositionEditor) {
        super.onSubmit(propositionEditor);
    }

    @Override
    public void validate(LUPropositionEditor propositionEditor) {
        super.validate(propositionEditor);
    }
    protected OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return organizationService;
    }
}
