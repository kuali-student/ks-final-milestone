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

package org.kuali.student.brms.statement.naturallanguage.util;

import org.kuali.student.brms.statement.entity.ReqComponent;

/**
 * This class attaches a boolean id 
 * (during statement parsing {@link StatementParser}) to 
 * a {@link CustomReqComponentInfo}.
 */
public class ReqComponentReference {
	private String booleanId;
	private ReqComponent reqComponent;
	
	public ReqComponentReference(ReqComponent reqComponent, String booleanId) {
		this.reqComponent = reqComponent;
		this.booleanId = booleanId;
	}

	public String getBooleanId() {
		return this.booleanId;
	}

	public ReqComponent getReqComponent() {
		return this.reqComponent;
	}
}
