/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
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
