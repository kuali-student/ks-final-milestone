package org.kuali.student.lum.lo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
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

    @Ignore
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

        LoInfo created = client.createLo(null, "lotype.type", loInfo); // TODO need actual type here
        assertNotNull(created);
        String id = created.getId();
        assertNotNull(id);

        RichTextInfo desc = created.getDesc();
        assertNotNull(desc);
        assertEquals("<p>New ResultComponent</p>", desc.getFormatted());
        assertEquals("New ResultComponent", desc.getPlain());
        assertEquals(date, created.getEffectiveDate());
        assertEquals(date, created.getExpirationDate());
        Map<String, String> newAttributes = created.getAttributes();
        assertNotNull(newAttributes);
        assertEquals("attrValue", newAttributes.get("attrKey"));
        assertEquals("lotype.type", created.getType()); // TODO need actual type here
        assertEquals("Active", created.getState());

        loInfo = client.getLo(id);
        loInfo.setName("Lo in the mid-30s");

        LoInfo updated = client.updateLo(id, loInfo);
        assertNotNull(updated);
        assertEquals(id, updated.getId());
        desc = updated.getDesc();
        assertNotNull(desc);
        assertEquals("<p>New ResultComponent</p>", desc.getFormatted());
        assertEquals("New ResultComponent", desc.getPlain());
        assertEquals(date, updated.getEffectiveDate());
        assertEquals(date, updated.getExpirationDate());
        newAttributes = updated.getAttributes();
        assertNotNull(newAttributes);
        assertEquals("attrValue", newAttributes.get("attrKey"));
        assertEquals("lotype.type", updated.getType()); // TODO need actual type here
        assertEquals("Active", updated.getState());

        try {
            client.updateLo(id, loInfo);
            fail("VersionMismatchException expected");
        } catch (VersionMismatchException e) {}

        StatusInfo statusInfo = client.deleteLo(id);
        assertTrue(statusInfo.getSuccess());

        // Detecting expected errors
        loInfo = new LoInfo();
        try {
            client.createLo(null, "anytype", loInfo);
            throw new Exception();
        } catch (MissingParameterException e) {} catch (Exception e) {
            fail("MissingParameterException expected for parentLoId");
        }
        try {
            client.createLo("parentloid", "", loInfo);
            throw new Exception();
        } catch (MissingParameterException e) {} catch (Exception e) {
            fail("MissingParameterException expected for loTypeId");
        }
        try {
            client.createLo("parentloid", "anytype", null);
            throw new Exception();
        } catch (MissingParameterException e) {} catch (Exception e) {
            fail("MissingParameterException expected for loInfo");
        }

    }
    
    @Ignore
    @Test
    public void testLoChild() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, CircularReferenceException, PermissionDeniedException, UnsupportedActionException, DependentObjectsExistException {
        List<String> ancestors = client.getAncestors("LOID-1"); //TODO need actual ids here and everywhere else in this method
        assertTrue(!ancestors.contains("LOID-2")); //TODO need a lo id that's NOT a parent of LOID-1
        
        StatusInfo result = client.addChildLoToLo("LOID-1", "LOID-2"); //TODO replace ids
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        ancestors = client.getAncestors("LOID-1"); //TODO replace ids
        assertTrue(ancestors.contains("LOID-2")); //TODO replace ids
        
        try {
            result = client.addChildLoToLo("LOID-1", "LOID-2"); //TODO replace ids
            fail("relationship should already exists and thus throw AlreadyExistsException");
        } catch (AlreadyExistsException e) {
        }
        
        List<String> descendants = client.getAllDescendants("LOID-2"); //TODO replace ids
        assertTrue(descendants.contains("LOID-1")); //TODO replace ids
        
        assertTrue(client.isDescendant("LOID-2", "LOID-1")); //TODO replace ids
        
        List<LoInfo> children = client.getLoChildren("LOID-2"); //TODO replace ids
        boolean foundLo = false;
        for (LoInfo loInfo : children) {
            if(loInfo.getId().equals("LOID-1")) { //TODO replace ids
                foundLo = true;
                break;
            }
        }
        assertTrue(foundLo);
        
        List<LoInfo> parents = client.getLoParents("LOID-1"); //TODO replace ids
        foundLo = false;
        for (LoInfo loInfo : parents) {
            if(loInfo.getId().equals("LOID-2")) { //TODO replace ids
                foundLo = true;
                break;
            }
        }
        assertTrue(foundLo);
        
        result = client.removeChildLoFromLo("LOID-1", "LOID-2"); //TODO replace ids
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        assertTrue(!client.isDescendant("LOID-2", "LOID-1")); //TODO replace ids
    }
    
    @Ignore
    @Test
    public void testLoEquivalency() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, PermissionDeniedException {
        List<LoInfo> equivalents = client.getEquivalentLos("LOID-1"); //TODO replace ids
        assertTrue(equivalents.isEmpty()); //this will probably fail. ha ha.
        
        StatusInfo result = client.addEquivalentLoToLo("LOID-1", "LOID-3"); //TODO replace ids
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        equivalents = client.getEquivalentLos("LOID-1"); //TODO replace ids
        assertEquals(1, equivalents.size());
        assertEquals("LOID-3", equivalents.get(0).getId()); //TODO replace ids
        
        assertTrue(client.isEquivalent("LOID-1", "LOID-3")); //TODO replace ids
        assertTrue(!client.isEquivalent("LOID-3", "LOID-1")); //TODO replace ids
        
        equivalents = client.getLoEquivalents("LOID-3"); //TODO replace ids
        assertEquals(1, equivalents.size());
        assertEquals("LOID-1", equivalents.get(0).getId()); //TODO replace ids
        
        try {
            result = client.addEquivalentLoToLo("LOID-1", "LOID-3"); //TODO replace ids
            fail("relationship should already exists and thus throw AlreadyExistsException");
        } catch (AlreadyExistsException e) {
        }
        
        result = client.removeEquivalentLoFromLo("LOID-1", "LOID-3"); //TODO replace ids
        assertNotNull(result);
        assertTrue(result.getSuccess());
        
        assertTrue(!client.isEquivalent("LOID-1", "LOID-3")); //TODO replace ids
        assertTrue(!client.isEquivalent("LOID-3", "LOID-1")); //TODO replace ids
    }
    
    @Ignore
    @Test
    public void testLoCategory() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DataValidationErrorException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, AlreadyExistsException, UnsupportedActionException {
        StatusInfo statusInfo = null;
        List<LoHierarchyInfo> hierarchies = client.getLoHierarchies();
        assertNotNull(hierarchies);
        assertTrue(!hierarchies.isEmpty());
        
        List<LoCategoryInfo> categories = client.getLoCategories(hierarchies.get(0).getId());
        int size = categories.size();
        
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
        
        LoCategoryInfo created = client.createLoCategory(hierarchies.get(0).getId(), category);
        assertNotNull(created);
        String id = created.getId();
        assertNotNull(id);
        
        categories = client.getLoCategories(hierarchies.get(0).getId());
        assertEquals(size + 1, categories.size());
        
        category = client.getLoCategory(id);
        category.setName("LENNY, THE LECHEROUS MILK THIEF");
        
        LoCategoryInfo updated = client.updateLoCategory(id, category);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        
        try {
            client.updateLoCategory(id, category);
            fail("VersionMismatchException expected");
        } catch (VersionMismatchException e) {}
        
        List<LoInfo> los = client.getLosByLoCategory(id);
        assertNotNull(los);
        assertEquals(0, los.size());
        
        statusInfo = client.addLoCategoryToLo(id, "LOID-1"); //TODO replace ids
        assertTrue(statusInfo.getSuccess());
        
        los = client.getLosByLoCategory(id);
        assertEquals(1, los.size());
        assertEquals("LOID-1", los.get(0).getId()); //TODO replace ids
        
        categories = client.getLoCategoriesForLo("LOID-1"); //TODO replace ids
        boolean foundCat = false;
        for (LoCategoryInfo loCategoryInfo : categories) {
            if(loCategoryInfo.getId().equals(id)) {
                foundCat = true;
                break;
            }
        }
        assertTrue(foundCat);
        
        try {
            statusInfo = client.deleteLoCategory(id);
            fail("DependentObjectsExistException expected"); //but is statusInfo false? Who knows?
        } catch(DependentObjectsExistException e) {}
        
        statusInfo = client.removeLoCategoryFromLo(id, "LOID-1"); //TODO replace ids
        assertTrue(statusInfo.getSuccess());
        
        statusInfo = client.deleteLoCategory(id);
        assertTrue(statusInfo.getSuccess());
    }
    
    @Ignore
    @Test
    public void testLoType() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<LoTypeInfo> loTypes = client.getLoTypes();
        assertNotNull(loTypes);
        assertTrue(!loTypes.isEmpty()); //this gets populated elsewhere
        
        LoTypeInfo loType = client.getLoType(loTypes.get(0).getId());
        assertEquals(loTypes.get(0).getName(), loType.getName());
    }
    
    @Ignore
    @Test
    public void testLoHierarchy() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<LoHierarchyInfo> loHierarchies = client.getLoHierarchies();
        assertNotNull(loHierarchies);
        assertTrue(!loHierarchies.isEmpty());
        
        LoHierarchyInfo loHierarchy = client.getLoHierarchy(loHierarchies.get(0).getId());
        assertEquals(loHierarchies.get(0).getName(), loHierarchy.getName());
    }
}
