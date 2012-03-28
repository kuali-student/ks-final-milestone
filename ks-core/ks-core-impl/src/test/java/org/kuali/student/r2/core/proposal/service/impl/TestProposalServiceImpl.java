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

package org.kuali.student.r2.core.proposal.service.impl;

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
import org.kuali.student.r1.common.dto.ReferenceTypeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r1.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;

/**
 * Test the Proposal Service methods
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Daos( { @Dao(value = "org.kuali.student.r1.core.proposal.dao.impl.ProposalDaoImpl",testSqlFile="classpath:ks-proposal.sql"/*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/proposal-persistence.xml")
public class TestProposalServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.r1.core.proposal.service.impl.ProposalServiceImpl", additionalContextFile="classpath:proposal-additional-context.xml")
    public ProposalService client;

    @Test
    public void testSearch() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearchKey("proposal.search.generic");
    	SearchResult result = client.search(searchRequest);
        assertEquals(3,result.getRows().size());
    }

    @Test
    public void testService() {
        assertNotNull(client);
    }

    @Test
    public void getProposalTypes() throws OperationFailedException {
        // TODO KSCM-504
//        List<ProposalTypeInfo> types = client.getProposalTypes();
//        assertNotNull(types);
//        assertEquals(2, types.size());
    }

    @Test
    public void getProposal() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        ProposalInfo proposalInfo = client.getProposal("PROPOSAL-1", contextInfo);
        assertNotNull(proposalInfo);
        assertEquals(1, proposalInfo.getProposerPerson().size());
        assertEquals(4, proposalInfo.getProposalReference().size());
        assertEquals("REFTYPE-1", proposalInfo.getProposalReferenceType());

        try {
            client.getProposal("PROPOSAL-XXX", contextInfo);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposal(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalsByIdList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        ArrayList<String> ids =  new ArrayList<String>();
        ids.add("PROPOSAL-1");
        ids.add("PROPOSAL-2");
        List<ProposalInfo> proposals = client.getProposalsByIdList(ids, contextInfo);
        assertNotNull(proposals);
        assertEquals(ids.size(), proposals.size());

        ids.add("PROPOSAL-XXX");
        try {
            client.getProposalsByIdList(ids, contextInfo);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            ids.clear();
            client.getProposalsByIdList(ids, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByIdList(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalsByProposalType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        List<ProposalInfo> proposals = client.getProposalsByProposalType("proposalType.courseCorrection", contextInfo);
        assertNotNull(proposals);
        assertEquals(2, proposals.size());

        try {
            assertEquals(null,client.getProposalsByProposalType("proposalType.courseCorrection-XXX", contextInfo));
        } catch (DoesNotExistException e) {
            assertTrue(false);
        }

        try {
            client.getProposalsByProposalType(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalsByReference() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        List<ProposalInfo> proposals = client.getProposalsByReference("REFTYPE-1" ,"REMOTEREF-1", contextInfo);
        assertNotNull(proposals);
        assertEquals(2, proposals.size());

        try {
            assertEquals(null,client.getProposalsByReference("REFTYPE-XXX", "REMOTEREF-1", contextInfo));
        } catch (DoesNotExistException e) {
            assertTrue(false);
        }

        try {
        	assertEquals(null, client.getProposalsByReference("REFTYPE-1","REMOTEREF-1XXX", contextInfo));
        } catch (DoesNotExistException e) {
            assertTrue(false);
        }

        try {
            client.getProposalsByReference(null ,"REMOTEREF-1", contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByReference("REFTYPE-1" ,null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getReferenceTypes() throws OperationFailedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        // TODO KSCM-504
//        List<ReferenceTypeInfo> referenceTypes = client.getReferenceTypes();
//        assertNotNull(referenceTypes);
//        assertEquals(1, referenceTypes.size());
    }

    @Test
    public void getProposalsByState() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        List<ProposalInfo> proposalInfos = client.getProposalsByState("active", "proposalType.courseCorrection", contextInfo);
        assertNotNull(proposalInfos);
        assertEquals(2, proposalInfos.size());

        try {
        	assertEquals(null, client.getProposalsByState("activeXXX", "proposalType.courseCorrection", contextInfo));
        } catch (DoesNotExistException e) {
            assertTrue(false);
        }

        try {
        	assertEquals(null, client.getProposalsByState("active", "proposalType.courseCorrectionXXX", contextInfo));
        } catch (DoesNotExistException e) {
            assertTrue(false);
        }

        try {
            client.getProposalsByState(null, "proposalType.courseCorrection", contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByState("active", null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalTypesForReferenceType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        // TODO KSCM-504
//        List<ProposalTypeInfo> proposalTypeInfos = client.getProposalTypesForReferenceType("REFTYPE-1");
//        assertNotNull(proposalTypeInfos);
//        assertEquals(2, proposalTypeInfos.size());
//
//        try {
//        	assertEquals(null, client.getProposalTypesForReferenceType("REFTYPE-XXX"));
//        } catch (DoesNotExistException e) {
//            assertTrue(false);
//        }
//
//        try {
//            client.getProposalTypesForReferenceType(null);
//            assertTrue(false);
//        } catch (MissingParameterException e) {
//            assertTrue(true);
//        }
    }

    @Test
    public void getProposalType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        // TODO KSCM-504
//        ProposalTypeInfo typeInfo = client.getProposalType("proposalType.courseCorrection");
//        assertNotNull(typeInfo);
        
//        try {
//            client.getProposalType("proposalType.courseCorrectionXXX");
//            assertTrue(false);
//        } catch (DoesNotExistException e) {
//            assertTrue(true);
//        }
        
//        try {
//            client.getProposalType(null);
//            assertTrue(false);
//        } catch (MissingParameterException e) {
//            assertTrue(true);
//        }
   }

    @Test
    public void proposalCrud() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        ProposalInfo proposalInfo = setupProposalInfo();

        String id = client.createProposal("proposalType.courseCorrection", proposalInfo, contextInfo).getId();

        ProposalInfo createdProposalInfo = client.getProposal(id, contextInfo);
        assertNotNull(createdProposalInfo);
        checkProposalCrud(proposalInfo, createdProposalInfo);

        List<String> orgs = new ArrayList<String>();
        orgs.add("kuali");
        orgs.add("ubc");
        createdProposalInfo.setProposerOrg(orgs);
         try {
             client.updateProposal(id, createdProposalInfo, contextInfo);
             assertTrue(false); // Can't have both person and org proposers
         } catch (InvalidParameterException e) {
             assertTrue(true);
         } catch (VersionMismatchException e) {
             assertTrue(false);
         }
         createdProposalInfo.setProposerPerson(null);
         try {
             client.updateProposal(id, createdProposalInfo, contextInfo);
             ProposalInfo updatedProposalInfo = client.getProposal(id, contextInfo);
             checkProposalCrud(createdProposalInfo, updatedProposalInfo);

             List<String> proposalReferences = new ArrayList<String>();
             proposalReferences.add("doc-2");

             updatedProposalInfo.setProposalReference(proposalReferences);
             client.updateProposal(id, updatedProposalInfo, contextInfo);
             ProposalInfo updatedProposalInfo2 = client.getProposal(id, contextInfo);
             checkProposalCrud(updatedProposalInfo, updatedProposalInfo2);
         } catch (InvalidParameterException e) {
             assertTrue(false);
         } catch (VersionMismatchException e) {
             assertTrue(false);
         }

         try {
             StatusInfo status = client.deleteProposal(id, contextInfo);
             assertTrue(status.getIsSuccess());
         } catch (DependentObjectsExistException e) {
             assertTrue(false);
         }

         try {
             StatusInfo status = client.deleteProposal(id, contextInfo);
             assertFalse(status.getIsSuccess());
         } catch (DependentObjectsExistException e) {
             assertTrue(false);
         }
         try {
             client.getProposal(id, contextInfo);
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
            RichTextInfo detailDesc = new RichTextInfo();
            detailDesc.setPlain("detail desc");
            proposalInfo.setDetailDesc(detailDesc);
            proposalInfo.setWorkflowId("kewDocumentId");
            Date effectiveDate = new Date();
            proposalInfo.setEffectiveDate(effectiveDate);
            Date expirationDate = new Date();
            proposalInfo.setExpirationDate(expirationDate);
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("key", "value");
            //proposalInfo.setAttributes(attributes);
            proposalInfo.setTypeKey("proposalType.courseCorrection");
            proposalInfo.setStateKey("active");
         return proposalInfo;
     }

     private static void checkProposalCrud(ProposalInfo master, ProposalInfo validate) {
         if (master.getId() != null) {
             assertEquals(master.getId(), validate.getId());
         }
         assertEquals(master.getWorkflowId(), validate.getWorkflowId());
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

         assertEquals(master.getTypeKey(), validate.getTypeKey());
         assertEquals(master.getStateKey(), validate.getStateKey());
     }

}
