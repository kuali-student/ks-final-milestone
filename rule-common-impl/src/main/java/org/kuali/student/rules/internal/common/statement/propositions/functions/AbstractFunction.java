package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class AbstractFunction implements Function {

	String operation;

	public abstract Object compute();

	public void setOperation(String operationType) {
		this.operation = operationType;
	}

	public abstract Integer getInputs();

	public abstract Integer getOutputs();

	public abstract Object getOutput();

	public abstract void setInput(Object input);

	public Number convertToNumber(Class<?> type, Number value) {
    	if (value == null) {
    		return null;
    	}
    	else if (type.isPrimitive()) {
        	throw new RuntimeException("Number type conversion error. Primitives cannot be converted: " + type);
    	}
    	else if (type.equals(String.class)) {
    		return value;
    	}
    	else if (type.equals(Integer.class)) {
    		return new Integer(value.intValue());
    	}
    	else if (type.equals(Double.class)) {
    		return new Double(value.doubleValue());
    	}
    	else if (type.equals(Long.class)) {
    		return new Long(value.longValue());
    	}
    	else if (type.equals(Float.class)) {
    		return new Float(value.floatValue());
    	}
    	else if (type.equals(Short.class)) {
    		return new Short(value.shortValue());
    	}
    	else if (type.equals(Byte.class)) {
    		return new Byte(value.byteValue());
    	}
    	else if (type.equals(BigDecimal.class)) {
    		return new BigDecimal(value.toString());
    	}
    	else if (type.equals(BigInteger.class)) {
    		return new BigDecimal(value.toString()).toBigIntegerExact();
    	}
    	throw new RuntimeException("Number type conversion error. Type not found: " + type);
	}
}
