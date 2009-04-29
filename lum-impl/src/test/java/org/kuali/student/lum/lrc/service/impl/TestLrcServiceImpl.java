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
import org.kuali.student.lum.lrc.service.LrcService;

@Daos( { @Dao(value = "org.kuali.student.lum.lrc.dao.impl.LrcDaoImpl",testSqlFile="classpath:ks-lrc.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lrc-persistence.xml")
public class TestLrcServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.lum.lrc.service.impl.LrcServiceImpl", port = "8181",additionalContextFile="classpath:lrc-additional-context.xml")
	public LrcService client;

    @Test
    public void testGetCredential() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        CredentialInfo credentialInfo = client.getCredential("LRC-CREDENTIAL-1");
        assertNotNull(credentialInfo);

        try {
            credentialInfo = client.getCredential("LRC-CREDENTIAL-1X");
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
        keys.add("LRC-CREDENTIAL-1");
        keys.add("LRC-CREDENTIAL-2");
        keys.add("LRC-CREDENTIAL-1x");
        List<CredentialInfo> credentials = client.getCredentialsByKeyList(keys);
        assertNotNull(credentials);
        assertEquals(2, credentials.size());

        keys.clear();
        keys.add("LRC-CREDENTIAL-1x");
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
        List<String> credentialIds = client.getCredentialKeysByCredentialType("lcrType.credential.3");
        assertNotNull(credentialIds);
        assertEquals(2, credentialIds.size());

        try {
            credentialIds = client.getCredentialKeysByCredentialType("lcrType.credential.3x");
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
        CredentialTypeInfo credentialTypeInfo = client.getCredentialType("lcrType.credential.2");
        assertNotNull(credentialTypeInfo);

        try {
            credentialTypeInfo = client.getCredentialType("lcrType.credential.2x");
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
        CreditInfo creditInfo = client.getCredit("LRC-CREDIT-1");
        assertNotNull(creditInfo);

        try {
            creditInfo = client.getCredit("LRC-CREDIT-1X");
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
        keys.add("LRC-CREDIT-1");
        keys.add("LRC-CREDIT-2");
        keys.add("LRC-CREDIT-1x");
        List<CreditInfo> credits = client.getCreditsByKeyList(keys);
        assertNotNull(credits);
        assertEquals(2, credits.size());

        keys.clear();
        keys.add("LRC-CREDIT-1x");
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
        List<String> creditIds = client.getCreditKeysByCreditType("lcrType.credit.3");
        assertNotNull(creditIds);
        assertEquals(2, creditIds.size());

        try {
            creditIds = client.getCreditKeysByCreditType("lcrType.credit.3x");
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
        CreditTypeInfo creditTypeInfo = client.getCreditType("lcrType.credit.2");
        assertNotNull(creditTypeInfo);

        try {
            creditTypeInfo = client.getCreditType("lcrType.credit.2x");
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
        GradeInfo gradeInfo = client.getGrade("LRC-GRADE-1");
        assertNotNull(gradeInfo);

        try {
            gradeInfo = client.getGrade("LRC-GRADE-1X");
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
        keys.add("LRC-GRADE-1");
        keys.add("LRC-GRADE-2");
        keys.add("LRC-GRADE-1x");
        List<GradeInfo> grades = client.getGradesByKeyList(keys);
        assertNotNull(grades);
        assertEquals(2, grades.size());

        keys.clear();
        keys.add("LRC-GRADE-1x");
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
        List<String> gradeIds = client.getGradeKeysByGradeType("lcrType.grade.3");
        assertNotNull(gradeIds);
        assertEquals(2, gradeIds.size());

        try {
            gradeIds = client.getGradeKeysByGradeType("lcrType.grade.3x");
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
        GradeTypeInfo gradeTypeInfo = client.getGradeType("lcrType.grade.2");
        assertNotNull(gradeTypeInfo);

        try {
            gradeTypeInfo = client.getGradeType("lcrType.grade.2x");
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
        List<GradeInfo> grades = client.getGradesByScale("Scale Key 1");
        assertNotNull(grades);
        assertEquals(2, grades.size());

        try {
            grades = client.getGradesByScale("Scale Key 1x");
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


}
