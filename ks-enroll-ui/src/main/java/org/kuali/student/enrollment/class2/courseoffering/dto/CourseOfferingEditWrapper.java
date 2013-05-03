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
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* TODO : see https://jira.kuali.org/browse/KSENROLL-5931
 */

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
    private boolean editCrossListedCoAlias;

    private String gradingOption;

    private String termName;

    private SocInfo socInfo;

    private List<String> alternateCourseCodesSuffixStripped;

    public CourseOfferingEditWrapper(){
        formatOfferingList = new ArrayList<FormatOfferingInfo>();
        studentRegOptions = new ArrayList<String>();
        alternateCourseCodesSuffixStripped = new ArrayList<String>();
    }

    public CourseOfferingEditWrapper(CourseOfferingInfo info){
        super(info);
        alternateCourseCodesSuffixStripped = new ArrayList<String>();
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

    public boolean isEditCrossListedCoAlias() {
        return editCrossListedCoAlias;
    }

    public void setEditCrossListedCoAlias(boolean editCrossListedCoAlias) {
        this.editCrossListedCoAlias = editCrossListedCoAlias;
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

        if (getCourseOfferingInfo() != null &&
            StringUtils.equals(getCourseOfferingInfo().getStateKey(), LuiServiceConstants.LUI_DRAFT_STATE_KEY) ||
            StringUtils.equals(getCourseOfferingInfo().getStateKey(), LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY) ||
            StringUtils.equals(getCourseOfferingInfo().getStateKey(), LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY)||
            StringUtils.equals(getCourseOfferingInfo().getStateKey(), LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY) ||
            StringUtils.equals(getCourseOfferingInfo().getStateKey(), LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY) ) {
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
        Map<String,String> adminOrgMap = new HashMap<String,String>();
        if (organizationNames != null && !organizationNames.isEmpty()){
            String orgIDs = "";
            for (OrganizationInfoWrapper organizationName : organizationNames) {
                orgIDs = orgIDs + organizationName.getId() + ",";
            }
            if (orgIDs.length() > 0) {
                adminOrgMap.put("offeringAdminOrgId", orgIDs.substring(0, orgIDs.length()-1));
            }
        }
        return  adminOrgMap;
    }

    public SocInfo getSocInfo() {
        return socInfo;
    }

    public void setSocInfo(SocInfo socInfo) {
        this.socInfo = socInfo;
    }

    public List<String> getAlternateCourseCodesSuffixStripped() {
        return alternateCourseCodesSuffixStripped;
    }

    /**
     * This is a suffix stripped out version of the Cross List codes from Course Offering DTO. This is needed to display
     * the cross list codes at edit co screen so that users can check/uncheck the cross lists
     *
     * <p>Here is the use case for that.
     * On Create CO, user might enter a suffix and it got appended to all the cross list codes associated
     * with a CO. So, On Edit CO, We display a list of Cross list codes from {@link org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo} to allow
     * the user to edit their already selected option. As we're storing with suffixes, this list doesnt match with what available at {@link org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo}
     * </p>
     * @param alternateCourseCodesSuffixStripped
     */
    @SuppressWarnings("unused")
    public void setAlternateCourseCodesSuffixStripped(List<String> alternateCourseCodesSuffixStripped) {
        this.alternateCourseCodesSuffixStripped = alternateCourseCodesSuffixStripped;
    }

    /**
     * This method returns a list of crosslisted course codes for a course as comma seperated
     * string -- intended as a UI-helper method particularly since after CO-edit the save operation is
     * currently asynchronous so the UI doesn't see the updated data at any of the other CO-layers; since
     * the edits are happening directly in this object then the UI code will see the change and be able to
     * display appropriately; the drawback is that it's false since if persistence fails down below this
     * data will still appear as if everything was updated correctly.
     *
     * Essentially, this is a patch for KSENROLL-5398 until KSENROLL-5346 is completed to make the edit
     * synchronous.
     *
     * @see # getAlternateCOCodesUITooltip()
     * @return
     */
    @SuppressWarnings("unused")
    public String getAlternateCOCodesUIList(){
        StringBuffer buffer = new StringBuffer();
        for (String crosslistingCode : alternateCourseCodesSuffixStripped){
            buffer.append(crosslistingCode + ", ");
        }

        return StringUtils.removeEnd(buffer.toString(), ", ");
    }

}

