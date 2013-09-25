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
 * Created by Charles on 2/21/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.adapter.issue;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingAutogenIssue {
    private String courseOfferingId;
    private List<FormatOfferingAutogenIssue> foIssues;

    public CourseOfferingAutogenIssue(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
        foIssues = new ArrayList<FormatOfferingAutogenIssue>();
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public List<FormatOfferingAutogenIssue> getFormatOfferingIssues() {
        return foIssues;
    }
}
