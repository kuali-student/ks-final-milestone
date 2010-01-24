/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

package org.kuali.student.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;

/**
 * This is a description of what this class does - Rich don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class AuthenticationGwtRpcFilter extends SpringSecurityFilter {
    
    public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        
        String URI = request.getRequestURI();
        
        if(URI.endsWith(".html") && !URI.endsWith(".cache.html")){
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
        }
        filterChain.doFilter(request, response);
    }

    public int getOrder(){
        return FilterChainOrder.CAS_PROCESSING_FILTER + 1;
    }
}
