/**
 * 
 */
package org.kuali.student.lum.workflow.qualifierresolver;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.role.QualifierResolver;
import org.kuali.student.core.organization.service.OrganizationService;

/**
 * An abstract base class that consolidates convenience methods for using the {@link OrganizationService} class.
 * 
 */
public abstract class AbstractOrganizationServiceQualifierResolver implements QualifierResolver {

    private OrganizationService organizationService;

    protected OrganizationService getOrganizationService() {
        if (null == organizationService) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization", "OrganizationService"));
        }
        return organizationService;
    }

    protected void setOrganizationService(OrganizationService orgSvc) {
        organizationService = orgSvc;
    }

}
