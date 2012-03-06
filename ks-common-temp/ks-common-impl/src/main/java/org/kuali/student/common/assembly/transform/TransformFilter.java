package org.kuali.student.common.assembly.transform;

/**
 * This is just a marker interface for all transform filters
 * 
 * @author Will
 *
 */
public interface TransformFilter {
	//Generic filter property keys
	public static final String FILTER_ACTION = "TransformFilter.Operation";
	
	public enum TransformFilterAction{SAVE, GET};
	
	/**
	 * The type of the object being filtered.
	 * @return
	 */
	public Class<?> getType();
}
