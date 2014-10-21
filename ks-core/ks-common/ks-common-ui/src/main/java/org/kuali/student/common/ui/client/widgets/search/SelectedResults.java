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

package org.kuali.student.common.ui.client.widgets.search;

import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;

@Deprecated
public class SelectedResults {
    private String displayKey;
	private String returnKey;
	private ResultRow resultRow;
    
    public SelectedResults(String resultReturnKey, String resultDisplayKey) {
    	displayKey = resultReturnKey;
    	returnKey = resultDisplayKey;
    }
    
    public SelectedResults(String resultReturnKey, String resultDisplayKey, ResultRow row) {
    	displayKey = resultReturnKey;
    	returnKey = resultDisplayKey;
    	this.resultRow = row;
    }

	public String getDisplayKey() {
		return displayKey;
	}

	public String getReturnKey() {
		return returnKey;
	}

	public void setResultRow(ResultRow resultRow) {
		this.resultRow = resultRow;
	}

	public ResultRow getResultRow() {
		return resultRow;
	}   
}
