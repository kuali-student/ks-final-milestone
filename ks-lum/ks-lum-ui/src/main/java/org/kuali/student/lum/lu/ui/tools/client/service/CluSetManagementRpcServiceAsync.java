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

package org.kuali.student.lum.lu.ui.tools.client.service;

import java.util.List;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluInformation;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluSetInformation;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CluSetManagementRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync{

//    public void getCluSetInfo(String cluSetId, AsyncCallback<CluSetInfo> callback);
    public void getCluSetInformation(String cluSetId, AsyncCallback<CluSetInformation> callback);
//    public void getCluInformation(List<String> cluIds, AsyncCallback<List<CluInformation>> callback);
//    public void getCluSetInfos(List<String> cluSetIds, AsyncCallback<List<CluSetInfo>> callback);
}
