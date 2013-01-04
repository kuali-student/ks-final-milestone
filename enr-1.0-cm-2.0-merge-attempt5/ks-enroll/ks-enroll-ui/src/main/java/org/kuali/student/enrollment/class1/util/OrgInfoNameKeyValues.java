package org.kuali.student.enrollment.class1.util;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class does the lookups for the states associated with HoldIssueInfo's lifecycle
 *
 * @author Kuali Student Team
 */
public class OrgInfoNameKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient OrganizationService orgService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        List<TypeInfo> orgTypes = new ArrayList<TypeInfo>();

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        try {
            orgTypes = getOrganizationService().getOrgTypes(context);
            for(TypeInfo type : orgTypes) {
                List<String> orgIds = (getOrganizationService().getOrgIdsByType(type.getKey(), context));
                if(!orgIds.isEmpty()) {
                    List<OrgInfo> orgInfo = getOrganizationService().getOrgsByIds(orgIds, context);
                    for (OrgInfo org : orgInfo) {
                        ConcreteKeyValue keyValue = new ConcreteKeyValue();
                        keyValue.setKey(org.getId());
                        keyValue.setValue(org.getShortName());
                        keyValues.add(keyValue);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keyValues;
    }

    protected OrganizationService getOrganizationService(){
        if(orgService == null) {
            orgService = (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return orgService;
    }
}
