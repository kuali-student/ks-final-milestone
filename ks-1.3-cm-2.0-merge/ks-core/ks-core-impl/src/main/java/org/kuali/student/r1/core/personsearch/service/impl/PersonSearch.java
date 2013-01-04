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

package org.kuali.student.r1.core.personsearch.service.impl;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.external.EntityExternalIdentifierType;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.identity.type.EntityTypeContactInfoDefault;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.rice.kim.impl.identity.PersonServiceImpl;
import org.kuali.rice.kns.service.BusinessObjectMetaDataService;
import org.kuali.rice.kns.service.MaintenanceDocumentDictionaryService;
import org.kuali.rice.krad.util.KRADPropertyConstants;

/**
 * Utility methods for dealing with Person searches
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Deprecated
public class PersonSearch {
    
    private static Logger LOG = Logger.getLogger( PersonSearch.class );
    protected static final String ENTITY_EXT_ID_PROPERTY_PREFIX = "externalIdentifiers.";
    protected static final String ENTITY_AFFILIATION_PROPERTY_PREFIX = "affiliations.";
    protected static final String ENTITY_TYPE_PROPERTY_PREFIX = "entityTypeContactInfos.";
    protected static final String ENTITY_EMAIL_PROPERTY_PREFIX = "entityTypeContactInfos.emailAddresses.";
    protected static final String ENTITY_PHONE_PROPERTY_PREFIX = "entityTypeContactInfos.phoneNumbers.";
    protected static final String ENTITY_ADDRESS_PROPERTY_PREFIX = "entityTypeContactInfos.addresses.";
    protected static final String ENTITY_NAME_PROPERTY_PREFIX = "names.";
    protected static final String PRINCIPAL_PROPERTY_PREFIX = "principals.";
    protected static final String ENTITY_EMPLOYEE_ID_PROPERTY_PREFIX = "employmentInformation.";
    // KULRICE-4442 Special handling for extension objects
    protected static final String EXTENSION = "extension";
    
    private IdentityService identityService;
    private RoleService roleService;
    private BusinessObjectMetaDataService businessObjectMetaDataService;
    private MaintenanceDocumentDictionaryService maintenanceDocumentDictionaryService;

    protected List<String> personEntityTypeCodes = new ArrayList<String>( 4 );
    // String that can be passed to the lookup framework to create an type = X OR type = Y criteria
    private String personEntityTypeLookupCriteria = null;
   
    protected Map<String,String> baseLookupCriteria = new HashMap<String,String>();
    protected Map<String,String> criteriaConversion = new HashMap<String,String>();
    protected ArrayList<String> personCachePropertyNames = new ArrayList<String>();
    {
        // init the criteria which will need to be applied to every lookup against
        // the identity data tables
        baseLookupCriteria.put( KIMPropertyConstants.Person.ACTIVE, "Y" );
        baseLookupCriteria.put( ENTITY_TYPE_PROPERTY_PREFIX + KRADPropertyConstants.ACTIVE, "Y" );
        
        // create the field mappings between the Person object and the KimEntity object
        criteriaConversion.put( KIMPropertyConstants.Person.ENTITY_ID, KIMPropertyConstants.Entity.ID);
        criteriaConversion.put( KIMPropertyConstants.Person.ACTIVE, PRINCIPAL_PROPERTY_PREFIX + KRADPropertyConstants.ACTIVE );
        criteriaConversion.put( KIMPropertyConstants.Person.PRINCIPAL_ID, PRINCIPAL_PROPERTY_PREFIX + KIMPropertyConstants.Person.PRINCIPAL_ID );
        criteriaConversion.put( KIMPropertyConstants.Person.PRINCIPAL_NAME, PRINCIPAL_PROPERTY_PREFIX + KIMPropertyConstants.Person.PRINCIPAL_NAME );
        criteriaConversion.put( KIMPropertyConstants.Person.FIRST_NAME, "names.firstName" );
        criteriaConversion.put( KIMPropertyConstants.Person.LAST_NAME, "names.lastName" );
        criteriaConversion.put( KIMPropertyConstants.Person.MIDDLE_NAME, "names.middleName" );
        criteriaConversion.put( KIMPropertyConstants.Person.EMAIL_ADDRESS, "entityTypeContactInfos.emailAddresses.emailAddress" );
        criteriaConversion.put( KIMPropertyConstants.Person.PHONE_NUMBER, "entityTypeContactInfos.phoneNumbers.phoneNumber" );
        criteriaConversion.put( KIMPropertyConstants.Person.ADDRESS_LINE_1, "entityTypeContactInfos.addresses.line1" );
        criteriaConversion.put( KIMPropertyConstants.Person.ADDRESS_LINE_2, "entityTypeContactInfos.addresses.line2" );
        criteriaConversion.put( KIMPropertyConstants.Person.ADDRESS_LINE_3, "entityTypeContactInfos.addresses.line3" );
        criteriaConversion.put( KIMPropertyConstants.Person.CITY, "entityTypeContactInfos.addresses.city" );
        criteriaConversion.put( KIMPropertyConstants.Person.STATE_CODE, "entityTypeContactInfos.addresses.stateProvinceCode" );
        criteriaConversion.put( KIMPropertyConstants.Person.POSTAL_CODE, "entityTypeContactInfos.addresses.postalCode" );
        criteriaConversion.put( KIMPropertyConstants.Person.COUNTRY_CODE, "entityTypeContactInfos.addresses.countryCode" );
        criteriaConversion.put( KIMPropertyConstants.Person.CAMPUS_CODE, "affiliations.campusCode" );
        criteriaConversion.put( KIMPropertyConstants.Person.AFFILIATION_TYPE_CODE, "affiliations.affiliationTypeCode" );
        criteriaConversion.put( KIMPropertyConstants.Person.EXTERNAL_IDENTIFIER_TYPE_CODE, "externalIdentifiers.externalIdentifierTypeCode" );
        criteriaConversion.put( KIMPropertyConstants.Person.EXTERNAL_ID, "externalIdentifiers.externalId" );        
        criteriaConversion.put( KIMPropertyConstants.Person.EMPLOYEE_TYPE_CODE, "employmentInformation.employeeTypeCode" );
        criteriaConversion.put( KIMPropertyConstants.Person.EMPLOYEE_STATUS_CODE, "employmentInformation.employeeStatusCode" );
        criteriaConversion.put( KIMPropertyConstants.Person.EMPLOYEE_ID, "employmentInformation.employeeId" );
        criteriaConversion.put( KIMPropertyConstants.Person.BASE_SALARY_AMOUNT, "employmentInformation.baseSalaryAmount" );
        criteriaConversion.put( KIMPropertyConstants.Person.PRIMARY_DEPARTMENT_CODE, "employmentInformation.primaryDepartmentCode" );

        personCachePropertyNames.add( KIMPropertyConstants.Person.PRINCIPAL_ID );
        personCachePropertyNames.add( KIMPropertyConstants.Person.PRINCIPAL_NAME );
        personCachePropertyNames.add( KIMPropertyConstants.Person.ENTITY_ID );
        personCachePropertyNames.add( KIMPropertyConstants.Person.FIRST_NAME );
        personCachePropertyNames.add( KIMPropertyConstants.Person.LAST_NAME );
        personCachePropertyNames.add( KIMPropertyConstants.Person.MIDDLE_NAME );
        personCachePropertyNames.add( KIMPropertyConstants.Person.CAMPUS_CODE );
        personCachePropertyNames.add( KIMPropertyConstants.Person.EMPLOYEE_ID );
        personCachePropertyNames.add( KIMPropertyConstants.Person.PRIMARY_DEPARTMENT_CODE );
    }

    @SuppressWarnings("unchecked")
    protected List<Person> findPeopleInternal(IdentityService identityService, Map<String, String> criteria, boolean unbounded) {

        List<Person> people = new ArrayList<Person>();

        // TODO: map the old call parameters into the criteria parametsrs
//        identityService.lookupEntityDefault(criteria, unbounded);
        Map<String,String> entityCriteria = convertPersonPropertiesToEntityProperties( criteria );

        Predicate predicate = PredicateUtils.convertMapToPredicate(entityCriteria);

        QueryByCriteria.Builder queryBuilder = QueryByCriteria.Builder.create();
        queryBuilder.setPredicates(predicate);

        EntityDefaultQueryResults results = identityService.findEntityDefaults(queryBuilder.build());
        List<EntityDefault> entities = results.getResults();
        if (entities != null) {
            for (EntityDefault e : entities) {
                // get to get all principals for the entity as well
                for (Principal p : e.getPrincipals()) {
                    Person person = convertEntityToPerson(e, p);
                    if (person != null) {
                        people.add(person);
                    }
                }
            }
        }

//        if (entities instanceof CollectionIncomplete) {
//            return new CollectionIncomplete(people, ((CollectionIncomplete) entities).getActualSizeIfTruncated());
//        }
        return people;
    }

    protected Person convertEntityToPerson(EntityDefault entity, Principal principal) {
        try {
            // get the EntityEntityType for the EntityType corresponding to a Person
            EntityTypeContactInfoDefault entType = entity.getEntityType(PersonSearchServiceImpl.PERSON_ENTITY_TYPE);
            // if no "person" entity type present for the given principal, skip to the next type in the list
            if (entType == null) {
                return null;
            }
            // attach the principal and entity objects
            // PersonImpl has logic to pull the needed elements from the KimEntity-related classes
            return new KsPerson(entity, principal);

        } catch (Exception ex) {
            // allow runtime exceptions to pass through
            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException("Problem building person object", ex);
            }
        }
    }
    
    
    public Map<String,String> convertPersonPropertiesToEntityProperties( Map<String,String> criteria ) {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( "convertPersonPropertiesToEntityProperties: " + criteria );
        }
        boolean nameCriteria = false;
        boolean addressCriteria = false;
        boolean externalIdentifierCriteria = false;
        boolean affiliationCriteria = false;
        boolean affiliationDefaultOnlyCriteria = false;
        boolean phoneCriteria = false;
        boolean emailCriteria = false;
        boolean employeeIdCriteria = false;
        // add base lookups for all person lookups
        HashMap<String,String> newCriteria = new HashMap<String,String>();
        newCriteria.putAll( baseLookupCriteria );

        newCriteria.put( "entityTypeContactInfos.entityTypeCode", personEntityTypeLookupCriteria );

        if ( criteria != null ) {
            for ( String key : criteria.keySet() ) {
                //check active radio button
                if(key.equals(KIMPropertyConstants.Person.ACTIVE)) {
                    newCriteria.put(KIMPropertyConstants.Person.ACTIVE, criteria.get(KIMPropertyConstants.Person.ACTIVE));
                } else {
                    // The following if statement enables the "both" button to work correctly.
                    if (!(criteria.containsKey(KIMPropertyConstants.Person.ACTIVE))) {
                        newCriteria.remove( KIMPropertyConstants.Person.ACTIVE );
                    }
                }
                
                // if no value was passed, skip the entry in the Map
                if ( StringUtils.isEmpty( criteria.get(key) ) ) {
                    continue;
                }
                // check if the value needs to be encrypted
                // handle encrypted external identifiers
                if ( key.equals( KIMPropertyConstants.Person.EXTERNAL_ID ) && StringUtils.isNotBlank(criteria.get(key)) ) {
                    // look for a ext ID type property
                    if ( criteria.containsKey( KIMPropertyConstants.Person.EXTERNAL_IDENTIFIER_TYPE_CODE ) ) {
                        String extIdTypeCode = criteria.get(KIMPropertyConstants.Person.EXTERNAL_IDENTIFIER_TYPE_CODE);
                        if ( StringUtils.isNotBlank(extIdTypeCode) ) {
                            // if found, load that external ID Type via service
                            EntityExternalIdentifierType extIdType = getIdentityService().getExternalIdentifierType(extIdTypeCode);
                            // if that type needs to be encrypted, encrypt the value in the criteria map
                            if ( extIdType != null && extIdType.isEncryptionRequired() ) {
                                try {
                                    criteria.put(key, 
                                            CoreApiServiceLocator.getEncryptionService().encrypt(criteria.get(key))
                                            );
                                } catch (GeneralSecurityException ex) {
                                    LOG.error("Unable to encrypt value for external ID search of type " + extIdTypeCode, ex );
                                }                               
                            }
                        }
                    }
                }
                
                // convert the property to the Entity data model
                String entityProperty = criteriaConversion.get( key );
                if ( entityProperty != null ) {
                    newCriteria.put( entityProperty, criteria.get( key ) );
                } else {
                    entityProperty = key;
                    // just pass it through if no translation present
                    newCriteria.put( key, criteria.get( key ) );
                }
                // check if additional criteria are needed based on the types of properties specified
                if ( isNameEntityCriteria( entityProperty ) ) {
                    nameCriteria = true;
                }
                if ( isExternalIdentifierEntityCriteria( entityProperty ) ) {
                    externalIdentifierCriteria = true;
                }
                if ( isAffiliationEntityCriteria( entityProperty ) ) {
                    affiliationCriteria = true;
                }
                if ( isAddressEntityCriteria( entityProperty ) ) {
                    addressCriteria = true;
                }
                if ( isPhoneEntityCriteria( entityProperty ) ) {
                    phoneCriteria = true;
                }
                if ( isEmailEntityCriteria( entityProperty ) ) {
                    emailCriteria = true;
                }
                if ( isEmployeeIdEntityCriteria( entityProperty ) ) {
                    employeeIdCriteria = true;
                }               
                // special handling for the campus code, since that forces the query to look
                // at the default affiliation record only
                if ( key.equals( "campusCode" ) ) {
                    affiliationDefaultOnlyCriteria = true;
                }
            } 
            
            if ( nameCriteria ) {
                newCriteria.put( ENTITY_NAME_PROPERTY_PREFIX + "active", "Y" );
                newCriteria.put( ENTITY_NAME_PROPERTY_PREFIX + "defaultValue", "Y" );
                //newCriteria.put(ENTITY_NAME_PROPERTY_PREFIX + "nameCode", "PRFR");//so we only display 1 result
            }
            if ( addressCriteria ) {
                newCriteria.put( ENTITY_ADDRESS_PROPERTY_PREFIX + "active", "Y" );
                newCriteria.put( ENTITY_ADDRESS_PROPERTY_PREFIX + "defaultValue", "Y" );
            }
            if ( phoneCriteria ) {
                newCriteria.put( ENTITY_PHONE_PROPERTY_PREFIX + "active", "Y" );
                newCriteria.put( ENTITY_PHONE_PROPERTY_PREFIX + "defaultValue", "Y" );
            }
            if ( emailCriteria ) {
                newCriteria.put( ENTITY_EMAIL_PROPERTY_PREFIX + "active", "Y" );
                newCriteria.put( ENTITY_EMAIL_PROPERTY_PREFIX + "defaultValue", "Y" );
            }
            if ( employeeIdCriteria ) {
                newCriteria.put( ENTITY_EMPLOYEE_ID_PROPERTY_PREFIX + "active", "Y" );
                newCriteria.put( ENTITY_EMPLOYEE_ID_PROPERTY_PREFIX + "primary", "Y" );
            }
            if ( affiliationCriteria ) {
                newCriteria.put( ENTITY_AFFILIATION_PROPERTY_PREFIX + "active", "Y" );
            }
            if ( affiliationDefaultOnlyCriteria ) {
                newCriteria.put( ENTITY_AFFILIATION_PROPERTY_PREFIX + "defaultValue", "Y" );
            } 
        }   
        
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( "Converted: " + newCriteria );
        }
        return newCriteria;     
    }

    protected boolean isNameEntityCriteria( String propertyName ) {
        return propertyName.startsWith( ENTITY_NAME_PROPERTY_PREFIX );
    }
    protected boolean isAddressEntityCriteria( String propertyName ) {
        return propertyName.startsWith( ENTITY_ADDRESS_PROPERTY_PREFIX );
    }
    protected boolean isPhoneEntityCriteria( String propertyName ) {
        return propertyName.startsWith( ENTITY_PHONE_PROPERTY_PREFIX );
    }
    protected boolean isEmailEntityCriteria( String propertyName ) {
        return propertyName.startsWith( ENTITY_EMAIL_PROPERTY_PREFIX );
    }
    protected boolean isEmployeeIdEntityCriteria( String propertyName ) {
        return propertyName.startsWith( ENTITY_EMPLOYEE_ID_PROPERTY_PREFIX );
    }
    protected boolean isAffiliationEntityCriteria( String propertyName ) {
        return propertyName.startsWith( ENTITY_AFFILIATION_PROPERTY_PREFIX );
    }
    protected boolean isExternalIdentifierEntityCriteria( String propertyName ) {
        return propertyName.startsWith( ENTITY_EXT_ID_PROPERTY_PREFIX );
    }
    
    protected IdentityService getIdentityService() {
        if ( identityService == null ) {
            identityService = KimApiServiceLocator.getIdentityService();
        }
        return identityService;
    }


}
