/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.lo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
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
    public void testLo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException {
        LoInfo loInfo = new LoInfo();
        loInfo.setName("How Lo Can You Go");
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p>New ResultComponent</p>");
        richText.setPlain("New ResultComponent");
        loInfo.setDesc(richText);
        Date date = new Date();
        loInfo.setEffectiveDate(date);
        loInfo.setExpirationDate(date);
        loInfo.setLoRepositoryKey("kuali.loRepository.key.singleUse");
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("attrKey", "attrValue");
        loInfo.setAttributes(attributes);
        loInfo.setType("kuali.lo.type.singleUse");
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
        loInfo.setName("Lo in the mid 30s");

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
        
        // now make sure we can't orphan "included" LO's
    	LoLoRelationInfo llrInfo = new LoLoRelationInfo();
    	
		llrInfo = client.createLoLoRelation("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "91A91860-D796-4A17-976B-A6165B1A0B05", "kuali.lo.relation.type.includes", llrInfo);
    	assertNotNull(llrInfo);
    	llrInfo = client.getLoLoRelation(llrInfo.getId());
    	try {
    		client.deleteLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22");
    		fail("Deleted an LO which orphaned included LO(s)");
    	} catch (DependentObjectsExistException doee) {}
    }
    
    @Test
    public void testGetLoByIdList() throws DoesNotExistException, InvalidParameterException, OperationFailedException, MissingParameterException {
    	List<LoInfo> loInfos = client.getLoByIdList(Arrays.asList("81ABEA67-3BCC-4088-8348-E265F3670145",
    																"DD0658D2-FDC9-48FA-9578-67A2CE53BF8A", 
    																"91A91860-D796-4A17-976B-A6165B1A0B05"));
    	assertEquals(3, loInfos.size());
    }
    
	/*
	 * Creating an LoCategory with the same name, type & state
	 */
	@Test
	public void testDisallowLoCategoryDuplication() throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
		String catName = "DontDupThisCategory";
		String catState = "active";
		String catType = "loCategoryType.accreditation";
		String catRepo = "kuali.loRepository.key.singleUse";
		
		LoCategoryInfo newCatInfo = new LoCategoryInfo();
		newCatInfo.setName(catName);
		newCatInfo.setType(catType);
		newCatInfo.setState(catState);
		newCatInfo.setLoRepository(catRepo);
		
		newCatInfo = client.createLoCategory(catRepo, catType, newCatInfo);
		
		LoCategoryInfo dupCatInfo = new LoCategoryInfo();
		dupCatInfo.setName(catName);
		dupCatInfo.setType(catType);
		dupCatInfo.setState(catState);
		dupCatInfo.setLoRepository(catRepo);
		
		
		try {
			dupCatInfo = client.createLoCategory(catRepo, catType, dupCatInfo);
			// delete the two (one erroneously) created so as to not mess up other tests
			client.deleteLoCategory(newCatInfo.getId());
			client.deleteLoCategory(dupCatInfo.getId());
            fail("OperationFailedException expected when creating LoCategory with the same name, type and state");
		} catch (OperationFailedException ofe) {
			// expected result
		}
		// delete the one created so as to not mess up other tests
		client.deleteLoCategory(newCatInfo.getId());
	}	

    @Test
    public void testDisallowLoWEmptyDesc() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException {
        LoInfo loInfo = new LoInfo();
        loInfo.setName("Lo with Empty Desc");
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p> </p>");
        richText.setPlain(" ");
        loInfo.setDesc(richText);
        Date date = new Date();
        loInfo.setEffectiveDate(date);
        loInfo.setExpirationDate(date);
        loInfo.setLoRepositoryKey("kuali.loRepository.key.singleUse");
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("attrKey", "attrValue");
        loInfo.setAttributes(attributes);
        loInfo.setType("kuali.lo.type.singleUse");
        loInfo.setState("draft");

        try {
        	 LoInfo created = client.createLo("kuali.loRepository.key.singleUse", "kuali.lo.type.singleUse", loInfo); 
        	 assertNotNull(created);
        	
          // delete the one erroneously created so as to not mess up other tests
        	 StatusInfo statusInfo = client.deleteLo(created.getId());
             assertTrue(statusInfo.getSuccess());            
             fail("OperationFailedException expected when creating Lo with empty description");
        } catch (MissingParameterException mpe) {
			// expected result
		}
      }
    
    @Test
    public void testDisallowLoCategoryWEmptyDesc() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException {
    	String catName = "DontDupThisCategorytest";
		String catState = "active";
		String catType = "loCategoryType.accreditation";
		String catRepo = "kuali.loRepository.key.singleUse";
		
		LoCategoryInfo newCatInfo = new LoCategoryInfo();
		newCatInfo.setName(catName);
		newCatInfo.setType(catType);
		newCatInfo.setState(catState);
		newCatInfo.setLoRepository(catRepo);
		
		//Testing KSLAB-692
	      RichTextInfo richText = new RichTextInfo();
	      richText.setFormatted("<p> </p>");
	      richText.setPlain("  ");
	      newCatInfo.setDesc(richText);
		
		try{
				newCatInfo = client.createLoCategory(catRepo, catType, newCatInfo);
				assertNotNull(newCatInfo);
	        	
	          // delete the one erroneously created so as to not mess up other tests
	        	 StatusInfo statusInfo = client.deleteLoCategory(newCatInfo.getId());
	             assertTrue(statusInfo.getSuccess());            
	             fail("OperationFailedException expected when creating LoCategory with empty description");
		} catch (MissingParameterException mpe) {
			// expected result
		}
			
    }
       
	/*
	 * Creating an LoCategory with the same name (case insensitive), type & state
	 */
	
	@Test
	public void testDisallowLoCategoryDuplicationCaseInsensitive() throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
		String catName = "DontDupThisCategory";
		String catState = "active";
		String catType = "loCategoryType.accreditation";
		String catRepo = "kuali.loRepository.key.singleUse";
		
		LoCategoryInfo newCatInfo = new LoCategoryInfo();
		newCatInfo.setName(catName);
		newCatInfo.setType(catType);
		newCatInfo.setState(catState);
		newCatInfo.setLoRepository(catRepo);
		
		try{				
			newCatInfo = client.createLoCategory(catRepo, catType, newCatInfo);
			newCatInfo = client.getLoCategory(newCatInfo.getId());
			catName = newCatInfo.getName();
			catRepo = newCatInfo.getLoRepository();
		} catch (OperationFailedException ofe) {
			System.err.println(ofe.getMessage());
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
		
		String dupCatName = "dontDupThisCategory";
		LoCategoryInfo dupCatInfo = new LoCategoryInfo();
		dupCatInfo.setName(dupCatName);
		dupCatInfo.setType(catType);
		dupCatInfo.setState(catState);
		dupCatInfo.setLoRepository(catRepo);
		
		
		try {
			dupCatInfo = client.createLoCategory(catRepo, catType, dupCatInfo);
			dupCatInfo = client.getLoCategory(dupCatInfo.getId());
			dupCatName = dupCatInfo.getName();
			
			// delete the two (one erroneously) created so as to not mess up other tests
			client.deleteLoCategory(newCatInfo.getId());
			client.deleteLoCategory(dupCatInfo.getId());
            fail("OperationFailedException expected when creating LoCategory with the same name, type and state");
		} catch (OperationFailedException ofe) {
			// expected result
		}
		// delete the one created so as to not mess up other tests
		client.deleteLoCategory(newCatInfo.getId());
	}	

	/*
	 * Updating a LoCategory with checking LoCategory duplication(based on name (case insensitive), type, state & repository
	 */
	
	@Test
	public void testUpdateLoCategoryDuplicationCaseInsensitive() throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
		String catState = "active";
		String catType = "loCategoryType.accreditation";
		String catRepo = "kuali.loRepository.key.singleUse";
		String catId1 = null;
		String catId2 = null;
		
		LoCategoryInfo catInfo1 = new LoCategoryInfo();
		catInfo1.setName( "DontDupThisCategory");
		catInfo1.setType(catType);
		catInfo1.setState(catState);
		catInfo1.setLoRepository(catRepo);
		
		LoCategoryInfo catInfo2 = new LoCategoryInfo();
		catInfo2.setName("DontDupThisCategory2");
		catInfo2.setType(catType);
		catInfo2.setState(catState);
		catInfo2.setLoRepository(catRepo);
		
		try{				
			catInfo1 = client.createLoCategory(catRepo, catType, catInfo1);
			catId1 = catInfo1.getId();
			
			catInfo2 = client.createLoCategory(catRepo, catType, catInfo2);
			catId2 = catInfo2.getId();
		} catch (OperationFailedException ofe) {
			System.err.println(ofe.getMessage());
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
		
		
		try {
			catInfo2.setName( "dontDupThisCategory");
			catInfo2 = client.updateLoCategory(catId2, catInfo2);
			
	
            fail("OperationFailedException expected when updating LoCategory with the same name, type and state");
		} catch (OperationFailedException ofe) {
			// expected result
		}catch (VersionMismatchException e){
			//expected for testing
		}
		
		// delete the two created so as to not mess up other tests
		client.deleteLoCategory(catId1);
		client.deleteLoCategory(catId2);
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
    public void testGetLoCategoryTypes() throws DoesNotExistException, InvalidParameterException, OperationFailedException {
    	List<LoCategoryTypeInfo> loCatTypeInfos = client.getLoCategoryTypes();
    	assertEquals(3, loCatTypeInfos.size());
    }
    	
    @Test
    public void testGetLoTypesAndGetLoType() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<LoTypeInfo> loTypes = client.getLoTypes();
        assertNotNull(loTypes);
        assertTrue(!loTypes.isEmpty());
        
        LoTypeInfo loType = client.getLoType(loTypes.get(0).getId());
        assertEquals(loTypes.get(0).getName(), loType.getName());
        
        loType = client.getLoType("kuali.lo.type.governed");
        assertEquals("Governed", loType.getName());
        
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
    public void testGetAllowedLoLoRelationTypesForLoType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<String> allowedTypes = client.getAllowedLoLoRelationTypesForLoType("kuali.lo.type.singleUse", "kuali.lo.type.singleUse");
    	assertEquals(1, allowedTypes.size());
    	allowedTypes = client.getAllowedLoLoRelationTypesForLoType("kuali.lo.type.governed", "kuali.lo.type.governed");
    	assertTrue(null == allowedTypes || allowedTypes.size() == 0);
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
    	assertEquals("81ABEA67-3BCC-4088-8348-E265F3670145", llrInfo.getLoId());
    	assertEquals("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A", llrInfo.getRelatedLoId());
    	assertEquals("kuali.lo.relation.type.includes", llrInfo.getType());
    	assertEquals("draft", llrInfo.getState());
        // Detecting expected errors
        try {
    		client.getLoLoRelation(null);
            fail("MissingParameterException expected for loLoRelationId");
        } catch (MissingParameterException e) {}
    }
    
	@Test
	public void testGetLoCategories() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		List<LoCategoryInfo> categories = client.getLoCategories("kuali.loRepository.key.singleUse");
		assertEquals(5, categories.size());
		categories = client.getLoCategories("kuali.loRepository.key.state");
		assertTrue(null == categories || categories.size() == 0);
	}
	
	@Test
	public void testGetLoCategoriesForLo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		List<LoCategoryInfo> categories = client.getLoCategoriesForLo("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
		assertNotNull(categories);
		assertEquals(2, categories.size());
		categories = client.getLoCategoriesForLo("E0619A90-66D6-4AF4-B357-E73AE44F7E88");
		assertEquals(1, categories.size());
		assertEquals("Test Category 3", categories.get(0).getName());
		categories = client.getLoCategoriesForLo("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF");
		assertTrue(null == categories || categories.size() == 0);
	}
    
	@Test
	public void testAddRemoveLoCategoryToFromLo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, PermissionDeniedException, UnsupportedActionException {
		List<LoCategoryInfo> categories = client.getLoCategoriesForLo("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
		assertEquals(2, categories.size());
		assertTrue(containsLoCatInfo(categories, Arrays.asList("550e8400-e29b-41d4-a716-446655440000", "7114D2A4-F66D-4D3A-9D41-A7AA4299C797")));
		client.addLoCategoryToLo("F2F02922-4E77-4144-AA07-8C2C956370DC", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
		categories = client.getLoCategoriesForLo("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
		assertEquals(3, categories.size());
		assertTrue(containsLoCatInfo(categories, Arrays.asList("550e8400-e29b-41d4-a716-446655440000", "7114D2A4-F66D-4D3A-9D41-A7AA4299C797", "F2F02922-4E77-4144-AA07-8C2C956370DC")));
		client.removeLoCategoryFromLo("F2F02922-4E77-4144-AA07-8C2C956370DC", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
		categories = client.getLoCategoriesForLo("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
		assertEquals(2, categories.size());
	}

    private boolean containsLoCatInfo(List<LoCategoryInfo> categories, List<String> idList) {
    	List<String> ids = new ArrayList<String>();
    	
    	for (LoCategoryInfo info : categories) {
    		ids.add(info.getId());
    	}
		return ids.containsAll(idList);
	}

    @Test
    public void testCreateLoLoRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, CircularReferenceException, DataValidationErrorException, PermissionDeniedException, CircularRelationshipException {
    	LoLoRelationInfo llrInfo = new LoLoRelationInfo();
    	
    	try {
    		llrInfo = client.createLoLoRelation("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "91A91860-D796-4A17-976B-A6165B1A0B05", "kuali.lo.relation.type.includes", llrInfo);
    	} catch (Exception e) {
           fail("Exception caught when calling LearningObjectiveService.createLoLoRelation(): " + e.getMessage());
    	}
    	assertNotNull(llrInfo);
    	llrInfo = client.getLoLoRelation(llrInfo.getId());
    	assertEquals("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", llrInfo.getLoId());
    	assertEquals("91A91860-D796-4A17-976B-A6165B1A0B05", llrInfo.getRelatedLoId());
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
    
    @Test
    public void testLoCategory() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, UnsupportedActionException {
        
        List<LoCategoryInfo> categories = client.getLoCategories("foo.bar.baz");
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
        
        
        String categoryId = "550e8400-e29b-41d4-a716-446655440000";
        
        category = client.getLoCategory(categoryId);
        assertEquals("Perception", category.getName());
        assertEquals("loCategoryType.skillarea", category.getType());
        category.setName("LENNY, THE LECHEROUS MILK THIEF");
        category.setType("loCategoryType.accreditation");
        
        List<LoInfo> twoLos = client.getLosByLoCategory(categoryId);
        assertTrue(null != twoLos);
        assertEquals(2, twoLos.size());
        assertTrue(twoLos.get(0).getId().equals("81ABEA67-3BCC-4088-8348-E265F3670145") ||
        			twoLos.get(0).getId().equals("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A"));
        assertTrue(twoLos.get(1).getId().equals("81ABEA67-3BCC-4088-8348-E265F3670145") ||
        			twoLos.get(1).getId().equals("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A"));
        
        LoCategoryInfo updated = client.updateLoCategory(categoryId, category);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        
        // make sure it all stuck
        updated = client.getLoCategory(updated.getId());
        assertEquals("LENNY, THE LECHEROUS MILK THIEF", updated.getName());
        assertEquals("loCategoryType.accreditation", updated.getType());
        
        try {
            client.updateLoCategory(categoryId, category);
            fail("VersionMismatchException expected");
        } catch (VersionMismatchException e) {}
           catch (OperationFailedException e) {}
           
        // switch to new LoCategory id; new one was created when we changed its type
        String newCategoryId = updated.getId();
        
        // make sure it's new
        assertFalse(categoryId.equals(newCategoryId));
       
        // make sure the LoCategories were cloned
        List<LoInfo> los = client.getLosByLoCategory(newCategoryId);
        assertEquals(2, los.size());
        assertTrue(los.get(0).getId().equals("81ABEA67-3BCC-4088-8348-E265F3670145") ||
        			los.get(0).getId().equals("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A"));
        assertTrue(los.get(1).getId().equals("81ABEA67-3BCC-4088-8348-E265F3670145") ||
        			los.get(1).getId().equals("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A"));
        
        los = client.getLosByLoCategory(categoryId);
        assertTrue(null == los || los.isEmpty());
        
        // add one to an LO that didn't have one
        categoryId = "054CAA88-C21D-4496-8287-36A311A11D68";
        StatusInfo statusInfo = client.addLoCategoryToLo(categoryId, "91A91860-D796-4A17-976B-A6165B1A0B05");
        assertTrue(statusInfo.getSuccess());
        
        los = client.getLosByLoCategory(categoryId);
        assertEquals(1, los.size());
        assertEquals("91A91860-D796-4A17-976B-A6165B1A0B05", los.get(0).getId()); 
        
        categories = client.getLoCategoriesForLo("91A91860-D796-4A17-976B-A6165B1A0B05");
        assertEquals(1, categories.size());
        
        try {
            statusInfo = client.deleteLoCategory(categoryId);
            fail("DependentObjectsExistException expected"); 
        } catch(DependentObjectsExistException e) {}
        
        statusInfo = client.removeLoCategoryFromLo(categoryId, "91A91860-D796-4A17-976B-A6165B1A0B05");
        assertTrue(statusInfo.getSuccess());
        
        los = client.getLosByLoCategory(categoryId);
        assertTrue(null == los || los.size() == 0);
        statusInfo = client.deleteLoCategory(categoryId);
        assertTrue(statusInfo.getSuccess());
    }
    
	@Test
	public void testSearchForResults() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		String testLoName = "Navigate Wiki";
		List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
		SearchParam qpv1 = new SearchParam();
		qpv1.setKey("lo.queryParam.loName");
		qpv1.setValue(testLoName);
		queryParamValues.add(qpv1);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setParams(queryParamValues);
		searchRequest.setSearchKey("lo.search.loByName");
		SearchResult result = client.search(searchRequest);
		assertEquals(1,result.getRows().size());
		
        List<SearchResultCell> resultCells = result.getRows().get(0).getCells();
        assertEquals(2, resultCells.size());
        SearchResultCell cell = resultCells.get(0);
        assertEquals("lo.resultColumn.loId", cell.getKey());
        assertEquals("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF", cell.getValue());
        cell = resultCells.get(1);
        assertEquals("lo.resultColumn.loName", cell.getKey());
        assertEquals(testLoName, cell.getValue());
	}
}
