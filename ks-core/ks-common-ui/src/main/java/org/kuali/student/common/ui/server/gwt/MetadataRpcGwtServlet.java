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
package org.kuali.student.common.ui.server.gwt;

import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.old.MetadataServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MetadataRpcGwtServlet extends RemoteServiceServlet implements MetadataRpcService {

    private static final long serialVersionUID = 1L;

    private MetadataServiceImpl serviceImpl; 

    @Override
    public Metadata getMetadata(String objectKey, String type, String state) {
        return serviceImpl.getMetadata(objectKey, type, state);
    }
    
    public void setServiceImpl(MetadataServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }    
}
