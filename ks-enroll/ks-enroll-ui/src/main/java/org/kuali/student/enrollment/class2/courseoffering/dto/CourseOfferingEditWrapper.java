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
 * Created by vgadiyak on 5/25/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.lum.course.dto.CourseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditWrapper implements Serializable {

    private CourseOfferingInfo coInfo;
    private List<FormatOfferingWrapper> formatOfferingWrapperList;
    private List<String> studentRegOptions;
    private List<OrganizationInfoWrapper> organizationNames;
    private CourseInfo course;

    private String selectedGradingOptionName;
    private String selectedstudentRegOpts;

    private CreditOptionInfo creditOption;
    private boolean creditOptionFixed;

    public CourseOfferingEditWrapper(){
        coInfo = new CourseOfferingInfo();
        formatOfferingWrapperList = new ArrayList<FormatOfferingWrapper>();
        studentRegOptions = new ArrayList<String>();
    }

    public CourseOfferingEditWrapper(CourseOfferingInfo info){
        super();
        coInfo = info;
    }

    public CourseOfferingInfo getCoInfo() {
        return coInfo;
    }

    public void setCoInfo(CourseOfferingInfo coInfo) {
        this.coInfo = coInfo;
    }

    public List<FormatOfferingWrapper> getFormatOfferingWrapperList() {
        return formatOfferingWrapperList;
    }

    public void setFormatOfferingWrapperList(List<FormatOfferingWrapper> formatOfferingWrapperList) {
        if (formatOfferingWrapperList == null) {
            formatOfferingWrapperList = new ArrayList<FormatOfferingWrapper>();
        }
        this.formatOfferingWrapperList = formatOfferingWrapperList;
    }

    public List<String> getStudentRegOptions() {
        return studentRegOptions;
    }

    public void setStudentRegOptions(List<String> studentRegOptions) {
        if (studentRegOptions == null) {
            studentRegOptions = new ArrayList<String>();
        }
        this.studentRegOptions = studentRegOptions;
    }

    public List<OrganizationInfoWrapper> getOrganizationNames() {
        return organizationNames;
    }

    public void setOrganizationNames(List<OrganizationInfoWrapper> organizationNames) {
        this.organizationNames = organizationNames;
    }


    public String getSelectedGradingOptionName() {
        return selectedGradingOptionName;
    }

    public void setSelectedGradingOptionName(String selectedGradingOptionName) {
        this.selectedGradingOptionName = selectedGradingOptionName;
    }

    public String getSelectedstudentRegOpts() {
        return selectedstudentRegOpts;
    }

    public void setSelectedstudentRegOpts(String selectedstudentRegOpts) {
        this.selectedstudentRegOpts = selectedstudentRegOpts;
    }

    public CourseInfo getCourse() {
        return course;
    }

    public void setCourse(CourseInfo course) {
        this.course = course;
    }

    public CreditOptionInfo getCreditOption() {
        return creditOption;
    }

    public void setCreditOption(CreditOptionInfo creditOption) {
        this.creditOption = creditOption;
    }

    public boolean getCreditOptionFixed() {
        return creditOptionFixed;
    }

    public void setCreditOptionFixed(boolean creditOptionFixed) {
        this.creditOptionFixed = creditOptionFixed;
    }
}

