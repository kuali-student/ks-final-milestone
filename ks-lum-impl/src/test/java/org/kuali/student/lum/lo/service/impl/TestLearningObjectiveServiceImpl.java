/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoHierarchyInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoTypeInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;

@Daos({@Dao(value = "org.kuali.student.lum.lo.dao.impl.LoDaoImpl", testSqlFile = "classpath:ks-lo.sql")})
@PersistenceFileLocation("classpath:META-INF/lo-persistence.xml")
public class TestLearningObjectiveServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.lum.lo.service.impl.LearningObjectiveServiceImpl", port = "8181", additionalContextFile = "classpath:lo-additional-context.xml")
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
        loInfo.setState("Active");

        LoInfo created = client.createLo(null, "loType.default", loInfo); 
        assertNotNull(created);
        String loId = created.getId();
        assertNotNull(loId);

        RichTextInfo desc = created.getDesc();
        assertNotNull(desc);
        assertEquals("<p>New ResultComponent</p>", desc.getFormatted());
        assertEquals("New ResultComponent", desc.getPlain());
        assertEquals(date.toString(), created.getEffectiveDate().toString());
        assertEquals(date.toString(), created.getExpirationDate().toString());
        Map<String, String> newAttributes = created.getAttributes();
        assertNotNull(newAttributes);
        assertEquals("attrValue", newAttributes.get("attrKey"));
        assertEquals("loType.default", created.getType()); 
        assertEquals("Active", created.getState());

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
        assertEquals("loType.default", updated.getType()); 
        assertEquals("Active", updated.getState());

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
            client.createLo("parentloid", null, loInfo);
            fail("MissingParameterException expected for loTypeId");
        } catch (MissingParameterException e) {}
        try {
            client.createLo("parentloid", "anytype", null);
            fail("MissingParameterException expected for loInfo");
        } catch (MissingParameterException e) {}
        
        loInfo = client.createLo(loId, "loType.default", loInfo);
        String childLoId = loInfo.getId();
        try {
	        client.deleteLo(loId);
	        fail("deleteLo() of Lo with child should have thrown DependentObjectsExistException");
        } catch (DependentObjectsExistException doee) {}
        
        StatusInfo statusInfo = client.deleteLo(childLoId);
        assertTrue(statusInfo.getSuccess());
        statusInfo = client.deleteLo(loId);
        assertTrue(statusInfo.getSuccess());

    }
    
    @Test
    public void testLoChild() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, CircularReferenceException, PermissionDeniedException, UnsupportedActionException, DependentObjectsExistException {
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
    
    @Test
    public void testLoEquivalency() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, PermissionDeniedException {
        List<LoInfo> equivalents = client.getEquivalentLos("Bogus_LO_ID");
        assertTrue(null == equivalents || equivalents.isEmpty());
    	
        // no LO's equivalent to 81ABEA67-3BCC-4088-8348-E265F3670145 currently
        equivalents = client.getEquivalentLos("81ABEA67-3BCC-4088-8348-E265F3670145");
        assertTrue(null == equivalents || equivalents.isEmpty()); 
        
        // now add one
        StatusInfo result = client.addEquivalentLoToLo("81ABEA67-3BCC-4088-8348-E265F3670145", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        // make sure it was persisted
        equivalents = client.getEquivalentLos("81ABEA67-3BCC-4088-8348-E265F3670145");
        Collections.sort(equivalents, new Comparator<LoInfo>(){
			public int compare(LoInfo o1, LoInfo o2) {
				return o1.getId().compareTo(o1.getId());
			}
        });
        assertEquals(1, equivalents.size());
        assertEquals("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A", equivalents.get(0).getId());
        
        // test uni-directionality
        assertTrue(client.isEquivalent("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A", "81ABEA67-3BCC-4088-8348-E265F3670145"));
        assertFalse(client.isEquivalent("81ABEA67-3BCC-4088-8348-E265F3670145", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A"));
        
        equivalents = client.getLoEquivalents("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
        // there was one already from initial sql datafile load
        assertEquals(2, equivalents.size());
        Set<String> equivIds = new TreeSet<String>(Arrays.asList( equivalents.get(0).getId(), equivalents.get(1).getId() ));
        // test that equiv from initial data load is there
        assertTrue(equivIds.contains("ABD8AE21-34E9-4858-A714-B04134F55D68"));
        // plus one we just added
        assertTrue(equivIds.contains("81ABEA67-3BCC-4088-8348-E265F3670145"));
        
        try {
            result = client.addEquivalentLoToLo("81ABEA67-3BCC-4088-8348-E265F3670145", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
            fail("relationship should already exist and thus throw AlreadyExistsException");
        } catch (AlreadyExistsException e) {
        }
        
        result = client.removeEquivalentLoFromLo("81ABEA67-3BCC-4088-8348-E265F3670145", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        assertFalse(client.isEquivalent("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A", "81ABEA67-3BCC-4088-8348-E265F3670145"));
        assertFalse(client.isEquivalent("81ABEA67-3BCC-4088-8348-E265F3670145", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A"));
    }
    
    @Test
    public void testLoCategory() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, UnsupportedActionException {
        StatusInfo statusInfo = null;
        List<LoHierarchyInfo> hierarchies = client.getLoHierarchies();
        assertNotNull(hierarchies);
        assertFalse(hierarchies.isEmpty());
        assertEquals(6, hierarchies.size());
        
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
        
        LoHierarchyInfo loHierarchy = client.getLoHierarchy("loHierarchy.empty.test");
        
        LoCategoryInfo created = client.createLoCategory(loHierarchy.getId(), category);
        assertNotNull(created);
        String categoryId = created.getId();
        assertNotNull(categoryId);
        
        categories = client.getLoCategories(loHierarchy.getId());
        assertEquals(1, categories.size());
        
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
        
        statusInfo = client.addLoCategoryToLo(categoryId, "91A91860-D796-4A17-976B-A6165B1A0B05");
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
    
    @Test
    public void testLoType() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<LoTypeInfo> loTypes = client.getLoTypes();
        assertNotNull(loTypes);
        assertTrue(!loTypes.isEmpty()); //this gets populated elsewhere
        
        LoTypeInfo loType = client.getLoType(loTypes.get(0).getId());
        assertEquals(loTypes.get(0).getName(), loType.getName());
    }
    
    @Test
    public void testLoHierarchy() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<LoHierarchyInfo> loHierarchies = client.getLoHierarchies();
        assertNotNull(loHierarchies);
        assertTrue(!loHierarchies.isEmpty());
        
        LoHierarchyInfo loHierarchy = client.getLoHierarchy(loHierarchies.get(0).getId());
        assertEquals(loHierarchies.get(0).getName(), loHierarchy.getName());
    }
}
