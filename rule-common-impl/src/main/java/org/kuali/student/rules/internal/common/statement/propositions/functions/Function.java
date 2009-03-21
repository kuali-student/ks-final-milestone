package org.kuali.student.rules.internal.common.statement.propositions.functions;

public interface Function {
	public Object compute();

	public void setOperation(String operationType);
	
	public void setInput(Object input);
	
	public Integer getInputs();
	
	public Integer getOutputs();
	
	public Object getOutput();
}
