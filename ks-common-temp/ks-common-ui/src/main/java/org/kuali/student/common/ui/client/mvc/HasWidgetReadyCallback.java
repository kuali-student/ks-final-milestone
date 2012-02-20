/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import com.google.gwt.user.client.ui.Widget;

/**
 * This interface should be implemented by widget that will not be ready for  
 * use until some action is completed (eg. load data from async callback) 
 * 
 * @author Kuali Student Team
 *
 */
public interface HasWidgetReadyCallback {
    
    /** 
     * This method sets an initialized state of the widget
     * 
     * @param initialized
     */
    public void setInitialized(boolean initialized);
    
    /** 
     * This method can be used to query if a widget has been initialized.
     * 
     * @return
     */
    public boolean isInitialized();
    
    /** 
     * This method can be used to register a callback to be executed when a widget
     * has been initialized.  The callback should be unregistered by the 
     * implementing widget after execution. 
     * 
     * @param callback
     */
    public void addWidgetReadyCallback(Callback<Widget> callback);    
}
