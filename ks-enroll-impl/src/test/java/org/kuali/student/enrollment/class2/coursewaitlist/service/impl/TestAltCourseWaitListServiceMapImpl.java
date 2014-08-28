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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseseatcount.dto.SeatCountInfo;
import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;
import org.kuali.student.enrollment.courseseatcount.service.CourseSeatCountService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.poc.jsondataloader.JsonDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for the course seat count service
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mocks-test-context.xml"})
public class TestAltCourseWaitListServiceMapImpl {
    @Resource(name = "courseWaitListService")
    private AltCourseWaitListService courseWaitListService;

    @Resource(name = "lprService")
    private LprService lprService;

    @Resource(name = "coService")
    private CourseOfferingService coService;

    @Resource(name = "seatCountService")
    private CourseSeatCountService seatCountService;

    @Before
    public void setUp() throws Throwable {
        loadData();
    }

    public void loadData() {
        if (lprService instanceof MockService) {
            MockService mockService = (MockService) lprService;
            mockService.clear();
        }
        if (coService instanceof MockService) {
            MockService mockService = (MockService) coService;
            mockService.clear();
        }
        String resourcePath =
                "jsonTestData/seatCountData.txt";
        JsonDataLoader loader = new JsonDataLoader(resourcePath,
                lprService, coService);
        loader.loadData();
    }

    @Test
    public void testInitialSeatCounts() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        List<String> allAoIds = new ArrayList<>();
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        allAoIds.add("AO-Lec_A");
        allAoIds.add("AO-Lec_B");
        allAoIds.add("AO-Lab_1");
        allAoIds.add("AO-Lab_2");
        allAoIds.add("AO-Lab_3");

        List<SeatCount> seatCounts = seatCountService.getSeatCountsForActivityOfferings(allAoIds, context);

        Assert.assertEquals(1, (int) seatCounts.get(0).getAvailableSeats());
        Assert.assertEquals(1, (int) seatCounts.get(0).getWaitListSize());

        Assert.assertEquals(2, (int) seatCounts.get(1).getAvailableSeats());
        Assert.assertEquals(1, (int) seatCounts.get(1).getWaitListSize());

        Assert.assertEquals(0, (int) seatCounts.get(2).getAvailableSeats());
        Assert.assertEquals(2, (int) seatCounts.get(2).getWaitListSize());

        Assert.assertEquals(1, (int) seatCounts.get(3).getAvailableSeats());
        Assert.assertEquals(0, (int) seatCounts.get(3).getWaitListSize());

        Assert.assertEquals(2, (int) seatCounts.get(4).getAvailableSeats());
        Assert.assertEquals(0, (int) seatCounts.get(4).getWaitListSize());
    }

    @Test
    public void testDrop() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        // Drop Carrie from course
        List<LprInfo> lprs = lprService.getLprsByMasterLprId("RGLPR-Carrie-B1", context);
        for (LprInfo lpr: lprs) {
            lprService.deleteLpr(lpr.getId(), context);
        }
        List<String> aoIdsDroppedByCarrie = new ArrayList<>();
        aoIdsDroppedByCarrie.add("AO-Lec_B");
        aoIdsDroppedByCarrie.add("AO-Lab_1");
        Map<String, Integer> aoId2OpenSeats = new HashMap<>();
        List<AltCourseWaitListService.WaitlistInfo> waitlistInfos =
            courseWaitListService.getPeopleToProcessFromWaitlist(aoIdsDroppedByCarrie, aoId2OpenSeats, context);
        AltCourseWaitListService.WaitlistInfo waitlistInfo = KSCollectionUtils.getRequiredZeroElement(waitlistInfos);
        Assert.assertEquals("Donald", waitlistInfo.personId);
    }
}
