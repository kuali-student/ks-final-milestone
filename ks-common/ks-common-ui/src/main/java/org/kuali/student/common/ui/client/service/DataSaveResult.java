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

package org.kuali.student.common.ui.client.service;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.common.dto.ValidationResultInfo;


public class DataSaveResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<ValidationResultInfo> validationResults;
	private Data value;
	
	public DataSaveResult() {
		super();
	}
	public DataSaveResult(final List<ValidationResultInfo> validationResults, final Data value) {
		super();
		setValidationResults(validationResults);
		setValue(value);
	}
	public List<ValidationResultInfo> getValidationResults() {
		return this.validationResults;
	}
	public Data getValue() {
		return this.value;
	}
	public void setValidationResults(
			List<ValidationResultInfo> validationResults) {
		this.validationResults = validationResults;
	}
	public void setValue(Data value) {
		this.value = value;
	}
	
}
