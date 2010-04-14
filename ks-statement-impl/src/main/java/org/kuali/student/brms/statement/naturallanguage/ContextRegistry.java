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

package org.kuali.student.brms.statement.naturallanguage;

import java.util.HashMap;
import java.util.Map;


/**
 * This class is a registry of template contexts which the requirement 
 * component translator uses to generate natural language.
 */
public class ContextRegistry<T extends Context<?>>  {

	/** Registry context map */
	private Map<String, T> registry = new HashMap<String, T>();

	/**
	 * Constructor.
	 */
	public ContextRegistry() {
	}

	/**
	 * Constructor. Adds a context registry as a map.
	 * 
	 * @param registry Context registry
	 */
	public ContextRegistry(final Map<String, T> registry) {
		this.registry = registry;
	}

	/**
	 * Adds a context to the registry. Key is usually a 
	 * <@link {@link ReqComponentType} key.
	 * 
	 * @param key Context key
	 * @param context Context
	 */
	public void add(final String key, final T context) {
		this.registry.put(key, context);
	}

	/**
	 * Gets a context from the registry. Key is usually a 
	 * <@link {@link ReqComponentType} key.
	 * 
	 * @param key Context key
	 * @return A context
	 */
	public T get(final String key) {
		return this.registry.get(key);
	}

	/**
	 * Returns true if a context exists for <code>key</code>; otherwise false.
	 * 
	 * @param key Context key
	 * @return True if a context exists otherwise false
	 */
	public boolean containsKey(final String key) {
		return this.registry.containsKey(key);
	}

	/**
	 * Remove a context from the registry. Key is usually a 
	 * <@link {@link ReqComponentType} key.
	 * 
	 * @param key
	 * @return
	 */
	public T remove(final String key) {
		return this.registry.remove(key);
	}

	@Override
	public String toString() {
		return this.registry.toString();
	}
}
