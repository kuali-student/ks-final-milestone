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

package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



/**
 * KSToolTip is a PopupPanel that can show near the user's mouse location when show() is called.
 * It contains a header and content section.
 * 
 * @author Kuali Student Team
 *
 */
public class KSToolTip extends PopupPanel {

    //the distance from mouse to the tooltip
    public static final int DEF_MOUSE_OFFSET_X = 20;
    public static final int DEF_MOUSE_OFFSET_Y = 20;

    private KSLabel header;

    private VerticalPanel container = new VerticalPanel();

    /**
     * This creates a new tooltip with the headerText used as the header and content
     * used as the tooltip's content
     * 
     * @param headerText the text to used as this tooltip's header
     * @param content the Widget to be used as this tooltip's content
     */
    public KSToolTip(String headerText, Widget content) {
        super();
        init(headerText, content);
    }

    //initialization
    /**
     * Initializes this tooltip widget
     * 
     * @param headerText the text to used as this tooltip's header
     * @param content the Widget to be used as this tooltip's content
     */
    private void init(String headerText, Widget content){
        this.header = new KSLabel(headerText);
        
        this.setStyleName(KSStyles.KS_TOOLTIP);
        header.setStyleName(KSStyles.KS_TOOLTIP_HEADER);
        content.setStyleName(KSStyles.KS_TOOLTIP_CONTENT);
        
        container.add(header);
        container.add(content);

        setWidget(container);

    }

    /**
     * Show this tooltip at the mouse location determined from the MouseOverEvent
     * passed in.
     * 
     * @param event the event to be used to determine where to show the tooltip.
     */
    public void show(MouseOverEvent event) {

        int x = event.getClientX() + DEF_MOUSE_OFFSET_X;
        int y = event.getClientY() + DEF_MOUSE_OFFSET_Y;
        this.setPopupPosition(x, y);
       
        super.show();
    }

    /**
     * Hides the tooltip.
     */
    public void hide() {
        super.hide();
    }
}

