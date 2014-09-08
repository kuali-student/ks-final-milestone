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

package org.kuali.student.lum.common.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("rpcservices/CluSetManagementRpcService")
public interface CluSetManagementRpcService extends BaseDataOrchestrationRpcService{
//    public CluSetInfo getCluSetInfo(String cluSetId) throws OperationFailedException;
    public CluSetInformation getCluSetInformation(String cluSetId) throws OperationFailedException;
//    public List<CluInformation> getCluInformation(List<String> cluIds) throws OperationFailedException;
//    public List<CluSetInfo> getCluSetInfos(List<String> cluSetIds) throws OperationFailedException;
}
