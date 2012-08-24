/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.util.krms.proposition;

import java.util.Collection;
import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;

/**
 * Proposition that checks for the completion of a single course
 *
 * @author alubbers
 */
public class SingleCourseCompletionProposition extends CourseCompletionProposition {

    private Collection<String> courseIdCollection;
    
    public SingleCourseCompletionProposition(String courseId) {
        this.courseIdCollection = Collections.singletonList(courseId);
    }

    @Override
    protected Collection<String> getTermCourseIds(ExecutionEnvironment environment) {
        return courseIdCollection;
    }

}
