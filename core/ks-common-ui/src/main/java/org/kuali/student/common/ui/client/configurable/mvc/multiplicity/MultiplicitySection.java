package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Section;

public abstract class MultiplicitySection extends Section {
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
