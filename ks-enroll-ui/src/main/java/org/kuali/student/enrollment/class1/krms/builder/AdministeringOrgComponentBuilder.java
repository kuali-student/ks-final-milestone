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

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/03/01
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
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
    protected OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return organizationService;
    }

}
