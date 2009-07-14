package org.kuali.student.lum.lu.ui.course.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ServerProperties")
public interface ServerProperties extends RemoteService {
    public String get(String property);
}
