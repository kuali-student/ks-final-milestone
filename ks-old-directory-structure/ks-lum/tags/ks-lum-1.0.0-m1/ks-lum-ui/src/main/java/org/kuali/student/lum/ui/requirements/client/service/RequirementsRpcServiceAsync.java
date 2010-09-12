/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
import java.util.Map;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author zzraly
 */
public interface RequirementsRpcServiceAsync extends BaseRpcServiceAsync {
    public void getNaturalLanguageForReqComponentInfo(ReqComponentInfo compInfo, String nlUsageTypeKey, AsyncCallback<String> asyncCallback);
    public void getNaturalLanguageForStatementVO(String cluId, StatementVO statementVO, String nlUsageTypeKey, AsyncCallback<String> asyncCallback);
    public void getReqComponentTypesForLuStatementType(String luStatementTypeKey, AsyncCallback<List<ReqComponentTypeInfo>> asyncCallback);
    public void getAllClus(AsyncCallback<Map<String, String>> asyncCallback);  
    public void getAllClusets(AsyncCallback<Map<String, String>> asyncCallback); 
    public void getLuStatementForCluAndStatementType(String cluId, String luStatementTypeKey, AsyncCallback<LuStatementInfo> asyncCallback); 
    public void getRuleRationale(String cluId, String luStatementTypeKey, AsyncCallback<String> asyncCallback);
    public void getStatementVO(String cluId, String luStatementTypeKey, AsyncCallback<StatementVO> asyncCallback);
}
