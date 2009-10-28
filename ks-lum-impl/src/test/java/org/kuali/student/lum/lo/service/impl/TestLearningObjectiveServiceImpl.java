package org.kuali.student.lum.lo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationTypeInfo;
import org.kuali.student.lum.lo.dto.LoRepositoryInfo;
import org.kuali.student.lum.lo.dto.LoTypeInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;

@Daos({@Dao(value = "org.kuali.student.lum.lo.dao.impl.LoDaoImpl", testSqlFile = "classpath:ks-lo.sql")})
@PersistenceFileLocation("classpath:META-INF/lo-persistence.xml")
public class TestLearningObjectiveServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.lum.lo.service.impl.LearningObjectiveServiceImpl", additionalContextFile = "classpath:lo-additional-context.xml")
    public LearningObjectiveService client;

    @Test
    public void testLo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException {
        LoInfo loInfo = new LoInfo();
        loInfo.setName("How Lo Can You Go?");
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p>New ResultComponent</p>");
        richText.setPlain("New ResultComponent");
        loInfo.setDesc(richText);
        Date date = new Date();
        loInfo.setEffectiveDate(date);
        loInfo.setExpirationDate(date);
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("attrKey", "attrValue");
        loInfo.setAttributes(attributes);
        loInfo.setState("draft");

        LoInfo created = client.createLo("kuali.loRepository.key.singleUse", "kuali.lo.type.singleUse", loInfo); 
        assertNotNull(created);
        String loId = created.getId();
        assertNotNull(loId);
        
        created = client.getLo(loId);

        RichTextInfo desc = created.getDesc();
        assertNotNull(desc);
        assertEquals("<p>New ResultComponent</p>", desc.getFormatted());
        assertEquals("New ResultComponent", desc.getPlain());
        assertEquals(date.toString(), created.getEffectiveDate().toString());
        assertEquals(date.toString(), created.getExpirationDate().toString());
        Map<String, String> newAttributes = created.getAttributes();
        assertNotNull(newAttributes);
        assertEquals("attrValue", newAttributes.get("attrKey"));
        assertEquals("kuali.lo.type.singleUse", created.getType()); 
        assertEquals("draft", created.getState());

        loInfo = client.getLo(loId);
        loInfo.setName("Lo in the mid-30s");

        LoInfo updated = client.updateLo(loId, loInfo);
        assertNotNull(updated);
        assertEquals(loId, updated.getId());
        desc = updated.getDesc();
        assertNotNull(desc);
        assertEquals("<p>New ResultComponent</p>", desc.getFormatted());
        assertEquals("New ResultComponent", desc.getPlain());
        assertEquals(date.toString(), updated.getEffectiveDate().toString());
        assertEquals(date.toString(), updated.getExpirationDate().toString());
        newAttributes = updated.getAttributes();
        assertNotNull(newAttributes);
        assertEquals("attrValue", newAttributes.get("attrKey"));
        assertEquals("kuali.lo.type.singleUse", updated.getType()); 
        assertEquals("draft", updated.getState());

        try {
            client.updateLo(loId, loInfo);
            fail("VersionMismatchException expected");
        } catch (VersionMismatchException e) {}

        // Detecting expected errors
        loInfo = new LoInfo();
        /* So, how do you create a LO that either has no parent yet,
         * or will be the root of a hierarchy? And, had to relax the
         * requirement so as to allow the first createLo() above to pass.
        try {
            client.createLo(null, "anytype", loInfo);
            throw new Exception();
        } catch (MissingParameterException e) {} catch (Exception e) {
            fail("MissingParameterException expected for parentLoId");
        }
        */
        try {
            client.createLo(null, "kuali.lo.type.singleUse", loInfo);
            fail("MissingParameterException expected for loRepositoryId");
        } catch (MissingParameterException e) {}
        try {
            client.createLo("kuali.loRepository.key.singleUse", null, loInfo);
            fail("MissingParameterException expected for loTypeId");
        } catch (MissingParameterException e) {}
        try {
            client.createLo("kuali.loRepository.key.singleUse", "kuali.lo.type.singleUse", null);
            fail("MissingParameterException expected for loInfo");
        } catch (MissingParameterException e) {}
        
        try {
            client.getLo(null);
            fail("MissingParameterException expected for loId");
        } catch (MissingParameterException e) {}
        
        try {
            client.updateLo(null, loInfo);
            fail("MissingParameterException expected for loId");
        } catch (MissingParameterException e) {}
        try {
            client.updateLo(loId, null);
            fail("MissingParameterException expected for loInfo");
        } catch (MissingParameterException e) {}
        
        StatusInfo statusInfo = client.deleteLo(loId);
        assertTrue(statusInfo.getSuccess());
    }
    
    @Test
    public void testGetLoByIdList() throws DoesNotExistException, InvalidParameterException, OperationFailedException, MissingParameterException {
    	List<LoInfo> loInfos = client.getLoByIdList(Arrays.asList("81ABEA67-3BCC-4088-8348-E265F3670145",
    																"DD0658D2-FDC9-48FA-9578-67A2CE53BF8A", 
    																"91A91860-D796-4A17-976B-A6165B1A0B05"));
    	assertEquals(3, loInfos.size());
    }
    
    @Test
    public void testGetRelatedLosByLoId() throws DoesNotExistException, InvalidParameterException, OperationFailedException {
    	List<LoInfo> relatedLos = null;
    	try {
    		relatedLos = client.getRelatedLosByLoId("81ABEA67-3BCC-4088-8348-E265F3670145", "kuali.lo.relation.type.includes");
    	} catch (Exception e) {
            fail("Exception caught when calling LearningObjectiveService.getRelatedLosByLoId(): " + e.getMessage());
    	}
    	assertNotNull(relatedLos);
    	assertEquals(2, relatedLos.size());
		assertTrue(relatedLos.get(0).getId().equals("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF") || relatedLos.get(1).getId().equals("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF"));

        // Detecting expected errors
        try {
    		relatedLos = client.getRelatedLosByLoId(null, "kuali.lo.relation.type.includes");
            fail("MissingParameterException expected for loId");
        } catch (MissingParameterException e) {}
        try {
    		relatedLos = client.getRelatedLosByLoId("81ABEA67-3BCC-4088-8348-E265F3670145", null);
            fail("MissingParameterException expected for loLoRelationTypeKey");
        } catch (MissingParameterException e) {}
    }
    
    @Test
    public void testGetLoRepositories() throws DoesNotExistException, InvalidParameterException, OperationFailedException {
    	List<LoRepositoryInfo> repos = null;
    	try {
    		repos = client.getLoRepositories();
    	} catch (Exception e) {
            fail("Exception caught when calling LearningObjectiveService.getLoRepositories(): " + e.getMessage());
    	}
    	assertNotNull(repos);
    	assertEquals(3, repos.size());
    	
		boolean found = false;
		String  repoId = "kuali.loRepository.key.state";
		for (LoRepositoryInfo loRInfo : repos) {
			if (loRInfo.getId().equals(repoId)) {
				found = true;
			}
		}
		if (!found) {
			fail("Unable to find expected LoRepository with ID == " + repoId);
		}
    }
    
    @Test
    public void testGetLoRepository() throws DoesNotExistException, InvalidParameterException, OperationFailedException {
    	LoRepositoryInfo repo = null;
    	try {
    		repo = client.getLoRepository("kuali.loRepository.key.state");
    	} catch (Exception e) {
            fail("Exception caught when calling LearningObjectiveService.getLoRepository(): " + e.getMessage());
    	}
    	assertNotNull(repo);
    	assertEquals("Learning objectives mandated by the state", repo.getDesc().getPlain());
        // Detecting expected errors
        try {
    		client.getLoRepository(null);
            fail("MissingParameterException expected for loRepositoryKey");
        } catch (MissingParameterException e) {}
    }
    
    @Test
    public void testGetLoTypesAndGetLoTypes() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<LoTypeInfo> loTypes = client.getLoTypes();
        assertNotNull(loTypes);
        assertTrue(!loTypes.isEmpty()); //this gets populated elsewhere
        
        LoTypeInfo loType = client.getLoType(loTypes.get(0).getId());
        assertEquals(loTypes.get(0).getName(), loType.getName());
        
        loType = client.getLoType("kuali.lo.type.formal");
        assertEquals("Formal", loType.getName());
        
        // Detecting expected errors
        try {
    		client.getLoType(null);
            fail("MissingParameterException expected for loTypeKey");
        } catch (MissingParameterException e) {}
    }
    
    @Test
    public void testGetLoLoRelationTypes()  {
    	List<LoLoRelationTypeInfo> llrtInfos = null;
    	try {
    		llrtInfos = client.getLoLoRelationTypes();
    	} catch (Exception e) {
            fail("Exception caught when calling LearningObjectiveService.getLoLoRelationTypes(): " + e.getMessage());
    	}
    	assertNotNull(llrtInfos);
    	assertEquals(2, llrtInfos.size());
    	assertTrue(llrtInfos.get(0).getName().equals("inSupportOf") || llrtInfos.get(1).getName().equals("inSupportOf"));
    			
    }
    
    @Test
    public void testGetLoLoRelation() throws OperationFailedException, DoesNotExistException, InvalidParameterException  {
    	LoLoRelationInfo llrInfo = null;
    	try {
    		llrInfo = client.getLoLoRelation("61FF5B2C-5D2F-464B-B6D8-082FBF671FCB");
    	} catch (Exception e) {
            fail("Exception caught when calling LearningObjectiveService.getLoLoRelation(): " + e.getMessage());
    	}
    	assertNotNull(llrInfo);
    	assertEquals("81ABEA67-3BCC-4088-8348-E265F3670145", llrInfo.getLo());
    	assertEquals("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A", llrInfo.getRelatedLo());
    	assertEquals("kuali.lo.relation.type.includes", llrInfo.getType());
    	assertEquals("draft", llrInfo.getState());
        // Detecting expected errors
        try {
    		client.getLoLoRelation(null);
            fail("MissingParameterException expected for loLoRelationId");
        } catch (MissingParameterException e) {}
    }
    
    @Test
    public void testCreateLoLoRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, CircularReferenceException, DataValidationErrorException, PermissionDeniedException {
    	LoLoRelationInfo llrInfo = new LoLoRelationInfo();
    	
    	try {
    		llrInfo = client.createLoLoRelation("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "91A91860-D796-4A17-976B-A6165B1A0B05", "kuali.lo.relation.type.includes", llrInfo);
    	} catch (Exception e) {
           fail("Exception caught when calling LearningObjectiveService.createLoLoRelation(): " + e.getMessage());
    	}
    	assertNotNull(llrInfo);
    	llrInfo = client.getLoLoRelation(llrInfo.getId());
    	assertEquals("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", llrInfo.getLo());
    	assertEquals("91A91860-D796-4A17-976B-A6165B1A0B05", llrInfo.getRelatedLo());
    	assertEquals("kuali.lo.relation.type.includes", llrInfo.getType());
    	assertEquals("draft", llrInfo.getState());
        // Detecting expected errors
        try {
    		client.createLoLoRelation(null, "foo", "bar", llrInfo);
            fail("MissingParameterException expected for loId");
        } catch (MissingParameterException e) {}
        try {
    		client.createLoLoRelation("foo", null, "bar", llrInfo);
            fail("MissingParameterException expected for relatedLoId");
        } catch (MissingParameterException e) {}
        try {
    		client.createLoLoRelation("foo", "bar", null, llrInfo);
            fail("MissingParameterException expected for loLoRelationType");
        } catch (MissingParameterException e) {}
        try {
    		client.createLoLoRelation("foo", "bar", "baz", null);
            fail("MissingParameterException expected for loLoRelationInfo");
        } catch (MissingParameterException e) {}
    }
    
    @Test
    public void testGetLoLoRelationType() throws OperationFailedException, DoesNotExistException  {
    	LoLoRelationTypeInfo llrtInfo = null;
    	try {
    		llrtInfo = client.getLoLoRelationType("kuali.lo.relation.type.includes");
    	} catch (Exception e) {
            fail("Exception caught when calling LearningObjectiveService.getLoLoRelationType(): " + e.getMessage());
    	}
    	assertNotNull(llrtInfo);
    	assertEquals("includes", llrtInfo.getName());
        // Detecting expected errors
        try {
    		client.getLoLoRelationType(null);
            fail("MissingParameterException expected for loLoRelationTypeKey");
        } catch (MissingParameterException e) {}
    }
    
    @Test
    public void testGetAllowedLoLoRelationTypesForLoType() {
    	
    }
    
    
    
    /*
    @Test
    public void testIncludedLo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, CircularReferenceException, PermissionDeniedException, UnsupportedActionException, DependentObjectsExistException {
        List<String> ancestors = client.getAncestors("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF"); 
        assertTrue(!ancestors.contains("ABD8AE21-34E9-4858-A714-B04134F55D68"));
        
        StatusInfo result = client.addChildLoToLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "ABD8AE21-34E9-4858-A714-B04134F55D68");
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        ancestors = client.getAncestors("7BCD7C0E-3E6B-4527-AC55-254C58CECC22");
        assertTrue(ancestors.contains("ABD8AE21-34E9-4858-A714-B04134F55D68")); 
        
        try {
	        result = client.addChildLoToLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "ABD8AE21-34E9-4858-A714-B04134F55D68");
            fail("relationship should already exists and thus throw AlreadyExistsException");
        } catch (AlreadyExistsException e) {
        }
        
        List<String> descendants = client.getAllDescendants("ABD8AE21-34E9-4858-A714-B04134F55D68");
        assertTrue(descendants.contains("7BCD7C0E-3E6B-4527-AC55-254C58CECC22"));
        
        assertTrue(client.isDescendant("ABD8AE21-34E9-4858-A714-B04134F55D68", "7BCD7C0E-3E6B-4527-AC55-254C58CECC22"));
        
        List<LoInfo> children = client.getLoChildren("ABD8AE21-34E9-4858-A714-B04134F55D68");
        boolean foundLo = false;
        for (LoInfo loInfo : children) {
            if(loInfo.getId().equals("7BCD7C0E-3E6B-4527-AC55-254C58CECC22")) {
                foundLo = true;
                break;
            }
        }
        assertTrue(foundLo);
        
        List<LoInfo> parents = client.getLoParents("7BCD7C0E-3E6B-4527-AC55-254C58CECC22"); 
        foundLo = false;
        for (LoInfo loInfo : parents) {
            if(loInfo.getId().equals("ABD8AE21-34E9-4858-A714-B04134F55D68")) {
                foundLo = true;
                break;
            }
        }
        assertTrue(foundLo);
        
        try { // this would result in "7B..." being orphaned
	        result = client.removeChildLoFromLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "ABD8AE21-34E9-4858-A714-B04134F55D68");
	        fail("LearningObjectiveService.removeChildLoFromLo should have thrown DependentObjectsExistException");
        } catch (DependentObjectsExistException doee) {}
        
        // add it as a child to another parent
        result = client.addChildLoToLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        // and now remove it from "AB..."'s children
        result = client.removeChildLoFromLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "ABD8AE21-34E9-4858-A714-B04134F55D68");
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        assertTrue(!client.isDescendant("ABD8AE21-34E9-4858-A714-B04134F55D68", "7BCD7C0E-3E6B-4527-AC55-254C58CECC22"));
    }
    */
    
    /*
    @Test
    public void testLoCategory() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, UnsupportedActionException {
        
        List<LoCategoryInfo> categories = client.getLoCategories("loHierarchy.empty.test");
        assertTrue(null == categories || categories.isEmpty());
        
        LoCategoryInfo category = new LoCategoryInfo();
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p>New Category</p>");
        richText.setPlain("New Category");
        category.setDesc(richText);
        Date date = new Date();
        category.setEffectiveDate(date);
        category.setExpirationDate(date);
        category.setName("BOB, THE AMAAAAAAZING WONDER LLAMA!!");
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("attrKey", "attrValue");
        category.setAttributes(attributes);
        
        
        String categoryId = "7114D2A4-F66D-4D3A-9D41-A7AA4299C79";
        category = client.getLoCategory(categoryId);
        category.setName("LENNY, THE LECHEROUS MILK THIEF");
        
        LoCategoryInfo updated = client.updateLoCategory(categoryId, category);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        
        try {
            client.updateLoCategory(categoryId, category);
            fail("VersionMismatchException expected");
        } catch (VersionMismatchException e) {}
        
        List<LoInfo> los = client.getLosByLoCategory(categoryId);
        assertTrue(null == los || los.isEmpty());
        
        StatusInfo statusInfo = client.addLoCategoryToLo(categoryId, "91A91860-D796-4A17-976B-A6165B1A0B05");
        assertTrue(statusInfo.getSuccess());
        
        los = client.getLosByLoCategory(categoryId);
        assertEquals(1, los.size());
        assertEquals("91A91860-D796-4A17-976B-A6165B1A0B05", los.get(0).getId()); 
        
        categories = client.getLoCategoriesForLo("91A91860-D796-4A17-976B-A6165B1A0B05");
        boolean foundCat = false;
        for (LoCategoryInfo loCategoryInfo : categories) {
            if(loCategoryInfo.getId().equals(categoryId)) {
                foundCat = true;
                break;
            }
        }
        assertTrue(foundCat);
        
        try {
            statusInfo = client.deleteLoCategory(categoryId);
            fail("DependentObjectsExistException expected"); 
        } catch(DependentObjectsExistException e) {}
        
        statusInfo = client.removeLoCategoryFromLo(categoryId, "91A91860-D796-4A17-976B-A6165B1A0B05");
        assertTrue(statusInfo.getSuccess());
        
        statusInfo = client.deleteLoCategory(categoryId);
        assertTrue(statusInfo.getSuccess());
    }
    */
	@Test
	public void testSearchForResults() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		String testLoName = "Navigate Wiki";
		List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
		QueryParamValue qpv1 = new QueryParamValue();
		qpv1.setKey("lo.queryParam.loName");
		qpv1.setValue(testLoName);
		queryParamValues.add(qpv1);
		List<Result> results = client.searchForResults("lo.search.loByName", queryParamValues);
		assertEquals(1,results.size());
		
        List<ResultCell> resultCells = results.get(0).getResultCells();
        assertEquals(2, resultCells.size());
        ResultCell cell = resultCells.get(0);
        assertEquals("lo.resultColumn.loId", cell.getKey());
        assertEquals("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF", cell.getValue());
        cell = resultCells.get(1);
        assertEquals("lo.resultColumn.loName", cell.getKey());
        assertEquals(testLoName, cell.getValue());
	}
}
