/**
 * 
 */
package org.kuali.student.lum.kim;

import java.security.GeneralSecurityException;

import javax.jws.WebService;

import org.kuali.rice.core.service.EncryptionService;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.rice.kim.service.impl.IdentityServiceImpl;
import org.kuali.rice.kim.util.KIMWebServiceConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.impl.DocumentServiceImpl;

/**
 * This service override is used to facilitate a fix to the encrypted passwords in the
 * Rice database.
 * 
 * @author delyea
 *
 */
@WebService(endpointInterface = KIMWebServiceConstants.IdentityService.INTERFACE_CLASS, serviceName = KIMWebServiceConstants.IdentityService.WEB_SERVICE_NAME, portName = KIMWebServiceConstants.IdentityService.WEB_SERVICE_PORT, targetNamespace = KIMWebServiceConstants.MODULE_TARGET_NAMESPACE)
public class StudentIdentityServiceImpl extends IdentityServiceImpl implements IdentityService {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DocumentServiceImpl.class);

	/* (non-Javadoc)
	 * @see org.kuali.rice.kim.service.impl.IdentityServiceImpl#getPrincipalByPrincipalNameAndPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public KimPrincipalInfo getPrincipalByPrincipalNameAndPassword(String principalName, String password) {
		try {
		    String finalPassword = KNSServiceLocator.getEncryptionService().hash(password)+ EncryptionService.HASH_POST_PREFIX;
			return super.getPrincipalByPrincipalNameAndPassword(principalName, finalPassword);
		} catch (GeneralSecurityException e) {
			String message = "Caught Exception attempting to encrypt password (with length " + password.length() + ") for principalName: " + principalName;
			LOG.error(message, e);
			throw new RuntimeException(e);
		}
	}

}
