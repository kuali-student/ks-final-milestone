package org.kuali.student.enrollment.class1.hold.util;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.core.person.service.PersonService;
import org.kuali.student.core.person.service.PersonServiceNamespace;
import org.kuali.student.enrollment.class1.hold.service.facade.HoldIssueAuthorizingOrgFacade;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.constants.OrganizationServiceConstants;

import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.organization.service.OrganizationService;


import javax.xml.namespace.QName;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 *
 * Utility Class for common auto generated reg group functions
 */
public class HoldsResourceLoader {

    private static HoldService holdService;
    private static OrganizationService organizationService;
    private static AcademicCalendarService academicCalendarService;
    private static HoldIssueAuthorizingOrgFacade holdIssueAuthorizingOrgFacade;
    private static PersonService personService;
    private static StateService stateService;

    public static HoldService getHoldService(){
        if(holdService == null) {
            holdService = (HoldService) GlobalResourceLoader.getService(new QName(HoldServiceConstants.NAMESPACE, HoldServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return holdService;
    }

    public static OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return organizationService;
    }

    public static AcademicCalendarService getAcademicCalendarService() {
        if (academicCalendarService == null) {
            academicCalendarService = GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return academicCalendarService;
    }

    public static HoldIssueAuthorizingOrgFacade getHoldIssueAuthorizingOrgFacade() {
        if (holdIssueAuthorizingOrgFacade == null) {
            holdIssueAuthorizingOrgFacade = (HoldIssueAuthorizingOrgFacade) GlobalResourceLoader.getService(new QName(HoldsConstants.NAMESPACE, HoldsConstants.SERVICE_NAME_LOCAL_PART));
        }
        return holdIssueAuthorizingOrgFacade;
    }

    public static PersonService getPersonService() {
        if (personService == null) {
            personService = GlobalResourceLoader.getService(new QName(PersonServiceNamespace.NAMESPACE, PersonServiceNamespace.SERVICE_NAME_LOCAL_PART));
        }
        return personService;
    }

    public static StateService getStateService(){
        if (stateService == null){
            stateService = GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }

}
