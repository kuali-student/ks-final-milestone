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

package org.kuali.student.lum.common.client.lo.rpc;


import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.lum.lo.dto.LoInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author Kuali Student Team
 *
 */
public interface LoRpcServiceAsync extends BaseRpcServiceAsync{
    public void getLoByIdList(List<String> loId,  AsyncCallback<List<LoInfo>> callback);
    public void getLosByLoCategory(String loCategoryId,  AsyncCallback<List<LoInfo>> callback);
    
    public void  updateLoCategory(String loCategoryId, LoCategoryInfo loCategoryInfo,AsyncCallback<LoCategoryInfo> callback);
    public void  deleteLoCategory(String loCategoryId,AsyncCallback<StatusInfo> callback);
    
    public void getLoCategoryTypes( AsyncCallback<List<LoCategoryTypeInfo>> callback);
    public void createLoCategory(String loRepositoryKey,  String loCategoryTypeKey, LoCategoryInfo loCategoryInfo,
            AsyncCallback<LoCategoryInfo> callback);
    public void getLoCategory(String loCategoryId, AsyncCallback<LoCategoryInfo> callback);
    public void  getLoCategoryType(String loCategoryTypeKey, AsyncCallback<LoCategoryTypeInfo> callback );
    public void getLoCategories(String loRepositoryKey,AsyncCallback<List<LoCategoryInfo>> callback);

}
