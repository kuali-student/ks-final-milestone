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

import com.google.gwt.user.client.ui.Widget;

/**
 * This is a read only version of MultiplicityComposite for use in read only screens.
 * 
 * 
 * @author Kuali Student Team
 *
 */

/**
 * @deprecated
 */
public abstract class DisplayMultiplicityComposite extends MultiplicityComposite {
   
    protected String itemLabel;

    public DisplayMultiplicityComposite(){
    	super(StyleType.SUB_LEVEL);
    }
    
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite#getItemDecorator()
     */
    @Override
    public MultiplicityItem getItemDecorator(StyleType style) {
    	DisplayItem item = new DisplayItem(style);
    	if (itemLabel != null) {
          item.setItemLabel(itemLabel + " " + itemCount);
    	}

        return item;
    }
    
    @Override
    public Widget generateAddWidget() {
        return null;        
    }
    
    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }
 
}
