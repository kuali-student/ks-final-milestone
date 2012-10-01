/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.application;

import org.kuali.student.common.ui.client.security.AsyncCallbackFailureHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * All GWT call backs use this class, which allows us to intercept any
 * failure and handle them appropriately.
 * 
 * @author Kuali Student Team
 *
 */
public abstract class KSAsyncCallback<T> implements AsyncCallback<T>{
 
    /**
     * Failure is delegated to this class which is created using GWT.create().
     * This allows implementing institution to swap out the class
     */
    private static final AsyncCallbackFailureHandler asyncCallbackFailureHandler = GWT.create(AsyncCallbackFailureHandler.class);
    
	/**
	 *  Allows institution to override this method by implementing its own 
	 *  AsyncCallbackFailureHandler. 
	 */
	public void onFailure(Throwable caught) {  
	    asyncCallbackFailureHandler.onFailure(caught);
    }
    /**
     *  Allows institution to override this method by implementing its own 
     *  AsyncCallbackFailureHandler.
     */
	public void handleFailure(Throwable caught) {
         asyncCallbackFailureHandler.handleFailure(caught);
	}
    /**
     *  Allows institution to override this method by implementing its own 
     *  AsyncCallbackFailureHandler.
     */
	 public void handleTimeout(Throwable caught){
	     asyncCallbackFailureHandler.handleTimeout(caught);
	 }
    /**
     *  Allows institution to override this method by implementing its own 
     *  AsyncCallbackFailureHandler.
     */
	 public void handleVersionMismatch(Throwable caught){
	     asyncCallbackFailureHandler.handleVersionMismatch(caught); 
	 }
    
}
