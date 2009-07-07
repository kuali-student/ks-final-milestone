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

package org.kuali.student.lum.workflow.qualifier;

import org.kuali.rice.kew.role.XPathQualifierResolver;
import org.kuali.rice.kew.rule.bo.RuleAttribute;

/**
 * @author Kuali Student Team
 *
 */
public class DeptQualifierResolver extends XPathQualifierResolver {
	
	
	protected static final String DEPARTMENT = "department";
	protected static final String COLLEGE = "college";
	private static final String DEPT_RESOLVER_CONFIG =
								"<resolverConfig>" +
									"<baseXPathExpression>/applicationContent/cluProposal</baseXPathExpression>" +
									"<qualifier name=\"" + DEPARTMENT +  "\">" +
										"<xPathExpression>./department</xPathExpression>" + 
									"</qualifier>" +
									"<qualifier name=\"" + COLLEGE + "\">" +
										"<xPathExpression>./college</xPathExpression>" + 
									"</qualifier>" +
								"</resolverConfig>";
	
	private static 	RuleAttribute ruleAttribute = new RuleAttribute();
	static {
		ruleAttribute.setXmlConfigData(DEPT_RESOLVER_CONFIG);
	}

	public DeptQualifierResolver() {
		setRuleAttribute(ruleAttribute);
	}
}
