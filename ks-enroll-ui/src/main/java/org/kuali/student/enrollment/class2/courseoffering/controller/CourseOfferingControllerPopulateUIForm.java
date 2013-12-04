/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 8/20/13
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OrganizationInfoWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.DefaultOptionKeysService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.DefaultOptionKeysServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.coursewaitlist.service.facade.CourseWaitListServiceFacade;
import org.kuali.student.enrollment.class2.coursewaitlist.service.facade.CourseWaitListServiceFacadeConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.search.CourseOfferingHistorySearchImpl;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This class provides business logic related to the CourseOfferingController
 *
 * @author Kuali Student Team
 */
public class CourseOfferingControllerPopulateUIForm {
    private final static Logger LOG = Logger.getLogger(CourseOfferingControllerPopulateUIForm.class);

    protected static void populateCreateCourseOfferingForm(MaintenanceDocumentForm form, HttpServletRequest request) {
        int firstValue = 0;

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            MaintenanceDocument document = form.getDocument();
            String termId = request.getParameter(CourseOfferingConstants.TARGET_TERM_ID);
            CourseInfo courseInfo = CourseOfferingManagementUtil.getCourseService().getCourse(request.getParameter(CourseOfferingConstants.COURSE_ID), contextInfo);

            //1. set CourseOfferingInfo - no saved Course Offering as of yet (see logic in CourseOfferingEditMaintainableImpl.retrieveObjectForEditOrCopy
            CourseOfferingInfo coInfo = CourseOfferingManagementUtil.createCourseOfferingInfo(termId, courseInfo);
            // set the default value for hasWaitlist based on info defined in ks-enroll-config.xml through
            // CourseWaitListServiceFacade
            coInfo.setHasWaitlist(getCourseWaitListServiceFacade().getHasWaitlist());

            CourseOfferingEditWrapper formObject = new CourseOfferingEditWrapper(coInfo);
            formObject.setCreateCO(true);

            //2. set CourseInfo
            formObject.setCourse(courseInfo);

            //3. set formatOfferingList
            formObject.setFormatOfferingList(new ArrayList<FormatOfferingWrapper>());
            FormatOfferingWrapper defaultFO = new FormatOfferingWrapper();
            defaultFO.setFormatId(courseInfo.getFormats().get(firstValue).getId());
            defaultFO.getRenderHelper().setNewRow(true);
            defaultFO.setCourseOfferingWrapper(formObject);
            formObject.getFormatOfferingList().add(defaultFO);

            //4. Checking if Grading Options should be disabled or not and assign default (if no value)
            //5. Checking if there are any student registration options from CLU for screen display
            List<String> studentRegOptions = new ArrayList<String>();
            List<String> crsGradingOptions = new ArrayList<String>();
            if (courseInfo != null) {
                List<String> gradingOptions = courseInfo.getGradingOptions();
                Set<String> regOpts = new HashSet<String>(Arrays.asList(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS));
                for (String gradingOption : gradingOptions) {
                    if (regOpts.contains(gradingOption)) {
                        studentRegOptions.add(gradingOption);
                    } else {
                        crsGradingOptions.add(gradingOption);
                    }
                }
                //Audit is pulled out into a dynamic attribute on course so map it back
                if("true".equals(courseInfo.getAttributeValue(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT))){
                    studentRegOptions.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
                }
            }

            formObject.setStudentRegOptions(studentRegOptions);
            formObject.setCrsGradingOptions(crsGradingOptions);

            //6. Defining Credit Option and if CLU is fixed (then it's disabled)
            boolean creditOptionFixed = false;
            CreditOptionInfo creditOption = new CreditOptionInfo();

            //Grab the Course's credit constraints
            //FindBugs: getCreditOptions() null check is in CourseInfo
            List<ResultValuesGroupInfo> courseCreditOptions = courseInfo.getCreditOptions();

            //Lookup the related course's credit constraints and set them on the creditOption
            if (!courseCreditOptions.isEmpty()) {
                ResultValuesGroupInfo resultValuesGroupInfo = courseCreditOptions.get(firstValue);
                //Check for fixed
                if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                    if (!resultValuesGroupInfo.getResultValueKeys().isEmpty()) {
                        creditOption.setCourseFixedCredits(CourseOfferingManagementUtil.getLrcService().getResultValue(resultValuesGroupInfo.getResultValueKeys().get(firstValue), contextInfo).getValue());
                    }
                    //Set the flag
                    creditOptionFixed = true;

                    //Default the value
                    creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
                    creditOption.setFixedCredit(creditOption.getCourseFixedCredits());
                    creditOption.getAllowedCredits().add(creditOption.getCourseFixedCredits());
                } else {
                    //This is either range or multiple

                    //Copy all the allowed credits and sort so that the multiple checkboxes can be properly displayed
                    List<ResultValueInfo> resultValueInfos = CourseOfferingManagementUtil.getLrcService().getResultValuesForResultValuesGroup(resultValuesGroupInfo.getKey(), contextInfo);
                    for (ResultValueInfo rVI: resultValueInfos) {
                        creditOption.getAllowedCredits().add(rVI.getValue());
                    }
                    Collections.sort(creditOption.getAllowedCredits());

                    if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
                        creditOption.setCourseMinCredits(resultValuesGroupInfo.getResultValueRange().getMinValue());
                        creditOption.setCourseMaxCredits(resultValuesGroupInfo.getResultValueRange().getMaxValue());

                        //Default the value
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
                        creditOption.setMinCredits(creditOption.getCourseMinCredits());
                        creditOption.setMaxCredits(creditOption.getCourseMaxCredits());
                    } else if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                        //Default the value
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
                        creditOption.getCredits().addAll(creditOption.getAllowedCredits());
                    }
                }
            }

            formObject.setCreditOption(creditOption);
            formObject.setCreditOptionFixed(creditOptionFixed);

            formObject.setOrganizationNames(new ArrayList<OrganizationInfoWrapper>());

            ArrayList<OrganizationInfoWrapper> orgList = new ArrayList<OrganizationInfoWrapper>();

            if(courseInfo.getUnitsContentOwner() != null && !courseInfo.getUnitsContentOwner().isEmpty()){
                for(String orgId : courseInfo.getUnitsContentOwner()){
                    OrgInfo orgInfo = CourseOfferingManagementUtil.getOrganizationService().getOrg(orgId,contextInfo);
                    orgList.add(new OrganizationInfoWrapper(orgInfo));
                }
            }
            formObject.setOrganizationNames(orgList);

            //retrieve exam period id for the term that the CO is attached to
            try {
                String examPeriodId = CourseOfferingManagementUtil.getExamOfferingServiceFacade().getExamPeriodId(formObject.getCourseOfferingInfo().getTermId(), ContextUtils.createDefaultContextInfo());
                if (!StringUtils.isEmpty(examPeriodId)) {
                    formObject.setExamPeriodId(examPeriodId);
                }
            } catch (DoesNotExistException e) {
                LOG.warn("The Term " + formObject.getCourseOfferingInfo().getTermId() + " that the course offering " + formObject.getCourseOfferingCode() + " is attached to doesn't have an exam period to create exam offerings.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // adding SOC
            SocInfo socInfo = CourseOfferingManagementUtil.getCourseOfferingSetService().getSoc(request.getParameter(CourseOfferingConstants.SOC_ID), contextInfo);
            formObject.setSocInfo(socInfo);

            setTermPropertiesOnFormObject(formObject, termId, contextInfo );
            formObject.setContextBar(CourseOfferingContextBar.NEW_INSTANCE(formObject.getTerm(), formObject.getSocInfo(),
                    CourseOfferingManagementUtil.getStateService(), CourseOfferingManagementUtil.getAcademicCalendarService(), contextInfo) );

            document.getNewMaintainableObject().setDataObject(formObject);
            document.getOldMaintainableObject().setDataObject(formObject);
            document.getDocumentHeader().setDocumentDescription("Create CO - " + courseInfo.getCode());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setTermPropertiesOnFormObject(CourseOfferingEditWrapper formObject, String termId, ContextInfo contextInfo) throws Exception {

        TermInfo termInfo = CourseOfferingManagementUtil.getAcademicCalendarService().getTerm(termId, contextInfo);
        formObject.setTerm(termInfo);
        formObject.setTermName(termInfo.getName());

        // Setting term string: Fall 2012 (09/28/2012 to 12/15/2012)
        String termStartDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(termInfo.getStartDate());
        String termEndDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(termInfo.getEndDate());
        StringBuilder termStartEnd = new StringBuilder();
        termStartEnd.append(termInfo.getName());
        termStartEnd.append(" (");
        termStartEnd.append(termStartDate);
        termStartEnd.append(" to ");
        termStartEnd.append(termEndDate);
        termStartEnd.append(")");
        formObject.setTermStartEnd(termStartEnd.toString());
    }

    private static DefaultOptionKeysService defaultOptionKeysService;
    protected static DefaultOptionKeysService getDefaultOptionKeysService() {
        if (defaultOptionKeysService == null) {
            defaultOptionKeysService = new DefaultOptionKeysServiceImpl();
        }
        return defaultOptionKeysService;
    }

    protected static CourseWaitListServiceFacade getCourseWaitListServiceFacade() {
        CourseWaitListServiceFacade courseWaitListServiceFacade =
                (CourseWaitListServiceFacade) GlobalResourceLoader.getService(CourseWaitListServiceFacadeConstants.getQName());
        return courseWaitListServiceFacade;

    }

    protected static void copyCourseOfferingInfo(CourseOfferingCreateWrapper coCreateWrapper, String targetTermCode, String catalogCourseCode, String coId) {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if(targetTermCode !=null){
            coCreateWrapper.setTargetTermCode(targetTermCode);
            TermInfo term = CourseOfferingManagementUtil.getTerm(targetTermCode);
            coCreateWrapper.setTerm(term);
        }

        if(catalogCourseCode !=null){
            coCreateWrapper.setCatalogCourseCode(catalogCourseCode);
        }

        if(coId != null){
            try {
                // configure context bar
                SocInfo soc = CourseOfferingManagementUtil.getMainSocForTerm(coCreateWrapper.getTerm(), contextInfo);
                coCreateWrapper.setSocInfo(soc);
                coCreateWrapper.setContextBar(CourseOfferingContextBar.NEW_INSTANCE(coCreateWrapper.getTerm(), coCreateWrapper.getSocInfo(),
                        CourseOfferingManagementUtil.getStateService(), CourseOfferingManagementUtil.getAcademicCalendarService(), contextInfo));

                CourseOfferingInfo theCO = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(coId, contextInfo);
                CourseOfferingEditWrapper coEditWrapper = new CourseOfferingEditWrapper(theCO);
                TermInfo termInfo = CourseOfferingManagementUtil.getAcademicCalendarService().getTerm(theCO.getTermId(), contextInfo);
                coEditWrapper.setTerm(termInfo);
                coEditWrapper.setGradingOption(CourseOfferingManagementUtil.getGradingOption(theCO.getGradingOptionId()));
                // To prevent showing the same row twice in the table. It can be caused by pressing F5 key.
                if (coCreateWrapper.getExistingTermOfferings().size() == 0) {
                    coCreateWrapper.getExistingTermOfferings().add(coEditWrapper);
                }
            } catch(Exception e){
                throw new RuntimeException ("An Exception occurred while trying to copy course to new onw. ", e);
            }
        }
    }

    protected static void continueFromCreateCopyCourseOfferingInfo(CourseOfferingCreateWrapper coWrapper, CourseInfo course, TermInfo term) {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        int firstValue = 0;

        try {
            List<CourseOfferingInfo> courseOfferingInfos = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOfferingsByCourseAndTerm(course.getId(), term.getId(), contextInfo);

            coWrapper.setCourse(course);
            coWrapper.setCreditCount(CourseOfferingViewHelperUtil.trimTrailing0(CourseOfferingManagementUtil.getLrcService().getResultValue(course.getCreditOptions().get(firstValue).getResultValueKeys().get(firstValue), contextInfo).getValue()));
            coWrapper.setShowAllSections(true);
            coWrapper.setShowCopyCourseOffering(false);
            coWrapper.setShowTermOfferingLink(true);

            coWrapper.setContextBar( CourseOfferingContextBar.NEW_INSTANCE(coWrapper.getTerm(), coWrapper.getSocInfo(), CourseOfferingManagementUtil.getStateService(), CourseOfferingManagementUtil.getAcademicCalendarService(), contextInfo) );

            coWrapper.getExistingTermOfferings().clear();
            coWrapper.getExistingOfferingsInCurrentTerm().clear();

            for (CourseOfferingInfo courseOfferingInfo : courseOfferingInfos) {
                if (StringUtils.equals(courseOfferingInfo.getStateKey(), LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)) {
                    CourseOfferingEditWrapper co = new CourseOfferingEditWrapper(courseOfferingInfo);
                    co.setGradingOption(CourseOfferingManagementUtil.getGradingOption(courseOfferingInfo.getGradingOptionId()));
                    coWrapper.getExistingOfferingsInCurrentTerm().add(co);
                }
            }

            //Get past 5 years CO
            Calendar termStart = Calendar.getInstance();
            termStart.setTime(term.getStartDate());
            String termYear = Integer.toString(termStart.get(Calendar.YEAR));

            org.kuali.student.r2.core.search.dto.SearchRequestInfo searchRequest = new org.kuali.student.r2.core.search.dto.SearchRequestInfo(CourseOfferingHistorySearchImpl.PAST_CO_SEARCH.getKey());
            searchRequest.addParam(CourseOfferingHistorySearchImpl.COURSE_ID, coWrapper.getCourse().getId());

            searchRequest.addParam(CourseOfferingHistorySearchImpl.TARGET_YEAR_PARAM, termYear);
            org.kuali.student.r2.core.search.dto.SearchResultInfo searchResult = CourseOfferingManagementUtil.getSearchService().search(searchRequest, null);

            List<String> courseOfferingIds = new ArrayList<String>(searchResult.getTotalResults());
            for (org.kuali.student.r2.core.search.dto.SearchResultRowInfo row : searchResult.getRows()) {
                courseOfferingIds.add(row.getCells().get(firstValue).getValue());
            }

            courseOfferingInfos = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOfferingsByIds(courseOfferingIds, contextInfo);

            for (CourseOfferingInfo courseOfferingInfo : courseOfferingInfos) {
                CourseOfferingEditWrapper co = new CourseOfferingEditWrapper(courseOfferingInfo);
                TermInfo termInfo = CourseOfferingManagementUtil.getAcademicCalendarService().getTerm(courseOfferingInfo.getTermId(), contextInfo);
                co.setTerm(termInfo);
                co.setGradingOption(CourseOfferingManagementUtil.getGradingOption(courseOfferingInfo.getGradingOptionId()));
                coWrapper.getExistingTermOfferings().add(co);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static List<String> getOptionKeys(CourseOfferingCreateWrapper createWrapper, CourseOfferingInfo existingCO) {

        List<String> optionKeys = getDefaultOptionKeysService().getDefaultOptionKeysForCopySingleCourseOffering();

        if (createWrapper.isExcludeInstructorInformation()) {
            optionKeys.add(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY);
        }

        if (createWrapper.isExcludeSchedulingInformation()) {
            optionKeys.add(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY);
        }

        if (createWrapper.isExcludeCancelledActivityOfferings()) {
            optionKeys.add(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_AO_OPTION_KEY);
        }

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        try {
            // if source term differs from target term determine if add suffix or not
            if (StringUtils.equals(existingCO.getTermId(), createWrapper.getTerm().getId())) {
                optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY);
            } else {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.and(
                        PredicateFactory.like("courseOfferingCode", existingCO.getCourseOfferingCode() + "%"),
                        PredicateFactory.equalIgnoreCase("atpId", createWrapper.getTerm().getId())));
                QueryByCriteria criteria = qbcBuilder.build();
                List<String> courseOfferingIds = CourseOfferingManagementUtil.getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

                if (courseOfferingIds.size() > 0) {
                    optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //  According to Jira 4353 copy the title from CO instead of CLU. So set the property not to copy from CLU
        optionKeys.add(CourseOfferingSetServiceConstants.NOT_COURSE_TITLE_OPTION_KEY);
        return optionKeys;
    }

}
