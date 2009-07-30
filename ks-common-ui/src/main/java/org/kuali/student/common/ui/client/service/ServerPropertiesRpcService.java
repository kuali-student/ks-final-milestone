package org.kuali.student.common.ui.client.service;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ServerProperties")
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
}
