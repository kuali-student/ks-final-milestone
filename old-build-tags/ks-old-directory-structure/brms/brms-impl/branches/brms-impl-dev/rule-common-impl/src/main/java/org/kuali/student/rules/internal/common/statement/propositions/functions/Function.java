package org.kuali.student.rules.internal.common.statement.propositions.functions;

public interface Function<T> {
	public T compute();

	public void setOperation(String operationType);
}
