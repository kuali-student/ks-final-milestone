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
package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.r1.common.assembly.data.Metadata;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/MetadataRpcService")
public interface MetadataRpcService extends RemoteService {
    public Metadata getMetadata(String objectKey, String type, String state);
    public List<Metadata> getMetadataList(String objectKey, List<String> types, String state);
    public Metadata getMetadataList(String objectKey, String state);    
}
