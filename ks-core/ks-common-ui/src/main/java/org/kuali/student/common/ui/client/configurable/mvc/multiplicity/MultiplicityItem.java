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
package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.mvc.Callback;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class should be extended to decorate the item widget as required.
 * 
 * @author Kuali Student Team
 *
 */
public abstract class MultiplicityItem extends Composite{
    private Integer itemKey;      
    private Widget itemWidget;
    private Callback<MultiplicityItem> removeCallback;
    
    private boolean isCreated = true;
    private boolean isDeleted = false;
    
    public Integer getItemKey() {
        return itemKey;
    }

    public void setItemKey(Integer itemKey) {
        this.itemKey = itemKey;
    }

    public Widget getItemWidget() {
        return itemWidget;
    }

    public void setItemWidget(Widget itemWidget) {
        this.itemWidget = itemWidget;
    }
    
    public void setRemoveCallback(Callback<MultiplicityItem> callback){
        removeCallback = callback;
    }
    
    public Callback<MultiplicityItem> getRemoveCallback(){
        return removeCallback;
    }
        
    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean created) {
        this.isCreated = created;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
        if(isDeleted)
            this.isCreated = false; 
    }
    
    public abstract void redraw();

    public abstract void clear();
    
}
