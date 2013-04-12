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
 * Created by David Yin on 12/4/12
 */
package org.kuali.student.enrollment.class2.courseoffering.util;

import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.web.controller.UifHandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KsHandlerExceptionResolver extends UifHandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(ex instanceof AuthorizationException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ModelAndView("/ks-enroll/ftl/permissiondenied");
        }
        return super.resolveException(request, response, handler, ex);
    }
}
