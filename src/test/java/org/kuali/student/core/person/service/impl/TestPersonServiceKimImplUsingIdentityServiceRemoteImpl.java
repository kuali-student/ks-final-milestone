/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.person.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.dto.PersonNameInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.kuali.student.core.person.dto.PersonAffiliationInfo;
import org.kuali.student.core.person.dto.PersonBioDemographicsInfo;
import org.kuali.student.core.person.dto.PersonIdentifierInfo;
import org.kuali.student.core.person.service.PersonService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:person-kim-impl-test-with-kim-remote-impl-context.xml"})
public class TestPersonServiceKimImplUsingIdentityServiceRemoteImpl {

    @Resource
    public PersonService testService;

    public PersonService getPersonService() {
        return testService;
    }

    public void setPersonService(PersonService service) {
        testService = service;
    }

    public ContextInfo contextInfo = null;
    public static String principalId = "123";

    @Before
    public void setUp() {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    @Test
    public void testCrudPerson() throws Exception {

        PersonInfo adminPerson = this.testService.getPerson("1100", contextInfo);
        assertEquals("1100", adminPerson.getId());
        assertEquals(PersonServiceConstants.PERSON_TYPE_KIM_BACKED_KEY, adminPerson.getTypeKey());
        assertEquals(PersonServiceConstants.PERSON_STATE_ACTIVE_KEY, adminPerson.getStateKey());
        assertEquals("admin, admin", adminPerson.getName());

        List<PersonNameInfo> adminNames = this.testService.getPersonNamesByPerson("1100", contextInfo);
        System.out.println("adminNames=" + adminNames);
        assertEquals(1, adminNames.size());
        PersonNameInfo adminName = adminNames.get(0);
        assertEquals(PersonServiceConstants.PERSON_NAME_PREFERRED_TYPE_KEY, adminName.getTypeKey());
        assertEquals(PersonServiceConstants.PERSON_NAME_STATE_ACTIVE_KEY, adminName.getStateKey());
        assertEquals("admin", adminName.getFirstName());
        assertEquals("admin", adminName.getLastName());
        assertEquals("admin, admin", adminName.getCompositeName());

        PersonNameInfo nameInfo = this.testService.getPersonName(adminName.getId(), contextInfo);
        assertEquals(adminName.getId(), nameInfo.getId());
        assertEquals(adminName.getPersonId(), nameInfo.getPersonId());
        assertEquals(adminName.getNameTitle(), nameInfo.getNameTitle());
        assertEquals(adminName.getNamePrefix(), nameInfo.getNamePrefix());
        assertEquals(adminName.getFirstName(), nameInfo.getFirstName());
        assertEquals(adminName.getMiddleName(), nameInfo.getMiddleName());
        assertEquals(adminName.getLastName(), nameInfo.getLastName());
        assertEquals(adminName.getNameSuffix(), nameInfo.getNameSuffix());
        assertEquals(adminName.getCompositeName(), nameInfo.getCompositeName());
        assertEquals(adminName.getNameChangedDate(), nameInfo.getNameChangedDate());

        try {
            PersonBioDemographicsInfo bioInfo = this.testService.
                    getPersonBioDemographicsByPerson(adminPerson.getId(), contextInfo);
            System.out.println("bioDemographicsInfo=" + bioInfo);
            fail("should have thrown does not exist");
        } catch (DoesNotExistException ex) {
            // expected
        }
        
        List<PersonIdentifierInfo> identifiers = this.testService.getPersonIdentifiersByPerson(adminPerson.getId(), contextInfo);
        System.out.println ("identifiers=" + identifiers);
        assertEquals (1, identifiers.size());
        PersonIdentifierInfo identifier = identifiers.get(0);
        assertEquals (adminPerson.getId(), identifier.getPersonId());
        assertEquals (PersonServiceConstants.PERSON_IDENTIFIER_TAX_TYPE_KEY, identifier.getTypeKey());
        assertEquals (PersonServiceConstants.PERSON_IDENTIFIER_STATE_ACTIVE_KEY, identifier.getStateKey());
        // apparently the tax id is encrypted
        assertEquals ( "XBy8HbA0p2kc3lalnjPKpA==", identifier.getIdentifier());
        
        PersonIdentifierInfo identifierInfo = this.testService.getPersonIdentifier(identifier.getId(), contextInfo);
        assertEquals(identifier.getId(), identifierInfo.getId());
        assertEquals(identifier.getPersonId(), identifierInfo.getPersonId());
        assertEquals(identifier.getTypeKey(), identifierInfo.getTypeKey());
        assertEquals(identifier.getStateKey(), identifierInfo.getStateKey());
        assertEquals(identifier.getIdentifier(), identifierInfo.getIdentifier());
        
        String institutionalAffiliationOrganizationId = this.testService.getInstitutionalAffiliationOrganizationId(contextInfo);
        assertEquals ("1", institutionalAffiliationOrganizationId);
        
        List<PersonAffiliationInfo> affiliations = this.testService.getPersonAffiliationsByPerson(adminPerson.getId(), contextInfo);
        System.out.println ("affiliations=" + affiliations);
        assertEquals (1, affiliations.size());
        PersonAffiliationInfo affiliation = affiliations.get(0);
        assertEquals (adminPerson.getId(), affiliation.getPersonId());
        assertEquals (PersonServiceConstants.PERSON_AFFILIATION_STUDENT_TYPE_KEY, affiliation.getTypeKey());
        assertEquals (PersonServiceConstants.PERSON_AFFILIATION_STATE_ACTIVE_KEY, affiliation.getStateKey());
        assertEquals (institutionalAffiliationOrganizationId, affiliation.getOrganizationId());
        assertNull (affiliation.getEffectiveDate());
        assertNull (affiliation.getExpirationDate());
        
        PersonAffiliationInfo affilInfo = this.testService.getPersonAffiliation(affiliation.getId(), contextInfo);
        assertEquals(affiliation.getId(), affilInfo.getId());
        assertEquals(affiliation.getPersonId(), affilInfo.getPersonId());
        assertEquals(affiliation.getTypeKey(), affilInfo.getTypeKey());
        assertEquals(affiliation.getStateKey(), affilInfo.getStateKey());
        assertEquals(affiliation.getEffectiveDate(), affilInfo.getEffectiveDate());
        assertEquals(affiliation.getExpirationDate(), affilInfo.getExpirationDate());
        
    }

}
