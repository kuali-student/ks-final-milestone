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
package org.kuali.student.enrollment.class2.room.service.impl;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mock-room-service-impl-test-context.xml"})
//@Transactional
//@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)

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
    /////////////////////////
    // GETTERS AND SETTERS
    /////////////////////////
    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    /////////////////////////
    // Test Methods
    /////////////////////////
    public void setUp() {
        // set up context
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);

        //test setup
        assertNotNull(roomService);
    }

    @Test
    public void testCrud()
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            DoesNotExistException, VersionMismatchException, DependentObjectsExistException {
        if (debugMode) {
            logger.debug("testing CRUD");
        }

        // test building info
        BuildingInfo expected = new BuildingInfo();

        BuildingInfo actual = roomService.createBuilding("testBuildingType", expected, contextInfo);
        assertNotNull(actual);
        expected = roomService.getBuilding(actual.getId(),contextInfo);
        assertNotNull(expected);
        assertEquals(expected.getId(), actual.getId());
        actual = roomService.updateBuilding("updatedBuildingId", expected, contextInfo);
        assertNotNull(actual);
        assert(actual.getId().equals("updatedBuildingId"));
        StatusInfo deleteStatus = roomService.deleteBuilding(actual.getId(), contextInfo);
        assertNotNull(deleteStatus);
        assert(deleteStatus.getIsSuccess());


        // test room info
        RoomInfo expectedRoom = new RoomInfo();
        RoomInfo actualRoom = roomService.createRoom(expected.getId(),"testRoomType", expectedRoom, contextInfo);
        assertNotNull(actualRoom);
        expectedRoom = roomService.getRoom(actualRoom.getId(), contextInfo);
        assertNotNull(expectedRoom);
        assertEquals(expectedRoom.getId(), actualRoom.getId());
        actualRoom = roomService.updateRoom("updatedRoomId", expectedRoom, contextInfo);
        assertNotNull(actualRoom);
        assert(actualRoom.getId().equals("updatedRoomId"));
        StatusInfo deleteRm = roomService.deleteBuilding(actualRoom.getId(), contextInfo);
        assertNotNull(deleteRm);
        assert(deleteRm.getIsSuccess());



        // test RoomResponsibleOrgInfo
        RoomResponsibleOrgInfo expectedRespInfo = new RoomResponsibleOrgInfo();
        RoomResponsibleOrgInfo actualRespInfo = roomService.createRoomResponsibleOrg(actualRoom.getId(),"testOrgId", "testOrgType", expectedRespInfo, contextInfo);
        assertNotNull(actualRespInfo);
        expectedRespInfo = roomService.getRoomResponsibleOrg(actualRespInfo.getOrgId(), contextInfo);
        assertNotNull(expectedRespInfo);
        assertEquals(expectedRespInfo.getId(), actualRespInfo.getId());
        actualRespInfo = roomService.updateRoomResponsibleOrg("updateOrgId", expectedRespInfo,contextInfo);
        assertNotNull(actualRespInfo);
        assert(actualRespInfo.getOrgId().equals("updateOrgId"));
        StatusInfo deleteResp = roomService.deleteRoomResponsibleOrg(actualRespInfo.getOrgId(), contextInfo);
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
