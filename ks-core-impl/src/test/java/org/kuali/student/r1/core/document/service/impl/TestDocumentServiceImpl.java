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

package org.kuali.student.r1.core.document.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.document.dto.DocumentBinaryInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
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
    
//
//    @Test
//    public void testDocTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
//        List<TypeInfo> types = client.getDocumentTypes();
//        assertNotNull(types);
//        assertTrue(types.size() > 0);
//        
//        TypeInfo type = client.getDocumentType(types.get(0).getId());
//        assertEquals(types.get(0).getId(), type.getId());
//        assertEquals(types.get(0).getName(), type.getName());
//    }
//    
//
//    @Test
//    public void testDocCategories() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
//        List<TypeInfo> categories = client.getDocumentCategories();
//        assertNotNull(categories);
//        assertTrue(categories.size() > 1); //this needs to be true for below test
//        
//        TypeInfo category = client.getDocumentCategory(categories.get(0).getId());
//        assertEquals(categories.get(0).getId(), category.getId());
//        assertEquals(categories.get(0).getName(), category.getName());
//    }
    

    @Test
    public void testDocument() throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            DoesNotExistException,
            VersionMismatchException,
            ReadOnlyException {
        ContextInfo context = new ContextInfo ();
        context.setPrincipalId("TESTUSER");
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
        doc.setAttributes(toAttributeInfoList(attributes));
        
        DocumentInfo created = client.createDocument("documentType.type1", "CAT_1", doc, context);
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
        Map<String, String> newAttributes = toAttributeMap (created.getAttributes());
        assertNotNull(newAttributes);
        assertEquals("attrValue", newAttributes.get("attrKey"));
        assertEquals("documentType.type1", created.getType());
        
        doc = client.getDocument(id, context);
        assertNotNull(doc);
        
        List<TypeInfo> categories = client.getDocumentCategoryTypesByDocument(id, context);
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("CAT_1", categories.get(0).getKey());
        
        List<DocumentInfo> documents = client.getDocumentsByIds(Arrays.asList(id), context);
        assertNotNull(documents);
        assertEquals(1, documents.size());
        assertEquals(id, documents.get(0).getId());
        doc = client.getDocument(id, context);
        StatusInfo status = client.addDocumentCategoryToDocument(id, "CAT_2", context);
        assertTrue(status.getIsSuccess());
        doc = client.getDocument(id, context);
        categories = client.getDocumentCategoryTypesByDocument(id, context);
        assertNotNull(categories);
        assertEquals(2, categories.size());
        boolean foundCat = false;
        for (TypeInfo documentCategoryInfo : categories) {
            if("CAT_2".equals(documentCategoryInfo.getKey())) {
                foundCat = true;
                break;
            }
        }
        assertTrue(foundCat);
        
        status = client.removeDocumentCategoryFromDocument(id, "CAT_2", context);
        assertTrue(status.getIsSuccess());
        
   
        status = client.removeDocumentCategoryFromDocument(id, "CAT_1", context);
        assertFalse(status.getIsSuccess());
        
        doc = client.getDocument(id, context);
        categories = client.getDocumentCategoryTypesByDocument(id, context);
        assertNotNull(categories);
        
        doc = client.getDocument(id, context);
        doc.setName("TPS");
        DocumentInfo updated = client.updateDocument(id, doc, context);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals("TPS", updated.getName());
        
        try {
            doc = client.updateDocument(id, doc, context);
            fail("Expected VersionMismatchException");
        } catch(VersionMismatchException e) {}
        
        status = client.deleteDocument(id, context);
        assertTrue(status.getIsSuccess());
    }


    @Test
    public void testDocumentCrud() throws DataValidationErrorException, InvalidParameterException , OperationFailedException, PermissionDeniedException, DoesNotExistException, MissingParameterException, VersionMismatchException {
        
        ContextInfo context = new ContextInfo ();
        context.setPrincipalId("TESTUSER");
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
        documentInfo.setType("documentType.type1");
        
        DocumentInfo documentInfoP = client.createDocument("documentType.type1", "CAT_1", documentInfo, context);
        assertEquals(documentInfo.getDescr().getPlain(),documentInfoP.getDescr().getPlain());
        assertEquals(documentInfo.getDocumentBinary().getBinary(),documentInfoP.getDocumentBinary().getBinary());
        assertEquals(documentInfo.getEffectiveDate(), documentInfoP.getEffectiveDate());
        assertEquals(documentInfo.getExpirationDate(), documentInfoP.getExpirationDate());
        assertEquals(documentInfo.getFileName(), documentInfoP.getFileName());
        
        

        StatusInfo statusInfo = client.addDocumentCategoryToDocument(documentInfoP.getId(), "CAT_2", context);
        assertTrue(statusInfo.getIsSuccess());
        
        
        List<TypeInfo> categoriesInfos = client.getDocumentCategoryTypesByDocument(documentInfoP.getId(), context);
        assertEquals(categoriesInfos.size(), 2);
        
        statusInfo = client.addDocumentCategoryToDocument(documentInfoP.getId(), "CAT_3", context);
        assertTrue(statusInfo.getIsSuccess());
        
        categoriesInfos = client.getDocumentCategoryTypesByDocument(documentInfoP.getId(), context);
        assertEquals(categoriesInfos.size(), 3);
        
        statusInfo = client.removeDocumentCategoryFromDocument(documentInfoP.getId(), "CAT_2", context);
        assertTrue(statusInfo.getIsSuccess());
        categoriesInfos = client.getDocumentCategoryTypesByDocument(documentInfoP.getId(), context);
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
//        List<DictValidationResultContainer> validations = atpService.validateDocument("", documentInfo);
//        for (DictValidationResultContainer validationResult : validations) {
//            assertTrue(validationResult.isOk());
//        }
    }
    
    
    @Test
    public void testDocRelationsCrud() throws Exception{
        ContextInfo context = new ContextInfo ();
        context.setPrincipalId("TESTUSER");
        //Create a document to get relations from
    	DocumentInfo documentInfo = new DocumentInfo();
        DocumentBinaryInfo binaryInfo = new DocumentBinaryInfo();
        binaryInfo.setBinary("Test document");
        documentInfo.setDocumentBinary(binaryInfo);
        documentInfo.setFileName("Sample");
        documentInfo.setTypeKey("documentType.type1");
        
        documentInfo = client.createDocument("documentType.type1", "CAT_1", documentInfo, context);
    	
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
        relation.setType("type");
        relation.setTitle("Title");
        
        try{
        	client.createRefDocRelation(relation.getRefObjectTypeKey(), 
                        relation.getRefObjectId(), 
                        relation.getDocumentId(), 
                        relation.getTypeKey(), 
                        relation,
                        context);
        	assertTrue(false);
        }catch(InvalidParameterException e){     	
        }
    	
        relation.setTypeKey("kuali.org.DocRelation.allObjectTypes");
        try{
        	client.createRefDocRelation(relation.getRefObjectTypeKey(), 
                        relation.getRefObjectId(), 
                        relation.getDocumentId(), 
                        relation.getTypeKey(), 
                        relation,
                        context);
	       	assertTrue(false);
	    }catch(InvalidParameterException e){     	
	    }
	    
	    relation.setRefObjectTypeKey("kuali.org.RefObjectType.CluInfo");
	    
	    RefDocRelationInfo created = client.createRefDocRelation(relation.getRefObjectTypeKey(), 
                    relation.getRefObjectId(), 
                    relation.getDocumentId(), 
                    relation.getTypeKey(), 
                    relation,
                    context);

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
        RefDocRelationInfo updated = client.updateRefDocRelation(created.getId(), created, context);
        
        //Test version mismatch
        try{
        	updated = client.updateRefDocRelation(created.getId(), created, context);
	       	assertTrue(false);
        }catch(VersionMismatchException e){
        }
        assertRefDocRelationsEquals(created,updated);
	    
	    //Test Gets
	    List<TypeInfo> docRelationTypes = client.getRefDocRelationTypes(context);
	    assertEquals(1, docRelationTypes.size());
	    
//	    docRelationTypes = client.getRefDocRelationTypesForRefObjectSubType("kuali.org.RefObjectSubType.Program");
//	    assertEquals(1, docRelationTypes.size());
//	    
//	    List<String> subTypes = client.getRefObjectSubTypes("kuali.org.RefObjectType.CluInfo");
//	    assertEquals(2, subTypes.size());
	    
//	    List<TypeInfo> objectTypes = client.getRefObjectTypes();
//	    assertEquals(2, objectTypes.size());
	    
        List<RefDocRelationInfo> fetchedList = client.getRefDocRelationsByDocument(created.getDocumentId(), context);
	    assertEquals(1,fetchedList.size());
	    assertRefDocRelationsEquals(updated,fetchedList.get(0));

	    fetchedList = client.getRefDocRelationsByRef(updated.getRefObjectTypeKey(), updated.getRefObjectId(), context);
	    assertEquals(1,fetchedList.size());
	    assertRefDocRelationsEquals(updated,fetchedList.get(0));
	    
	    RefDocRelationInfo fetched = client.getRefDocRelation(updated.getId(), context);
	    assertRefDocRelationsEquals(updated,fetched);
	    	    
	    //Test delete
	    client.deleteRefDocRelation(fetched.getId(), context);
	    
	    try{
	    	fetched = client.getRefDocRelation(updated.getId(), context);
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
	    assertEquals(ref1.getState(),ref2.getState());
	    assertEquals(ref1.getTitle(),ref2.getTitle());
	    assertEquals(ref1.getType(),ref2.getType());
    }
    
    
    private List<AttributeInfo> toAttributeInfoList(Map<String,String> attributes) {
        List<AttributeInfo> list = new ArrayList<AttributeInfo> ();
        for (String key : attributes.keySet()) {
            String value = attributes.get(key);
            list.add(new AttributeInfo (key, value));
        }
        return list;
    }
    
    
    private Map<String,String> toAttributeMap(List<AttributeInfo> attributes) {
        Map<String,String> map = new LinkedHashMap<String, String> ();
        for (AttributeInfo attribute : attributes) {
            map.put (attribute.getKey(), attribute.getValue());
        }
        return map;
    }
}
