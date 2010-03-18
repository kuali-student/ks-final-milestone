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
package org.kuali.student.lum.lrc.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lrc.dao.LrcDao;
import org.kuali.student.lum.lrc.entity.Credential;
import org.kuali.student.lum.lrc.entity.Credit;
import org.kuali.student.lum.lrc.entity.Grade;
import org.kuali.student.lum.lrc.entity.ResultComponentType;

import edu.emory.mathcs.backport.java.util.Collections;

@PersistenceFileLocation("classpath:META-INF/lrc-persistence.xml")
public class TestLrcDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lrc.dao.impl.LrcDaoImpl", testSqlFile = "classpath:ks-lrc.sql")
	public LrcDao dao;

	@Test
    public void testGetCredentialsByIdList() {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add("LRC-RESULT_VALUE-CREDENTIAL-1");
        ids.add("LRC-RESULT_VALUE-CREDENTIAL-2");
        List<Credential> credentials = dao.getCredentialsByIdList(ids);
		Collections.sort(credentials, new Comparator<Credential>() {
			public int compare(Credential o1, Credential o2) {
				return o1.getId().compareTo(o2.getId());
			}});
        assertEquals("LRC-RESULT_VALUE-CREDENTIAL-1", credentials.get(0).getId());

        ids.clear();
        ids.add("LRC-RESULT_VALUE-CREDENTIAL-1x");
        credentials = dao.getCredentialsByIdList(ids);
        assertTrue(credentials.isEmpty());

    }

    @Test
    public void testGetCredentialIdsByCredentialType() {
        List<String> credentialIds = dao.getCredentialIdsByCredentialType("lrcType.credential.3");
        assertNotNull(credentialIds);
        assertEquals(2, credentialIds.size());

        credentialIds = dao.getCredentialIdsByCredentialType("lrcType.credential.3x");
        assertTrue(credentialIds.isEmpty());
    }

    @Test
    public void testGetCreditsByIdList() {
        ArrayList<String> keys = new ArrayList<String>();
        keys.add("LRC-RESULT_VALUE-CREDIT-1");
        keys.add("LRC-RESULT_VALUE-CREDIT-2");
        List<Credit> credits = dao.getCreditsByIdList(keys);
        assertNotNull(credits);
        assertEquals(2, credits.size());

        keys.clear();
        keys.add("LRC-RESULT_VALUE-CREDIT-1x");
        credits = dao.getCreditsByIdList(keys);
        assertTrue(credits.isEmpty());
    }

    @Test
    public void testGetCreditIdsByCreditType() {
        List<String> creditIds = dao.getCreditIdsByCreditType("lrcType.credit.3");
        assertNotNull(creditIds);
        assertEquals(2, creditIds.size());

        creditIds = dao.getCreditIdsByCreditType("lrcType.credit.3x");
        assertTrue(creditIds.isEmpty());
    }

    @Test
    public void testGetGradesByIdList() {
        ArrayList<String> keys = new ArrayList<String>();
        keys.add("LRC-RESULT_VALUE-GRADE-1");
        keys.add("LRC-RESULT_VALUE-GRADE-2");
        List<Grade> grades = dao.getGradesByIdList(keys);
        assertNotNull(grades);
        assertEquals(2, grades.size());

        keys.clear();
        keys.add("LRC-RESULT_VALUE-GRADE-1x");
        grades = dao.getGradesByIdList(keys);
        assertTrue(grades.isEmpty());
    }

    @Test
    public void testGetGradeIdsByGradeType() {
        List<String> gradeIds = dao.getGradeIdsByGradeType("lrcType.grade.3");
        assertNotNull(gradeIds);
        assertEquals(2, gradeIds.size());

        gradeIds = dao.getGradeIdsByGradeType("lrcType.grade.3x");
        assertTrue(gradeIds.isEmpty());
    }

    @Test
    public void testGetGradesByScale() {
        List<Grade> grades = dao.getGradesByScale("LRC-SCALE-1");
        assertNotNull(grades);
        assertEquals(2, grades.size());

        grades = dao.getGradesByScale("LRC-SCALE-1x");
        assertTrue(grades.isEmpty());

    }

    @Test
    public void testGetResultComponentIdsByResult() {
        List<String> rcis = dao.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1", "resultComponentType.credential");
        assertNotNull(rcis);
        assertEquals(1, rcis.size());

        rcis = dao.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1x", "resultComponentType.credential");
        assertTrue(rcis.isEmpty());

        rcis = dao.getResultComponentIdsByResult("LRC-RESULT_VALUE-CREDIT-1", "resultComponentType.credentialx");
        assertTrue(rcis.isEmpty());
    }

    @Test
    public void testGetResultComponentIdsByResultComponentType() {
        List<String> rcis = dao.getResultComponentIdsByResultComponentType("resultComponentType.credential");
        assertNotNull(rcis);
        assertEquals(1, rcis.size());

        rcis = dao.getResultComponentIdsByResultComponentType("resultComponentType.credentialx");
        assertTrue(rcis.isEmpty());
    }

    @Test
    public void testGetResultComponentType() {
        try {
            @SuppressWarnings("unused")
            ResultComponentType rcti = dao.getResultComponentType("resultComponentType.credential");
            assertTrue(true);
        } catch (DoesNotExistException e) {
            assertTrue(false);
        }

        try {
            @SuppressWarnings("unused")
            ResultComponentType rcti = dao.getResultComponentType("resultComponentType.credentialYYY");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

    }
}
