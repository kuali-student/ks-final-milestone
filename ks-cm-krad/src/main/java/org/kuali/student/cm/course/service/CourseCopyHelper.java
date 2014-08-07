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
 * Created by venkat on 8/6/14
 */
package org.kuali.student.cm.course.service;

import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

/**
 *
 * @author Kuali Student Team
 */
public interface CourseCopyHelper {

    public void cleanUpCourseWrapperOnCopy(CourseInfoWrapper target);

    public void copyCourse(CourseInfo source, CourseInfo target);

}
