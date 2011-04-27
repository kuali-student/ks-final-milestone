/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

import javax.xml.ws.WebFault;

import org.apache.log4j.Logger;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

@WebFault(faultBean="org.kuali.student.r2.common.exceptions.jaxws.DataValidationErrorExceptionBean")
public class DataValidationErrorException extends Exception {

	private static final Logger log = Logger.getLogger(DataValidationErrorException.class);

	private static final long serialVersionUID = 1L;

	private List<ValidationResultInfo> validationResults;

	/**
	 *
	 */
	public DataValidationErrorException() {
		super();
	}

	/**
	 * @param validationResults
	 */
	public DataValidationErrorException(String message, List<ValidationResultInfo> validationResults) {
		this(message, validationResults, null);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DataValidationErrorException(String message, List<ValidationResultInfo> validationResults, Throwable cause) {
		super(message, cause);
		this.validationResults = validationResults;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DataValidationErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DataValidationErrorException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DataValidationErrorException(Throwable cause) {
		super(cause);
	}


	/**
	 * @return the validationResults
	 */
	public List<ValidationResultInfo> getValidationResults() {
		return validationResults;
	}

	@Override
	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
		logValidationResults();
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
		logValidationResults();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.getMessage()).append("\n");
		if (validationResults != null){
			sb.append("Validation Results: \n");
			for (ValidationResultInfo info:validationResults){
				sb.append(info).append("\n");
			}
		} else {
			sb.append("Validation Results: None set.");
		}
		return sb.toString();
	}

	private void logValidationResults(){
		log.debug(toString());	}



}
