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
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.core.person.service.PersonService;
import org.kuali.student.r2.common.dto.ContextInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:person-kim-impl-test-with-kim-map-impl-context.xml"})
public class TestPersonServiceKimImplUsingIdentityServiceMapImpl {

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
        PersonInfo origPerson1 = new PersonInfo();
        origPerson1.setTypeKey(PersonServiceConstants.PERSON_TYPE_KIM_BACKED_KEY);
        origPerson1.setStateKey(PersonServiceConstants.PERSON_STATE_ACTIVE_KEY);
        origPerson1.setName("Norman J Wright");
//        orig.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
        PersonInfo actualPerson1 = this.testService.createPerson(origPerson1.getTypeKey(), origPerson1, contextInfo);

        assertNotNull (actualPerson1.getId());
        assertEquals(origPerson1.getTypeKey(), actualPerson1.getTypeKey());
        assertEquals(origPerson1.getStateKey(), actualPerson1.getStateKey());
        assertEquals(origPerson1.getName(), actualPerson1.getName());
        new RichTextTester().check(origPerson1.getDescr(), actualPerson1.getDescr());
        
        origPerson1 = actualPerson1;
        actualPerson1 = this.testService.getPerson(actualPerson1.getId(), contextInfo);
        assertEquals (origPerson1.getId(), actualPerson1.getId());
        assertEquals(origPerson1.getTypeKey(), actualPerson1.getTypeKey());
        assertEquals(origPerson1.getStateKey(), actualPerson1.getStateKey());
        assertEquals(origPerson1.getName(), actualPerson1.getName());
        new RichTextTester().check(origPerson1.getDescr(), actualPerson1.getDescr());
        
        List<PersonNameInfo> personNames = this.testService.getPersonNamesByPerson(actualPerson1.getId(), contextInfo);
        assertEquals (1, personNames.size());
        PersonNameInfo origPersonName1 = personNames.get(0);
        assertNotNull (origPersonName1.getId());
        assertEquals ("Norman", origPersonName1.getFirstName());
        assertEquals ("J", origPersonName1.getMiddleName());
        assertEquals ("Wright", origPersonName1.getLastName());
        assertEquals (origPersonName1.getPersonId(), origPerson1.getId());
        assertEquals (origPerson1.getName(), origPersonName1.getCompositeName());
        
        // this doesn't work yet against map impl because in memory matcher doesn't work right yet for nested repeating objects
//        PersonNameInfo actualPersonName1 = this.testService.getPersonName(origPersonName1.getId(), contextInfo);
//        assertEquals (origPersonName1.getId(), actualPersonName1.getId());
//        assertEquals (origPersonName1.getPersonId(), actualPersonName1.getPersonId());
//        assertEquals (origPersonName1.getFirstName(), actualPersonName1.getFirstName());
//        assertEquals (origPersonName1.getMiddleName(), actualPersonName1.getMiddleName());
//        assertEquals (origPersonName1.getLastName(), actualPersonName1.getLastName());
//        assertEquals (origPersonName1.getCompositeName(), actualPersonName1.getCompositeName());
    }


}
