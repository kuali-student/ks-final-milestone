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
