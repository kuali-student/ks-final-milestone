/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.proposal.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.service.ProposalService;

/**
 * Test the Proposal Service methods
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Daos( { @Dao(value = "org.kuali.student.core.proposal.dao.impl.ProposalDaoImpl",testSqlFile="classpath:ks-proposal.sql"/*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/proposal-persistence.xml")
public class TestProposalServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.core.proposal.service.impl.ProposalServiceImpl", port = "8181",additionalContextFile="classpath:proposal-additional-context.xml")
    public ProposalService client;

    @Test
    public void testService() {
        assertNotNull(client);
    }

    @Test
    public void getProposalTypes() throws OperationFailedException {
        List<ProposalTypeInfo> types = client.getProposalTypes();
        assertNotNull(types);
        assertEquals(2, types.size());

    }
}
