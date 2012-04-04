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

package org.kuali.student.common.ui.client.mvc.history;

import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEvent;

import com.google.gwt.user.client.ui.Widget;

/**
 * Event that is fired when a history token should be logged on the history stack
 * 
 * @author Kuali Student Team
 *
 */
@Deprecated
public class NavigationEvent extends UncheckedApplicationEvent<NavigationEventHandler>{
    public static final Type<NavigationEventHandler> TYPE = new Type<NavigationEventHandler>();
    
    private final Widget originatingWidget;
    
    public NavigationEvent(final Widget originatingWidget) {
        this.originatingWidget = originatingWidget;
    }
    
    @Override
    protected void dispatch(NavigationEventHandler handler) {
        handler.onNavigationEvent(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<NavigationEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Widget getOriginatingWidget() {
        return originatingWidget;
    }

}
