/*
 * Copyright 2009 Johnson Consulting Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package org.kuali.student.common.assembly.client;

import java.util.Iterator;

import org.kuali.student.common.assembly.client.Data.Key;
/**
 * @author wilj
 *
 */
public class DefaultPathParser implements PathParser {

    private final String PATH_SEPARATOR = "/";
    
	/* (non-Javadoc)
	 * @see com.johnsoncs.gwt.application.client.model.PathParser#format(com.johnsoncs.gwt.application.client.model.QueryPath)
	 */
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

	/* (non-Javadoc)
	 * @see com.johnsoncs.gwt.application.client.model.PathParser#getWildCard()
	 */
	@Override
	public String getWildCard() {
		return "*";
	}
	
	/* (non-Javadoc)
	 * @see com.johnsoncs.gwt.application.client.model.PathParser#parse(java.lang.String)
	 */
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
