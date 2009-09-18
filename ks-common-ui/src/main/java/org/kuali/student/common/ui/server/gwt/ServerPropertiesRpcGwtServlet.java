package org.kuali.student.common.ui.server.gwt;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServerPropertiesRpcGwtServlet extends RemoteServiceServlet implements ServerPropertiesRpcService {
    
    Map<String,String> properties = new HashMap<String,String>();

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public String get(String property) {
        return properties.get(property);
    }

    @Override
    public Map<String, String> get(List<String> properties) {
        Map<String,String> map = new LinkedHashMap<String,String>();
        for (String property : properties) {
            map.put(property, get(property));
        }
        return map;
    }

}
