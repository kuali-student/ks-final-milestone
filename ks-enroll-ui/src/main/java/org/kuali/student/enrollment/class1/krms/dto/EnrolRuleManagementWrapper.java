package org.kuali.student.enrollment.class1.krms.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.dto.RuleManagementWrapper;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/04/04
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnrolRuleManagementWrapper extends RuleManagementWrapper {

    private String cluDescription;

    public String getCluDescription() {
        return cluDescription;
    }

    public void setCluDescription(String cluDescription) {
        this.cluDescription = cluDescription;
    }

    public EnrolRuleEditor getEnrolRuleEditor(){
        return (EnrolRuleEditor) this.getRuleEditor();
    }

    public String getSearchByCourseRange() {
        if (this.getEnrolRuleEditor()==null){
            return "-1";
        }
        return this.getEnrolRuleEditor().getSearchByCourseRange();
    }

    public void setSearchByCourseRange(String searchByCourseRange) {
        this.getEnrolRuleEditor().setSearchByCourseRange(searchByCourseRange);
    }

    public String getSubjectCode() {
        if(this.getEnrolRuleEditor()==null){
            return StringUtils.EMPTY;
        }
        return this.getEnrolRuleEditor().getSubjectCode();
    }

    public void setSubjectCode(String subjectCode) {
        this.getEnrolRuleEditor().setSubjectCode(subjectCode);
    }

    public String getCourseNumberRange() {
        if(this.getEnrolRuleEditor()==null){
            return StringUtils.EMPTY;
        }
        return this.getEnrolRuleEditor().getCourseNumberRange();
    }

    public void setCourseNumberRange(String courseNumberRange) {
        this.getEnrolRuleEditor().setCourseNumberRange(courseNumberRange);
    }

    public String getLearningObjective() {
        if(this.getEnrolRuleEditor()==null){
            return StringUtils.EMPTY;
        }
        return this.getEnrolRuleEditor().getLearningObjective();
    }

    public void setLearningObjective(String learningObjective) {
        this.getEnrolRuleEditor().setLearningObjective(learningObjective);
    }

    public Date getEffectiveFrom() {
        if(this.getEnrolRuleEditor()==null){
            return new Date();
        }
        return this.getEnrolRuleEditor().getEffectiveFrom();
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.getEnrolRuleEditor().setEffectiveFrom(effectiveFrom);
    }

    public Date getEffectiveTo() {
        if(this.getEnrolRuleEditor()==null){
            return new Date();
        }
        return this.getEnrolRuleEditor().getEffectiveTo();
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.getEnrolRuleEditor().setEffectiveTo(effectiveTo);
    }

}
