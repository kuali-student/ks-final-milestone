/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class around {@link CourseJointInfo} and it's used only in Create CO screen to display a list of joint offerings and allowing user
 * to select/deselect a CO
 *
 * @see CourseOfferingCreateWrapper
 * @see FormatOfferingWrapper
 * @author Kuali Student Team
 */
public class JointCourseWrapper {

    private CourseJointInfo courseJointInfo;
    private CourseInfo courseInfo;
    private boolean selectedToJointlyOfferred;
    private boolean alreadyOffered;
    private boolean enableCreateNewCOActionLink = false;

    private List<FormatOfferingWrapper> formatOfferingWrappers;

    public JointCourseWrapper(){
        courseJointInfo = new CourseJointInfo();
        courseInfo = new CourseInfo();
        formatOfferingWrappers = new ArrayList<FormatOfferingWrapper>();
    }

    /**
     * @see #setAlreadyOffered
     * @return
     */
    @SuppressWarnings("unused")
    public boolean isAlreadyOffered() {
        return alreadyOffered;
    }

    /**
     * Sets whether this joint course has already been offered.
     *
     * <p>
     *     This is set during load when there are course offerings exists for this course code
     *     in the user selected term.
     * </p>
     * @param alreadyOffered boolean
     */
    public void setAlreadyOffered(boolean alreadyOffered) {
        this.alreadyOffered = alreadyOffered;
    }

    /**
    * @see #setSelectedToJointlyOfferred
    * @return
    */
    public boolean isSelectedToJointlyOfferred() {
        return selectedToJointlyOfferred;
    }

    /**
     * This is set when the user selects a joint course to add formats.
     *
     * @param selectedToJointlyOfferred
     */
    public void setSelectedToJointlyOfferred(boolean selectedToJointlyOfferred) {
        this.selectedToJointlyOfferred = selectedToJointlyOfferred;
    }

    /**
     * @see #setCourseJointInfo(org.kuali.student.r2.lum.course.dto.CourseJointInfo)
     * @return
     */
    @SuppressWarnings("unused")
    public CourseJointInfo getCourseJointInfo() {
        return courseJointInfo;
    }

    /**
     * Sets the {@link CourseJointInfo} dto associated with this wrapper.
     * @param courseJointInfo
     */
    public void setCourseJointInfo(CourseJointInfo courseJointInfo) {
        this.courseJointInfo = courseJointInfo;
    }

    /**
     * This is called from the view xml to display the course title at the ui.
     *
     * @return course title
     */
    @SuppressWarnings("unused")
    public String getCourseTitle(){
        return courseJointInfo.getCourseTitle();
    }

    /**
     * Sets the {@link CourseInfo} associated with this joint course.
     *
     * @see #setCourseJointInfo
     * @param courseInfo
     */
    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    /**
     * Returns the {@link CourseInfo} associated with the wrapper
     *
     * @return
     */
    public CourseInfo getCourseInfo(){
        return courseInfo;
    }

    /**
     * Returns the course code for a joint course
     *
     * @return
     */
    public String getCourseCode(){
        return courseInfo.getCode();
    }

    /**
     * List of {@link FormatOfferingWrapper} for a joint course
     *
     * @return formatOfferingWrappers
     */
    public List<FormatOfferingWrapper> getFormatOfferingWrappers() {
        return formatOfferingWrappers;
    }

    /**
     *
     * @see #getFormatOfferingWrappers()
     * @param formatOfferingWrappers
     */
    public void setFormatOfferingWrappers(List<FormatOfferingWrapper> formatOfferingWrappers) {
        this.formatOfferingWrappers = formatOfferingWrappers;
    }

    public boolean isEnableCreateNewCOActionLink() {
        return enableCreateNewCOActionLink;
    }

    public void setEnableCreateNewCOActionLink(boolean enableCreateNewCOActionLink) {
        this.enableCreateNewCOActionLink = enableCreateNewCOActionLink;
    }

}
