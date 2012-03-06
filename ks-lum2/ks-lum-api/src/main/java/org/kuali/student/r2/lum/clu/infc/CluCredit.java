/*
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

package org.kuali.student.r2.lum.clu.infc;

import org.kuali.student.r2.common.dto.TimeAmountInfo;

/**
 * Detailed information about credit for a CLU, including common data-driven
 * constraints around repetition.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface CluCredit {
    /**
     * The number of times a student may repeat the course for credit. The
     * values of this field are restricted to integer values and the string
     * "unbounded".
     *
     * @name Repeat Count
     */
    public String getRepeatCount();

    /**
     * The amount of time after which a student may repeat the course (in ATP)
     */
    public TimeAmountInfo getRepeatTime();

    /**
     * The total number of units for which the student may repeat this course.
     */
    public String getRepeatUnits();

    /**
     * The minimum total number of credits or units earned by the Student. This
     * field will be used for calculations. If specified, should be less than or
     * equal to the maxTotalUnits.
     */
    public Integer getMinTotalUnits();

    /**
     * The maximum total number of credits or units earned by the Student. This
     * field will be used for calculations. If specified, should be greater than
     * or equal to the minTotalUnits.
     */
    public Integer getMaxTotalUnits();

    /**
     * The total credit hours for use in evaluating the Instructor, meant to
     * cover the concept of Contact Hours
     */
    public Integer getInstructorUnits();

    /**
     * Minimum amount of time required to complete the CLU. If specified, should
     * be less than or equal to maxTimeToComplete.
     */
    public TimeAmountInfo getMinTimeToComplete();

    /**
     * The default maximum amount of time allowed to complete the CLU. If
     * specified, should be greater than or equal to the minTimeToComplete.
     */
    public TimeAmountInfo getMaxTimeToComplete();

    /**
     * The Maximum Allowable Number of Time Periods of Inactivity (i.e. Stale or
     * Resting time).
     */
    public TimeAmountInfo getMaxAllowableInactivity();

    /**
     * After this period expires, the results will no longer be recognized and
     * the student will have to retake the CLU. This may apply to certain types
     * of exams.
     */
    public TimeAmountInfo getMaxTimeResultsRecognized();
}
