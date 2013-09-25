package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.keyvalue.WaitlistLevelOptionsKeyValues;
import org.kuali.student.enrollment.class2.courseoffering.keyvalue.WaitlistTypeOptionsKeyValues;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/* TODO : This class needs refactoring; should inherit from CourseOfferingWrapper; see https://jira.kuali.org/browse/KSENROLL-5931
 */
public class CourseOfferingCopyWrapper implements Serializable{

    private CourseOfferingInfo coInfo;

    // Course Info
    private String courseOfferingCode;
    private String courseTitle;
    private String termId;
    private String creditCount;
    private String gradingOption;
    private List<String> studentRegistrationGradingOptionsList;
    private String finalExamType;
    private String waitlistLevelTypeKey;
    private String waitlistTypeKey;
    private boolean isHonorsOffering;
    private List<ExistingCourseOffering> existingOfferingsInCurrentTerm;

    // Activity Offerings
    private List<ActivityOfferingWrapper> activityOfferingWrapperList;

    // Configure Course Offering Copy
    private boolean excludeCancelledActivityOfferings;
    private boolean excludeSchedulingInformation;
    private boolean excludeInstructorInformation;

    public CourseOfferingInfo getCoInfo() {
        return coInfo;
    }

    public void setCoInfo(CourseOfferingInfo coInfo) {
        this.coInfo = coInfo;
    }

    public CourseOfferingCopyWrapper() {
        super();
        clear();
    }
    
    public void clear() {
        setCoInfo(null);
        setCourseOfferingCode("");
        setCourseTitle("");
        setTermId("");
        setCreditCount("");
        setGradingOption("");
        setStudentRegistrationGradingOptionsList(new ArrayList<String>());
        setFinalExamType("");
        setWaitlistLevelTypeKey("");
        setWaitlistTypeKey("");
        setIsHonors(false);

        setExcludeCancelledActivityOfferings(false);
        setExcludeSchedulingInformation(false);
        setExcludeInstructorInformation(false);
        setExistingOfferingsInCurrentTerm(new ArrayList<ExistingCourseOffering>());
    }

    public List<ExistingCourseOffering> getExistingOfferingsInCurrentTerm() {
        return existingOfferingsInCurrentTerm;
    }

    public void setExistingOfferingsInCurrentTerm(List<ExistingCourseOffering> existingOfferingsInCurrentTerm) {
        this.existingOfferingsInCurrentTerm = existingOfferingsInCurrentTerm;
    }

    public List<ActivityOfferingWrapper> getActivityOfferingWrapperList() {
        return activityOfferingWrapperList;
    }

    public void setActivityOfferingWrapperList(List<ActivityOfferingWrapper> activityOfferingWrapperList) {
        this.activityOfferingWrapperList = activityOfferingWrapperList;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getCreditCountUI() {
        return getCreditCount() + ".0";
    }

    public String getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(String creditCount) {
        this.creditCount = creditCount;
    }

    public String getGradingOption() {
        return gradingOption;
    }

    public void setGradingOption(String gradingOption) {
        this.gradingOption = gradingOption;
    }

    public String getStudentRegistrationGradingOptionsUI() {
        String returnString = CourseOfferingConstants.COURSEOFFERING_TEXT_STD_REG_OPTS_EMPTY;
        StringBuffer sb = new StringBuffer();
        List<String> studentGradingOptionsList = getStudentRegistrationGradingOptionsList();
        if (studentGradingOptionsList.size() > 0) {
            sb.append(studentGradingOptionsList.get(0));
            for (int i = 1; i < studentGradingOptionsList.size(); i++) {
                sb.append(",");
                sb.append(studentGradingOptionsList.get(i));
            }
            returnString = sb.toString();
        }
        return returnString;
    }

    public List<String> getStudentRegistrationGradingOptionsList() {
        return studentRegistrationGradingOptionsList;
    }

    public void setStudentRegistrationGradingOptionsList(List<String> studentRegistrationGradingOptionsList) {
        this.studentRegistrationGradingOptionsList = studentRegistrationGradingOptionsList;
    }

    public String getFinalExamType() {
        return finalExamType;
    }

    public void setFinalExamType(String finalExamType) {
        this.finalExamType = finalExamType;
    }

    public String getWaitlistLevelUI() {
        WaitlistLevelOptionsKeyValues waitlistLevelOptionsKeyValues = new WaitlistLevelOptionsKeyValues();
        String waitlistLevelTypeKey = getWaitlistLevelTypeKey();
        String uiString = "None";
        for (KeyValue keyValue : waitlistLevelOptionsKeyValues.getKeyValues()) {
            if (keyValue.getKey().equalsIgnoreCase(waitlistLevelTypeKey)) {
                uiString = keyValue.getValue();
            }
        }
        return uiString;
    }

    public String getWaitlistLevelTypeKey() {
        return waitlistLevelTypeKey;
    }

    public void setWaitlistLevelTypeKey(String waitlistLevelTypeKey) {
        this.waitlistLevelTypeKey = waitlistLevelTypeKey;
    }

    public String getWaitlistTypeUI() {
        WaitlistTypeOptionsKeyValues waitlistTypeOptionsKeyValues = new WaitlistTypeOptionsKeyValues();
        String waitlistTypeKey = getWaitlistTypeKey();
        String uiString = "None";
        ViewModel nullViewModelThatSetsTheKeyValues = null;
        List<KeyValue> list = waitlistTypeOptionsKeyValues.getKeyValues(nullViewModelThatSetsTheKeyValues);
        if (list != null) {
            for (KeyValue keyValue : list) {
                if (keyValue.getKey().equalsIgnoreCase(waitlistTypeKey)) {
                    uiString = keyValue.getValue();
                }
            }
        }
        return uiString;
    }

    public String getWaitlistTypeKey() {
        return waitlistTypeKey;
    }

    public void setWaitlistTypeKey(String waitlistTypeKey) {
        this.waitlistTypeKey = waitlistTypeKey;
    }

    public String getIsHonorsUI() {
        return StringUtils.capitalize(BooleanUtils.toStringYesNo(isHonors()));
    }

    public boolean isHonors() {
        return isHonorsOffering;
    }

    public void setIsHonors(boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    public boolean isExcludeCancelledActivityOfferings() {
        return excludeCancelledActivityOfferings;
    }

    public void setExcludeCancelledActivityOfferings(boolean excludeCancelledActivityOfferings) {
        this.excludeCancelledActivityOfferings = excludeCancelledActivityOfferings;
    }

    public boolean isExcludeSchedulingInformation() {
        return excludeSchedulingInformation;
    }

    public void setExcludeSchedulingInformation(boolean excludeSchedulingInformation) {
        this.excludeSchedulingInformation = excludeSchedulingInformation;
    }

    public boolean isExcludeInstructorInformation() {
        return excludeInstructorInformation;
    }

    public void setExcludeInstructorInformation(boolean excludeInstructorInformation) {
        this.excludeInstructorInformation = excludeInstructorInformation;
    }

    /**
     * TODO: this method is duplicated by method of the same name in CourseOfferingWrapper; see https://jira.kuali.org/browse/KSENROLL-5931
     * (note: also notice that this UI-helper might be better located elsewhere outside of this DTO, like in a static UI-helper class or something)
     *
     * This method returns a list of crosslisted course codes for a course as a comma-
     * separated string
     *
     * @see {@link CourseOfferingWrapper}
     * @return a comma-separated string of cross-listed course codes;
     *         else, empty string; will not return null
     */
    @SuppressWarnings("unused")
    public String getAlternateCOCodesUIList() {
        if( coInfo == null || coInfo.getCrossListings() == null ) return "";

        StringBuilder sb = new StringBuilder();

        for( CourseOfferingCrossListingInfo cross : coInfo.getCrossListings() ) {
            sb.append( cross.getCode() + ", " );
        }

        return StringUtils.removeEnd( sb.toString(), ", " );
    }

}
