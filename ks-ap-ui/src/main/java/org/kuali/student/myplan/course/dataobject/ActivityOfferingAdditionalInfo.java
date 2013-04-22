package org.kuali.student.myplan.course.dataobject;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 2/20/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActivityOfferingAdditionalInfo implements Serializable {

	private static final long serialVersionUID = -8966240639502810074L;

	private String feeAmount;
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
    private boolean addCodeRequired;
    private boolean independentStudy;
    private String gradingOption;
    private String sectionComments;
    private String summerTerm;

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

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public boolean isAddCodeRequired() {
        return addCodeRequired;
    }

    public void setAddCodeRequired(boolean addCodeRequired) {
        this.addCodeRequired = addCodeRequired;
    }

    public boolean isIndependentStudy() {
        return independentStudy;
    }

    public void setIndependentStudy(boolean independentStudy) {
        this.independentStudy = independentStudy;
    }

    public String getSectionComments() {
        return sectionComments;
    }

    public void setSectionComments(String sectionComments) {
        this.sectionComments = sectionComments;
    }
    public String getSummerTerm() {
        return summerTerm;
    }

    public void setSummerTerm(String summerTerm) {
        this.summerTerm = summerTerm;
    }
}
