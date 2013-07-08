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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeEntryDefinitionContract;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeRuleEntry;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.ActivityOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.CourseOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.service.ScheduleOfClassesViewHelperService;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.KeyNameInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class performs queries for scheduling of classes
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesViewHelperServiceImpl extends ViewHelperServiceImpl implements ScheduleOfClassesViewHelperService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ScheduleOfClassesViewHelperServiceImpl.class);

    private CourseOfferingService coService;
    private LprService lprService;
    private OrganizationService organizationService;
    private AcademicCalendarService academicCalendarService;
    private TypeService typeService;
    private RuleManagementService ruleManagementService;

    public void loadCourseOfferingsByTermAndCourseCode(String termId, String courseCode, ScheduleOfClassesSearchForm form) throws Exception{

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        // Building a query
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like("courseOfferingCode", courseCode + "%"),
                PredicateFactory.equalIgnoreCase("atpId", termId)),
                PredicateFactory.equal("luiState", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

        //Retrieve requisites for courseOfferings
        Map<String, String> requisites = retrieveRequisites(courseOfferingIds);

        if(courseOfferingIds.size() > 0){
            form.getCoDisplayWrapperList().clear();
            form.setCoDisplayWrapperList(getCourseOfferingDisplayWrappersByIds(courseOfferingIds,getCourseOfferingService(),requisites, contextInfo));
        } else {
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError("Term & courseCode", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "courseCode", courseCode, termId);
            form.getCoDisplayWrapperList().clear();
        }
    }

    @Override
    public void loadCourseOfferingsByTermAndInstructor(String termId, String instructorId, String instructorName, ScheduleOfClassesSearchForm form) throws Exception {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        // Search ID based on organizationName
        if (instructorId == null || instructorId.isEmpty()) {
            Map<String, String> searchCriteria = new HashMap<String, String>();
            searchCriteria.put(KIMPropertyConstants.Person.PRINCIPAL_NAME, instructorName);
            List<Person> instructors = getPersonService().findPeople(searchCriteria);
            if (instructors.isEmpty()) {
                LOG.error("Error: Can't find any instructor for selected instructor in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "instructor", instructorName, termId);
                form.getCoDisplayWrapperList().clear();
            } else if (instructors.size() > 1) {
                LOG.error("Error: There is more than one instructor with the same name in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_MULTIPLE_INSTRUCTOR_IS_FOUND, instructorName);
                instructorId = null;
                form.getCoDisplayWrapperList().clear();
            } else {
                instructorId = instructors.get(0).getPrincipalId();
            }
        }

        if (instructorId != null) {
            //this is a cross service search between LPR and LUI, so it is inefficient (no join)
            //First get all the luiIds that the instructor is teaching
            //Only get active courses
            List<String> luiIds = getLprService().getLuiIdsByPersonAndTypeAndState(instructorId, LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, LprServiceConstants.ACTIVE_STATE_KEY, contextInfo);

            List<String> courseOfferingIds = null;

            if(luiIds != null && !luiIds.isEmpty()){
                //Now find all the COs with Aos that are attached to that instructor.
                // Build a query
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.in("aoid", luiIds.toArray()),
                    PredicateFactory.equalIgnoreCase("atpId", termId)),
                    PredicateFactory.equal("luiState", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY));
                QueryByCriteria criteria = qbcBuilder.build();
                courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

                //Retrieve requisites for courseOfferings
                Map<String, String> requisites = retrieveRequisites(courseOfferingIds);

                if(courseOfferingIds.size() > 0){
                    form.getCoDisplayWrapperList().clear();
                    form.setCoDisplayWrapperList(getCourseOfferingDisplayWrappersByIds(courseOfferingIds,getCourseOfferingService(),requisites,contextInfo));
                }
            }

            //If nothing was found then error
            if(courseOfferingIds == null || courseOfferingIds.isEmpty()) {
                LOG.error("Error: Can't find any Course Offering for selected Instructor in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "instructor", instructorId, termId);
                form.getCoDisplayWrapperList().clear();
            }
        }
    }

    public void loadCourseOfferingsByTermAndDepartment(String termId, String organizationId, String organizationName, ScheduleOfClassesSearchForm form) throws Exception{
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        // Search ID based on organizationName
        if (organizationId == null || organizationId.isEmpty()) {
            QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
            qBuilder.setPredicates(PredicateFactory.equalIgnoreCase("longName", organizationName));
            QueryByCriteria query = qBuilder.build();
            OrganizationService organizationService = getOrganizationService();
            List<String> orgIDs = organizationService.searchForOrgIds(query, ContextUtils.createDefaultContextInfo());
            if (orgIDs.isEmpty()) {
                LOG.error("Error: Can't find any Department for selected Department in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "department", organizationName, termId);
                form.getCoDisplayWrapperList().clear();
            } else if (orgIDs.size() > 1) {
                LOG.error("Error: There is more than one departments with the same long name in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_MULTIPLE_DEPARTMENT_IS_FOUND, organizationName);
                form.getCoDisplayWrapperList().clear();
            }
        } else {
            qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("luiContentOwner", organizationId),
                PredicateFactory.equal("atpId", termId),
                PredicateFactory.equal("luiType", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY),
                PredicateFactory.equal("luiState", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)));
            QueryByCriteria criteria = qbcBuilder.build();
            List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

            //Retrieve requisites for courseOfferings
            Map<String, String> requisites = retrieveRequisites(courseOfferingIds);

            if(courseOfferingIds.size() > 0){
                form.getCoDisplayWrapperList().clear();
                form.setCoDisplayWrapperList(getCourseOfferingDisplayWrappersByIds(courseOfferingIds,getCourseOfferingService(), requisites, contextInfo));
            } else {            //If nothing was found then error
                LOG.error("Error: Can't find any Course Offering for selected Department in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "department", organizationName, termId);
                form.getCoDisplayWrapperList().clear();
            }
        }
    }

    public void loadActivityOfferingsByCourseOfferingId(String courseOfferingId, ScheduleOfClassesSearchForm form) throws Exception {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        List<ActivityOfferingDisplayWrapper> aoDisplayWrapperList = new ArrayList<ActivityOfferingDisplayWrapper>();
        List<ActivityOfferingDisplayInfo> aoDisplayInfoList = getCourseOfferingService().getActivityOfferingDisplaysForCourseOffering(courseOfferingId, contextInfo);

        Map<String, String> subTermInfoMap = new HashMap<String, String>();

        for (ActivityOfferingDisplayInfo aoDisplayInfo : aoDisplayInfoList) {
            //Only returned offered AOS
            if(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY.equals(aoDisplayInfo.getStateKey())){
                ActivityOfferingDisplayWrapper aoDisplayWrapper = new ActivityOfferingDisplayWrapper();
                aoDisplayWrapper.setAoDisplayInfo(aoDisplayInfo);

                // Adding Information (icons)
                String information = "";
                if (aoDisplayInfo.getIsHonorsOffering() != null && aoDisplayInfo.getIsHonorsOffering()) {
                    information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_ACTIVITY + "\"> ";
                }

                // Adding subterm
                String termId = aoDisplayInfo.getTermId();
                String subTermDisplay = "";
                if(!form.getTermCode().equals(termId)) {
                    if (subTermInfoMap.isEmpty() || subTermInfoMap.get(termId) == null) {
                        TermInfo subTerm = getAcademicCalendarService().getTerm(termId, contextInfo);
                        // check if term or subterm
                        List<TypeTypeRelationInfo> terms = getTypeService().getTypeTypeRelationsByRelatedTypeAndType(subTerm.getTypeKey(), TypeServiceConstants.TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY, contextInfo);
                        // if subterm
                        if (!terms.isEmpty()) {
                            TypeInfo subTermType = getTypeService().getType(subTerm.getTypeKey(), contextInfo);
                            subTermDisplay = "This activity is in " + subTermType.getName() + " - " + getTermStartEndDate(subTerm);
                            subTermInfoMap.put(termId, subTermDisplay);
                            // displaying information
                           information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_SUBTERM_IMG + " title=\"" + subTermDisplay + "\"> ";
                        }
                    } else {
                        subTermDisplay = subTermInfoMap.get(termId);
                        information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_SUBTERM_IMG + " title=\"" + subTermDisplay + "\"> ";
                    }
                }

                aoDisplayWrapper.setInformation(information);

                if(aoDisplayInfo.getScheduleDisplay()!=null && !aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays().isEmpty()){
                    //TODO handle TBA state
                    List<? extends ScheduleComponentDisplay> scheduleComponentDisplays = aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays();
                    for (ScheduleComponentDisplay scheduleComponentDisplay : scheduleComponentDisplays) {
                        if(scheduleComponentDisplay.getBuilding() != null){
                            aoDisplayWrapper.setBuildingName(scheduleComponentDisplay.getBuilding().getBuildingCode(), true);
                        }
                        if(scheduleComponentDisplay.getRoom() != null){
                            aoDisplayWrapper.setRoomName(scheduleComponentDisplay.getRoom().getRoomCode(), true);
                        }
                        if(!scheduleComponentDisplay.getTimeSlots().isEmpty()){
                            if(scheduleComponentDisplay.getTimeSlots().get(0).getStartTime() != null){
                                aoDisplayWrapper.setStartTimeDisplay(millisToTime(scheduleComponentDisplay.getTimeSlots().get(0).getStartTime().getMilliSeconds()), true);
                            }
                            if(scheduleComponentDisplay.getTimeSlots().get(0).getEndTime() != null){
                                aoDisplayWrapper.setEndTimeDisplay(millisToTime(scheduleComponentDisplay.getTimeSlots().get(0).getEndTime().getMilliSeconds()), true);
                            }
                            aoDisplayWrapper.setDaysDisplayName(getDays(scheduleComponentDisplay.getTimeSlots().get(0).getWeekdays()), true);
                        }
                    }

                }

                //  Set the instructor name
                aoDisplayWrapper.setInstructorDisplayNames(aoDisplayInfo.getInstructorName(), true);

                aoDisplayWrapperList.add(aoDisplayWrapper);
            }
        }

        form.setAoDisplayWrapperList(aoDisplayWrapperList);
    }

    @Override
    public void loadCourseOfferingsByTitleAndDescription(String termId, String titleOrDescription, ScheduleOfClassesSearchForm form) throws Exception {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        // Note: the longName is not in the luiEntity so we need to use the criteriaLookupService is used.
        // it is linked back to CourseOfferingCriteriaTransform and wired in the ks-enroll-context.xml
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("atpId", termId),
                PredicateFactory.equal("luiType", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY),
                PredicateFactory.equal("luiState", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY),
                PredicateFactory.and(
                        PredicateFactory.or(
                           PredicateFactory.like("plain", "%" + titleOrDescription + "%"), // this is for the description
                           PredicateFactory.like("longName", titleOrDescription + "%")     // this is for the title
                        )
                )

        ));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

        //Retrieve requisites for courseOfferings
        Map<String, String> requisites = retrieveRequisites(courseOfferingIds);

        if(courseOfferingIds.size() > 0){
            form.getCoDisplayWrapperList().clear();
            form.setCoDisplayWrapperList(getCourseOfferingDisplayWrappersByIds(courseOfferingIds,getCourseOfferingService(), requisites, contextInfo));
        } else {    //If nothing was found then error
            LOG.error("Error: Can't find any Course Offering for selected Department in term: " + termId);
            GlobalVariables.getMessageMap().putError("Title & Description", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "title or description", titleOrDescription, termId);
            form.getCoDisplayWrapperList().clear();
        }

    }

    protected static List<CourseOfferingDisplayWrapper> getCourseOfferingDisplayWrappersByIds(List<String> courseOfferingIds, CourseOfferingService courseOfferingService, Map<String, String> requisites, ContextInfo contextInfo) throws Exception{
        List<CourseOfferingDisplayWrapper> coDisplayWrapperList = new ArrayList<CourseOfferingDisplayWrapper>();

        if(courseOfferingIds.size() > 0){

            List<CourseOfferingDisplayInfo> coDisplayInfoList = courseOfferingService.getCourseOfferingDisplaysByIds(courseOfferingIds, contextInfo);

            for (CourseOfferingDisplayInfo coDisplayInfo : coDisplayInfoList) {
                CourseOfferingDisplayWrapper coDisplayWrapper = new CourseOfferingDisplayWrapper();
                coDisplayWrapper.setCoDisplayInfo(coDisplayInfo);

                coDisplayWrapper.setRequisites(requisites.get(coDisplayInfo.getId()));

                // Adding Information (icons)
                String information = "";
                if (coDisplayInfo.getIsHonorsOffering() != null && coDisplayInfo.getIsHonorsOffering()) {
                    information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_COURSE + "\"> ";
                }
                if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().getKey() != null
                        && coDisplayInfo.getGradingOption().getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY)) {
                    information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_SATISFACTORY_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_SATISFACTORY + "\"> ";
                } else if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().getKey() != null
                        && coDisplayInfo.getGradingOption().getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE)) {
                    information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_PERCENT_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_PERCENT + "\"> ";
                }
                if (!coDisplayInfo.getStudentRegistrationGradingOptions().isEmpty()) {
                    for (KeyNameInfo stuRegOption : coDisplayInfo.getStudentRegistrationGradingOptions()) {
                        if (stuRegOption.getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                            information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_PASSFAIL_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_PASSFAIL + "\">";
                        } else if (stuRegOption.getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                            //FindBugs - it is fine as is
                            information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_AUDIT_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_AUDIT + "\">";
                        }
                    }
                }
                coDisplayWrapper.setInformation(information);

                coDisplayWrapperList.add(coDisplayWrapper);
            }

        }

        return coDisplayWrapperList;
    }

    /**
     * Method to build the course offering requisites string for display
     *
     * @param courseOfferingIds
     * @return Map of course offering requisites
     */
    private Map<String, String> retrieveRequisites(List<String> courseOfferingIds) {
        Map<String, String> requisites = new HashMap<String, String>();

        for(String courseOfferingId : courseOfferingIds) {
            List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();
            //Retrieve reference object bindings for course offering
            List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(KSKRMSServiceConstants.RULE_DISCR_TYPE_COURSE_OFFERING, courseOfferingId);

            //If reference object bindings found, build requisite string, else set requisite string to null so that it does not display
            if(refObjectsBindings != null) {
                //Retrieve agenda's for course offering
                for(ReferenceObjectBinding referenceObjectBinding : refObjectsBindings){
                    AgendaDefinition agenda = this.getRuleManagementService().getAgenda(referenceObjectBinding.getKrmsObjectId());
                    agendas.add(new AgendaEditor(agenda));
                }

                List<RuleDefinition> rules = null;
                String requisiteString = StringUtils.EMPTY;
                //For each agenda retrieve rules
                for(AgendaEditor agenda : agendas) {
                    AgendaTreeDefinition agendaTree = this.getRuleManagementService().getAgendaTree(agenda.getId());
                    rules = getRuleEditorsFromTree(agendaTree.getEntries());
                    //For each rule build requisite string
                    for(RuleDefinition rule : rules) {
                        String descriptionUsageId = this.getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KSKRMSServiceConstants.KRMS_NL_TYPE_DESCRIPTION, PermissionServiceConstants.KS_SYS_NAMESPACE).getId();
                        String ruleTypeUsageId = this.getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KSKRMSServiceConstants.KRMS_NL_RULE_EDIT, PermissionServiceConstants.KS_SYS_NAMESPACE).getId();

                        String ruleDescription = getDescriptionForTypeAndUsage(rule.getTypeId(), descriptionUsageId);
                        String ruleRequisite = this.getRuleManagementService().translateNaturalLanguageForProposition(ruleTypeUsageId, rule.getProposition(), KSKRMSServiceConstants.LANGUAGE_CODE_ENGLISH);

                        requisiteString += ruleDescription + ": " + ruleRequisite + ".<br>";
                    }
                }
                //Add rule requisites to map and bind to course offering id
                requisites.put(courseOfferingId, requisiteString);
            } else {
                requisites.put(courseOfferingId, null);
            }
        }
        //Return map of requisites
        return requisites;
    }

    /**
     * Retrieve rules from agenda tree.
     *
     * @param agendaTreeEntries
     * @return  List of existing rules
     */
    private List<RuleDefinition> getRuleEditorsFromTree(List<AgendaTreeEntryDefinitionContract> agendaTreeEntries) {

        List<RuleDefinition> rules = new ArrayList<RuleDefinition>();
        for (AgendaTreeEntryDefinitionContract treeEntry : agendaTreeEntries) {
            if (treeEntry instanceof AgendaTreeRuleEntry) {

                AgendaItemDefinition agendaItem = this.getRuleManagementService().getAgendaItem(treeEntry.getAgendaItemId());
                if (agendaItem.getRule() != null) {
                    RuleDefinition ruleDefinition = agendaItem.getRule();
                    rules.add(ruleDefinition);
                }
            }
        }
        return rules;
    }

    /**
     * Method to retrieve discription of rule type
     *
     * @param typeId
     * @param usageId
     * @return String of rule description
     */
    private String getDescriptionForTypeAndUsage(String typeId, String usageId) {
        NaturalLanguageTemplate template = null;
        try {
            template = getRuleManagementService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en", typeId, usageId);
            return template.getTemplate();
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    private String millisToTime(Long milliseconds){
        if(milliseconds == null){
            return null;
        }
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        return DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(cal.getTime());

    }


    private CourseOfferingService getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    private LprService getLprService() {
        if (lprService == null) {
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE,
                    LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }


    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }

    public PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
    }

    private AcademicCalendarService getAcademicCalendarService() {
        if (academicCalendarService == null) {
            academicCalendarService = CourseOfferingResourceLoader.loadAcademicCalendarService();
        }

        return academicCalendarService;
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

    private String convertIntoDaysDisplay(int day) {
        String dayOfWeek;
        switch (day) {
            case 1:
                dayOfWeek = SchedulingServiceConstants.SUNDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 2:
                dayOfWeek = SchedulingServiceConstants.MONDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 3:
                dayOfWeek = SchedulingServiceConstants.TUESDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 4:
                dayOfWeek = SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 5:
                dayOfWeek = SchedulingServiceConstants.THURSDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 6:
                dayOfWeek = SchedulingServiceConstants.FRIDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 7:
                dayOfWeek = SchedulingServiceConstants.SATURDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            default:
                dayOfWeek = StringUtils.EMPTY;
        }
        // TODO implement TBA when service stores it.
        return dayOfWeek;
    }

    private String getDays(List<Integer> intList) {

        StringBuilder sb = new StringBuilder();
        if(intList == null){
            return sb.toString();
        }

        for(Integer d : intList) {
            sb.append(convertIntoDaysDisplay(d));
        }

        return sb.toString();
    }

    private String getTermStartEndDate(TermInfo term) {
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
}