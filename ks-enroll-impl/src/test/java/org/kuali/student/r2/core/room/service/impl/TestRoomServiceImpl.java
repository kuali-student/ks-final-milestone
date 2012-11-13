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
import org.kuali.student.r2.core.room.service.RoomService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @BeforeClass
    public static void beforeClass() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("JUnitTest");
        contextInfo.setAuthenticatedPrincipalId("JUnitTest");
//        contextInfo.setCurrentDate(new Date());
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
    private BuildingInfo addAttributeInfo(BuildingInfo buildingInfo, String name, String value) {
        if (buildingInfo.getAttributes() == null) {
            buildingInfo.setAttributes(new ArrayList<AttributeInfo>(1));
        }

        buildingInfo.getAttributes().add( new AttributeInfo(name, value) );

        return buildingInfo;
    }

    @Test
    public void testCreateBuilding() {
        BuildingInfo buildingInfo = createBuildingInfo();

        //test 1, exception if no ContextInfo
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, null);
            assert false;
        } catch (MissingParameterException e) {
            //this is the exception we expect
            assert true;
        } catch (Throwable t) {
            t.printStackTrace();
            //any other failures are not allowed
            assert false;
        }

        //test invalid values in ContextInfo
        {
            //test 2, null principal id
            ContextInfo testContextInfo = new ContextInfo();

            try {
                roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, testContextInfo);
                assert false;
            } catch (InvalidParameterException e) {
                //this is the exception we expect
                assert true;
            } catch (Throwable t) {
                t.printStackTrace();
                assert false;
            }

            //test 3, null date
            testContextInfo.setPrincipalId("Hey there");
            testContextInfo.setCurrentDate(null);
            try {
                roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, testContextInfo);
                assert false;
            } catch (InvalidParameterException e) {
                //this is the exception we expect
                assert true;
            } catch (Throwable t) {
                t.printStackTrace();
                assert false;
            }
        }


        //test 4, exception if no BuildingInfo
        try {
            BuildingInfo nullBuildingInfo = null;
            roomService.createBuilding(buildingInfo.getTypeKey(), nullBuildingInfo, contextInfo);
            assert false;
        } catch (MissingParameterException e) {
            //this is the exception we expect
            assert true;
        } catch (Throwable t) {
            t.printStackTrace();
            //any other failures are not allowed
            assert false;
        }

        //test 5, ok if no TypeKey specified
        try {
            roomService.createBuilding(null, buildingInfo, contextInfo);
            assert true;
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        //test 6, ok if no TypeKey specified
        try {
            roomService.createBuilding("", buildingInfo, contextInfo);
            assert true;
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        //test 7, typical usage
        try {
            roomService.createBuilding(buildingInfo.getTypeKey(), buildingInfo, contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        //test 8, ok if new type key specified
        try {
            BuildingInfo buildingInfo1 = roomService.createBuilding("org.kuali.building.type.Extracurricular", buildingInfo, contextInfo);
            assert( buildingInfo.getName().equals( buildingInfo1.getName() ) );
            assert(!buildingInfo1.getTypeKey().equals( buildingInfo.getTypeKey() ) );
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

    }

    @Test
    public void testGetBuilding() {
        contextInfo.setCurrentDate( new Date() );
        contextInfo.setPrincipalId( "createBuilding" );
        BuildingInfo buildingInfo = createBuildingInfo();

        try {
            buildingInfo = roomService.createBuilding("", buildingInfo, contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }
        //Sanity check
        assert (buildingInfo.getId() != null);
        assert (!buildingInfo.getId().equals(""));
        assert (buildingInfo.getMeta() != null);
        assert (buildingInfo.getDescr() != null);

        BuildingInfo getBuildingInfo = null;

        try {
            getBuildingInfo = roomService.getBuilding(buildingInfo.getId(), contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        assert equals(buildingInfo, getBuildingInfo);
    }

    @Test
    public void testUpdateBuilding() {
        contextInfo.setCurrentDate( new Date() );
        contextInfo.setPrincipalId( "createBuilding" );
        BuildingInfo buildingInfo = null;
        try {
            buildingInfo = roomService.createBuilding("", createBuildingInfo(), contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }
        //Sanity check
        assert (buildingInfo.getId() != null);
        assert (!buildingInfo.getId().equals(""));
        assert (buildingInfo.getMeta() != null);
        assert (buildingInfo.getDescr() != null);

        BuildingInfo originalBuildingInfo = null;
        try {
            originalBuildingInfo = roomService.getBuilding(buildingInfo.getId(), contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        //test 1, missing ContextInfo
        try {
            roomService.updateBuilding(buildingInfo.getId(), buildingInfo, null);
            assert false; //shouldn't get here
        } catch(MissingParameterException e) {
            //exception we're expecting
            assert true;
        } catch (Throwable t) {
            //fail!
            t.printStackTrace();
            assert false;
        }

        //test invalid values in ContextInfo
        {
            //test 2, no principal id
            ContextInfo testContextInfo = new ContextInfo();
            testContextInfo.setCurrentDate(new Date());

            try {
                roomService.updateBuilding(buildingInfo.getTypeKey(), buildingInfo, testContextInfo);
                assert false;
            } catch (InvalidParameterException e) {
                //this is the exception we expect
                assert true;
            } catch (Throwable t) {
                t.printStackTrace();
                assert false;
            }

            //test 3, no current date
            testContextInfo.setPrincipalId("Hey there");
            testContextInfo.setCurrentDate(null);
            try {
                roomService.updateBuilding(buildingInfo.getTypeKey(), buildingInfo, testContextInfo);
                assert false;
            } catch (InvalidParameterException e) {
                //this is the exception we expect
                assert true;
            } catch (Throwable t) {
                t.printStackTrace();
                assert false;
            }
        }

        //test 4, exception if no BuildingInfo
        try {
            BuildingInfo nullBuildingInfo = null;
            roomService.updateBuilding(null, nullBuildingInfo, contextInfo);
            assert(false);
        } catch (MissingParameterException e) {
            //this is the exception we expect
            assert(true);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        //test 5, an actual update
        buildingInfo.setBuildingCode( buildingInfo.getBuildingCode() + "_new");
        buildingInfo.setCampusKey( buildingInfo.getCampusKey() + "_new");
        buildingInfo.setDescr( new RichTextInfo(buildingInfo.getDescr().getPlain() + "_new", buildingInfo.getDescr().getFormatted() + "_new"));
        buildingInfo.setName( buildingInfo.getName() + "_new");
        buildingInfo.setStateKey("org.kuali.building.state.Inactive");
        buildingInfo.setTypeKey("org.kuali.building.type.Recreational");

        contextInfo.setPrincipalId("updateBuilding");
        //sleep for 1 sec to allow Time to change
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        contextInfo.setCurrentDate(new Date());


        BuildingInfo newBuildingInfo = null;
        try {
            newBuildingInfo = roomService.updateBuilding(buildingInfo.getId(), buildingInfo, contextInfo); //roomService.getBuilding(buildingInfo.getId(), contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }
        assert(originalBuildingInfo.getId().equals(newBuildingInfo.getId()));
        MetaInfo metaInfo = originalBuildingInfo.getMeta();
        MetaInfo newMetaInfo = newBuildingInfo.getMeta();

        assert(newMetaInfo.getCreateId().equals(metaInfo.getCreateId()));
        assert(newMetaInfo.getCreateTime().equals(metaInfo.getCreateTime()));
        assert(!newMetaInfo.getUpdateId().equals(metaInfo.getUpdateId()));
        assert(!newMetaInfo.getUpdateTime().equals(metaInfo.getUpdateTime()));

        assert(!originalBuildingInfo.getBuildingCode().equals(buildingInfo.getBuildingCode()));
        assert(!originalBuildingInfo.getCampusKey().equals(buildingInfo.getCampusKey()));
        assert(!originalBuildingInfo.getDescr().getFormatted().equals(buildingInfo.getDescr().getFormatted()));
        assert(!originalBuildingInfo.getDescr().getPlain().equals(buildingInfo.getDescr().getPlain()));
        assert(!originalBuildingInfo.getName().equals(buildingInfo.getName()));
        assert(!originalBuildingInfo.getStateKey().equals(buildingInfo.getStateKey()));
        assert(!originalBuildingInfo.getTypeKey().equals(buildingInfo.getTypeKey()));

        //test 6, ok if no Id specified
        try {
            roomService.updateBuilding(null, buildingInfo, contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        //test 7, ok if Id is blank
        try {
            roomService.updateBuilding("", buildingInfo, contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

    }

    @Test
    public void testSearchesAndDelete() {
        BuildingInfo b1 = createBuildingInfo("ARM", "1", "Armory", "active", "recreation", "the armory", "the armory");
        BuildingInfo b2 = createBuildingInfo("MIT", "1", "Armory", "active", "administration", "Mitchell", "Mitchell");

        try {
            b1 = roomService.createBuilding(null, b1, contextInfo);
            b1 = roomService.getBuilding(b1.getId(), contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        try {
            b2 = roomService.createBuilding(null, b2, contextInfo);
            b2 = roomService.getBuilding(b2.getId(), contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        try {
            roomService.getBuildingIdsByCampus("1", null);
            assert false;
        } catch (MissingParameterException e) {
            //this is the exception we expect
            assert true;
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        ContextInfo testContextInfo = new ContextInfo();
        try {
            roomService.getBuildingIdsByCampus("1", testContextInfo);
            assert false;
        } catch (InvalidParameterException e) {
            //this is the exception we expect
            assert true;
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        testContextInfo.setPrincipalId("principalId");
        testContextInfo.setCurrentDate(null);
        try {
            roomService.getBuildingIdsByCampus("1", testContextInfo);
            assert false;
        } catch (InvalidParameterException e) {
            //this is the exception we expect
            assert true;
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        try {
            roomService.getBuildingIdsByCampus(null, contextInfo);
            assert false;
        } catch (MissingParameterException e) {
            //this is the exception we are expecting
            assert true;
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        try {
            roomService.getBuildingIdsByCampus("", contextInfo);
            assert false;
        } catch (MissingParameterException e) {
            //this is the exception we are expecting
            assert true;
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        List<String> ids = null;
        try {
            ids = roomService.getBuildingIdsByCampus("1", contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }
        assert(ids.size() == 2);

        List<BuildingInfo> buildingInfos = null;
        try {
            buildingInfos = roomService.getBuildingsByIds(ids, contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }
        assert buildingInfos.size() == 2;

        try {
            StatusInfo statusInfo = roomService.deleteBuilding(b1.getId(), contextInfo);
            assert statusInfo.getIsSuccess();
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        try {
            buildingInfos = roomService.getBuildingsByIds(ids, contextInfo);
        } catch (DoesNotExistException e) {
            //this is the exception we are expecting
            assert true;
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        try {
            ids = roomService.getBuildingIdsByCampus("1", contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        assert ids.size() == 1;
    }

    @Test
    public void testAttributes() {
        contextInfo.setPrincipalId("testAttributes");
        contextInfo.setCurrentDate( new Date() );

        BuildingInfo b = null;
        try {
            b = roomService.createBuilding("", createBuildingInfo("ADM", "umuc", "Administration Building", "active", "administration", "the administration building", "the administration building"), contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        b = addAttributeInfo(b, "key1", "value1");
        b = addAttributeInfo(b, "key2", "value2");

        try {
            b = roomService.updateBuilding(b.getId(), b, contextInfo);
        } catch (Throwable t) {
            t.printStackTrace();
            assert false;
        }

        assert b.getAttributes().size() == 2;
    }

    @Ignore
    public boolean equals(BuildingInfo b1, BuildingInfo b2) {
        assert b1.getBuildingCode().equals(b2.getBuildingCode());
        assert b1.getCampusKey().equals(b2.getCampusKey());
        assert b1.getId().equals(b2.getId());
        assert b1.getName().equals(b2.getName());
        assert b1.getStateKey().equals(b2.getStateKey());
        assert b1.getTypeKey().equals(b2.getTypeKey());

        MetaInfo m1 = b1.getMeta();
        MetaInfo m2 = b2.getMeta();
        assert m1.getCreateId().equals(m2.getCreateId());
        assert m1.getCreateTime().equals(m2.getCreateTime());
        assert m1.getUpdateId().equals(m2.getUpdateId());
        assert m1.getUpdateTime().equals(m2.getUpdateTime());

        RichTextInfo r1 = b1.getDescr();
        RichTextInfo r2 = b2.getDescr();
        assert r1.getFormatted().equals(r2.getFormatted());
        assert r1.getPlain().equals(r2.getPlain());

        List<AttributeInfo> a1 = b1.getAttributes();
        List<AttributeInfo> a2 = b2.getAttributes();
        assert a1.size() == a2.size();

        return true;
    }
}
