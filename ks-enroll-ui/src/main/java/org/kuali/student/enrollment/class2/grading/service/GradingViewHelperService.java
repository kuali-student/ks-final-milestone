package org.kuali.student.enrollment.class2.grading.service;

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

import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;
import org.kuali.student.enrollment.class2.grading.form.GradingForm;
import org.kuali.student.enrollment.class2.grading.form.StudentGradeForm;

import java.util.List;

//Core slice classes, just still around for reference.. needs cleanup
@Deprecated
public interface GradingViewHelperService extends ViewHelperService{

    public void populateGradeOptions(InputField field, GradingForm gradingForm);

    public void unAssignGrade(View view,Object model,String selectedCollectionPath, Integer selectedLine );

    public List<GradeStudent> loadStudents(String selectedCourse,GradingForm gradingForm) throws  Exception;

    public boolean saveGrades(GradingForm gradingForm)throws Exception;

    public boolean submitGradeRoster(GradingForm gradingForm)throws Exception;

    public void loadStudentGrades(StudentGradeForm studentGradeForm) throws Exception;

    public void loadCourses(GradingForm gradingForm) throws Exception;

}
