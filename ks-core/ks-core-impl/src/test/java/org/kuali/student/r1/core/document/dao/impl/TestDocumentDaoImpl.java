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

package org.kuali.student.r1.core.document.dao.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r1.core.document.dao.DocumentDao;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@PersistenceFileLocation("classpath:META-INF/document-persistence.xml")
public class TestDocumentDaoImpl extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.r1.core.document.dao.impl.DocumentDaoImpl", testSqlFile = "classpath:ks-document.sql")
    public DocumentDao dao;

    @Test
    public void testDao() {
        assertNotNull(dao);
    }
}
