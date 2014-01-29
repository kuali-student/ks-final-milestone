/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r1.core.proposal.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.AttributeHelper;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

/**
 * Test the Proposal Service methods
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Daos({
    @Dao(value = "org.kuali.student.r1.core.proposal.dao.impl.ProposalDaoImpl", testSqlFile = "classpath:ks-proposal.sql"/*, testDataFile = "classpath:test-beans.xml"*/)})
@PersistenceFileLocation("classpath:META-INF/proposal-persistence.xml")
public class TestProposalServiceImpl extends AbstractServiceTest {

    @Client(value = "org.kuali.student.r1.core.proposal.service.impl.ProposalServiceImpl", additionalContextFile = "classpath:proposal-additional-context.xml")
    public ProposalService client;
    private ContextInfo context = new ContextInfo();

    @Test
    public void testSearch() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("proposal.search.generic");
        SearchResultInfo result = client.search(searchRequest, context);
        assertEquals(3, result.getRows().size());
    }

    @Test
    public void testService() {
        assertNotNull(client);
    }

//    @Test
//    public void getProposalTypes() throws OperationFailedException {
//        List<TypeInfo> types = client.getProposalTypes();
//        assertNotNull(types);
//        assertEquals(2, types.size());
//    }
    @Test
    public void getProposal() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ProposalInfo proposalInfo = client.getProposal("PROPOSAL-1", ContextUtils.getContextInfo());
        assertNotNull(proposalInfo);
        assertEquals(1, proposalInfo.getProposerPerson().size());
        assertEquals(4, proposalInfo.getProposalReference().size());
        assertEquals("REFTYPE-1", proposalInfo.getProposalReferenceType());

        try {
            client.getProposal("PROPOSAL-XXX", ContextUtils.getContextInfo());
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            client.getProposal(null, ContextUtils.getContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalsByIdList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add("PROPOSAL-1");
        ids.add("PROPOSAL-2");
        List<ProposalInfo> proposals = client.getProposalsByIds(ids, ContextUtils.getContextInfo());
        assertNotNull(proposals);
        assertEquals(ids.size(), proposals.size());

        ids.add("PROPOSAL-XXX");
        try {
            client.getProposalsByIds(ids, ContextUtils.getContextInfo());
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            ids.clear();
            client.getProposalsByIds(ids, ContextUtils.getContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByIds(null, ContextUtils.getContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalsByProposalType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ProposalInfo> proposals = client.getProposalsByProposalType("proposalType.courseCorrection", ContextUtils.getContextInfo());
        assertNotNull(proposals);
        assertEquals(2, proposals.size());

        try {
            client.getProposalsByProposalType(null, ContextUtils.getContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getProposalsByReference() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ProposalInfo> proposals = client.getProposalsByReference("REFTYPE-1", "REMOTEREF-1", ContextUtils.getContextInfo());
        assertNotNull(proposals);
        assertEquals(2, proposals.size());

        try {
            assertEquals(null, client.getProposalsByReference("REFTYPE-XXX", "REMOTEREF-1", ContextUtils.getContextInfo()));
        } catch (DoesNotExistException e) {
            assertTrue(false);
        }

        try {
            assertEquals(null, client.getProposalsByReference("REFTYPE-1", "REMOTEREF-1XXX", ContextUtils.getContextInfo()));
        } catch (DoesNotExistException e) {
            assertTrue(false);
        }

        try {
            client.getProposalsByReference(null, "REMOTEREF-1", ContextUtils.getContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByReference("REFTYPE-1", null, ContextUtils.getContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }
//
//    @Test
//    public void getReferenceTypes() throws OperationFailedException {
//        List<TypeInfo> referenceTypes = client.getReferenceTypes();
//        assertNotNull(referenceTypes);
//        assertEquals(1, referenceTypes.size());
//    }

    @Test
    public void getProposalsByState() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ProposalInfo> proposalInfos = client.getProposalsByState("active", "proposalType.courseCorrection", ContextUtils.getContextInfo());
        assertNotNull(proposalInfos);
        assertEquals(2, proposalInfos.size());

        try {
            assertEquals(null, client.getProposalsByState("activeXXX", "proposalType.courseCorrection", ContextUtils.getContextInfo()));
        } catch (DoesNotExistException e) {
            assertTrue(false);
        }

        try {
            assertEquals(null, client.getProposalsByState("active", "proposalType.courseCorrectionXXX", ContextUtils.getContextInfo()));
        } catch (DoesNotExistException e) {
            assertTrue(false);
        }

        try {
            client.getProposalsByState(null, "proposalType.courseCorrection", ContextUtils.getContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            client.getProposalsByState("active", null, ContextUtils.getContextInfo());
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

//    @Test
//    public void getProposalTypesForReferenceType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
//        List<TypeInfo> proposalTypeInfos = client.getProposalTypesForReferenceType("REFTYPE-1", ContextUtils.getContextInfo());
//        assertNotNull(proposalTypeInfos);
//        assertEquals(2, proposalTypeInfos.size());
//
//        try {
//        	assertEquals(null, client.getProposalTypesForReferenceType("REFTYPE-XXX", ContextUtils.getContextInfo()));
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
//    }
//    @Test
//    public void getProposalType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
//        TypeInfo typeInfo = client.getProposalType("proposalType.courseCorrection");
//        assertNotNull(typeInfo);
//        try {
//            client.getProposalType("proposalType.courseCorrectionXXX");
//            assertTrue(false);
//        } catch (DoesNotExistException e) {
//            assertTrue(true);
//        }
//
//        try {
//            client.getProposalType(null);
//            assertTrue(false);
//        } catch (MissingParameterException e) {
//            assertTrue(true);
//        }
//   }
    @Test
    public void proposalCrud()
            throws AlreadyExistsException,
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        ProposalInfo proposalInfo = setupProposalInfo();

        ProposalInfo createdProposalInfo = client.createProposal("proposalType.courseCorrection", proposalInfo, ContextUtils.getContextInfo());
        String id = createdProposalInfo.getId();

        createdProposalInfo = client.getProposal(id, ContextUtils.getContextInfo());
        assertNotNull(createdProposalInfo);
        checkProposalCrud(proposalInfo, createdProposalInfo);

        List<String> orgs = new ArrayList<String>();
        orgs.add("kuali");
        orgs.add("ubc");
        createdProposalInfo.setProposerOrg(orgs);
        try {
           client.updateProposal(id, createdProposalInfo, ContextUtils.getContextInfo());
            assertTrue(false); // Can't have both person and org proposers
        } catch (InvalidParameterException e) {
            assertTrue(true);
        } catch (VersionMismatchException e) {
            assertTrue(false);
        }
        createdProposalInfo.setProposerPerson(null);
        Map<String,String> map = new HashMap<String,String> ();
        map.put ("key", "Differentvalue");
        map.put ("key2", "value2");
        map.put ("key3", "value3");
        new AttributeHelper (createdProposalInfo.getAttributes()).putAll(map);
        try {
            ProposalInfo updatedProposalInfo = client.updateProposal(id, createdProposalInfo, ContextUtils.getContextInfo());
            checkProposalCrud(createdProposalInfo, updatedProposalInfo);
            ProposalInfo fetchedProposalInfo = client.getProposal(id, ContextUtils.getContextInfo());
            checkProposalCrud(createdProposalInfo, fetchedProposalInfo);

            List<String> proposalReferences = new ArrayList<String>();
            proposalReferences.add("doc-2");

            fetchedProposalInfo.setProposalReference(proposalReferences);
            client.updateProposal(id, fetchedProposalInfo, ContextUtils.getContextInfo());
            ProposalInfo updatedProposalInfo2 = client.getProposal(id, ContextUtils.getContextInfo());
            checkProposalCrud(fetchedProposalInfo, updatedProposalInfo2);
        } catch (InvalidParameterException e) {
            assertTrue(false);
        } catch (VersionMismatchException e) {
            assertTrue(false);
        }

        try {
            StatusInfo status = client.deleteProposal(id, ContextUtils.getContextInfo());
            assertTrue(status.getIsSuccess());
        } catch (DependentObjectsExistException e) {
            assertTrue(false);
        }

        try {
            StatusInfo status = client.deleteProposal(id, ContextUtils.getContextInfo());
            assertFalse(status.getIsSuccess());
        } catch (DependentObjectsExistException e) {
            assertTrue(false);
        }
        try {
            client.getProposal(id, ContextUtils.getContextInfo());
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
        proposalInfo.setRationale(new RichTextHelper ().fromPlain("rationale"));
        proposalInfo.setDescr(new RichTextHelper ().fromPlain ("detail desc"));
        proposalInfo.setWorkflowId("kewDocumentId");
        Date effectiveDate = new Date();
        proposalInfo.setEffectiveDate(effectiveDate);
        Date expirationDate = new Date();
        proposalInfo.setExpirationDate(expirationDate);
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("key", "value");
        new AttributeHelper (proposalInfo.getAttributes()).putAll(attributes);
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
        check(master.getRationale(), validate.getRationale());
        check(master.getDescr(), validate.getDescr());
        assertEquals(master.getEffectiveDate(), validate.getEffectiveDate());
        assertEquals(master.getExpirationDate(), validate.getExpirationDate());
        check(master.getAttributes(), validate.getAttributes());

        assertEquals(master.getTypeKey(), validate.getTypeKey());
        assertEquals(master.getStateKey(), validate.getStateKey());
    }
    
    
    private static void check(RichTextInfo expected, RichTextInfo actual) {
        if (expected == null) {
            if (actual == null) {
                return;
            } else {
                if (actual.getPlain() == null && actual.getFormatted() == null) {
                    fail("expected null but found an empty structure " + actual);
                    return;
                }
                fail("expected null but found not null " + actual);
                return;
            }
        } else {
            if (actual == null) {
                if (expected.getPlain() == null && expected.getFormatted() == null) {
                    fail("expected empty structure but found null ");
                    return;
                }
                fail("expected not null but found null " + expected);
                return;
            } else {
                assertEquals(expected.getPlain(), actual.getPlain());
                assertEquals(expected.getFormatted(), actual.getFormatted());
            }
        }
    }
    
    
    
    public static void check(List<AttributeInfo> expectedList, List<AttributeInfo> actualList) {
        if (expectedList.size() != actualList.size()) {
            dump(expectedList, actualList);
        }
        assertEquals(expectedList.size(), actualList.size());
        List<AttributeInfo> expectedSorted = new ArrayList(expectedList);
        Collections.sort(expectedSorted, new AttributeInfoComparator());
        List<AttributeInfo> actualSorted = new ArrayList(actualList);
        Collections.sort(actualSorted, new AttributeInfoComparator());
        for (int i = 0; i < expectedSorted.size(); i++) {
            AttributeInfo expected = expectedSorted.get(i);
            AttributeInfo actual = actualSorted.get(i);
            if (expected.getId() != null) {
                assertEquals(i + "", expected.getId(), actual.getId());
            }
            assertEquals(i + "", expected.getKey(), actual.getKey());
            assertEquals(i + "", expected.getValue(), actual.getValue());
        }
    }

    private static void dump(List<AttributeInfo> expectedList, List<AttributeInfo> actualList) {
        System.out.println("Expected List");
        dump(expectedList);
        System.out.println("Actual List");
        dump(actualList);
    }

    private static void dump(List<AttributeInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            AttributeInfo expected = list.get(i);
            System.out.println(i + ".) " + expected.getKey() + "=" + expected.getValue() + "\t" + expected.getId());
        }
    }

    private static class AttributeInfoComparator implements Comparator<AttributeInfo> {

        @Override
        public int compare(AttributeInfo o1, AttributeInfo o2) {
            return calcSortKey(o1).compareTo(calcSortKey(o2));
        }

        private String calcSortKey(AttributeInfo attr) {
            StringBuilder sb = new StringBuilder();
            sb.append(attr.getKey());
            sb.append("\t");
            if (attr.getId() != null) {
                sb.append(attr.getId());
            }
            sb.append("\t");
            if (attr.getValue() != null) {
                sb.append(attr.getValue());
            }
            return sb.toString();
        }
    }
}
