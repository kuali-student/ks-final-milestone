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

package org.kuali.student.lum.lrc.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.r1.lum.lrc.service.LrcService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r1.common.dto.StatusInfo;
import org.kuali.student.r1.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r2.common.dto.ContextInfo;


import edu.emory.mathcs.backport.java.util.Arrays;

@Daos( { @Dao(value = "org.kuali.student.lum.lrc.dao.impl.LrcDaoImpl",testSqlFile="classpath:ks-lrc.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lrc-persistence.xml")
public class TestLrcServiceImpl extends AbstractServiceTest {
    
	@Client(value = "org.kuali.student.lum.lrc.service.impl.LrcServiceImpl", additionalContextFile="classpath:lrc-additional-context.xml")
	public LrcService client;

    @Test
    public void testResultComponentCrud() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        ResultValuesGroupInfo rci = new ResultValuesGroupInfo();
        rci.setName("New Result Component");
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p>New ResultComponent</p>");
        richText.setPlain("New ResultComponent");
        rci.setDescr(richText);
        
        List<String> resultValueIds = new ArrayList<String>();
        resultValueIds.add("LRC-RESULT_VALUE-GRADE-1");
        rci.setResultValueKeys(resultValueIds);
        Date date = new Date();
        rci.setEffectiveDate(date);
        rci.setExpirationDate(date);
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        attributes.add(new AttributeInfo("attrKey", "attrValue"));
        rci.setAttributes(attributes);
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setCreateId("1");
        metaInfo.setCreateTime(date);
        metaInfo.setUpdateId("2");
        metaInfo.setUpdateTime(date);
        rci.setMeta(metaInfo);
        rci.setTypeKey("resultComponentType.grade");
        rci.setStateKey("Active");
        try {
            ResultValuesGroupInfo newRci = client.createResultValuesGroup(rci, ContextInfoTestUtility.getEnglishContextInfo());
            assertNotNull(newRci);
            String id = newRci.getKey();
            assertNotNull(id);

            RichTextInfo rti = newRci.getDescr();
            
            assertNotNull(rti);
            assertEquals("<p>New ResultComponent</p>", rti.getFormatted());
            assertEquals("New ResultComponent", rti.getPlain());
            List<String> ids = newRci.getResultValueKeys();
            java.util.Collections.sort(ids);
            assertNotNull(ids);
            assertEquals(1, ids.size());
            assertEquals("LRC-RESULT_VALUE-GRADE-1", ids.get(0));
            assertEquals(date.toString(), newRci.getEffectiveDate().toString());
            assertEquals(date.toString(), newRci.getExpirationDate().toString());
            List<AttributeInfo> newAttributes = newRci.getAttributes();
            assertNotNull(newAttributes);
            for (AttributeInfo attr : newAttributes){
                if (attr.getKey().equals("attrKey")){
                    assertEquals("attrValue", attr.getValue());
                }
            }
            assertEquals("resultComponentType.grade", newRci.getTypeKey());
            assertEquals("Active", newRci.getStateKey());

            rci = client.getResultValuesGroup(id, contextInfo);
            rci.getResultValueKeys().add("LRC-RESULT_VALUE-GRADE-2");
            try {
            	
                client.updateResultValuesGroup(id, rci, ContextInfoTestUtility.getEnglishContextInfo());
                newRci = client.getResultValuesGroup(newRci.getKey(), contextInfo);
                assertNotNull(newRci);
                assertNotNull(newRci.getKey());
                rti = newRci.getDescr();
                
                assertNotNull(rti);
                assertEquals("<p>New ResultComponent</p>", rti.getFormatted());
                assertEquals("New ResultComponent", rti.getPlain());
                ids = newRci.getResultValueKeys();
                java.util.Collections.sort(ids);
                assertNotNull(ids);
                assertEquals(2, ids.size());
                assertEquals("LRC-RESULT_VALUE-GRADE-1", ids.get(0));
                assertEquals("LRC-RESULT_VALUE-GRADE-2", ids.get(1));
                assertEquals(date.toString(), newRci.getEffectiveDate().toString());
                assertEquals(date.toString(), newRci.getExpirationDate().toString());
                newAttributes = newRci.getAttributes();
                assertNotNull(newAttributes);
                for (AttributeInfo attr : newAttributes){
                    if (attr.getKey().equals("attrKey")){
                        assertEquals("attrValue", attr.getValue());
                    }
                }
                assertEquals("resultComponentType.grade", newRci.getTypeKey());
                assertEquals("Active", newRci.getStateKey());
            } catch (VersionMismatchException e) {
                assertTrue(false);
            }
            
            //Updating an out of date version should throw an exception
            try{
            	client.updateResultValuesGroup(id, rci, ContextInfoTestUtility.getEnglishContextInfo());
            	assertTrue(false);
            }catch(VersionMismatchException e){
            	assertTrue(true);
            }
            
            rci = client.getResultValuesGroup(id, contextInfo);
            rci.getResultValueKeys().add("LRC-RESULT_VALUE-CREDIT-1");
            try {
                client.updateResultValuesGroup(id, rci, ContextInfoTestUtility.getEnglishContextInfo());
                //assertTrue(false);
            } catch (DataValidationErrorException e) {
                assertTrue(false);
            } catch (VersionMismatchException e) {
                assertTrue(false);
            }

            
            

            StatusInfo statusInfo = client.deleteResultValuesGroup(id, contextInfo);
            assertTrue(statusInfo.getSuccess());

        } catch (AlreadyExistsException e) {
            assertTrue(false);
        } catch (DataValidationErrorException e) {
            assertTrue(false);
        } catch (PermissionDeniedException e) {
            assertTrue(false);
        }

        ResultValuesGroupInfo resultComponentInfo = new ResultValuesGroupInfo();
        try {
            client.createResultValuesGroup(resultComponentInfo, ContextInfoTestUtility.getEnglishContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        } catch (AlreadyExistsException e) {
            assertTrue(false);
        } catch (DataValidationErrorException e) {
           // assertTrue(false);
        } catch (PermissionDeniedException e) {
            assertTrue(false);
        }

        try {
            client.createResultValuesGroup(null, ContextInfoTestUtility.getEnglishContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        } catch (AlreadyExistsException e) {
            assertTrue(false);
        } catch (DataValidationErrorException e) {
            assertTrue(false);
        } catch (PermissionDeniedException e) {
            assertTrue(false);
        }

        try {
            client.updateResultValuesGroup(null, resultComponentInfo, ContextInfoTestUtility.getEnglishContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        } catch (VersionMismatchException e) {
            assertTrue(false);
        } catch (DataValidationErrorException e) {
            assertTrue(false);
        } catch (PermissionDeniedException e) {
            assertTrue(false);
        }

        try {
            client.updateResultValuesGroup("a", null, ContextInfoTestUtility.getEnglishContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        } catch (VersionMismatchException e) {
            assertTrue(false);
        } catch (DataValidationErrorException e) {
            assertTrue(false);
        } catch (PermissionDeniedException e) {
            assertTrue(false);
        }

        try {
            client.deleteResultValuesGroup("xx1", contextInfo);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        } catch (PermissionDeniedException e) {
            assertTrue(false);
        }
        try {
            client.deleteResultValuesGroup(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        } catch (PermissionDeniedException e) {
            assertTrue(false);
        }
  }

    @Test
    public void testGetResultComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        ResultValuesGroupInfo rci = client.getResultValuesGroup("LRC-RESCOMP-1", contextInfo);
        assertNotNull(rci);
        assertEquals(4, rci.getResultValueKeys().size());

        try {
            rci = client.getResultValuesGroup("LRC-RESCOMP-1x", contextInfo);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            rci = client.getResultValuesGroup(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test  
    public void testGetResultComponentIdsByResult() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        List<ResultValuesGroupInfo> rcis = client.getResultValuesGroupsByResultValue("LRC-RESULT_VALUE-CREDIT-1", contextInfo);
        assertNotNull(rcis);
        assertEquals(1, rcis.size());

        
        rcis = client.getResultValuesGroupsByResultValue("LRC-RESULT_VALUE-CREDIT-1x", contextInfo);
        assertNotNull(rcis); // it should not be null, it should be an empty array, the 2.2.9 cxf return empty array, thus 2.3.8 must also do it
        assertEquals(0,rcis.size());
     
        rcis = client.getResultValuesGroupsByResultValue("LRC-RESULT_VALUE-CREDIT-1", contextInfo);
        assertNotNull(rcis); // it should not be null, it should be an empty array, the 2.2.9 cxf return empty array, thus 2.3.8 must also do it
        assertEquals(0,rcis.size());

        try {
            rcis = client.getResultValuesGroupsByResultValue(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            rcis = client.getResultValuesGroupsByResultValue("a",contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
   }

    @Test
    public void testGetResultComponentIdsByResultComponentType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        List<String> rcis = client.getResultValuesGroupIdsByType("resultComponentType.credential", contextInfo);
        assertNotNull(rcis);
        assertEquals(1, rcis.size());

        
        rcis = client.getResultValuesGroupIdsByType("resultComponentType.credentialx", contextInfo);
        assertNotNull(rcis); // it should not be null, it should be an empty array, the 2.2.9 cxf return empty array, thus 2.3.8 must also do it
        assertEquals(0,rcis.size());

        try {
            rcis = client.getResultValuesGroupIdsByType(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

    }

    @Test
    public void getResultComponentTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        List<ResultComponentTypeInfo> rctis = client.getResultComponentTypes(contextInfo);
        assertNotNull(rctis);
        assertEquals(7, rctis.size());
    }

    @Test
    public void testGetResultComponentType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        ResultComponentTypeInfo rcti = client.getResultComponentType("resultComponentType.credential", contextInfo);
        assertNotNull(rcti);

        try {
            rcti = client.getResultComponentType("resultComponentType.credentialYYY", contextInfo);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    
    public void testBusinessCaseExample() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ResultValuesGroupInfo rc = new ResultValuesGroupInfo();
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p>ResultComponent</p>");
        richText.setPlain("ResultComponent");
        rc.setDescr(richText);
    	
        String specificGradeId = "LRC-RESULT_VALUE-GRADE-1";
        
        rc.setName("ResultComponent");
        rc.setResultValueKeys(Arrays.asList(new String[] {specificGradeId}));
        rc.setStateKey("ACTIVE");
        rc.setTypeKey("resultComponentType.grade");
        
        client.createResultValuesGroup(rc, ContextInfoTestUtility.getEnglishContextInfo());
    	
    	
    }
}
