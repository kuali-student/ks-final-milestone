/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by vgadiyak on 9/10/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.rice.krad.uif.component.ReferenceCopy;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.util.KSCollectionUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.CourseOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.service.ScheduleOfClassesViewHelperService;
import org.kuali.student.enrollment.class2.scheduleofclasses.sort.KSComparator;
import org.kuali.student.enrollment.class2.scheduleofclasses.sort.KSComparatorChain;
import org.kuali.student.enrollment.class2.scheduleofclasses.sort.impl.ActivityOfferingCodeComparator;
import org.kuali.student.enrollment.class2.scheduleofclasses.sort.impl.ActivityOfferingTypeComparator;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.SOCRequisiteHelper;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.SOCRequisiteWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * This class performs queries for scheduling of classes
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesViewHelperServiceImpl extends CourseOfferingManagementViewHelperServiceImpl implements ScheduleOfClassesViewHelperService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ScheduleOfClassesViewHelperServiceImpl.class);

    private OrganizationService organizationService;
    private TypeService typeService;
    private RuleManagementService ruleManagementService;

    @ReferenceCopy
    private KSComparatorChain activityComparatorChain;
    @ReferenceCopy
    private KSComparatorChain regGroupComparatorChain;

    /**
     * These are templates to make creating "additional information" icons with BubblePopOver tooltips.
     * The $FOO items are placeholders which are filled in when the icon is created. The 'script' attribute has been changed
     * from 'first_run' to 'soc_run' and code has been added to onDocumentReady initialize the popovers.
     */
    private final static String TOOLTIP_CREATE_SCRIPT
        = "<input type='hidden' data-role='script' data-for='$ID' value=\"createTooltip('$ID', '$TEXT', " +
            "{position:'bottom',alwaysVisible:false,themeName:'black',themePath:'$APPLICATION_URL/plugins/tooltip/jquerybubblepopup-theme/',selectable:true,align:'left',tail:{ align:'left', hidden: false }}, true, false);\" script='soc_run'>";

    private final static String TOOLTIP_ADD_ATTRIBUTE
        = "<input type='hidden' data-role='script' data-for='$ID' " +
            "value=\"addAttribute('$ID', 'class', 'uif-tooltip', true);\" script='soc_run'>";

    private final static String IMG = "<img id='$ID' src='$SRC'/>";

    /**
     * Loads all the Course offerings in a specific term by course code.
     *
     * @param termId
     * @param courseCode
     * @param form
     * @throws Exception
     */
    public void loadCourseOfferingsByTermAndCourseCode(String termId, String courseCode, ScheduleOfClassesSearchForm form) throws Exception {

        Map additionalParams = new HashMap();
        additionalParams.put(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);

        buildCOResultsDisplay(form, termId, additionalParams);

    }

    /**
     * Loads course offerings from a term which matches the title or description
     *
     * @param termId
     * @param titleOrDescription
     * @param form
     * @throws Exception
     */
    public void loadCourseOfferingsByTitleAndDescription(String termId, String titleOrDescription, ScheduleOfClassesSearchForm form) throws Exception {

        Map additionalParams = new HashMap();
        additionalParams.put(CourseOfferingManagementSearchImpl.SearchParameters.DESCRIPTION, titleOrDescription);

        buildCOResultsDisplay(form, termId, additionalParams);

    }

    /**
     *
     * Loads course offerings from a term which has a specific instructor
     *
     * @param termId
     * @param instructorId
     * @param instructorName
     * @param form
     * @throws Exception
     */
    public void loadCourseOfferingsByTermAndInstructor(String termId, String instructorId, String instructorName, ScheduleOfClassesSearchForm form) throws Exception {

        // Search ID based on organizationName
        if (StringUtils.isBlank(instructorId)) {

            Map<String, String> searchCriteria = new HashMap<String, String>();
            searchCriteria.put(KIMPropertyConstants.Person.PRINCIPAL_NAME, instructorName);

            List<Person> instructors = getPersonService().findPeople(searchCriteria);

            //JIRA FIX : KSENROLL-8730 - Added NULL check
            int firstInstructor = 0;

            if (instructors == null || instructors.isEmpty()) {
                LOG.error("Error: Can't find any instructor for selected instructor in term: " + termId);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM, "Invalid Principal Id/Name.");
                return;
            } else if (instructors.size() > 1) {
                LOG.error("Error: There is more than one instructor with the same name in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_MULTIPLE_INSTRUCTOR_IS_FOUND, instructorName);
                return;
            } else {
                instructorId = instructors.get(firstInstructor).getPrincipalId();
            }
        }

        if (StringUtils.isNotBlank(instructorId)) {

            ContextInfo context = ContextUtils.createDefaultContextInfo();

            //this is a cross service search between LPR and LUI, so it is inefficient (no join)
            //First get all the luiIds that the instructor is teaching
            //Only get active courses
            List<String> luiIds = getLprService().getLuiIdsByPersonAndTypeAndState(instructorId, LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, LprServiceConstants.ACTIVE_STATE_KEY, context);

            List<String> courseOfferingIds = null;

            if (!luiIds.isEmpty()) {
                //Now find all the COs with Aos that are attached to that instructor.
                // Build a query
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.and(
                        PredicateFactory.in("aoid", luiIds.toArray()),
                        PredicateFactory.equalIgnoreCase("atpId", termId)),
                        PredicateFactory.equal("luiState", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY));
                QueryByCriteria criteria = qbcBuilder.build();
                courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, context);
            }

            //If nothing was found then error
            if (courseOfferingIds.isEmpty()) {
                LOG.error("Error: Can't find any Course Offering for selected Instructor in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "instructor", instructorId, termId);
                form.getCoDisplayWrapperList().clear();
                return;
            }

            Map additionalParams = new HashMap();
            additionalParams.put(CourseOfferingManagementSearchImpl.SearchParameters.CO_IDS, courseOfferingIds);

            buildCOResultsDisplay(form, termId, additionalParams);
        }

    }

    /**
     * Loads course offerings by term and department.
     *
     * @param termId
     * @param organizationId
     * @param organizationName
     * @param form
     * @throws Exception
     */
    public void loadCourseOfferingsByTermAndDepartment(String termId, String organizationId, String organizationName, ScheduleOfClassesSearchForm form) throws Exception {

        // Search ID based on organizationName
        if (StringUtils.isBlank(organizationId)) {

            QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
            qBuilder.setPredicates(PredicateFactory.equalIgnoreCase("longName", organizationName));
            QueryByCriteria query = qBuilder.build();
            OrganizationService organizationService = getOrganizationService();

            List<String> orgIDs = organizationService.searchForOrgIds(query, ContextUtils.createDefaultContextInfo());

            if (orgIDs.isEmpty()) {
                LOG.error("Error: Can't find any Department for selected Department in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "department", organizationName, termId);
                return;
            } else if (orgIDs.size() > 1) {
                LOG.error("Error: There is more than one departments with the same long name in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_MULTIPLE_DEPARTMENT_IS_FOUND, organizationName);
                return;
            } else {
                organizationId = KSCollectionUtils.getRequiredZeroElement(orgIDs);
            }
        }

        Map additionalParams = new HashMap();
        additionalParams.put(CourseOfferingManagementSearchImpl.SearchParameters.DEPARTMENT_ID, organizationId);

        buildCOResultsDisplay(form, termId, additionalParams);

    }

    /**
     * Loads all the Course offerings from a term which matches the additional search parameters.
     *
     * @param form
     * @param termId
     * @param searchParameters
     * @throws Exception
     */
    protected void buildCOResultsDisplay(ScheduleOfClassesSearchForm form, String termId, Map<String, Object> searchParameters) throws Exception {

        form.getCoDisplayWrapperList().clear();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());

        for (Map.Entry< String, Object > entry : searchParameters.entrySet()){
            if (entry.getValue() instanceof String) {
                 searchRequest.addParam(entry.getKey(), (String)entry.getValue());
            } else if (entry.getValue() instanceof List) {
                searchRequest.addParam(entry.getKey(), (List)entry.getValue());
            } else {
                throw new RuntimeException("Invalid Search Parameter type. Only String and List are allowed.");
            }
        }

        List<String> filterCOStates = new ArrayList<String>(1);
        filterCOStates.add(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);

        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.FILTER_CO_STATES, filterCOStates);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, BooleanUtils.toStringTrueFalse(false));
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.INCLUDE_PASSFAIL_AUDIT_HONORS_RESULTS, BooleanUtils.toStringTrueFalse(true));

        loadCourseOfferings(searchRequest, form);

        if (form.getCoDisplayWrapperList() == null || form.getCoDisplayWrapperList().isEmpty()) {
            LOG.error("Error: Can't find any Course Offering for the selected search");
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM, "Can't find any Course Offering for the selected search");
            return;
        }

        for (CourseOfferingDisplayWrapper coDisplayWrapper : form.getCoDisplayWrapperList()) {

            // Adding Information (icons)
            StringBuilder information = new StringBuilder();
           if (coDisplayWrapper.isHonorsCourse()) {
               information.append(makeInfoIconWithTooltip(ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG,ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_COURSE));
           }

           if (StringUtils.equals(coDisplayWrapper.getCourseOfferingGradingOptionKey(),LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY)) {
               information.append(makeInfoIconWithTooltip(ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_SATISFACTORY_IMG, ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_SATISFACTORY));
           } else if (StringUtils.equals(coDisplayWrapper.getCourseOfferingGradingOptionKey(),LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE)) {
               information.append(makeInfoIconWithTooltip(ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_PERCENT_IMG, ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_PERCENT));
           }

           if (coDisplayWrapper.isAuditCourse()){
               information.append(makeInfoIconWithTooltip(ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_AUDIT_IMG, ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_AUDIT));
           }

           if (coDisplayWrapper.isStudentSelectablePassFail()) {
               information.append(makeInfoIconWithTooltip(ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_PASSFAIL_IMG, ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_PASSFAIL));
           }

            coDisplayWrapper.setInformation(information.toString());

        }
    }

    /**
     * Creates an HTML img tag and the two hidden input fields to necessary to create a tooltip.
     * @param src The URL of the image to be used in the icon.
     * @param text The text to display in the tooltip.
     * @return An additional information icon with a tooltip attached.
     */
    private String makeInfoIconWithTooltip(String src, String text) {
        String id = UUIDHelper.genStringUUID();
        String tt = IMG + TOOLTIP_CREATE_SCRIPT + TOOLTIP_ADD_ATTRIBUTE;
        tt = tt.replace("$APPLICATION_URL", ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.ConfigParameters.APPLICATION_URL));
        tt = tt.replace("$ID", id);
        tt = tt.replace("$SRC", src);
        tt = tt.replace("$TEXT", text);
        return tt;
    }

    /**
     * Method to build the course offering requisites string for display
     *
     * @param courseOfferingId
     * @return Map of course offering requisites
     */
    public SOCRequisiteHelper retrieveRequisites(String courseOfferingId, List<ActivityOfferingWrapper> activityOfferingWrapperList) {

        SOCRequisiteHelper reqHelper = new SOCRequisiteHelper();

        String catalogUsageId = getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KSKRMSServiceConstants.KRMS_NL_TYPE_CATALOG, PermissionServiceConstants.KS_SYS_NAMESPACE).getId();

        //Retrieve reference object bindings for course offering
        List<ReferenceObjectBinding> coRefObjectsBindingList = ruleManagementService.findReferenceObjectBindingsByReferenceObject(
                CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING, courseOfferingId);

        //Retrieve agenda's for course offering
        for(ReferenceObjectBinding coReferenceObjectBinding : coRefObjectsBindingList) {
            AgendaDefinition agendaDefinition = ruleManagementService.getAgenda(coReferenceObjectBinding.getKrmsObjectId());
            AgendaItemDefinition agendaItem = ruleManagementService.getAgendaItem(agendaDefinition.getFirstItemId());
            loadNaturalLanguageForRuleTypes(reqHelper.getReqWrapper().getCoRequisiteTypeMap(), agendaItem, catalogUsageId, reqHelper.getReqWrapper().getRuleTypes());
        }

        Map<String, List<ReferenceObjectBinding>> aoRefObjectsBindingMap = new HashMap<String, List<ReferenceObjectBinding>>();
        //Retrieve reference object bindings for activity offering's
        for(ActivityOfferingWrapper activityOfferingWrapper : activityOfferingWrapperList) {
            List<ReferenceObjectBinding> aoRefObjectBindingList = ruleManagementService.findReferenceObjectBindingsByReferenceObject(
                    CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, activityOfferingWrapper.getAoInfo().getId());
            if(aoRefObjectBindingList != null && !aoRefObjectBindingList.isEmpty()) {
                aoRefObjectsBindingMap.put(activityOfferingWrapper.getActivityCode(), aoRefObjectBindingList);
            }
        }

        StringBuilder aoRequisite = new StringBuilder();
        //Retrieve agenda's for activity offering
        for(Map.Entry<String, List<ReferenceObjectBinding>> aoEntry : aoRefObjectsBindingMap.entrySet()) {
            Map<String, String> typeRequisites = new HashMap<String, String>();
            for(ReferenceObjectBinding aoReferenceObjectBinding : aoEntry.getValue()) {
                AgendaDefinition agendaDefinition = ruleManagementService.getAgenda(aoReferenceObjectBinding.getKrmsObjectId());
                AgendaItemDefinition agendaItemDefinition = ruleManagementService.getAgendaItem(agendaDefinition.getFirstItemId());
                this.loadNaturalLanguageForRuleTypes(typeRequisites, agendaItemDefinition, catalogUsageId, reqHelper.getReqWrapper().getRuleTypes());
            }
            reqHelper.getReqWrapper().getAoRequisiteTypeMap().put(aoEntry.getKey(), typeRequisites);
        }
        reqHelper.loadRequisites(activityOfferingWrapperList);
        return reqHelper;
    }

    protected void loadNaturalLanguageForRuleTypes(Map<String, String> typeRequisites, AgendaItemDefinition agendaItem, String catalogUsageId, Set<String> ruleTypes) {

        if(agendaItem.getRuleId() != null) {
            String ruleRequisite = ruleManagementService.translateNaturalLanguageForObject(catalogUsageId, "rule", agendaItem.getRuleId(), "en");
            typeRequisites.put(agendaItem.getRule().getTypeId(), ruleRequisite);
            if(!ruleTypes.contains(agendaItem.getRule().getTypeId())) {
                ruleTypes.add(agendaItem.getRule().getTypeId());
            }
        }

        if(agendaItem.getWhenTrue() != null) {
            this.loadNaturalLanguageForRuleTypes(typeRequisites, agendaItem.getWhenTrue(), catalogUsageId, ruleTypes);
        }
    }

    public String getTermStartEndDate(TermInfo term) {
        // Return Term as String display like 'FALL 2020 (9/26/2020-12/26/2020)'
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.US);
        String displayString = "";
        if (term != null) {
            String startDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getStartDate());
            String endDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getEndDate());
            formatter.format("%s - %s", startDate, endDate);
            displayString = stringBuilder.toString();
        }
        return displayString;
    }

    /**
     * Comparators to be executed on the AOs
     *
     * @param activityComparatorChain
     */
    public void setActivityComparatorChain(KSComparatorChain activityComparatorChain) {
        this.activityComparatorChain = activityComparatorChain;
    }

    /**
     * Returns the Comparator chain associated with the Acivity sorting
     *
     * @return activityComparatorChain
     */
    public KSComparatorChain getActivityComparatorChain() {
        return activityComparatorChain;
    }

    /**
     * Sorts regGroups by the comparators in the chain.
     *
     * @param regGroupWrappers
     */
    public void sortRegGroups(List<RegistrationGroupWrapper> regGroupWrappers){
        if (regGroupComparatorChain != null){
            regGroupComparatorChain.sort(regGroupWrappers);
        }
    }

    /**
     * Comparators to be executed on the AOs
     *
     * @param regGroupComparatorChain
     */
    public void setRegGroupComparatorChain(KSComparatorChain regGroupComparatorChain) {
        this.regGroupComparatorChain = regGroupComparatorChain;
    }

    /**
     * Sorts AOs by the comparators in the chain.
     *
     * @param form
     * @param coWrapper
     */
    public void sortActivityOfferings(ScheduleOfClassesSearchForm form,CourseOfferingDisplayWrapper coWrapper){
        if (form.getAoDisplayFormat() == ScheduleOfClassesSearchForm.AoDisplayFormat.FLAT){
           KSComparatorChain defaultComparator = new KSComparatorChain();
           List<KSComparator> comparators = new ArrayList<KSComparator>(2);
           comparators.add(new ActivityOfferingTypeComparator());
           comparators.add(new ActivityOfferingCodeComparator());
           defaultComparator.setComparators(comparators);
           defaultComparator.sort(coWrapper.getActivityWrapperList());
        } else if (form.getAoDisplayFormat() == ScheduleOfClassesSearchForm.AoDisplayFormat.CLUSTER){
           /**
            * Sort the AOs first by the type and then by institutionally configured list of comparators
            */
           for (ActivityOfferingClusterWrapper clusterWrapper : form.getClusterResultList()){
               if(clusterWrapper.getAoWrapperList().size() >1){
                   //Add the type sorting as first.
                   getActivityComparatorChain().addComparator(0,new ActivityOfferingTypeComparator());
                   activityComparatorChain.sort(clusterWrapper.getAoWrapperList());
               }
           }
        }
    }


    /**
     * This method returns the institutionally configured AO states to filter at the ui. If it's not
     * configured, by default, it returns offerred state.
     *
     * @return
     */
    public List<String> getAOStateFilter(){

        String allowedAOStates = ConfigContext.getCurrentContextConfig().getProperty(CourseOfferingConstants.CONFIG_PARAM_KEY_SCHOC_AO_STATES);
        List<String> aoStates;

        if ((allowedAOStates != null) && (!allowedAOStates.isEmpty())) {

            aoStates = Arrays.asList(allowedAOStates.split("\\s*,\\s*"));

            if (!Arrays.asList(LuiServiceConstants.ACTIVITY_OFFERING_LIFECYCLE_STATE_KEYS).containsAll(aoStates)) {
                String errorMessage = String.format("Error: invalid value for configuration parameter:  %s Value: %s",
                        CourseOfferingConstants.CONFIG_PARAM_KEY_SCHOC_AO_STATES, aoStates.toString());
                throw new RuntimeException(errorMessage);
            }

        } else {
            // If an institution does not customize valid AO states, then the default is AO Offered state
            aoStates = new ArrayList<String>();
            aoStates.add(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
        }

        return aoStates;
    }


    /**
     * This method returns the institutionally configured reg group states to filter at the ui. If it's not
     * configured, by default, it returns offerred, invalid and offered-invalid states.
     *
     * @return
     */
    public List<String> getRegGroupStateFilter(){

        String allowedRegGroupStates = ConfigContext.getCurrentContextConfig().getProperty(CourseOfferingConstants.CONFIG_PARAM_KEY_SCHOC_REG_GROUP_STATES);
        List<String> regGroupStates;

        if ((allowedRegGroupStates != null) && (!allowedRegGroupStates.isEmpty())) {
            regGroupStates = Arrays.asList(allowedRegGroupStates.split("\\s*,\\s*"));
            if (!Arrays.asList(LuiServiceConstants.REGISTRATION_GROUP_LIFECYCLE_KEY_STATES).containsAll(regGroupStates)) {
                String errorMessage = String.format("Error: invalid value for configuration parameter:  %s Value: %s",
                        CourseOfferingConstants.CONFIG_PARAM_KEY_SCHOC_REG_GROUP_STATES, regGroupStates.toString());
                throw new RuntimeException(errorMessage);
            }
        } else {
            /*
            If an institution does not customize valid RegGroup states, then the default is RegGroup Offered+Invalid+Offered-invalid state.
            Offered-invalid not yet available. So, we ignored for now but eventually that will be added.
             */
            regGroupStates = new ArrayList<String>(2);
            regGroupStates.add(LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY);
            regGroupStates.add(LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY);
        }

        return regGroupStates;
    }

    private OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }

    public PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
    }

    public TypeService getTypeService() {
        if (typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }
}