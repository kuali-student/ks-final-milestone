/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.document.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
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
import org.kuali.student.core.document.dto.DocumentBinaryInfo;
import org.kuali.student.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.dto.DocumentTypeInfo;
import org.kuali.student.core.document.service.DocumentService;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Daos( { @Dao(value = "org.kuali.student.core.document.dao.impl.DocumentDaoImpl",testSqlFile="classpath:ks-document.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/document-persistence.xml")
public class TestDocumentServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.core.document.service.impl.DocumentServiceImpl", port = "8181")
    public DocumentService client;


    @Test
    public void testClient() {
        assertNotNull(client);
    }
    
    @Ignore
    @Test
    public void testDocTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<DocumentTypeInfo> types = client.getDocumentTypes();
        assertNotNull(types);
        assertTrue(types.size() > 0);
        
        DocumentTypeInfo type = client.getDocumentType(types.get(0).getId());
        assertEquals(types.get(0).getId(), type.getId());
        assertEquals(types.get(0).getName(), type.getName());
    }
    
    @Ignore
    @Test
    public void testDocCategories() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<DocumentCategoryInfo> categories = client.getDocumentCategories();
        assertNotNull(categories);
        assertTrue(categories.size() > 1); //this needs to be true for below test
        
        DocumentCategoryInfo category = client.getDocumentCategory(categories.get(0).getId());
        assertEquals(categories.get(0).getId(), category.getId());
        assertEquals(categories.get(0).getName(), category.getName());
    }
    
    @Ignore
    @Test
    public void testDocument() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {
        DocumentInfo doc = new DocumentInfo();
        doc.setName("TPS Report");
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setFormatted("<p>Sponsored by Initech</p>");
        richTextInfo.setPlain("Sponsored by Initech");
        doc.setDesc(richTextInfo);
        Date date = new Date();
        doc.setEffectiveDate(date);
        doc.setExpirationDate(date);
        doc.setState("Active");
        
        doc.setFileName("malware.doc");
        DocumentBinaryInfo documentBinaryInfo = new DocumentBinaryInfo();
        documentBinaryInfo.setBinary("bobloblawlawblogbobloblawlawblogbobloblawlawblogbobloblawlawblogbobloblawlawblog");
        doc.setDocumentBinaryInfo(documentBinaryInfo);
        
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("attrKey", "attrValue");
        doc.setAttributes(attributes);
        
        DocumentInfo created = client.createDocument("DOCTYPE-1", "DOCCAT-1", doc);
        assertNotNull(created);
        String id = created.getId();
        assertNotNull(id);
        
        assertEquals(date, created.getEffectiveDate());
        assertEquals(date, created.getExpirationDate());
        assertEquals("Active", created.getState());
        assertEquals("TPS Report", created.getName());
        assertEquals("Sponsored by Initech", created.getDesc().getPlain());
        assertEquals("<p>Sponsored by Initech</p>", created.getDesc().getFormatted());
        assertEquals("malware.doc", created.getFileName());
        assertEquals("bobloblawlawblogbobloblawlawblogbobloblawlawblogbobloblawlawblogbobloblawlawblog", created.getDocumentBinaryInfo().getBinary());
        Map<String, String> newAttributes = created.getAttributes();
        assertNotNull(newAttributes);
        assertEquals("attrValue", newAttributes.get("attrKey"));
        assertEquals("DOCTYPE-1", created.getType());
        
        doc = client.getDocument(id);
        assertNotNull(doc);
        
        List<DocumentCategoryInfo> categories = client.getCategoriesByDocument(id);
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("DOCCAT-1", categories.get(0).getId());
        
        List<DocumentInfo> documents = client.getDocumentsByIdList(Arrays.asList(id));
        assertNotNull(documents);
        assertEquals(1, documents.size());
        assertEquals(id, documents.get(0).getId());
        
        StatusInfo status = client.addDocumentCategoryToDocument(id, "DOCCAT-2");
        assertTrue(status.getSuccess());
        
        categories = client.getCategoriesByDocument(id);
        assertNotNull(categories);
        assertEquals(2, categories.size());
        boolean foundCat = false;
        for (DocumentCategoryInfo documentCategoryInfo : categories) {
            if("DOCCAT-2".equals(documentCategoryInfo.getId())) {
                foundCat = true;
                break;
            }
        }
        assertTrue(foundCat);
        
        status = client.removeDocumentCategoryFromDocument(id, "DOCCAT-2");
        assertTrue(status.getSuccess());
        
        try {
            status = client.removeDocumentCategoryFromDocument(id, "DOCCAT-1");
            fail("Expected some sort of exception since you can't have a doc with no categories"); // as stated at https://test.kuali.org/confluence/display/KULSTU/Document+Service+Description+and+Assumptions+v1.0-rc1
        } catch(Exception e) {} //what exception is this going to be? OperationFailed? or should status just be false?
        
        doc.setName("TPS");
        DocumentInfo updated = client.updateDocument(id, doc);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals("TPS", updated.getName());
        
        try {
            doc = client.updateDocument(id, doc);
            fail("Expected VersionMismatchException");
        } catch(VersionMismatchException e) {}
        
        status = client.deleteDocument(id);
        assertTrue(status.getSuccess());
    }

   
}
