package org.kuali.student.lum.lu.ui.course.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServerPropertiesAsync {
    public void get(String property, AsyncCallback<String> callback);
}
