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
import org.kuali.student.lum.lrc.dto.CredentialInfo;
import org.kuali.student.lum.lrc.dto.CredentialTypeInfo;
import org.kuali.student.lum.lrc.dto.CreditInfo;
import org.kuali.student.lum.lrc.dto.CreditTypeInfo;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.dto.GradeTypeInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lrc.dto.ScaleInfo;
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
        rci.setResultValueIds(resultValueIds);
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
            List<String> ids = newRci.getResultValueIds();
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
            rci.getResultValueIds().add("LRC-RESULT_VALUE-GRADE-2");
            try {
            	
                client.updateResultComponent(id, rci);
                newRci = client.getResultComponent(newRci.getId());
                assertNotNull(newRci);
                assertNotNull(newRci.getId());
                rti = newRci.getDesc();
                assertNotNull(rti);
                assertEquals("<p>New ResultComponent</p>", rti.getFormatted());
                assertEquals("New ResultComponent", rti.getPlain());
                ids = newRci.getResultValueIds();
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
            rci.getResultValueIds().add("LRC-RESULT_VALUE-CREDIT-1");
            try {
                client.updateResultComponent(id, rci);
                assertTrue(false);
            } catch (DataValidationErrorException e) {
                assertTrue(true);
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
        assertEquals(3, rci.getResultValueIds().size());

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

        try {
            rcis = client.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1x", "resultComponentType.credential");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            rcis = client.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1", "resultComponentType.credentialx");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

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

        try {
            rcis = client.getResultComponentIdsByResultComponentType("resultComponentType.credentialx");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

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

	@Test
    public void testGetCredential() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        CredentialInfo credentialInfo = client.getCredential("LRC-RESULT_VALUE-CREDENTIAL-1");
        assertNotNull(credentialInfo);

        try {
            credentialInfo = client.getCredential("LRC-RESULT_VALUE-CREDENTIAL-1X");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
        try {
            credentialInfo = client.getCredential(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetCredentialByKeyList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add("LRC-RESULT_VALUE-CREDENTIAL-1");
        ids.add("LRC-RESULT_VALUE-CREDENTIAL-2");
        ids.add("LRC-RESULT_VALUE-CREDENTIAL-1x");
        List<CredentialInfo> credentials = client.getCredentialsByKeyList(ids);
        assertNotNull(credentials);
        assertEquals(2, credentials.size());

        ids.clear();
        ids.add("LRC-RESULT_VALUE-CREDENTIAL-1x");
        try {
            credentials = client.getCredentialsByKeyList(ids);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        ids.clear();
        try {
            credentials = client.getCredentialsByKeyList(ids);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            credentials = client.getCredentialsByKeyList(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetCredentialKeysByCredentialType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> credentialIds = client.getCredentialKeysByCredentialType("lrcType.credential.3");
        assertNotNull(credentialIds);
        assertEquals(2, credentialIds.size());

        try {
            credentialIds = client.getCredentialKeysByCredentialType("lrcType.credential.3x");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            credentialIds = client.getCredentialKeysByCredentialType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetCredentialType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        CredentialTypeInfo credentialTypeInfo = client.getCredentialType("lrcType.credential.2");
        assertNotNull(credentialTypeInfo);

        try {
            credentialTypeInfo = client.getCredentialType("lrcType.credential.2x");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
        try {
            credentialTypeInfo = client.getCredentialType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

	@Test
    public void testGetCredit() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        CreditInfo creditInfo = client.getCredit("LRC-RESULT_VALUE-CREDIT-1");
        assertNotNull(creditInfo);

        try {
            creditInfo = client.getCredit("LRC-RESULT_VALUE-CREDIT-1X");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
        try {
            creditInfo = client.getCredit(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

	@Test
    public void testGetCreditByKeyList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ArrayList<String> keys = new ArrayList<String>();
        keys.add("LRC-RESULT_VALUE-CREDIT-1");
        keys.add("LRC-RESULT_VALUE-CREDIT-2");
        keys.add("LRC-RESULT_VALUE-CREDIT-1x");
        List<CreditInfo> credits = client.getCreditsByKeyList(keys);
        assertNotNull(credits);
        assertEquals(2, credits.size());

        keys.clear();
        keys.add("LRC-RESULT_VALUE-CREDIT-1x");
        try {
            credits = client.getCreditsByKeyList(keys);
            assertTrue(false);
        } catch (DoesNotExistException e1) {
            assertTrue(true);
        }

        keys.clear();
        try {
            credits = client.getCreditsByKeyList(keys);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            credits = client.getCreditsByKeyList(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetCreditKeysByCreditType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> creditIds = client.getCreditKeysByCreditType("lrcType.credit.3");
        assertNotNull(creditIds);
        assertEquals(2, creditIds.size());

        try {
            creditIds = client.getCreditKeysByCreditType("lrcType.credit.3x");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            creditIds = client.getCreditKeysByCreditType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetCreditType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        CreditTypeInfo creditTypeInfo = client.getCreditType("lrcType.credit.2");
        assertNotNull(creditTypeInfo);

        try {
            creditTypeInfo = client.getCreditType("lrcType.credit.2x");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
        try {
            creditTypeInfo = client.getCreditType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetCreditTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<CreditTypeInfo> creditTypeInfos = client.getCreditTypes();
        assertNotNull(creditTypeInfos);
        assertEquals(3, creditTypeInfos.size());
    }

    @Test
    public void testGetGrade() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        GradeInfo gradeInfo = client.getGrade("LRC-RESULT_VALUE-GRADE-1");
        assertNotNull(gradeInfo);

        try {
            gradeInfo = client.getGrade("LRC-RESULT_VALUE-GRADE-1X");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
        try {
            gradeInfo = client.getGrade(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetGradeByKeyList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ArrayList<String> keys = new ArrayList<String>();
        keys.add("LRC-RESULT_VALUE-GRADE-1");
        keys.add("LRC-RESULT_VALUE-GRADE-2");
        keys.add("LRC-RESULT_VALUE-GRADE-1x");
        List<GradeInfo> grades = client.getGradesByKeyList(keys);
        assertNotNull(grades);
        assertEquals(2, grades.size());

        keys.clear();
        keys.add("LRC-RESULT_VALUE-GRADE-1x");
        try {
            grades = client.getGradesByKeyList(keys);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        keys.clear();
        try {
            grades = client.getGradesByKeyList(keys);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            grades = client.getGradesByKeyList(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetGradeKeysByGradeType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> gradeIds = client.getGradeKeysByGradeType("lrcType.grade.3");
        assertNotNull(gradeIds);
        assertEquals(2, gradeIds.size());

        try {
            gradeIds = client.getGradeKeysByGradeType("lrcType.grade.3x");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            gradeIds = client.getGradeKeysByGradeType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetGradeType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        GradeTypeInfo gradeTypeInfo = client.getGradeType("lrcType.grade.2");
        assertNotNull(gradeTypeInfo);

        try {
            gradeTypeInfo = client.getGradeType("lrcType.grade.2x");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
        try {
            gradeTypeInfo = client.getGradeType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetGradeTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<GradeTypeInfo> gradeTypeInfos = client.getGradeTypes();
        assertNotNull(gradeTypeInfos);
        assertEquals(3, gradeTypeInfos.size());
    }

    @Test
    public void testGetGradesByScale() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<GradeInfo> grades = client.getGradesByScale("LRC-SCALE-1");
        assertNotNull(grades);
        assertEquals(2, grades.size());

        try {
            grades = client.getGradesByScale("LRC-SCALE-1x");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            grades = client.getGradesByScale(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetScale() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ScaleInfo scaleInfo = client.getScale("LRC-SCALE-1");
        assertNotNull(scaleInfo);
        try {
            scaleInfo = client.getScale("LRC-SCALE-1x");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            scaleInfo = client.getScale(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
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
        rc.setResultValueIds(Arrays.asList(new String[] {specificGradeId}));
        rc.setState("ACTIVE");
        rc.setType("resultComponentType.grade");
        
        client.createResultComponent("resultComponentType.grade", rc);
    	
    	
    }
}
