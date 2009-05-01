package org.kuali.student.brms.internal.common.statement.propositions.functions;

public interface Function<T> {
	public T compute();

	public void setOperation(String operationType);
}
