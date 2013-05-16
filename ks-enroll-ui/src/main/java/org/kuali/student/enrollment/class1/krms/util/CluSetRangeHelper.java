package org.kuali.student.enrollment.class1.krms.util;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class CluSetRangeHelper implements Serializable {

    private String searchByCourseRange;
    private String cluSetRangeLabel;
    private String subjectCode;
    private String courseNumberRange;
    private String learningObjective;
    private Date effectiveFrom;
    private Date effectiveTo;

    public CluSetRangeHelper() {
        this.searchByCourseRange = StringUtils.EMPTY;
        this.cluSetRangeLabel = StringUtils.EMPTY;
        this.subjectCode = StringUtils.EMPTY;
        this.courseNumberRange = StringUtils.EMPTY;
        this.learningObjective = StringUtils.EMPTY;
        this.effectiveFrom = new Date();
        this.effectiveTo = new Date();
    }

    public String getCluSetRangeLabel() {
        return cluSetRangeLabel;
    }

    public void setCluSetRangeLabel(String cluSetRangeLabel) {
        this.cluSetRangeLabel = cluSetRangeLabel;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCourseNumberRange() {
        return courseNumberRange;
    }

    public void setCourseNumberRange(String courseNumberRange) {
        this.courseNumberRange = courseNumberRange;
    }

    public String getLearningObjective() {
        return learningObjective;
    }

    public void setLearningObjective(String learningObjective) {
        this.learningObjective = learningObjective;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getSearchByCourseRange() {
        return searchByCourseRange;
    }

    public void setSearchByCourseRange(String searchByCourseRange) {
        this.searchByCourseRange = searchByCourseRange;
    }
}
