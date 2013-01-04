package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.keyvalue.WaitlistLevelOptionsKeyValues;
import org.kuali.student.enrollment.class2.courseoffering.keyvalue.WaitlistTypeOptionsKeyValues;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.WaitlistLevel;
import org.springframework.ui.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

        setIsExcludeCancelledActivityOfferings(false);
        setIsExcludeSchedulingInformation(false);
        setIsExcludeInstructorInformation(false);
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
            for (int i = 0; i < studentGradingOptionsList.size(); i++) {
                sb.append(",");
                sb.append(studentGradingOptionsList.get(i+1));
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

    public void setIsExcludeCancelledActivityOfferings(boolean excludeCancelledActivityOfferings) {
        this.excludeCancelledActivityOfferings = excludeCancelledActivityOfferings;
    }

    public boolean isExcludeSchedulingInformation() {
        return excludeSchedulingInformation;
    }

    public void setIsExcludeSchedulingInformation(boolean excludeSchedulingInformation) {
        this.excludeSchedulingInformation = excludeSchedulingInformation;
    }

    public boolean isExcludeInstructorInformation() {
        return excludeInstructorInformation;
    }

    public void setIsExcludeInstructorInformation(boolean excludeInstructorInformation) {
        this.excludeInstructorInformation = excludeInstructorInformation;
    }

}
