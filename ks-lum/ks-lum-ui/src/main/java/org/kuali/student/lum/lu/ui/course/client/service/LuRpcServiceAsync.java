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

package org.kuali.student.lum.lu.ui.course.client.service;


import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluLoRelationInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public interface LuRpcServiceAsync extends BaseRpcServiceAsync{
    public void createClu(String luTypeKey, CluInfo cluInfo, AsyncCallback<CluInfo> callback);
    public void updateClu(String cluId, CluInfo cluInfo, AsyncCallback<CluInfo> callback);
    public void getCluLoRelationsByClu(String cluId,  AsyncCallback<List<CluLoRelationInfo>> callback);
    public void getClu(String cluId, AsyncCallback<CluInfo> callback);
}
