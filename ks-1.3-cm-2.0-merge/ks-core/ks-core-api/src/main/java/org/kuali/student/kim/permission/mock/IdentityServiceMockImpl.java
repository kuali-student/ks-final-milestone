/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.kim.permission.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.kim.api.identity.CodedAttribute;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.address.EntityAddress;
import org.kuali.rice.kim.api.identity.affiliation.EntityAffiliation;
import org.kuali.rice.kim.api.identity.affiliation.EntityAffiliationType;
import org.kuali.rice.kim.api.identity.citizenship.EntityCitizenship;
import org.kuali.rice.kim.api.identity.email.EntityEmail;
import org.kuali.rice.kim.api.identity.employment.EntityEmployment;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.entity.EntityQueryResults;
import org.kuali.rice.kim.api.identity.external.EntityExternalIdentifier;
import org.kuali.rice.kim.api.identity.external.EntityExternalIdentifierType;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.identity.personal.EntityBioDemographics;
import org.kuali.rice.kim.api.identity.personal.EntityEthnicity;
import org.kuali.rice.kim.api.identity.phone.EntityPhone;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.identity.principal.PrincipalQueryResults;
import org.kuali.rice.kim.api.identity.privacy.EntityPrivacyPreferences;
import org.kuali.rice.kim.api.identity.residency.EntityResidency;
import org.kuali.rice.kim.api.identity.type.EntityTypeContactInfo;
import org.kuali.rice.kim.api.identity.visa.EntityVisa;

import javax.jws.WebParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nwright
 */
public class IdentityServiceMockImpl implements IdentityService {

    private Map<String, Entity> entities = new HashMap<String,Entity> ();
    
    @Override
    public EntityAddress addAddressToEntity(EntityAddress ea) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityAffiliation addAffiliationToEntity(EntityAffiliation ea) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityBioDemographics addBioDemographicsToEntity(EntityBioDemographics ebd) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityCitizenship addCitizenshipToEntity(EntityCitizenship ec) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityEmail addEmailToEntity(EntityEmail ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityEmployment addEmploymentToEntity(EntityEmployment ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityTypeContactInfo addEntityTypeContactInfoToEntity(EntityTypeContactInfo etci) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityEthnicity addEthnicityToEntity(EntityEthnicity ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityExternalIdentifier addExternalIdentifierToEntity(EntityExternalIdentifier eei) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityName addNameToEntity(EntityName en) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityPhone addPhoneToEntity(EntityPhone ep) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Principal addPrincipalToEntity(Principal prncpl) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityPrivacyPreferences addPrivacyPreferencesToEntity(EntityPrivacyPreferences epp) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityResidency addResidencyToEntity(EntityResidency er) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityVisa addVisaToEntity(EntityVisa ev) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Entity createEntity(Entity entity) throws RiceIllegalArgumentException, RiceIllegalStateException {
        Entity.Builder builder = Entity.Builder.create(entity);
        if (builder.getId() == null) {
            builder.setId(this.entities.size() + "");
        }
        Entity copy = builder.build();
        this.entities.put(entity.getId(), copy);
        return copy;
    }

    @Override
    public EntityQueryResults findEntities(QueryByCriteria qbc) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityDefaultQueryResults findEntityDefaults(QueryByCriteria qbc) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodedAttribute getAddressType(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CodedAttribute> findAllAddressTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityAffiliationType getAffiliationType(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityAffiliationType> findAllAffiliationTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodedAttribute getCitizenshipStatus(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CodedAttribute> findAllCitizenshipStatuses() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodedAttribute getEmailType(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CodedAttribute> findAllEmailTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PrincipalQueryResults findPrincipals(@WebParam(name = "query") QueryByCriteria query) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodedAttribute getEmploymentStatus(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CodedAttribute> findAllEmploymentStatuses() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodedAttribute getEmploymentType(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CodedAttribute> findAllEmploymentTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Entity getEntity(String id) throws RiceIllegalArgumentException {
        Entity entity = this.entities.get(id);
        return entity;
    }

    @Override
    public Entity getEntityByPrincipalId(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Entity getEntityByPrincipalName(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Gets a {@link Entity} from a employeeId.
     * <p/>
     * <p>
     * This method will return null if the Entity does not exist.
     * </p>
     *
     * @param employeeId the unique id to retrieve the entity by. cannot be null.
     * @return a {@link Entity} or null
     * @throws IllegalArgumentException if the employeeId is blank
     */
    @Override
    public Entity getEntityByEmployeeId(@WebParam(name = "employeeId") String employeeId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityDefault getEntityDefault(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityDefault getEntityDefaultByPrincipalId(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityDefault getEntityDefaultByPrincipalName(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Gets a {@link EntityDefault} from an employeeId.
     * {@link EntityDefault} is a condensed version of {@link Entity} that contains
     * default values of its subclasses
     * <p/>
     * <p>
     * This method will return null if the Entity does not exist.
     * </p>
     *
     * @param employeeId the unique id to retrieve the entity by. cannot be null.
     * @return a {@link EntityDefault} or null
     * @throws IllegalArgumentException if the employeeId is blank
     */
    @Override
    public EntityDefault getEntityDefaultByEmployeeId(@WebParam(name = "employeeId") String employeeId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityPrivacyPreferences getEntityPrivacyPreferences(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodedAttribute getEntityType(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CodedAttribute> findAllEntityTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityExternalIdentifierType getExternalIdentifierType(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntityExternalIdentifierType> findAllExternalIdendtifierTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodedAttribute getNameType(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CodedAttribute> findAllNameTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodedAttribute getPhoneType(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CodedAttribute> findAllPhoneTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Principal getPrincipal(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Principal> getPrincipals(@WebParam(name = "principalIds") List<String> strings) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Principal getPrincipalByPrincipalName(String string) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Principal getPrincipalByPrincipalNameAndPassword(String string, String string1) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityAddress inactivateAddress(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityAffiliation inactivateAffiliation(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * This returns the display name information for the given principal
     * without loading the full person object.
     *
     * @param principalId The principal ID to find the name information for
     * @return The default name information for the principal
     */
    @Override
    public EntityNamePrincipalName getDefaultNamesForPrincipalId(@WebParam(name = "principalId") String principalId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityCitizenship inactivateCitizenship(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityEmail inactivateEmail(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityEmployment inactivateEmployment(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Entity inactivateEntity(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityTypeContactInfo inactivateEntityTypeContactInfo(String string, String string1) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityName inactivateName(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityPhone inactivatePhone(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Principal inactivatePrincipal(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Principal inactivatePrincipalByName(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityAddress updateAddress(EntityAddress ea) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityAffiliation updateAffiliation(EntityAffiliation ea) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityBioDemographics updateBioDemographics(EntityBioDemographics ebd) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityCitizenship updateCitizenship(EntityCitizenship ec) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityEmail updateEmail(EntityEmail ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityEmployment updateEmployment(EntityEmployment ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Entity updateEntity(Entity entity) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityTypeContactInfo updateEntityTypeContactInfo(EntityTypeContactInfo etci) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityEthnicity updateEthnicity(EntityEthnicity ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityExternalIdentifier updateExternalIdentifier(EntityExternalIdentifier eei) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityName updateName(EntityName en) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityPhone updatePhone(EntityPhone ep) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Principal updatePrincipal(Principal prncpl) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityPrivacyPreferences updatePrivacyPreferences(EntityPrivacyPreferences epp) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityResidency updateResidency(EntityResidency er) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityVisa updateVisa(EntityVisa ev) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
