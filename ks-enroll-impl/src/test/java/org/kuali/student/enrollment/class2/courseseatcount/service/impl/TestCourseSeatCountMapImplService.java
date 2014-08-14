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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;
import org.kuali.student.enrollment.courseseatcount.service.CourseSeatCountService;
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

import org.junit.Assert;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the course seat count service
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mocks-test-context.xml"})
public class TestCourseSeatCountMapImplService {
    @Resource(name = "seatCountService")
    private CourseSeatCountService seatCountService;

    @Resource(name = "lprService")
    private LprService lprService;

    @Resource(name = "coService")
    private CourseOfferingService coService;

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
        String fileName =
                "ks-enroll/ks-enroll-impl/src/test/resources/jsonTestData/seatCountData.txt";
        JsonDataLoader loader = new JsonDataLoader(fileName,
                lprService, coService);
        loader.loadData();
    }


    @Test
    public void testSeatCount() {
        List<String> aoIds = new ArrayList<>();
        aoIds.add("AO-Lec_A");
        aoIds.add("AO-Lec_B");
        aoIds.add("AO-Lab_1");
        aoIds.add("AO-Lab_2");
        aoIds.add("AO-Lab_3");
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        try {
            List<SeatCount> seatCounts =
                    seatCountService.getSeatCountsForActivityOfferings(aoIds, context);
            Assert.assertEquals(1, (int) seatCounts.get(0).getAvailableSeats());
            Assert.assertEquals(2, (int) seatCounts.get(1).getAvailableSeats());
            Assert.assertEquals(0, (int) seatCounts.get(2).getAvailableSeats());
            Assert.assertEquals(1, (int) seatCounts.get(3).getAvailableSeats());
            Assert.assertEquals(2, (int) seatCounts.get(4).getAvailableSeats());

            Assert.assertEquals(1, (int) seatCounts.get(0).getWaitListSize());
            Assert.assertEquals(1, (int) seatCounts.get(1).getWaitListSize());
            Assert.assertEquals(2, (int) seatCounts.get(2).getWaitListSize());
            Assert.assertEquals(0, (int) seatCounts.get(3).getWaitListSize());
            Assert.assertEquals(1, (int) seatCounts.get(4).getWaitListSize());
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
    }
}
