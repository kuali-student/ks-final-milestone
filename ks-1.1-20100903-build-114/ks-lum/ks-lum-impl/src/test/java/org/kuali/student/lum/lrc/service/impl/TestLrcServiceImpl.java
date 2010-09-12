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
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lrc.service.LrcService;

import edu.emory.mathcs.backport.java.util.Arrays;

@Daos( { @Dao(value = "org.kuali.student.lum.lrc.dao.impl.LrcDaoImpl",testSqlFile="classpath:ks-lrc.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lrc-persistence.xml")
public class TestLrcServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.lum.lrc.service.impl.LrcServiceImpl", additionalContextFile="classpath:lrc-additional-context.xml")
	public LrcService client;

	@Test
    public void testResultComponentCrud() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ResultComponentInfo rci = new ResultComponentInfo();
        rci.setName("New Result Component");
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p>New ResultComponent</p>");
        richText.setPlain("New ResultComponent");
        rci.setDesc(richText);
        List<String> resultValueIds = new ArrayList<String>();
        resultValueIds.add("LRC-RESULT_VALUE-GRADE-1");
        rci.setResultValues(resultValueIds);
        Date date = new Date();
        rci.setEffectiveDate(date);
        rci.setExpirationDate(date);
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("attrKey", "attrValue");
        rci.setAttributes(attributes);
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setCreateId("1");
        metaInfo.setCreateTime(date);
        metaInfo.setUpdateId("2");
        metaInfo.setUpdateTime(date);
        rci.setMetaInfo(metaInfo);
        rci.setType("resultComponentType.grade");
        rci.setState("Active");
        try {
            ResultComponentInfo newRci = client.createResultComponent("resultComponentType.grade", rci);
            assertNotNull(newRci);
            String id = newRci.getId();
            assertNotNull(id);

            RichTextInfo rti = newRci.getDesc();
            assertNotNull(rti);
            assertEquals("<p>New ResultComponent</p>", rti.getFormatted());
            assertEquals("New ResultComponent", rti.getPlain());
            List<String> ids = newRci.getResultValues();
            java.util.Collections.sort(ids);
            assertNotNull(ids);
            assertEquals(1, ids.size());
            assertEquals("LRC-RESULT_VALUE-GRADE-1", ids.get(0));
            assertEquals(date.toString(), newRci.getEffectiveDate().toString());
            assertEquals(date.toString(), newRci.getExpirationDate().toString());
            Map<String, String> newAttributes = newRci.getAttributes();
            assertNotNull(newAttributes);
            assertEquals("attrValue", newAttributes.get("attrKey"));
            assertEquals("resultComponentType.grade", newRci.getType());
            assertEquals("Active", newRci.getState());

            rci = client.getResultComponent(id);
            rci.getResultValues().add("LRC-RESULT_VALUE-GRADE-2");
            try {
            	
                client.updateResultComponent(id, rci);
                newRci = client.getResultComponent(newRci.getId());
                assertNotNull(newRci);
                assertNotNull(newRci.getId());
                rti = newRci.getDesc();
                assertNotNull(rti);
                assertEquals("<p>New ResultComponent</p>", rti.getFormatted());
                assertEquals("New ResultComponent", rti.getPlain());
                ids = newRci.getResultValues();
                java.util.Collections.sort(ids);
                assertNotNull(ids);
                assertEquals(2, ids.size());
                assertEquals("LRC-RESULT_VALUE-GRADE-1", ids.get(0));
                assertEquals("LRC-RESULT_VALUE-GRADE-2", ids.get(1));
                assertEquals(date.toString(), newRci.getEffectiveDate().toString());
                assertEquals(date.toString(), newRci.getExpirationDate().toString());
                newAttributes = newRci.getAttributes();
                assertNotNull(newAttributes);
                assertEquals("attrValue", newAttributes.get("attrKey"));
                assertEquals("resultComponentType.grade", newRci.getType());
                assertEquals("Active", newRci.getState());
            } catch (VersionMismatchException e) {
                assertTrue(false);
            }
            
            //Updateing an out of date version should throw an exception
            try{
            	client.updateResultComponent(id, rci);
            	assertTrue(false);
            }catch(VersionMismatchException e){
            	assertTrue(true);
            }
            
            rci = client.getResultComponent(id);
            rci.getResultValues().add("LRC-RESULT_VALUE-CREDIT-1");
            try {
                client.updateResultComponent(id, rci);
                //assertTrue(false);
            } catch (DataValidationErrorException e) {
                assertTrue(false);
            } catch (VersionMismatchException e) {
                assertTrue(false);
            }

            
            

            StatusInfo statusInfo = client.deleteResultComponent(id);
            assertTrue(statusInfo.getSuccess());

        } catch (AlreadyExistsException e) {
            assertTrue(false);
        } catch (DataValidationErrorException e) {
            assertTrue(false);
        } catch (PermissionDeniedException e) {
            assertTrue(false);
        }

        ResultComponentInfo resultComponentInfo = new ResultComponentInfo();
        try {
            client.createResultComponent(null, resultComponentInfo);
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
            client.createResultComponent("a", null);
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
            client.updateResultComponent(null, resultComponentInfo);
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
            client.updateResultComponent("a", null);
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
            client.deleteResultComponent("xx1");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        } catch (PermissionDeniedException e) {
            assertTrue(false);
        }
        try {
            client.deleteResultComponent(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        } catch (PermissionDeniedException e) {
            assertTrue(false);
        }
  }

    @Test
    public void testGetResultComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ResultComponentInfo rci = client.getResultComponent("LRC-RESCOMP-1");
        assertNotNull(rci);
        assertEquals(4, rci.getResultValues().size());

        try {
            rci = client.getResultComponent("LRC-RESCOMP-1x");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            rci = client.getResultComponent(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetResultComponentIdsByResult() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> rcis = client.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1", "resultComponentType.credential");
        assertNotNull(rcis);
        assertEquals(1, rcis.size());

        rcis = client.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1x", "resultComponentType.credential");
        assertEquals(0,rcis.size());
     
        rcis = client.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1", "resultComponentType.credentialx");
        assertEquals(0,rcis.size());

        try {
            rcis = client.getResultComponentIdsByResult(null, "resultComponentType.credentialx");
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            rcis = client.getResultComponentIdsByResult("a", null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
   }

    @Test
    public void testGetResultComponentIdsByResultComponentType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> rcis = client.getResultComponentIdsByResultComponentType("resultComponentType.credential");
        assertNotNull(rcis);
        assertEquals(1, rcis.size());

        rcis = client.getResultComponentIdsByResultComponentType("resultComponentType.credentialx");
        assertEquals(0,rcis.size());

        try {
            rcis = client.getResultComponentIdsByResultComponentType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

    }

    @Test
    public void getResultComponentTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<ResultComponentTypeInfo> rctis = client.getResultComponentTypes();
        assertNotNull(rctis);
        assertEquals(6, rctis.size());
    }

    @Test
    public void testGetResultComponentType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ResultComponentTypeInfo rcti = client.getResultComponentType("resultComponentType.credential");
        assertNotNull(rcti);

        try {
            rcti = client.getResultComponentType("resultComponentType.credentialYYY");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    
    public void testBusinessCaseExample() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	ResultComponentInfo rc = new ResultComponentInfo();
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted("<p>ResultComponent</p>");
        richText.setPlain("ResultComponent");
        rc.setDesc(richText);
    	
        String specificGradeId = "LRC-RESULT_VALUE-GRADE-1";
        
        rc.setName("ResultComponent");
        rc.setResultValues(Arrays.asList(new String[] {specificGradeId}));
        rc.setState("ACTIVE");
        rc.setType("resultComponentType.grade");
        
        client.createResultComponent("resultComponentType.grade", rc);
    	
    	
    }
}
