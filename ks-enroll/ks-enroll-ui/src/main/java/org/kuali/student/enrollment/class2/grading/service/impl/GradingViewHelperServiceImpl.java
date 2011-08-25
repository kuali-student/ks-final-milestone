package org.kuali.student.enrollment.class2.grading.service.impl;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;
import org.kuali.student.enrollment.class2.grading.form.GradingForm;
import org.kuali.student.enrollment.class2.grading.service.GradingViewHelperService;
import org.kuali.student.enrollment.class2.grading.util.GradingConstants;
import org.kuali.student.enrollment.grading.dto.AssignedGradeInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.test.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.View;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.core.Component;
import org.kuali.rice.krad.uif.field.AttributeField;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;

public class GradingViewHelperServiceImpl extends ViewHelperServiceImpl implements GradingViewHelperService{

    @Override
    public void populateGradeOptions(AttributeField field, GradingForm gradingForm){
        List keyValues = new ArrayList();
        keyValues.add(new ConcreteKeyValue("", ""));

        if (field.getContext().get(UifConstants.ContextVariableNames.LINE) != null && field.getControl() instanceof  SelectControl){
            GradeStudent student = (GradeStudent)field.getContext().get(UifConstants.ContextVariableNames.LINE);
            for (String option : student.getAvailabeGradingOptions()){
                keyValues.add(new ConcreteKeyValue(option,option));
            }
            ((SelectControl)field.getControl()).setOptions(keyValues);
        }

    }

    @Override
    public void unAssignGrade(View view,Object model,String selectedCollectionPath, Integer selectedLine ){
         CollectionGroup collectionGroup = view.getViewIndex().getCollectionGroupByPath(selectedCollectionPath);

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(model, selectedCollectionPath);
        if (collection == null) {
            throw new RuntimeException("Unable to get collection property from model for path: " + selectedCollectionPath);
        }

        GradeStudent student = (GradeStudent)((List<Object>) collection).get(selectedLine);
        student.setSelectedGrade("");
    }

    @Override
    public List<GradeStudent> loadStudents(String selectedCourse)
    throws  Exception {

       ContextInfo context = TestHelper.getContext1();

       GradingService gradingService = (GradingService) GlobalResourceLoader.getService(new QName(GradingConstants.GRADING_SERVICE_URL, GradingConstants.GRADING_SERVICE_NAME));
       IdentityService identityService = (IdentityService) GlobalResourceLoader.getService(new QName(GradingConstants.IDENTITY_SERVICE_URL, GradingConstants.IDENTITY_SERVICE_NAME));

        List<GradeStudent> students = new ArrayList();
        List<GradeRosterInfo> rosterInfos = gradingService.getFinalGradeRostersForCourseOffering(selectedCourse, context);
        if (rosterInfos != null){
            for (GradeRosterInfo rosterInfo : rosterInfos){
                List<GradeRosterEntryInfo> entryInfos = gradingService.getGradeRosterEntriesByIdList(rosterInfo.getGradeRosterEntryIds(), context);
                int i = 0;
                for (GradeRosterEntryInfo entryInfo : entryInfos){
                    GradeStudent student = new GradeStudent();
                    if ( i == 0 || i == 2){
                        student.setPercentGrade(true);
                    }
                    i++;
                    student.setStudentId(entryInfo.getStudentId());
                    Entity entityInfo = identityService.getEntity(entryInfo.getStudentId());
                    List<EntityName> entityNameInfos = entityInfo.getNames();
                    for (EntityName entityNameInfo : entityNameInfos){
                        if (entityNameInfo.isDefaultValue()){
                            student.setFirstName(entityNameInfo.getFirstNameUnmasked());
                            student.setLastName(entityNameInfo.getLastNameUnmasked());
                        }
                    }
                    List<ResultValuesGroupInfo> grades = gradingService.getValidGradesForStudentByRoster(entryInfo.getStudentId(), rosterInfo.getId(), context);
                    student.setResultValuesGroupInfoList(grades);

                    for (ResultValuesGroupInfo grade : grades){
                        if (grade.getResultValueIds() != null && !grade.getResultValueIds().isEmpty()){
                            student.getAvailabeGradingOptions().addAll(grade.getResultValueIds());
                            student.setPercentGrade(true);
                        }else if (grade.getResultValueRange() != null){
                            student.setPercentGrade(false);
                            //Populate the range info to the form.
                        }
                    }

                    String assignedGrade = null;
                    AssignedGradeInfo assignedGrageInfo = entryInfo.getAssignedGrade();

                    if (assignedGrageInfo != null) {
                        assignedGrade = assignedGrageInfo.getGrade();

                    }
                    student.setSelectedGrade(assignedGrade);

                    students.add(student);
                }
            }
        }

        return students;
    }

}
