package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.lum.lu.ui.course.client.service.ServerProperties;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServerPropertiesServlet extends RemoteServiceServlet implements ServerProperties {
    
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

}
