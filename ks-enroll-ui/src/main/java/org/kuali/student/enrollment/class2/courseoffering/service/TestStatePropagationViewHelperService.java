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
 * Created by Charles on 5/6/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.class2.courseoffering.form.TestStatePropagationForm;

import java.util.Map;

/**
 * See KSENROLL-6616.
 *
 * @author Kuali Student Team
 */
public interface TestStatePropagationViewHelperService {
    /**
     * Runs all the "tests" in testing state propagation.
     * @param form Used to display the results of the AFUT to the screen
     * @throws Exception Generic exception thrown due to errors.
     */
    public void runTests(TestStatePropagationForm form) throws Exception;

    /**
     * To run tests, a course offering is rolled over from a source term to a target term.  Codes are used to
     * simplify the calling of the code.
     * @param courseOfferingCode A CO code like CHEM237
     * @param sourceTermCode A source term code like 201201
     * @param targetTermCode A target term code like 200001
     * @return A map of the course offering key to the target CO that was created.  (A map is used so
     * multiple things can be returned if needed).
     * @throws Exception Generic exception thrown due to errors.
     */
    public Map<String, Object> rolloverCourseOfferingFromSourceTermToTargetTerm(String courseOfferingCode,
                                                                                String sourceTermCode,
                                                                                String targetTermCode) throws Exception;
}
