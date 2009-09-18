package org.kuali.student.common.ui.client.mvc;


/**
 * Passed in as an argument to Controller.requestModel Because a model may need to be initialized via an asynchronous call,
 * the request uses a callback to pass the result back to the caller
 * 
 * @author Kuali Student Team
 * @param <T>
 *            the type of model being requested
 */
public interface ModelRequestCallback<T> {
    /**
     * Called when the model is available
     * 
     * @param model
     *            the model that was requested
     */
    public void onModelReady(Model<T> model);

    /**
     * Called when the controller was unable to provide the model
     * 
     * @param cause
     *            the exception that prevented the model from being provided, or null if the model was simply not located
     */
    public void onRequestFail(Throwable cause);
}
