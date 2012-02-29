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

package org.kuali.student.common.messagebuilder.impl;

public class BooleanOperators {
	private String andOperator = "AND";
	private String orOperator = "OR";

	public BooleanOperators() {
	}
	
	public BooleanOperators(String and, String or) {
		this.andOperator = and;
		this.orOperator = or;
	}

	public String getAndOperator() {
		return this.andOperator;
	}

	public String getOrOperator() {
		return this.orOperator;
	}
	
	public String toString() {
		return "andOperator=" + this.andOperator + ", orOperator=" + this.orOperator;
	}
}
