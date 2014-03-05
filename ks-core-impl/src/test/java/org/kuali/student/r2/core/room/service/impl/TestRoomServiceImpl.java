package org.kuali.student.r2.core.room.service.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    private BuildingInfo createBuildingInfo() {
        return createBuildingInfo("ARM", "1", "Armory", "org.kuali.building.state.Active", "org.kuali.building.type.Miscellaneous", "plain description", "formatted description");
    }

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

    private BuildingInfo addAttributeInfo(BuildingInfo buildingInfo, String name, String value) {
        if (buildingInfo.getAttributes() == null) {
            buildingInfo.setAttributes(new ArrayList<AttributeInfo>(1));
        }

        buildingInfo.getAttributes().add( new AttributeInfo(name, value) );

        return buildingInfo;
    }

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
    public void testCreateBuilding() throws Exception {
        BuildingInfo buildingInfo = createBuildingInfo();

        //test 1, exception if no ContextInfo
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test invalid values in ContextInfo
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test invalid value in contextInfo
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }


        //exception if no BuildingInfo
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("Object is missing!", e.getMessage());
        }

        //ok if no TypeKey specified
        roomService.createBuilding(null, buildingInfo, contextInfo);

        //ok if blank TypeKey specified
        roomService.createBuilding("", buildingInfo, contextInfo);

        //typical usage
        roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, contextInfo);

        //ok if new type key specified
        BuildingInfo buildingInfo1 = roomService.createBuilding("org.kuali.building.type.Extracurricular", buildingInfo, contextInfo);
        assertEquals(buildingInfo.getName(), buildingInfo1.getName() );
        assertFalse(buildingInfo1.getTypeKey().equals(buildingInfo.getTypeKey()));
    }

    @Test
    public void testGetBuilding() throws Exception {
        contextInfo.setCurrentDate( new Date() );
        contextInfo.setPrincipalId("createBuilding");
        BuildingInfo buildingInfo = createBuildingInfo();
        buildingInfo = roomService.createBuilding("", buildingInfo, contextInfo);

        try {
            roomService.getBuilding(buildingInfo.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        try {
            roomService.getBuilding(buildingInfo.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        try {
            roomService.getBuilding(buildingInfo.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        try {
            roomService.getBuilding(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("id key is missing!", e.getMessage());
        }

        try {
            roomService.getBuilding("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("id key is missing!", e.getMessage());
        }

        BuildingInfo getBuildingInfo;

        getBuildingInfo = roomService.getBuilding(buildingInfo.getId(), contextInfo);

        assertTrue(equals(buildingInfo, getBuildingInfo));
    }

    @Test
    public void testUpdateBuilding() throws Exception {
        final long currentTime = System.currentTimeMillis();
        contextInfo.setCurrentDate(new Date(currentTime));
        contextInfo.setPrincipalId( "createBuilding" );
        BuildingInfo buildingInfo;
        buildingInfo = roomService.createBuilding("", createBuildingInfo(), contextInfo);

        assertEquals("0", buildingInfo.getMeta().getVersionInd());
        
        BuildingInfo originalBuildingInfo;
        originalBuildingInfo = roomService.getBuilding(buildingInfo.getId(), contextInfo);

        assertEquals("0", buildingInfo.getMeta().getVersionInd());
        
        //test missing ContextInfo
        try {
            roomService.updateBuilding(buildingInfo.getId(), buildingInfo, null);
            fail("Expected exception not thrown"); //shouldn't get here
        } catch(MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test no principal id
        try {
            roomService.updateBuilding(buildingInfo.getTypeKey(), buildingInfo, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test no current date
        try {
            roomService.updateBuilding(buildingInfo.getTypeKey(), buildingInfo, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //exception if no BuildingInfo
        try {
            roomService.updateBuilding(null, null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("Object is missing!", e.getMessage());
        }

        //test 5, an actual update
        buildingInfo.setBuildingCode( buildingInfo.getBuildingCode() + "_new");
        buildingInfo.setCampusKey( buildingInfo.getCampusKey() + "_new");
        buildingInfo.setDescr( new RichTextInfo(buildingInfo.getDescr().getPlain() + "_new", buildingInfo.getDescr().getFormatted() + "_new"));
        buildingInfo.setName( buildingInfo.getName() + "_new");
        buildingInfo.setStateKey("org.kuali.building.state.Inactive");
        buildingInfo.setTypeKey("org.kuali.building.type.Recreational");

        contextInfo.setPrincipalId("testUpdateBuilding");
        contextInfo.setCurrentDate(new Date(currentTime + 1000L));


        BuildingInfo newBuildingInfo;
        newBuildingInfo = roomService.updateBuilding(buildingInfo.getId(), buildingInfo, contextInfo);

        assertEquals("1", newBuildingInfo.getMeta().getVersionInd());
        
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
        contextInfo.setCurrentDate(new Date(currentTime + 2000L));
        BuildingInfo newNewBuildingInfo;
        newNewBuildingInfo = roomService.updateBuilding(newBuildingInfo.getId(), newBuildingInfo, contextInfo);

        assertEquals("2", newNewBuildingInfo.getMeta().getVersionInd());
        
        assertFalse(newNewBuildingInfo.getMeta().getUpdateTime().equals(newBuildingInfo.getMeta().getUpdateTime()));

        //test ok if Id is blank
        contextInfo.setCurrentDate(new Date(currentTime + 3000L));
        BuildingInfo newNewNewBuildingInfo;
        newNewNewBuildingInfo = roomService.updateBuilding(newNewBuildingInfo.getId(), newNewBuildingInfo, contextInfo);
        assertFalse(newNewNewBuildingInfo.getMeta().getUpdateTime().equals(newNewBuildingInfo.getMeta().getUpdateTime()));
    }

    @Test
    public void testGetBuildingIdsbyCampus() throws Exception {
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "2", "Armory", "active", "administration", "Mitchell", "Mitchell");

        roomService.createBuilding(null, b1, contextInfo);
        roomService.createBuilding(null, b2, contextInfo);

        //test null contextInfo
        try {
            roomService.getBuildingIdsByCampus("1", null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test invalid principalId
        try {
            roomService.getBuildingIdsByCampus("1", t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test invalid currentDate
        try {
            roomService.getBuildingIdsByCampus("1", t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null campusKey
        try {
            roomService.getBuildingIdsByCampus(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("campusKey key is missing!", e.getMessage());
        }

        //test blank campusKey
        try {
            roomService.getBuildingIdsByCampus("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("campusKey key is missing!", e.getMessage());
        }


        //test no results
        List<String> ids = roomService.getBuildingIdsByCampus("3", contextInfo);
        assertTrue(ids.isEmpty());

        //test normal search
        ids = roomService.getBuildingIdsByCampus("1", contextInfo);
        assertEquals(1, ids.size());
    }

    @Test
    public void testGetBuildingsByIds() throws Exception {
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "1", "Armory", "active", "administration", "Mitchell", "Mitchell");

        b1 = roomService.createBuilding(null, b1, contextInfo);
        b1 = roomService.getBuilding(b1.getId(), contextInfo);

        b2 = roomService.createBuilding(null, b2, contextInfo);
        b2 = roomService.getBuilding(b2.getId(), contextInfo);

        List<String> ids = new ArrayList<String>(2);
        //test null contextInfo
        try {
            roomService.getBuildingsByIds(ids, null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test invalid principalId
        try {
            roomService.getBuildingsByIds(ids, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test invalid date
        try {
            roomService.getBuildingsByIds(ids, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null list
        try {
            roomService.getBuildingsByIds(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("id list is null!", e.getMessage());
        }

        //test empty list
        try {
            roomService.getBuildingsByIds(ids, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("id list is null!", e.getMessage());
        }

        //normal search
        ids.add(b1.getId());
        ids.add(b2.getId());
        List<BuildingInfo> buildingInfos = roomService.getBuildingsByIds(ids, contextInfo);
        assertEquals(buildingInfos.size(), 2);

        //test exception when not all ids are found
        ids.add("3");
        try {
            roomService.getBuildingsByIds(ids, contextInfo);
            fail("Expected exception not thrown");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("Missing data for : 3", e.getMessage());
        }
    }

    @Test
    public void testGetBuildingsByBuildingCode() throws Exception {
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "1", "Armory", "active", "administration", "Mitchell", "Mitchell");

        b1 = roomService.createBuilding(null, b1, contextInfo);
        b1 = roomService.getBuilding(b1.getId(), contextInfo);

        b2 = roomService.createBuilding(null, b2, contextInfo);
        b2 = roomService.getBuilding(b2.getId(), contextInfo);

        //test null contextInfo
        try {
            roomService.getBuildingsByBuildingCode(b1.getBuildingCode(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test invalid principalId
        try {
            roomService.getBuildingsByBuildingCode(b1.getBuildingCode(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test invalid date
        try {
            roomService.getBuildingsByBuildingCode(b1.getBuildingCode(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null code
        try {
            roomService.getBuildingsByBuildingCode(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingCode key is missing!", e.getMessage());
        }

        //test blank code
        try {
            roomService.getBuildingsByBuildingCode("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingCode key is missing!", e.getMessage());
        }

        //test when not found
        List<BuildingInfo> list = roomService.getBuildingsByBuildingCode("ADM", contextInfo);
        assertTrue(list.isEmpty());

        //test normal search
        list = roomService.getBuildingsByBuildingCode(b2.getBuildingCode(), contextInfo);
        assertEquals(1, list.size());
    }

    @Test
    public void testDeleteBuilding() throws Exception {
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "1", "Armory", "active", "administration", "Mitchell", "Mitchell");

        b1 = roomService.createBuilding(null, b1, contextInfo);
        roomService.createBuilding(null, b2, contextInfo);

        //test null contextInfo
        try {
            roomService.deleteBuilding(b1.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test invalid principalId
        try {
            roomService.deleteBuilding(b1.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e){
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test invalid currentDate
        try {
            roomService.deleteBuilding(b1.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null key
        try {
            roomService.deleteBuilding(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("id key is missing!", e.getMessage());
        }

        //test blank key
        try {
            roomService.deleteBuilding("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("id key is missing!", e.getMessage());
        }

        //normal delete
        StatusInfo statusInfo = roomService.deleteBuilding(b1.getId(), contextInfo);
        assertTrue(statusInfo.getIsSuccess());

        //double-check record was deleted
        List<String> ids = roomService.getBuildingIdsByCampus("1", contextInfo);
        assertEquals(1, ids.size());
    }

    @Test
    public void testBuildingAttributes() throws Exception {
        contextInfo.setPrincipalId("testBuildingAttributes");
        contextInfo.setCurrentDate( new Date() );

        //create a building
        BuildingInfo b1;
        b1 = roomService.createBuilding("", createBuildingInfo("ADM", "umuc", "Administration Building", "active", "administration", "the administration building", "the administration building"), contextInfo);

        //give it attributes and update
        b1 = addAttributeInfo(b1, "key1", "value1");
        b1 = addAttributeInfo(b1, "key2", "value2");
        BuildingInfo b2;
        b2 = roomService.updateBuilding(b1.getId(), b1, contextInfo);
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
        BuildingInfo b3;
        b3 = roomService.updateBuilding(b2.getId(), b2, contextInfo);
        assertEquals(b3.getAttributes().size(), 1);
        assertEquals(b3.getAttributes().get(0).getKey(), "key3");
        assertEquals(b3.getAttributes().get(0).getValue(), "value3");
    }

    @Test
    public void testCreateRoom() throws Exception {
        contextInfo.setPrincipalId("testCreateRoom");
        contextInfo.setCurrentDate( new Date() );

        String typeKey = "org.kuali.room.type.Lecture";
        String floor = "1";
        String roomCode = "1234";
        String name = "classroom 1";
        String state = "org.kuali.room.state.Active";
        String plain = "classroom for lectures";
        String formatted = "classroom for lectures";
        BuildingInfo b;
        b = roomService.createBuilding("", createBuildingInfo("ADM", "umuc", "Administration Building", "active", "administration", "the administration building", "the administration building"), contextInfo);

        RoomInfo r = createRoomInfo(b, floor, roomCode, name, state, typeKey, plain, formatted);

        //test null contextInfo
        try {
            roomService.createRoom(b.getId(), r.getTypeKey(), r, null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test invalid principalId
        try {
            roomService.createRoom(b.getId(), r.getTypeKey(), r, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test invalid date
        try {
            roomService.createRoom(b.getId(), r.getTypeKey(), r, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null roomInfo
        try {
            roomService.createRoom(b.getId(), r.getTypeKey(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("Object is missing!", e.getMessage());
        }

        //test null typeKey
        RoomInfo r2;
        r2 = roomService.createRoom(b.getId(), null, r, contextInfo);
        assertEquals(r2.getBuildingId(), r.getBuildingId());
        assertEquals(r2.getTypeKey(), r.getTypeKey());
        assertEquals(r2.getFloor(), r.getFloor());
        assertEquals(r2.getRoomCode(), r.getRoomCode());
        assertEquals(r2.getDescr().getPlain(), r.getDescr().getPlain());
        assertEquals(r2.getDescr().getFormatted(), r.getDescr().getFormatted());

        //test blank typeKey
        r2 = roomService.createRoom(b.getId(), "", r, contextInfo);
        assertEquals(r2.getTypeKey(), r.getTypeKey());

        //test new typeKey
        String newTypeKey = r.getTypeKey() + "_new";
        r2 = roomService.createRoom(b.getId(), newTypeKey, r, contextInfo);
        assertEquals(r2.getTypeKey(), newTypeKey);

        //test null buildingId
        r2 = roomService.createRoom(null, r.getTypeKey(), r, contextInfo);
        assertEquals(r2.getBuildingId(), r.getBuildingId());

        //test blank buildingId
        r2 = roomService.createRoom("", r.getTypeKey(), r, contextInfo);
        assertEquals(r2.getBuildingId(), r.getBuildingId());

        //test new buildingId
        String newBuildingId = b.getId() + "_new";
        r2 = roomService.createRoom(newBuildingId, r.getTypeKey(), r, contextInfo);
        assertEquals(r2.getBuildingId(), newBuildingId);
    }

    @Test
    public void testGetRoom() throws Exception {
        contextInfo.setPrincipalId("testGetRoom");
        contextInfo.setCurrentDate( new Date() );

        BuildingInfo b;
        b = roomService.createBuilding("", createBuildingInfo("ADM", "umuc", "Administration Building", "active", "administration", "the administration building", "the administration building"), contextInfo);

        RoomInfo room = createRoomInfo(b, "1", "1234", "classroom 1", "org.kuali.room.state.Active", "org.kuali.room.type.Lecture", "classroom for lectures", "classroom for lectures");
        room = roomService.createRoom(b.getId(), "", room, contextInfo);

        //test null ContextInfo
        try {
            roomService.getRoom(room.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test invalid principalId
        try {
            roomService.getRoom(room.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test invalid date
        try {
            roomService.getRoom(room.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null roomId
        try {
            roomService.getRoom(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("id key is missing!", e.getMessage());
        }

        //test blank roomId
        try {
            roomService.getRoom("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("id key is missing!", e.getMessage());
        }

        //test does not exist
        try {
            roomService.getRoom("DoesNotExist", contextInfo);
            fail("Expected exception not thrown");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("Missing data for : 'DoesNotExist'", e.getMessage());
        }

        //normal use
        RoomInfo r;
        r = roomService.getRoom(room.getId(), contextInfo);
        assertNotNull(r);
        assertEquals(r.getId(), room.getId() );
        assertEquals(r.getBuildingId(), room.getBuildingId() );
        assertEquals(r.getFloor(), room.getFloor() );
        assertEquals(r.getRoomCode(), room.getRoomCode() );
        assertEquals(r.getDescr().getFormatted(),  room.getDescr().getFormatted() );
        assertEquals(r.getDescr().getPlain(), room.getDescr().getPlain() );
    }

    @Test
    public void testUpdateRoom() throws Exception {
        final long currentTime = System.currentTimeMillis();
        contextInfo.setCurrentDate(new Date(currentTime));
        contextInfo.setPrincipalId( "testCreateRoom" );

        BuildingInfo b;
        b = roomService.createBuilding("", createBuildingInfo(), contextInfo);

        RoomInfo r = createRoomInfo(b, "floor", "1234", "The Room", "Active", "Classroom", "plain", "formatted");
        r = roomService.createRoom(b.getId(), r.getTypeKey(), r, contextInfo);

        RoomInfo originalRoomInfo;
        originalRoomInfo = roomService.getRoom(r.getId(), contextInfo);
        assertNotNull(originalRoomInfo);

        //test missing ContextInfo
        try {
            roomService.updateRoom(r.getId(), r, null);
            fail("Expected exception not thrown"); //shouldn't get here
        } catch(MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test no principal id
        try {
            roomService.updateRoom(r.getId(), r, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test no current date
        try {
            roomService.updateRoom(r.getId(), r, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null RoomInfo
        try {
            roomService.updateRoom(r.getId(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("Object is missing!", e.getMessage());
        }

        //test normal update
        contextInfo.setPrincipalId("testUpdateRoom");
        contextInfo.setCurrentDate( new Date(currentTime + 1000L) );
        r.setBuildingId( r.getBuildingId() + "_new");
        r.setFloor( r.getFloor() + "_new");
        r.setRoomCode( r.getRoomCode() + "_new");
        r.setDescr( new RichTextInfo(r.getDescr().getPlain() + "_new", r.getDescr().getFormatted() + "_new"));
        r.setName( r.getName() + "_new");
        r.setTypeKey( r.getTypeKey() + "_new");
        r.setStateKey( r.getStateKey() + "_new");
        RoomInfo r2;
        r2 = roomService.updateRoom(r.getId(), r, contextInfo);
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
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException iae) {
            assertNotNull(iae.getMessage());
            assertEquals("id to load is required for loading", iae.getMessage());
        }

        //test update with blank roomId
        try {
            roomService.updateRoom("", r2, contextInfo);
            fail("Expected exception not thrown");
        } catch (PersistenceException pe) {
            assertNotNull(pe.getMessage());
            assertTrue(pe.getMessage().contains("could not load an entity"));
        }
    }

    @Test
    public void testSearchAndDeleteRoom() throws Exception {
        contextInfo.setPrincipalId("testSearchAndDeleteRoom");
        contextInfo.setCurrentDate( new Date() );
        //create some buildings
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "1", "Armory", "active", "administration", "Mitchell", "Mitchell");
        b1 = roomService.createBuilding(null, b1, contextInfo);
        b2 = roomService.createBuilding(null, b2, contextInfo);

        //create some rooms
        RoomInfo r1 = createRoomInfo(b1, "1", "100", "War Room", "org.kuali.room.state.Active", "org.kuali.room.type.Classroom", "the war room", "the war room");
        RoomInfo r2 = createRoomInfo(b1, "2", "200", "Peace Room", "org.kuali.room.state.Active", "org.kuali.room.type.Classroom", "the peace room", "the peace room");
        RoomInfo r3 = createRoomInfo(b2, "3", "300", "North Room", "org.kuali.room.state.Active", "org.kuali.room.type.LectureHall", "the north room", "the north room");
        RoomInfo r4 = createRoomInfo(b2, "4", "405", "South Room", "org.kuali.room.state.Inactive", "org.kuali.room.type.ExecutionChamber", "the execution room", "the execution room");

        roomService.createRoom(r1.getBuildingId(), r1.getTypeKey(), r1, contextInfo);
        roomService.createRoom(r2.getBuildingId(), r2.getTypeKey(), r2, contextInfo);
        roomService.createRoom(r3.getBuildingId(), r3.getTypeKey(), r3, contextInfo);
        roomService.createRoom(r4.getBuildingId(), r4.getTypeKey(), r4, contextInfo);


        testGetRoomIdsByBuilding(b1);

        testGetRoomIdsByBuildingAndFloor(b1, b2, r3, r4);

        testGetRoomIdsByBuildingAndRoomType(b1, b2, r3, r4);

        testGetRoomIdsByBuildingAndRoomTypes(b1, b2, r1, r2);

        testGetRoomIdsByType(r1, r2, r3, r4);

        testGetRoomsByBuildingAndRoomCode(b1, b2, r1, r2, r3);
    }

    private void testGetRoomIdsByBuilding(BuildingInfo b1) throws Exception {
        //test for null context
        try {
            roomService.getRoomIdsByBuilding(b1.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test for null principalId
        try {
            roomService.getRoomIdsByBuilding(b1.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test for null currentDate
        try {
            roomService.getRoomIdsByBuilding(b1.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test for null buildingId
        try {
            roomService.getRoomIdsByBuilding(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingId key is missing!", e.getMessage());
        }

        //test for blank buildingId
        try {
            roomService.getRoomIdsByBuilding("", t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test for no matches
        List<String> ids = roomService.getRoomIdsByBuilding("doesnotmatch", contextInfo);
        assertTrue(ids.isEmpty());

        //test for matches
        ids = roomService.getRoomIdsByBuilding(b1.getId(), contextInfo);
        assertEquals(ids.size(), 2);
    }

    private void testGetRoomIdsByBuildingAndFloor(BuildingInfo b1, BuildingInfo b2, RoomInfo r3, RoomInfo r4) throws Exception {
        //test for NULL context
        try {
            roomService.getRoomIdsByBuildingAndFloor(b2.getId(), r3.getFloor(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test for null principalId
        try {
            roomService.getRoomIdsByBuildingAndFloor(b2.getId(), r3.getFloor(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test for null date
        try {
            roomService.getRoomIdsByBuildingAndFloor(b2.getId(), r3.getFloor(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test for null floor
        try {
            roomService.getRoomIdsByBuildingAndFloor(b2.getId(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("floor key is missing!", e.getMessage());
        }

        //test for empty floor
        try {
            roomService.getRoomIdsByBuildingAndFloor(b2.getId(), "", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("floor key is missing!", e.getMessage());
        }

        //test for null buildingId
        try {
            roomService.getRoomIdsByBuildingAndFloor(null, r3.getFloor(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingId key is missing!", e.getMessage());
        }

        //test for empty buildingId
        try {
            roomService.getRoomIdsByBuildingAndFloor("", r3.getFloor(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingId key is missing!", e.getMessage());
        }

        List<String> ids;
        //test for no matches
        ids = roomService.getRoomIdsByBuildingAndFloor(b1.getId(), r3.getFloor(), contextInfo);
        assertTrue(ids.isEmpty());

        //test normal
        ids = roomService.getRoomIdsByBuildingAndFloor(b2.getId(), r4.getFloor(), contextInfo);
        assertEquals(1, ids.size());

    }

    private void testGetRoomIdsByBuildingAndRoomType(BuildingInfo b1, BuildingInfo b2, RoomInfo r3, RoomInfo r4) throws Exception {
        //test for NULL context
        try {
            roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), r4.getTypeKey(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test for null principalId
        try {
            roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), r4.getTypeKey(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test for null date
        try {
            roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), r4.getTypeKey(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test for null typeKey
        try {
            roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomType key is missing!", e.getMessage());
        }

        //test for empty typeKey
        try {
            roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), "", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomType key is missing!", e.getMessage());
        }

        //test for null buildingId
        try {
            roomService.getRoomIdsByBuildingAndRoomType(null, r3.getTypeKey(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingId key is missing!", e.getMessage());
        }

        //test for empty buildingId
        try {
            roomService.getRoomIdsByBuildingAndRoomType("", r3.getTypeKey(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingId key is missing!", e.getMessage());
        }

        List<String> ids;
        //test for no matches
        ids = roomService.getRoomIdsByBuildingAndRoomType(b1.getId(), r3.getTypeKey(), contextInfo);
        assertTrue(ids.isEmpty());

        ids = roomService.getRoomIdsByBuildingAndRoomType(b2.getId(), r4.getTypeKey(), contextInfo);
        assertEquals(1, ids.size());
    }

    private void testGetRoomIdsByBuildingAndRoomTypes(BuildingInfo b1, BuildingInfo b2, RoomInfo r1, RoomInfo r2) throws Exception {
        List<String> typeKeys = new ArrayList<String>(2);
        typeKeys.add(r1.getTypeKey());
        typeKeys.add(r2.getTypeKey());

        List<String> ids;

        //test for NULL context
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), typeKeys, null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test for null principalId
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), typeKeys, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test for null date
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), typeKeys, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test for null typeKey
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomType list is null!", e.getMessage());
        }

        //test for empty typeKey
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), new ArrayList<String>(0), contextInfo);
            fail("Expected exception not thrown");
        } catch(MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomType list is null!", e.getMessage());
        }

        //test for null buildingId
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes(null, typeKeys, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingId key is missing!", e.getMessage());
        }

        //test for empty buildingId
        try {
            roomService.getRoomIdsByBuildingAndRoomTypes("", typeKeys, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingId key is missing!", e.getMessage());
        }

        //test for no results
        ids = roomService.getRoomIdsByBuildingAndRoomTypes(b2.getId(), typeKeys, contextInfo);
        assertTrue(ids.isEmpty());

        //normal use
        ids = roomService.getRoomIdsByBuildingAndRoomTypes(b1.getId(), typeKeys, contextInfo);
        assertEquals(2, ids.size());
    }

    private void testGetRoomIdsByType(RoomInfo r1, RoomInfo r2, RoomInfo r3, RoomInfo r4) throws Exception {

        //test null context
        try {
            roomService.getRoomIdsByType(r1.getTypeKey(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test for null principalId
        try {
            roomService.getRoomIdsByType(r2.getTypeKey(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test for null date
        try {
            roomService.getRoomIdsByType(r3.getTypeKey(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test for null type
        try {
            roomService.getRoomIdsByType(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomType key is missing!", e.getMessage());
        }

        //test for empty type
        try {
            roomService.getRoomIdsByType("", contextInfo);
            fail("Expected exception not thrown");
        } catch(MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomType key is missing!", e.getMessage());
        }

        //test for no results
        List<String> ids = roomService.getRoomIdsByType(r4.getTypeKey() + "_not", contextInfo);
        assertTrue(ids.isEmpty());

        //normal use
        ids = roomService.getRoomIdsByType(r1.getTypeKey(), contextInfo);
        assertEquals(2, ids.size());
    }

    private void testGetRoomsByBuildingAndRoomCode(BuildingInfo b1, BuildingInfo b2, RoomInfo r1, RoomInfo r2, RoomInfo r3) throws Exception {

        //test for NULL context
        try {
            roomService.getRoomsByBuildingAndRoomCode(b1.getBuildingCode(), r1.getRoomCode(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test for null principalId
        try {
            roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), r1.getRoomCode(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test for null date
        try {
            roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), r1.getRoomCode(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test for null roomCode
        try {
            roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomCode key is missing!", e.getMessage());
        }

        //test for empty roomCode
        try {
            roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), "", contextInfo);
            fail("Expected exception not thrown");
        } catch(MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomCode key is missing!", e.getMessage());
        }

        //test for null buildingCode
        try {
            roomService.getRoomsByBuildingAndRoomCode(null, r2.getRoomCode(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingCode key is missing!", e.getMessage());
        }

        //test for empty buildingCode
        try {
            roomService.getRoomsByBuildingAndRoomCode("", r2.getRoomCode(), contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("buildingCode key is missing!", e.getMessage());
        }

        //test for no results
        List<RoomInfo> rooms = roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), r2.getRoomCode(), contextInfo);
        assertTrue(rooms.isEmpty());


        //normal use
        rooms = roomService.getRoomsByBuildingAndRoomCode(b2.getBuildingCode(), r3.getRoomCode(), contextInfo);
        assertEquals(1, rooms.size());
    }

    @Test
    public void testCreateRoomResponsibleOrg() throws Exception {
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
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test invalid principalId
        try {
            roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), i, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test invalid date
        try {
            roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), i, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null info
        try {
            roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("Object is missing!", e.getMessage());
        }

        //test null typeKey
        RoomResponsibleOrgInfo i2;
        i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), null, i, contextInfo);
        assertEquals(i2.getOrgId(), i.getOrgId());
        assertEquals(i2.getTypeKey(), i.getTypeKey());
        assertEquals(i2.getRoomId(), i.getRoomId());
        assertEquals(i2.getEffectiveDate(), i.getEffectiveDate());
        assertEquals(i2.getExpirationDate(), i.getExpirationDate());
        assertEquals(i2.getStateKey(), i.getStateKey());

        //test blank typeKey
        i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), "", i, contextInfo);
        assertEquals(i2.getTypeKey(), i.getTypeKey());

        //test new typeKey
        String newTypeKey = i.getTypeKey() + "_new";
        i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), newTypeKey, i, contextInfo);
        assertEquals(i2.getTypeKey(), newTypeKey);

        //test null orgId
        i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), null, i.getTypeKey(), i, contextInfo);
        assertEquals(i2.getOrgId(), i.getOrgId());

        //test blank orgId
        i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), "", i.getTypeKey(), i, contextInfo);
        assertEquals(i2.getOrgId(), i.getOrgId());

        //test new orgId
        String newOrgId = i.getOrgId() + "_new";
        i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), newOrgId, i.getTypeKey(), i, contextInfo);
        assertEquals(i2.getOrgId(), newOrgId);

        //test null roomId
        i2 = roomService.createRoomResponsibleOrg(null, i.getOrgId(), i.getTypeKey(), i, contextInfo);
        assertEquals(i2.getRoomId(), i.getRoomId());

        //test blank orgId
        i2 = roomService.createRoomResponsibleOrg("", i.getOrgId(), i.getTypeKey(), i, contextInfo);
        assertEquals(i2.getRoomId(), i.getRoomId());

        //test new orgId
        String newRoomId = i.getRoomId() + "_new";
        i2 = roomService.createRoomResponsibleOrg(newRoomId, i.getOrgId(), i.getTypeKey(), i, contextInfo);
        assertEquals(i2.getRoomId(), newRoomId);

        //test nominal usage
        i2 = roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), i, contextInfo);
        assertEquals(i2.getRoomId(), roomId);
        assertEquals(i2.getOrgId(), orgId);
        assertEquals(i2.getTypeKey(), type);
        assertEquals(i2.getStateKey(), state);
        assertEquals(i2.getEffectiveDate(), effDate);
        assertEquals(i2.getExpirationDate(), expDate);
    }

    @Test
    public void testGetRoomResponsibleOrg() throws Exception {
        contextInfo.setPrincipalId("testGetRoomResponsibleOrg");
        contextInfo.setCurrentDate(new Date());

        String type = "kuali.roomresponsibleorg.type.Owner";
        String roomId = "1";
        String orgId = "1";
        String state = "kuali.roomresponsibleorg.state.Active";
        Date effDate = new Date( new Date().getTime() - 86400);
        Date expDate = new Date( new Date().getTime() + 86400);
        RoomResponsibleOrgInfo i = createRoomResponsibleOrgInfo(roomId, orgId, state, type, effDate, expDate);

        i = roomService.createRoomResponsibleOrg(i.getRoomId(), i.getOrgId(), i.getTypeKey(), i, contextInfo);
        //test null contextInfo
        RoomResponsibleOrgInfo i2;
        try {
            roomService.getRoomResponsibleOrg(i.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test invalid principalId
        try {
            roomService.getRoomResponsibleOrg(i.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test invalid date
        try {
            roomService.getRoomResponsibleOrg(i.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null id
        try {
            roomService.getRoomResponsibleOrg(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomResponsibleOrgId key is missing!", e.getMessage());
        }

        //test blank id
        try {
            roomService.getRoomResponsibleOrg("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomResponsibleOrgId key is missing!", e.getMessage());
        }

        //nominal
        i2 = roomService.getRoomResponsibleOrg(i.getId(), contextInfo);

        assertEquals(i2.getId(), i.getId());
        assertEquals(i2.getOrgId(), i.getOrgId());
        assertEquals(i2.getRoomId(), i.getRoomId());
        assertEquals(i2.getEffectiveDate(), i.getEffectiveDate());
        assertEquals(i2.getExpirationDate(), i.getExpirationDate());
        assertEquals(i2.getStateKey(), i.getStateKey());
        assertEquals(i2.getTypeKey(), i.getTypeKey());
    }

    @Test
    public void testUpdateRoomResponsibleOrg() throws Exception {
        final long currentTime = System.currentTimeMillis();
        contextInfo.setCurrentDate( new Date(currentTime) );
        contextInfo.setPrincipalId( "createRoomResponsibleOrg" );

        String roomId = "roomId";
        String orgId = "orgId";
        String state = "state";
        String type = "type";
        Date effDate = new Date( currentTime - 86400L);
        Date expDate = new Date( currentTime + 86400L);

        RoomResponsibleOrgInfo i;
        i = roomService.createRoomResponsibleOrg("", "", "", createRoomResponsibleOrgInfo(roomId, orgId, state, type, effDate, expDate), contextInfo);

        RoomResponsibleOrgInfo originalInfo;
        originalInfo = roomService.getRoomResponsibleOrg(i.getId(), contextInfo);

        //test missing ContextInfo
        try {
            roomService.updateRoomResponsibleOrg(i.getId(), i, null);
            fail("Expected exception not thrown"); //shouldn't get here
        } catch(MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test no principal id
        try {
            roomService.updateRoomResponsibleOrg(i.getId(), i, t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test no current date
        try {
            roomService.updateRoomResponsibleOrg(i.getId(), i, t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //exception if no RoomResponsibleOrgInfo
        try {
            roomService.updateRoomResponsibleOrg(i.getId(), null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("Object is missing!", e.getMessage());
        }

        //test 5, an actual update
        i.setTypeKey(i.getTypeKey() + "_new");
        i.setStateKey(i.getStateKey() + "_new");
        i.setOrgId(i.getOrgId() + "_new");
        i.setRoomId(i.getRoomId() + "_new");
        i.setEffectiveDate(new Date(i.getEffectiveDate().getTime() - 86400L));
        i.setExpirationDate(new Date(i.getExpirationDate().getTime() + 86400L));

        contextInfo.setPrincipalId("testUpdateRoomResponsibleOrg");
        contextInfo.setCurrentDate(new Date(currentTime + 1000L));

        RoomResponsibleOrgInfo newInfo;
        newInfo = roomService.updateRoomResponsibleOrg(i.getId(), i, contextInfo);
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

        contextInfo.setCurrentDate(new Date(currentTime + 2000L));
        RoomResponsibleOrgInfo newnewInfo;
        newnewInfo = roomService.updateRoomResponsibleOrg(newInfo.getId(), newInfo, contextInfo);
        assertFalse(newnewInfo.getMeta().getUpdateTime().equals(newInfo.getMeta().getUpdateTime()));

        contextInfo.setCurrentDate(new Date(currentTime + 3000L));

        RoomResponsibleOrgInfo newnewnewInfo;
        newnewnewInfo = roomService.updateRoomResponsibleOrg(newnewInfo.getId(), newnewInfo, contextInfo);
        assertFalse(newnewnewInfo.getMeta().getUpdateTime().equals(newnewInfo.getMeta().getUpdateTime()));

    }

    @Test
    public void testSearchAndDeleteRoomResponsibleOrg() throws Exception {
        RoomResponsibleOrgInfo i1 = createRoomResponsibleOrgInfo("roomId1", "orgId1", "state1", "type1", new Date(), new Date());
        RoomResponsibleOrgInfo i2 = createRoomResponsibleOrgInfo("roomId1", "orgId2", "state2", "type2", new Date(), new Date());

        i1 = roomService.createRoomResponsibleOrg(null, null, null, i1, contextInfo);
        i1 = roomService.getRoomResponsibleOrg(i1.getId(), contextInfo);

        i2 = roomService.createRoomResponsibleOrg(null, null, null, i2, contextInfo);
        i2 = roomService.getRoomResponsibleOrg(i2.getId(), contextInfo);

        //test null contextInfo
        try {
            roomService.getRoomResponsibleOrgIdsByRoom("roomId1", null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test empty principalId
        try {
            roomService.getRoomResponsibleOrgIdsByRoom("roomId2", t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test null date
        try {
            roomService.getRoomResponsibleOrgIdsByRoom("roomId1", t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null id
        try {
            roomService.getRoomResponsibleOrgIdsByRoom(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomId key is missing!", e.getMessage());
        }

        //test blank id
        try {
            roomService.getRoomResponsibleOrgIdsByRoom("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomId key is missing!", e.getMessage());
        }

        List<String> ids;
        ids = roomService.getRoomResponsibleOrgIdsByRoom("roomId1", contextInfo);
        assertEquals(2, ids.size());

        //test null contextInfo
        try {
            roomService.deleteRoomResponsibleOrg(i2.getId(), null);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("contextInfo is null!", e.getMessage());
        }

        //test invalid principalId
        try {
            roomService.deleteRoomResponsibleOrg(i1.getId(), t1);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e){
            assertNotNull(e.getMessage());
            assertEquals("principalId is missing!", e.getMessage());
        }

        //test invalid currentDate
        try {
            roomService.deleteRoomResponsibleOrg(i1.getId(), t2);
            fail("Expected exception not thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("currentDate is missing!", e.getMessage());
        }

        //test null key
        try {
            roomService.deleteRoomResponsibleOrg(null, contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomResponsibleOrgId key is missing!", e.getMessage());
        }

        //test blank key
        try {
            roomService.deleteRoomResponsibleOrg("", contextInfo);
            fail("Expected exception not thrown");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("roomResponsibleOrgId key is missing!", e.getMessage());
        }

        //normal delete
        StatusInfo statusInfo = roomService.deleteRoomResponsibleOrg(i2.getId(), contextInfo);
        assertTrue(statusInfo.getIsSuccess());

        //double-check record was deleted
        ids = roomService.getRoomResponsibleOrgIdsByRoom("roomId1", contextInfo);
        assertEquals(1, ids.size());

    }


}

