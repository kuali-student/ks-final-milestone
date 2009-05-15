package org.kuali.student.lum.lu.naturallanguage;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.lum.lu.naturallanguage.contexts.Context;

public class ContextRegistry {

	private Map<String, Context> registry = new HashMap<String, Context>();
	
	public ContextRegistry() {
	}
	
	public ContextRegistry(Map<String, Context> registry) {
		this.registry = registry;
	}

	public void put(String key, Context context) {
		this.registry.put(key, context);
	}
	
	public Context get(String key) {
		return this.registry.get(key);
	}
	
	public String toString() {
		return this.registry.toString();
	}
}
