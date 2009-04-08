package org.kuali.student.lum.lu.naturallanguage;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;

public abstract class AbstractTranslator<T> {

	public abstract String translate(T type, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

	public abstract String translate(String id, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;
}
