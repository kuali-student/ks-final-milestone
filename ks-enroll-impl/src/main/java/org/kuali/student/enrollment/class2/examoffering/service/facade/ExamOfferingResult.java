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
 * Created by Charles on 2/20/13
 */
package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.kuali.student.r2.common.dto.BulkStatusInfo;

/**
 *
 *
 * @author Kuali Student Team
 */
public class ExamOfferingResult {
    private BulkStatusInfo examPeriodStatus; // Exam period is not defined for the Term
    private BulkStatusInfo examMatrixStatus; //status for whether or not the exam matrix exist for the term
    private BulkStatusInfo startTimeDaysStatus; //status for whether or not the  the AO start time and days is found on the Standard Final Exam
    private BulkStatusInfo courseStatus;  //status for whether or not the the Course Offering is  found on the Matrix
    private BulkStatusInfo examStatus;

    public ExamOfferingResult() {
        examPeriodStatus = new BulkStatusInfo();
        examPeriodStatus.setSuccess(Boolean.TRUE);
        examMatrixStatus = new BulkStatusInfo();
        examMatrixStatus.setSuccess(Boolean.TRUE);
        startTimeDaysStatus = new BulkStatusInfo();
        startTimeDaysStatus.setSuccess(Boolean.TRUE);
        courseStatus = new BulkStatusInfo();
        courseStatus.setSuccess(Boolean.TRUE);
        examStatus = new BulkStatusInfo();
        examStatus.setSuccess(Boolean.TRUE);
    }


    public BulkStatusInfo getExamPeriodStatus() {
        return examPeriodStatus;
    }

    public void setExamPeriodStatus(BulkStatusInfo examPeriodStatus) {
        this.examPeriodStatus = examPeriodStatus;
    }

    public BulkStatusInfo getExamMatrixStatus() {
        return examMatrixStatus;
    }

    public void setExamMatrixStatus(BulkStatusInfo examMatrixStatus) {
        this.examMatrixStatus = examMatrixStatus;
    }

    public BulkStatusInfo getStartTimeDaysStatus() {
        return startTimeDaysStatus;
    }

    public void setStartTimeDaysStatus(BulkStatusInfo startTimeDaysStatus) {
        this.startTimeDaysStatus = startTimeDaysStatus;
    }

    public BulkStatusInfo getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(BulkStatusInfo courseStatus) {
        this.courseStatus = courseStatus;
    }

    public BulkStatusInfo getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(BulkStatusInfo examStatus) {
        this.examStatus = examStatus;
    }
}
