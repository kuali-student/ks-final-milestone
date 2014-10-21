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
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides a wrapper for CourseOffering data for the Course Offering Edit ui
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditWrapper extends CourseOfferingWrapper {

    private List<FormatOfferingWrapper> formatOfferingList;
    private List<String> studentRegOptions;
    private List<String> crsGradingOptions;
    private List<OrganizationInfoWrapper> organizationNames;
    private List<OfferingInstructorWrapper> instructors;
    private List<String> alternateCOCodes;
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

    // term-info
    private String termStartEnd;
    private String termName;

    private SocInfo socInfo;

    private List<String> alternateCourseCodesSuffixStripped;

    private boolean createCO;

    private RenderHelper renderHelper;

    protected String viewId;

    private Boolean hasWaitlist;

    public List<String> getAlternateCOCodes() {
        if (alternateCOCodes == null) {
            alternateCOCodes = new ArrayList<String>();
        }
        return alternateCOCodes;
    }

    public void setAlternateCOCodes(List<String> alternateCOCodes) {
        this.alternateCOCodes = alternateCOCodes;
    }

    //this field is used for CO inquiry page to display all associated AOs
    private List<ActivityOfferingWrapper> aoWrapperList;

    public CourseOfferingEditWrapper() {
        formatOfferingList = new ArrayList<FormatOfferingWrapper>();
        studentRegOptions = new ArrayList<String>();
        alternateCourseCodesSuffixStripped = new ArrayList<String>();
        instructors = new ArrayList<OfferingInstructorWrapper>();
        OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper();
        instructors.add(instructorWrapper);
        renderHelper = new RenderHelper();
        FormatOfferingWrapper defaultFO = new FormatOfferingWrapper();
        formatOfferingList.add(defaultFO);
        aoWrapperList = new ArrayList<ActivityOfferingWrapper>();
    }

    public CourseOfferingEditWrapper(CourseOfferingInfo info) {
        super(info);
        alternateCourseCodesSuffixStripped = new ArrayList<String>();
        instructors = new ArrayList<OfferingInstructorWrapper>();
        if (info.getInstructors() == null || info.getInstructors().isEmpty()) {
            OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper();
            instructors.add(instructorWrapper);
        } else if (info.getInstructors().size() > 0) {
            for (OfferingInstructorInfo instructorInfo : info.getInstructors()) {
                OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper(instructorInfo);
                instructors.add(instructorWrapper);
            }
        }
        renderHelper = new RenderHelper();
    }

    public List<FormatOfferingWrapper> getFormatOfferingList() {
        if (formatOfferingList == null) {
            formatOfferingList = new ArrayList<FormatOfferingWrapper>();
        }
        return formatOfferingList;
    }

    public void setFormatOfferingList(List<FormatOfferingWrapper> formatOfferingList) {
        this.formatOfferingList = formatOfferingList;
    }

    public List<String> getStudentRegOptions() {
        if (studentRegOptions == null) {
            studentRegOptions = new ArrayList<String>();
        }
        return studentRegOptions;
    }

    public void setStudentRegOptions(List<String> studentRegOptions) {
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
                StringUtils.equals(getCourseOfferingInfo().getStateKey(), LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY) ||
                StringUtils.equals(getCourseOfferingInfo().getStateKey(), LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY) ||
                StringUtils.equals(getCourseOfferingInfo().getStateKey(), LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)) {
            return true;
        }

        return false;
    }

    public String getCreditOptionCreditsUI() {
        if (creditOption != null && !creditOption.getAllowedCredits().isEmpty() && !creditOption.getCredits().isEmpty()) {
            return StringUtils.join(creditOption.getCredits(), ",");
        }
        return "No Credits Selected";
    }

    public String getSelectedStudentRegOptsUI() {
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

    public String getTermStartEnd() {
        return termStartEnd;
    }

    public void setTermStartEnd(String termStartEnd) {
        this.termStartEnd = termStartEnd;
    }

    public void setTermName(String name) {
        this.termName = name;
    }

    public String getTermName() {
        return termName;
    }

    public Map<String, String> getAdminOrg() {
        Map<String, String> adminOrgMap = new HashMap<String, String>();
        if (organizationNames != null && !organizationNames.isEmpty()) {
            StringBuilder orgIDs = new StringBuilder("");
            for (OrganizationInfoWrapper organizationName : organizationNames) {
                orgIDs.append(organizationName.getId()).append(",");
            }
            if (orgIDs.length() > 0) {
                adminOrgMap.put("offeringAdminOrgId", orgIDs.substring(0, orgIDs.length() - 1));
            }
        }
        return adminOrgMap;
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

    public boolean getCreateCO() {
        return createCO;
    }

    public void setCreateCO(boolean createCO) {
        this.createCO = createCO;
    }

    /**
     * This is a suffix stripped out version of the Cross List codes from Course Offering DTO. This is needed to display
     * the cross list codes at edit co screen so that users can check/uncheck the cross lists
     * <p/>
     * <p>Here is the use case for that.
     * On Create CO, user might enter a suffix and it got appended to all the cross list codes associated
     * with a CO. So, On Edit CO, We display a list of Cross list codes from {@link org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo} to allow
     * the user to edit their already selected option. As we're storing with suffixes, this list doesnt match with what available at {@link org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo}
     * </p>
     *
     * @param alternateCourseCodesSuffixStripped
     *
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
     * <p/>
     * Essentially, this is a patch for KSENROLL-5398 until KSENROLL-5346 is completed to make the edit
     * synchronous.
     *
     * @return
     * @see # getAlternateCOCodesUITooltip()
     */
    @SuppressWarnings("unused")
    public String getAlternateCOCodesUIList() {
        //JIRA FIX : KSENROLL-8731 - Replaced StringBuffer with StringBuilder
        StringBuilder sb = new StringBuilder();

        if (alternateCourseCodesSuffixStripped == null) {
            alternateCourseCodesSuffixStripped=new ArrayList<String>();
        }

        for (String crosslistingCode : alternateCourseCodesSuffixStripped) {
            sb.append(crosslistingCode + ", ");
        }

        return StringUtils.removeEnd(sb.toString(), ", ");
    }

    public RenderHelper getRenderHelper() {
        return renderHelper;
    }

    public void setRenderHelper(RenderHelper renderHelper) {
        this.renderHelper = renderHelper;
    }

    public class RenderHelper implements Serializable {

        private String selectedCoCode;
        private CourseOfferingInfo nextCO;
        private CourseOfferingInfo prevCO;
        private List<KeyValue> relatedCOs;

        protected RenderHelper() {
            relatedCOs = new ArrayList<KeyValue>();
        }


        public boolean isShowFormatAddButton() {
            return getFormatOfferingList().size() < getCourse().getFormats().size();
        }

        public void setSelectedCoCode(String selectedCoCode) {
            this.selectedCoCode = selectedCoCode;
        }

        public String getSelectedCoCode() {
            return this.selectedCoCode;
        }

        public void setPrevCO(CourseOfferingInfo prevCO) {
            this.prevCO = prevCO;
        }

        public CourseOfferingInfo getPrevCO() {
            return this.prevCO;
        }

        public void setNextCO(CourseOfferingInfo nextCO) {
            this.nextCO = nextCO;
        }

        public CourseOfferingInfo getNextCO() {
            return this.nextCO;
        }

        public void setRelatedCOs(List<KeyValue> relatedCOs) {
            this.relatedCOs = relatedCOs;
        }

        public List<KeyValue> getRelatedCOs() {
            return this.relatedCOs;
        }

    }

    public List<ActivityOfferingWrapper> getAoWrapperList() {
        return aoWrapperList;
    }

    public void setAoWrapperList(List<ActivityOfferingWrapper> aoWrapperList) {
        this.aoWrapperList = aoWrapperList;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public Boolean getHasWaitlist() {
        return this.hasWaitlist;
    }

    public void setHasWaitlist(Boolean hasWaitlist) {
        this.hasWaitlist = hasWaitlist;
    }

    /**
     * This method returns a list of crosslisted/official course code for a course. This will
     * be displayed as the tooltip (if crosslisted cos exists) at Copy CO Screen.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getCrossListedCodesUI() {

        StringBuilder sb = new StringBuilder();
        sb.append("This course is crosslisted with:<br>");
        for (String code : alternateCOCodes) {
            sb.append(code + ",");
        }

        return StringUtils.removeEnd(sb.toString(), ",");
    }

    public String getCrosslistedCodes() {
        StringBuilder sb = new StringBuilder();
        for (String code : alternateCOCodes) {
            sb.append(code + ",");
        }

        return StringUtils.removeEnd(sb.toString(), ",");
    }
}

