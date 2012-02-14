package org.kuali.student.enrollment.class1.lui.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lui-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestLuiServiceImpl {

    @Resource  // look up bean via variable name, then type
    private LuiService luiServiceValidation;

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo ();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testLuiServiceSetup() {
        assertNotNull(luiServiceValidation);
    }

    @Test
    public void testGetLui() throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        try {
            try {
                luiServiceValidation.getLui("Lui-blah", callContext);
            } catch (DoesNotExistException enee) {
            }

            LuiInfo obj = luiServiceValidation.getLui("Lui-1", callContext);
            assertNotNull(obj);
            assertEquals("Lui one", obj.getName());
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, obj.getStateKey());
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, obj.getTypeKey());
            assertEquals("Lui Desc 101", obj.getDescr().getPlain());
            assertNotNull(obj.getOfficialIdentifier());
            assertEquals("Chem 123", obj.getOfficialIdentifier().getShortName());

            // TODO - remove the following, or fix
            // List<OfferingInstructorInfo> instructors = obj.getInstructors();
            // assertTrue(instructors.size() == 1);
            // assertEquals("Pers-1", instructors.get(0).getPersonId());
            // assertEquals("Org-1", instructors.get(0).getOrgId());
            // assertEquals("Instr-1",
            // instructors.get(0).getPersonInfoOverride());
            // assertEquals(Float.valueOf("30.5"),
            // instructors.get(0).getPercentageEffort());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testGetLuiIdsByRelation()
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
    	 try {
    		 List<String> luiIds = luiServiceValidation.getLuiIdsByRelation(
                     "Lui-2", "kuali.lui.lui.relation.associated", callContext);
    		 assertNotNull(luiIds);
    	     assertEquals(2, luiIds.size());
    	     assertEquals("Lui-1", luiIds.get(0));
             assertEquals("Lui-5", luiIds.get(1));
    	 } catch (Exception e) {
    		 fail(e.getMessage());
    	 }
    }
    
    @Test
    public void testGetLuisByRelation()throws InvalidParameterException, MissingParameterException,
		OperationFailedException {
    	 try {
    		 List<LuiInfo> luis = luiServiceValidation.getLuisByRelation(
                     "Lui-2", "kuali.lui.lui.relation.associated", callContext);
    		 assertNotNull(luis);
    	     assertEquals(2, luis.size());
    	     assertEquals("Lui-1", luis.get(0).getId());
             assertEquals("Lui-5", luis.get(1).getId());
    	 } catch (Exception e) {
    		 fail(e.getMessage());
    	 }
    }
    
    @Test
    @Ignore
    // TODO: enable after https://jira.kuali.org/browse/KULRICE-6705 is fixed
    public void testCreateLui() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiInfo info = new LuiInfo();
        info.setName("Test lui one");
        info.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        info.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        info.setEffectiveDate(Calendar.getInstance().getTime());
        info.setMaximumEnrollment(25);
        info.setMinimumEnrollment(10);
        // info.setStudySubjectArea("Math");
        info.setCluId("testCluId");
        info.setAtpId("testAtpId1");

        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
        OfferingInstructorInfo instructor = new OfferingInstructorInfo();
        instructor.setId("LUI-INSTR-Test-1");
        instructor.setPersonId("Pers-2");
        instructor.setPercentageEffort((float) 100);
        instructors.add(instructor);
        // info.setInstructors(instructors);

        LuiInfo created = null;
        // try{
        created = luiServiceValidation.createLui("testCluId", "testAtpId1", info, callContext);
        assertNotNull(created);
        assertNull("official identifier was non-null after creation even though it was never set intentionally", created.getOfficialIdentifier());
        assertEquals("Test lui one", created.getName());
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, created.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, created.getTypeKey());
        assertEquals(Integer.valueOf(25), created.getMaximumEnrollment());
        assertEquals(Integer.valueOf(10), created.getMinimumEnrollment());
        assertEquals("testCluId", created.getCluId());
        assertEquals("testAtpId1", created.getAtpId());
        // assertEquals("Math", created.getStudySubjectArea());
        // assertTrue(created.getInstructors().size() == 1);

        // } catch (Exception e) {
        // throw new RuntimeException ("unexpected exception", e);
        // fail(e.getMessage());
        // }

        try {
            LuiInfo retrieved = luiServiceValidation.getLui(created.getId(), callContext);
            assertNotNull(retrieved);
            assertEquals("Test lui one", retrieved.getName());
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey());
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, retrieved.getTypeKey());
            assertEquals(Integer.valueOf(25), retrieved.getMaximumEnrollment());
            assertEquals(Integer.valueOf(10), retrieved.getMinimumEnrollment());
            assertEquals("testCluId", retrieved.getCluId());
            assertEquals("testAtpId1", retrieved.getAtpId());
            // assertEquals("Math", retrieved.getStudySubjectArea());
            // assertTrue(retrieved.getInstructors().size() == 1);
            // assertEquals("Org-1",
            // retrieved.getInstructors().get(0).getOrgId());
            // assertEquals("Pers-2",
            // retrieved.getInstructors().get(0).getPersonId());
        } catch (DoesNotExistException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateLui() throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        LuiInfo info = luiServiceValidation.getLui("Lui-1", callContext);
        assertNotNull(info);
        assertEquals("Lui-1", info.getId());
        assertEquals("Lui one", info.getName());

        LuiInfo modified = new LuiInfo(info);
        modified.setStateKey(LuiServiceConstants.LUI_APROVED_STATE_KEY);
        modified.setMaximumEnrollment(25);
        modified.setMinimumEnrollment(10);
        assertNotNull(modified.getOfficialIdentifier());
        modified.getOfficialIdentifier().setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        modified.getOfficialIdentifier().setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        
        // try{
        LuiInfo updated = luiServiceValidation.updateLui("Lui-1", modified, callContext);
        assertNotNull(updated);
        assertEquals(LuiServiceConstants.LUI_APROVED_STATE_KEY, updated.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, updated.getTypeKey());
        assertEquals(Integer.valueOf(25), updated.getMaximumEnrollment());
        assertEquals(Integer.valueOf(10), updated.getMinimumEnrollment());
        // }catch (Exception e) {
        // fail(e.getMessage());
        // }

    }

    @Test
    public void testGetLuiLuiRelation() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        try {
            LuiLuiRelationInfo obj = luiServiceValidation.getLuiLuiRelation("LUILUIREL-1", callContext);
            assertNotNull(obj);
            assertEquals("Lui-1", obj.getLuiId());
            assertEquals("Lui-2", obj.getRelatedLuiId());

        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testGetLuiLuiRelationsByLui() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        try {
            try {
                luiServiceValidation.getLuiLuiRelationsByLui("Lui-Lui-Blah", callContext);
            } catch (DoesNotExistException enee) {
            }

            List<LuiLuiRelationInfo> objs = luiServiceValidation.getLuiLuiRelationsByLui("Lui-1", callContext);
            assertNotNull(objs);
            assertEquals(1, objs.size());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    @Ignore
    // TODO: enable after https://jira.kuali.org/browse/KULRICE-6705 is fixed
    public void testCreateLuiLuiRelation() throws AlreadyExistsException, CircularRelationshipException,
            DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LuiInfo info = new LuiInfo();
        info.setName("Test lui-Lui relation");
        info.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        info.setTypeKey(LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY);
        info.setEffectiveDate(Calendar.getInstance().getTime());
        info.setMaximumEnrollment(25);
        info.setMinimumEnrollment(10);

        LuiInfo newLui = luiServiceValidation.createLui("testCluId", "testAtpId1", info, callContext);
        LuiLuiRelationInfo created = null;
        try {
            LuiLuiRelationInfo rel = new LuiLuiRelationInfo();
            rel.setLuiId("Lui-1");
            rel.setRelatedLuiId(newLui.getId());
            rel.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
            rel.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY);
            rel.setEffectiveDate(Calendar.getInstance().getTime());
            created = luiServiceValidation.createLuiLuiRelation("Lui-1", newLui.getId(),
                    LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, rel, callContext);

            assertNotNull(created);
            assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, created.getStateKey());
            assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, created.getTypeKey());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }

        try {
            LuiLuiRelationInfo retrieved = luiServiceValidation.getLuiLuiRelation(created.getId(), callContext);
            assertNotNull(retrieved);
            assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, retrieved.getStateKey());
            assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, retrieved.getTypeKey());

            List<LuiLuiRelationInfo> objs = luiServiceValidation.getLuiLuiRelationsByLui("Lui-1", callContext);
            assertNotNull(objs);
            assertEquals(2, objs.size());
            for (LuiLuiRelationInfo obj : objs) {
                assertTrue(obj.getRelatedLuiId().equals("Lui-2") || obj.getRelatedLuiId().equals(newLui.getId()));
            }

        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testDeleteLui() throws DependentObjectsExistException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiInfo info = luiServiceValidation.getLui("Lui-3", callContext);
        assertNotNull(info);

        List<LuiLuiRelationInfo> objs = luiServiceValidation.getLuiLuiRelationsByLui("Lui-3", callContext);
        assertNotNull(objs);
        assertEquals(1, objs.size());

        try {
            try {
                luiServiceValidation.deleteLui("Lui-3-blah", callContext);
            } catch (DoesNotExistException ee) {
            }

            StatusInfo status = luiServiceValidation.deleteLui("Lui-3", callContext);
            assertTrue(status.getIsSuccess());

            try {
                luiServiceValidation.getLuiLuiRelationsByLui("Lui-3", callContext);
            } catch (DoesNotExistException ee) {
            }

            try {
                luiServiceValidation.getLui("Lui-3", callContext);
            } catch (DoesNotExistException ee) {
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
