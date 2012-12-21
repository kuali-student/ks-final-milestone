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
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditWrapper extends CourseOfferingWrapper {

    private List<FormatOfferingInfo> formatOfferingList;
    private List<String> studentRegOptions;
    private List<String> crsGradingOptions;
    private List<OrganizationInfoWrapper> organizationNames;
    private List<OfferingInstructorWrapper> instructors;
    private String termStartEnd;
    private String stateName;

    private String selectedGradingOptionName;
    private String selectedStudentRegOpts;

    private boolean passFailStudentRegOpts;//TODO RICE IS BROKEN for checklists so these are here temporarily
    private boolean auditStudentRegOpts;

    private CreditOptionInfo creditOption;
    private boolean creditOptionFixed;
    private boolean isChecked;

    private String gradingOption;

    private String termName;

    public CourseOfferingEditWrapper(){
        super();
        formatOfferingList = new ArrayList<FormatOfferingInfo>();
        studentRegOptions = new ArrayList<String>();
    }

    public CourseOfferingEditWrapper(CourseOfferingInfo info){
        super();
        setCoInfo(info);
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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public boolean isLegalToDelete() {

        if (getCoInfo() != null &&
            StringUtils.equals(getCoInfo().getStateKey(), LuiServiceConstants.LUI_DRAFT_STATE_KEY) ||
            StringUtils.equals(getCoInfo().getStateKey(), LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY) ||
            StringUtils.equals(getCoInfo().getStateKey(), LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY)) {
            return true;
        }

        return false;
    }

    public String getCreditOptionCreditsUI(){
        if(creditOption!=null && !creditOption.getAllowedCredits().isEmpty() && !creditOption.getCredits().isEmpty() ){
            return StringUtils.join(creditOption.getCredits(),",");
        }
        return "No Credits Selected";
    }

    public String getSelectedStudentRegOptsUI(){
        return selectedStudentRegOpts;
    }

    public boolean getAuditStudentRegOpts() {
        return auditStudentRegOpts;
    }

    public void setAuditStudentRegOpts(boolean auditStudentRegOpts) {
        this.auditStudentRegOpts = auditStudentRegOpts;
    }

    public boolean getPassFailStudentRegOpts() {
        return passFailStudentRegOpts;
    }

    public void setPassFailStudentRegOpts(boolean passFailStudentRegOpts) {
        this.passFailStudentRegOpts = passFailStudentRegOpts;
    }

    public void setTermName(String name){
        this.termName=name;
    }

    public String getTermName(){
        return termName;
    }

    public Map<String,String> getAdminOrg(){
        String org=  getOrganizationNames().get(0).getId();
        Map<String,String> temp = new HashMap<String,String>();
        temp.put("org",org);
        return  temp;
    }
}

