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
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.r2.lum.lo.dto.LoRepositoryInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;

@Daos({@Dao(value = "org.kuali.student.r2.lum.lo.dao.impl.LoDaoImpl", testSqlFile = "classpath:ks-lo.sql")})
@PersistenceFileLocation("classpath:META-INF/lo-persistence.xml")
@FixMethodOrder(NAME_ASCENDING)
public class TestLearningObjectiveServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.r2.lum.lo.service.impl.LearningObjectiveServiceImpl", additionalContextFile = "classpath:lo-additional-context.xml")
    public LearningObjectiveService client;

    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
    @Test
    public void test00Lo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException, ReadOnlyException {
        LoInfo loInfo = new LoInfo();
        loInfo.setName("How Lo Can You Go");
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p>New ResultComponent</p>");
        richText.setPlain("New ResultComponent");
        loInfo.setDescr(richText);
        Date date = new Date();
        loInfo.setEffectiveDate(date);
        loInfo.setExpirationDate(date);
        loInfo.setLoRepositoryKey("kuali.loRepository.key.singleUse");
        //Map<String, String> attributes = new HashMap<String, String>();
        AttributeInfo rAttributeInfo= new AttributeInfo();
        rAttributeInfo.setKey("attrKey");
        rAttributeInfo.setValue("attrValue");	
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        attributes.add(rAttributeInfo);
        loInfo.setAttributes(attributes);
        loInfo.setTypeKey(CourseAssemblerConstants.COURSE_LO_TYPE);
        loInfo.setStateKey(DtoConstants.STATE_DRAFT);

        LoInfo created = client.createLo("kuali.loRepository.key.singleUse", CourseAssemblerConstants.COURSE_LO_TYPE, loInfo, contextInfo);
        assertNotNull(created);
        String loId = created.getId();
        assertNotNull(loId);
        
        created = client.getLo(loId, contextInfo);

        RichTextInfo desc = created.getDescr();
        assertNotNull(desc);
        assertEquals("<p>New ResultComponent</p>", desc.getFormatted());
        assertEquals("New ResultComponent", desc.getPlain());
        assertEquals(date.toString(), created.getEffectiveDate().toString());
        assertEquals(date.toString(), created.getExpirationDate().toString());
        assertNotNull(created.getAttributes());
        assertEquals("attrValue", created.getAttributes().get(0).getValue());
        assertEquals("attrKey", created.getAttributes().get(0).getKey());
        assertEquals(CourseAssemblerConstants.COURSE_LO_TYPE, created.getTypeKey());
        assertEquals(DtoConstants.STATE_DRAFT, created.getStateKey());

        loInfo = client.getLo(loId, contextInfo);
        loInfo.setName("Lo in the mid 30s");

        LoInfo updated = client.updateLo(loId, loInfo, contextInfo);
        assertNotNull(updated);
        assertEquals(loId, updated.getId());
        desc = updated.getDescr();
        assertNotNull(desc);
        assertEquals("<p>New ResultComponent</p>", desc.getFormatted());
        assertEquals("New ResultComponent", desc.getPlain());
        assertEquals(date.toString(), updated.getEffectiveDate().toString());
        assertEquals(date.toString(), updated.getExpirationDate().toString());
        assertNotNull(updated.getAttributes());
        assertEquals("attrValue", updated.getAttributes().get(0).getValue());
        assertEquals("attrKey", updated.getAttributes().get(0).getKey());
        assertEquals(CourseAssemblerConstants.COURSE_LO_TYPE, updated.getTypeKey());
        assertEquals(DtoConstants.STATE_DRAFT, updated.getStateKey());

        try {
            client.updateLo(loId, loInfo, contextInfo);
            fail("VersionMismatchException expected");
        } catch (VersionMismatchException e) {
            assertNotNull(e.getMessage());
            assertEquals("LO to be updated is not the current version", e.getMessage());
        }

        // Detecting expected errors
        loInfo = new LoInfo();
        try {
            client.createLo(null, CourseAssemblerConstants.COURSE_LO_TYPE, loInfo, contextInfo);
            fail("MissingParameterException expected for loRepositoryId");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("repositoryId can not be null", e.getMessage());
        }
        try {
            client.createLo("kuali.loRepository.key.singleUse", null, loInfo, contextInfo);
            fail("MissingParameterException expected for loTypeId");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loType can not be null", e.getMessage());
        }
        try {
            client.createLo("kuali.loRepository.key.singleUse", CourseAssemblerConstants.COURSE_LO_TYPE, null, contextInfo);
            fail("MissingParameterException expected for loInfo");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loInfo can not be null", e.getMessage());
        }
        
        try {
            client.getLo(null, contextInfo);
            fail("MissingParameterException expected for loId");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loId can not be null", e.getMessage());
        }
        
        try {
            client.updateLo(null, loInfo, contextInfo);
            fail("MissingParameterException expected for loId");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loId can not be null", e.getMessage());
        }
        try {
            client.updateLo(loId, null, contextInfo);
            fail("MissingParameterException expected for loInfo");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loInfo can not be null", e.getMessage());
        }
        
        StatusInfo statusInfo = client.deleteLo(loId, contextInfo);
        assertTrue(statusInfo.getIsSuccess());
        
        // now make sure we can't orphan "included" LO's
    	LoLoRelationInfo llrInfo = new LoLoRelationInfo();
    	llrInfo.setLoId ("7bcd7c0e-3e6b-4527-ac55-254c58cecc22");
    	llrInfo.setRelatedLoId ("91a91860-d796-4a17-976b-a6165b1a0b05");
    	llrInfo.setTypeKey ("kuali.lo.relation.type.includes");
		llrInfo = client.createLoLoRelation(llrInfo.getTypeKey(), llrInfo, contextInfo);
    	assertNotNull(llrInfo);
    	client.getLoLoRelation(llrInfo.getId(), contextInfo);
    	try {
    		client.deleteLo("7bcd7c0e-3e6b-4527-ac55-254c58cecc22", contextInfo);
    		fail("Deleted an LO which orphaned included LO(s)");
    	} catch (DependentObjectsExistException doee) {
            assertNotNull(doee.getMessage());
            assertEquals("Lo(7bcd7c0e-3e6b-4527-ac55-254c58cecc22) cannot be deleted without orphaning child Lo(s).", doee.getMessage());
        }
    }
    
    @Test
    public void test01GetLoByIdList() throws DoesNotExistException, InvalidParameterException, OperationFailedException, MissingParameterException, PermissionDeniedException {
    	List<LoInfo> loInfos = client.getLosByIds(Arrays.asList("81abea67-3bcc-4088-8348-e265f3670145",
                "dd0658d2-fdc9-48fa-9578-67a2ce53bf8a",
                "91a91860-d796-4a17-976b-a6165b1a0b05"), contextInfo);
    	assertEquals(3, loInfos.size());
    }
    
	/*
	 * Creating an LoCategory with the same name, type & state
	 */
	@Test
	public void test02DisallowLoCategoryDuplication() throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException, ReadOnlyException {
		String catName = "DontDupThisCategory";
		String catState = "active";
		String catType = "loCategoryType.accreditation";
		String catRepo = "kuali.loRepository.key.singleUse";
		
		LoCategoryInfo newCatInfo = new LoCategoryInfo();
		newCatInfo.setName(catName);
		newCatInfo.setTypeKey(catType);
		newCatInfo.setStateKey(catState);
		newCatInfo.setLoRepositoryKey(catRepo);
		
		newCatInfo = client.createLoCategory(/* TODO KSCM catRepo, catType, */catType,newCatInfo, contextInfo);
		
		LoCategoryInfo dupCatInfo = new LoCategoryInfo();
		dupCatInfo.setName(catName);
		dupCatInfo.setTypeKey(catType);
		dupCatInfo.setStateKey(catState);
		dupCatInfo.setLoRepositoryKey(catRepo);


		try {
			client.createLoCategory(/*TODO KSCM catRepo, catType,*/catType, dupCatInfo,contextInfo);
            fail("DataValidationErrorException expected when creating LoCategory with the same name, type and state");
		} catch (DataValidationErrorException dvee) {
			assertNotNull(dvee.getMessage());
            assertEquals("Validation error!", dvee.getMessage());
            assertEquals(1, dvee.getValidationResults().size());
            ValidationResultInfo resultInfo = dvee.getValidationResults().get(0);
            assertEquals("LO Category Name", resultInfo.getElement());
            assertEquals("LO Category already exists", resultInfo.getMessage());
        }
		// delete the one created so as to not mess up other tests
		client.deleteLoCategory(newCatInfo.getId(), contextInfo);
	}	

    @Test
    public void test03DisallowLoWEmptyDesc() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException, ReadOnlyException {
        LoInfo loInfo = new LoInfo();
        loInfo.setName("Lo with Empty Desc");
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p> </p>");
        richText.setPlain(" ");
        loInfo.setDescr(richText);
        Date date = new Date();
        loInfo.setEffectiveDate(date);
        loInfo.setExpirationDate(date);
        loInfo.setLoRepositoryKey("kuali.loRepository.key.singleUse");
        AttributeInfo rAttributeInfo= new AttributeInfo();
        rAttributeInfo.setKey("attrKey");
        rAttributeInfo.setValue("attrValue");	
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        attributes.add(rAttributeInfo);
        loInfo.setAttributes(attributes);
        loInfo.setTypeKey(CourseAssemblerConstants.COURSE_LO_TYPE);
        loInfo.setStateKey(DtoConstants.STATE_DRAFT);

        try {
        	client.createLo("kuali.loRepository.key.singleUse", CourseAssemblerConstants.COURSE_LO_TYPE, loInfo, contextInfo);
            fail("DataValidationErrorException expected when creating Lo with empty description");
        } catch (DataValidationErrorException dvee) {
            assertNotNull(dvee.getMessage());
            assertEquals("Validation error!", dvee.getMessage());
            assertEquals(1, dvee.getValidationResults().size());
            ValidationResultInfo resultInfo = dvee.getValidationResults().get(0);
            assertEquals("descr/plain", resultInfo.getElement());
            assertEquals("validation.required", resultInfo.getMessage());
        }
      }
    
    @Test
    public void test04DisallowLoCategoryWEmptyName() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException, ReadOnlyException {
//    	String catName = "DontDupThisCategorytest";
		String catState = "active";
		String catType = "loCategoryType.accreditation";
		String catRepo = "kuali.loRepository.key.singleUse";
		
		LoCategoryInfo newCatInfo = new LoCategoryInfo();
//		newCatInfo.setName(catName);
		newCatInfo.setTypeKey(catType);
		newCatInfo.setStateKey(catState);
		newCatInfo.setLoRepositoryKey(catRepo);
		
		//Testing KSLAB-692 *** this was not the intention of this jira
  // it was that you needed to have at least one category with the LO
	      RichTextInfo richText = new RichTextInfo();
	      richText.setFormatted("<p> </p>");
	      richText.setPlain("  ");
	      newCatInfo.setDescr(richText);
		
		try {
            client.createLoCategory(/*catRepo, catType,*/catType, newCatInfo, contextInfo);
            fail("DataValidationErrorException expected when creating Lo with empty name");
        } catch (DataValidationErrorException dvee) {
            assertNotNull(dvee.getMessage());
            assertEquals("Validation error!", dvee.getMessage());
            assertEquals(1, dvee.getValidationResults().size());
            ValidationResultInfo resultInfo = dvee.getValidationResults().get(0);
            assertEquals("/name", resultInfo.getElement());
            assertEquals("validation.required", resultInfo.getMessage());
        }
    }
       
	/*
	 * Creating an LoCategory with the same name (case insensitive), type & state
	 */
	
	@Test
	public void test05DisallowLoCategoryDuplicationCaseInsensitive() throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException, ReadOnlyException {
		String catName = "DontDupThisCategory";
		String catState = "active";
		String catType = "loCategoryType.accreditation";
		String catRepo = "kuali.loRepository.key.singleUse";
		
		LoCategoryInfo newCatInfo = new LoCategoryInfo();
		newCatInfo.setName(catName);
		newCatInfo.setTypeKey(catType);
		newCatInfo.setStateKey(catState);
		newCatInfo.setLoRepositoryKey(catRepo);
		
        newCatInfo = client.createLoCategory(/*TODO KSCM catRepo, catType,*/catType, newCatInfo, contextInfo);
        newCatInfo = client.getLoCategory(newCatInfo.getId(), contextInfo);
        newCatInfo.getName();
        catRepo = newCatInfo.getLoRepositoryKey();

		String dupCatName = "dontDupThisCategory";
		LoCategoryInfo dupCatInfo = new LoCategoryInfo();
		dupCatInfo.setName(dupCatName);
		dupCatInfo.setTypeKey(catType);
		dupCatInfo.setStateKey(catState);
		dupCatInfo.setLoRepositoryKey(catRepo);
		
		
		try {
			client.createLoCategory(/*TODO KSCM catRepo, catType,*/catType, dupCatInfo, contextInfo);
            fail("DataValidationErrorException expected when creating LoCategory with the same name, type and state");
		} catch (DataValidationErrorException dvee) {
            assertNotNull(dvee.getMessage());
            assertEquals("Validation error!", dvee.getMessage());
            assertEquals(1, dvee.getValidationResults().size());
            ValidationResultInfo resultInfo = dvee.getValidationResults().get(0);
            assertEquals("LO Category Name", resultInfo.getElement());
            assertEquals("LO Category already exists", resultInfo.getMessage());
        }
		// delete the one created so as to not mess up other tests
		client.deleteLoCategory(newCatInfo.getId(), contextInfo);
	}	

	/*
	 * Updating a LoCategory with checking LoCategory duplication(based on name (case insensitive), type, state & repository
	 */
	
	@Test
	public void test06UpdateLoCategoryDuplicationCaseInsensitive() throws Exception {
		String catState = "active";
		String catType = "loCategoryType.accreditation";
		String catRepo = "kuali.loRepository.key.singleUse";
		String catId1;
		String catId2;
		
		LoCategoryInfo catInfo1 = new LoCategoryInfo();
		catInfo1.setName( "DontDupThisCategory");
		catInfo1.setTypeKey(catType);
		catInfo1.setStateKey(catState);
		catInfo1.setLoRepositoryKey(catRepo);
		
		LoCategoryInfo catInfo2 = new LoCategoryInfo();
		catInfo2.setName("DontDupThisCategory2");
		catInfo2.setTypeKey(catType);
		catInfo2.setStateKey(catState);
		catInfo2.setLoRepositoryKey(catRepo);
		
        catInfo1 = client.createLoCategory(catType, catInfo1, contextInfo);
        catId1 = catInfo1.getId();

        catInfo2 = client.createLoCategory(catType, catInfo2, contextInfo);
        catId2 = catInfo2.getId();

		
		try {
			catInfo2.setName( "dontDupThisCategory");
			client.updateLoCategory(catId2, catInfo2, contextInfo);
            fail("DataValidationErrorException expected when updating LoCategory with the same name, type and state");
		} catch (DataValidationErrorException dvee) {
            assertNotNull(dvee.getMessage());
            assertEquals("Validation error!", dvee.getMessage());
            assertEquals(1, dvee.getValidationResults().size());
            ValidationResultInfo resultInfo = dvee.getValidationResults().get(0);
            assertEquals("LO Category Name", resultInfo.getElement());
            assertEquals("LO Category already exists", resultInfo.getMessage());
        }
		
		// delete the two created so as to not mess up other tests
		client.deleteLoCategory(catId1, contextInfo);
		client.deleteLoCategory(catId2, contextInfo);
	}	
	
    @Test
    public void test07GetRelatedLosByLoId() throws Exception {
    	List<LoInfo> relatedLos;
        relatedLos = client.getRelatedLosByLoId("81abea67-3bcc-4088-8348-e265f3670145", "kuali.lo.relation.type.includes", contextInfo);
    	assertNotNull(relatedLos);
    	assertEquals(2, relatedLos.size());
		assertTrue(relatedLos.get(0).getId().equals("e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf") || relatedLos.get(1).getId().equals("e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf"));

        // Detecting expected errors
        try {
    		client.getRelatedLosByLoId(null, "kuali.lo.relation.type.includes", contextInfo);
            fail("MissingParameterException expected for loId");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loId can not be null", e.getMessage());
        }
        try {
    		client.getRelatedLosByLoId("81abea67-3bcc-4088-8348-e265f3670145", null, contextInfo);
            fail("MissingParameterException expected for loLoRelationTypeKey");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loLoRelationTypeKey can not be null", e.getMessage());
        }
    }
    
    @Test
    public void test08GetLoRepositories() throws Exception {
    	List<LoRepositoryInfo> repos;
        repos = client.getLoRepositories(contextInfo);
    	assertNotNull(repos);
    	assertEquals(3, repos.size());
    	
		boolean found = false;
		String  repoId = "kuali.loRepository.key.state";
		for (LoRepositoryInfo loRInfo : repos) {
			if (loRInfo.getKey().equals(repoId)) {
				found = true;
			}
		}
		if (!found) {
			fail("Unable to find expected LoRepository with ID == " + repoId);
		}
    }
    
    @Test
    public void test09GetLoRepository() throws Exception {
    	LoRepositoryInfo repo;
        repo = client.getLoRepository("kuali.loRepository.key.state", contextInfo);
    	assertNotNull(repo);
    	assertEquals("Learning objectives mandated by the state", repo.getDescr().getPlain());
        // Detecting expected errors
        try {
    		client.getLoRepository(null, contextInfo);
            fail("MissingParameterException expected for loRepositoryKey");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loRepositoryKey can not be null", e.getMessage());
        }
    }
    	
    @Test
    public void test10GetLoLoRelation() throws Exception  {
    	LoLoRelationInfo llrInfo = null;
        llrInfo = client.getLoLoRelation("61ff5b2c-5d2f-464b-b6d8-082fbf671fcb", contextInfo);
    	assertNotNull(llrInfo);
    	assertEquals("81abea67-3bcc-4088-8348-e265f3670145", llrInfo.getLoId());
    	assertEquals("dd0658d2-fdc9-48fa-9578-67a2ce53bf8a", llrInfo.getRelatedLoId());
    	assertEquals("kuali.lo.relation.type.includes", llrInfo.getTypeKey());
    	assertEquals(DtoConstants.STATE_DRAFT, llrInfo.getStateKey());
        // Detecting expected errors
        try {
    		client.getLoLoRelation(null, contextInfo);
            fail("MissingParameterException expected for loLoRelationId");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loLoRelationId can not be null", e.getMessage());
        }
    }

	@Test
	public void test11GetLoCategories() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<LoCategoryInfo> categories = client.getLoCategoriesByLoRepository("kuali.loRepository.key.singleUse", contextInfo);
		assertEquals(5, categories.size());
		categories = client.getLoCategoriesByLoRepository("kuali.loRepository.key.state", contextInfo);
		assertTrue(null == categories || categories.size() == 0);
	}
	
	@Test
	public void test12GetLoCategoriesForLo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<LoCategoryInfo> categories = client.getLoCategoriesByLo("dd0658d2-fdc9-48fa-9578-67a2ce53bf8a", contextInfo);
		assertNotNull(categories);
		assertEquals(2, categories.size());
		categories = client.getLoCategoriesByLo("e0619a90-66d6-4af4-b357-e73ae44f7e88", contextInfo);
		assertEquals(1, categories.size());
		assertEquals("Test Category 3", categories.get(0).getName());
		categories = client.getLoCategoriesByLo("e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf", contextInfo);
		assertTrue(null == categories || categories.size() == 0);
	}
    
	@Test
	public void test13AddRemoveLoCategoryToFromLo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, PermissionDeniedException, UnsupportedActionException {
		List<LoCategoryInfo> categories = client.getLoCategoriesByLo("dd0658d2-fdc9-48fa-9578-67a2ce53bf8a", contextInfo);
		assertEquals(2, categories.size());
		assertTrue(containsLoCatInfo(categories, Arrays.asList("550e8400-e29b-41d4-a716-446655440000", "7114d2a4-f66d-4d3a-9d41-a7aa4299c797")));
		client.addLoCategoryToLo("f2f02922-4e77-4144-aa07-8c2c956370dc", "dd0658d2-fdc9-48fa-9578-67a2ce53bf8a", contextInfo);
		categories = client.getLoCategoriesByLo("dd0658d2-fdc9-48fa-9578-67a2ce53bf8a", contextInfo);
		assertEquals(3, categories.size());
		assertTrue(containsLoCatInfo(categories, Arrays.asList("550e8400-e29b-41d4-a716-446655440000", "7114d2a4-f66d-4d3a-9d41-a7aa4299c797", "f2f02922-4e77-4144-aa07-8c2c956370dc")));
		client.removeLoCategoryFromLo("f2f02922-4e77-4144-aa07-8c2c956370dc", "dd0658d2-fdc9-48fa-9578-67a2ce53bf8a", contextInfo);
		categories = client.getLoCategoriesByLo("dd0658d2-fdc9-48fa-9578-67a2ce53bf8a", contextInfo);
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
    public void test14CreateLoLoRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, CircularReferenceException, DataValidationErrorException, PermissionDeniedException, CircularRelationshipException, ReadOnlyException {
    	LoLoRelationInfo llrInfo = new LoLoRelationInfo();
    	llrInfo.setLoId ("7bcd7c0e-3e6b-4527-ac55-254c58cecc22");
        llrInfo.setRelatedLoId ("91a91860-d796-4a17-976b-a6165b1a0b05");
        llrInfo.setTypeKey ("kuali.lo.relation.type.includes");
        llrInfo = client.createLoLoRelation(llrInfo.getTypeKey (), llrInfo, contextInfo);
    	assertNotNull(llrInfo);
    	llrInfo = client.getLoLoRelation(llrInfo.getId(), contextInfo);
    	assertEquals("7bcd7c0e-3e6b-4527-ac55-254c58cecc22", llrInfo.getLoId());
    	assertEquals("91a91860-d796-4a17-976b-a6165b1a0b05", llrInfo.getRelatedLoId());
    	assertEquals("kuali.lo.relation.type.includes", llrInfo.getTypeKey());
    	assertEquals(DtoConstants.STATE_DRAFT, llrInfo.getStateKey());
        // Detecting expected errors
        try {
    		client.createLoLoRelation("bar", null, contextInfo);
            fail("MissingParameterException expected for loLoRelationInfo");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loLoRelationInfo can not be null", e.getMessage());
        }
        try {
    		client.createLoLoRelation(null, llrInfo, contextInfo);
            fail("MissingParameterException expected for loLoRelationTypeKey");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loLoRelationTypeKey can not be null", e.getMessage());
        }
        try {
    		client.createLoLoRelation(null, llrInfo, contextInfo);
            fail("MissingParameterException expected for loLoRelationTypeKey");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loLoRelationTypeKey can not be null", e.getMessage());
        }
        try {
    		client.createLoLoRelation("baz", null,  contextInfo);
            fail("MissingParameterException expected for loLoRelationInfo");
        } catch (MissingParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("loLoRelationInfo can not be null", e.getMessage());
        }
    }
    
    /*
    @Test
    public void test15IncludedLo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, CircularReferenceException, PermissionDeniedException, UnsupportedActionException, DependentObjectsExistException {
        List<String> ancestors = atpService.getAncestors("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF");
        assertTrue(!ancestors.contains("ABD8AE21-34E9-4858-A714-B04134F55D68"));
        
        StatusInfo result = atpService.addChildLoToLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "ABD8AE21-34E9-4858-A714-B04134F55D68");
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        ancestors = atpService.getAncestors("7BCD7C0E-3E6B-4527-AC55-254C58CECC22");
        assertTrue(ancestors.contains("ABD8AE21-34E9-4858-A714-B04134F55D68")); 
        
        try {
	        result = atpService.addChildLoToLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "ABD8AE21-34E9-4858-A714-B04134F55D68");
            fail("relationship should already exists and thus throw AlreadyExistsException");
        } catch (AlreadyExistsException e) {
        }
        
        List<String> descendants = atpService.getAllDescendants("ABD8AE21-34E9-4858-A714-B04134F55D68");
        assertTrue(descendants.contains("7BCD7C0E-3E6B-4527-AC55-254C58CECC22"));
        
        assertTrue(atpService.isDescendant("ABD8AE21-34E9-4858-A714-B04134F55D68", "7BCD7C0E-3E6B-4527-AC55-254C58CECC22"));
        
        List<LoInfo> children = atpService.getLoChildren("ABD8AE21-34E9-4858-A714-B04134F55D68");
        boolean foundLo = false;
        for (LoInfo loInfo : children) {
            if(loInfo.getId().equals("7BCD7C0E-3E6B-4527-AC55-254C58CECC22")) {
                foundLo = true;
                break;
            }
        }
        assertTrue(foundLo);
        
        List<LoInfo> parents = atpService.getLoParents("7BCD7C0E-3E6B-4527-AC55-254C58CECC22");
        foundLo = false;
        for (LoInfo loInfo : parents) {
            if(loInfo.getId().equals("ABD8AE21-34E9-4858-A714-B04134F55D68")) {
                foundLo = true;
                break;
            }
        }
        assertTrue(foundLo);
        
        try { // this would result in "7B..." being orphaned
	        result = atpService.removeChildLoFromLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "ABD8AE21-34E9-4858-A714-B04134F55D68");
	        fail("LearningObjectiveService.removeChildLoFromLo should have thrown DependentObjectsExistException");
        } catch (DependentObjectsExistException doee) {}
        
        // add it as a child to another parent
        result = atpService.addChildLoToLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        // and now remove it from "AB..."'s children
        result = atpService.removeChildLoFromLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", "ABD8AE21-34E9-4858-A714-B04134F55D68");
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        assertTrue(!atpService.isDescendant("ABD8AE21-34E9-4858-A714-B04134F55D68", "7BCD7C0E-3E6B-4527-AC55-254C58CECC22"));
    }
    */
    
    @Test
    public void test16LoCategory() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, UnsupportedActionException, ReadOnlyException {
        
        List<LoCategoryInfo> categories = client.getLoCategoriesByLoRepository("foo.bar.baz", contextInfo);
        assertTrue(null == categories || categories.isEmpty());
        
        LoCategoryInfo category = new LoCategoryInfo();
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p>New Category</p>");
        richText.setPlain("New Category");
        category.setDescr(richText);
        Date date = new Date();
        category.setEffectiveDate(date);
        category.setExpirationDate(date);
        category.setName("BOB, THE AMAAAAAAZING WONDER LLAMA!!");
        AttributeInfo rAttributeInfo= new AttributeInfo();
        rAttributeInfo.setKey("attrKey");
        rAttributeInfo.setValue("attrValue");	
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        attributes.add(rAttributeInfo);
        category.setAttributes(attributes);
        
        
        String categoryId = "550e8400-e29b-41d4-a716-446655440000";
        
        category = client.getLoCategory(categoryId, contextInfo);
        assertEquals("Perception", category.getName());
        assertEquals("loCategoryType.skillarea", category.getTypeKey());
        category.setName("LENNY, THE LECHEROUS MILK THIEF");
        category.setTypeKey("loCategoryType.accreditation");
        
        List<LoInfo> twoLos = client.getLosByLoCategory(categoryId, contextInfo);
        assertNotNull(twoLos);
        assertEquals(2, twoLos.size());
        assertTrue(twoLos.get(0).getId().equals("81abea67-3bcc-4088-8348-e265f3670145") ||
        			twoLos.get(0).getId().equals("dd0658d2-fdc9-48fa-9578-67a2ce53bf8a"));
        assertTrue(twoLos.get(1).getId().equals("81abea67-3bcc-4088-8348-e265f3670145") ||
        			twoLos.get(1).getId().equals("dd0658d2-fdc9-48fa-9578-67a2ce53bf8a"));
        
        LoCategoryInfo updated = client.updateLoCategory(categoryId, category, contextInfo);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        
        // make sure it all stuck
        updated = client.getLoCategory(updated.getId(), contextInfo);
        assertEquals("LENNY, THE LECHEROUS MILK THIEF", updated.getName());
        assertEquals("loCategoryType.accreditation", updated.getTypeKey());
        
        try {
            client.updateLoCategory(categoryId, category, contextInfo);
            fail("DataValidationErrorException expected: LO Category already exists");
        } catch (DataValidationErrorException dvee) {
            assertNotNull(dvee.getMessage());
            assertEquals("Validation error!", dvee.getMessage());
            assertEquals(1, dvee.getValidationResults().size());
            ValidationResultInfo resultInfo = dvee.getValidationResults().get(0);
            assertEquals("LO Category Name", resultInfo.getElement());
            assertEquals("LO Category already exists", resultInfo.getMessage());
        }
           
        // switch to new LoCategory id; new one was created when we changed its type
        String newCategoryId = updated.getId();
        
        // make sure it's new
        assertFalse(categoryId.equals(newCategoryId));
       
        // make sure the LoCategories were cloned
        List<LoInfo> los = client.getLosByLoCategory(newCategoryId, contextInfo);
        assertEquals(2, los.size());
        assertTrue(los.get(0).getId().equals("81abea67-3bcc-4088-8348-e265f3670145") ||
        			los.get(0).getId().equals("dd0658d2-fdc9-48fa-9578-67a2ce53bf8a"));
        assertTrue(los.get(1).getId().equals("81abea67-3bcc-4088-8348-e265f3670145") ||
        			los.get(1).getId().equals("dd0658d2-fdc9-48fa-9578-67a2ce53bf8a"));
        
        los = client.getLosByLoCategory(categoryId, contextInfo);
        assertTrue(null == los || los.isEmpty());
        
        // add one to an LO that didn't have one
        categoryId = "054caa88-c21d-4496-8287-36a311a11d68";
        StatusInfo statusInfo = client.addLoCategoryToLo(categoryId, "91a91860-d796-4a17-976b-a6165b1a0b05", contextInfo);
        assertTrue(statusInfo.getIsSuccess());
        
        los = client.getLosByLoCategory(categoryId, contextInfo);
        assertEquals(1, los.size());
        assertEquals("91a91860-d796-4a17-976b-a6165b1a0b05", los.get(0).getId());
        
        categories = client.getLoCategoriesByLo("91a91860-d796-4a17-976b-a6165b1a0b05", contextInfo);
        assertEquals(1, categories.size());
        
        try {
            client.deleteLoCategory(categoryId, contextInfo);
            fail("DependentObjectsExistException expected"); 
        } catch(DependentObjectsExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("LoCategory(" + categoryId + ") still has 1 Learning Objective(s) associated with it.", e.getMessage());
        }
        
        statusInfo = client.removeLoCategoryFromLo(categoryId, "91a91860-d796-4a17-976b-a6165b1a0b05", contextInfo);
        assertTrue(statusInfo.getIsSuccess());
        
        los = client.getLosByLoCategory(categoryId, contextInfo);
        assertTrue(null == los || los.size() == 0);
        statusInfo = client.deleteLoCategory(categoryId, contextInfo);
        assertTrue(statusInfo.getIsSuccess());
    }
    
	@Test
	public void test17SearchForResults() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		String testLoName = "Navigate Wiki";
		List<SearchParamInfo> queryParamValues = new ArrayList<SearchParamInfo>();
		SearchParamInfo qpv1 = new SearchParamInfo();
		qpv1.setKey("lo.queryParam.loName");
		qpv1.getValues().add(testLoName);
		queryParamValues.add(qpv1);
		SearchRequestInfo searchRequest = new SearchRequestInfo();
		searchRequest.setParams(queryParamValues);
		searchRequest.setSearchKey("lo.search.loByName");
		SearchResultInfo result = client.search(searchRequest, ContextInfoTestUtility.getEnglishContextInfo());
		assertEquals(1,result.getRows().size());
		
        List<SearchResultCellInfo> resultCells = result.getRows().get(0).getCells();
        assertEquals(2, resultCells.size());
        SearchResultCellInfo cell = resultCells.get(0);
        assertEquals("lo.resultColumn.loId", cell.getKey());
        assertEquals("e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf", cell.getValue());
        cell = resultCells.get(1);
        assertEquals("lo.resultColumn.loName", cell.getKey());
        assertEquals(testLoName, cell.getValue());
	}
}
