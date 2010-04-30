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

package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.configurable.mvc.Section;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite.StyleType;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a read only version of MultiplicityItem for use in read only multiplicity composites.
 * 
 * FIXME: Initial checkin - still needs fleshing out and more testing!
 *
 * @author Kuali Student Team
 */
public class DisplayItem extends MultiplicityItem {
    private boolean loaded = false;
	private KSLabel itemLabel;

    protected FlowPanel itemPanel = new FlowPanel();
    
	private StyleType style;

    
    public DisplayItem(){        
        initWidget(itemPanel);
    }

	public DisplayItem(StyleType style){
		this.style = style;
		this.initWidget(itemPanel);
		
	}
    public void onLoad(){
    }
    
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem#clear()
     */
    @Override
    public void clear() {
        loaded = false;
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem#redraw()
     */
    @Override
    public void redraw() {
        Widget item = getItemWidget();
        if (!loaded){
       
            if (itemLabel != null) {
                itemPanel.add(itemLabel);
             	itemLabel.addStyleName("KS-Multiplicity-Item-Header");
            }
            itemPanel.add(item);
            itemPanel.addStyleName("KS-Multiplicity-Display-Item");
            loaded = true;
        }

        if (item instanceof Section){
            ((Section)item).redraw();
        }
    }

	
    public void setItemLabel(String itemLabel) {
        this.itemLabel = new KSLabel(itemLabel);
    }
    
}
