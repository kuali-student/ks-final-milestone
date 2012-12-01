package org.kuali.student.r2.core.room.service.impl;

import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomFixedResourceInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.dto.RoomUsageInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: Gordon
 * Date: 11/8/12
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:room-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestRoomServiceImpl {
    @Resource
    private RoomService roomService;

    private static ContextInfo contextInfo;
    private static ContextInfo t1 = new ContextInfo(); //test no principalId
    private static ContextInfo t2 = new ContextInfo(); //test no currentDate

    @BeforeClass
    public static void beforeClass() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("JUnitTest");
        contextInfo.setAuthenticatedPrincipalId("JUnitTest");

        t2.setPrincipalId("principalId");
        t2.setCurrentDate( null );
    }

    @Ignore
    private BuildingInfo createBuildingInfo() {
        return createBuildingInfo("ARM", "1", "Armory", "org.kuali.building.state.Active", "org.kuali.building.type.Miscellaneous", "plain description", "formatted description");
    }

    @Ignore
    private BuildingInfo createBuildingInfo(String buildingCode, String campusKey, String name, String stateKey, String typeKey, String plain, String formatted) {
        BuildingInfo buildingInfo = new BuildingInfo();

        buildingInfo.setBuildingCode(buildingCode);
        buildingInfo.setCampusKey(campusKey);
        buildingInfo.setDescr(new RichTextInfo(plain, formatted));
        buildingInfo.setName(name);
        buildingInfo.setStateKey(stateKey);
        buildingInfo.setTypeKey(typeKey);

        return buildingInfo;
    }

    @Ignore
    private RoomInfo createRoomInfo(BuildingInfo building, String floor, String roomCode, String name, String state, String type, String descPlain, String descFormatted) {
        RoomInfo room = new RoomInfo();

        room.setBuildingId(building.getId());
        room.setFloor(floor);
        room.setRoomCode(roomCode);
        room.setName(name);
        room.setStateKey(state);
        room.setTypeKey(type);
        room.setDescr( new RichTextInfo(descPlain, descFormatted) );

        return room;
    }

    @Ignore
    private RoomResponsibleOrgInfo createRoomResponsibleOrgInfo(String roomId, String orgId, String state, String type, Date effDate, Date expDate) {
        RoomResponsibleOrgInfo roomResponsibleOrgInfo = new RoomResponsibleOrgInfo();

        roomResponsibleOrgInfo.setRoomId(roomId);
        roomResponsibleOrgInfo.setOrgId(orgId);
        roomResponsibleOrgInfo.setStateKey(state);
        roomResponsibleOrgInfo.setTypeKey(type);
        roomResponsibleOrgInfo.setEffectiveDate( effDate );
        roomResponsibleOrgInfo.setExpirationDate( expDate );

        return roomResponsibleOrgInfo;
    }

    @Ignore
    private BuildingInfo addAttributeInfo(BuildingInfo buildingInfo, String name, String value) {
        if (buildingInfo.getAttributes() == null) {
            buildingInfo.setAttributes(new ArrayList<AttributeInfo>(1));
        }

        buildingInfo.getAttributes().add( new AttributeInfo(name, value) );

        return buildingInfo;
    }

    @Ignore
    private RoomInfo addAttributeInfo(RoomInfo room, String name, String value) {
        if (room.getAttributes() == null) {
            room.setAttributes( new ArrayList<AttributeInfo>(1));
        }

        room.getAttributes().add(new AttributeInfo(name, value));

        return room;
    }

    @Ignore RoomResponsibleOrgInfo addAttributeInfo(RoomResponsibleOrgInfo info, String name, String value) {
        if (info.getAttributes() == null) {
            info.setAttributes( new ArrayList<AttributeInfo>(1));
        }

        info.getAttributes().add(new AttributeInfo(name, value));

        return info;
    }

    @Ignore
    public boolean equals(BuildingInfo b1, BuildingInfo b2) {
        assertEquals(b1.getBuildingCode(), b2.getBuildingCode());
        assertEquals(b1.getCampusKey(), b2.getCampusKey());
        assertEquals(b1.getId(), b2.getId());
        assertEquals(b1.getName(), b2.getName());
        assertEquals(b1.getStateKey(), b2.getStateKey());
        assertEquals(b1.getTypeKey(), b2.getTypeKey());

        MetaInfo m1 = b1.getMeta();
        MetaInfo m2 = b2.getMeta();
        assertEquals(m1.getCreateId(), m2.getCreateId());
        assertEquals(m1.getCreateTime(), m2.getCreateTime());
        assertEquals(m1.getUpdateId(), m2.getUpdateId());
        assertEquals(m1.getUpdateTime(), m2.getUpdateTime());

        RichTextInfo r1 = b1.getDescr();
        RichTextInfo r2 = b2.getDescr();
        assertEquals(r1.getFormatted(), r2.getFormatted());
        assertEquals(r1.getPlain(), r2.getPlain());

        assertEquals(b1.getAttributes().size(), b2.getAttributes().size());

        return true;
    }

    @Test
    public void testCreateBuilding() {
        BuildingInfo buildingInfo = createBuildingInfo();

        //test 1, exception if no ContextInfo
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid values in ContextInfo
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid value in contextInfo
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }


        //exception if no BuildingInfo
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //ok if no TypeKey specified
        try {
            roomService.createBuilding(null, buildingInfo, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //ok if blank TypeKey specified
        try {
            roomService.createBuilding("", buildingInfo, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //typical usage
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //ok if new type key specified
        try {
            BuildingInfo buildingInfo1 = roomService.createBuilding("org.kuali.building.type.Extracurricular", buildingInfo, contextInfo);
            assertEquals(buildingInfo.getName(), buildingInfo1.getName() );
            assertFalse(buildingInfo1.getTypeKey().equals(buildingInfo.getTypeKey()));
        } catch (Throwable t) {
            fail(t.toString());
        }
    }

    @Test
    public void testGetBuilding() {
        contextInfo.setCurrentDate( new Date() );
        contextInfo.setPrincipalId("createBuilding");
        BuildingInfo buildingInfo = createBuildingInfo();
        try {
            buildingInfo = roomService.createBuilding("", buildingInfo, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            roomService.getBuilding(buildingInfo.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            roomService.getBuilding(buildingInfo.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            roomService.getBuilding(buildingInfo.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            roomService.getBuilding(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            roomService.getBuilding("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        BuildingInfo getBuildingInfo = null;

        try {
            getBuildingInfo = roomService.getBuilding(buildingInfo.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        assertTrue(equals(buildingInfo, getBuildingInfo));
    }

    @Test
    public void testUpdateBuilding() {
        contextInfo.setCurrentDate( new Date() );
        contextInfo.setPrincipalId( "createBuilding" );
        BuildingInfo buildingInfo = null;
        try {
            buildingInfo = roomService.createBuilding("", createBuildingInfo(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        BuildingInfo originalBuildingInfo = null;
        try {
            originalBuildingInfo = roomService.getBuilding(buildingInfo.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test missing ContextInfo
        try {
            roomService.updateBuilding(buildingInfo.getId(), buildingInfo, null);
            fail("Expected exception not thrown"); //shouldn't get here
        } catch(MissingParameterException e) {
            //exception we're expecting
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test no principal id
        try {
            roomService.updateBuilding(buildingInfo.getTypeKey(), buildingInfo, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test no current date
        try {
            roomService.updateBuilding(buildingInfo.getTypeKey(), buildingInfo, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //exception if no BuildingInfo
        try {
            roomService.updateBuilding(null, null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test 5, an actual update
        buildingInfo.setBuildingCode( buildingInfo.getBuildingCode() + "_new");
        buildingInfo.setCampusKey( buildingInfo.getCampusKey() + "_new");
        buildingInfo.setDescr( new RichTextInfo(buildingInfo.getDescr().getPlain() + "_new", buildingInfo.getDescr().getFormatted() + "_new"));
        buildingInfo.setName( buildingInfo.getName() + "_new");
        buildingInfo.setStateKey("org.kuali.building.state.Inactive");
        buildingInfo.setTypeKey("org.kuali.building.type.Recreational");

        contextInfo.setPrincipalId("testUpdateBuilding");
        //sleep for 1 sec to allow Time to change
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        contextInfo.setCurrentDate(new Date());


        BuildingInfo newBuildingInfo = null;
        try {
            newBuildingInfo = roomService.updateBuilding(buildingInfo.getId(), buildingInfo, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(originalBuildingInfo.getId(), newBuildingInfo.getId());

        MetaInfo metaInfo = originalBuildingInfo.getMeta();
        MetaInfo newMetaInfo = newBuildingInfo.getMeta();
        assertEquals(newMetaInfo.getCreateId(), metaInfo.getCreateId());
        assertEquals(newMetaInfo.getCreateTime(), metaInfo.getCreateTime());
        assertFalse(newMetaInfo.getUpdateId().equals(metaInfo.getUpdateId()));
        assertFalse(newMetaInfo.getUpdateTime().equals(metaInfo.getUpdateTime()));

        assertFalse(originalBuildingInfo.getBuildingCode().equals(buildingInfo.getBuildingCode()));
        assertFalse(originalBuildingInfo.getCampusKey().equals(buildingInfo.getCampusKey()));
        assertFalse(originalBuildingInfo.getDescr().getFormatted().equals(buildingInfo.getDescr().getFormatted()));
        assertFalse(originalBuildingInfo.getDescr().getPlain().equals(buildingInfo.getDescr().getPlain()));
        assertFalse(originalBuildingInfo.getName().equals(buildingInfo.getName()));
        assertFalse(originalBuildingInfo.getStateKey().equals(buildingInfo.getStateKey()));
        assertFalse(originalBuildingInfo.getTypeKey().equals(buildingInfo.getTypeKey()));

        //ok if no Id specified
        //sleep for 1 sec to allow Time to change
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        contextInfo.setCurrentDate(new Date());
        BuildingInfo newNewBuildingInfo = null;
        try {
            newNewBuildingInfo = roomService.updateBuilding(null, newBuildingInfo, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertFalse(newNewBuildingInfo.getMeta().getUpdateTime().equals(newBuildingInfo.getMeta().getUpdateTime()));

        //test ok if Id is blank
        //sleep for 1 sec to allow Time to change
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        contextInfo.setCurrentDate(new Date());
        BuildingInfo newNewNewBuildingInfo = null;
        try {
            newNewNewBuildingInfo = roomService.updateBuilding("", newNewBuildingInfo, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertFalse(newNewNewBuildingInfo.getMeta().getUpdateTime().equals(newNewBuildingInfo.getMeta().getUpdateTime()));
    }

    @Test
    public void testGetBuildingIdsbyCampus() {
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "2", "Armory", "active", "administration", "Mitchell", "Mitchell");

        try {
            b1 = roomService.createBuilding(null, b1, contextInfo);
            b1 = roomService.getBuilding(b1.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            b2 = roomService.createBuilding(null, b2, contextInfo);
            b2 = roomService.getBuilding(b2.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null contextInfo
        try {
            roomService.getBuildingIdsByCampus("1", null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid principalId
        try {
            roomService.getBuildingIdsByCampus("1", t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid currentDate
        try {
            roomService.getBuildingIdsByCampus("1", t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null campusKey
        try {
            roomService.getBuildingIdsByCampus(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we are expecting
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test blank campusKey
        try {
            roomService.getBuildingIdsByCampus("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we are expecting
        } catch (Throwable t) {
            fail(t.toString());
        }


        //test no results
        try {
            List<String> ids = roomService.getBuildingIdsByCampus("3", contextInfo);
            assertEquals(0, ids.size());
        } catch (DoesNotExistException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test normal search
        try {
            List<String> ids = roomService.getBuildingIdsByCampus("1", contextInfo);
            assertEquals(ids.size(), 1);
        } catch (Throwable t) {
            fail(t.toString());
        }
    }

    @Test
    public void testGetBuildingsByIds() {
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "1", "Armory", "active", "administration", "Mitchell", "Mitchell");

        try {
            b1 = roomService.createBuilding(null, b1, contextInfo);
            b1 = roomService.getBuilding(b1.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            b2 = roomService.createBuilding(null, b2, contextInfo);
            b2 = roomService.getBuilding(b2.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        List<String> ids = new ArrayList<String>(2);
        //test null contextInfo
        try {
            roomService.getBuildingsByIds(ids, null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid principalId
        try {
            roomService.getBuildingsByIds(ids, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid date
        try {
            roomService.getBuildingsByIds(ids, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null list
        try {
            roomService.getBuildingsByIds(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test empty list
        try {
            roomService.getBuildingsByIds(ids, contextInfo);
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //normal search
        ids.add(b1.getId());
        ids.add(b2.getId());
        try {
            List<BuildingInfo> buildingInfos = roomService.getBuildingsByIds(ids, contextInfo);
            assertEquals(buildingInfos.size(), 2);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test exception when not all ids are found
        ids.add("3");
        try {
            roomService.getBuildingsByIds(ids, contextInfo);
        } catch (DoesNotExistException e) {
            //this is the exception we are expecting
        } catch (Throwable t) {
            fail(t.toString());
        }
    }

    @Test
    public void testGetBuildingsByBuildingCode() {
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "1", "Armory", "active", "administration", "Mitchell", "Mitchell");

        try {
            b1 = roomService.createBuilding(null, b1, contextInfo);
            b1 = roomService.getBuilding(b1.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            b2 = roomService.createBuilding(null, b2, contextInfo);
            b2 = roomService.getBuilding(b2.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null contextInfo
        try {
            roomService.getBuildingsByBuildingCode(b1.getBuildingCode(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid principalId
        try {
            roomService.getBuildingsByBuildingCode(b1.getBuildingCode(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid date
        try {
            roomService.getBuildingsByBuildingCode(b1.getBuildingCode(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null code
        try {
            roomService.getBuildingsByBuildingCode(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test blank code
        try {
            roomService.getBuildingsByBuildingCode("", contextInfo);
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test when not found
        try {
            roomService.getBuildingsByBuildingCode("ADM", contextInfo);
        } catch (DoesNotExistException e) {
            //this is the exception we are expecting
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test normal search
        try {
            List<BuildingInfo> list = roomService.getBuildingsByBuildingCode(b2.getBuildingCode(), contextInfo);
            assertEquals(1, list.size());
        } catch (Throwable t) {
            fail(t.toString());
        }
    }

    @Test
    public void testDeleteBuilding() {
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "1", "Armory", "active", "administration", "Mitchell", "Mitchell");

        try {
            b1 = roomService.createBuilding(null, b1, contextInfo);
            b1 = roomService.getBuilding(b1.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            b2 = roomService.createBuilding(null, b2, contextInfo);
            b2 = roomService.getBuilding(b2.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null contextInfo
        try {
            roomService.deleteBuilding(b1.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid principalId
        try {
            roomService.deleteBuilding(b1.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e){
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid currentDate
        try {
            roomService.deleteBuilding(b1.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null key
        try {
            roomService.deleteBuilding(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test blank key
        try {
            roomService.deleteBuilding("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //normal delete
        try {
            StatusInfo statusInfo = roomService.deleteBuilding(b1.getId(), contextInfo);
            assertTrue(statusInfo.getIsSuccess());
        } catch (Throwable t) {
            fail(t.toString());
        }

        //double-check record was deleted
        try {
            List<String> ids = roomService.getBuildingIdsByCampus("1", contextInfo);
            assertEquals(1, ids.size());
        } catch (Throwable t) {
            fail(t.toString());
        }
    }

    @Test
    public void testBuildingAttributes() {
        contextInfo.setPrincipalId("testBuildingAttributes");
        contextInfo.setCurrentDate( new Date() );

        //create a building
        BuildingInfo b1 = null;
        try {
            b1 = roomService.createBuilding("", createBuildingInfo("ADM", "umuc", "Administration Building", "active", "administration", "the administration building", "the administration building"), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //give it attributes and update
        b1 = addAttributeInfo(b1, "key1", "value1");
        b1 = addAttributeInfo(b1, "key2", "value2");
        BuildingInfo b2 = null;
        try {
            b2 = roomService.updateBuilding(b1.getId(), b1, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        List<AttributeInfo> a2 = b2.getAttributes();
        assertEquals(a2.size(), 2);
        HashMap<String, String> m2 = new HashMap<String, String>(2);
        for (AttributeInfo ai : a2) {
            m2.put(ai.getKey(), ai.getValue());
        }
        assertEquals(m2.get("key1"), "value1");
        assertEquals(m2.get("key2"), "value2");

        //remove attributes and update
        b2.setAttributes(new ArrayList<AttributeInfo>());
        b2 = addAttributeInfo(b2, "key3", "value3");
        BuildingInfo b3 = null;
        try {
            b3 = roomService.updateBuilding(b2.getId(), b2, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(b3.getAttributes().size(), 1);
        assertEquals(b3.getAttributes().get(0).getKey(), "key3");
        assertEquals(b3.getAttributes().get(0).getValue(), "value3");
    }

    @Test
    public void testCreateRoom() {
        contextInfo.setPrincipalId("testCreateRoom");
        contextInfo.setCurrentDate( new Date() );

        String typeKey = "org.kuali.room.type.Lecture";
        String floor = "1";
        String roomCode = "1234";
        String name = "classroom 1";
        String state = "org.kuali.room.state.Active";
        String plain = "classroom for lectures";
        String formatted = "classroom for lectures";
        BuildingInfo b = null;
        try {
            b = roomService.createBuilding("", createBuildingInfo("ADM", "umuc", "Administration Building", "active", "administration", "the administration building", "the administration building"), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        RoomInfo r = createRoomInfo(b, floor, roomCode, name, state, typeKey, plain, formatted);

        //test null contextInfo
        try {
            roomService.createRoom(b.getId(), r.getTypeKey(), r, null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid principalId
        try {
            roomService.createRoom(b.getId(), r.getTypeKey(), r, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid date
        try {
            roomService.createRoom(b.getId(), r.getTypeKey(), r, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null roomInfo
        try {
            roomService.createRoom(b.getId(), r.getTypeKey(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null typeKey
        RoomInfo r2 = null;
        try {
            r2 = roomService.createRoom(b.getId(), null, r, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(r2.getBuildingId(), r.getBuildingId());
        assertEquals(r2.getTypeKey(), r.getTypeKey());
        assertEquals(r2.getFloor(), r.getFloor());
        assertEquals(r2.getRoomCode(), r.getRoomCode());
        assertEquals(r2.getDescr().getPlain(), r.getDescr().getPlain());
        assertEquals(r2.getDescr().getFormatted(), r.getDescr().getFormatted());

        //test blank typeKey
        r2 = null;
        try {
            r2 = roomService.createRoom(b.getId(), "", r, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(r2.getTypeKey(), r.getTypeKey());

        //test new typeKey
        r2 = null;
        String newTypeKey = r.getTypeKey() + "_new";
        try {
            r2 = roomService.createRoom(b.getId(), newTypeKey, r, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(r2.getTypeKey(), newTypeKey);

        //test null buildingId
        r2 = null;
        try {
            r2 = roomService.createRoom(null, r.getTypeKey(), r, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(r2.getBuildingId(), r.getBuildingId());

        //test blank buildingId
        r2 = null;
        try {
            r2 = roomService.createRoom("", r.getTypeKey(), r, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(r2.getBuildingId(), r.getBuildingId());

        //test new buildingId
        r2 = null;
        String newBuildingId = b.getId() + "_new";
        try {
            r2 = roomService.createRoom(newBuildingId, r.getTypeKey(), r, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(r2.getBuildingId(), newBuildingId);
    }

    @Test
    public void testGetRoom() {
        contextInfo.setPrincipalId("testGetRoom");
        contextInfo.setCurrentDate( new Date() );

        BuildingInfo b = null;
        try {
            b = roomService.createBuilding("", createBuildingInfo("ADM", "umuc", "Administration Building", "active", "administration", "the administration building", "the administration building"), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        RoomInfo room = createRoomInfo(b, "1", "1234", "classroom 1", "org.kuali.room.state.Active", "org.kuali.room.type.Lecture", "classroom for lectures", "classroom for lectures");
        try {
            room = roomService.createRoom(b.getId(), "", room, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null ContextInfo
        try {
            roomService.getRoom(room.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid principalId
        try {
            roomService.getRoom(room.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid date
        try {
            roomService.getRoom(room.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null roomId
        try {
            roomService.getRoom(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test blank roomId
        try {
            roomService.getRoom("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test does not exist
        try {
            roomService.getRoom("DoesNotExist", contextInfo);
        } catch (DoesNotExistException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            t.printStackTrace();
            fail(t.toString());
        }

        //normal use
        RoomInfo r = null;
        try {
            r = roomService.getRoom(room.getId(), contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            fail(t.toString());
        }
        assertNotNull(r);
        assertEquals(r.getId(), room.getId() );
        assertEquals(r.getBuildingId(), room.getBuildingId() );
        assertEquals(r.getFloor(), room.getFloor() );
        assertEquals(r.getRoomCode(), room.getRoomCode() );
        assertEquals(r.getDescr().getFormatted(),  room.getDescr().getFormatted() );
        assertEquals(r.getDescr().getPlain(), room.getDescr().getPlain() );
    }

    @Test
    public void testUpdateRoom() {
        contextInfo.setCurrentDate( new Date() );
        contextInfo.setPrincipalId( "testCreateRoom" );

        BuildingInfo b = null;
        try {
            b = roomService.createBuilding("", createBuildingInfo(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        RoomInfo r = createRoomInfo(b, "floor", "1234", "The Room", "Active", "Classroom", "plain", "formatted");
        try {
            r = roomService.createRoom(b.getId(), r.getTypeKey(), r, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        RoomInfo originalRoomInfo = null;
        try {
            originalRoomInfo = roomService.getRoom(r.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertNotNull(originalRoomInfo);

        //test missing ContextInfo
        try {
            roomService.updateRoom(r.getId(), r, null);
            fail("Expected exception not thrown"); //shouldn't get here
        } catch(MissingParameterException e) {
            //exception we're expecting
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test no principal id
        try {
            roomService.updateRoom(r.getId(), r, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test no current date
        try {
            roomService.updateRoom(r.getId(), r, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null RoomInfo
        try {
            roomService.updateRoom(r.getId(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test normal update
        contextInfo.setPrincipalId("testUpdateRoom");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        contextInfo.setCurrentDate( new Date() );
        r.setBuildingId( r.getBuildingId() + "_new");
        r.setFloor( r.getFloor() + "_new");
        r.setRoomCode( r.getRoomCode() + "_new");
        r.setDescr( new RichTextInfo(r.getDescr().getPlain() + "_new", r.getDescr().getFormatted() + "_new"));
        r.setName( r.getName() + "_new");
        r.setTypeKey( r.getTypeKey() + "_new");
        r.setStateKey( r.getStateKey() + "_new");
        RoomInfo r2 = null;
        try {
            r2 = roomService.updateRoom(r.getId(), r, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertNotNull(r2);
        assertEquals(r2.getId(), originalRoomInfo.getId());
        assertFalse(r2.getBuildingId().equals(originalRoomInfo.getBuildingId()));
        assertFalse(r2.getFloor().equals(originalRoomInfo.getFloor()));
        assertFalse(r2.getRoomCode().equals(originalRoomInfo.getRoomCode()));
        assertFalse(r2.getName().equals(originalRoomInfo.getName()));
        assertFalse(r2.getStateKey().equals(originalRoomInfo.getStateKey()));
        assertFalse(r2.getTypeKey().equals(originalRoomInfo.getTypeKey()));
        MetaInfo m1 = originalRoomInfo.getMeta();
        MetaInfo m2 = r2.getMeta();
        assertEquals(m1.getCreateTime(), m2.getCreateTime());
        assertEquals(m1.getCreateId(), m2.getCreateId());
        assertFalse(m1.getUpdateId().equals(m2.getUpdateId()));
        assertFalse(m1.getUpdateTime().equals(m2.getUpdateTime()));

        //test update with null roomId
        try {
            roomService.updateRoom(null, r2, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        //test update with blank roomId
        try {
            roomService.updateRoom("", r2, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

    }

    @Test
    public void testSearchAndDeleteRoom() {
        contextInfo.setPrincipalId("testSearchAndDeleteRoom");
        contextInfo.setCurrentDate( new Date() );
        //create some buildings
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "1", "Armory", "active", "administration", "Mitchell", "Mitchell");
        try {
            b1 = roomService.createBuilding(null, b1, contextInfo);
            b2 = roomService.createBuilding(null, b2, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //create some rooms
        RoomInfo r1 = createRoomInfo(b1, "1", "100", "War Room", "org.kuali.room.state.Active", "org.kuali.room.type.Classroom", "the war room", "the war room");
        RoomInfo r2 = createRoomInfo(b1, "2", "200", "Peace Room", "org.kuali.room.state.Active", "org.kuali.room.type.Classroom", "the peace room", "the peace room");
        RoomInfo r3 = createRoomInfo(b2, "3", "300", "North Room", "org.kuali.room.state.Active", "org.kuali.room.type.LectureHall", "the north room", "the north room");
        RoomInfo r4 = createRoomInfo(b2, "4", "405", "South Room", "org.kuali.room.state.Inactive", "org.kuali.room.type.ExecutionChamber", "the execution room", "the execution room");

        try {
            roomService.createRoom(r1.getBuildingId(), r1.getTypeKey(), r1, contextInfo);
            roomService.createRoom(r2.getBuildingId(), r2.getTypeKey(), r2, contextInfo);
            roomService.createRoom(r3.getBuildingId(), r3.getTypeKey(), r3, contextInfo);
            roomService.createRoom(r4.getBuildingId(), r4.getTypeKey(), r4, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }


        testGetRoomIdsByBuilding(b1, b2, r1, r2, r3, r4);

        testGetRoomIdsByBuildingAndFloor(b1, b2, r1, r2, r3, r4);

        testGetRoomIdsByBuildingAndRoomType(b1, b2, r1, r2, r3, r4);

        testGetRoomIdsByBuildingAndRoomTypes(b1, b2, r1, r2, r3, r4);

        testGetRoomIdsByType(r1, r2, r3, r4);

        testGetRoomsByBuildingAndRoomCode(b1, b2, r1, r2, r3, r4);
    }

    @Ignore
    private void testGetRoomIdsByBuilding(BuildingInfo b1, BuildingInfo b2, RoomInfo r1, RoomInfo r2, RoomInfo r3, RoomInfo r4) {
        //test for null context
        try {
            roomService.getRoomIdsByBuilding(b1.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null principalId
        try {
            roomService.getRoomIdsByBuilding(b1.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null currentDate
        try {
            roomService.getRoomIdsByBuilding(b1.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null buildingId
        try {
            roomService.getRoomIdsByBuilding(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for blank buildingId
        try {
            roomService.getRoomIdsByBuilding("", t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for no matches
        try {
            List<String> ids = roomService.getRoomIdsByBuilding("doesnotmatch", contextInfo);
            assertEquals(0, ids.size());
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for matches
        List<String> ids = null;
        try {
            ids = roomService.getRoomIdsByBuilding(b1.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(ids.size(), 2);
    }

    @Ignore
    private void testGetRoomIdsByBuildingAndFloor(BuildingInfo b1, BuildingInfo b2, RoomInfo r1, RoomInfo r2, RoomInfo r3, RoomInfo r4) {
        //test for NULL context
        try {
            roomService.getRoomIdsByBuildingAndFloor(b2.getId(), r3.getFloor(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null principalId
        try {
            roomService.getRoomIdsByBuildingAndFloor(b2.getId(), r3.getFloor(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null date
        try {
            roomService.getRoomIdsByBuildingAndFloor(b2.getId(), r3.getFloor(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null floor
        try {
            roomService.getRoomIdsByBuildingAndFloor(b2.getId(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for empty floor
        try {
            roomService.getRoomIdsByBuildingAndFloor(b2.getId(), "", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null buildingId
        try {
            roomService.getRoomIdsByBuildingAndFloor(null, r3.getFloor(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for empty buildingId
        try {
            roomService.getRoomIdsByBuildingAndFloor("", r3.getFloor(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        List<String> ids;
        //test for no matches
        try {
            ids = roomService.getRoomIdsByBuildingAndFloor(b1.getId(), r3.getFloor(), contextInfo);
            assertEquals(0, ids.size());
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test normal
        ids = null;
        try {
            ids = roomService.getRoomIdsByBuildingAndFloor(b2.getId(), r4.getFloor(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(ids.size(), 1);

    }

    @Ignore
    private void testGetRoomIdsByBuildingAndRoomType(BuildingInfo b1, BuildingInfo b2, RoomInfo r1, RoomInfo r2, RoomInfo r3, RoomInfo r4) {
        //test for NULL context
        try {
            roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), r4.getTypeKey(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null principalId
        try {
            roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), r4.getTypeKey(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null date
        try {
            roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), r4.getTypeKey(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null typeKey
        try {
            roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for empty typeKey
        try {
            roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), "", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null buildingId
        try {
            roomService.getRoomIdsByBuildingAndRoomType(null, r3.getTypeKey(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for empty buildingId
        try {
            roomService.getRoomIdsByBuildingAndRoomType("", r3.getTypeKey(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        List<String> ids;
        //test for no matches
        try {
            ids = roomService.getRoomIdsByBuildingAndRoomType(b1.getId(), r3.getTypeKey(), contextInfo);
            assertEquals(0, ids.size());
        } catch (Throwable t) {
            fail(t.toString());
        }

        ids = null;
        try {
            ids = roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), r4.getTypeKey(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(ids.size(), 1);
    }

    @Ignore
    private void testGetRoomIdsByBuildingAndRoomTypes(BuildingInfo b1, BuildingInfo b2, RoomInfo r1, RoomInfo r2, RoomInfo r3, RoomInfo r4) {
        List<String> typeKeys = new ArrayList<String>(2);
        typeKeys.add(r1.getTypeKey());
        typeKeys.add(r2.getTypeKey());

        List<String> ids = null;

        //test for NULL context
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), typeKeys, null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null principalId
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), typeKeys, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null date
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), typeKeys, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null typeKey
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for empty typeKey
        try {
            ids = roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), new ArrayList<String>(0), contextInfo);
            fail("Expected exception not thrown");
        } catch(MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null buildingId
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(null, typeKeys, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for empty buildingId
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes("", typeKeys, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for no results
        try {
            ids = roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), typeKeys, contextInfo);
            assertEquals(0, ids.size());
        } catch (Throwable t) {
            fail(t.toString());
        }

        //normal use
        try {
            ids = roomService.getRoomIdsByBuildingAndRoomTypes(b1.getId(), typeKeys, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(ids.size(), 2);
    }

    @Ignore
    private void testGetRoomIdsByType(RoomInfo r1, RoomInfo r2, RoomInfo r3, RoomInfo r4) {

        //test null context
        try {
            roomService.getRoomIdsByType(r1.getTypeKey(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null principalId
        try {
            roomService.getRoomIdsByType(r2.getTypeKey(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null date
        try {
            roomService.getRoomIdsByType(r3.getTypeKey(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null type
        try {
            roomService.getRoomIdsByType(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for empty type
        try {
            roomService.getRoomIdsByType("", contextInfo);
            fail("Expected exception not thrown");
        } catch(MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for no results
        try {
            List<String> ids = roomService.getRoomIdsByType(r4.getTypeKey() + "_not", contextInfo);
            assertEquals(0, ids.size());
        } catch (Throwable t) {
            fail(t.toString());
        }

        //normal use
        try {
            List<String> ids = roomService.getRoomIdsByType(r1.getTypeKey(), contextInfo);
            assertEquals(2, ids.size());
        } catch (Throwable t) {
            fail(t.toString());
        }
    }

    @Ignore
    private void testGetRoomsByBuildingAndRoomCode(BuildingInfo b1, BuildingInfo b2, RoomInfo r1, RoomInfo r2, RoomInfo r3, RoomInfo r4) {

        //test for NULL context
        try {
            roomService.getRoomsByBuildingAndRoomCode(b1.getBuildingCode(), r1.getRoomCode(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null principalId
        try {
            roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), r1.getRoomCode(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null date
        try {
            roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), r1.getRoomCode(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null roomCode
        try {
            roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for empty roomCode
        try {
            roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), "", contextInfo);
            fail("Expected exception not thrown");
        } catch(MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for null buildingCode
        try {
            roomService.getRoomsByBuildingAndRoomCode(null, r2.getRoomCode(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for empty buildingCode
        try {
            roomService.getRoomsByBuildingAndRoomCode("", r2.getRoomCode(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test for no results
        try {
            List<RoomInfo> rooms = roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), r2.getRoomCode(), contextInfo);
            assertEquals(0, rooms.size());
        } catch (DoesNotExistException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }


        //normal use
        try {
            List<RoomInfo> rooms = roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), r3.getRoomCode(), contextInfo);
            assertEquals(1, rooms.size());
        } catch (Throwable t) {
            fail(t.toString());
        }
    }

    @Test
    public void testCreateRoomResponsibleOrg() {
        contextInfo.setPrincipalId("testCreateRoomResponsibleOrg");
        contextInfo.setCurrentDate( new Date() );

        String type = "kuali.roomresponsibleorg.type.Owner";
        String roomId = "1";
        String orgId = "1";
        String state = "kuali.roomresponsibleorg.state.Active";
        Date effDate = new Date( new Date().getTime() - 86400);
        Date expDate = new Date( new Date().getTime() + 86400);
        RoomResponsibleOrgInfo i = createRoomResponsibleOrgInfo(roomId, orgId, state, type, effDate, expDate);

        //test null contextInfo
        try {
            roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), i, null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid principalId
        try {
            roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), i, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid date
        try {
            roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), i, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null info
        try {
            roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null typeKey
        RoomResponsibleOrgInfo i2 = null;
        try {
            i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), null, i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(i2.getOrgId(), i.getOrgId());
        assertEquals(i2.getTypeKey(), i.getTypeKey());
        assertEquals(i2.getRoomId(), i.getRoomId());
        assertEquals(i2.getEffectiveDate(), i.getEffectiveDate());
        assertEquals(i2.getExpirationDate(), i.getExpirationDate());
        assertEquals(i2.getStateKey(), i.getStateKey());

        //test blank typeKey
        i2 = null;
        try {
            i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), "", i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(i2.getTypeKey(), i.getTypeKey());

        //test new typeKey
        i2 = null;
        String newTypeKey = i.getTypeKey() + "_new";
        try {
            i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), newTypeKey, i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(i2.getTypeKey(), newTypeKey);

        //test null orgId
        i2 = null;
        try {
            i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), null, i.getTypeKey(), i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(i2.getOrgId(), i.getOrgId());

        //test blank orgId
        i2 = null;
        try {
            i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), "", i.getTypeKey(), i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(i2.getOrgId(), i.getOrgId());

        //test new orgId
        i2 = null;
        String newOrgId = i.getOrgId() + "_new";
        try {
            i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), newOrgId, i.getTypeKey(), i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(i2.getOrgId(), newOrgId);

        //test null roomId
        i2 = null;
        try {
            i2 = roomService.createRoomResponsibleOrg(null, i.getOrgId(), i.getTypeKey(), i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(i2.getRoomId(), i.getRoomId());

        //test blank orgId
        i2 = null;
        try {
            i2 = roomService.createRoomResponsibleOrg("", i.getOrgId(), i.getTypeKey(), i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(i2.getRoomId(), i.getRoomId());

        //test new orgId
        i2 = null;
        String newRoomId = i.getRoomId() + "_new";
        try {
            i2 = roomService.createRoomResponsibleOrg(newRoomId, i.getOrgId(), i.getTypeKey(), i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(i2.getRoomId(), newRoomId);

        //test nominal usage
        i2 = null;
        try {
            i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(i2.getRoomId(), roomId);
        assertEquals(i2.getOrgId(), orgId);
        assertEquals(i2.getTypeKey(), type);
        assertEquals(i2.getStateKey(), state);
        assertEquals(i2.getEffectiveDate(), effDate);
        assertEquals(i2.getExpirationDate(), expDate);
    }

    @Test
    public void testGetRoomResponsibleOrg() {
        contextInfo.setPrincipalId("testGetRoomResponsibleOrg");
        contextInfo.setCurrentDate(new Date());

        String type = "kuali.roomresponsibleorg.type.Owner";
        String roomId = "1";
        String orgId = "1";
        String state = "kuali.roomresponsibleorg.state.Active";
        Date effDate = new Date( new Date().getTime() - 86400);
        Date expDate = new Date( new Date().getTime() + 86400);
        RoomResponsibleOrgInfo i = createRoomResponsibleOrgInfo(roomId, orgId, state, type, effDate, expDate);

        try {
            i = roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        //test null contextInfo
        RoomResponsibleOrgInfo i2 = null;
        try {
            i2 = roomService.getRoomResponsibleOrg(i.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid principalId
        try {
            i2 = roomService.getRoomResponsibleOrg(i.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid date
        try {
            i2 = roomService.getRoomResponsibleOrg(i.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null id
        try {
            roomService.getRoomResponsibleOrg(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test blank id
        try {
            roomService.getRoomResponsibleOrg("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //nominal
        try {
            i2 = roomService.getRoomResponsibleOrg(i.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        assertEquals(i2.getId(), i.getId());
        assertEquals(i2.getOrgId(), i.getOrgId());
        assertEquals(i2.getRoomId(), i.getRoomId());
        assertEquals(i2.getEffectiveDate(), i.getEffectiveDate());
        assertEquals(i2.getExpirationDate(), i.getExpirationDate());
        assertEquals(i2.getStateKey(), i.getStateKey());
        assertEquals(i2.getTypeKey(), i.getTypeKey());
    }

    @Test
    public void testUpdateRoomResponsibleOrg() {
        contextInfo.setCurrentDate( new Date() );
        contextInfo.setPrincipalId( "createRoomResponsibleOrg" );

        String roomId = "roomId";
        String orgId = "orgId";
        String state = "state";
        String type = "type";
        Date effDate = new Date( new Date().getTime() - 86400);
        Date expDate = new Date( new Date().getTime() + 86400);

        RoomResponsibleOrgInfo i = null;
        try {
            i = roomService.createRoomResponsibleOrg("", "", "", createRoomResponsibleOrgInfo(roomId, orgId, state, type, effDate, expDate), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        RoomResponsibleOrgInfo originalInfo = null;
        try {
            originalInfo = roomService.getRoomResponsibleOrg(i.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test missing ContextInfo
        try {
            roomService.updateRoomResponsibleOrg(i.getId(), i, null);
            fail("Expected exception not thrown"); //shouldn't get here
        } catch(MissingParameterException e) {
            //exception we're expecting
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test no principal id
        try {
            roomService.updateRoomResponsibleOrg(i.getId(), i, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test no current date
        try {
            roomService.updateRoomResponsibleOrg(i.getId(), i, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //exception if no RoomResponsibleOrgInfo
        try {
            roomService.updateRoomResponsibleOrg(i.getId(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test 5, an actual update
        i.setTypeKey(i.getTypeKey() + "_new");
        i.setStateKey(i.getStateKey() + "_new");
        i.setOrgId(i.getOrgId() + "_new");
        i.setRoomId(i.getRoomId() + "_new");
        i.setEffectiveDate(new Date(i.getEffectiveDate().getTime() - 86400));
        i.setExpirationDate(new Date(i.getExpirationDate().getTime() + 86400));

        contextInfo.setPrincipalId("testUpdateRoomResponsibleOrg");
        //sleep for 1 sec to allow Time to change
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        contextInfo.setCurrentDate(new Date());

        RoomResponsibleOrgInfo newInfo = null;
        try {
            newInfo = roomService.updateRoomResponsibleOrg(i.getId(), i, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(originalInfo.getId(), newInfo.getId());

        MetaInfo metaInfo = originalInfo.getMeta();
        MetaInfo newMetaInfo = newInfo.getMeta();
        assertEquals(newMetaInfo.getCreateId(), metaInfo.getCreateId());
        assertEquals(newMetaInfo.getCreateTime(), metaInfo.getCreateTime());
        assertFalse(newMetaInfo.getUpdateId().equals(metaInfo.getUpdateId()));
        assertFalse(newMetaInfo.getUpdateTime().equals(metaInfo.getUpdateTime()));

        assertFalse(originalInfo.getRoomId().equals(i.getRoomId()));
        assertFalse(originalInfo.getStateKey().equals(i.getStateKey()));
        assertFalse(originalInfo.getOrgId().equals(i.getOrgId()));
        assertFalse(originalInfo.getExpirationDate().equals(i.getExpirationDate()));
        assertFalse(originalInfo.getEffectiveDate().equals(i.getEffectiveDate()));
        assertFalse(originalInfo.getTypeKey().equals(i.getTypeKey()));

        //ok if no Id specified
        //sleep for 1 sec to allow Time to change
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        contextInfo.setCurrentDate(new Date());
        RoomResponsibleOrgInfo newnewInfo = null;
        try {
            newnewInfo = roomService.updateRoomResponsibleOrg(null, newInfo, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertFalse(newnewInfo.getMeta().getUpdateTime().equals(newInfo.getMeta().getUpdateTime()));

        //test ok if Id is blank
        //sleep for 1 sec to allow Time to change
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        contextInfo.setCurrentDate(new Date());

        RoomResponsibleOrgInfo newnewnewInfo = null;
        try {
            newnewnewInfo = roomService.updateRoomResponsibleOrg("", newnewInfo, contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertFalse(newnewnewInfo.getMeta().getUpdateTime().equals(newnewInfo.getMeta().getUpdateTime()));

    }

    @Test
    public void testSearchAndDeleteRoomResponsibleOrg() {
        RoomResponsibleOrgInfo i1 = createRoomResponsibleOrgInfo("roomId1", "orgId1", "state1", "type1", new Date(), new Date());
        RoomResponsibleOrgInfo i2 = createRoomResponsibleOrgInfo("roomId1", "orgId2", "state2", "type2", new Date(), new Date());

        try {
            i1 = roomService.createRoomResponsibleOrg(null, null, null, i1, contextInfo);
            i1 = roomService.getRoomResponsibleOrg(i1.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            i2 = roomService.createRoomResponsibleOrg(null, null, null, i2, contextInfo);
            i2 = roomService.getRoomResponsibleOrg(i2.getId(), contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null contextInfo
        try {
            roomService.getRoomResponsibleOrgIdsByRoom("roomId1", null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test empty principalId
        try {
            roomService.getRoomResponsibleOrgIdsByRoom("roomId2", t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch(Throwable t) {
            fail(t.toString());
        }

        //test null date
        try {
            roomService.getRoomResponsibleOrgIdsByRoom("roomId1", t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null id
        try {
            roomService.getRoomResponsibleOrgIdsByRoom(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test blank id
        try {
            roomService.getRoomResponsibleOrgIdsByRoom("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        List<String> ids = null;
        try {
            ids = roomService.getRoomResponsibleOrgIdsByRoom("roomId1", contextInfo);
        } catch (Throwable t) {
            fail(t.toString());
        }
        assertEquals(2, ids.size());

        //test null contextInfo
        try {
            roomService.deleteRoomResponsibleOrg(i2.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid principalId
        try {
            roomService.deleteRoomResponsibleOrg(i1.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e){
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test invalid currentDate
        try {
            roomService.deleteRoomResponsibleOrg(i1.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test null key
        try {
            roomService.deleteRoomResponsibleOrg(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //test blank key
        try {
            roomService.deleteRoomResponsibleOrg("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            //this is the exception we expect
        } catch (Throwable t) {
            fail(t.toString());
        }

        //normal delete
        try {
            StatusInfo statusInfo = roomService.deleteRoomResponsibleOrg(i2.getId(), contextInfo);
            assertTrue(statusInfo.getIsSuccess());
        } catch (Throwable t) {
            fail(t.toString());
        }

        //double-check record was deleted
        ids = null;
        try {
            ids = roomService.getRoomResponsibleOrgIdsByRoom("roomId1", contextInfo);
            assertEquals(1, ids.size());
        } catch (Throwable t) {
            fail(t.toString());
        }

    }


}

