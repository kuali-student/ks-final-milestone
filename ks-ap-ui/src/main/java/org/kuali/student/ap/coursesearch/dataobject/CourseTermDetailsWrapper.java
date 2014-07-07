/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
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
