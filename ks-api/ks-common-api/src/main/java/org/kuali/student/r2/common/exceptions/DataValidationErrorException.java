/**
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.exceptions;

import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.WebFault;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebFault(name = "DataValidationError")
public class DataValidationErrorException 
    extends Exception {

    private static final Logger LOG = LoggerFactory.getLogger(DataValidationErrorException.class);
    
    private static final long serialVersionUID = 1L;

    private List<ValidationResultInfo> validationResults;
    
    public DataValidationErrorException() {
    }
    
    public DataValidationErrorException(String message, List<ValidationResultInfo> validationResults) {
        this(message, validationResults, null);
    }

    public DataValidationErrorException(String message, List<ValidationResultInfo> validationResults, Throwable cause) {
        super(message, cause);
        this.validationResults = validationResults;
    }

    public DataValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataValidationErrorException(String message) {
        super(message);
    }

    public DataValidationErrorException(Throwable cause) {
        super(cause);
    }

    public List<ValidationResultInfo> getValidationResults() {
        if(validationResults == null){
            validationResults = new ArrayList<ValidationResultInfo>();
        }
        return validationResults;
    }

    public void setValidationResults(List<ValidationResultInfo> validationResults) {
        this.validationResults = validationResults;
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

        if (validationResults != null) {
            sb.append("Validation Results: \n");
            for (ValidationResultInfo info:validationResults) {
                sb.append(info).append("\n");
            }
        } else {
            sb.append("Validation Results: None set.");
        }
        return sb.toString();
    }
    
    private void logValidationResults() {
        if (LOG.isDebugEnabled()) {
            LOG.debug(toString());
        }
    }
}
