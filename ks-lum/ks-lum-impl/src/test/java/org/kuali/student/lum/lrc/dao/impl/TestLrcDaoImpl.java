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

package org.kuali.student.lum.lrc.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.lum.lrc.dao.LrcDao;
import org.kuali.student.lum.lrc.entity.ResultComponentType;

@PersistenceFileLocation("classpath:META-INF/lrc-persistence.xml")
public class TestLrcDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lrc.dao.impl.LrcDaoImpl", testSqlFile = "classpath:ks-lrc.sql")
	public LrcDao dao;

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
