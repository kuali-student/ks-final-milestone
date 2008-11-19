package org.kuali.student.enumeration.web.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EnumerationGWTClientServiceAsync{
    public void fetchEnumertionMeta(AsyncCallback<List<String>> callback);
}