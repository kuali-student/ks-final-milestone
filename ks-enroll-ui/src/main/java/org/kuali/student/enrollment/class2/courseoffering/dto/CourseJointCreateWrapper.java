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
 * Created by venkat on 1/28/13
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is wrapper used in Create CO screen to display a list of joint offerings and allowing user
 * to select/deselect a CO
 *
 * @author Kuali Student Team
 */
public class CourseJointCreateWrapper {

    private CourseJointInfo courseJointInfo;
    private CourseInfo courseInfo;
    private boolean selectedToJointlyOfferred;
    private boolean alreadyOffered;

    private List<FormatOfferingCreateWrapper> formatOfferingWrappers;

    public CourseJointCreateWrapper(){
        courseJointInfo = new CourseJointInfo();
        courseInfo = new CourseInfo();
        formatOfferingWrappers = new ArrayList<FormatOfferingCreateWrapper>();
    }

    public boolean isAlreadyOffered() {
        return alreadyOffered;
    }

    public void setAlreadyOffered(boolean alreadyOffered) {
        this.alreadyOffered = alreadyOffered;
    }

    public boolean isSelectedToJointlyOfferred() {
        return selectedToJointlyOfferred;
    }

    public void setSelectedToJointlyOfferred(boolean selectedToJointlyOfferred) {
        this.selectedToJointlyOfferred = selectedToJointlyOfferred;
    }

    public CourseJointInfo getCourseJointInfo() {
        return courseJointInfo;
    }

    public void setCourseJointInfo(CourseJointInfo courseJointInfo) {
        this.courseJointInfo = courseJointInfo;
    }

    public String getCourseTitle(){
        return courseJointInfo.getCourseTitle();
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public CourseInfo getCourseInfo(){
        return courseInfo;
    }

    public String getCourseCode(){
        return courseInfo.getCode();
    }

    public List<FormatOfferingCreateWrapper> getFormatOfferingWrappers() {
        return formatOfferingWrappers;
    }

    public void setFormatOfferingWrappers(List<FormatOfferingCreateWrapper> formatOfferingWrappers) {
        this.formatOfferingWrappers = formatOfferingWrappers;
    }

}
