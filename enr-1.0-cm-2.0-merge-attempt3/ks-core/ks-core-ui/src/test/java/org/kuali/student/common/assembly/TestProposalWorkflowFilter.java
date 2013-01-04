package org.kuali.student.common.assembly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.core.assembly.data.MockProposal;
import org.kuali.student.core.assembly.transform.ProposalWorkflowFilter;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.transform.DataBeanMapper;
import org.kuali.student.r1.common.assembly.transform.DefaultDataBeanMapper;
import org.kuali.student.r1.common.assembly.transform.DocumentTypeConfiguration;
import org.kuali.student.r2.common.dto.AttributeInfo;

public class TestProposalWorkflowFilter {

    @Test
    public void testDocumentContentConfiguration() throws Exception {
        MockProposal proposal = new MockProposal();
        proposal.setId("123456789");
        proposal.setOrgId(Arrays.asList("testOrg"));
        proposal.setProposalRationale("Testing a proposal");
        proposal.setProposalTitle("Test Proposal");
        AttributeInfo attribute = new AttributeInfo("user", "test");
        List<AttributeInfo> attributeList = new ArrayList<AttributeInfo>();
        attributeList.add(attribute);
        proposal.setAttributes(attributeList);

        ProposalWorkflowFilter workflowFilter = new ProposalWorkflowFilter();
        Map<String, String> docFieldPaths = new HashMap<String, String>();
        docFieldPaths.put("proposalId", "id");
        docFieldPaths.put("primaryOrg", "orgId[0]");
        docFieldPaths.put("secondaryOrg", "orgId[1]"); //Non-existant secondary org
        docFieldPaths.put("userId", "user");
        docFieldPaths.put("foo", "foo"); //Non-existant property

        DocumentTypeConfiguration docTypeConfig = new DocumentTypeConfiguration();
        docTypeConfig.setDefaultDocumentTitle("Proposal Name: {proposalTitle}");
        docTypeConfig.setDocContentFieldMap(docFieldPaths);
        docTypeConfig.setDocumentType("testDocType");

        workflowFilter.setDefaultDocType("testDocType");

        DataBeanMapper mapper = new DefaultDataBeanMapper();

        Data data = mapper.convertFromBean(proposal, null);
        assertEquals("Proposal Name: Test Proposal", workflowFilter.getDefaultDocumentTitle(docTypeConfig, data));

        String docContent = workflowFilter.getDocumentContent(data, docTypeConfig);
        assertTrue(docContent.contains("<proposalId>123456789</proposalId>"));
    }
}
