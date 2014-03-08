package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.service.ScheduleOfClassesViewHelperService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 9/25/12
 * Time: 3:10 PM
 * General Schedule of Classes utility file. Methods should be static
 */
public class ScheduleOfClassesUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleOfClassesUtil.class);

    private static ScheduleOfClassesViewHelperService viewHelperService;
    private static AcademicCalendarService acalService;
    private static CourseOfferingSetService courseOfferingSetService;
    private static AtpService atpService;
    private static TypeService typeService;
    private static PersonService personService;
    private static OrganizationService organizationService;
    private static RuleManagementService ruleManagementService;
    private static KrmsTypeRepositoryService krmsTypeRepositoryService;

    public static ScheduleOfClassesViewHelperService getViewHelperService(ScheduleOfClassesSearchForm theForm) {
        if (viewHelperService == null) {
            if (theForm.getView().getViewHelperService() != null) {
                viewHelperService = (ScheduleOfClassesViewHelperService) theForm.getView().getViewHelperService();
            } else {
                viewHelperService = (ScheduleOfClassesViewHelperService) theForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }

    public static AcademicCalendarService getAcademicCalendarService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    public static CourseOfferingSetService getCourseOfferingSetService() {
        if (courseOfferingSetService == null) {

            courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }

    public static AtpService getAtpService() {
        if (atpService == null) {
            atpService = (AtpService) GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return atpService;
    }

    public static TypeService getTypeService() {
        if (typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return typeService;
    }

    public static OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }

    public static PersonService getPersonService() {
        if (personService == null) {
            personService = KimApiServiceLocator.getPersonService();
        }
        return personService;
    }

    public static RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    public static KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        if (krmsTypeRepositoryService == null) {
            krmsTypeRepositoryService = (KrmsTypeRepositoryService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "krmsTypeRepositoryService"));
        }
        return krmsTypeRepositoryService;
    }

    /**
     * Hiding the constructor this this is a utility class (all static methods).
     */
    private ScheduleOfClassesUtil() {}

    /**
     * Builds a list of terms by querying for SOCs with state == published, then querying for the related ATPs by id.
     *
     * @param courseOfferingSetService
     * @param atpService
     * @param context
     * @return
     */
    public static List<AtpInfo> getValidSocTerms(CourseOfferingSetService courseOfferingSetService, AtpService atpService, ContextInfo context) {
        List<SocInfo> socs;
        List<String> termIds = new ArrayList<String>();
        List<AtpInfo> atps = new ArrayList<AtpInfo>();

        // Build a predicate to search for published Socs
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        qBuilder.setPredicates();
        Predicate pred = equal(CourseOfferingSetServiceConstants.SearchParameters.SOC_STATE, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY);
        qBuilder.setPredicates(pred);
        try {
            socs = courseOfferingSetService.searchForSocs(qBuilder.build(), context);
            if (socs != null && socs.size() > 0) {
                for(SocInfo soc: socs){
                    // Add all published Soc termIds to termIds List
                    termIds.add(soc.getTermId());
                }
                // Use AtpService to get Term name by Id
                atps = atpService.getAtpsByIds(termIds, context);
            } else {
                return atps;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting Valid SOC Terms", e);
        }
        return atps;
    }

    /**
     * This method will return the term with the start date that is closest in the future.
     * If there is no future term then return null.
     * @param atpInfos
     * @return
     */
    public static AtpInfo getClosestAtp(List<AtpInfo> atpInfos) {
        Date now = new Date();
        AtpInfo closestAtp = null;

        for (AtpInfo atp : atpInfos) {
            if (isAtpValid(atp)) {
                //  Only consider the ATP if it's in the future.
                boolean isFuture = atp.getStartDate().after(now);
                if (isFuture) {
                    if (closestAtp == null) {
                        closestAtp = atp;
                    } else {
                        //  If this ATP has a sooner start date then make it "closest".
                        boolean isCloser = atp.getStartDate().before(closestAtp.getStartDate());
                        if (isCloser) {
                            closestAtp = atp;
                        }
                    }
                }
            }
        }
        return closestAtp;
    }

    /**
     * Validates that the ATP and its start and end dates aren't null.
     * @param atp
     * @return
     */
    private static boolean isAtpValid(AtpInfo atp){
        if (atp != null && atp.getStartDate() != null && atp.getEndDate() != null) {
            return true;
        } else {
            LOG.error("ATP {} has a null start or end date.", atp.getId());
            return false;
        }
    }

    /**
     * Build and populate requisites
     *
     * @param aoIds
     */
    public static void loadRequisites(SOCRequisiteWrapper reqWrapper, List<String> aoIds) {

        //Populate map of overridden CO requisites
        for (String ruleType : reqWrapper.getRuleTypes()) {

            boolean overridden = false;
            RuleDefinition coRule = getRuleForType(reqWrapper.getCoRules(), ruleType);
            if(coRule!=null){

                //Check if CO rule is overridden.
                for (Map.Entry<String, List<RuleDefinition>> aoEntry : reqWrapper.getAoToRulesMap().entrySet()) {
                    if(getRuleForType(aoEntry.getValue(), ruleType)!=null){
                        overridden = true;
                        break;
                    }
                }

                if(!overridden){
                    reqWrapper.getCoRequisite().append(reqWrapper.getNlMap().get(coRule.getId()));
                }
            }

            //Load AO Requisites.
            for (String aoId : aoIds) {
                RuleDefinition aoRule = getRuleForType(reqWrapper.getAoToRulesMap().get(aoId), ruleType);
                if(aoRule!=null){
                    if(aoRule.getPropId()==null){
                        continue; //Rule is suppressed.
                    } else {
                        addAORequisite(reqWrapper, aoId, ruleType, reqWrapper.getNlMap().get(aoRule.getId()));
                    }
                } else if (overridden) {
                    addAORequisite(reqWrapper, aoId, ruleType, reqWrapper.getNlMap().get(coRule.getId()));   //If CO rule exist, insert it.
                }
            }
        }

    }

    private static RuleDefinition getRuleForType(List<RuleDefinition> rules, String ruleType){
        if(rules==null){
            return null;
        }

        for(RuleDefinition rule : rules){
            if(ruleType.equals(rule.getTypeId())){
                return rule;
            }
        }

        return null;
    }

    private static void addAORequisite(SOCRequisiteWrapper reqWrapper, String aoId, String ruleType, String aoRequisite){
        if (reqWrapper.getAoRequisiteMap().containsKey(aoId)) {
            reqWrapper.getAoRequisiteMap().get(aoId).put(ruleType, aoRequisite);
        } else {
            Map<String, String> temp = new LinkedHashMap<String, String>();
            temp.put(ruleType, aoRequisite);
            reqWrapper.getAoRequisiteMap().put(aoId, temp);
        }
    }

    /**
     * Build and populate RegistrationGroupWrappers requisites from AO requisite map
     *
     * @param registrationGroupWrapperList
     */
    public static void loadRegRequisites(SOCRequisiteWrapper reqWrapper, List<RegistrationGroupWrapper> registrationGroupWrapperList) {
        StringBuilder firstReq;
        StringBuilder secondReq;
        StringBuilder commonReq;

        //For each RegistrationGroupWrapper and its partner with same name
        for(int i = 0; i < registrationGroupWrapperList.size(); i++) {
            RegistrationGroupWrapper registrationGroupWrapper = registrationGroupWrapperList.get(i);

            firstReq = new StringBuilder();
            secondReq = new StringBuilder();
            commonReq = new StringBuilder();

            //Retrieve RegistrationGroupWrapper with same name
            RegistrationGroupWrapper partnerRegGroup = getRegistrationGroupWrapper(registrationGroupWrapperList, registrationGroupWrapper.getRgInfo().getName(), registrationGroupWrapper.getAoActivityCodeText());

            String regAoId, partnerAoId = null;
            try{
                regAoId = KSCollectionUtils.getOptionalZeroElement(registrationGroupWrapper.getRgInfo().getActivityOfferingIds());
                if (!partnerRegGroup.getAoActivityCodeText().equals("null")) {
                    i++;
                    partnerAoId = KSCollectionUtils.getOptionalZeroElement(partnerRegGroup.getRgInfo().getActivityOfferingIds());
                }
            } catch (OperationFailedException e) {
                throw new RuntimeException("Unable to retrieve activity offering id.", e);
            }

            //Determine each RegistrationGroupWrapper's requisite and common requisite
            if(reqWrapper.getAoRequisiteMap().containsKey(regAoId) ||
                    reqWrapper.getAoRequisiteMap().containsKey(partnerAoId)) {

                for(String rule : reqWrapper.getRuleTypes()) {
                    Map<String, String> firstReqMap = reqWrapper.getAoRequisiteMap().get(regAoId);
                    Map<String, String> secondReqMap = reqWrapper.getAoRequisiteMap().get(partnerAoId);

                    if (firstReqMap != null && secondReqMap != null) {
                        if (firstReqMap.containsKey(rule) && secondReqMap.containsKey(rule)) {
                            if (firstReqMap.get(rule).equals(secondReqMap.get(rule))) {
                                commonReq.append(firstReqMap.get(rule));
                                continue;
                            }
                        }
                    }
                    if (firstReqMap != null) {
                        if (firstReqMap.containsKey(rule)) {
                            firstReq.append(firstReqMap.get(rule));
                        }
                    }
                    if (secondReqMap != null) {
                        if (secondReqMap.containsKey(rule)) {
                            secondReq.append(secondReqMap.get(rule));
                        }
                    }
                }
                //Set requisite on RegistrationGroupWrapper
                if (!commonReq.toString().isEmpty()) {
                    registrationGroupWrapper.setCommonRequisite(commonReq.toString());
                }
                if (!firstReq.toString().isEmpty()) {
                    registrationGroupWrapper.setRequisite(firstReq.toString());
                }
                if (!secondReq.toString().isEmpty()) {
                    partnerRegGroup.setRequisite(secondReq.toString());
                }
            }
        }
    }

    private static RegistrationGroupWrapper getRegistrationGroupWrapper(List<RegistrationGroupWrapper> registrationGroupWrapperList, String name, String aoCode) {
        for (RegistrationGroupWrapper registrationGroupWrapper : registrationGroupWrapperList) {
            if(registrationGroupWrapper.getRgInfo().getName().equals(name) && !registrationGroupWrapper.getAoActivityCodeText().equals(aoCode)) {
                return registrationGroupWrapper;
            }
        }
        RegistrationGroupWrapper emptyRegistrationGroupWrapper = new RegistrationGroupWrapper();
        emptyRegistrationGroupWrapper.setAoActivityCodeText("null");
        return emptyRegistrationGroupWrapper;
    }

}
