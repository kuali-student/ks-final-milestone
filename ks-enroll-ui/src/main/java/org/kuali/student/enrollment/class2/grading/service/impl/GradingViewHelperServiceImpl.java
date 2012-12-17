package org.kuali.student.enrollment.class2.grading.service.impl;

/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.infc.StudentCourseRecord;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;
import org.kuali.student.enrollment.class2.grading.dataobject.StudentCredit;
import org.kuali.student.enrollment.class2.grading.form.GradingForm;
import org.kuali.student.enrollment.class2.grading.form.StudentGradeForm;
import org.kuali.student.enrollment.class2.grading.service.GradingViewHelperService;
import org.kuali.student.enrollment.class2.grading.util.GradingConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.dto.GradeValuesGroupInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.*;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.mock.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Core slice classes, just still around for reference.. needs cleanup
@Deprecated
public class GradingViewHelperServiceImpl extends ViewHelperServiceImpl implements GradingViewHelperService {

    private AcademicCalendarService acalService;
    private CourseOfferingService coService;
    private GradingService gradingService;
    private AcademicRecordService academicRecordService;

    @Override
    public void populateGradeOptions(InputField field, GradingForm gradingForm) {
        List keyValues = new ArrayList();
        keyValues.add(new ConcreteKeyValue("", ""));

        if (field.getContext().get(UifConstants.ContextVariableNames.LINE) != null
                && field.getControl() instanceof SelectControl) {
            GradeStudent student = (GradeStudent) field.getContext().get(UifConstants.ContextVariableNames.LINE);
            for (ResultValueInfo option : student.getAvailabeGradingOptions()) {
                keyValues.add(new ConcreteKeyValue(option.getKey(), option.getValue()));
            }
            ((SelectControl) field.getControl()).setOptions(keyValues);
        }

    }

    @Override
    public void unAssignGrade(View view, Object model, String selectedCollectionPath, Integer selectedLine) {
        //CollectionGroup collectionGroup = view.getViewIndex().getCollectionGroupByPath(selectedCollectionPath);

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(model, selectedCollectionPath);
        if (collection == null) {
            throw new RuntimeException("Unable to get collection property from model for path: "
                    + selectedCollectionPath);
        }

        GradeStudent student = (GradeStudent) ((List<Object>) collection).get(selectedLine);
        student.setSelectedGrade("");
    }

    @Override
    public List<GradeStudent> loadStudents(String selectedCourse,GradingForm gradingForm) throws Exception {

        ContextInfo context = TestHelper.getContext1();

        IdentityService identityService = (IdentityService) GlobalResourceLoader.getService(new QName(
                GradingConstants.IDENTITY_SERVICE_URL, GradingConstants.IDENTITY_SERVICE_NAME));

        List<GradeStudent> students = new ArrayList();
        List<GradeRosterInfo> rosterInfos = getGradingService().getFinalGradeRostersForCourseOffering(selectedCourse, context);
        gradingForm.setRosterInfos(rosterInfos);

        if (rosterInfos != null) {
            for (GradeRosterInfo rosterInfo : rosterInfos) {
                if (StringUtils.equals(LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_SUBMITTED_STATE_KEY,rosterInfo.getStateKey())){
                    gradingForm.setReadOnly(true);
                }

                if (rosterInfo.getGradeRosterEntryIds().isEmpty()){
                    return students;
                }

                List<GradeRosterEntryInfo> entryInfos = getGradingService().getGradeRosterEntriesByIds(rosterInfo.getGradeRosterEntryIds(), context);

                if (!entryInfos.isEmpty() && entryInfos.get(0).getValidGradeGroupKeys().isEmpty()){
                    GlobalVariables.getMessageMap().putWarning("selectedGrade",GradingConstants.WARNING_GRADING_OPTIONS_NOT_FOUND,"test");
                    gradingForm.setReadOnly(true);
                }

                for (GradeRosterEntryInfo entryInfo : entryInfos) {
                    GradeStudent student = new GradeStudent();
                    student.setGradeRosterEntryInfo(entryInfo);
                    student.setStudentId(entryInfo.getStudentId());
                    Entity entityInfo = identityService.getEntityByPrincipalId(entryInfo.getStudentId());
                    List<EntityName> entityNameInfos = entityInfo.getNames();
                    for (EntityName entityNameInfo : entityNameInfos) {
                        if (entityNameInfo.isDefaultValue()) {
                            student.setFirstName(entityNameInfo.getFirstNameUnmasked());
                            student.setLastName(entityNameInfo.getLastNameUnmasked());
                        }
                    }

                    List<String> validGradeGroupKeys = entryInfo.getValidGradeGroupKeys();

                    List<GradeValuesGroupInfo> gradeValueInfos = getGradingService().getGradeGroupsByKeyList(validGradeGroupKeys, context);
                    student.setGradeValuesGroupInfoList(gradeValueInfos);

                    for (GradeValuesGroupInfo grade : gradeValueInfos) {
                        if (grade.getResultValueInfos() != null && !grade.getResultValueInfos().isEmpty()) {
                            student.getAvailabeGradingOptions().addAll(grade.getResultValueInfos());
                            student.setPercentGrade(false);
                        } else if (grade.getResultValueRange() != null) {
                            student.setPercentGrade(true);
                        }
                    }

                    if (StringUtils.isNotBlank(entryInfo.getAssignedGradeKey())) {
                        student.setSelectedGrade(entryInfo.getAssignedGradeKey());
                    }

                    students.add(student);
                }
            }
        }

        return students;
    }

    public boolean saveGrades(GradingForm gradingForm)throws Exception {

        ContextInfo context = TestHelper.getContext1();

        List<GradeStudent> gradeStudentList = gradingForm.getStudents();
        boolean updateRoster = false;
        for (GradeStudent gradeStudent : gradeStudentList) {
            GradeRosterEntryInfo gradeRosterEntryInfo = gradeStudent.getGradeRosterEntryInfo();
            String assignedGradeKey = gradeStudent.getSelectedGrade();
            boolean returnValue = getGradingService().updateGrade(gradeRosterEntryInfo.getId(), assignedGradeKey, context);
            if (returnValue){
                updateRoster = true;
            }
        }

        if (updateRoster){
            for (GradeRosterInfo info : gradingForm.getRosterInfos()){
                getGradingService().updateFinalGradeRosterState(info.getId(), LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_SAVED_STATE_KEY,context);
            }
            return true;
        }

        return false;
    }

    public boolean submitGradeRoster(GradingForm gradingForm)throws Exception {
        //boolean save = saveGrades(gradingForm);
        ContextInfo context = TestHelper.getContext1();

        for (GradeRosterInfo info : gradingForm.getRosterInfos()){
            getGradingService().updateFinalGradeRosterState(info.getId(), LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_SUBMITTED_STATE_KEY,context);
        }

        return true;
    }

    public void loadStudentGrades(StudentGradeForm studentGradeForm)
    throws Exception {

        List creditList = new ArrayList();

        ContextInfo context = new ContextInfo();

        TermInfo term = getAcalService().getTerm(studentGradeForm.getSelectedTerm(), context);

        List<StudentCourseRecordInfo> courseRecords = getAcademicRecordService().getCompletedCourseRecordsForTerm(context.getPrincipalId(), term.getId(), context);
        if (null != courseRecords) {
            for (StudentCourseRecord courseRecord : courseRecords) {
                StudentCredit credit = new StudentCredit();
                // TODO - is this correct?
                credit.setCourseId(courseRecord.getCourseCode());
                // TODO - is this correct?
                credit.setCourseName(courseRecord.getCourseTitle());

                credit.setGrade(courseRecord.getAssignedGradeValue());
                credit.setCredits(courseRecord.getCreditsEarned());
                creditList.add(credit);
            }
        }

        studentGradeForm.setCreditList(creditList);
        studentGradeForm.setTitle(term.getName() + " Grades");
    }

    private TermInfo getCurrentACal(){
        ContextInfo context = new ContextInfo();

        try{
                return getAcalService().getCurrentTerms(null,context).get(0);
        } catch (DoesNotExistException e) {
            throw new RuntimeException("No Terms found for current AcademicCalendar(s)! There should be some in the database.", e);
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadCourses(GradingForm form)throws Exception{

        ContextInfo context = new ContextInfo();

        TermInfo term = getAcalService().getTerm(form.getSelectedTerm(), context);

        if (term == null){
            throw new RuntimeException("No record found for the selected term");
        }

        form.setSelectedTerm(term.getName());

//        List<CourseOfferingInfo> courseOfferingInfoList = new ArrayList<CourseOfferingInfo>();

        try{
            List<CourseOfferingInfo> cos = getCOService().getCourseOfferingsByTermAndInstructor(term.getId(), context.getPrincipalId(), context);

            if (cos == null || cos.isEmpty()){
                GlobalVariables.getMessageMap().putInfo("firstName",GradingConstants.INFO_COURSE_NOT_FOUND_TO_GRADE,term.getName());
                return;
            }

            form.setCourseOfferingInfoList(new ArrayList<CourseOfferingInfo>());
            if (!cos.isEmpty()){
                for (CourseOfferingInfo co : cos) {
                    if (StringUtils.equals(co.getStateKey(), LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY) &&
                        StringUtils.equals(co.getTypeKey(),LuiServiceConstants.COURSE_OFFERING_TYPE_KEY)){
                        form.getCourseOfferingInfoList().add(co);
                    }
                }
            }
        }catch(Exception e){
            //FIXME: Change it to use proper error handling
            throw new RuntimeException(e);
        }
    }

    protected AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

    protected CourseOfferingService getCOService(){
        if (coService == null){
            coService = (CourseOfferingService)GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    protected GradingService getGradingService() {
        if (gradingService == null){
            gradingService = (GradingService) GlobalResourceLoader.getService(new QName(GradingServiceConstants.NAMESPACE, GradingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return gradingService;
    }

    protected AcademicRecordService getAcademicRecordService() {
        if (academicRecordService == null){
            academicRecordService = (AcademicRecordService) GlobalResourceLoader.getService(new QName(AcademicRecordServiceConstants.NAMESPACE, AcademicRecordServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return academicRecordService;
    }
}
