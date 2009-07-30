package org.kuali.student.common.ui.client.service;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServerPropertiesAsync {
    public void get(String property, AsyncCallback<String> callback);
    public void get(List<String> property, AsyncCallback<Map<String,String>> callback);
}
