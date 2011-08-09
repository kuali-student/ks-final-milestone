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
import org.kuali.rice.core.util.ConcreteKeyValue;
import org.kuali.rice.kim.api.entity.services.IdentityService;
import org.kuali.rice.kim.bo.entity.dto.KimEntityInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityNameInfo;
import org.kuali.rice.kns.uif.UifConstants;
import org.kuali.rice.kns.uif.container.CollectionGroup;
import org.kuali.rice.kns.uif.container.View;
import org.kuali.rice.kns.uif.control.SelectControl;
import org.kuali.rice.kns.uif.core.Component;
import org.kuali.rice.kns.uif.field.AttributeField;
import org.kuali.rice.kns.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.kns.uif.util.ObjectPropertyUtils;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;
import org.kuali.student.enrollment.class2.grading.service.GradingViewHelperService;
import org.kuali.student.enrollment.class2.grading.util.GradingConstants;
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

public class GradingViewHelperServiceImpl extends ViewHelperServiceImpl implements GradingViewHelperService{

    public void populateGradeOptions(Component component){

        AttributeField field = (AttributeField)component;
        List keyValues = new ArrayList();
        keyValues.add(new ConcreteKeyValue("", ""));

        if (component.getContext().get(UifConstants.ContextVariableNames.LINE) != null){
            GradeStudent student = (GradeStudent)component.getContext().get(UifConstants.ContextVariableNames.LINE);
            for (String option : student.getAvailabeGradingOptions()){
                keyValues.add(new ConcreteKeyValue(option,option));
            }
            ((SelectControl)field.getControl()).setOptions(keyValues);
        }

    }

    public void unAssignGrade(View view,Object model,String selectedCollectionPath, Integer selectedLine ){
         CollectionGroup collectionGroup = view.getViewIndex().getCollectionGroupByPath(selectedCollectionPath);

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(model, selectedCollectionPath);
        if (collection == null) {
            throw new RuntimeException("Unable to get collection property from model for path: " + selectedCollectionPath);
        }

        GradeStudent student = (GradeStudent)((List<Object>) collection).get(selectedLine);
        student.setSelectedGrade("");
    }

    public List<GradeStudent> loadStudents(String selectedCourse)
    throws  Exception {

       ContextInfo context = TestHelper.getContext1();

       GradingService gradingService = (GradingService) GlobalResourceLoader.getService(new QName(GradingConstants.GRADING_SERVICE_URL, GradingConstants.GRADING_SERVICE_NAME));
       IdentityService identityService = (IdentityService) GlobalResourceLoader.getService(new QName("http://rice.kuali.org/wsdl/kim", "kimIdentityServiceSOAP"));

        List<GradeStudent> students = new ArrayList();
        List<GradeRosterInfo> rosterInfos = gradingService.getFinalGradeRostersForCourseOffering(selectedCourse, context);
        if (rosterInfos != null){
            for (GradeRosterInfo rosterInfo : rosterInfos){
                List<GradeRosterEntryInfo> entryInfos = gradingService.getGradeRosterEntriesByIdList(rosterInfo.getGradeRosterEntryIds(), context);
                for (GradeRosterEntryInfo entryInfo : entryInfos){
                    GradeStudent student = new GradeStudent();
                    student.setStudentId(entryInfo.getStudentId());
                    KimEntityInfo entityInfo = identityService.getEntityInfo(entryInfo.getStudentId());
                    List<KimEntityNameInfo> entityNameInfos = entityInfo.getNames();
                    for (KimEntityNameInfo entityNameInfo : entityNameInfos){
                        if (entityNameInfo.isDefaultValue()){
                            student.setFirstName(entityNameInfo.getFirstNameUnmasked());
                            student.setLastName(entityNameInfo.getLastNameUnmasked());
                        }
                    }
                    List<ResultValuesGroupInfo> grades = gradingService.getValidGradesForStudentByRoster(entryInfo.getStudentId(), rosterInfo.getId(), context);
                    for (ResultValuesGroupInfo grade : grades){
                        student.getAvailabeGradingOptions().addAll(grade.getResultValueIds());
                    }
                    students.add(student);
                }
            }
        }

        return students;
    }

}
