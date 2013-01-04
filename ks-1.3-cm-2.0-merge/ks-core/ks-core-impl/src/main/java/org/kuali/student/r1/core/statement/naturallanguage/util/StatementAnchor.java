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

package org.kuali.student.r1.core.statement.naturallanguage.util;

import org.kuali.student.r1.core.statement.entity.Statement;

/**
 * This class attaches a CLU id anchor to a {@link CustomLuStatementInfo}.
 */
@Deprecated
public class StatementAnchor {
	private Statement statement;
	private String refObjectTypeKey;
	private String refObjectId;

	public StatementAnchor(Statement statement, String refObjectTypeKey, String refObjectId) {
		this.statement = statement;
		this.refObjectTypeKey = refObjectTypeKey;
		this.refObjectId = refObjectId;
	}

	public Statement getStatement() {
		return this.statement;
	}

	public String getRefObjectTypeKey() {
		return refObjectTypeKey;
	}

	public String getRefObjectId() {
		return refObjectId;
	}
}
