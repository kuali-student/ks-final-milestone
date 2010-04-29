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

package org.kuali.student.common.ui.client.event;


import com.google.gwt.event.shared.GwtEvent;

/**
 * Use this event to set the state of a widget to a dirty state. 
 * 
 * @author Kuali Student Team
 *
 */
public class DirtyStateChangeEvent extends GwtEvent<DirtyStateChangeHandler> {
    public static final Type<DirtyStateChangeHandler> TYPE = new Type<DirtyStateChangeHandler>();
          
    boolean dirty = false;
    
    public DirtyStateChangeEvent(boolean dirty){
        this.dirty = dirty;
    }
    
    public void setDirty(boolean dirty){
        this.dirty = dirty;
    }
    
    public boolean isDirty(){
        return dirty;
    }
        
    @Override
    protected void dispatch(DirtyStateChangeHandler handler) {
        handler.onDirtyStateChange(this);
    }

    @Override
    public Type<DirtyStateChangeHandler> getAssociatedType() {
        return TYPE;
    }

}
