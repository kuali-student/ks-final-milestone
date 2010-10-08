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

import java.util.Map;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.rice.authorization.PermissionType;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BaseDataOrchestrationRpcServiceAsync{
	
	//Data operations
	public void getData(String dataId, AsyncCallback<Data> callback);
	
	public void getMetadata(String id, Map<String,String> idType, AsyncCallback<Metadata> callback);

	public void saveData(Data data, AsyncCallback<DataSaveResult> callback);
	
	public void isAuthorized(PermissionType type, Map<String,String> attributes, AsyncCallback<Boolean> callback);

}
