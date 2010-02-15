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
package org.kuali.student.lum.nlt.naturallanguage.util;

/**
 * This class attaches a CLU id anchor to a {@link CustomLuStatementInfo}.
 */
public class LuStatementAnchor {
	private CustomLuStatementInfo luStatement;
	private String cluAnchorId;

	public LuStatementAnchor(CustomLuStatementInfo luStatement, String cluAnchorId) {
		this.luStatement = luStatement;
		this.cluAnchorId = cluAnchorId;
	}

	public CustomLuStatementInfo getLuStatement() {
		return this.luStatement;
	}

	public String getCluAnchorId() {
		return this.cluAnchorId;
	}
}
