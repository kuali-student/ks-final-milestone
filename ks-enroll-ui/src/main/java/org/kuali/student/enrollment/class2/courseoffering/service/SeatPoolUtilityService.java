package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: David Yin
 * Date: 8/7/12
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SeatPoolUtilityService {
    public void updateSeatPoolDefinitionList (List<SeatPoolDefinitionInfo> updatedSeatPoolList, String activityOfferingId, ContextInfo context);
}
