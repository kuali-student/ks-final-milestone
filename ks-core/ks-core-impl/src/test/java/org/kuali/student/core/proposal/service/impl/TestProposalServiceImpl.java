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
package org.kuali.student.core.proposal.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.kuali.student.core.dto.ReferenceTypeInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.core.proposal.dto.ProposalDocRelationTypeInfo;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;

/**
 * Test the Proposal Service methods
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Daos( { @Dao(value = "org.kuali.student.core.proposal.dao.impl.ProposalDaoImpl",testSqlFile="classpath:ks-proposal.sql"/*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/proposal-persistence.xml")
public class TestProposalServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.core.proposal.service.impl.ProposalServiceImpl", additionalContextFile="classpath:proposal-additional-context.xml")
    public ProposalService client;

    @Test
    public void testSearch() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearchKey("proposal.search.courses");
    	SearchResult result = client.search(searchRequest);
        assertEquals(3,result.getRows().size());
    }

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
        assertEquals("REFTYPE-1", proposalInfo.getProposalReferenceType());

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
    public void getProposalDocRelationsByDocument() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
    {
        List<ProposalDocRelationInfo> docRelationInfos = client.getProposalDocRelationsByDocument("DOC-ID-1");
        assertNotNull(docRelationInfos);

        try {
            client.getProposalDocRelation("DOC-ID-XXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalDocRelationsByIdList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
    {
        ArrayList<String> ids =  new ArrayList<String>();
        ids.add("DOCREL-1");
        ids.add("DOCREL-2");
        List<ProposalDocRelationInfo> docRelationInfos = client.getProposalDocRelationsByIdList(ids);
        assertNotNull(docRelationInfos);

        try {
            ArrayList<String> idsMock =  new ArrayList<String>();
            idsMock.add("DOCREL-XX");
            idsMock.add("DOCREL-XX");
            client.getProposalDocRelationsByIdList(idsMock);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalDocRelationsByProposal() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
    {
        List<ProposalDocRelationInfo> docRelationInfos = client.getProposalDocRelationsByProposal("PROPOSAL-1");
        assertNotNull(docRelationInfos);

        try {
            client.getProposalDocRelationsByProposal("PROPOSAL-XXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalDocRelationsByType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
    {
        List<ProposalDocRelationInfo> docRelationInfos = client.getProposalDocRelationsByType("PROP-DOCREL-TYPE1");
        assertNotNull(docRelationInfos);

        try {
            client.getProposalDocRelationsByType("PROP-DOCREL-TYPEXXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
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
        if (true) {
        List<ReferenceTypeInfo> referenceTypes = client.getReferenceTypes();
        assertNotNull(referenceTypes);
        assertEquals(1, referenceTypes.size());
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

    @Test
    public void proposalCrud() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ProposalInfo proposalInfo = setupProposalInfo();

        String id = client.createProposal("proposalType.courseCorrection", proposalInfo).getId();

        ProposalInfo createdProposalInfo = client.getProposal(id);
        assertNotNull(createdProposalInfo);
        checkProposalCrud(proposalInfo, createdProposalInfo);

        List<String> orgs = new ArrayList<String>();
        orgs.add("kuali");
        orgs.add("ubc");
        createdProposalInfo.setProposerOrg(orgs);
         try {
             client.updateProposal(id, createdProposalInfo);
             assertTrue(false); // Can't have both person and org proposers
         } catch (InvalidParameterException e) {
             assertTrue(true);
         } catch (VersionMismatchException e) {
             assertTrue(false);
         }
         createdProposalInfo.setProposerPerson(null);
         try {
             client.updateProposal(id, createdProposalInfo);
             ProposalInfo updatedProposalInfo = client.getProposal(id);
             checkProposalCrud(createdProposalInfo, updatedProposalInfo);

             List<String> proposalReferences = new ArrayList<String>();
             proposalReferences.add("doc-2");

             updatedProposalInfo.setProposalReference(proposalReferences);
             client.updateProposal(id, updatedProposalInfo);
             ProposalInfo updatedProposalInfo2 = client.getProposal(id);
             checkProposalCrud(updatedProposalInfo, updatedProposalInfo2);
         } catch (InvalidParameterException e) {
             assertTrue(false);
         } catch (VersionMismatchException e) {
             assertTrue(false);
         }

         try {
             StatusInfo status = client.deleteProposal(id);
             assertTrue(status.getSuccess());
         } catch (DependentObjectsExistException e) {
             assertTrue(false);
         }

         try {
             StatusInfo status = client.deleteProposal(id);
             assertFalse(status.getSuccess());
         } catch (DependentObjectsExistException e) {
             assertTrue(false);
         }
         try {
             client.getProposal(id);
             assertTrue(false);
         } catch (DoesNotExistException e) {
             assertTrue(true);
         }

    }

     private ProposalInfo setupProposalInfo() {
         ProposalInfo proposalInfo = new ProposalInfo();
            proposalInfo.setName("name");

            List<String> proposerPersons = new ArrayList<String>();
            proposerPersons.add("gnl");
            proposerPersons.add("david");
            proposalInfo.setProposerPerson(proposerPersons);

            List<String> proposalReferences = new ArrayList<String>();
            proposalReferences.add("doc-1");
            proposalInfo.setProposalReference(proposalReferences);
            proposalInfo.setProposalReferenceType("REFTYPE-1");
            proposalInfo.setRationale("rationale");
            proposalInfo.setDetailDesc("detail desc");
            Date effectiveDate = new Date();
            proposalInfo.setEffectiveDate(effectiveDate);
            Date expirationDate = new Date();
            proposalInfo.setExpirationDate(expirationDate);
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("key", "value");
            proposalInfo.setAttributes(attributes);
            proposalInfo.setType("proposalType.courseCorrection");
            proposalInfo.setState("active");
         return proposalInfo;
     }

     private static void checkProposalCrud(ProposalInfo master, ProposalInfo validate) {
         if (master.getId() != null) {
             assertEquals(master.getId(), validate.getId());
         }
         assertEquals(master.getName(), validate.getName());
         assertEquals(master.getProposerPerson(), validate.getProposerPerson());
         assertEquals(master.getProposerOrg(), validate.getProposerOrg());
         assertEquals(master.getProposalReferenceType(), validate.getProposalReferenceType());
         assertEquals(master.getProposalReference(), validate.getProposalReference());
         assertEquals(master.getRationale(), validate.getRationale());
         assertEquals(master.getDetailDesc(), validate.getDetailDesc());
         assertEquals(master.getEffectiveDate(), validate.getEffectiveDate());
         assertEquals(master.getExpirationDate(), validate.getExpirationDate());
         assertEquals(master.getAttributes(), validate.getAttributes());

         assertEquals(master.getType(), validate.getType());
         assertEquals(master.getState(), validate.getState());
     }

    @Test
    public void proposalDocCrud() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ProposalDocRelationInfo docInfo = new ProposalDocRelationInfo();
        ProposalInfo proposalInfo = setupProposalInfo();
        docInfo.setTitle("doc title");
        RichTextInfo desc = new RichTextInfo();
        desc.setFormatted("<p>doc rel desc</p");
        desc.setPlain("doc rel desc");
        docInfo.setDesc(desc);
        docInfo.setEffectiveDate(new Date());
        docInfo.setExpirationDate(new Date());
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("key", "value");
        docInfo.setAttributes(attributes);
        docInfo.setType("PROP-DOCREL-TYPE1");
        docInfo.setState("active");

        String proposalId = client.createProposal("proposalType.courseCorrection", proposalInfo).getId();

        String id = client.createProposalDocRelation("PROP-DOCREL-TYPE1", "DOC-1", proposalId, docInfo).getId();

        ProposalDocRelationInfo createdDocRelInfo = client.getProposalDocRelation(id);
        assertEquals(id, createdDocRelInfo.getId());
        checkProposalDocRelationCrud(docInfo, createdDocRelInfo);
        createdDocRelInfo.setState("inactive");
        try {
            client.updateProposalDocRelation(id, createdDocRelInfo);
            assertTrue(true);
        } catch (VersionMismatchException e) {
            assertTrue(false);
        }
        try {
            client.updateProposalDocRelation("xxx", createdDocRelInfo);
            assertTrue(false);
        } catch (DoesNotExistException e1) {
            assertTrue(true);
        } catch (VersionMismatchException e) {
            assertTrue(false);
        }
        ProposalDocRelationInfo createdDocRelInfo2 = client.getProposalDocRelation(id);
        checkProposalDocRelationCrud(createdDocRelInfo, createdDocRelInfo2);

        StatusInfo status = client.deleteProposalDocRelation(id);
        assertTrue(status.getSuccess());

        status = client.deleteProposalDocRelation(id);
        assertFalse(status.getSuccess());

        try {
           client.getProposalDocRelation(id);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    private void checkProposalDocRelationCrud(ProposalDocRelationInfo master, ProposalDocRelationInfo validate) {
        if (master.getId() != null) {
            assertEquals(master.getId(), validate.getId());
        }
        if (master.getProposalId() != null) {
            assertEquals(master.getProposalId(), validate.getProposalId());
        }
        if (master.getDocumentId() != null) {
            assertEquals(master.getDocumentId(), validate.getDocumentId());
        }
        assertEquals(master.getTitle(), validate.getTitle());
        RichTextInfo masterDesc = master.getDesc();
        RichTextInfo validateDesc = validate.getDesc();
        assertEquals(masterDesc.getPlain(), validateDesc.getPlain());
        assertEquals(masterDesc.getFormatted(), validateDesc.getFormatted());
        assertEquals(master.getEffectiveDate(), validate.getEffectiveDate());
        assertEquals(master.getExpirationDate(), validate.getExpirationDate());
        assertEquals(master.getAttributes(), validate.getAttributes());
        assertEquals(master.getType(), validate.getType());
        assertEquals(master.getState(), validate.getState());
    }

    @Test
    public void getProposalDocRelationType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ProposalDocRelationTypeInfo docRelInfo = client.getProposalDocRelationType("PROP-DOCREL-TYPE1");
        assertNotNull(docRelInfo);

        try {
            client.getProposalDocRelationType("PROP-DOCREL-TYPEXXX");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposalDocRelationType(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalDocRelationTypes() throws OperationFailedException {
        List<ProposalDocRelationTypeInfo> types = client.getProposalDocRelationTypes();
        assertNotNull(types);
        assertEquals(1, types.size());
    }

    @Test
    public void getAllowedProposalDocRelationTypesForProposalType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> proposalDocRelationTypeKeyList = client.getAllowedProposalDocRelationTypesForProposalType("proposalType.courseCorrection");
        assertNotNull(proposalDocRelationTypeKeyList);
        assertEquals(1,proposalDocRelationTypeKeyList.size());
        try {
            proposalDocRelationTypeKeyList = client.getAllowedProposalDocRelationTypesForProposalType("proposalType.newCourse");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }
}
