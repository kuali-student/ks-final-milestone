package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 9/25/12
 * Time: 3:10 PM
 * General Schedule of Classes utility file. Methods should be static
 */
public class ScheduleOfClassesUtil {

    private static final Logger LOG = Logger.getLogger(ScheduleOfClassesUtil.class);

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
        List<AtpInfo> atps;

        // Build a predicate to search for published Socs
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        qBuilder.setPredicates();
        Predicate pred = equal(CourseOfferingSetServiceConstants.SearchParameters.SOC_STATE, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY);
        qBuilder.setPredicates(pred);
        try {
            socs = courseOfferingSetService.searchForSocs(qBuilder.build(), context);
            for(SocInfo soc: socs){
                // Add all published Soc termIds to termIds List
                termIds.add(soc.getTermId());
            }
            // Use AtpService to get Term name by Id
            atps = atpService.getAtpsByIds(termIds, context);
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
            LOG.error(String.format("ATP %s has a null start or end date.", atp.getId()));
            return false;
        }
    }

    /**
     * Build and populate requisites
     *
     * @param aoIds
     */
    public static void loadRequisites(SOCRequisiteWrapper reqWrapper, List<String> aoIds) {

        Map<String, String> overriddenRules = new HashMap<String, String>();

        //Populate map of overridden CO requisites
        for (String ruleType : reqWrapper.getRuleTypes()) {
            String coRuleId = getRuleIdForType(reqWrapper.getCoRules(), ruleType);
            if(coRuleId!=null){
                for (Map.Entry<String, List<RuleDefinition>> aoEntry : reqWrapper.getAoToRulesMap().entrySet()) {
                    String aoRuleId = getRuleIdForType(aoEntry.getValue(), ruleType);
                    if(aoRuleId!=null){
                        overriddenRules.put(ruleType, reqWrapper.getNlMap().get(coRuleId));
                    }
                }
            }
        }

        Map<String, String> suppressNullMap = loadAORequisites(reqWrapper, overriddenRules);

        if (!overriddenRules.isEmpty()) {
            loadCOOverriddenRules(reqWrapper, overriddenRules, suppressNullMap, aoIds);
        }

        if (!reqWrapper.getCoRules().isEmpty()) {
            loadCORequisites(reqWrapper, overriddenRules);
        }
    }

    private static String getRuleIdForType(List<RuleDefinition> rules, String ruleType){
        for(RuleDefinition rule : rules){
            if(ruleType.equals(rule.getTypeId())){
                return rule.getId();
            }
        }

        return null;
    }

    /**
     * Build map of AO requisites and add overridden requisites
     *
     * @param overriddenRules
     */
    private static Map<String, String> loadAORequisites(SOCRequisiteWrapper reqWrapper, Map<String, String> overriddenRules) {

        Map<String, String> suppressNullMap = new HashMap<String, String>();
        for (String ruleType : reqWrapper.getRuleTypes()) {
            for (Map.Entry<String, List<RuleDefinition>> aoEntry : reqWrapper.getAoToRulesMap().entrySet()) {
                String aoRequisite = null;
                String aoRuleId = getRuleIdForType(aoEntry.getValue(), ruleType);
                if (aoRuleId!=null) {
                    String aoValue = StringUtils.substringAfter(reqWrapper.getNlMap().get(aoRuleId), ":");
                    if(aoValue.length() > 1) {
                        aoRequisite = reqWrapper.getNlMap().get(aoRuleId);
                    } else {
                        suppressNullMap.put(aoEntry.getKey(), ruleType);
                    }
                } else if (!overriddenRules.isEmpty()) {
                    if (overriddenRules.containsKey(ruleType)) {
                        aoRequisite = overriddenRules.get(ruleType);
                    }
                }

                if ((aoRequisite!=null)&&(!aoRequisite.isEmpty())) {
                    if (reqWrapper.getAoRequisiteMap().containsKey(aoEntry.getKey())) {
                        reqWrapper.getAoRequisiteMap().get(aoEntry.getKey()).put(ruleType, aoRequisite);
                        continue;
                    }
                    Map<String, String> temp = new LinkedHashMap<String, String>();
                    temp.put(ruleType, aoRequisite);
                    reqWrapper.getAoRequisiteMap().put(aoEntry.getKey(), temp);
                }
            }
        }
        return suppressNullMap;
    }

    /**
     * Populate AO requisite map with overridden requisites for all outstanding ActivityOfferingWrappers
     *
     * @param overriddenRules
     * @param aoIds
     */
    private static void loadCOOverriddenRules(SOCRequisiteWrapper reqWrapper, Map<String, String> overriddenRules,
                                      Map<String, String> suppressNullMap, List<String> aoIds) {

        for (String aoId : aoIds) {
            if (!reqWrapper.getAoRequisiteMap().containsKey(aoId)) {
                if (suppressNullMap.containsKey(aoId)) {
                    Map<String, String> temp = new LinkedHashMap<String, String>(overriddenRules);
                    temp.remove(suppressNullMap.get(aoId));
                    reqWrapper.getAoRequisiteMap().put(aoId, temp);
                    continue;
                }
                reqWrapper.getAoRequisiteMap().put(aoId, overriddenRules);
            }
        }
    }

    /**
     * Populate CO requisites with requisites not overridden
     */
    private static void loadCORequisites(SOCRequisiteWrapper reqWrapper, Map<String, String> overriddenRules) {
        for (String ruleType : reqWrapper.getRuleTypes()) {
            if(overriddenRules.containsKey(ruleType)){
                continue;
            }
            String ruleId = getRuleIdForType(reqWrapper.getCoRules(), ruleType);
            if(ruleId!=null){
                reqWrapper.getCoRequisite().append(reqWrapper.getNlMap().get(ruleId));
            }
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
