package org.kuali.student.enumeration.web.client.service;

import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.dto.EnumerationMetaList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EnumerationGWTServiceAsync{
    public void fetchEnumerationMetas(AsyncCallback<EnumerationMetaList> callback);
    
    public void fetchEnumeration(String enumerationKey,String enumContextKey,String contextValue,Date contextDate,AsyncCallback<EnumeratedValueList> callback );
    
    public void addEnumeratedValue(String enumerationKey,EnumeratedValue value, AsyncCallback<EnumeratedValue> callback);

    public void updateEnumeratedValue(String enumerationKey,String code,EnumeratedValue value, AsyncCallback<EnumeratedValue> callback );

    public void removeEnumeratedValue(String enumerationKey,String code,  AsyncCallback<Boolean> callback);
}
