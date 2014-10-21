package org.kuali.student.ap.bookmark.dto;

import org.kuali.student.ap.common.infc.HasUniqueId;
import org.kuali.student.ap.coursesearch.dataobject.CourseDetailsWrapper;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 12/20/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookmarkDetailWrapper implements HasUniqueId, Comparable<BookmarkDetailWrapper> {
    private Date dateAdded;
    private String learningPlanId;
    private String planItemId;
    private String uniqueId;

    private CourseDetailsWrapper courseDetailsWrapper;

    public String getLearningPlanId() {
        return learningPlanId;
    }

    public void setLearningPlanId(String learningPlanId) {
        this.learningPlanId = learningPlanId;
    }

    public String getPlanItemId() {
        return planItemId;
    }

    public void setPlanItemId(String planItemId) {
        this.planItemId = planItemId;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public CourseDetailsWrapper getCourseDetailsWrapper() {
        return courseDetailsWrapper;
    }

    public void setCourseDetailsWrapper(CourseDetailsWrapper courseDetailsWrapper) {
        this.courseDetailsWrapper = courseDetailsWrapper;
    }

    @Override
    public int compareTo(BookmarkDetailWrapper o) {
        return this.getCourseDetailsWrapper().getCourseCode().compareTo(o.getCourseDetailsWrapper().getCourseCode());
    }
}
