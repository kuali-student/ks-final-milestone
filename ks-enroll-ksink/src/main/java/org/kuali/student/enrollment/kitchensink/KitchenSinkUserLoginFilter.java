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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This class will set a default user for KS Kitchen Sink pages
 *
 * @author Kuali Student Team
 */
public class KitchenSinkUserLoginFilter extends org.kuali.rice.kew.web.UserLoginFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if ((request instanceof HttpServletRequest)
        &&  (null == KRADUtils.getUserSessionFromRequest((HttpServletRequest) request))) {
            String url = ((HttpServletRequest)request).getRequestURL().toString();

            if (url.endsWith("/kr-krad/kitchensink")) {
                String principalName = ConfigContext.getCurrentContextConfig().getProperty("kitchensink.login.autouser");
                if (null == principalName) {
                    throw new IllegalStateException("The 'kitchensink.login.autouser' user name parameter is not configured.");
                }

                //TODO - super's private method establishUserSession() needs to execute in full, so the two lines
                //       of code below need to be replaced with something that somehow sets the principal name so
                //       this statement gets the 'kitchensink.login.autouser' name:
                //  String principalName = ((AuthenticationService) GlobalResourceLoader.getResourceLoader()
                //          .getService(new QName("kimAuthenticationService"))).getPrincipalName(request);
                UserSession userSession = new UserSession(principalName);
                ((HttpServletRequest)request).getSession().setAttribute(KRADConstants.USER_SESSION_KEY, userSession);
            }
        }

        super.doFilter(request, response, chain);
    }

}
