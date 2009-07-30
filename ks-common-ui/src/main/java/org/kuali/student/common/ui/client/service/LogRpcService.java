package org.kuali.student.common.ui.client.service;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

@RemoteServiceRelativePath("LogService")
public interface LogRpcService extends RemoteService {

    
    /**
     * Sends a List<LogMessage> of messages to the server.
     * @param clientContextInfo any configuration or runtime information about the client
     * @param messages the messages to log
     */
    public Boolean sendLog(Map<String, String> clientContextInfo, String log);
}
