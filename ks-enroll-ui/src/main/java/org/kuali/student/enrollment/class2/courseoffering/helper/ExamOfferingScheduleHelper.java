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
 * Created by David Yin on 3/11/14
 */
package org.kuali.student.enrollment.class2.courseoffering.helper;

import org.kuali.student.enrollment.class2.acal.dto.ExamPeriodWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExamOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;

import java.util.List;

/**
 * This class defines methods to load and save schedules in Exam Offering schedule logic
 *
 * @author Kuali Student Team
 */
public interface ExamOfferingScheduleHelper {

    public StatusInfo saveScheduleRequestBulk(List<ExamOfferingWrapper> eoWrappers, ContextInfo defaultContextInfo);

    public StatusInfo saveScheduleRequest(ExamOfferingWrapper eoWrapper, ContextInfo defaultContextInfo);

    public void loadSchedules(ExamOfferingWrapper eoWrapper, CourseOfferingManagementForm theForm, ContextInfo defaultContextInfo);

    public void setScheduleTimeSlotInfo(ScheduleWrapper scheduleWrapper, ExamPeriodWrapper examPeriodWrapper)
            throws OperationFailedException;

    public void setScheduleRoomAndBuilding(ScheduleWrapper scheduleWrapper, ScheduleRequestComponentInfo componentInfo,
                                           ContextInfo defaultContextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

}
