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

package org.kuali.student.lum.ui.requirements.client.service;

import java.util.List;

import org.kuali.student.brms.statement.dto.ReqCompFieldInfo;
import org.kuali.student.brms.statement.dto.ReqComponentInfo;
import org.kuali.student.brms.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author zzraly
 */
public interface RequirementsRpcServiceAsync extends BaseRpcServiceAsync {
    public void getNaturalLanguageForReqComponentInfo(ReqComponentInfo compInfo, String nlUsageTypeKey, String language, AsyncCallback<String> asyncCallback);
    public void getNaturalLanguageForStatementVO(String cluId, StatementVO statementVO, String nlUsageTypeKey, String language, AsyncCallback<String> asyncCallback);
    public void getReqComponentTypesForLuStatementType(String luStatementTypeKey, AsyncCallback<List<ReqComponentTypeInfo>> asyncCallback);  
	public void verifyFieldsAndSetIds(List<ReqCompFieldInfo> editedFields, AsyncCallback<List<ReqCompFieldInfo>> asyncCallback);
	public void retrieveCluCode(String cluId, AsyncCallback<String> asyncCallback);
}
