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
package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.GlassPanel;

/**
 * 
 * */
public class KSLightBox {
    private final PopupPanel pop = new PopupPanel(false, true);
    private final GlassPanel glass = new GlassPanel(false);
    private final AbsolutePanel parentPanel;
    private boolean showing = false;

    public KSLightBox() {
        this.parentPanel = RootPanel.get();
        Window.addResizeHandler(new ResizeHandler(){

            @Override
            public void onResize(ResizeEvent event) {
                if(showing){
                    pop.center();
                }
            }
        });
    }

    public void show() {
        if (!showing) {
            glass.getElement().setAttribute("zIndex", "" + KSZIndexStack.pop());
            parentPanel.add(glass, 0, 0);
            pop.getElement().setAttribute("zIndex", "" + KSZIndexStack.pop());
        }
        pop.center();
        showing = true;
    }

    public void hide() {
        pop.hide();
        KSZIndexStack.push();
        parentPanel.remove(glass);
        KSZIndexStack.push();
        showing = false;
    }

    public Widget getWidget() {
        return pop.getWidget();
    }

    public void setWidget(Widget w) {
        pop.setWidget(w);
    }
    public void add(CloseHandler handler){
        pop.addCloseHandler(handler);
    }

	public boolean isShowing() {
		return showing;
	}

    
}
