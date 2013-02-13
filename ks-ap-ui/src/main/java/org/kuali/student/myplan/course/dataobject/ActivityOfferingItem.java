package org.kuali.student.myplan.course.dataobject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jasonosgood
 * Date: 12/5/12
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 *
 *
 * https://wiki.cac.washington.edu/display/MyPlan/MyPlan+Course+Section+Details+Data+Needs
 */
public class ActivityOfferingItem {

    private String code;
    private String campus;
    private String activityOfferingType;
    private String credits;
    private List<MeetingDetails> meetingDetailsList;
    
    // Section Line Number - UW-ism
    private String atpId;
    private String sln;
    // Used by the UI to feed the QTRYR query parameter, like in the following:
    // https://sdb.admin.washington.edu/timeschd/uwnetid/sln.asp?QTRYR=WIN+2013&amp;SLN=11944
    private String qtryr;
    private boolean enrollRestriction;
    private boolean enrollOpen;
    private int enrollCount;
    private int enrollMaximum;
    private String instructor;
    private String details;

    private boolean distanceLearning;
    private boolean honorsSection;
    private boolean jointOffering;
    private boolean research;
    private boolean writingSection;
    private boolean serviceLearning;
    private boolean newThisYear;
    private boolean ineligibleForFinancialAid;
    private String gradingOption;

    private boolean planned = false;

    private boolean primary = false;

    private List<ActivityOfferingItem> secondaryList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getCampus() {
    	return campus;
    }
    
    public void setCampus( String campus ) {
    	this.campus = campus;
    }

    public String getActivityOfferingType() {
        return activityOfferingType;
    }

    public void setActivityOfferingType(String activityOfferingType) {
        this.activityOfferingType = activityOfferingType;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public List<MeetingDetails> getMeetingDetailsList() {
        if(meetingDetailsList == null )
        {
        	meetingDetailsList = new ArrayList<MeetingDetails>();
        }
        return meetingDetailsList;
    }

    public void setMeetingDetailsList(List<MeetingDetails> meetingDetailsList) {
        this.meetingDetailsList = meetingDetailsList;
    }

    public String getSln() {
        return sln;
    }

    public void setSln(String sln) {
        this.sln = sln;
    }

    public String getQtryr() {
        return qtryr;
    }

    public void setQtryr(String qtryr) {
        this.qtryr = qtryr;
    }

    public boolean isEnrollRestriction() {
        return enrollRestriction;
    }

    public void setEnrollRestriction(boolean enrollRestriction) {
        this.enrollRestriction = enrollRestriction;
    }

    public boolean isEnrollOpen() {
        return enrollOpen;
    }

    public void setEnrollOpen(boolean enrollOpen) {
        this.enrollOpen = enrollOpen;
    }

    public int getEnrollCount() {
        return enrollCount;
    }

    public void setEnrollCount(int enrollCount) {
        this.enrollCount = enrollCount;
    }

    public int getEnrollMaximum() {
        return enrollMaximum;
    }

    public void setEnrollMaximum(int enrollMaximum) {
        this.enrollMaximum = enrollMaximum;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails( String details ) {
        this.details = details;
    }

    /*
        Other course attribute indicators

     */

    public boolean isDistanceLearning() {
        return distanceLearning;
    }

    public void setDistanceLearning(boolean distanceLearning) {
        this.distanceLearning = distanceLearning;
    }

    public boolean isHonorsSection() {
        return honorsSection;
    }

    public void setHonorsSection(boolean honorsSection) {
        this.honorsSection = honorsSection;
    }

    public boolean isJointOffering() {
        return jointOffering;
    }

    public void setJointOffering(boolean jointOffering) {
        this.jointOffering = jointOffering;
    }

    public boolean isResearch() {
        return research;
    }

    public void setResearch(boolean research) {
        this.research = research;
    }

    public boolean isWritingSection() {
        return writingSection;
    }

    public void setWritingSection(boolean writingSection) {
        this.writingSection = writingSection;
    }

    public boolean isServiceLearning() {
        return serviceLearning;
    }

    public void setServiceLearning(boolean serviceLearning) {
        this.serviceLearning = serviceLearning;
    }

    public boolean isNewThisYear() {
        return newThisYear;
    }

    public void setNewThisYear(boolean newThisYear) {
        this.newThisYear = newThisYear;
    }

    public boolean isIneligibleForFinancialAid() {
        return ineligibleForFinancialAid;
    }

    public void setIneligibleForFinancialAid(boolean ineligibleForFinancialAid) {
        this.ineligibleForFinancialAid = ineligibleForFinancialAid;
    }
    
    public String getGradingOption() {
    	return gradingOption;
    }
    
    public void setGradingOption( String gradingOption ) {
    	this.gradingOption = gradingOption;
    }


    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public List<ActivityOfferingItem> getSecondaryList() {
        if (secondaryList == null) {
            secondaryList = new ArrayList<ActivityOfferingItem>();
        }
        return secondaryList;
    }
    
    public void setSecondaryList(  List<ActivityOfferingItem> secondaryList ) {
    	this.secondaryList = secondaryList;
    }
    
    public boolean isStandalone() {
    	return isPrimary() && getSecondaryList().size() == 0;
    }

    public boolean isPlanned() {
        return planned;
    }

    public void setPlanned(boolean planned) {
        this.planned = planned;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }
}
