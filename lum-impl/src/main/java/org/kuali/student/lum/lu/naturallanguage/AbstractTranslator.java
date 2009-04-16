package org.kuali.student.lum.lu.naturallanguage;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;

public abstract class AbstractTranslator<T> {

	/**
	 * Translates an <code>object</code> for a specific 
	 * <code>nlUsageTypeKey</code> into natural language.
	 * 
	 * @param object Object type to translate 
	 * @param nlUsageTypeKey Natural language usuage type key (context)
	 * @return Natural language translation
	 * @throws DoesNotExistException Natural language usuage type key does not exist
	 * @throws OperationFailedException Translation fails
	 */
	public abstract String translate(T object, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

	/**
	 * Translates an object by <code>id</code> for a specific 
	 * <code>nlUsageTypeKey</code> into natural language.
	 * 
	 * @param id Id of object type to translate
	 * @param nlUsageTypeKey Natural language usuage type key (context)
	 * @return Natural language translation
	 * @throws DoesNotExistException Id or natural language usuage type key does not exist
	 * @throws OperationFailedException Translation fails
	 */
	public abstract String translate(String id, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;
}
