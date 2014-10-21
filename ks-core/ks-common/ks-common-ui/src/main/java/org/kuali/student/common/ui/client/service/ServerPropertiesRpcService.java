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

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/ServerPropertiesRpcService")
public interface ServerPropertiesRpcService extends RemoteService {
    /**
     * Gets the property from the server for use on the client side.
     * 
     * @param property property name to get
     * @return the property value
     */
    public String get(String property);
    
    /**
     * Gets the list of properties from the server for use on the client side.
     * 
     * @param properties the properties to get
     * @return a map of the properties and values, null if no property exists by the names given
     */
    public Map<String,String> get(List<String> properties);
        
    /** 
     * @return the context path of the app
     */
    public String getContextPath();
}
