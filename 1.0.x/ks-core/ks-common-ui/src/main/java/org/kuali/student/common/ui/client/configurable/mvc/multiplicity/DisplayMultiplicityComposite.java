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
 * FIXME: Initial checkin - still needs fleshing out and more testing!
 * 
 * @author Kuali Student Team
 *
 */
public abstract class DisplayMultiplicityComposite extends MultiplicityComposite {
   
    public DisplayMultiplicityComposite(){
    	super(StyleType.SUB_LEVEL);
    }
    
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite#getItemDecorator()
     */
    @Override
    public MultiplicityItem getItemDecorator(StyleType style) {
        return new DisplayItem(style);
    }
    
    @Override
    public Widget generateAddWidget() {
        return null;        
    }
 
}
