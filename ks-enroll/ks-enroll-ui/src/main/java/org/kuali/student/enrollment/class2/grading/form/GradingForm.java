package org.kuali.student.enrollment.class2.grading.form;

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

import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;

import java.util.List;
import org.kuali.rice.krad.web.form.UifFormBase;

public class GradingForm extends UifFormBase{

    private static final long serialVersionUID = -1054046347823986329L;

    private String selectedCourse;
    private List<GradeStudent> students;

    public GradingForm(){
        super();
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public List<GradeStudent> getStudents() {
        return students;
    }

    public void setStudents(List<GradeStudent> students) {
        this.students = students;
    }

}
