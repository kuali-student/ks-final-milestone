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

package org.kuali.rice.student.kew.xml;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.jdom.Namespace;
import org.kuali.rice.kew.xml.UserXmlParser;

import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.kew.xml.UserXmlParser;
import org.kuali.rice.kim.impl.identity.affiliation.EntityAffiliationBo;
import org.kuali.rice.kim.impl.identity.email.EntityEmailBo;
import org.kuali.rice.kim.impl.identity.employment.EntityEmploymentBo;
import org.kuali.rice.kim.impl.identity.entity.EntityBo;
import org.kuali.rice.kim.impl.identity.name.EntityNameBo;
import org.kuali.rice.kim.impl.identity.principal.PrincipalBo;
import org.kuali.rice.kim.impl.identity.type.EntityTypeContactInfoBo;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.SequenceAccessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Adds password to the User xml ingestion
 *
 */
public class KSUserXmlParser extends UserXmlParser {
	
    private static final Logger LOG = LoggerFactory.getLogger(KSUserXmlParser.class);
	
    private static final Namespace NAMESPACE = Namespace.getNamespace("", "ns:workflow/User");

    private static final String WORKFLOW_ID_ELEMENT = "workflowId";
    private static final String AUTHENTICATION_ID_ELEMENT = "authenticationId";
    private static final String PRINCIPAL_ID_ELEMENT = "principalId";
    private static final String PRINCIPAL_NAME_ELEMENT = "principalName";
    private static final String EMPL_ID_ELEMENT = "emplId";
    private static final String EMAIL_ELEMENT = "emailAddress";
    private static final String GIVEN_NAME_ELEMENT = "givenName";
    private static final String LAST_NAME_ELEMENT = "lastName";    
    private static final String TYPE_ELEMENT = "type";
    private static final String PASSWORD_ELEMENT = "password";
    private static final String HASH_SUFFIX = "(&^HSH#&)";

	private static final String AFFILIATION_CD_ELEMENT = "affiliationTypeCode";
	private static final String ACTIVE_ELEMENT = "active";
	private static final String CAMPUS_CD_ELEMENT = "campusCode";
	
    @Override
    protected EntityBo constructEntity(Element userElement) {
        SequenceAccessorService sas = KNSServiceLocator.getSequenceAccessorService();
    	
    	String firstName = userElement.getChildTextTrim(GIVEN_NAME_ELEMENT, NAMESPACE);
        String lastName = userElement.getChildTextTrim(LAST_NAME_ELEMENT, NAMESPACE);
        String emplId = userElement.getChildTextTrim(EMPL_ID_ELEMENT, NAMESPACE);
        String entityTypeCode = userElement.getChildTextTrim(TYPE_ELEMENT, NAMESPACE);
        if (StringUtils.isBlank(entityTypeCode)) {
        	entityTypeCode = "PERSON";
        }
    	
        Long entityId = sas.getNextAvailableSequenceNumber("KRIM_ENTITY_ID_S", 
        		EntityEmploymentBo.class);
        
        // if they define an empl id, let's set that up
        EntityEmploymentBo emplInfo = null;
        if (!StringUtils.isBlank(emplId)) {
        	emplInfo = new EntityEmploymentBo();
        	emplInfo.setActive(true);
        	emplInfo.setEmployeeId(emplId);
        	emplInfo.setPrimary(true);
        	emplInfo.setEntityId("" + entityId);
        }
        
    	
		EntityBo entity = new EntityBo();
		
		//Set active on the entity
		String active = userElement.getChildTextTrim(ACTIVE_ELEMENT, NAMESPACE);
		if("false".equals(active)){
			entity.setActive(false);
		}else{
			entity.setActive(true);
        }

        entity.setId("" + entityId);
		List<EntityEmploymentBo> emplInfos = new ArrayList<EntityEmploymentBo>();
		if (emplInfo != null) {
			emplInfos.add(emplInfo);
		}
		entity.setEmploymentInformation(emplInfos);

		//Add affiliations
		String affiliationTypeCode = userElement.getChildTextTrim(AFFILIATION_CD_ELEMENT, NAMESPACE);
		if (!StringUtils.isBlank(affiliationTypeCode)) {
			if(entity.getAffiliations()==null){
				entity.setAffiliations(new ArrayList<EntityAffiliationBo>());
			}
			EntityAffiliationBo affiliation = new EntityAffiliationBo();

            Long affiliationId = sas.getNextAvailableSequenceNumber(
					"KRIM_ENTITY_AFLTN_ID_S", EntityAffiliationBo.class);
			affiliation.setId(""+affiliationId);
			affiliation.setAffiliationTypeCode(affiliationTypeCode);
			affiliation.setActive(true);
			affiliation.setDefaultValue(true);
			affiliation.setEntityId(entity.getId());
			String campusCode = userElement.getChildTextTrim(CAMPUS_CD_ELEMENT, NAMESPACE);
			if(!StringUtils.isBlank(campusCode)){
				affiliation.setCampusCode(campusCode);
			}
			entity.getAffiliations().add(affiliation);
		}		
		
		EntityTypeContactInfoBo entityType = new EntityTypeContactInfoBo();
		entity.getEntityTypeContactInfos().add(entityType);
		entityType.setEntityTypeCode(entityTypeCode);
		entityType.setEntityId(entity.getId());
		entityType.setActive(true);
		
		if (!StringUtils.isBlank(firstName) || !StringUtils.isBlank(lastName)) {
			Long entityNameId = sas.getNextAvailableSequenceNumber(
					"KRIM_ENTITY_NM_ID_S", EntityNameBo.class);
			EntityNameBo name = new EntityNameBo();
            name.setActive(true);
            name.setId("" + entityNameId);
            name.setEntityId(entity.getId());
            // must be in krim_ent_nm_typ_t.ent_nm_typ_cd
            name.setNameCode("PRFR");
            name.setFirstName(firstName);
            name.setMiddleName("");
            name.setLastName(lastName);
            name.setDefaultValue(true);

			entity.getNames().add(name);
		}

        KradDataServiceLocator.getDataObjectService().save(entity);
		
		String emailAddress = userElement.getChildTextTrim(EMAIL_ELEMENT, NAMESPACE);
		if (!StringUtils.isBlank(emailAddress)) {
			Long emailId = sas.getNextAvailableSequenceNumber(
					"KRIM_ENTITY_EMAIL_ID_S", EntityEmailBo.class);
			EntityEmailBo email = new EntityEmailBo();
            email.setActive(true);
			email.setEntityTypeCode("PERSON");
			// must be in krim_email_typ_t.email_typ_cd:
			email.setEmailTypeCode("WRK");
			email.setEmailAddress(emailAddress);
			email.setDefaultValue(true);
			email.setEntityId(entity.getId());

			KradDataServiceLocator.getDataObjectService().save(email);
		}
		
		return entity;
    }
    
    @Override
	protected PrincipalBo constructPrincipal(Element userElement, String entityId) {
    	String principalId = userElement.getChildTextTrim(WORKFLOW_ID_ELEMENT, NAMESPACE);
    	if (principalId == null) {
    		principalId = userElement.getChildTextTrim(PRINCIPAL_ID_ELEMENT, NAMESPACE);
    	}
    	String principalName = userElement.getChildTextTrim(AUTHENTICATION_ID_ELEMENT, NAMESPACE);
    	if (principalName == null) {
    		principalName = userElement.getChildTextTrim(PRINCIPAL_NAME_ELEMENT, NAMESPACE);
    	}
    	String password= userElement.getChildTextTrim(PASSWORD_ELEMENT, NAMESPACE);
    	
    	
		PrincipalBo principal = new PrincipalBo();
		principal.setActive(true);
		principal.setPrincipalId(principalId);
		principal.setPrincipalName(principalName);
		principal.setEntityId(entityId);
		try {
			principal.setPassword(CoreApiServiceLocator.getEncryptionService().hash(password)+HASH_SUFFIX);
		} catch (GeneralSecurityException e) {
			LOG.warn("Error hashing password.",e);
		}
        KradDataServiceLocator.getDataObjectService().save(principal);
		
		return principal;
    }
}
