/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by haroon on 2013-05-10
 */
package org.kuali.rice.krad.web.listener;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.document.authorization.PessimisticLock;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

/**
 * Used to handle session timeouts where {@link PessimisticLock} objects should
 * be removed from a document.
 *
 * TODO: workaround for KULRICE-9467 (remove class and it's use in web.xml once rice issue is fixed)
 * Class was created to bypass a NullPointerException
 * in {@link KualiHttpSessionListener}, when spring security invalidated a non-existent session.
 *
 * @author Kuali Student Team
 */
public class KualiStudentHttpSessionListener implements HttpSessionListener {
    /**
     * Notification that a session was created.
     *
     * @param se the notification event
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // no operation required at this time
    }

    /**
     * Notification that a session is about to be invalidated.
     *
     * @param se the notification event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        releaseLocks();
    }

    /**
     * Remove any locks that the user has for this session
     */
    private void releaseLocks() {
        UserSession userSession = GlobalVariables.getUserSession();

        if (userSession == null) return;

        String sessionId = userSession.getKualiSessionId();
        List<PessimisticLock> locks = KRADServiceLocatorWeb.getPessimisticLockService().getPessimisticLocksForSession(
                sessionId);
        Person user = userSession.getPerson();

        KRADServiceLocatorWeb.getPessimisticLockService().releaseAllLocksForUser(locks, user);
    }
}