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

package org.kuali.student.common.assembly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.student.core.assembly.AssemblerFilterManager;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.MockProposal;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.transform.WorkflowFilter;

public class TestFilterStuff {
	final Logger LOG = Logger.getLogger(TestFilterStuff.class);
	@Test
	public void testFilter() throws AssemblyException{
		AddOneAssembler assembler = new AddOneAssembler();
		MultiplyFilter filter = new MultiplyFilter();
		AssemblerFilterManager<Integer,Integer> mgr = new AssemblerFilterManager<Integer,Integer>(assembler);
		mgr.addFilter(filter);
		mgr.addFilter(new MultiplyFilter());
		SaveResult<Integer> result = mgr.save(new Integer(4));
		LOG.warn("Final result is:"+result.getValue());
		assertEquals(28,result.getValue().intValue());
		
	}
	
	@Test
	public void testWorkflowFilter() throws Exception {
		MockProposal proposal = new MockProposal();
		proposal.setId("123456789");
		proposal.setOrgId(Arrays.asList("testOrg"));
		proposal.setProposalRationale("Testing a proposal");
		proposal.setProposalTitle("Test Proposal");
		proposal.getAttributes().put("user","test");
		
		WorkflowFilter workflowFilter = new WorkflowFilter();
		Map<String,String> docFieldPaths = new HashMap<String,String>();
		docFieldPaths.put("proposalId", "id");
		docFieldPaths.put("primaryOrg", "orgId[0]");
		docFieldPaths.put("secondaryOrg", "orgId[1]");	//Non-existant secondary org
		docFieldPaths.put("userId", "user");
		docFieldPaths.put("foo", "foo");				//Non-existant property
		
		workflowFilter.setDtoClass(MockProposal.class);
		workflowFilter.setDocTitlePath("proposalTitle");
		workflowFilter.setDocFieldPaths(docFieldPaths);
		workflowFilter.setObjectIdPath("id");
						
		assertEquals("123456789", workflowFilter.getObjectId(proposal));
		assertEquals("Test Proposal", workflowFilter.getDocumentTitle(proposal));
		
		String docContent = workflowFilter.getDocumentContent(proposal);
		assertTrue(docContent.contains("<proposalId>123456789</proposalId>"));
	}
}
