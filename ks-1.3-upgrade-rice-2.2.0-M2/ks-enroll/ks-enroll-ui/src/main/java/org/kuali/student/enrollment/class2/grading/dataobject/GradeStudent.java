package org.kuali.student.enrollment.class2.grading.dataobject;

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

import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GradeStudent implements Serializable {

    private String studentId;
    private String firstName;
    private String lastName;

    private String selectedGrade;

    private boolean percentGrade;

    private List<ResultValueInfo> availabeGradingOptions;

    private List<GradeValuesGroupInfo> gradeValuesGroupInfoList;

    private GradeRosterEntryInfo gradeRosterEntryInfo;

    public GradeStudent() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<ResultValueInfo> getAvailabeGradingOptions() {
        if (availabeGradingOptions == null) {
            availabeGradingOptions = new ArrayList();
        }
        return availabeGradingOptions;
    }

    public void setAvailabeGradingOptions(List<ResultValueInfo> availabeGradingOptions) {
        this.availabeGradingOptions = availabeGradingOptions;
    }

    public String getSelectedGrade() {
        return selectedGrade;
    }

    public void setSelectedGrade(String selectedGrade) {
        this.selectedGrade = selectedGrade;
    }

    public boolean isPercentGrade() {
        return percentGrade;
    }

    public void setPercentGrade(boolean percentGrade) {
        this.percentGrade = percentGrade;
    }

    public List<GradeValuesGroupInfo> getGradeValuesGroupInfoList() {
        return gradeValuesGroupInfoList;
    }

    public void setGradeValuesGroupInfoList(List<GradeValuesGroupInfo> resultValuesGroupInfoList) {
        this.gradeValuesGroupInfoList = resultValuesGroupInfoList;
    }

    public GradeRosterEntryInfo getGradeRosterEntryInfo() {
        return gradeRosterEntryInfo;
    }

    public void setGradeRosterEntryInfo(GradeRosterEntryInfo gradeRosterEntryInfo) {
        this.gradeRosterEntryInfo = gradeRosterEntryInfo;
    }
}
