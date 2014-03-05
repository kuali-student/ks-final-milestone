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
package org.kuali.student.enrollment.class2.scheduleofclasses.controller;

/**
 * This class provides a controller for the Schedule of Classes ui
 *
 * @author Kuali Student Team
 */

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.CourseOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.SOCRequisiteWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/scheduleOfClassesSearch")
public class ScheduleOfClassesSearchController extends UifControllerBase {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ScheduleOfClassesSearchController.class);

    private static final String MODEL_ATTRIBUTE_FORM = "KualiForm";
    private static final String SEARCH_TYPE_COURSE = "course";
    private static final String SEARCH_FIELD = "course";
    private static final String ERROR_SEARCH_FIELD_CANNOT_BE_EMPTY = "Error: search field can't be empty";

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new ScheduleOfClassesSearchForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start( @ModelAttribute( MODEL_ATTRIBUTE_FORM ) UifFormBase form,
                               BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        ScheduleOfClassesSearchForm scheduleOfClassesSearchForm = (ScheduleOfClassesSearchForm) form;
        scheduleOfClassesSearchForm.setSearchType( SEARCH_TYPE_COURSE );

        configureCurrentTermCodeOnInitialRequest( scheduleOfClassesSearchForm );
        //This scenario will be very rare if ever (no published term in db)
        if (GlobalVariables.getMessageMap().hasErrors() && GlobalVariables.getMessageMap().containsMessageKey(ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_PUBLISHED_TERM)) {
            return getUIFModelAndView(scheduleOfClassesSearchForm, ScheduleOfClassesConstants.SOC_RESULT_PAGE);
        }
        configureDefaultAoDisplayFormat( scheduleOfClassesSearchForm );
        configureSelectableAoRenderingWidget( scheduleOfClassesSearchForm, request );

        return super.start(form, result, request, response);
    }

    private void configureCurrentTermCodeOnInitialRequest( ScheduleOfClassesSearchForm form ) {
        if (form.getTermCode() == null) {
            ContextInfo context = ContextUtils.getContextInfo();
            List<AtpInfo> atps = ScheduleOfClassesUtil.getValidSocTerms(ScheduleOfClassesUtil.getCourseOfferingSetService(), ScheduleOfClassesUtil.getAtpService(), context);
            if (atps != null && atps.size() > 0 && !atps.isEmpty()) {
                form.setTermCode(ScheduleOfClassesUtil.getClosestAtp(atps).getId());
            } else {
                GlobalVariables.getMessageMap().putError("termCode", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_PUBLISHED_TERM);
            }
        }
    }

    private void configureDefaultAoDisplayFormat( ScheduleOfClassesSearchForm form ) {
        String aoDisplayFormat = ConfigContext.getCurrentContextConfig().getProperty(ScheduleOfClassesConstants.ConfigProperties.AO_DISPLAY_FORMAT);
        if (StringUtils.isNotBlank(aoDisplayFormat)){
            ScheduleOfClassesSearchForm.AoDisplayFormat format = ScheduleOfClassesSearchForm.AoDisplayFormat.valueOf(StringUtils.upperCase(aoDisplayFormat));
            form.setAoDisplayFormat(format);
        }
    }

    private void configureSelectableAoRenderingWidget( ScheduleOfClassesSearchForm form, HttpServletRequest request ) {

        boolean displaySelectableAoRenderingWidget = false;

        // URL-parameter overrides setting in kuali config file
        if( request.getParameter( "showAoDisplayWidget" ) != null ) {
            displaySelectableAoRenderingWidget = Boolean.valueOf( request.getParameter( "showAoDisplayWidget" ) );
        } else if(request.getParameter("scheduleOfClassesDisplayFormat") != null) {
            String urlSelectedRendering = request.getParameter( "scheduleOfClassesDisplayFormat" );
            if(ScheduleOfClassesSearchForm.AoDisplayFormat.REG_GROUP.getText().equals(urlSelectedRendering)) {
                form.setAoDisplayFormat(ScheduleOfClassesSearchForm.AoDisplayFormat.REG_GROUP);
            } else if(ScheduleOfClassesSearchForm.AoDisplayFormat.CLUSTER.getText().equals(urlSelectedRendering)) {
                form.setAoDisplayFormat(ScheduleOfClassesSearchForm.AoDisplayFormat.CLUSTER);
            } else {
                form.setAoDisplayFormat(ScheduleOfClassesSearchForm.AoDisplayFormat.FLAT);
            }
        }
        // Setting in kuali config file is applied if URL-parameter was not supplied
        // (and in event kuali config file does not specify, then UI widget will be set to not show)
        else {
            String allowSelectableAoRendering = ConfigContext.getCurrentContextConfig().getProperty( ScheduleOfClassesConstants.ConfigProperties.ALLOW_SELECTABLE_AO_RENDERING );
            displaySelectableAoRenderingWidget = Boolean.valueOf( allowSelectableAoRendering );
        }

        form.setAllowSelectableAoRendering(displaySelectableAoRenderingWidget);
    }

    /**
     * Method used to
     * Search for course offerings based on search parameters: term and courseCode/Title&Desc/Instructor/Department
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute( MODEL_ATTRIBUTE_FORM ) ScheduleOfClassesSearchForm theForm)
            throws Exception, DoesNotExistException, PermissionDeniedException, OperationFailedException
    {

        theForm.getCoDisplayWrapperList().clear();

        //First, find termName based on termCode
        String termCode = theForm.getTermCode();
        if (StringUtils.isNotBlank(termCode)) {
            String termName = ScheduleOfClassesUtil.getAcademicCalendarService().getTerm(termCode, ContextUtils.createDefaultContextInfo()).getName();
            theForm.setTermName(termName);
        } else {
            LOG.error("Error: term can't be empty");
            GlobalVariables.getMessageMap().putError("termCode", ScheduleOfClassesConstants.SOC_MSG_ERROR_TERM_IS_EMPTY);
            return getUIFModelAndView(theForm);
        }

        // Second, handle searchType
        if (theForm.getSearchType().equals( SEARCH_TYPE_COURSE )) {
            String course = StringUtils.upperCase(theForm.getCourse());
            if (StringUtils.isNotBlank(course)) {
                ScheduleOfClassesUtil.getViewHelperService(theForm).loadCourseOfferingsByTermAndCourseCode(termCode, course, theForm);
                theForm.setSearchParameter("Course: " + course);
            } else {
                LOG.error( ERROR_SEARCH_FIELD_CANNOT_BE_EMPTY );
                GlobalVariables.getMessageMap().putError( SEARCH_FIELD, ScheduleOfClassesConstants.SOC_MSG_ERROR_COURSE_IS_EMPTY);
                return getUIFModelAndView(theForm);
            }
        } else if (theForm.getSearchType().equals("instructor")) {
            String instructorId = theForm.getInstructor();
            String instructorName = theForm.getInstructorName();
            if ((StringUtils.isNotBlank(instructorId)) || (StringUtils.isNotBlank(instructorName))) {
                ScheduleOfClassesUtil.getViewHelperService(theForm).loadCourseOfferingsByTermAndInstructor(termCode, instructorId, instructorName, theForm);
                theForm.setSearchParameter("Instructor: " + instructorName);
            } else {
                LOG.error( ERROR_SEARCH_FIELD_CANNOT_BE_EMPTY );
                GlobalVariables.getMessageMap().putError( SEARCH_FIELD, ScheduleOfClassesConstants.SOC_MSG_ERROR_COURSE_IS_EMPTY);
                return getUIFModelAndView(theForm);
            }
        } else if (theForm.getSearchType().equals("department")) {
            String departmentId = theForm.getDepartment();
            String departmentName = theForm.getDepartmentName();
            if ((StringUtils.isNotBlank(departmentId)) || (StringUtils.isNotBlank(departmentName))) {
                ScheduleOfClassesUtil.getViewHelperService(theForm).loadCourseOfferingsByTermAndDepartment(termCode, departmentId, departmentName, theForm);
                theForm.setSearchParameter("Department: " + departmentName);
            } else {
                LOG.error( ERROR_SEARCH_FIELD_CANNOT_BE_EMPTY );
                GlobalVariables.getMessageMap().putError( SEARCH_FIELD, ScheduleOfClassesConstants.SOC_MSG_ERROR_COURSE_IS_EMPTY);
                return getUIFModelAndView(theForm);
            }
        } else if (theForm.getSearchType().equals("titleDesc")) {
            String titleDesc = theForm.getTitleDesc();
            if (StringUtils.isNotBlank(titleDesc)) {
                ScheduleOfClassesUtil.getViewHelperService(theForm).loadCourseOfferingsByTitleAndDescription(termCode, titleDesc, theForm);
                theForm.setSearchParameter("Title & Description: " + titleDesc);
            } else {
                LOG.error(ERROR_SEARCH_FIELD_CANNOT_BE_EMPTY );
                GlobalVariables.getMessageMap().putError( SEARCH_FIELD, ScheduleOfClassesConstants.SOC_MSG_ERROR_COURSE_IS_EMPTY);
                return getUIFModelAndView(theForm);
            }
        }

        return getUIFModelAndView(theForm, ScheduleOfClassesConstants.SOC_RESULT_PAGE);
    }

    /**
     *
     * Populates the AOs and clusters when the user clicks on the disclosure at the CO display.
     *
     * @param theForm
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=populateAOs")
    public ModelAndView populateAOs(@ModelAttribute( MODEL_ATTRIBUTE_FORM ) ScheduleOfClassesSearchForm theForm) throws Exception {

        CourseOfferingDisplayWrapper coDisplayWrapper = (CourseOfferingDisplayWrapper)KSControllerHelper.getSelectedCollectionItem(theForm);
        theForm.setCourseOfferingId(coDisplayWrapper.getCourseOfferingId());

        SearchRequestInfo searchRequestInfo = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY);

        List<String> aoStates = ScheduleOfClassesUtil.getViewHelperService(theForm).getAOStateFilter();
        searchRequestInfo.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_STATES, aoStates);

        ScheduleOfClassesUtil.getViewHelperService(theForm).build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(theForm, searchRequestInfo, false);

        coDisplayWrapper.getClusterResultList().clear();
        coDisplayWrapper.getClusterResultList().addAll(theForm.getClusterResultList());
        coDisplayWrapper.getActivityWrapperList().clear();
        coDisplayWrapper.getActivityWrapperList().addAll(theForm.getActivityWrapperList());

        SOCRequisiteWrapper requisites =  ScheduleOfClassesUtil.getViewHelperService(theForm).retrieveRequisites(coDisplayWrapper.getCourseOfferingId(), coDisplayWrapper.getActivityWrapperList());
        coDisplayWrapper.setRequisites(requisites.getCoRequisite().toString());

        ScheduleOfClassesUtil.getViewHelperService(theForm).sortActivityOfferings(theForm,coDisplayWrapper);

        Map<String, String> subTermInfoMap = new HashMap<String, String>();

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        for (ActivityOfferingWrapper aoWrapper : coDisplayWrapper.getActivityWrapperList()){
            //Adding Requisites
            if(requisites.getAoRequisiteMap().containsKey(aoWrapper.getId())) {
                aoWrapper.setRequisite(requisites.getRequisiteForAO(aoWrapper.getId()));
            }

            // Adding Information (icons)
            StringBuilder information = new StringBuilder();
            if (aoWrapper.getAoInfo().getIsHonorsOffering() != null && aoWrapper.getAoInfo().getIsHonorsOffering()) {
                information.append("<img src=").append(ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG).append(" title=\"").append(ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_ACTIVITY).append("\"> ");
            }

            // Adding subterm
            String termId = aoWrapper.getAoInfo().getTermId();
            String subTermDisplay = "";
            if (!theForm.getTermCode().equals(termId)) {
                if (!subTermInfoMap.containsKey(termId)) {
                    TermInfo subTerm = ScheduleOfClassesUtil.getAcademicCalendarService().getTerm(termId, contextInfo);
                    // check if term or subterm
                    List<TypeTypeRelationInfo> terms = ScheduleOfClassesUtil.getTypeService().getTypeTypeRelationsByRelatedTypeAndType(subTerm.getTypeKey(), TypeServiceConstants.TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY, contextInfo);
                    // if subterm
                    if (!terms.isEmpty()) {
                        TypeInfo subTermType = ScheduleOfClassesUtil.getTypeService().getType(subTerm.getTypeKey(), contextInfo);
                        subTermDisplay = "This activity is in " + subTermType.getName() + " - " + ScheduleOfClassesUtil.getViewHelperService(theForm).getTermStartEndDate(subTerm);
                        subTermInfoMap.put(termId, subTermDisplay);
                        // displaying information
                        information.append("<img src=").append(ScheduleOfClassesConstants.SOC_RESULT_PAGE_SUBTERM_IMG).append(" title=\"").append(subTermDisplay).append("\"> ");
                    }
                } else {
                    subTermDisplay = subTermInfoMap.get(termId);
                    information.append("<img src=").append(ScheduleOfClassesConstants.SOC_RESULT_PAGE_SUBTERM_IMG).append(" title=\"").append(subTermDisplay).append("\"> ");
                }
            }

            aoWrapper.setSchOfClassesRenderHelper(aoWrapper.new SchOfClassesRenderHelper());
            aoWrapper.getSchOfClassesRenderHelper().setInformation(information.toString());
        }


        return getUIFModelAndView(theForm, ScheduleOfClassesConstants.SOC_RESULT_PAGE);
    }

    /**
     * Populates the reg group when the user clicks on the disclosure at the CO display.
     *
     * @param theForm
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=populateRegGroups")
    public ModelAndView populateRegGroups(@ModelAttribute( MODEL_ATTRIBUTE_FORM ) ScheduleOfClassesSearchForm theForm) throws Exception {

        SearchRequestInfo searchRequestInfo = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY);

        CourseOfferingDisplayWrapper coDisplayWrapper = (CourseOfferingDisplayWrapper)KSControllerHelper.getSelectedCollectionItem(theForm);
        theForm.setCourseOfferingId(coDisplayWrapper.getCourseOfferingId());

        List<String> regGroupStates = ScheduleOfClassesUtil.getViewHelperService(theForm).getRegGroupStateFilter();
        searchRequestInfo.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.REGGROUP_STATES, regGroupStates);

        ScheduleOfClassesUtil.getViewHelperService(theForm).build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(theForm,searchRequestInfo, true);

        coDisplayWrapper.getClusterResultList().clear();
        coDisplayWrapper.getClusterResultList().addAll(theForm.getClusterResultList());

        /**
         * Sort the RegGroups first by the ID and then by institutionally configured list of comparators
         */
        for (ActivityOfferingClusterWrapper clusterWrapper : coDisplayWrapper.getClusterResultList()){
            Map<RegistrationGroupWrapper, List<RegistrationGroupWrapper>> regGroupMap = new HashMap<RegistrationGroupWrapper, List<RegistrationGroupWrapper>>();
            List<RegistrationGroupWrapper> sortRegGroupWrappers = new ArrayList<RegistrationGroupWrapper>();

            for (int i = 0; i < clusterWrapper.getRgWrapperList().size(); i++) {
                RegistrationGroupWrapper registrationGroupWrapper = clusterWrapper.getRgWrapperList().get(i);
                for (RegistrationGroupWrapper partnerRegistrationGroupWrappers : clusterWrapper.getRgWrapperList()) {
                    if (registrationGroupWrapper.getRgInfo().getId().equals(partnerRegistrationGroupWrappers.getRgInfo().getId())) {
                        if (regGroupMap.containsKey(registrationGroupWrapper)) {
                            regGroupMap.get(registrationGroupWrapper).add(partnerRegistrationGroupWrappers);
                            i++;
                        } else {
                            regGroupMap.put(registrationGroupWrapper, new ArrayList<RegistrationGroupWrapper>());
                            regGroupMap.get(registrationGroupWrapper).add(registrationGroupWrapper);
                        }
                    }
                }
            }

            sortRegGroupWrappers.addAll(regGroupMap.keySet());

            if(clusterWrapper.getRgWrapperList().size() > 1){
                //Sort Reg Groups by Reg Group name (which is not institutionally configurable)
               // Collections.sort(clusterWrapper.getRgWrapperList(),new RegGroupNameComparator());
                //Sort by whatever configured at the xml (which are institutionally configurable)
                ScheduleOfClassesUtil.getViewHelperService(theForm).sortRegGroups(sortRegGroupWrappers);
            }

            clusterWrapper.getRgWrapperList().clear();

            for (RegistrationGroupWrapper registrationGroupWrapper : sortRegGroupWrappers) {
                clusterWrapper.getRgWrapperList().addAll(regGroupMap.get(registrationGroupWrapper));
            }
        }


        SOCRequisiteWrapper requisites =  ScheduleOfClassesUtil.getViewHelperService(theForm).retrieveRequisites(coDisplayWrapper.getCourseOfferingId(), theForm.getActivityWrapperList());
        coDisplayWrapper.setRequisites(requisites.getCoRequisite().toString());
        for(ActivityOfferingClusterWrapper activityOfferingClusterWrapper : theForm.getClusterResultList()) {
            ScheduleOfClassesUtil.loadRegRequisites(requisites, activityOfferingClusterWrapper.getRgWrapperList());
        }

        return getUIFModelAndView(theForm, ScheduleOfClassesConstants.SOC_RESULT_PAGE);
    }
}
