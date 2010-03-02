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
package org.kuali.student.lum.lu.ui.course.client.service;

import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.lum.lo.dto.LoInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Kuali Student Team
 */
@RemoteServiceRelativePath("rpcservices/LoRpcService")
public interface LoRpcService extends BaseRpcService {
    public List<LoCategoryInfo> getLoCategories(String loRepositoryKey);
    public LoCategoryInfo updateLoCategory(String loCategoryId, LoCategoryInfo loCategoryInfo);
    public StatusInfo deleteLoCategory(String loCategoryId);
    public List<LoInfo> getLoByIdList(List<String> loId);

    public List<LoCategoryTypeInfo> getLoCategoryTypes();

    public LoCategoryInfo createLoCategory(String loRepositoryKey,
            String loCategoryTypeKey, LoCategoryInfo loCategoryInfo);

    public LoCategoryInfo getLoCategory(String loCategoryId);
    
    public LoCategoryTypeInfo getLoCategoryType(String loCategoryTypeKey);
    
    public List<LoInfo> getLosByLoCategory(String loCategoryId);
}
