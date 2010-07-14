package org.kuali.student.lum.program.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;

/**
 * @author Igor
 */
public class AbstractCallback<T> implements AsyncCallback<T> {

    private BlockingTask loadingTask = new BlockingTask("Loading");

    public AbstractCallback() {
        KSBlockingProgressIndicator.addTask(loadingTask);
    }

    @Override
    public void onFailure(Throwable caught) {
        KSBlockingProgressIndicator.removeTask(loadingTask);
        Window.alert("Call failed on server.");
    }

    @Override
    public void onSuccess(T result) {
        KSBlockingProgressIndicator.removeTask(loadingTask);
    }
}
