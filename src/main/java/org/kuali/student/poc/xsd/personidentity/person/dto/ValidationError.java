package org.kuali.student.poc.xsd.personidentity.person.dto;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationError implements Errors{
	@XmlElement
	private String errorMessage;
		
	@Override
	public void addAllErrors(Errors arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List getAllErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getErrorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FieldError getFieldError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FieldError getFieldError(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFieldErrorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFieldErrorCount(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getFieldErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getFieldErrors(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getFieldType(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getFieldValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectError getGlobalError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGlobalErrorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getGlobalErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNestedPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getObjectName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasFieldErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasFieldErrors(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasGlobalErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void popNestedPath() throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pushNestedPath(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reject(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reject(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reject(String arg0, Object[] arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rejectValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rejectValue(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rejectValue(String arg0, String arg1, Object[] arg2, String arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNestedPath(String arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
