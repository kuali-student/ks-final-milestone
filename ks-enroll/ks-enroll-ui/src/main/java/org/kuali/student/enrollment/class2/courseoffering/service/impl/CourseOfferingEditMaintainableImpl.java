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
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OrganizationInfoWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.constants.StateServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.common.state.service.StateService;
import org.kuali.student.r2.common.type.service.TypeService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditMaintainableImpl extends MaintainableImpl {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingEditMaintainableImpl.class);

    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;
    private transient TypeService typeService;
    private transient StateService stateService;
    private transient CourseService courseService;
    private transient OrganizationService organizationService;
    private transient LRCService lrcService;
    private transient AcademicCalendarService acalService;

    //TODO : implement the functionality for Personnel section and its been delayed now since the backend implementation is not yet ready (06/06/2012).

    @Override
    public void saveDataObject() {
        if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)) {
            CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)getDataObject(); 
            updateCourseOffering(coEditWrapper);
        }
        else{//for new and copy action, report error
             System.out.println(">>>Do not support!");
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

            CourseOfferingInfo coInfo = coEditWrapper.getCoInfo();
            coInfo.setUnitsDeploymentOrgIds(unitDeploymentOrgIds);

            // Credit Options (also creates extra-line)
            if (coEditWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED) &&
                    !coEditWrapper.getCreditOption().getFixedCredit().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = getLrcService().getCreateFixedCreditResultValuesGroup(coEditWrapper.getCreditOption().getFixedCredit(),
                        LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, getContextInfo());
                coInfo.setCreditOptionId(rvgInfo.getKey());
            } else if (coEditWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE) &&
                    !coEditWrapper.getCreditOption().getMinCredits().isEmpty() && !coEditWrapper.getCreditOption().getMaxCredits().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = getLrcService().getCreateRangeCreditResultValuesGroup(coEditWrapper.getCreditOption().getMinCredits(),
                        coEditWrapper.getCreditOption().getMaxCredits(), "1", LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, getContextInfo());
                coInfo.setCreditOptionId(rvgInfo.getKey());
            } else if (coEditWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE) &&
                    !coEditWrapper.getCreditOption().getCredits().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = getLrcService().getCreateMultipleCreditResultValuesGroup(coEditWrapper.getCreditOption().getCredits(),
                        LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, getContextInfo());
                coInfo.setCreditOptionId(rvgInfo.getKey());
            }

            // CO code
            String courseOfferingCode = coEditWrapper.getCourse().getCode();
            if (!StringUtils.isEmpty(coInfo.getCourseNumberSuffix())) {
                courseOfferingCode += coInfo.getCourseNumberSuffix();
            }
            coInfo.setCourseOfferingCode(courseOfferingCode);

            // Waitlist
            if (!coInfo.getHasWaitlist()) {
                coInfo.setWaitlistTypeKey(null);
                coInfo.setWaitlistLevelTypeKey(null);
            }

            getCourseOfferingService().updateCourseOffering(coInfo.getId(), coInfo, getContextInfo());
        }   catch (Exception ex){
            throw new RuntimeException(ex);
        }

    }

    private void updateFormatOfferings(CourseOfferingEditWrapper coEditWrapper) throws Exception{
        List<FormatOfferingInfo> updatedFormatOfferingList = new ArrayList<FormatOfferingInfo>();
        List<FormatOfferingInfo> formatOfferingList = coEditWrapper.getFormatOfferingList();
        CourseOfferingInfo coInfo = coEditWrapper.getCoInfo();
        List <String> currentFOIds = getExistingFormatOfferingIds(coInfo.getId());
        if (formatOfferingList != null && !formatOfferingList.isEmpty())  {
            for(FormatOfferingInfo formatOfferingInfo : formatOfferingList){
                if(formatOfferingInfo.getId()!=null &&
                        !formatOfferingInfo.getId().isEmpty() &&
                        currentFOIds.contains(formatOfferingInfo.getId())) {
                    //update FO
                    if (coInfo.getFinalExamType() != null && !coInfo.getFinalExamType().equals("STANDARD")) {
                        formatOfferingInfo.setFinalExamLevelTypeKey(null);
                    }
                    FormatOfferingInfo updatedFormatOffering = getCourseOfferingService().
                            updateFormatOffering(formatOfferingInfo.getId(),formatOfferingInfo, getContextInfo());
                    updatedFormatOfferingList.add(updatedFormatOffering);
                    currentFOIds.remove(formatOfferingInfo.getId());
                }
                else{
                    //create a new FO
                    formatOfferingInfo.setStateKey(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
                    formatOfferingInfo.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
                    formatOfferingInfo.setTermId(coInfo.getTermId());
                    formatOfferingInfo.setCourseOfferingId(coInfo.getId());
                    if (coInfo.getFinalExamType() != null && !coInfo.getFinalExamType().equals("STANDARD")) {
                        formatOfferingInfo.setFinalExamLevelTypeKey(null);
                    }
                    FormatOfferingInfo createdFormatOffering = getCourseOfferingService().
                            createFormatOffering(coInfo.getId(), formatOfferingInfo.getFormatId(), formatOfferingInfo.getTypeKey(), formatOfferingInfo, getContextInfo());
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
                getCourseOfferingService().deleteFormatOfferingCascaded(formatOfferingId, getContextInfo());
            }
        }
    }

    private List<String> getExistingFormatOfferingIds(String courseOfferingId) throws Exception{
        List<FormatOfferingInfo> formatOfferingInfoList = getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, getContextInfo());
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
            MaintenanceForm form = (MaintenanceForm)model;
            CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            List<OfferingInstructorWrapper> instructors = coEditWrapper.getInstructors();
            if(instructors != null && !instructors.isEmpty()){
                for(OfferingInstructorWrapper thisInst : instructors){
                    if(instructorInfo.getPersonId().equals(thisInst.getOfferingInstructorInfo().getPersonId())){
                        GlobalVariables.getMessageMap().putErrorForSectionId("KS-CourseOfferingEdit-PersonnelSection", ActivityOfferingConstants.MSG_ERROR_INSTRUCTOR_DUPLICATE, instructorInfo.getPersonId());
                        return false;
                    }
                }
            }

            //validate ID
            List<Person> lstPerson = ViewHelperUtil.getInstructorByPersonId(instructorInfo.getPersonId());
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
            MaintenanceForm form = (MaintenanceForm)model;
            CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            FormatInfo theFormat = getFormatInfo(coEditWrapper, formatId);
            // TODO: fix R2 Format to include name and short name
            newLine.setName("FIX ME!");
            newLine.setShortName("FIX ME!");
        }
    }

    @Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if(addLine instanceof OfferingInstructorInfo) {
            // set the person name if it's null, in the case of user-input personell id
            OfferingInstructorInfo instructorInfo = (OfferingInstructorInfo)addLine;
            if(instructorInfo.getPersonName() == null && instructorInfo.getPersonId() != null) {
                List<Person> personList = ViewHelperUtil.getInstructorByPersonId(instructorInfo.getPersonId());
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
            if (getDataObject() instanceof CourseOfferingEditWrapper){
                //0. get credit count from CourseInfo
                CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(dataObjectKeys.get("coInfo.id"), getContextInfo());
                CourseInfo courseInfo = (CourseInfo) getCourseService().getCourse(coInfo.getCourseId(), getContextInfo());
                coInfo.setCreditCnt(ViewHelperUtil.getCreditCount(coInfo, courseInfo)); //set for CO title

                //1. set CourseOfferingInfo
                CourseOfferingEditWrapper formObject = new CourseOfferingEditWrapper(coInfo);

                //2. set CourseInfo
                formObject.setCourse(courseInfo);

                //3. set formatOfferingList
                List<FormatOfferingInfo> formatOfferingList = getCourseOfferingService().getFormatOfferingsByCourseOffering(coInfo.getId(), getContextInfo());
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

                formObject.setStudentRegOptions(studentRegOptions);
                formObject.setCrsGradingOptions(crsGradingOptions);

                //6. Defining Credit Option and if CLU is fixed (then it's disabled)
                boolean creditOptionFixed = false;
                String creditOptionId = coInfo.getCreditOptionId();

                CreditOptionInfo creditOption = new CreditOptionInfo();

                //Grab the Course's credit constraints
                List<ResultValuesGroupInfo> courseCreditOptions = courseInfo.getCreditOptions();

                //Lookup the related course's credit constraints and set them on the creditOption
                if (coInfo.getCourseId() != null && courseInfo != null && !courseCreditOptions.isEmpty()) {
                    ResultValuesGroupInfo resultValuesGroupInfo = courseCreditOptions.get(0);
                    //Check for fixed
                    if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED)) {
                        if (!resultValuesGroupInfo.getResultValueKeys().isEmpty()) {
                            creditOption.setCourseFixedCredits(getLrcService().getResultValue(resultValuesGroupInfo.getResultValueKeys().get(0), contextInfo).getValue());
                        }
                        //Set the flag
                        creditOptionFixed = true;

                        //Default the value
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
                        creditOption.setFixedCredit(creditOption.getCourseFixedCredits());
                    } else {
                        //This is either range or multiple

                        //Copy all the allowed credits and sort so that the multiple checkboxes can be properly displayed
                        List<ResultValueInfo> resultValueInfos = getLrcService().getResultValuesForResultValuesGroup(resultValuesGroupInfo.getKey(), contextInfo);
                        for (ResultValueInfo rVI: resultValueInfos) {
                            creditOption.getAllowedCredits().add(rVI.getValue());
                        }
                        Collections.sort(creditOption.getAllowedCredits());

                        if (resultValuesGroupInfo.getType().equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE)) {
                            creditOption.setCourseMinCredits(resultValuesGroupInfo.getAttributeValue(LrcServiceConstants.R1_DYN_ATTR_CREDIT_OPTION_MIN_CREDITS));
                            creditOption.setCourseMaxCredits(resultValuesGroupInfo.getAttributeValue(LrcServiceConstants.R1_DYN_ATTR_CREDIT_OPTION_MAX_CREDITS));

                            //Default the value
                            creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
                            creditOption.setMinCredits(creditOption.getCourseMinCredits());
                            creditOption.setMaxCredits(creditOption.getCourseMaxCredits());
                        } else if (resultValuesGroupInfo.getType().equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE)) {
                            //Default the value
                            creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
                            creditOption.getCredits().addAll(creditOption.getAllowedCredits());
                        }
                    }
                }

                //Lookup the selected credit option and set from persisted values
                if (creditOptionId != null) {
                    //Lookup the resultValueGroup Information
                    ResultValuesGroupInfo resultValuesGroupInfo = getLrcService().getResultValuesGroup(creditOptionId, getContextInfo());
                    String typeKey = resultValuesGroupInfo.getTypeKey();

                    //Get the actual values
                    List<ResultValueInfo> resultValueInfos = getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), getContextInfo());

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
                        OrgInfo orgInfo = getOrganizationService().getOrg(orgId,getContextInfo());
                        orgList.add(new OrganizationInfoWrapper(orgInfo));
                    }
                }
                formObject.setOrganizationNames(orgList);

                // Setting term string: Fall 2012 (09/28/2012 to 12/15/2012)
                TermInfo termInfo = getAcalService().getTerm(coInfo.getTermId(), getContextInfo());
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                StringBuilder termStartDate = new StringBuilder(dateFormat.format(termInfo.getStartDate()));
                StringBuilder termEndDate = new StringBuilder(dateFormat.format(termInfo.getEndDate()));
                String termStartEnd = termInfo.getName() + " (" + termStartDate + " to " +termEndDate + ")";
                formObject.setTermStartEnd(termStartEnd);

                document.getNewMaintainableObject().setDataObject(formObject);
                document.getOldMaintainableObject().setDataObject(formObject);
                document.getDocumentHeader().setDocumentDescription("Edit CO - " + coInfo.getCourseOfferingCode());

                //            StateInfo state = getStateService().getState(formObject.getDto().getStateKey(), getContextInfo());
    //            formObject.setStateName(state.getName());
                return formObject;
            }
        } catch (Exception e) {
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

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = new ContextInfo();
            contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            LocaleInfo localeInfo = new LocaleInfo();
            localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
            localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
            contextInfo.setLocale(localeInfo);
        }
        return contextInfo;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public StateService getStateService() {
        if(stateService == null) {
            stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, "CourseOfferingService"));
        }
        return courseOfferingService;
    }

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, "CourseService"));
        }
        return this.courseService;
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

}