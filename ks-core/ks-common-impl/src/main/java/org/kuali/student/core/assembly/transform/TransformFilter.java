package org.kuali.student.core.assembly.transform;

/**
 * This is just a marker interface for all transform filters
 * 
 * @author Will
 *
 */
public interface TransformFilter {
	public static final String FILTER_OPERATION = "TransformFilter.Operation";
	
	/**
	 * The type of the object being filtered.
	 * @return
	 */
	public Class<?> getType();
}
