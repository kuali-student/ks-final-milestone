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
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.field.AttributeField;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;
import org.kuali.student.enrollment.class2.grading.form.GradingForm;
import org.kuali.student.enrollment.class2.grading.service.GradingViewHelperService;
import org.kuali.student.enrollment.class2.grading.util.GradingConstants;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.dto.GradeValuesGroupInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.test.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GradingViewHelperServiceImpl extends ViewHelperServiceImpl implements GradingViewHelperService {

    private AcademicCalendarService acalService;
    private CourseOfferingService coService;

    @Override
    public void populateGradeOptions(AttributeField field, GradingForm gradingForm) {
        List keyValues = new ArrayList();
        keyValues.add(new ConcreteKeyValue("", ""));

        if (field.getContext().get(UifConstants.ContextVariableNames.LINE) != null
                && field.getControl() instanceof SelectControl) {
            GradeStudent student = (GradeStudent) field.getContext().get(UifConstants.ContextVariableNames.LINE);
            for (ResultValueInfo option : student.getAvailabeGradingOptions()) {
                keyValues.add(new ConcreteKeyValue(option.getValue(), option.getValue()));
            }
            ((SelectControl) field.getControl()).setOptions(keyValues);
        }

    }

    @Override
    public void unAssignGrade(View view, Object model, String selectedCollectionPath, Integer selectedLine) {
        CollectionGroup collectionGroup = view.getViewIndex().getCollectionGroupByPath(selectedCollectionPath);

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

        GradingService gradingService = (GradingService) GlobalResourceLoader.getService(new QName(
                GradingConstants.GRADING_SERVICE_URL, GradingConstants.GRADING_SERVICE_NAME));
        IdentityService identityService = (IdentityService) GlobalResourceLoader.getService(new QName(
                GradingConstants.IDENTITY_SERVICE_URL, GradingConstants.IDENTITY_SERVICE_NAME));

        List<GradeStudent> students = new ArrayList();
        List<GradeRosterInfo> rosterInfos = gradingService.getFinalGradeRostersForCourseOffering(selectedCourse,
                context);
        if (rosterInfos != null) {
            for (GradeRosterInfo rosterInfo : rosterInfos) {
                if (StringUtils.equals("kuali.assessment.roster.state.ready",rosterInfo.getStateKey()) ||
                    StringUtils.equals("kuali.assessment.roster.state.saved",rosterInfo.getStateKey())){
                    gradingForm.setSubmitEnabled(true);
                    gradingForm.setSaveEnabled(true);
                }
                List<GradeRosterEntryInfo> entryInfos = gradingService.getGradeRosterEntriesByIdList(
                        rosterInfo.getGradeRosterEntryIds(), context);
                int i = 0;
                for (GradeRosterEntryInfo entryInfo : entryInfos) {
                    GradeStudent student = new GradeStudent();
                    //FIXME:Temp flag set for testing, have to delete
                    student.setPercentGrade(true);

                    student.setStudentId(entryInfo.getStudentId());
                    Entity entityInfo = identityService.getEntityByPrincipalId(entryInfo.getStudentId());
                    List<EntityName> entityNameInfos = entityInfo.getNames();
                    for (EntityName entityNameInfo : entityNameInfos) {
                        if (entityNameInfo.isDefaultValue()) {
                            student.setFirstName(entityNameInfo.getFirstNameUnmasked());
                            student.setLastName(entityNameInfo.getLastNameUnmasked());
                        }
                    }
                    /*List<String> grades = gradingService.getValidGradeGroupKeysForStudentByRoster(
                            entryInfo.getStudentId(), rosterInfo.getId(), context);

                    List<GradeValuesGroupInfo> gradeValueInfos = gradingService.getGradeGroupsByKeyList(grades, context);
                    student.setGradeValuesGroupInfoList(gradeValueInfos);

                    for (GradeValuesGroupInfo grade : gradeValueInfos) {
                        if (grade.getResultValueInfos() != null && !grade.getResultValueInfos().isEmpty()) {
                            student.getAvailabeGradingOptions().addAll(grade.getResultValueInfos());
                            student.setPercentGrade(true);
                        } else if (grade.getResultValueRange() != null) {
                            student.setPercentGrade(false);
                            // Populate the range info to the form.
                        }
                    }*/

                    /*String assignedGrade = null;
                    String assignedGradeKey = entryInfo.getAssignedGradeKey();

                    // TODO change key to actual value - need to call Grading
                    // Service getGrade method
                    if (assignedGradeKey != null) {
                        assignedGrade = assignedGradeKey;

                    }
                    student.setSelectedGrade(assignedGrade);*/

                    students.add(student);
                }
            }
        }

        return students;
    }

    private TermInfo getCurrentACal(){
        ContextInfo context = ContextInfo.newInstance();

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

        ContextInfo context = ContextInfo.newInstance();

        TermInfo term = getAcalService().getTerm(form.getSelectedTerm(), context);

        if (term == null){
            throw new RuntimeException("No record found for the selected term");
        }

        form.setSelectedTerm(term.getName());

        List<CourseOfferingInfo> courseOfferingInfoList = new ArrayList<CourseOfferingInfo>();

        try{
            List<String> coIds = getCOService().getCourseOfferingIdsByTermAndInstructorId(term.getKey(), context.getPrincipalId(), context);

            if (coIds == null || coIds.isEmpty()){
                GlobalVariables.getMessageMap().putInfo("firstName",GradingConstants.INFO_COURSE_NOT_FOUND_TO_GRADE,term.getName());
                return;
            }

            form.setCourseOfferingInfoList(new ArrayList<CourseOfferingInfo>());
            if (!coIds.isEmpty()){
                courseOfferingInfoList = getCOService().getCourseOfferingsByIdList(coIds, context);
                for (CourseOfferingInfo co : courseOfferingInfoList) {
                    if (StringUtils.equals(co.getStateKey(), LuiServiceConstants.LUI_OFFERED_STATE_KEY) &&
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
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }

    protected CourseOfferingService getCOService(){
        if (coService == null){
            coService = (CourseOfferingService)GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseOffering", "coService"));
        }
        return coService;
    }
}
