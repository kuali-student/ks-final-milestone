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
package org.kuali.student.r1.common.assembly.data;

import java.util.Iterator;

import org.kuali.student.r1.common.assembly.data.Data.Key;
/**
 * @author wilj
 *
 */
@Deprecated
public class DefaultPathParser implements PathParser {

    private static final String PATH_SEPARATOR = "/";
    
	@Override
	public String format(final QueryPath path) {
		final StringBuilder sb = new StringBuilder();
		for (final Iterator<Key> itr = path.iterator(); itr.hasNext();) {
			final Key key = itr.next();
			if (key == null) {
				// note, this branch should ideally never be hit
				// but if we throw an exception here, it breaks the eclipse debugger when trying to introspect
				sb.append("null");
			} else {
				sb.append(key.toString());
			}
			if (itr.hasNext()) {
				sb.append(PATH_SEPARATOR);
			}
		}
		return sb.toString();
	}

	@Override
	public String getWildCard() {
		return "*";
	}
	
	@Override
	public QueryPath parse(final String path) {
		final QueryPath result = new QueryPath();
		final String[] elements = path.split(PATH_SEPARATOR);
		for (String element : elements) {
			element = element.trim();
			if (!element.isEmpty()) {
				Integer index = null;
				try {
					index = Integer.valueOf(element);
				} catch (final Exception e) {
					// do nothing
				}
				if (index == null) {
					result.add(new Data.StringKey(element));
				} else {
					result.add(new Data.IntegerKey(index));
				}
			}
		}
		return result;
	}

    @Override
    public String getPathSeparator() {
        return PATH_SEPARATOR;
    }
}
