/**
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
 *
 * Created by venkat on 7/18/14
 */
package org.kuali.student.cm.course.form;

import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.common.uif.form.KSUifForm;

/**
 * Base model for the course detailed view. This is being used at ViewCourseView.xml
 *
 * @author Kuali Student Team
 */
public class ViewCourseForm extends KSUifForm {

    private CurriculumManagementConstants.ViewCourseType viewType;
    private CourseInfoWrapper courseInfoWrapper;
    private CourseInfoWrapper compareCourseInfoWrapper;
    private boolean canRetireCourse;
    private boolean useReviewProcess;
    private boolean isComparison;
    private boolean isModifiableCourse;
    private boolean isRequisitesEqual;

    public ViewCourseForm(){
        viewType = CurriculumManagementConstants.ViewCourseType.COURSE_VIEW;
        canRetireCourse = false;
        // defaulted to false because only the CS user should ever see this as a checkbox
        useReviewProcess = false;
    }

    /**
     * @see #setViewType(org.kuali.student.cm.common.util.CurriculumManagementConstants.ViewCourseType)
     * @return
     */
    public CurriculumManagementConstants.ViewCourseType getViewType() {
        return viewType;
    }

    /**
     * Sets the View type, list of valid types defined at <class>CurriculumManagementConstants.ViewCourseType</class>
     * @param viewType
     */
    public void setViewType(CurriculumManagementConstants.ViewCourseType viewType) {
        this.viewType = viewType;
    }

    /**
     * Returns true if it's a detailed course view
     * @return
     */
    public boolean isCourseView(){
        return getViewType() == CurriculumManagementConstants.ViewCourseType.COURSE_VIEW;
    }

    /**
     * Returns true if it's a compare course view
     * @return
     */
    public boolean isCourseCompareView(){
       return getViewType() == CurriculumManagementConstants.ViewCourseType.COURSE_COMPARE_VIEW;
    }

    /**
     * @see #setCourseInfoWrapper(CourseInfoWrapper)
     * @return
     */
    public CourseInfoWrapper getCourseInfoWrapper() {
        return courseInfoWrapper;
    }

    /**
     * Model to display the course details at the ui.
     *
     * @param courseInfoWrapper
     */
    public void setCourseInfoWrapper(CourseInfoWrapper courseInfoWrapper) {
        this.courseInfoWrapper = courseInfoWrapper;
    }

    public CourseInfoWrapper getCompareCourseInfoWrapper() {
        return compareCourseInfoWrapper;
    }

    /**
     * Model to compare with #getCourseInfoWrapper(). This is used only for course compare view.
     *
     * @param compareCourseInfoWrapper
     */
    public void setCompareCourseInfoWrapper(CourseInfoWrapper compareCourseInfoWrapper) {
        this.compareCourseInfoWrapper = compareCourseInfoWrapper;
    }

    public boolean isCanRetireCourse() {
        return canRetireCourse;
    }

    public void setCanRetireCourse(boolean canRetireCourse) {
        this.canRetireCourse = canRetireCourse;
    }

    public boolean isUseReviewProcess() {
        return useReviewProcess;
    }

    public void setUseReviewProcess(boolean useReviewProcess) {
        this.useReviewProcess = useReviewProcess;
    }

    /**
     * Returns true if this is course comparison view
     *
     */
    public boolean isComparison() {
        return this.isComparison;
    }

    public void setComparison(boolean isComparison) {
        this.isComparison = isComparison;
    }

    /**
     * Returns true if this course is modifiable
     *
     */

    public boolean isModifiableCourse() {
        return this.isModifiableCourse;
    }

    public void setModifiableCourse(boolean isModifiableCourse) {
        this.isModifiableCourse = isModifiableCourse;
    }

    /**
     * If in compare mode, are the two sets of requisites equal.
     */
    public boolean isRequisitesEqual() {
        return isRequisitesEqual;
    }

    public void setRequisitesEqual(boolean isRequisitesEqual) {
        this.isRequisitesEqual = isRequisitesEqual;
    }
}