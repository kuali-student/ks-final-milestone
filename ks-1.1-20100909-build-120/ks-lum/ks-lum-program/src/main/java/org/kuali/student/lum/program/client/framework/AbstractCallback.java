package org.kuali.student.lum.program.client.framework;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * @author Igor
 */
public class AbstractCallback<T> extends KSAsyncCallback<T> {

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
		super.onFailure(caught);
	}

	@Override
    public void handleFailure(Throwable caught) {
        KSBlockingProgressIndicator.removeTask(loadingTask);
        Window.alert("Call failed on server.");
        GWT.log("Exception:", caught);
    }

    @Override
    public void onSuccess(T result) {
        KSBlockingProgressIndicator.removeTask(loadingTask);
    }
}
