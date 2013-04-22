package org.kuali.student.myplan.course.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;

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
public class ActivityOfferingItem implements Serializable {

	private static final long serialVersionUID = -5761634338639381397L;

	private String code;
    private String campus;
    private String activityOfferingType;
    private String credits;
    private List<MeetingDetails> meetingDetailsList;

    // Section Line Number - UW-ism
    private String atpId;
    private String registrationCode;
    // Used by the UI to feed the QTRYR query parameter, like in the following:
    // https://sdb.admin.washington.edu/timeschd/uwnetid/registrationCode.asp?QTRYR=WIN+2013&amp;SLN=11944
    private String qtryr;
    private boolean enrollRestriction;
    private boolean enrollOpen;
    private String enrollCount;
    private String enrollMaximum;
    private String enrollEstimate;

    private String planItemId;
    private boolean primary = false;

    private String instituteCode;
    private String instituteName;

    private List<ActivityOfferingAdditionalInfo> additionalInfo = new ArrayList<ActivityOfferingAdditionalInfo>();

    public ActivityOfferingItem(){
        ActivityOfferingAdditionalInfo additionalInfo = new ActivityOfferingAdditionalInfo();
        this.additionalInfo.add(additionalInfo);
    }
    public void setAdditionalInfo(List<ActivityOfferingAdditionalInfo> additionalInfo){
        this.additionalInfo = additionalInfo;
    }
    public List<ActivityOfferingAdditionalInfo> getAdditionalInfo (){
        return additionalInfo;
    }


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

    public String getMeetingDetailsListOutput(){
        List<MeetingDetails> list = meetingDetailsList;
        String TO_BE_ARRANGED="To be Arranged";
        StringBuilder sb = new StringBuilder();
        sb.append( "<div class='meetingdetailslist'>" );

        for( MeetingDetails m : list ) {

            boolean tba = false;

            StringBuilder temp = new StringBuilder();
            temp.append( "<div class='meetingdetails'>" );

            String days = m.getDays();
            String time = m.getTime();
            String building = m.getBuilding();
            String room = m.getRoom();
            String campus = m.getCampus();

            // If the days and building are empty, section is TBA
            if ( ( days == null || days.equals( "" ) ) && ( time == null || time.equals( "" ) ) ) {
                tba = true;
            }

            if ( days == null ) days = "";
            if ( time == null ) time = "";
            if ( building == null ) building = "";
            if ( room == null ) room = "";
            if ( campus == null ) campus = "";

            if ( !tba ) {
                temp.append( "<span class='meetingdays'>" + days + "</span>" );
                temp.append( "<span class='meetingtime'>" + time + "</span>" );
            } else {
                temp.append( "<span class='meetingtba'>" + TO_BE_ARRANGED + "</span>" );
            }

            if ( !building.equals( "NOC" ) && !building.startsWith( "*" ) && campus.equalsIgnoreCase( "seattle" ) ) {
                temp.append( "<span class='meetingbuilding'><a href='http://uw.edu/maps/?" + building + "' target='_blank'>" + building + "</a></span>" );
            } else {
                temp.append( "<span class='meetingbuilding'>" + building + "</span>" );
            }

            temp.append( "<span class='meetingroom'>" + room + "</span>" );

            temp.append( "</div>" );

            sb.append( temp );
        }

        sb.append( "</div>" );

        String result = sb.toString();
        result = result.replace( '\'', '\"' );
        return result;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
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

    public String getEnrollCount() {
        return enrollCount;
    }

    public void setEnrollCount(String enrollCount) {
        this.enrollCount = enrollCount;
    }

    public String getEnrollMaximum() {
        return enrollMaximum;
    }

    public void setEnrollMaximum(String enrollMaximum) {
        this.enrollMaximum = enrollMaximum;
    }

    public String getEnrollEstimate() {
        return enrollEstimate;
    }

    public void setEnrollEstimate(String enrollEstimate) {
        this.enrollEstimate = enrollEstimate;
    }

    public String getInstructor() {
        return additionalInfo.get(0).getInstructor();
    }

    public void setInstructor(String instructor) {
        this.additionalInfo.get(0).setInstructor(instructor);
    }

    public String getDetails() {
        return  additionalInfo.get(0).getDetails();
    }

    public void setDetails( String details ) {
        this.additionalInfo.get(0).setDetails(details);
    }

    /*
        Other course attribute indicators

     */

    public boolean isDistanceLearning() {
        return additionalInfo.get(0).isDistanceLearning();
    }

    public void setDistanceLearning(boolean distanceLearning) {
        this.additionalInfo.get(0).setDistanceLearning(distanceLearning);
    }

    public boolean isHonorsSection() {
        return  additionalInfo.get(0).isHonorsSection();
    }

    public void setHonorsSection(boolean honorsSection) {
        this.additionalInfo.get(0).setHonorsSection(honorsSection);
    }

    public boolean isJointOffering() {
        return  additionalInfo.get(0).isJointOffering();
    }

    public void setJointOffering(boolean jointOffering) {
        this.additionalInfo.get(0).setJointOffering(jointOffering);
    }

    public boolean isResearch() {
        return  additionalInfo.get(0).isResearch();
    }

    public void setResearch(boolean research) {
        this.additionalInfo.get(0).setResearch(research);
    }

    public boolean isWritingSection() {
        return additionalInfo.get(0).isWritingSection();
    }

    public void setWritingSection(boolean writingSection) {
        this.additionalInfo.get(0).setWritingSection(writingSection);
    }

    public boolean isServiceLearning() {
        return  additionalInfo.get(0).isServiceLearning();
    }

    public void setServiceLearning(boolean serviceLearning) {
        this.additionalInfo.get(0).setServiceLearning(serviceLearning);
    }

    public boolean isNewThisYear() {
        return  additionalInfo.get(0).isNewThisYear();
    }

    public void setNewThisYear(boolean newThisYear) {
        this.additionalInfo.get(0).setNewThisYear(newThisYear);
    }

    public boolean isIneligibleForFinancialAid() {
        return  additionalInfo.get(0).isIneligibleForFinancialAid();
    }

    public void setIneligibleForFinancialAid(boolean ineligibleForFinancialAid) {
        this.additionalInfo.get(0).setIneligibleForFinancialAid(ineligibleForFinancialAid);
    }

    public String getGradingOption() {
        return  additionalInfo.get(0).getGradingOption();
    }

    public void setGradingOption( String gradingOption ) {
        this.additionalInfo.get(0).setGradingOption(gradingOption);
    }


    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isStandalone() {
        return isPrimary();
//    	return isPrimary() && getSecondaryList().size() == 0;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    public String getFeeAmount() {
        return  additionalInfo.get(0).getFeeAmount();
    }

    public void setFeeAmount(String feeAmount) {
        this.additionalInfo.get(0).setFeeAmount(feeAmount);
    }

    public boolean isAddCodeRequired() {
        return  additionalInfo.get(0).isAddCodeRequired();
    }

    public void setAddCodeRequired(boolean addCodeRequired) {
        this.additionalInfo.get(0).setAddCodeRequired(addCodeRequired);
    }

    public boolean isIndependentStudy() {
        return  additionalInfo.get(0).isIndependentStudy();
    }

    public void setIndependentStudy(boolean independentStudy) {
        this.additionalInfo.get(0).setIndependentStudy(independentStudy);
    }

    public String getSectionComments() {
        return  additionalInfo.get(0).getSectionComments();
    }

    public void setSectionComments(String sectionComments) {
        this.additionalInfo.get(0).setSectionComments(sectionComments);
    }

    public String getInstituteCode() {
        if(this.instituteCode == null){
           this.instituteCode="Defualt";
        }
        return instituteCode;
    }

    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }

    public String getInstituteName() {
        if(this.instituteName == null){
            this.instituteName="Default";
        }
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getActivityDisplayId(){
        String id = this.getCode()+"-";
        id=id+this.getQtryr().replace("+","");
        return id;
    }

    public String getPlanItemId() {
        return planItemId;
    }

    public void setPlanItemId(String planItemId) {
        this.planItemId = planItemId;
    }
    public String getSummerTerm() {
        return this.additionalInfo.get(0).getSummerTerm();
    }

    public void setSummerTerm(String summerTerm) {
        this.additionalInfo.get(0).setSummerTerm(summerTerm);
    }

    /*Used to get the Short term name for atp (WI 13 for kuali.uw.atp.2013.1)*/
    public String getShortTermName() {
        return KsapFrameworkServiceLocator.getTermHelper().getYearTerm(this.getAtpId()).getShortName();
    }

    /*Used to get the Short term name for atp (Winter 13 for kuali.uw.atp.2013.1)*/
    public String getLongTermName() {
        return KsapFrameworkServiceLocator.getTermHelper().getYearTerm(this.getAtpId()).getLongName();
    }
    public String getActivityIdentifier(){
        String id = this.code+"-"+this.getShortTermName().replace(" ","")+"-"+this.getRegistrationCode()+"sln";
        return id;
    }
}
