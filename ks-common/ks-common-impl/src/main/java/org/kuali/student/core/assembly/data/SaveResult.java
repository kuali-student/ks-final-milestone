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

package org.kuali.student.core.assembly.data;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.core.validation.dto.ValidationResultInfo;

@Deprecated
public class SaveResult<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private T value;
	private List<ValidationResultInfo> validationResults;
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
	public List<ValidationResultInfo> getValidationResults() {
		return validationResults;
	}
	
	public void setValidationResults(List<ValidationResultInfo> validationResults) {
		this.validationResults = validationResults;
	}
	
	public void addValidationResults(List<ValidationResultInfo> validationResults){
		if (this.validationResults == null){
			setValidationResults(this.validationResults);
		} else{
			this.validationResults.addAll(validationResults);
		}
	}
	
}
