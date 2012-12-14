package org.kuali.student.myplan.course.dataobject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  Course Details
 */
public class CourseSummaryDetails {

    // List of fields populated when only summary information is loaded
    private String courseId;
    private String code;
    private String courseTitle;
    private String credit;
    private String courseDescription;
    private List<String> termsOffered;

    // Last Effective Term. Empty if open ended
    private String lastEffectiveTerm;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseTitle() {
        //  Double quotes are very problematic in the serialization to JSON so change to single quotes.;
        if (StringUtils.isEmpty(courseTitle)) {
            return courseTitle;
        }
        return courseTitle.replaceAll("\"", "'");
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCourseDescription() {
            return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public List<String> getTermsOffered() {
        return termsOffered;
    }

    public void setTermsOffered(List<String> termsOffered) {
        this.termsOffered = termsOffered;
    }

    public String getLastEffectiveTerm() {
        return lastEffectiveTerm;
    }

    public void setLastEffectiveTerm(String lastEffectiveTerm) {
        this.lastEffectiveTerm = lastEffectiveTerm;
    }
}