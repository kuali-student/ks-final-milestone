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
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OrganizationInfoWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseInfoByTermLookupableImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.DefaultOptionKeysService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.DefaultOptionKeysServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.search.CourseOfferingHistorySearchImpl;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * This class provides business logic related to the CourseOfferingController
 *
 * @author Kuali Student Team
 */
public class CourseOfferingControllerBusinessLogic {

    private static transient CourseService courseService;
    private static TypeService typeService;
    private static LRCService lrcService;
    private static CourseOfferingSetService courseOfferingSetService;
    private static AcademicCalendarService acalService;
    private static StateService stateService;
    private static OrganizationService organizationService;
    private static CluService cluService;
    private static CourseOfferingService courseOfferingService;
    private static SearchService searchService;
    private static CourseOfferingSetService socService;
    private static AtpService atpService;

    protected static void populateCreateCourseOfferingForm(MaintenanceDocumentForm form, HttpServletRequest request) {
        int firstValue = 0;

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            MaintenanceDocument document = form.getDocument();
            String termId = request.getParameter(CourseOfferingConstants.TARGET_TERM_ID);
            CourseInfo courseInfo = getCourseService().getCourse(request.getParameter(CourseOfferingConstants.COURSE_ID), contextInfo);

            //1. set CourseOfferingInfo - no saved Course Offering as of yet (see logic in CourseOfferingEditMaintainableImpl.retrieveObjectForEditOrCopy
            CourseOfferingInfo coInfo = createCourseOfferingInfo(termId, courseInfo);
            CourseOfferingEditWrapper formObject = new CourseOfferingEditWrapper(coInfo);
            formObject.setCreateCO(true);

            //2. set CourseInfo
            formObject.setCourse(courseInfo);

            //3. set formatOfferingList
            formObject.setFormatOfferingList(new ArrayList<FormatOfferingWrapper>());
            FormatOfferingWrapper defaultFO = new FormatOfferingWrapper();
            defaultFO.setFormatId(courseInfo.getFormats().get(firstValue).getId());
            defaultFO.getRenderHelper().setNewRow(true);
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
                        creditOption.setCourseFixedCredits(getLrcService().getResultValue(resultValuesGroupInfo.getResultValueKeys().get(firstValue), contextInfo).getValue());
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
                    List<ResultValueInfo> resultValueInfos = getLrcService().getResultValuesForResultValuesGroup(resultValuesGroupInfo.getKey(), contextInfo);
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
                    OrgInfo orgInfo = getOrganizationService().getOrg(orgId,contextInfo);
                    orgList.add(new OrganizationInfoWrapper(orgInfo));
                }
            }
            formObject.setOrganizationNames(orgList);

            // adding SOC
            SocInfo socInfo = getCourseOfferingSetService().getSoc(request.getParameter(CourseOfferingConstants.SOC_ID), contextInfo);
            formObject.setSocInfo(socInfo);

            setTermPropertiesOnFormObject(formObject, termId, contextInfo );
            formObject.setContextBar(CourseOfferingContextBar.NEW_INSTANCE(formObject.getTerm(), formObject.getSocInfo(),
                    getStateService(), getAcalService(), contextInfo) );

            document.getNewMaintainableObject().setDataObject(formObject);
            document.getOldMaintainableObject().setDataObject(formObject);
            document.getDocumentHeader().setDocumentDescription("Create CO - " + courseInfo.getCode());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static CourseOfferingInfo createCourseOfferingInfo(String termId, CourseInfo courseInfo) throws Exception {

        int firstGradingOption = 0;
        CourseOfferingInfo courseOffering = new CourseOfferingInfo();

        courseOffering.setTermId(termId);
        courseOffering.setCourseOfferingTitle(courseInfo.getCourseTitle());
        courseOffering.setCourseId(courseInfo.getId());
        courseOffering.setCourseCode(courseInfo.getCode());
        courseOffering.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        courseOffering.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        courseOffering.setHasWaitlist(false);
        courseOffering.setCourseOfferingCode(courseInfo.getCode());

        //Copy grading and credit options
        if(!courseInfo.getCreditOptions().isEmpty()){
            courseOffering.setCreditOptionId(courseInfo.getCreditOptions().get(firstGradingOption).getKey());
        }
        //Remove these two special student registration options and set them on the CO
        List<String> courseGradingOptions = new ArrayList<String>(courseInfo.getGradingOptions());
        if(courseGradingOptions.remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL) ){
            courseOffering.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
        }
        if(courseGradingOptions.remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT) ){
            courseOffering.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
        }
        //set the first remaining grading option on the CO
        if(!courseGradingOptions.isEmpty()){
            courseOffering.setGradingOptionId(courseGradingOptions.get(firstGradingOption));
        }

        // make sure we set attribute information from the course
        if(!courseInfo.getAttributes().isEmpty()){
            for(AttributeInfo info: courseInfo.getAttributes()){
                // Default the CourseOffering Final Exam Type to the Final Exam type in the Course
                if(info.getKey().equals("finalExamStatus")){
                    courseOffering.setFinalExamType(convertCourseFinalExamTypeToCourseOfferingFinalExamType(info.getValue()));
                }
            }
        }

        return courseOffering;
    }

    protected static String convertCourseFinalExamTypeToCourseOfferingFinalExamType(String courseFinalExamType){
        String sRet = null;
        if("STD".equals(courseFinalExamType))   {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD;
        } else if("ALT".equals(courseFinalExamType)) {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_ALTERNATE;
        } else {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_NONE;
        }
        return sRet;
    }

    private static void setTermPropertiesOnFormObject(CourseOfferingEditWrapper formObject, String termId, ContextInfo contextInfo ) throws Exception {

        TermInfo termInfo = getAcalService().getTerm(termId, contextInfo);
        formObject.setTerm(termInfo);
        formObject.setTermName(termInfo.getName());

        // Setting term string: Fall 2012 (09/28/2012 to 12/15/2012)
        String termStartDate = new String( DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format( termInfo.getStartDate() ) );
        String termEndDate = new String( DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format( termInfo.getEndDate() ) );
        StringBuilder termStartEnd = new StringBuilder();
        termStartEnd.append( termInfo.getName() );
        termStartEnd.append( " (" );
        termStartEnd.append( termStartDate );
        termStartEnd.append( " to " );
        termStartEnd.append( termEndDate );
        termStartEnd.append( ")" );
        formObject.setTermStartEnd( termStartEnd.toString() );
    }

    protected static String getGradingOption(String gradingOptionId) throws Exception {
        String gradingOption = "";
        if (StringUtils.isNotBlank(gradingOptionId)) {
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(gradingOptionId, ContextUtils.createDefaultContextInfo());
            if (rvg != null && StringUtils.isNotBlank(rvg.getName())) {
                gradingOption = rvg.getName();
            }
        }

        return gradingOption;
    }

    private static DefaultOptionKeysService defaultOptionKeysService;
    protected static DefaultOptionKeysService getDefaultOptionKeysService() {
        if (defaultOptionKeysService == null) {
            defaultOptionKeysService = new DefaultOptionKeysServiceImpl();
        }
        return defaultOptionKeysService;
    }

    protected static void copyCourseOfferingInfo(CourseOfferingCreateWrapper coCreateWrapper, String targetTermCode, String catalogCourseCode, String coId) {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if(targetTermCode !=null){
            coCreateWrapper.setTargetTermCode(targetTermCode);
            TermInfo term = getTerm(targetTermCode);
            coCreateWrapper.setTerm(term);
        }

        if(catalogCourseCode !=null){
            coCreateWrapper.setCatalogCourseCode(catalogCourseCode);
        }

        if(coId != null){
            try {
                // configure context bar
                SocInfo soc = getMainSocForTerm(coCreateWrapper.getTerm(), contextInfo);
                coCreateWrapper.setSocInfo(soc);
                coCreateWrapper.setContextBar(CourseOfferingContextBar.NEW_INSTANCE(coCreateWrapper.getTerm(), coCreateWrapper.getSocInfo(),
                        getStateService(), getAcalService(), contextInfo));

                CourseOfferingInfo theCO = getCourseOfferingService().getCourseOffering(coId, contextInfo);
                CourseOfferingEditWrapper coEditWrapper = new CourseOfferingEditWrapper(theCO);
                TermInfo termInfo = getAcalService().getTerm(theCO.getTermId(), contextInfo);
                coEditWrapper.setTerm(termInfo);
                coEditWrapper.setGradingOption(getGradingOption(theCO.getGradingOptionId()));
                // To prevent showing the same row twice in the table. It can be caused by pressing F5 key.
                if (coCreateWrapper.getExistingTermOfferings().size() == 0) {
                    coCreateWrapper.getExistingTermOfferings().add(coEditWrapper);
                }
            } catch(Exception e){
                throw new RuntimeException ("An Exception occurred while trying to copy course to new onw. ", e);
            }
        }
    }

    protected static TermInfo getTerm(String termCode) {

        int firstTerm = 0;

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p = null;

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(termCode)) {
            p = equal("atpCode", termCode);
            pList.add(p);
        }
        qBuilder.setPredicates(p);

        try {
            List<TermInfo> terms = getAcalService().searchForTerms(qBuilder.build(), ContextUtils.createDefaultContextInfo());
            if (terms.size() > 1) {
                return null;
            } else if (terms.isEmpty()) {
                return null;
            }
            return terms.get(firstTerm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static List<CourseInfo> retrieveMatchingCourses(String courseCode, TermInfo term) {

        int firstTerm = 0;
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        CourseInfo returnCourseInfo;
        String courseId;
        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        List<CourseInfo> courseInfoList = new ArrayList<CourseInfo>();

        SearchParamInfo qpv = new SearchParamInfo();
        qpv.setKey("lu.queryParam.luOptionalType");
        qpv.getValues().add("kuali.lu.type.CreditCourse");
        searchParams.add(qpv);

        //Include course states of: Active, Superseded and Retired
        qpv = new SearchParamInfo();
        qpv.setKey("lu.queryParam.luOptionalState");
        qpv.setValues(Arrays.asList(
                DtoConstants.STATE_ACTIVE,
                DtoConstants.STATE_SUPERSEDED,
                DtoConstants.STATE_RETIRED));
        searchParams.add(qpv);

        /**Note:  TermCode lookup criteria field takes the special bus route:  it is used to retrieve
         * term begin/end dates to in-turn constrain courses by their effective and
         * retire dates, respectively.  AND NOTE that termId is not itself used
         * directly in the course lookup query.
         */
        List<AtpInfo> atps;
        try {
            atps = getAtpService().getAtpsByCode(term.getCode(), context);
        } catch (Exception e) {
            return courseInfoList;
        }
        if (atps == null || atps.size() != 1) {
            return courseInfoList;
        }

        qpv = new SearchParamInfo();
        qpv.setKey(CourseInfoByTermLookupableImpl.QueryParamEnum.TERM_START.getQueryKey());
        qpv.getValues().add(DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(atps.get(firstTerm).getStartDate()));
        searchParams.add(qpv);

        qpv = new SearchParamInfo();
        qpv.setKey(CourseInfoByTermLookupableImpl.QueryParamEnum.TERM_END.getQueryKey());
        qpv.getValues().add(DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(atps.get(firstTerm).getEndDate()));
        searchParams.add(qpv);

        //set courseCode to query by
        qpv = new SearchParamInfo();
        qpv.setKey(CourseInfoByTermLookupableImpl.QueryParamEnum.CODE.getQueryKey());
        qpv.getValues().add(courseCode);
        searchParams.add(qpv);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.courseCodes");

        try {
            SearchResultInfo searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            if (searchResult.getRows().size() > 0) {
                for (SearchResultRowInfo row : searchResult.getRows()) {
                    List<SearchResultCellInfo> srCells = row.getCells();
                    if (srCells != null && srCells.size() > 0) {
                        for (SearchResultCellInfo cell : srCells) {
                            if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                                courseId = cell.getValue();
                                returnCourseInfo = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
                                courseInfoList.add(returnCourseInfo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return courseInfoList;
    }

    protected static Properties _buildCOURLParameters(String courseId, String termId, String socId, String methodToCall) {
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(CourseOfferingConstants.COURSE_ID, courseId);
        props.put(CourseOfferingConstants.TARGET_TERM_ID, termId);
        props.put(CourseOfferingConstants.SOC_ID, socId);
        props.put(CourseOfferingConstants.CREATE_COURSEOFFERING, "true");
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName());

        return props;
    }

    protected static SocInfo getMainSocForTerm(TermInfo term, ContextInfo contextInfo) throws Exception {
        List<String> socIds = getSocService().getSocIdsByTerm(term.getId(), contextInfo);
        List<SocInfo> socInfos = getSocService().getSocsByIds(socIds, contextInfo);
        for (SocInfo socInfo: socInfos) {
            if (socInfo.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                return socInfo;
            }
        }
        return null;
    }

    protected static void continueFromCreateCopyCourseOfferingInfo(CourseOfferingCreateWrapper coWrapper, CourseInfo course, TermInfo term) {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        int firstValue = 0;

        try {
            List<CourseOfferingInfo> courseOfferingInfos = getCourseOfferingService().getCourseOfferingsByCourseAndTerm(course.getId(), term.getId(), contextInfo);

            coWrapper.setCourse(course);
            coWrapper.setCreditCount(CourseOfferingViewHelperUtil.trimTrailing0(getLrcService().getResultValue(course.getCreditOptions().get(firstValue).getResultValueKeys().get(firstValue), contextInfo).getValue()));
            coWrapper.setShowAllSections(true);
            coWrapper.setShowCopyCourseOffering(false);
            coWrapper.setShowTermOfferingLink(true);

            coWrapper.setContextBar( CourseOfferingContextBar.NEW_INSTANCE(coWrapper.getTerm(), coWrapper.getSocInfo(), getStateService(), getAcalService(), contextInfo) );

            coWrapper.getExistingTermOfferings().clear();
            coWrapper.getExistingOfferingsInCurrentTerm().clear();

            for (CourseOfferingInfo courseOfferingInfo : courseOfferingInfos) {
                if (StringUtils.equals(courseOfferingInfo.getStateKey(), LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)) {
                    CourseOfferingEditWrapper co = new CourseOfferingEditWrapper(courseOfferingInfo);
                    co.setGradingOption(getGradingOption(courseOfferingInfo.getGradingOptionId()));
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
            org.kuali.student.r2.core.search.dto.SearchResultInfo searchResult = getSearchService().search(searchRequest, null);

            List<String> courseOfferingIds = new ArrayList<String>(searchResult.getTotalResults());
            for (org.kuali.student.r2.core.search.dto.SearchResultRowInfo row : searchResult.getRows()) {
                courseOfferingIds.add(row.getCells().get(firstValue).getValue());
            }

            courseOfferingInfos = getCourseOfferingService().getCourseOfferingsByIds(courseOfferingIds, contextInfo);

            for (CourseOfferingInfo courseOfferingInfo : courseOfferingInfos) {
                CourseOfferingEditWrapper co = new CourseOfferingEditWrapper(courseOfferingInfo);
                TermInfo termInfo = getAcalService().getTerm(courseOfferingInfo.getTermId(), contextInfo);
                co.setTerm(termInfo);
                co.setGradingOption(getGradingOption(courseOfferingInfo.getGradingOptionId()));
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
                List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

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

    private static CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    private static LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lrc", "LrcService"));
        }
        return lrcService;
    }

    protected static CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }

    private static AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
        }
        return acalService;
    }

    private static StateService getStateService() {
        if( stateService == null ) {
            stateService = (StateService) GlobalResourceLoader.getService( new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART) );
        }
        return stateService;
    }

    private static OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }

    private static TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    private static CluService getCluService() {
        if (cluService == null) {
            cluService = CourseOfferingResourceLoader.loadCluService();
        }
        return cluService;
    }

    private static SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    private static CourseOfferingSetService getSocService() {
        if (socService == null) {
            socService = CourseOfferingResourceLoader.loadSocService();
        }
        return socService;
    }

    private static AtpService getAtpService() {
        if(atpService == null){
            atpService = CourseOfferingResourceLoader.loadAtpService();
        }
        return atpService;
    }

    protected static CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

}
