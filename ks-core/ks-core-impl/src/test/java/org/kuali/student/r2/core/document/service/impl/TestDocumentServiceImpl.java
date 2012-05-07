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

package org.kuali.student.r2.core.document.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r2.core.document.dto.DocumentBinaryInfo;
import org.kuali.student.r2.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r1.core.document.dto.DocumentTypeInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r1.core.document.dto.RefDocRelationTypeInfo;
import org.kuali.student.r2.core.document.service.DocumentService;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Daos( { @Dao(value = "org.kuali.student.r1.core.document.dao.impl.DocumentDaoImpl",testSqlFile="classpath:ks-document.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/document-persistence.xml")
public class TestDocumentServiceImpl extends AbstractServiceTest {
	
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyyMMdd");
	
	@Client(value = "org.kuali.student.r1.core.document.service.impl.DocumentServiceImpl", additionalContextFile="classpath:document-additional-context.xml")
    public DocumentService client;


    @Test
    public void testClient() {
        assertNotNull(client);
    }
    

    @Test
    public void testDocTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO KSCM-504
//        List<DocumentTypeInfo> types = client.getDocumentTypes();
//        assertNotNull(types);
//        assertTrue(types.size() > 0);
        
        // TODO KSCM-504
//        DocumentTypeInfo type = client.getDocumentType(types.get(0).getId());
//        assertEquals(types.get(0).getId(), type.getId());
//        assertEquals(types.get(0).getName(), type.getName());
    }
    

    @Test
    public void testDocCategories() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        List<DocumentCategoryInfo> categories = client.getDocumentCategories(contextInfo);
        assertNotNull(categories);
        assertTrue(categories.size() > 1); //this needs to be true for below test
        
        DocumentCategoryInfo category = client.getDocumentCategory(categories.get(0).getKey(), contextInfo);
        assertEquals(categories.get(0).getKey(), category.getKey());
        assertEquals(categories.get(0).getName(), category.getName());
    }
    

    @Test
    public void testDocument() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        DocumentInfo doc = new DocumentInfo();
        doc.setName("TPS Report");
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setFormatted("<p>Sponsored by Initech</p>");
        richTextInfo.setPlain("Sponsored by Initech");
        doc.setDescr(richTextInfo);
        Date date = new Date();
        doc.setEffectiveDate(date);
        doc.setExpirationDate(date);
        doc.setStateKey("Active");
        
        doc.setFileName("malware.doc");
        DocumentBinaryInfo documentBinaryInfo = new DocumentBinaryInfo();
        documentBinaryInfo.setBinary("bobloblawlawblogbobloblawlawblogbobloblawlawblogbobloblawlawblogbobloblawlawblog");
        doc.setDocumentBinary(documentBinaryInfo);
        
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("attrKey", "attrValue");
        //doc.setAttributes(attributes);
        
        DocumentInfo created = client.createDocument("documentType.type1", "CAT_1", doc, contextInfo);
        assertNotNull(created);
        String id = created.getId();
        assertNotNull(id);
        
        assertEquals(date, created.getEffectiveDate());
        assertEquals(date, created.getExpirationDate());
        assertEquals("Active", created.getStateKey());
        assertEquals("TPS Report", created.getName());
        assertEquals("Sponsored by Initech", created.getDescr().getPlain());
        assertEquals("<p>Sponsored by Initech</p>", created.getDescr().getFormatted());
        assertEquals("malware.doc", created.getFileName());
        assertEquals("bobloblawlawblogbobloblawlawblogbobloblawlawblogbobloblawlawblogbobloblawlawblog", created.getDocumentBinary().getBinary());
        //Map<String, String> newAttributes = created.getAttributes();
        //assertNotNull(newAttributes);
        //assertEquals("attrValue", newAttributes.get("attrKey"));
        assertEquals("documentType.type1", created.getTypeKey());
        
        doc = client.getDocument(id, contextInfo);
        assertNotNull(doc);
        
        List<DocumentCategoryInfo> categories = client.getDocumentCategoriesByDocumentId(id, contextInfo);
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("CAT_1", categories.get(0).getKey());
        
        List<DocumentInfo> documents = client.getDocumentsByIds(Arrays.asList(id), contextInfo);
        assertNotNull(documents);
        assertEquals(1, documents.size());
        assertEquals(id, documents.get(0).getId());
        doc = client.getDocument(id, contextInfo);
        StatusInfo status = client.addDocumentCategoryToDocument(id, "CAT_2", contextInfo);
        assertTrue(status.getIsSuccess());
        doc = client.getDocument(id, contextInfo);
        categories = client.getDocumentCategoriesByDocumentId(id, contextInfo);
        assertNotNull(categories);
        assertEquals(2, categories.size());
        boolean foundCat = false;
        for (DocumentCategoryInfo documentCategoryInfo : categories) {
            if("CAT_2".equals(documentCategoryInfo.getKey())) {
                foundCat = true;
                break;
            }
        }
        assertTrue(foundCat);
        
        status = client.removeDocumentCategoryFromDocument(id, "CAT_2", contextInfo);
        assertTrue(status.getIsSuccess());
        
   
        status = client.removeDocumentCategoryFromDocument(id, "CAT_1", contextInfo);
        assertFalse(status.getIsSuccess());
        
        doc = client.getDocument(id, contextInfo);
        categories = client.getDocumentCategoriesByDocumentId(id, contextInfo);
        assertNotNull(categories);
        
        doc = client.getDocument(id, contextInfo);
        doc.setName("TPS");
        DocumentInfo updated = client.updateDocument(id, doc, contextInfo);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals("TPS", updated.getName());
        
        try {
            doc = client.updateDocument(id, doc, contextInfo);
            fail("Expected VersionMismatchException");
        } catch(VersionMismatchException e) {}
        
        status = client.deleteDocument(id, contextInfo);
        assertTrue(status.getIsSuccess());
    }


    @Test
    public void testDocumentCrud() throws DataValidationErrorException, InvalidParameterException , OperationFailedException, PermissionDeniedException, DoesNotExistException, MissingParameterException, VersionMismatchException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        DocumentInfo documentInfo = new DocumentInfo();
        DocumentBinaryInfo binaryInfo = new DocumentBinaryInfo();
        binaryInfo.setBinary("Test document");
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setFormatted("<p>created document</p>");
        richTextInfo.setPlain("created document");
        documentInfo.setDescr(richTextInfo);
        documentInfo.setDocumentBinary(binaryInfo);
        documentInfo.setEffectiveDate(new Date());
        documentInfo.setExpirationDate(new Date());
        documentInfo.setFileName("Sample");
        documentInfo.setTypeKey("documentType.type1");
        
        DocumentInfo documentInfoP = client.createDocument("documentType.type1", "CAT_1", documentInfo, contextInfo);
        assertEquals(documentInfo.getDescr().getPlain(),documentInfoP.getDescr().getPlain());
        assertEquals(documentInfo.getDocumentBinary().getBinary(),documentInfoP.getDocumentBinary().getBinary());
        assertEquals(documentInfo.getEffectiveDate(), documentInfoP.getEffectiveDate());
        assertEquals(documentInfo.getExpirationDate(), documentInfoP.getExpirationDate());
        assertEquals(documentInfo.getFileName(), documentInfoP.getFileName());
        
        

        StatusInfo statusInfo = client.addDocumentCategoryToDocument(documentInfoP.getId(), "CAT_2", contextInfo);
        assertTrue(statusInfo.getIsSuccess());
        
        
        List<DocumentCategoryInfo> categoriesInfos = client.getDocumentCategoriesByDocumentId(documentInfoP.getId(), contextInfo);
        assertEquals(categoriesInfos.size(), 2);
        
        statusInfo = client.addDocumentCategoryToDocument(documentInfoP.getId(), "CAT_3", contextInfo);
        assertTrue(statusInfo.getIsSuccess());
        
        categoriesInfos = client.getDocumentCategoriesByDocumentId(documentInfoP.getId(), contextInfo);
        assertEquals(categoriesInfos.size(), 3);
        
        statusInfo = client.removeDocumentCategoryFromDocument(documentInfoP.getId(), "CAT_2", contextInfo);
        assertTrue(statusInfo.getIsSuccess());
        categoriesInfos = client.getDocumentCategoriesByDocumentId(documentInfoP.getId(), contextInfo);
        assertEquals(categoriesInfos.size(), 2);
        
        
        
    }
    
    @Test
    public void testValidateMethods() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
//        //FIXME validation needs to be tested, this code doesn't work and I need to refactor
//    	  DocumentInfo documentInfo = new DocumentInfo();
//        RichTextInfo desc = new RichTextInfo();
//        desc.setFormatted("<p>document&gt;!%/#;&amp;@$</p>");
//        desc.setPlain("document");
//        documentInfo.setDesc(desc);
//        documentInfo.setFileName("sample.pdf");
//        documentInfo.setEffectiveDate(new Date());
//        documentInfo.setExpirationDate(new Date());
//        documentInfo.setType("kuali.org.Document");
//        documentInfo.setState("active");
//        
//        List<DictValidationResultContainer> validations = client.validateDocument("", documentInfo);
//        for (DictValidationResultContainer validationResult : validations) {
//            assertTrue(validationResult.isOk());
//        }
    }
    
    
    @Test
    public void testDocRelationsCrud() throws Exception{
        //Create a document to get relations from
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        DocumentInfo documentInfo = new DocumentInfo();
        DocumentBinaryInfo binaryInfo = new DocumentBinaryInfo();
        binaryInfo.setBinary("Test document");
        documentInfo.setDocumentBinary(binaryInfo);
        documentInfo.setFileName("Sample");
        documentInfo.setTypeKey("documentType.type1");
        
        documentInfo = client.createDocument("documentType.type1", "CAT_1", documentInfo, contextInfo);
    	
        RefDocRelationInfo relation = new RefDocRelationInfo();
        relation.setDescr(new RichTextInfo());
        relation.getDescr().setFormatted("formatted");
        relation.getDescr().setPlain("plain");
        relation.setDocumentId(documentInfo.getId());
        relation.setEffectiveDate(DF.parse("20100101"));
        relation.setExpirationDate(DF.parse("20100102"));
        relation.setRefObjectId("REFER_TO_ME");
        relation.setRefObjectTypeKey("refObjectTypeKey");
        relation.setStateKey("state");
        relation.setTypeKey("type");
        relation.setTitle("Title");
        
        try{
        	client.createRefDocRelation(relation.getRefObjectTypeKey(), relation.getRefObjectId(), relation.getDocumentId(), relation.getTypeKey(), relation, contextInfo);
        	assertTrue(false);
        }catch(InvalidParameterException e){     	
        }
    	
        relation.setTypeKey("kuali.org.DocRelation.allObjectTypes");
        try{
        	client.createRefDocRelation(relation.getRefObjectTypeKey(), relation.getRefObjectId(), relation.getDocumentId(), relation.getTypeKey(), relation, contextInfo);
	       	assertTrue(false);
	    }catch(InvalidParameterException e){     	
	    }
	    
	    relation.setRefObjectTypeKey("kuali.org.RefObjectType.CluInfo");
	    
	    RefDocRelationInfo created = client.createRefDocRelation(relation.getRefObjectTypeKey(), relation.getRefObjectId(), relation.getDocumentId(), relation.getTypeKey(), relation, contextInfo);

	    //compare with original
	    assertRefDocRelationsEquals(relation, created);
	    
	    //Change some values
        created.setDescr(new RichTextInfo());
        created.getDescr().setFormatted("formattedU");
        created.getDescr().setPlain("plainU");
        created.setDocumentId(documentInfo.getId());
        created.setEffectiveDate(DF.parse("20100103"));
        created.setExpirationDate(DF.parse("20100104"));
        created.setRefObjectId("REFER_TO_MEU");
        created.setStateKey("stateU");
        created.setTitle("TitleU");
        
        //Update
        RefDocRelationInfo updated = client.updateRefDocRelation(created.getId(), created, contextInfo);
        
        //Test version mismatch
        try{
        	updated = client.updateRefDocRelation(created.getId(), created, contextInfo);
	       	assertTrue(false);
        }catch(VersionMismatchException e){
        }
        assertRefDocRelationsEquals(created,updated);
	    
	    //Test Gets
        // TODO KSCM-504
//	    List<RefDocRelationTypeInfo> docRelationTypes = client.getRefDocRelationTypes();
//	    assertEquals(1, docRelationTypes.size());
//	    
//	    docRelationTypes = client.getRefDocRelationTypesForRefObjectSubType("kuali.org.RefObjectSubType.Program");
//	    assertEquals(1, docRelationTypes.size());
	    
        List<RefDocRelationInfo> fetchedList = client.getRefDocRelationsByDocument(created.getDocumentId(), contextInfo);
	    assertEquals(1,fetchedList.size());
	    assertRefDocRelationsEquals(updated,fetchedList.get(0));

	    fetchedList = client.getRefDocRelationsByRef(updated.getRefObjectTypeKey(), updated.getRefObjectId(), contextInfo);
	    assertEquals(1,fetchedList.size());
	    assertRefDocRelationsEquals(updated,fetchedList.get(0));
	    
	    RefDocRelationInfo fetched = client.getRefDocRelation(updated.getId(), contextInfo);
	    assertRefDocRelationsEquals(updated,fetched);
	    	    
	    //Test delete
	    client.deleteRefDocRelation(fetched.getId(), contextInfo);
	    
	    try{
	    	fetched = client.getRefDocRelation(updated.getId(), contextInfo);
	    	assertTrue(false);
	    }catch(DoesNotExistException e){}
    }
    
    private void assertRefDocRelationsEquals(RefDocRelationInfo ref1, RefDocRelationInfo ref2){
	    assertEquals(ref1.getDescr().getFormatted(),ref2.getDescr().getFormatted());
	    assertEquals(ref1.getDescr().getPlain(),ref2.getDescr().getPlain());
	    assertEquals(ref1.getDocumentId(),ref2.getDocumentId());
	    assertEquals(ref1.getEffectiveDate(),ref2.getEffectiveDate());
	    assertEquals(ref1.getExpirationDate(),ref2.getExpirationDate());
	    assertEquals(ref1.getRefObjectId(),ref2.getRefObjectId());
	    assertEquals(ref1.getRefObjectTypeKey(),ref2.getRefObjectTypeKey());
	    assertEquals(ref1.getStateKey(),ref2.getStateKey());
	    assertEquals(ref1.getTitle(),ref2.getTitle());
	    assertEquals(ref1.getTypeKey(),ref2.getTypeKey());
    }
    
}
