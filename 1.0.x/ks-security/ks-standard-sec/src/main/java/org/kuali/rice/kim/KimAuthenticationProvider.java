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

package org.kuali.rice.kim;

import org.kuali.student.security.spring.KSRiceDefaultUserDetailsService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.providers.dao.DaoAuthenticationProvider;
import org.springframework.security.providers.dao.SaltSource;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.PlaintextPasswordEncoder;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.util.Assert;

/**
 * An {@link AuthenticationProvider} implementation that retrieves user details
 * from an {@link UserDetailsService}. Slightly modified from {@link DaoAuthenticationProvider}
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class KimAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    
    //~ Instance fields ================================================================================================

    private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();

    private SaltSource saltSource;

    private UserDetailsService userDetailsService;

    private boolean includeDetailsObject = true;

    //~ Methods ========================================================================================================

    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object salt = null;

        if (this.saltSource != null) {
            salt = this.saltSource.getSalt(userDetails);
        }

        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
                    includeDetailsObject ? userDetails : null);
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
                    includeDetailsObject ? userDetails : null);
        }
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;

        UserDetailsService ksRiceDefaultUserDetailsService = this.getUserDetailsService();
        if(!(ksRiceDefaultUserDetailsService instanceof KSRiceDefaultUserDetailsService)){
            throw new AuthenticationServiceException(
                "UserDetailsService is not an an instance of KSRiceDefaultUserDetailsService");
        }
        
        try {
            //loadedUser = this.getUserDetailsService().loadUserByUsername(username);
            loadedUser = ((KSRiceDefaultUserDetailsService)ksRiceDefaultUserDetailsService).loadUserByUsernameAndToken(username, authentication);
        }
        catch (DataAccessException repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new AuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    /**
     * Sets the PasswordEncoder instance to be used to encode and validate passwords.
     * If not set, {@link PlaintextPasswordEncoder} will be used by default.
     *
     * @param passwordEncoder The passwordEncoder to use
     */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * The source of salts to use when decoding passwords. <code>null</code>
     * is a valid value, meaning the <code>DaoAuthenticationProvider</code>
     * will present <code>null</code> to the relevant <code>PasswordEncoder</code>.
     *
     * @param saltSource to use when attempting to decode passwords via the <code>PasswordEncoder</code>
     */
    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    protected SaltSource getSaltSource() {
        return saltSource;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    protected boolean isIncludeDetailsObject() {
        return includeDetailsObject;
    }
    
    public static class UserWithId extends User{
		private static final long serialVersionUID = 1L;
		private String userId;
		public UserWithId(String username, String password,
				boolean enabled, boolean accountNonExpired,
				boolean credentialsNonExpired, boolean accountNonLocked,
				GrantedAuthority[] authorities) throws IllegalArgumentException {
			super(username, password, enabled, accountNonExpired, credentialsNonExpired,
					accountNonLocked, authorities);
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
    }

//	@Override
//	protected Authentication createSuccessAuthentication(Object principal,
//			Authentication authentication, UserDetails user) {
//		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal,
//                authentication.getCredentials(), user.getAuthorities());
//        result.setDetails(authentication.getDetails());
//        //Add the userId back in here
//		if(user instanceof UserWithId){
//			result.setDetails(((UserWithId)user).getUserId());
//		}
//		return result;
//	}
}
