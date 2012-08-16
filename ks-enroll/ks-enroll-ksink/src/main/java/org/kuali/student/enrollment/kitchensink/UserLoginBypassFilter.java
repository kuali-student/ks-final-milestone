/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by bobhurt on 8/9/12
 */
package org.kuali.student.enrollment.kitchensink;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.filter.AutoLoginFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class UserLoginBypassFilter extends org.kuali.rice.kew.web.UserLoginFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        /*
        if (null == KRADUtils.getUserSessionFromRequest((HttpServletRequest) request)) {
            if (request instanceof HttpServletRequest) {
                //TODO - refactor 'if' statements above, and make sure URL includes "kitchensink"
                String url = ((HttpServletRequest)request).getRequestURL().toString();
                String queryString = ((HttpServletRequest)request).getQueryString();

                String principalName = ConfigContext.getCurrentContextConfig().getProperty(AutoLoginFilter.USER_PARAM_NAME);
                if (null == principalName) {
                    throw new IllegalStateException("The '"+ AutoLoginFilter.USER_PARAM_NAME +"' parameter is not set.");
                }
                UserSession userSession = new UserSession(principalName);
                ((HttpServletRequest)request).getSession().setAttribute(KRADConstants.USER_SESSION_KEY, userSession);
            }
        }*/

        //TODO - why is the ClassCastException being thrown?
        try {
            super.doFilter(request, response, chain);
        }
        catch(ClassCastException ex) {
            // do nothing ?
        }
    }

}
