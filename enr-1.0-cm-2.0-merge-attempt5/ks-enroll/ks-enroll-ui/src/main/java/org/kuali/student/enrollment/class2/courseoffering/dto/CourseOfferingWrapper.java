/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * @author Kuali Student Team
 */

package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.io.Serializable;

/**
 * Base class for all the wrappers around CourseOfferingInfo.
 *
 * This will be extended by the different Course Offering Wrappers (for now, it's Create and Edit)
 */
public abstract class CourseOfferingWrapper implements Serializable{

    private CourseInfo course;
    private CourseOfferingInfo coInfo;
    private TermInfo term;

    public CourseOfferingWrapper(){
        this.coInfo = new CourseOfferingInfo();
    }

    public CourseInfo getCourse() {
        return course;
    }

    public void setCourse(CourseInfo course) {
        this.course = course;
    }

    public CourseOfferingInfo getCoInfo() {
        return coInfo;
    }

    public void setCoInfo(CourseOfferingInfo coInfo) {
        this.coInfo = coInfo;
    }

    public TermInfo getTerm() {
        return term;
    }

    public void setTerm(TermInfo term) {
        this.term = term;
    }

}
