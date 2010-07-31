package org.kuali.student.lum.program.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Igor
 */
public class AbstractCallback<T> implements AsyncCallback<T> {
    @Override
    public void onFailure(Throwable caught) {
        Window.alert("Call failed on server.");
    }

    @Override
    public void onSuccess(T result) {

    }
}
