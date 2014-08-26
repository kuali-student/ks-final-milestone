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
 * Created by Charles on 8/19/2014
 */
package org.kuali.student.enrollment.class2.coursewaitlist.service.impl;


import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A map impl to the course waitlist service
 *
 * @author Kuali Student Team
 */
public class AltCourseWaitListServiceMapImpl implements AltCourseWaitListService {
    @Resource(name = "lprService")
    private LprService lprService;

    @Resource(name = "seatCountService")
    private CourseSeatCountService seatCountService;

    @Resource(name = "coService")
    private CourseOfferingService coService;

    @Override
    public List<WaitlistInfo> getPeopleToProcessFromWaitlist(List<String> aoIds,
                                                             Map<String, Integer> aoid2openSeatsMap,
                                                             ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException {
        List<SeatCount> seatCounts = seatCountService.getSeatCountsForActivityOfferings(aoIds, contextInfo);
        // Fill in seats
        for (SeatCount seatCount: seatCounts) {
            String aoId = seatCount.getLuiId();
            Integer count = seatCount.getAvailableSeats();
            aoid2openSeatsMap.put(aoId, count);
        }
        Map<String, Set<String>> rgId2AoIds = findRegGroupIdToAoIds(aoIds, contextInfo);
        List<WaitlistInfo> waitlistInfos =
                getPotentialWaitlistInfos(rgId2AoIds, contextInfo);

        //Process the waitlist
        List<WaitlistInfo> results = new ArrayList<>();
        for (WaitlistInfo entry : waitlistInfos) {

            //Check each ao in the reg group for the current person
            boolean seatAvailable = true;
            for (String aoId : rgId2AoIds.get(entry.rgId)) {
                //If there are no seats available for at least one waitlist AO then skip to the next entry
                if (aoid2openSeatsMap.get(aoId) <= 0) {
                    seatAvailable = false;
                    break;
                }
            }

            //If there is a seat available on all ao waitlist items
            if (seatAvailable) {
                for (String aoId : rgId2AoIds.get(entry.rgId)) {
                    //Decrement the open seats for each ao in the RG
                    aoid2openSeatsMap.put(aoId, aoid2openSeatsMap.get(aoId) - 1);
                }
                //Add a mapping of the person to the RG they should be added to from the waitlist
                results.add(entry);
            }

        }

        return results;
    }

    private List<WaitlistInfo> getPotentialWaitlistInfos(Map<String, Set<String>> rgIdToAoIds,
                                                         ContextInfo contextInfo)
            throws MissingParameterException, PermissionDeniedException,
            InvalidParameterException, OperationFailedException, DoesNotExistException {

        List<LprInfo> lprInfos = getWaitlistMasterLprs(rgIdToAoIds, contextInfo);
        List<WaitlistInfo> waitlistInfos = new ArrayList<>();

        for (LprInfo masterLpr: lprInfos) {
            String rgId = masterLpr.getLuiId();
            String personId = masterLpr.getPersonId();
            String masterLprId = masterLpr.getMasterLprId();
            String atpId = masterLpr.getAtpId();
            waitlistInfos.add(new WaitlistInfo(rgId, personId, masterLprId, atpId));
        }

        return waitlistInfos;
    }

    /**
     * Find all RGs that contain any of the aoIds listed
     * @param aoIds List of AO ids
     * @param context Context info
     * @return Map of reg group to AO ids
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private Map<String, Set<String>> findRegGroupIdToAoIds(List<String> aoIds, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        Map<String, Set<String>> regGroupIdToAoIds = new HashMap<>();
        for (String aoId: aoIds) {
            List<RegistrationGroupInfo> rgInfos
                    = coService.getRegistrationGroupsByActivityOffering(aoId, context);
            for (RegistrationGroupInfo rgInfo: rgInfos) {
                Set<String> aoIdsInRg = regGroupIdToAoIds.get(rgInfo.getId());
                if (aoIdsInRg == null) {
                    Set<String> aoIdSet = new HashSet<>(rgInfo.getActivityOfferingIds());
                    regGroupIdToAoIds.put(rgInfo.getId(), aoIdSet);
                }
            }
        }
        return regGroupIdToAoIds;
    }

    private List<LprInfo> getWaitlistMasterLprs(Map<String, Set<String>> rgIdToAoIds, ContextInfo context)
            throws MissingParameterException, InvalidParameterException,
            OperationFailedException, PermissionDeniedException {
        List<LprInfo> waitListLprs = new ArrayList<>();
        for (String rgId: rgIdToAoIds.keySet()) {
            List<LprInfo> lprs = lprService.getLprsByLui(rgId, context);
            for (LprInfo lpr: lprs) {
                if (LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY.equals(lpr.getTypeKey())) {
                    waitListLprs.add(lpr);
                }
            }
        }
        return waitListLprs;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public void setSeatCountService(CourseSeatCountService seatCountService) {
        this.seatCountService = seatCountService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }
}
