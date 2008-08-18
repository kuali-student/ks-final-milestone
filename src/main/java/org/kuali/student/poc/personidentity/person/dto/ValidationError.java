package org.kuali.student.poc.personidentity.person.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationError {
	@XmlElement
	private String errorMessage;

	public List<?> getAllErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getErrorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFieldErrorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFieldErrorCount(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<?> getFieldErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> getFieldErrors(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<?> getFieldType(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getFieldValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getGlobalErrorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<?> getGlobalErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNestedPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getObjectName() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasFieldErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasFieldErrors(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasGlobalErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	public void popNestedPath() throws IllegalStateException {
		// TODO Auto-generated method stub

	}

	public void pushNestedPath(String arg0) {
		// TODO Auto-generated method stub

	}

	public void reject(String arg0) {
		// TODO Auto-generated method stub

	}

	public void reject(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void reject(String arg0, Object[] arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	public void rejectValue(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void rejectValue(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	public void rejectValue(String arg0, String arg1, Object[] arg2, String arg3) {
		// TODO Auto-generated method stub

	}

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
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
