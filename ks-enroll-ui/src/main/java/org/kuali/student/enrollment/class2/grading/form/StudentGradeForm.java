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

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.grading.dataobject.StudentCredit;

import java.util.ArrayList;
import java.util.List;

//Core slice classes, just still around for reference.. needs cleanup
@Deprecated
public class StudentGradeForm extends UifFormBase {

    private static final long serialVersionUID = -1054046347823982329L;

    private String name;
    private String firstTerm;
    private List<StudentCredit> creditList;

    private String selectedTerm;

    private String title = "Term Record";

    public StudentGradeForm(){
        creditList = new ArrayList<StudentCredit>();
    }

    public List<StudentCredit> getCreditList() {
        return creditList;
    }

    public void setCreditList(List<StudentCredit> creditList) {
        this.creditList = creditList;
    }

    public String getFirstTerm() {
        return firstTerm;
    }

    public void setFirstTerm(String firstTerm) {
        this.firstTerm = firstTerm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelectedTerm() {
        return selectedTerm;
    }

    public void setSelectedTerm(String selectedTerm) {
        this.selectedTerm = selectedTerm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
