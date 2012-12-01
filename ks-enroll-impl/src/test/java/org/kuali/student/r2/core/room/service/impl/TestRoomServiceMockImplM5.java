/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Mezba Mahtab on 9/6/12
 */
package org.kuali.student.r2.core.room.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.CrudInfoTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomFixedResourceInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomUsageInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests the Room Service mock impl for M5.
 *
 * @author Mezba Mahtab
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:room-mock-impl-test-context.xml"})
public class TestRoomServiceMockImplM5 {

    ///////////////////////////////
    // TEST CONSTANTS
    ///////////////////////////////

    private final static String TEST_TYPE = "TEST_TYPE";
    private final static String TEST_TYPE2 = "TEST_TYPE2";
    private final static String TEST_STATE = "TEST_STATE";

    ///////////////////////////////
    // DATA VARIABLES
    ///////////////////////////////

    @Resource
    private RoomService roomService;

    public static String principalId1 = "123";
    public static String principalId2 = "321";
    public ContextInfo callContext = null;
    public CrudInfoTester crudInfoTester = null;

    /////////////////////////////
    // GETTERS AND SETTERS
    /////////////////////////////

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    //////////////////////////////
    // TESTS AND FUNCTIONALS
    //////////////////////////////

    @Before
    public void setUp() {
        principalId1 = "123";
        principalId1 = "321";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId1);
        crudInfoTester = new CrudInfoTester(principalId1, principalId2, callContext);
    }

    // test crud RoomInfo
    @Test
    public void testCrudRoom () throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,Exception {

        // test create
        // ----------------
        RoomInfo expected = new RoomInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, TEST_TYPE, TEST_STATE);
        expected.setAccessibilityTypeKeys(new ArrayList<String>());
        expected.setRoomFixedResources(new ArrayList<RoomFixedResourceInfo>());
        expected.setRoomUsages(new ArrayList<RoomUsageInfo>());
        expected.setBuildingId("111");
        RoomInfo actual = roomService.createRoom(expected.getBuildingId(), expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);
        assertEquals(expected.getBuildingId(), actual.getBuildingId());
        assertEquals("111", actual.getBuildingId());

        // test read
        // ----------------
        expected = actual;
        actual = roomService.getRoom(expected.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected);
        crudInfoTester.testRead(expected, actual);
        assertEquals(expected.getBuildingId(), actual.getBuildingId());
        assertEquals("111", actual.getBuildingId());

        // test update
        // ----------------
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected, TEST_STATE);
        expected.setBuildingId("100");
        actual = roomService.updateRoom(actual.getId(), expected, callContext);
        crudInfoTester.testUpdate(expected, actual);
        assertEquals(expected.getBuildingId(), actual.getBuildingId());
        assertEquals("100", actual.getBuildingId());

        // create a 2nd RoomInfo
        // -------------------------------
        RoomInfo expected2 = new RoomInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected2, TEST_TYPE2, TEST_STATE);
        expected2.setAccessibilityTypeKeys(new ArrayList<String>());
        expected2.setRoomFixedResources(new ArrayList<RoomFixedResourceInfo>());
        expected2.setRoomUsages(new ArrayList<RoomUsageInfo>());
        expected2.setBuildingId("200");
        RoomInfo actual2 = roomService.createRoom(expected2.getBuildingId(), expected2.getTypeKey(), expected2, callContext);

        // test bulk get
        // -------------------
        List<String> IDS = new ArrayList<String>();
        IDS.add(actual.getId());
        IDS.add(actual2.getId());
        List<RoomInfo> infos = roomService.getRoomsByIds(IDS, callContext);
        assertEquals(IDS.size(), infos.size());
        for (RoomInfo info: infos) {
            if (!IDS.remove(info.getId())) {
                fail(info.getId());
            }
        }
        assertEquals(0, IDS.size());

        // test get by type
        // -------------------
        assertEquals(actual.getTypeKey(), TEST_TYPE);
        assertEquals(actual2.getTypeKey(), TEST_TYPE2);
        IDS = roomService.getRoomIdsByType(TEST_TYPE, callContext);
        assertEquals(1, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
        IDS = roomService.getRoomIdsByType(TEST_TYPE2, callContext);
        assertEquals(1, IDS.size());
        assertEquals(actual2.getId(), IDS.get(0));
        IDS = roomService.getRoomIdsByType(TEST_TYPE + "123", callContext);
        assertEquals(0, IDS.size());

        // test delete
        // -----------------
        StatusInfo status = roomService.deleteRoom(actual.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = roomService.getRoom(actual.getId(), callContext);
            assertEquals("FAKE_ID",actual.getId());
            //fail("Did not receive DoesNotExistException when attempting to get already-deleted RoomInfo");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

    // test crud BuildingInfo
    @Test
    public void testCrudBuilding () throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,Exception {

        // test create
        // ----------------
        BuildingInfo expected = new BuildingInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, TEST_TYPE, TEST_STATE);
        expected.setCampusKey("111");
        BuildingInfo actual = roomService.createBuilding(expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);
        assertEquals(expected.getCampusKey(), actual.getCampusKey());
        assertEquals("111", actual.getCampusKey());

        // test read
        // ----------------
        expected = actual;
        actual = roomService.getBuilding(expected.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected);
        crudInfoTester.testRead(expected, actual);
        assertEquals(expected.getCampusKey(), actual.getCampusKey());
        assertEquals("111", actual.getCampusKey());

        // test update
        // ----------------
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected, TEST_STATE);
        expected.setCampusKey("100");
        actual = roomService.updateBuilding(actual.getId(), expected, callContext);
        crudInfoTester.testUpdate(expected, actual);
        assertEquals(expected.getCampusKey(), actual.getCampusKey());
        assertEquals("100", actual.getCampusKey());

        // create a 2nd BuildingInfo
        // -------------------------------
        BuildingInfo expected2 = new BuildingInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected2, TEST_TYPE2, TEST_STATE);
        expected2.setCampusKey("200");
        BuildingInfo actual2 = roomService.createBuilding(expected2.getTypeKey(), expected2, callContext);

        // test bulk get
        // -------------------
        List<String> IDS = new ArrayList<String>();
        IDS.add(actual.getId());
        IDS.add(actual2.getId());
        List<BuildingInfo> infos = roomService.getBuildingsByIds(IDS, callContext);
        assertEquals(IDS.size(), infos.size());
        for (BuildingInfo info: infos) {
            if (!IDS.remove(info.getId())) {
                fail(info.getId());
            }
        }
        assertEquals(0, IDS.size());

        // test get by campus key
        // -------------------
        assertEquals(actual.getCampusKey(), "100");
        assertEquals(actual2.getCampusKey(), "200");
        IDS = roomService.getBuildingIdsByCampus("100", callContext);
        assertEquals(1, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
        IDS = roomService.getBuildingIdsByCampus("200", callContext);
        assertEquals(1, IDS.size());
        assertEquals(actual2.getId(), IDS.get(0));
        IDS = roomService.getBuildingIdsByCampus("300", callContext);
        assertEquals(0, IDS.size());

        // test delete
        // -----------------
        StatusInfo status = roomService.deleteBuilding(actual.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = roomService.getBuilding(actual.getId(), callContext);
            assertEquals("FAKE_ID",actual.getId());
            //fail("Did not receive DoesNotExistException when attempting to get already-deleted BuildingInfo");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

}
