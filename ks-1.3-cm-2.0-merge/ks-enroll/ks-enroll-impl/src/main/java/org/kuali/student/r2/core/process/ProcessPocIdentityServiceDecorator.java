/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import java.util.Arrays;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.identity.personal.EntityBioDemographics;
import org.kuali.student.kim.permission.mock.IdentityServiceDecorator;
import org.kuali.student.r2.common.util.constants.KimIdentityServiceConstants;

/**
 *
 * @author nwright
 */
public class ProcessPocIdentityServiceDecorator extends IdentityServiceDecorator {
    
    public ProcessPocIdentityServiceDecorator (IdentityService nextDecorator) {
        this.setNextDecorator(nextDecorator);
        this._initializeData ();
    }
    
    private void _initializeData () {
        for (ProcessPocPersonEnum person : ProcessPocPersonEnum.values()) {
            _createPerson (person);
        }
    }
    private void _createPerson (ProcessPocPersonEnum person) {
        Entity.Builder entity = Entity.Builder.create();
        entity.setId(person.getPersonId() + "");
        entity.setActive(true);
        EntityName.Builder name = EntityName.Builder.create();
        name.setActive(true);
        name.setDefaultValue(true);
        name.setEntityId(person.getEntityId());
        name.setFirstName(person.getFirstName());
        name.setId(person.getEntityId());
        name.setLastName(person.getLastName());
        name.setMiddleName(person.getMiddleName());
        name.setNamePrefix(person.getTitle());
        name.setNameType(KimIdentityServiceConstants.PREFERRED_NAME_CODED_ATTRIBUTE);
        name.setNameSuffix(person.getSuffix());
        name.setVersionNumber(0l);
        entity.setNames(Arrays.asList(name));
        EntityBioDemographics.Builder bio = EntityBioDemographics.Builder.create(person.getEntityId(), person.getGender());
        bio.setBirthDate(_nullIt (person.getBirthDate()));
        bio.setDeceasedDate(_nullIt (person.getDeceasedDate()));
        bio.setVersionNumber(0l);
        entity.setBioDemographics(bio);
        entity.setVersionNumber(0l);
        this.createEntity(entity.build());
    }
    
    private String _nullIt (String str) {
        if (str.isEmpty()) {
            return null;
        }
        return str;
    }
}
