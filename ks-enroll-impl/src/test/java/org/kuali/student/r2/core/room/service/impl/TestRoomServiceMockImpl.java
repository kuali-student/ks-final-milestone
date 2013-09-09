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
 * Created by chongzhu on 8/14/12
 */
package org.kuali.student.r2.core.room.service.impl;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.process.service.impl.TestProcessServiceModel;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.service.RoomService;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mock-room-service-impl-test-context.xml"})

/**
 * This class tests the RoomServiceMockImpl
 *
 * @author Kuali Student Team
 */
public class TestRoomServiceMockImpl {
    ///////////////////
    // CONSTANTS
    ///////////////////
    private static final Logger logger = Logger.getLogger(TestProcessServiceModel.class);
    ///////////////////
    // DATA FIELDS
    ///////////////////
    public static String principalId = "123";
    public ContextInfo contextInfo = null;

    @Resource(name = "roomService")
    private RoomService roomService;
    @Resource(name = "debugMode")
    private boolean debugMode;

    @Before
    public void setUp() {
        // set up context
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);

        //test setup
        assertNotNull(roomService);
    }

    @Test
    public void testGetMethods()  throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            DoesNotExistException, VersionMismatchException, DependentObjectsExistException {
        if (debugMode) {
            logger.debug("testing testGetMethods");
        }

        // test rooms
        List<String> idList = new ArrayList<String>();
        idList.add("1115097");
        idList.add("2118406");
        idList.add("1505039");
        List<RoomInfo> rooms = roomService.getRoomsByIds(idList, contextInfo);
        assertEquals(3, rooms.size());

        List<String> roomIds = roomService.getRoomIdsByBuilding("097", contextInfo);
        assertNotNull(roomIds);
        assertEquals(1,roomIds.size());
        assertEquals("1115097",roomIds.get(0));

        roomIds.clear();
        roomIds = roomService.getRoomIdsByBuildingAndFloor("406", "2", contextInfo);
        assertNotNull(roomIds);
        assertEquals(1,roomIds.size());
        assertEquals("2118406",roomIds.get(0));

        roomIds.clear();
        roomIds = roomService.getRoomIdsByBuildingAndRoomType("097","kuali.room.type.classroom.general", contextInfo);
        assertEquals(1, roomIds.size());
        assertEquals("1115097",roomIds.get(0));

        roomIds.clear();
        List<String> roomTypes = new ArrayList<String>();
        roomTypes.add("kuali.room.type.classroom.general");
        roomIds = roomService.getRoomIdsByBuildingAndRoomTypes("039", roomTypes, contextInfo);
        assertEquals(1, roomIds.size());
        assertEquals("1505039",roomIds.get(0));

        roomIds.clear();
        idList.clear();
        idList.add("usageType");
        roomIds = roomService.getRoomsByBuildingAndRoomUsageTypes("039", idList, contextInfo);
        assertEquals(2, roomIds.size());
        assertEquals("1505039",roomIds.get(0));


        idList.clear();
        idList.add("097");
        idList.add("406");
        idList.add("039");
        List<BuildingInfo> buildings =  roomService.getBuildingsByIds(idList, contextInfo);
        assertEquals(3, buildings.size());

        List<String> buildingIds = roomService.getBuildingIdsByCampus("MAIN", contextInfo);
        assertNotNull(buildingIds);
        assertEquals(3, buildingIds.size());

        idList.clear();
        idList.add("1001");
        idList.add("1010");
        idList.add("1168");
        List<RoomResponsibleOrgInfo> roomResponsibleOrgInfoList =  roomService.getRoomResponsibleOrgsByIds(idList, contextInfo);
        assertEquals(3, roomResponsibleOrgInfoList.size());

        List<String> reponseOrgIdList = new ArrayList<String>();
        reponseOrgIdList = roomService.getRoomResponsibleOrgIdsByRoom("1505039", contextInfo);
        assertNotNull(reponseOrgIdList);
        assertEquals(1, reponseOrgIdList.size());
        assertTrue(reponseOrgIdList.get(0).equalsIgnoreCase("1168"));

        reponseOrgIdList.clear();
        reponseOrgIdList = roomService.getRoomResponsibleOrgIdsByType("kuali.room.type.classroom.general", contextInfo);
        assertNotNull(reponseOrgIdList);
        assertEquals(3, reponseOrgIdList.size());

        reponseOrgIdList = roomService.getRoomResponsibleOrgIdsForBuilding("097", contextInfo);
        assertNotNull(reponseOrgIdList);
        assertEquals(1, reponseOrgIdList.size());
        assertTrue(reponseOrgIdList.get(0).equalsIgnoreCase("1001"));

    }

    @Test(expected = DoesNotExistException.class)
    public void testExceptionsToGetMethods()throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            DoesNotExistException, VersionMismatchException, DependentObjectsExistException {
        if (debugMode) {
            logger.debug("testing testGetMethods");
        }

        // test rooms
        List<String> idList = new ArrayList<String>();
        idList.add("1115097");
        idList.add("2118406");
        idList.add("039");
        List<RoomInfo> rooms = roomService.getRoomsByIds(idList, contextInfo);
        assertEquals(3, rooms.size());
        idList.clear();
        idList.add("097");
        idList.add("2118406");
        List<BuildingInfo> buildings =  roomService.getBuildingsByIds(idList, contextInfo);
        assertEquals(3, buildings.size());

        idList.clear();
        idList.add("097");
        idList.add("1010");
        idList.add("1168");
        List<RoomResponsibleOrgInfo> roomResponsibleOrgInfoList =  roomService.getRoomResponsibleOrgsByIds(idList, contextInfo);
        assertEquals(3, roomResponsibleOrgInfoList.size());


    }


    @Test
    public void testReadAndDelete()
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            DoesNotExistException, VersionMismatchException, DependentObjectsExistException {
        if (debugMode) {
            logger.debug("testing CRUD");
        }

        // test building info
        // Valid buildingIDs: "097", 406, 039
        BuildingInfo expected = roomService.getBuilding("097",contextInfo);
        assertNotNull(expected);
        assertEquals(expected.getId(), "097");
        StatusInfo deleteStatus = roomService.deleteBuilding(expected.getId(), contextInfo);
        assertNotNull(deleteStatus);
        assert(deleteStatus.getIsSuccess());


        // test room info  Valid room IDs: 1115097, 2118406, 1505039
        RoomInfo expectedRoom = roomService.getRoom("1115097", contextInfo);
        assertNotNull(expectedRoom);
        assertEquals(expectedRoom.getId(), "1115097");
        StatusInfo deleteRm = roomService.deleteRoom(expectedRoom.getId(), contextInfo);
        assertNotNull(deleteRm);
        assert(deleteRm.getIsSuccess());

        // test RoomResponsibleOrgInfo   the IDs 1001, 1010, 1168, org ID 102
        RoomResponsibleOrgInfo  expectedRespInfo = roomService.getRoomResponsibleOrg("1001", contextInfo);
        assertNotNull(expectedRespInfo);
        assertEquals(expectedRespInfo.getId(), "1001");
        StatusInfo deleteResp = roomService.deleteRoomResponsibleOrg("1168", contextInfo);
        assertNotNull(deleteResp);
        assert(deleteResp.getIsSuccess());
    }

    @Test
    public void testValidations() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            DoesNotExistException, VersionMismatchException, DependentObjectsExistException {
        if (debugMode) {
            logger.debug("testing Validations");
        }

        List<ValidationResultInfo> infoList = null;

        BuildingInfo expected = new BuildingInfo();
        BuildingInfo actual = roomService.createBuilding("testBuildingType", expected, contextInfo);
        assertNotNull(actual);
        infoList = roomService.validateBuilding(actual.getTypeKey(), actual.getStateKey(), actual, contextInfo);
        assertNotNull(infoList);
        for(ValidationResultInfo resultInfo : infoList) {
            assertNotNull(resultInfo.getElement());
            assertNotNull(resultInfo.getMessage());
        }

        RoomInfo expectedRoom = new RoomInfo();
        RoomInfo actualRoom = roomService.createRoom(expected.getId(),"testRoomType", expectedRoom, contextInfo);
        assertNotNull(actualRoom);
        infoList = roomService.validateBuilding(actual.getTypeKey(), actual.getStateKey(), actual, contextInfo);
        assertNotNull(infoList);
        for(ValidationResultInfo resultInfo : infoList) {
            assertNotNull(resultInfo.getElement());
            assertNotNull(resultInfo.getMessage());
        }

        RoomResponsibleOrgInfo expectedRespInfo = new RoomResponsibleOrgInfo();
        RoomResponsibleOrgInfo actualRespInfo = roomService.createRoomResponsibleOrg(actualRoom.getId(), "testOrgId", "testOrgType", expectedRespInfo, contextInfo);
        assertNotNull(actualRespInfo);
        infoList = roomService.validateBuilding(actual.getTypeKey(), actual.getStateKey(), actual, contextInfo);
        assertNotNull(infoList);
        for(ValidationResultInfo resultInfo : infoList) {
            assertNotNull(resultInfo.getElement());
            assertNotNull(resultInfo.getMessage());
        }
    }
}
