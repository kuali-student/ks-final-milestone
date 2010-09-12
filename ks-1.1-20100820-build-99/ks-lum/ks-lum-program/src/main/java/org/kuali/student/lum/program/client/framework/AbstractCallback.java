package org.kuali.student.lum.program.client.framework;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;

/**
 * @author Igor
 */
public class AbstractCallback<T> implements AsyncCallback<T> {

    private BlockingTask loadingTask;

    public AbstractCallback() {
        this("Loading");
    }

    public AbstractCallback(String text) {
        loadingTask = new BlockingTask(text);
        KSBlockingProgressIndicator.addTask(loadingTask);
    }

    @Override
    public void onFailure(Throwable caught) {
        KSBlockingProgressIndicator.removeTask(loadingTask);
        Window.alert("Call failed on server.");
        GWT.log("Exception:", caught);
    }

    @Override
    public void onSuccess(T result) {
        KSBlockingProgressIndicator.removeTask(loadingTask);
    }
}
