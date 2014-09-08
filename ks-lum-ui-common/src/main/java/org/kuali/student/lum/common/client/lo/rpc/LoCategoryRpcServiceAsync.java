/*
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

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import java.util.List;

/**
 * 
 * @author Kuali Student Team
 * 
 */
public interface LoCategoryRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync {

    public void deleteLoCategory(String loCategoryId, AsyncCallback<StatusInfo> callback);

    public void getLoCategoryTypes(AsyncCallback<List<TypeInfo>> callback);

    public void getLoCategoryType(String loCategoryTypeKey, AsyncCallback<TypeInfo> callback);

    public void getLoCategories(String loRepositoryKey, AsyncCallback<List<LoCategoryInfo>> callback);

}
