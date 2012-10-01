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

/**
 * 
 */
package org.kuali.student.common.assembly.data;

import java.util.ArrayList;
import java.util.Collection;

import org.kuali.student.common.assembly.data.Data.Key;
/**
 * QueryPath is used for retrieving and storing data in the DataModel.  A path is can be represented with
 * a "/" separated string.
 * 
 * @author Kuali Student Team
 *
 */
public class QueryPath extends ArrayList<Key> {
	//private static final PathParser parser = GWT.create(PathParser.class);
	private static final PathParser parser = new DefaultPathParser();
	
	private static final long serialVersionUID = 1L;

	public static String getWildCard() {
		return parser.getWildCard();
	}
	
	public static String getPathSeparator(){
	    return parser.getPathSeparator();
	}

	public static QueryPath parse(final String path) {
		return parser.parse(path);
	}

	private String stringPath = null;

	/* (non-Javadoc)
	 * @see java.util.ArrayList#add(int, java.lang.Object)
	 */
	@Override
	public void add(final int index, final Key element) {
		stringPath = null;
		super.add(index, element);
	}

	/* (non-Javadoc)
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(final Key e) {
		stringPath = null;
		return super.add(e);
	}

	/* (non-Javadoc)
	 * @see java.util.ArrayList#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(final Collection<? extends Key> c) {
		stringPath = null;
		return super.addAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.ArrayList#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(final int index, final Collection<? extends Key> c) {
		stringPath = null;
		return super.addAll(index, c);
	}

	/* (non-Javadoc)
	 * @see java.util.ArrayList#clear()
	 */
	@Override
	public void clear() {
		stringPath = null;
		super.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.ArrayList#remove(int)
	 */
	@Override
	public Key remove(final int index) {
		stringPath = null;
		return super.remove(index);
	}

	/* (non-Javadoc)
	 * @see java.util.ArrayList#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(final Object o) {
		stringPath = null;
		return super.remove(o);
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(final Collection<?> c) {
		stringPath = null;
		return super.removeAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.ArrayList#removeRange(int, int)
	 */
	@Override
	protected void removeRange(final int fromIndex, final int toIndex) {
		stringPath = null;
		super.removeRange(fromIndex, toIndex);
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(final Collection<?> c) {
		stringPath = null;
		return super.retainAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.ArrayList#set(int, java.lang.Object)
	 */
	@Override
	public Key set(final int index, final Key element) {
		stringPath = null;
		return super.set(index, element);
	}

	public QueryPath subPath(final int fromIndex, final int toIndex) {
		// TODO revamp this method to use subList once GWT issue 1791 is fixed
		final QueryPath result = new QueryPath();
		for (int i = fromIndex; i < toIndex; i++) {
			result.add(this.get(i));
		}
		return result;
	}

	@Override
	public String toString() {
		if (stringPath == null) {
			stringPath = parser.format(this);
		}
		return stringPath;
	}
	
	public static QueryPath concat(String... paths) {
		QueryPath result = new QueryPath();
		for (String path : paths) {
			if (path != null) {
				result.addAll(QueryPath.parse(path));
			}
		}
		return result;
	}
}
