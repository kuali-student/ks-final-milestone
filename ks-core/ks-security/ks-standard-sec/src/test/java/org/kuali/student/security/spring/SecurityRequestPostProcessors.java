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
 */
package org.kuali.student.security.spring;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Uses a {@link RequestPostProcessor} to add request-building methods for establishing
 * a security context for Spring Security. Found from samples at:
 * https://github.com/spring-projects/spring-test-mvc/blob/master/src/test/java/org/springframework/test/web/server/samples/context/SecurityRequestPostProcessors.java
 *
 * @author Kuali Student Team
 */
public final class SecurityRequestPostProcessors {

	/**
	 * Establish a security context for a user with the specified username. All
	 * details are declarative and do not require that the user actually exists.
	 * This means that the authorities or roles need to be specified too.
	 */
	public static UserRequestPostProcessor user(String username) {
		return new UserRequestPostProcessor(username);
	}

	/** Support class for {@link RequestPostProcessor}'s that establish a Spring Security context */
	private static abstract class SecurityContextRequestPostProcessorSupport {

		private SecurityContextRepository repository = new HttpSessionSecurityContextRepository();

		final void save(Authentication authentication, HttpServletRequest request) {
			SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
			securityContext.setAuthentication(authentication);
			save(securityContext, request);
		}

		final void save(SecurityContext securityContext, HttpServletRequest request) {
			HttpServletResponse response = new MockHttpServletResponse();

			HttpRequestResponseHolder requestResponseHolder = new HttpRequestResponseHolder(request, response);
			this.repository.loadContext(requestResponseHolder);

			request = requestResponseHolder.getRequest();
			response = requestResponseHolder.getResponse();

			this.repository.saveContext(securityContext, request, response);
		}
	}

	public final static class UserRequestPostProcessor
			extends SecurityContextRequestPostProcessorSupport implements RequestPostProcessor {

		private final String username;

		private static final String ROLE_PREFIX = "ROLE_";

		private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		private UserRequestPostProcessor(String username) {
			Assert.notNull(username, "username cannot be null");
			this.username = username;
		}

		/**
		 * Specify the roles of the user to authenticate as.
		 */
		public UserRequestPostProcessor roles(String... roles) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roles.length);
			for(String role : roles) {
				if(role.startsWith(ROLE_PREFIX)) {
					authorities.add(new SimpleGrantedAuthority(role));
				} else {
					authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
				}
			}
            this.authorities = authorities;
			return this;
		}

		public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(this.username, null, this.authorities);
			save(authentication,request);
			return request;
		}
	}

	private SecurityRequestPostProcessors() {}
}
