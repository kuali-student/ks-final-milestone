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
import org.kuali.student.common.uif.form.KSUifForm;

/**
 *
 * @author Kuali Student Team
 */
public class CourseDetailedViewForm extends KSUifForm {

    private CurriculumManagementConstants.CourseViewType viewType;
    private CourseInfoWrapper courseInfoWrapper;

    public CourseDetailedViewForm(){
        viewType = CurriculumManagementConstants.CourseViewType.COURSE_VIEW;
    }

    public CurriculumManagementConstants.CourseViewType getViewType() {
        return viewType;
    }

    public void setViewType(CurriculumManagementConstants.CourseViewType viewType) {
        this.viewType = viewType;
    }

    public boolean isCourseView(){
        return getViewType() == CurriculumManagementConstants.CourseViewType.COURSE_VIEW;
    }

    public boolean isCourseCompareView(){
       return getViewType() == CurriculumManagementConstants.CourseViewType.COURSE_COMPARE_VIEW;
    }

    public CourseInfoWrapper getCourseInfoWrapper() {
        return courseInfoWrapper;
    }

    public void setCourseInfoWrapper(CourseInfoWrapper courseInfoWrapper) {
        this.courseInfoWrapper = courseInfoWrapper;
    }

}
