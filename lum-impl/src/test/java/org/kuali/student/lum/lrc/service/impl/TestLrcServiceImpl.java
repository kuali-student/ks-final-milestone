package org.kuali.student.lum.lrc.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
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

@Daos( { @Dao(value = "org.kuali.student.lum.lrc.dao.impl.LrcDaoImpl",testSqlFile="classpath:ks-lrc.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lrc-persistence.xml")
public class TestLrcServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.lum.lrc.service.impl.LrcServiceImpl", port = "8181",additionalContextFile="classpath:lrc-additional-context.xml")
	public LrcService client;

    @Test
    public void testGetResultComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ResultComponentInfo rci = client.getResultComponent("LRC-RESCOMP-1");
        assertNotNull(rci);
        assertEquals(2, rci.getResultValueIds().size());

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
        List<String> rcis = client.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1", "lrcType.resultComponent.1");
        assertNotNull(rcis);
        assertEquals(1, rcis.size());

        try {
            rcis = client.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1x", "lrcType.resultComponent.1");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            rcis = client.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1", "lrcType.resultComponent.1x");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            rcis = client.getResultComponentIdsByResult(null, "lrcType.resultComponent.1x");
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
        List<String> rcis = client.getResultComponentIdsByResultComponentType("lrcType.resultComponent.1");
        assertNotNull(rcis);
        assertEquals(1, rcis.size());

        try {
            rcis = client.getResultComponentIdsByResultComponentType("lrcType.resultComponent.1x");
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
        assertEquals(3, rctis.size());
    }

    @Test
    public void testGetResultComponentType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ResultComponentTypeInfo rcti = client.getResultComponentType("lrcType.resultComponent.1");
        assertNotNull(rcti);
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
        ArrayList<String> keys = new ArrayList<String>();
        keys.add("LRC-RESULT_VALUE-CREDENTIAL-1");
        keys.add("LRC-RESULT_VALUE-CREDENTIAL-2");
        keys.add("LRC-RESULT_VALUE-CREDENTIAL-1x");
        List<CredentialInfo> credentials = client.getCredentialsByKeyList(keys);
        assertNotNull(credentials);
        assertEquals(2, credentials.size());

        keys.clear();
        keys.add("LRC-RESULT_VALUE-CREDENTIAL-1x");
        try {
            credentials = client.getCredentialsByKeyList(keys);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        keys.clear();
        try {
            credentials = client.getCredentialsByKeyList(keys);
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
}
