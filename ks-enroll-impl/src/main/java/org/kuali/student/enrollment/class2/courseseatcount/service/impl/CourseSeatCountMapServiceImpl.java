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
 * Created by Charles on 8/7/2014
 */
package org.kuali.student.enrollment.class2.courseseatcount.service.impl;

import org.kuali.student.enrollment.class1.lpr.service.decorators.LprServiceCacheDecorator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseseatcount.dto.SeatCountInfo;
import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;
import org.kuali.student.enrollment.courseseatcount.service.CourseSeatCountService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A map implementation of the course seat count service
 *
 * @author Kuali Student Team
 */
public class CourseSeatCountMapServiceImpl implements CourseSeatCountService {
    @Resource(name = "lprService")
    private LprService lprService;

    @Resource(name = "coService")
    private CourseOfferingService coService;

    @Override
    public SeatCount getSeatCountForActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getSeatCountHelper(activityOfferingId, new Date(), context);
    }

    private SeatCount getSeatCountHelper(String activityOfferingId,
                                         Date current,
                                         ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        SeatCountInfo seatCountInfo = new SeatCountInfo();
        ActivityOfferingInfo aoInfo = coService.getActivityOffering(activityOfferingId, context);
        seatCountInfo.setTotalSeats(aoInfo.getMaximumEnrollment());
        List<LprInfo> lprs = lprService.getLprsByLui(aoInfo.getId(), context);
        int aoCount = 0, aoWlCount = 0;
        for (LprInfo lpr: lprs) {
            String lprTypeKey = lpr.getTypeKey();
            if (LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY.equals(lprTypeKey)) {
                aoCount++;
            } else if (LprServiceConstants.WAITLIST_AO_LPR_TYPE_KEY.equals(lprTypeKey)) {
                aoWlCount++;
            }
        }
        seatCountInfo.setLuiTypeKey(aoInfo.getTypeKey());
        seatCountInfo.setLuiId(aoInfo.getId());
        seatCountInfo.setUsedSeats(aoCount);
        seatCountInfo.setHasWaitList(true);
        seatCountInfo.setAvailableSeats(seatCountInfo.getTotalSeats() - seatCountInfo.getUsedSeats());
        seatCountInfo.setWaitListSize(aoWlCount);
        seatCountInfo.setTimestamp(current);
        return seatCountInfo;
    }

    @Override
    public List<SeatCount> getSeatCountsForActivityOfferings(List<String> activityOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<SeatCount> seatCounts = new ArrayList<>();
        Date now = new Date();
        for (String aoId: activityOfferingIds) {
            SeatCount seatCount = getSeatCountHelper(aoId, now, context);
            seatCounts.add(seatCount);
        }
        return seatCounts;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public void setCourseOfferingService(CourseOfferingService coService) {
        this.coService = coService;
    }
}
