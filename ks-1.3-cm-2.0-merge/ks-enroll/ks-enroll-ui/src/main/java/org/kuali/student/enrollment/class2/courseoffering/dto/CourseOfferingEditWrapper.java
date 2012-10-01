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

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

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
    private List<FormatOfferingInfo> formatOfferingList;
    private List<String> studentRegOptions;
    private List<String> crsGradingOptions;
    private List<OrganizationInfoWrapper> organizationNames;
    private List<OfferingInstructorWrapper> instructors;
    private CourseInfo course;
    private String termStartEnd;

    private String selectedGradingOptionName;
    private String selectedStudentRegOpts;

    private CreditOptionInfo creditOption;
    private boolean creditOptionFixed;
    private boolean isChecked;

    private String gradingOption;

    public CourseOfferingEditWrapper(){
        coInfo = new CourseOfferingInfo();
        formatOfferingList = new ArrayList<FormatOfferingInfo>();
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

    public List<FormatOfferingInfo> getFormatOfferingList() {
        return formatOfferingList;
    }

    public void setFormatOfferingList(List<FormatOfferingInfo> formatOfferingList) {
        if (formatOfferingList == null) {
            formatOfferingList = new ArrayList<FormatOfferingInfo>();
        }
        this.formatOfferingList = formatOfferingList;
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

    public List<String> getCrsGradingOptions() {
        if (crsGradingOptions == null) {
            crsGradingOptions = new ArrayList<String>();
        }
        return crsGradingOptions;
    }

    public void setCrsGradingOptions(List<String> crsGradingOptions) {
        this.crsGradingOptions = crsGradingOptions;
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

    public String getSelectedStudentRegOpts() {
        return selectedStudentRegOpts;
    }

    public void setSelectedStudentRegOpts(String selectedStudentRegOpts) {
        this.selectedStudentRegOpts = selectedStudentRegOpts;
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

    public String getTermStartEnd() {
        return termStartEnd;
    }

    public void setTermStartEnd(String termStartEnd) {
        this.termStartEnd = termStartEnd;
    }

    public List<OfferingInstructorWrapper> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<OfferingInstructorWrapper> instructors) {
        this.instructors = instructors;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        this.isChecked = checked;
    }

    public String getGradingOption() {
        return gradingOption;
    }

    public void setGradingOption(String gradingOption) {
        this.gradingOption = gradingOption;
    }

    public boolean isLegalToDelete() {

        if(StringUtils.equals(coInfo.getStateKey(), LuiServiceConstants.LUI_DRAFT_STATE_KEY) ||
                StringUtils.equals(coInfo.getStateKey(), LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY) ||
                StringUtils.equals(coInfo.getStateKey(), LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY)) {
            return true;
        }

        return false;
    }

}

