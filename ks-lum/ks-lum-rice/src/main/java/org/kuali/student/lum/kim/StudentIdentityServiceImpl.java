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

/**
 *
 */
package org.kuali.student.lum.kim;

import java.security.GeneralSecurityException;

import javax.jws.WebService;

import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.encryption.EncryptionService;
import org.kuali.rice.kim.api.KimApiConstants;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.impl.identity.IdentityServiceImpl;
import org.kuali.rice.krad.service.impl.DocumentServiceImpl;

/**
 * This service override is used to facilitate a fix to the encrypted passwords in the
 * Rice database.
 *
 * @author delyea
 *
 */

// TODO: RICE=M9 UPGRADE Check that the values for the portName and targetNamespace still make sense for rice-2.0
// @see org.kuali.rice.kim.api.KimApiConstants.KIM_NAMESPACE_PREFIX
@WebService(endpointInterface = KimIdentityServiceConstants.INTERFACE_CLASS, serviceName = KimApiConstants.ServiceNames.IDENTITY_SERVICE_SOAP, portName = KimIdentityServiceConstants.WEB_SERVICE_PORT, targetNamespace = KimIdentityServiceConstants.MODULE_TARGET_NAMESPACE)
public class StudentIdentityServiceImpl extends IdentityServiceImpl implements IdentityService {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DocumentServiceImpl.class);

	/* (non-Javadoc)
	 * @see org.kuali.rice.kim.service.impl.IdentityServiceImpl#getPrincipalByPrincipalNameAndPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public Principal getPrincipalByPrincipalNameAndPassword(String principalName, String password) {
		try {
		    String finalPassword = CoreApiServiceLocator.getEncryptionService().hash(password)+ EncryptionService.HASH_POST_PREFIX;
			return super.getPrincipalByPrincipalNameAndPassword(principalName, finalPassword);
		} catch (GeneralSecurityException e) {
			String message = "Caught Exception attempting to encrypt password (with length " + password.length() + ") for principalName: " + principalName;
			LOG.error(message, e);
			throw new RuntimeException(e);
		}
	}

}
