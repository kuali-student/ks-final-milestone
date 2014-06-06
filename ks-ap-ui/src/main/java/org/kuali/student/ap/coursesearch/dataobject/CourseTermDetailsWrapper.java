package org.kuali.student.ap.coursesearch.dataobject;

import java.util.List;

/**
 * Wrapper class used to feed the CourseSectionDetailsUI.xml page
 */
public class CourseTermDetailsWrapper {

    private String termId;
    private String termName;
    private boolean openDisclosure;
    private List<CourseOfferingDetailsWrapper> courseOfferingDetailWrappers;

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public boolean isOpenDisclosure() {
        return openDisclosure;
    }

    public void setOpenDisclosure(boolean openDisclosure) {
        this.openDisclosure = openDisclosure;
    }

    public List<CourseOfferingDetailsWrapper> getCourseOfferingDetailWrappers() {
        return courseOfferingDetailWrappers;
    }

    public void setCourseOfferingDetailWrappers(List<CourseOfferingDetailsWrapper> courseOfferingDetailWrappers) {
        this.courseOfferingDetailWrappers = courseOfferingDetailWrappers;
    }
}
