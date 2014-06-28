package org.kuali.student.ap.coursesearch.dataobject;

import java.util.List;

/**
 * Wrapper class used to feed the CourseSectionDetailsUI.xml page
 */
public class CourseTermDetailsWrapper {

    private String termId;
    private String termName;
    private boolean openDisclosure;
    private List<CourseOfferingDetailsWrapper> courseOfferingDetailsWrappers;

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    /**
     * Get an XML safe representation of the termId by replacing "." with "-"
     * @return A termId with all occurrences of "." replaced with "-"
     */
    public String getXmlSafeTermId() {
        return termId.replace(".", "-");
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

    public List<CourseOfferingDetailsWrapper> getCourseOfferingDetailsWrappers() {
        return courseOfferingDetailsWrappers;
    }

    public void setCourseOfferingDetailsWrappers(List<CourseOfferingDetailsWrapper> courseOfferingDetailsWrappers) {
        this.courseOfferingDetailsWrappers = courseOfferingDetailsWrappers;
    }

    /**
     * Get the term abbreviation.  It's used in styling the term display
     * @return
     */
    public String getTermAbbreviation() {
        return termName.substring(0, 2).toUpperCase();
    }
}
