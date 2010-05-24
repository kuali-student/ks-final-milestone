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

package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;

public abstract class MultiplicitySection extends BaseSection {
	private final QueryPath path;
	
	public MultiplicitySection(String path) {
		this(QueryPath.parse(path));
	}
	public MultiplicitySection(QueryPath path) {
		this.path = path;
	}
	
	protected String getPath(int index, String relativePath) {
		QueryPath relative = QueryPath.parse(relativePath);
		QueryPath result = new QueryPath();
		result.addAll(path);
		result.add(new Data.IntegerKey(index));
		result.addAll(relative);
		return result.toString();
	}
	abstract Section createItem(int index);
	abstract void removeItem(int index);
	
}
