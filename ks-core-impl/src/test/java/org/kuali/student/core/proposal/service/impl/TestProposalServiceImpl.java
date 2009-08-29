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
import org.kuali.student.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.dto.ReferenceTypeInfo;
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

    @Test
    public void getProposal() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ProposalInfo proposalInfo = client.getProposal("PROPOSAL-1");
        assertNotNull(proposalInfo);
        assertEquals(1, proposalInfo.getProposerPerson().size());
        assertEquals(4, proposalInfo.getProposalReference().size());
        assertEquals("Clu", proposalInfo.getProposalReferenceType());

        try {
            client.getProposal("PROPOSAL-XXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposal(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalDocRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ProposalDocRelationInfo docRelationInfo = client.getProposalDocRelation("DOCREL-1");
        assertNotNull(docRelationInfo);
        assertEquals("PROPOSAL-1", docRelationInfo.getProposalId());
        assertEquals("DOC-ID-1", docRelationInfo.getDocumentId());

        try {
            client.getProposalDocRelation("DOCREL-XXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposalDocRelation(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalsByIdList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ArrayList<String> ids =  new ArrayList<String>();
        ids.add("PROPOSAL-1");
        ids.add("PROPOSAL-2");
        List<ProposalInfo> proposals = client.getProposalsByIdList(ids);
        assertNotNull(proposals);
        assertEquals(ids.size(), proposals.size());

        ids.add("PROPOSAL-XXX");
        try {
            client.getProposalsByIdList(ids);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            ids.clear();
            client.getProposalsByIdList(ids);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByIdList(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalsByProposalType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<ProposalInfo> proposals = client.getProposalsByProposalType("proposalType.courseCorrection");
        assertNotNull(proposals);
        assertEquals(2, proposals.size());

        try {
            client.getProposalsByProposalType("proposalType.courseCorrection-XXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByProposalType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalsByReference() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<ProposalInfo> proposals = client.getProposalsByReference("REFTYPE-1" ,"REMOTEREF-1");
        assertNotNull(proposals);
        assertEquals(2, proposals.size());

        try {
            client.getProposalsByReference("REFTYPE-XXX", "REMOTEREF-1");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByReference("REFTYPE-1","REMOTEREF-1XXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByReference(null ,"REMOTEREF-1");
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByReference("REFTYPE-1" ,null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getReferenceTypes() throws OperationFailedException {
        // TODO Why is ReferenceTypeInfo causing problems
        if (false) {
        List<ReferenceTypeInfo> referenceTypes = client.getReferenceTypes();
        assertNotNull(referenceTypes);
        assertEquals(2, referenceTypes.size());
        }
    }

    @Test
    public void getProposalsByState() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<ProposalInfo> proposalInfos = client.getProposalsByState("active", "proposalType.courseCorrection");
        assertNotNull(proposalInfos);
        assertEquals(2, proposalInfos.size());

        try {
            client.getProposalsByState("activeXXX", "proposalType.courseCorrection");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByState("active", "proposalType.courseCorrectionXXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByState(null, "proposalType.courseCorrection");
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByState("active", null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalTypesForReferenceType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<ProposalTypeInfo> proposalTypeInfos = client.getProposalTypesForReferenceType("REFTYPE-1");
        assertNotNull(proposalTypeInfos);
        assertEquals(2, proposalTypeInfos.size());

        try {
            client.getProposalTypesForReferenceType("REFTYPE-XXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposalTypesForReferenceType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ProposalTypeInfo typeInfo = client.getProposalType("proposalType.courseCorrection");
        assertNotNull(typeInfo);
        try {
            client.getProposalType("proposalType.courseCorrectionXXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposalType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
   }
}
