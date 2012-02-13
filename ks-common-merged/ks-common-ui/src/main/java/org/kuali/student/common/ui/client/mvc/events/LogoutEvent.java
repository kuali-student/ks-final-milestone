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

package org.kuali.student.common.ui.client.mvc.events;

import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEvent;

/**
 * Example UncheckedApplicationEvent that can be fired on logout. TODO replace or modify this event when implementing
 * authn/authz code
 * 
 * @author Kuali Student Team
 */
public class LogoutEvent extends UncheckedApplicationEvent<LogoutHandler> {
    public static final Type<LogoutHandler> TYPE = new Type<LogoutHandler>();

    @Override
    protected void dispatch(LogoutHandler handler) {
        handler.onLogout(this);
    }

    @Override
    public Type<LogoutHandler> getAssociatedType() {
        return TYPE;
    }

}
