package org.kuali.student.ap.bookmark.dto;

import org.kuali.student.ap.common.infc.HasUniqueId;

import java.math.BigDecimal;
import java.util.Date;

public class BookmarkSideBarWrapper implements HasUniqueId, Comparable<BookmarkSideBarWrapper> {
    private String courseId;
    private String courseCd;
    private String learningPlanId;
    private String courseTitle;
    private String planItemId;
    private String uniqueId;
    private BigDecimal credits;
    private Date dateAdded;

    /**
     * Get the unique identifier.
     * <p/>
     * <p>
     * The value returned by this method should always be unique within the
     * context of the rendered page, and therefore reliable for referring to the
     * represented user interface element using a DOM selector within a
     * front-end script.
     * </p>
     * <p/>
     * <p>
     * This value must be usable in an XML document as an ID attribute without
     * modification. Therefore, only letters, numbers, hyphens, and the
     * underscore character are permitted.
     * </p>
     *
     * @return A unique identifier for this component.
     */
    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseCd() {
        return courseCd;
    }

    public void setCourseCd(String courseCd) {
        this.courseCd = courseCd;
    }

    public String getLearningPlanId() {
        return learningPlanId;
    }

    public void setLearningPlanId(String learningPlanId) {
        this.learningPlanId = learningPlanId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getPlanItemId() {
        return planItemId;
    }

    public void setPlanItemId(String planItemId) {
        this.planItemId = planItemId;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int compareTo(BookmarkSideBarWrapper o) {
        return this.getDateAdded().compareTo(o.getDateAdded());
    }
}
