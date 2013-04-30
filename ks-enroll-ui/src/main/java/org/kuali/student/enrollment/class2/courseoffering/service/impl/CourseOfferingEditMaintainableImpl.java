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
 * Created by vgadiyak on 5/30/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OrganizationInfoWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditMaintainableImpl extends CourseOfferingMaintainableImpl implements CourseOfferingMaintainable{
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(CourseOfferingEditMaintainableImpl.class);

    private transient OrganizationService organizationService;
    private transient LRCService lrcService;
    private transient AcademicCalendarService acalService;
    private transient CourseOfferingSetService courseOfferingSetService;
    private transient TypeService typeService;

    //TODO : implement the functionality for Personnel section and its been delayed now since the backend implementation is not yet ready (06/06/2012).

    @Override
    public void saveDataObject() {
        if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)) {
            CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)getDataObject(); 
            updateCourseOffering(coEditWrapper);
        }
        else{//for new and copy action, report error
             LOG.error(">>>Do not support!");
        }        
 
    }

    private void updateCourseOffering(CourseOfferingEditWrapper coEditWrapper){
        try{
            // persist format offerings
            updateFormatOfferings(coEditWrapper);

            //persist unitDeploymentOrgIds
            List<String> unitDeploymentOrgIds = new ArrayList<String>();
            for(OrganizationInfoWrapper orgWrapper : coEditWrapper.getOrganizationNames()){
                unitDeploymentOrgIds.add(orgWrapper.getId());
            }

            CourseOfferingInfo coInfo = coEditWrapper.getCourseOfferingInfo();
            coInfo.setUnitsDeploymentOrgIds(unitDeploymentOrgIds);

            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            // Credit Options (also creates extra-line)
            if (coEditWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED) &&
                    !coEditWrapper.getCreditOption().getFixedCredit().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = getLrcService().getCreateFixedCreditResultValuesGroup(coEditWrapper.getCreditOption().getFixedCredit(),
                        LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, contextInfo);
                coInfo.setCreditOptionId(rvgInfo.getKey());
            } else if (coEditWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE) &&
                    !coEditWrapper.getCreditOption().getMinCredits().isEmpty() && !coEditWrapper.getCreditOption().getMaxCredits().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = getLrcService().getCreateRangeCreditResultValuesGroup(coEditWrapper.getCreditOption().getMinCredits(),
                        coEditWrapper.getCreditOption().getMaxCredits(), calculateIncrement(coEditWrapper.getCreditOption().getAllowedCredits()), LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, contextInfo);
                coInfo.setCreditOptionId(rvgInfo.getKey());
            } else if (coEditWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE) &&
                    !coEditWrapper.getCreditOption().getCredits().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = getLrcService().getCreateMultipleCreditResultValuesGroup(coEditWrapper.getCreditOption().getCredits(),
                        LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, contextInfo);
                coInfo.setCreditOptionId(rvgInfo.getKey());
            }

            // CO code
            String courseOfferingCode = coEditWrapper.getCourse().getCode();
            coInfo.setCourseNumberSuffix(StringUtils.upperCase(coInfo.getCourseNumberSuffix()));
            if (!StringUtils.isEmpty(coInfo.getCourseNumberSuffix())) {
                courseOfferingCode += coInfo.getCourseNumberSuffix();
            }
            coInfo.setCourseOfferingCode(courseOfferingCode);

            // Waitlist
            if (!coInfo.getHasWaitlist()) {
                coInfo.setWaitlistTypeKey(null);
                coInfo.setWaitlistLevelTypeKey(null);
            }

            //TODO REMOVE THIS AFTER KRAD CHECKLISTS ARE FIXED for student registration options
            //determine if audit reg options and pass/fail reg options should be added/removed to/from coInfo
            if(coEditWrapper.getAuditStudentRegOpts() &&
                    !coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)){
                coInfo.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
            }else if (!coEditWrapper.getAuditStudentRegOpts() &&
                    coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)){
                coInfo.getStudentRegistrationGradingOptions().remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
            }

            if(coEditWrapper.getPassFailStudentRegOpts() &&
                    !coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)){
                coInfo.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
            }else if (!coEditWrapper.getPassFailStudentRegOpts() &&
                    coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)){
                coInfo.getStudentRegistrationGradingOptions().remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
            }

            //Save cross lists
            loadCrossListedCOs(coEditWrapper,coInfo);

            getCourseOfferingService().updateCourseOffering(coInfo.getId(), coInfo, contextInfo);

            // check for changes to states in CO and related FOs (may happen in the case of deleted FOs)
//            CourseOfferingViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(coInfo, contextInfo);

        }   catch (Exception ex){
            throw new RuntimeException(ex);
        }

    }

    private String calculateIncrement(List<String> credits) {
        //Sort the list of credits options by the float value.
        Collections.sort(credits, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return Float.valueOf(o1).compareTo(Float.valueOf(02));
            }
        });
        //Find the difference between the first two values to get the increment
        return String.valueOf(Float.parseFloat(credits.get(1))-Float.parseFloat(credits.get(0)));
    }

    private void updateFormatOfferings(CourseOfferingEditWrapper coEditWrapper) throws Exception{
        List<FormatOfferingInfo> updatedFormatOfferingList = new ArrayList<FormatOfferingInfo>();
        List<FormatOfferingInfo> formatOfferingList = coEditWrapper.getFormatOfferingList();
        CourseOfferingInfo coInfo = coEditWrapper.getCourseOfferingInfo();
        List <String> currentFOIds = getExistingFormatOfferingIds(coInfo.getId());
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        if (formatOfferingList != null && !formatOfferingList.isEmpty())  {
            for(FormatOfferingInfo formatOfferingInfo : formatOfferingList){
                if(formatOfferingInfo.getId()!=null &&
                        !formatOfferingInfo.getId().isEmpty() &&
                        currentFOIds.contains(formatOfferingInfo.getId())) {
                    //update FO
                    if (coInfo.getFinalExamType() != null && !coInfo.getFinalExamType().equals(CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD)) {
                        formatOfferingInfo.setFinalExamLevelTypeKey(null);
                    }
                    // Populate AO types (all FOs should "require" this (less important here since it should
                    // already exist)
                    CourseOfferingViewHelperUtil.addActivityOfferingTypesToFormatOffering(formatOfferingInfo, coEditWrapper.getCourse(), getTypeService(), contextInfo);
                    FormatOfferingInfo updatedFormatOffering = getCourseOfferingService().
                            updateFormatOffering(formatOfferingInfo.getId(),formatOfferingInfo, contextInfo);
                    updatedFormatOfferingList.add(updatedFormatOffering);
                    currentFOIds.remove(formatOfferingInfo.getId());
                }
                else{
                    //create a new FO
                    formatOfferingInfo.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
                    formatOfferingInfo.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
                    formatOfferingInfo.setName(null);//Clear these out so they are generated nicely
                    formatOfferingInfo.setDescr(null);
                    formatOfferingInfo.setTermId(coInfo.getTermId());
                    formatOfferingInfo.setCourseOfferingId(coInfo.getId());
                    if (coInfo.getFinalExamType() != null && !coInfo.getFinalExamType().equals(CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD)) {
                        formatOfferingInfo.setFinalExamLevelTypeKey(null);
                    }
                    // Populate AO types (all FOs should "require" this
                    CourseOfferingViewHelperUtil.addActivityOfferingTypesToFormatOffering(formatOfferingInfo, coEditWrapper.getCourse(), getTypeService(), contextInfo);
                    FormatOfferingInfo createdFormatOffering = getCourseOfferingService().
                            createFormatOffering(coInfo.getId(), formatOfferingInfo.getFormatId(), formatOfferingInfo.getTypeKey(), formatOfferingInfo, contextInfo);
                    updatedFormatOfferingList.add(createdFormatOffering);
                }
            }
            coEditWrapper.setFormatOfferingList(updatedFormatOfferingList);

        }
        //delete FormatOfferings that have been removed by the user
        if (currentFOIds != null && currentFOIds.size() > 0){
            for(String formatOfferingId: currentFOIds){
                //delete all AOs associated with this FO, then delete FO
                //Note by bonnie deleteAO invoked in deleteFormatOfferingCascaded seems not completely correct.
                //I didn't see the code if removing FO-AO relations before deleting AOs....
                getCourseOfferingService().deleteFormatOfferingCascaded(formatOfferingId, contextInfo);
            }
        }
    }

    private List<String> getExistingFormatOfferingIds(String courseOfferingId) throws Exception{
        List<FormatOfferingInfo> formatOfferingInfoList = getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, ContextUtils.createDefaultContextInfo());
        List<String> formatOfferingIds = new ArrayList<String>();

        if(formatOfferingInfoList != null && !formatOfferingInfoList.isEmpty()){
            for(FormatOfferingInfo formatOfferingInfo : formatOfferingInfoList){
                formatOfferingIds.add(formatOfferingInfo.getId());
            }
        }
        return formatOfferingIds;
    }

    @Override
    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof OfferingInstructorInfo){
            OfferingInstructorInfo instructorInfo = (OfferingInstructorInfo) addLine;

            //check duplication
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            List<OfferingInstructorInfo> instructors = coEditWrapper.getCourseOfferingInfo().getInstructors();
            if(instructors != null && !instructors.isEmpty()){
                for(OfferingInstructorInfo thisInst : instructors){
                    if(instructorInfo.getPersonId().equals(thisInst.getPersonId())){
                        GlobalVariables.getMessageMap().putErrorForSectionId("KS-CourseOfferingEdit-PersonnelTableSection", ActivityOfferingConstants.MSG_ERROR_INSTRUCTOR_DUPLICATE, instructorInfo.getPersonId());
                        return false;
                    }
                }
            }

            //validate ID
            List<Person> lstPerson = CourseOfferingViewHelperUtil.getInstructorByPersonId(instructorInfo.getPersonId());
            if(lstPerson == null || lstPerson.isEmpty()){
                GlobalVariables.getMessageMap().putErrorForSectionId("KS-CourseOfferingEdit-PersonnelSection", ActivityOfferingConstants.MSG_ERROR_INSTRUCTOR_NOTFOUND, instructorInfo.getPersonId());
                return false;
            }
        }

        return super.performAddLineValidation(view, collectionGroup, model, addLine);
    }

    @Override
    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof FormatOfferingInfo){
            FormatOfferingInfo newLine = (FormatOfferingInfo)addLine;
            String formatId = newLine.getFormatId();
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            FormatInfo formatInfo = getFormatInfo(coEditWrapper, formatId);
            // TODO: fix R2 Format to include name and short name
            StringBuilder sb = new StringBuilder();
            for(ActivityInfo activityInfo:formatInfo.getActivities()){
                String activityTypeKey = activityInfo.getTypeKey();
                String activityName = "";
                try{
                    if(!activityTypeKey.isEmpty()){
                        activityName = typeService.getType(activityTypeKey, ContextUtils.createDefaultContextInfo()).getName();
                    }
                } catch (Exception e){
                    throw new RuntimeException(e);
                }

                sb.append(activityName);
                sb.append("/");
            }
            String tempName = sb.toString().substring(0,sb.toString().length()-1);
            newLine.setName(tempName);
            newLine.setShortName(tempName);
        }
    }

    @Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if(addLine instanceof OfferingInstructorInfo) {
            // set the person name if it's null, in the case of user-input personell id
            OfferingInstructorInfo instructorInfo = (OfferingInstructorInfo)addLine;
            if(instructorInfo.getPersonName() == null && instructorInfo.getPersonId() != null) {
                List<Person> personList = CourseOfferingViewHelperUtil.getInstructorByPersonId(instructorInfo.getPersonId());
                if(personList.size() == 1) {
                    instructorInfo.setPersonName(personList.get(0).getName());
                }
            }

            // make sure state is not null
            if(instructorInfo.getStateKey() == null) {
                instructorInfo.setStateKey(LprServiceConstants.TENTATIVE_STATE_KEY);
            }
        }
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            if (getDataObject() instanceof CourseOfferingEditWrapper){
                //0. get credit count from CourseInfo
                CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(dataObjectKeys.get("courseOfferingInfo.id"), contextInfo);
                CourseInfo courseInfo = getCourseService().getCourse(coInfo.getCourseId(), contextInfo);

                //1. set CourseOfferingInfo
                CourseOfferingEditWrapper formObject = new CourseOfferingEditWrapper(coInfo);

                //2. set CourseInfo
                formObject.setCourse(courseInfo);

                //3. set formatOfferingList
                List<FormatOfferingInfo> formatOfferingList = getCourseOfferingService().getFormatOfferingsByCourseOffering(coInfo.getId(), contextInfo);
                formObject.setFormatOfferingList(formatOfferingList);

                //4. Checking if Grading Options should be disabled or not and assign default (if no value)
                //5. Checking if there are any student registration options from CLU for screen display
                List<String> studentRegOptions = new ArrayList<String>();
                List<String> crsGradingOptions = new ArrayList<String>();
                if (coInfo.getCourseId() != null && courseInfo != null) {
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

                //TODO REMOVE THIS WHEN KRAD IS FIXED
                if(coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)){
                    formObject.setAuditStudentRegOpts(true);
                }else{
                    formObject.setAuditStudentRegOpts(false);
                }
                if(coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)){
                    formObject.setPassFailStudentRegOpts(true);
                }else{
                    formObject.setPassFailStudentRegOpts(false);
                }

                formObject.setStudentRegOptions(studentRegOptions);
                formObject.setCrsGradingOptions(crsGradingOptions);

                //6. Defining Credit Option and if CLU is fixed (then it's disabled)
                boolean creditOptionFixed = false;
                String creditOptionId = coInfo.getCreditOptionId();

                CreditOptionInfo creditOption = new CreditOptionInfo();

                //Grab the Course's credit constraints
                //FindBugs: getCreditOptions() null check is in CourseInfo
                List<ResultValuesGroupInfo> courseCreditOptions = courseInfo.getCreditOptions();

                //Lookup the related course's credit constraints and set them on the creditOption
                if (coInfo.getCourseId() != null && !courseCreditOptions.isEmpty()) {
                    ResultValuesGroupInfo resultValuesGroupInfo = courseCreditOptions.get(0);
                    //Check for fixed
                    if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                        if (!resultValuesGroupInfo.getResultValueKeys().isEmpty()) {
                            creditOption.setCourseFixedCredits(getLrcService().getResultValue(resultValuesGroupInfo.getResultValueKeys().get(0), contextInfo).getValue());
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

                //Lookup the selected credit option and set from persisted values
                if (creditOptionId != null) {
                    //Lookup the resultValueGroup Information
                    ResultValuesGroupInfo resultValuesGroupInfo = getLrcService().getResultValuesGroup(creditOptionId, contextInfo);
                    String typeKey = resultValuesGroupInfo.getTypeKey();

                    //Get the actual values
                    List<ResultValueInfo> resultValueInfos = getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), contextInfo);

                    if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
                        if (!resultValueInfos.isEmpty()) {
                            creditOption.setFixedCredit(resultValueInfos.get(0).getValue());
                        }
                    } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
                        creditOption.setMinCredits(resultValuesGroupInfo.getResultValueRange().getMinValue());
                        creditOption.setMaxCredits(resultValuesGroupInfo.getResultValueRange().getMaxValue());
                    } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
                        if (!resultValueInfos.isEmpty()) {
                            List<String> credits = new ArrayList<String>();
                            for (ResultValueInfo resultValueInfo : resultValueInfos) {
                                credits.add(resultValueInfo.getValue());
                            }
                            creditOption.setCredits(credits);
                        }
                    }
                }

                formObject.setCreditOption(creditOption);
                formObject.setCreditOptionFixed(creditOptionFixed);

                formObject.setOrganizationNames(new ArrayList<OrganizationInfoWrapper>());

                ArrayList<OrganizationInfoWrapper> orgList = new ArrayList<OrganizationInfoWrapper>();

                if(coInfo.getUnitsDeploymentOrgIds() != null){
                    for(String orgId: coInfo.getUnitsDeploymentOrgIds()){
                        OrgInfo orgInfo = getOrganizationService().getOrg(orgId,contextInfo);
                        orgList.add(new OrganizationInfoWrapper(orgInfo));
                    }
                }
                formObject.setOrganizationNames(orgList);

                // Setting term string: Fall 2012 (09/28/2012 to 12/15/2012)
                TermInfo termInfo = getAcalService().getTerm(coInfo.getTermId(), contextInfo);
                StringBuilder termStartDate = new StringBuilder(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(termInfo.getStartDate()));
                StringBuilder termEndDate = new StringBuilder(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(termInfo.getEndDate()));
                String termStartEnd = termInfo.getName() + " (" + termStartDate + " to " +termEndDate + ")";
                formObject.setTermStartEnd(termStartEnd);

                // Set socInfo
//                List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(coInfo.getTermId(), ContextUtils.createDefaultContextInfo());
//                 if (socIds != null && !socIds.isEmpty()){
//                    //For M5, it should have only one SOC
//                    if (socIds.size() > 1){
//                        throw new RuntimeException("More than one SOC found for a term");
//                    }
//                    SocInfo soc = getCourseOfferingSetService().getSoc(socIds.get(0),ContextUtils.createDefaultContextInfo());
//                    formObject.setSocInfo(soc);
//                }

                //Bonnie: above implementation assumes that each term only has one SOC, which might not be true any more
                // in m6 and after...
                // Although a Term can have more than one SOC, it only has one Main type of SOC and
                // all COs should look into the state of that MAIN socInfo for the specified term
                List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(coInfo.getTermId(), ContextUtils.createDefaultContextInfo());
                if (socIds != null && !socIds.isEmpty()) {
                    List<SocInfo> targetSocs = getCourseOfferingSetService().getSocsByIds(socIds, ContextUtils.createDefaultContextInfo());
                    for (SocInfo soc: targetSocs) {
                        if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                            formObject.setSocInfo(soc);
                        }
                    }
                }

                document.getNewMaintainableObject().setDataObject(formObject);
                document.getOldMaintainableObject().setDataObject(formObject);
                document.getDocumentHeader().setDocumentDescription("Edit CO - " + coInfo.getCourseOfferingCode());

                //            StateInfo state = getStateService().getState(formObject.getDto().getStateKey(), contextInfo());
    //            formObject.setStateName(state.getName());
                Person user = GlobalVariables.getUserSession().getPerson();

                boolean canOpenView = this.getDocumentDictionaryService().getDocumentAuthorizer(document).canOpen(document,user);
                if (!canOpenView) {
                    throw new AuthorizationException(user.getPrincipalName(), "open", null,
                            "User '" + user.getPrincipalName() + "' is not authorized to open view", null);
                }


                //Cross listing
                for (CourseOfferingCrossListingInfo crossListingInfo : coInfo.getCrossListings()){
                    formObject.getAlternateCOCodes().add(crossListingInfo.getCode());
                    formObject.getAlternateCourseCodesSuffixStripped().add(StringUtils.stripEnd(crossListingInfo.getCode(),crossListingInfo.getCourseNumberSuffix()));
                }
                return formObject;
            }
        } catch (Exception e) {
            if(e instanceof AuthorizationException){
                throw new AuthorizationException(null,null,null,null);
            }
            throw new RuntimeException(e);
        }
        return null;
    }

    private FormatInfo getFormatInfo(CourseOfferingEditWrapper courseOfferingEditWrapper, String coFormId ){
        List<FormatInfo> formatInfoList = courseOfferingEditWrapper.getCourse().getFormats();
        for(FormatInfo formatInfo : formatInfoList) {
            if(coFormId.equals(formatInfo.getId())){
                return formatInfo;
            }
        }
        return null;
    }

    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }

    protected LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lrc", "LrcService"));
        }
        return this.lrcService;
    }

    protected AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }

    protected TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }
    protected CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }

}