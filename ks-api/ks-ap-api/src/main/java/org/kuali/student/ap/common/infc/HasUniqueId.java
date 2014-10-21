package org.kuali.student.ap.common.infc;

/**
 * Interface for model elements that present a per-instance unique identifier
 * suitable for distinguishing between multiple views of the same underlying
 * record.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version 0.7.6
 */
public interface HasUniqueId {

	/**
	 * Get the unique identifier.
	 * 
	 * <p>
	 * The value returned by this method should always be unique within the
	 * context of the rendered page, and therefore reliable for referring to the
	 * represented user interface element using a DOM selector within a
	 * front-end script.
	 * </p>
	 * 
	 * <p>
	 * This value must be usable in an XML document as an ID attribute without
	 * modification. Therefore, only letters, numbers, hyphens, and the
	 * underscore character are permitted.
	 * </p>
	 * 
	 * @return A unique identifier for this component.
	 */
	String getUniqueId();

}
