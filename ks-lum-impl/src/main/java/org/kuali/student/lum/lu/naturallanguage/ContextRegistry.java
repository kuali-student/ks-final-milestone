package org.kuali.student.lum.lu.naturallanguage;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.lum.lu.entity.ReqComponentType;
import org.kuali.student.lum.lu.naturallanguage.contexts.Context;

/**
 * This class is a registry of template contexts which the requirement 
 * component translator uses to generate the natural language.
 */
public class ContextRegistry {

	private Map<String, Context> registry = new HashMap<String, Context>();

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
	public ContextRegistry(Map<String, Context> registry) {
		this.registry = registry;
	}

	/**
	 * Adds a context to the registry. Key is usually a 
	 * <@link {@link ReqComponentType} key.
	 * 
	 * @param key Context key
	 * @param context Context
	 */
	public void add(String key, Context context) {
		this.registry.put(key, context);
	}

	/**
	 * Gets a context from the registry. Key is usually a 
	 * <@link {@link ReqComponentType} key.
	 * 
	 * @param key Context key
	 * @return A context
	 */
	public Context get(String key) {
		return this.registry.get(key);
	}

	/**
	 * Remove a context from the registry. Key is usually a 
	 * <@link {@link ReqComponentType} key.
	 * 
	 * @param key
	 * @return
	 */
	public Context remove(String key) {
		return this.registry.remove(key);
	}

	@Override
	public String toString() {
		return this.registry.toString();
	}
}
