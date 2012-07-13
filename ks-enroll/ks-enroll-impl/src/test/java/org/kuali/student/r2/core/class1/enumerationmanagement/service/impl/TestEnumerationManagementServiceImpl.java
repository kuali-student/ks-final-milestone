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

package org.kuali.student.r2.core.class1.enumerationmanagement.service.impl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumContextValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:em-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestEnumerationManagementServiceImpl {
    
    @Resource(name="enumServiceAuthDecorator")
    public EnumerationManagementService enumService;
    
    public static String principalId = "123";

    public ContextInfo callContext = null;
    
    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testGetEnumerations() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        //findEnumerationMetas
        EnumerationInfo dto = null;
        List<EnumerationInfo> list = enumService.getEnumerations(callContext);
        
        assertEquals(11, list.size());
        boolean foundMeta = false;
        
        for(EnumerationInfo ei : list) {
            if(ei.getKey().equals("kuali.enum.lu.program.level")) {
                foundMeta = true;
                dto = ei;
            }
        }
        
        assertTrue(foundMeta);
        
        assertEquals("kuali.enum.lu.program.level", dto.getKey());
        assertEquals("Program Level", dto.getName());
        assertEquals("level.descr", dto.getDescr().getPlain());
    }
    
    @Test
    public void testGetEnumeration() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        //fetchEnumerationMeta
        EnumerationInfo dto = enumService.getEnumeration("kuali.enum.lu.program.level", callContext);
        assertEquals("kuali.enum.lu.program.level", dto.getKey());
        assertEquals("Program Level", dto.getName());
        assertEquals("level.descr", dto.getDescr().getPlain());
        
        //fetchEnumerationMeta for "NULL" key
        EnumerationInfo dto_null = null;
        try {
            dto_null = enumService.getEnumeration("NULL", callContext);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
        assertEquals(dto_null, null);
    }
    
    @Test
    public void testGetEnumeratedValues() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, ReadOnlyException{
        long baseTime = System.currentTimeMillis();
        
        EnumeratedValueInfo entity1 = new EnumeratedValueInfo();
        entity1.setEnumerationKey("kuali.lu.finalExam.status");
        entity1.setAbbrevValue("Abbrev1");
        entity1.setCode("Code1");
        entity1.setEffectiveDate(new Date(baseTime-10000000L));
        entity1.setExpirationDate(new Date(baseTime+10000000L));
        entity1.setSortKey("1");
        entity1.setValue("Value1");
        entity1.setMeta(generateMetaInfo());

        EnumeratedValueInfo entity2 = new EnumeratedValueInfo();
        entity2.setEnumerationKey("kuali.lu.finalExam.status");
        entity2.setAbbrevValue("Abbrev2");
        entity2.setCode("Code2");
        entity2.setEffectiveDate(new Date(baseTime-10000000L));
        entity2.setExpirationDate(new Date(baseTime+50000000L));
        entity2.setSortKey("1");
        entity2.setValue("Value2");
        entity2.setMeta(generateMetaInfo());
        
        EnumeratedValueInfo entity3 = new EnumeratedValueInfo();
        entity3.setEnumerationKey("kuali.lu.finalExam.status");
        entity3.setAbbrevValue("Abbrev3");
        entity3.setCode("Code3");
        entity3.setEffectiveDate(new Date(baseTime-10000000L));
        entity3.setExpirationDate(null);
        entity3.setSortKey("1");
        entity3.setValue("Value3");
        entity3.setMeta(generateMetaInfo());
        
        EnumeratedValueInfo entity4 = new EnumeratedValueInfo();
        entity4.setEnumerationKey("kuali.lu.finalExam.status");
        entity4.setAbbrevValue("Abbrev4");
        entity4.setCode("Code4");
        entity4.setEffectiveDate(new Date(baseTime-10000000L));
        entity4.setExpirationDate(new Date(baseTime+50000000L));
        entity4.setSortKey("1");
        entity4.setValue("Value4");
        entity4.setMeta(generateMetaInfo());

        EnumContextValueInfo contextEntity1 = new EnumContextValueInfo();
        contextEntity1.setKey("country");
        contextEntity1.setValue("US");
        contextEntity1.setMeta(generateMetaInfo());
        
        EnumContextValueInfo contextEntity2 = new EnumContextValueInfo();
        contextEntity2.setKey("country");
        contextEntity2.setValue("US");
        contextEntity2.setMeta(generateMetaInfo());
        
        EnumContextValueInfo contextEntity3 = new EnumContextValueInfo();
        contextEntity3.setKey("country");
        contextEntity3.setValue("CA");
        contextEntity3.setMeta(generateMetaInfo());
        
        EnumContextValueInfo contextEntity4 = new EnumContextValueInfo();
        contextEntity4.setKey("country");
        contextEntity4.setValue("CA");
        contextEntity4.setMeta(generateMetaInfo());
        
        entity1.getContexts().add(contextEntity1);
        entity2.getContexts().add(contextEntity2);
        entity3.getContexts().add(contextEntity3);
        entity4.getContexts().add(contextEntity4);
        
        enumService.addEnumeratedValue("kuali.lu.finalExam.status", entity1.getCode(), entity1, callContext);
        enumService.addEnumeratedValue("kuali.lu.finalExam.status", entity2.getCode(), entity2, callContext);
        enumService.addEnumeratedValue("kuali.lu.finalExam.status", entity3.getCode(), entity3, callContext);
        enumService.addEnumeratedValue("kuali.lu.finalExam.status", entity4.getCode(), entity4, callContext);
        
        List<EnumeratedValueInfo> list = enumService.getEnumeratedValues("kuali.lu.finalExam.status", "country", "US", new Date(baseTime), callContext);
        assertEquals(2, list.size());        
            
        list = enumService.getEnumeratedValues("kuali.lu.finalExam.status", null, null, null, callContext);
        assertEquals(4, list.size());
        
        list = enumService.getEnumeratedValues("kuali.lu.finalExam.status", "country", "CA", null, callContext);
        assertEquals(2, list.size());
            
        list = enumService.getEnumeratedValues("kuali.lu.finalExam.status", "country", "US", null, callContext);
        assertEquals(2, list.size());
        
        //testing accuracy
        EnumeratedValueInfo listItem = list.get(0); 
        
        assertEquals(listItem.getAbbrevValue(), entity1.getAbbrevValue());
        assertEquals(listItem.getCode(), entity1.getCode());
        assertEquals(listItem.getEffectiveDate(), entity1.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), entity1.getExpirationDate());
        assertEquals(listItem.getSortKey(), entity1.getSortKey());
        assertEquals(listItem.getValue(), entity1.getValue());
        int i =0;
        for(EnumContextValueInfo c: listItem.getContexts()){
            EnumContextValueInfo original = entity2.getContexts().get(i);
            assertEquals(c.getKey(), original.getKey());
            assertEquals(c.getValue(), original.getValue());
            i++;
        }
        
        listItem = list.get(1); 
        
        assertEquals(listItem.getAbbrevValue(), entity2.getAbbrevValue());
        assertEquals(listItem.getCode(), entity2.getCode());
        assertEquals(listItem.getEffectiveDate(), entity2.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), entity2.getExpirationDate());
        assertEquals(listItem.getSortKey(), entity2.getSortKey());
        assertEquals(listItem.getValue(), entity2.getValue());
        i =0;
        for(EnumContextValueInfo c: listItem.getContexts()){
            EnumContextValueInfo original = entity2.getContexts().get(i);
            assertEquals(c.getKey(), original.getKey());
            assertEquals(c.getValue(), original.getValue());
            i++;
        }

        list = enumService.getEnumeratedValues("kuali.lu.finalExam.status", null, null, new Date(baseTime), callContext);
        assertEquals(4, list.size());

        list = enumService.getEnumeratedValues("kuali.lu.finalExam.status", null, null, new Date(baseTime+40000000L), callContext);
        assertEquals(3, list.size());
        
        list = enumService.getEnumeratedValues("kuali.lu.finalExam.status", "country", "US", new Date(baseTime+40000000L), callContext);
        assertEquals(1, list.size());
        
        list = enumService.getEnumeratedValues("kuali.lu.finalExam.status", "country", "CA", new Date(baseTime+40000000L), callContext);
        assertEquals(2, list.size());
        
        list = enumService.getEnumeratedValues("kuali.lu.finalExam.status", "country", "CA", new Date(baseTime), callContext);
        assertEquals(2, list.size());
        
        try{
            list = enumService.getEnumeratedValues(null, null, null, null, callContext);
            assertTrue("This line is not supposed to be reached.", false);
        }catch (DoesNotExistException e) {
            assertTrue(true);
        }
        
    }

    private MetaInfo generateMetaInfo() {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(principalId);
        meta.setCreateTime(new Date());

        return meta;
    }

    @Test
    public void testValidateEnumeratedValues(){
        EnumerationInfo em = new EnumerationInfo();
        em.setKey("KeyV");
        em.setName("Validation Meta");
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain("Meta used for validation");
        em.setDescr(rt);
    }
    
    @Test
    public void testUpdateEnumeratedValue() throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException, AlreadyExistsException{

        long baseTime = System.currentTimeMillis();
        
        EnumeratedValueInfo dto = new EnumeratedValueInfo();
        dto.setEnumerationKey("kuali.enum.type.cip2000");
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey("1");
        dto.setValue("v");
        dto.setAbbrevValue("a");
        dto.setMeta(generateMetaInfo());

        
        //dto context
        List<EnumContextValueInfo> dtoContext = new ArrayList<EnumContextValueInfo>();
        EnumContextValueInfo newContext = new EnumContextValueInfo();
        newContext.setKey("ContextA");
        newContext.setValue("1");
        newContext.setMeta(generateMetaInfo());
        dtoContext.add(newContext);
        dto.setContexts(dtoContext);
        //add first
        dto = enumService.addEnumeratedValue("kuali.enum.type.cip2000", dto.getCode(), dto, callContext);

        List<EnumeratedValueInfo> list = enumService.getEnumeratedValues("kuali.enum.type.cip2000", "ContextA", "1", new Date(baseTime), callContext);
        assertEquals(list.size(), 1);
        EnumeratedValueInfo listItem = list.get(0);
        
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        
        for(EnumContextValueInfo c: listItem.getContexts()){
            EnumContextValueInfo original = newContext;
            assertEquals(c.getKey(), original.getKey());
            assertEquals(c.getValue(), original.getValue());
        }
        
        //update
        //update currently fails on context updates
        dto.setCode("newCode");
        dto.setValue("newValue");
        dto.getContexts().get(0).setKey("newType");
        dto.getContexts().get(0).setValue("newContextValue");
        enumService.updateEnumeratedValue("kuali.enum.type.cip2000", "c", dto, callContext);

        list = enumService.getEnumeratedValues("kuali.enum.type.cip2000", "newType", "newContextValue", new Date(baseTime), callContext);
        assertEquals(list.size(), 1);
        listItem = list.get(0);
        
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        
        for(EnumContextValueInfo c: listItem.getContexts()){
            assertEquals(c.getKey(), "newType");
            assertEquals(c.getValue(), "newContextValue");
        }
    }
    
    @Test
    public void testDeleteEnumeratedValue() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException{
        
        long baseTime = System.currentTimeMillis();
        
        EnumeratedValueInfo dto = new EnumeratedValueInfo();
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey("1");
        dto.setValue("v");
        dto.setAbbrevValue("a");
        dto.setEnumerationKey("kuali.enum.type.cip2000");
        dto.setMeta(generateMetaInfo());
        
        //dto context
        List<EnumContextValueInfo> dtoContext = new ArrayList<EnumContextValueInfo>();
        EnumContextValueInfo newContext = new EnumContextValueInfo();
        newContext.setKey("ContextA");
        newContext.setValue("1");
        newContext.setMeta(generateMetaInfo());
        dtoContext.add(newContext);
        dto.setContexts(dtoContext);
        //add first
        enumService.addEnumeratedValue("kuali.enum.type.cip2000", dto.getCode(), dto, callContext);
        enumService.deleteEnumeratedValue("kuali.enum.type.cip2000", "c", callContext);

        List<EnumeratedValueInfo> list = enumService.getEnumeratedValues("kuali.enum.type.cip2000", "ContextA", "1", new Date(baseTime), callContext);
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testAddEnumeratedValue() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException{

        long baseTime = System.currentTimeMillis();

        EnumeratedValueInfo dto = new EnumeratedValueInfo();
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey("1");
        dto.setValue("v");
        dto.setAbbrevValue("a");
        dto.setEnumerationKey("kuali.lu.fee.feeType");
        dto.setMeta(generateMetaInfo());
        
        //dto context
        List<EnumContextValueInfo> dtoContext = new ArrayList<EnumContextValueInfo>();
        EnumContextValueInfo newContext = new EnumContextValueInfo();
        newContext.setKey("ContextA");
        newContext.setValue("1");
        newContext.setMeta(generateMetaInfo());
        dtoContext.add(newContext);
        dto.setContexts(dtoContext);
        enumService.addEnumeratedValue("kuali.lu.fee.feeType", dto.getCode(), dto, callContext);

        List<EnumeratedValueInfo> list = enumService.getEnumeratedValues("kuali.lu.fee.feeType", "ContextA", "1", new Date(baseTime), callContext);
        assertEquals(1, list.size());
        EnumeratedValueInfo listItem = list.get(0);
        
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        
        for(EnumContextValueInfo c: listItem.getContexts()){
            EnumContextValueInfo original = newContext;
            assertEquals(c.getKey(), original.getKey());
            assertEquals(c.getValue(), original.getValue());
        }
    }
}
