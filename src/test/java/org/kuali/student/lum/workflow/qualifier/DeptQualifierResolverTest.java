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

import java.util.List;

import org.junit.Test;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.routeheader.StandardDocumentContent;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.test.BaseRiceTestCase;
import org.kuali.student.lum.workflow.qualifier.DeptQualifierResolver;

/**
 * Tests the XPathQualifierResolver.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class DeptQualifierResolverTest extends BaseRiceTestCase {
	
	private static final String SIMPLE_CLU_DOC_NO_DEPT_OR_COLLEGE_XML =
								"<applicationContent>" +
									"<cluProposal>" +
										"<cluId>Doc Type 1</cluId>" +
									"</cluProposal>" +
								"</applicationContent>";
	
	private static final String SIMPLE_CLU_DOC_XML =
								"<applicationContent>" +
									"<cluProposal>" +
										"<cluId>Doc Type 1</cluId>" +
										"<department>Chemistry</department>" +
									"</cluProposal>" +
								"</applicationContent>";
	
	@Test
	public void testResolveBasic() throws Exception {
		DeptQualifierResolver resolver = new DeptQualifierResolver();
		
		RouteContext context = new RouteContext();
		DocumentContent docContent = new StandardDocumentContent(SIMPLE_CLU_DOC_NO_DEPT_OR_COLLEGE_XML);
		context.setDocumentContent(docContent);
		
		// shouldn't find a department, since there is none
		List<AttributeSet> attributeSets = resolver.resolve(context);
		assertTrue(attributeSets.size() == 0 || attributeSets.get(0).size() == 0);
		
		// however, should succeed with this
		context = new RouteContext();
		docContent = new StandardDocumentContent(SIMPLE_CLU_DOC_XML);
		context.setDocumentContent(docContent);
		
		attributeSets = resolver.resolve(context);
		assertEquals(1, attributeSets.size());
		assertEquals(1, attributeSets.get(0).size());
		assertEquals("Chemistry", attributeSets.get(0).get("department"));
	}
}
