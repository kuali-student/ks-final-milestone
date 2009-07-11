/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.lum.workflow.qualifier;

import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.routeheader.StandardDocumentContent;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.test.BaseRiceTestCase;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.lum.workflow.qualifier.DeptQualifierResolver;

/**
 * Tests the XPathQualifierResolver.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class DeptQualifierResolverTest extends BaseRiceTestCase {
	
	private static final String SIMPLE_CLU_DOC_NO_ORG_ID_XML =
								"<documentContent>" +
									"<applicationContent>" +
										"<cluProposal>" +
											"<cluId>Doc Type 1</cluId>" +
										"</cluProposal>" +
									"</applicationContent>" +
								"</documentContent>";
	
	private static final String SIMPLE_CLU_DOC_XML =
								"<documentContent>" +
									"<applicationContent>" +
										"<cluProposal>" +
											"<cluId>Doc Type 1</cluId>" +
											"<orgId>64</orgId>" +
										"</cluProposal>" +
									"</applicationContent>" +
								"</documentContent>";
	
	OrganizationService mockOrgSvc = EasyMock.createMock(OrganizationService.class);

	@Test
	public void testResolveBasic() throws Exception {
		AbstractOrgQualifierResolver resolver = new DeptQualifierResolver();
		resolver.setOrganizationService(getMockOrgService());
		
		RouteContext context = new RouteContext();
		DocumentContent docContent = new StandardDocumentContent(SIMPLE_CLU_DOC_NO_ORG_ID_XML);
		context.setDocumentContent(docContent);
		
		// shouldn't find a department, since there is none
		List<AttributeSet> attributeSets = resolver.resolve(context);
		assertNotNull(attributeSets);
		assertTrue(attributeSets.isEmpty());
		
		// however, should succeed with this
		context = new RouteContext();
		docContent = new StandardDocumentContent(SIMPLE_CLU_DOC_XML);
		context.setDocumentContent(docContent);
		
		attributeSets = resolver.resolve(context);
		assertEquals(1, attributeSets.size());
		assertEquals(1, attributeSets.get(0).size());
		assertEquals("Chemistry", attributeSets.get(0).get("department"));
	}
	
	/**
	 * @return EasyMock-based mock OrganizationService
	 * 
	 * @throws PermissionDeniedException 
	 * @throws OperationFailedException 
	 * @throws MissingParameterException 
	 * @throws InvalidParameterException 
	 * @throws DoesNotExistException 
	 */
	@SuppressWarnings("unchecked")
	private OrganizationService getMockOrgService() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		EasyMock.reset(mockOrgSvc);
		/*
		ResultCell cell = new ResultCell();
		ResultCell cell2 = new ResultCell();
		ResultCell cell3 = new ResultCell();
		cell.setKey("org.resultColumn.orgId");
		cell.setValue("23");
		cell2.setValue("TestOrgShortName");
		cell3.setValue("kuali.org.hierarchy.Main");
		Result queryResult = new Result();
		Result queryResult2 = new Result();
		queryResult.setResultCells(Arrays.asList(cell, cell2));
		queryResult2.setResultCells(Arrays.asList(cell3));
		*/
		
		OrgInfo mockOrgInfo = new OrgInfo();
		mockOrgInfo.setId("64");
		mockOrgInfo.setType("kuali.org.Department");
		mockOrgInfo.setShortName("Chemistry");
		EasyMock.expect(mockOrgSvc.getOrganization("64")).andReturn(mockOrgInfo);
		
		/*
		EasyMock.expect(mockOrgSvc.searchForResults(EasyMock.matches("org.search.orgByShortName"), EasyMock.isA(List.class))).andStubReturn(Arrays.asList(queryResult));
		EasyMock.expect(mockOrgSvc.searchForResults(EasyMock.matches("org.search.hierarchiesOrgIsInByShortName"), EasyMock.isA(List.class))).andReturn(Arrays.asList(queryResult2));
		EasyMock.expect(mockOrgSvc.getAncestors("23", "kuali.org.hierarchy.Main")).andReturn(Arrays.asList("30", "137"));
		
		
		OrgInfo mockBoard = new OrgInfo();
		OrgInfo mockCollege = new OrgInfo();
		OrgInfo mockCommittee = new OrgInfo();
		mockBoard.setId("5");
		mockCollege.setType("kuali.org.Board");
		mockCollege.setId("30");
		mockCollege.setType("kuali.org.College");
		mockCommittee.setId("137");
		mockCommittee.setType("kuali.org.Committee");
		EasyMock.expect(mockOrgSvc.getOrganizationsByIdList(Arrays.asList("30", "137"))).andReturn(Arrays.asList(mockCollege, mockCommittee));
		EasyMock.expect(mockOrgSvc.searchForResults(EasyMock.matches("org.search.hierarchiesOrgIsInByShortName"), EasyMock.isA(List.class))).andReturn(Arrays.asList(queryResult2));
		*/
		EasyMock.replay(mockOrgSvc);
		return mockOrgSvc;
	}
}
