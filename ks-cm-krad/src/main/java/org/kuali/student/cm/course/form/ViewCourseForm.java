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

    public ViewCourseForm(){
        viewType = CurriculumManagementConstants.ViewCourseType.COURSE_VIEW;
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

}
