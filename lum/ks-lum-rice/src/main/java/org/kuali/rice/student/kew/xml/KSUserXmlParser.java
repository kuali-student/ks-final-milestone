package org.kuali.rice.student.kew.xml;

import java.security.GeneralSecurityException;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.Namespace;
import org.kuali.rice.kew.xml.UserXmlParser;
import org.kuali.rice.kim.bo.entity.impl.KimPrincipalImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

/**
 * Adds password to the User xml ingestion
 *
 */
public class KSUserXmlParser extends UserXmlParser {
	
    protected final Logger LOG = Logger.getLogger(getClass());
	
    private static final Namespace NAMESPACE = Namespace.getNamespace("", "ns:workflow/User");

    private static final String WORKFLOW_ID_ELEMENT = "workflowId";
    private static final String AUTHENTICATION_ID_ELEMENT = "authenticationId";
    private static final String PRINCIPAL_ID_ELEMENT = "principalId";
    private static final String PRINCIPAL_NAME_ELEMENT = "principalName";
    private static final String PASSWORD_ELEMENT = "password";
    private static final String HASH_SUFFIX = "(&^HSH#&)";
    
    @Override
	protected KimPrincipalImpl constructPrincipal(Element userElement, String entityId) {
    	String principalId = userElement.getChildTextTrim(WORKFLOW_ID_ELEMENT, NAMESPACE);
    	if (principalId == null) {
    		principalId = userElement.getChildTextTrim(PRINCIPAL_ID_ELEMENT, NAMESPACE);
    	}
    	String principalName = userElement.getChildTextTrim(AUTHENTICATION_ID_ELEMENT, NAMESPACE);
    	if (principalName == null) {
    		principalName = userElement.getChildTextTrim(PRINCIPAL_NAME_ELEMENT, NAMESPACE);
    	}
    	String password= userElement.getChildTextTrim(PASSWORD_ELEMENT, NAMESPACE);
    	
    	
		KimPrincipalImpl principal = new KimPrincipalImpl();
		principal.setActive(true);
		principal.setPrincipalId(principalId);
		principal.setPrincipalName(principalName);
		principal.setEntityId(entityId);
		try {
			principal.setPassword(KNSServiceLocator.getEncryptionService().hash(password)+HASH_SUFFIX);
		} catch (GeneralSecurityException e) {
			LOG.warn("Error hashing password.",e);
		}
		KNSServiceLocator.getBusinessObjectService().save(principal);
		
		return principal;
    }
}
