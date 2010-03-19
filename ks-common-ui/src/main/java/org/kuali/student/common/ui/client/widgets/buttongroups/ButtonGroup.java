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
package org.kuali.student.common.ui.client.widgets.buttongroups;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonLayout;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class ButtonGroup<T extends ButtonEnum> extends Composite{
    private List<Callback<T>> callbacks = new ArrayList<Callback<T>>();
    protected Map<T, KSButton> buttonMap = new HashMap<T, KSButton>();
    protected ButtonLayout layout;

    public void addCallback(Callback<T> callback) {
        callbacks.add(callback);
    }

    public List<Callback<T>> getCallbacks() {
        return callbacks;
    }
    
    protected void sendCallbacks(T type){
        for(Callback<T> c: getCallbacks()){
            c.exec(type);
        }
    }
    
    public void setButtonText(T key, String text){
        buttonMap.get(key).setText(text);
    }
    
    public KSButton getButton(T key){
        return buttonMap.get(key);
    }
    
    /**
     * This method is optional, the button panel can be contained inside of a parent panel which
     * will limit it's maximum size.
     * 
     * This method provides an alternative, the button panel will "wrap" the content and become the
     * proper size based on maximum size of the content. 
     * 
     * @param content - the content the button panel will align itself next to
     */
    public void setContent(Widget content){
        layout.setContent(content);
    }
}
