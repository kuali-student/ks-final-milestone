/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Daniel on 7/9/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.student.r1.core.personsearch.service.impl.KsPerson;

import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PersonServiceMockImpl implements PersonService {
    @Override
    public Person getPerson(final String s) {
        return new Person(){

            @Override
            public String getPrincipalId() {
                return s;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getPrincipalName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getEntityId() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getEntityTypeCode() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getFirstName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getFirstNameUnmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getMiddleName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getMiddleNameUnmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getLastName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getLastNameUnmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getNameUnmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getEmailAddress() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getEmailAddressUnmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressLine1() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressLine1Unmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressLine2() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressLine2Unmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressLine3() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressLine3Unmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressCity() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressCityUnmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressStateProvinceCode() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressStateProvinceCodeUnmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressPostalCode() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressPostalCodeUnmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressCountryCode() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getAddressCountryCodeUnmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getPhoneNumber() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getPhoneNumberUnmasked() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getCampusCode() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public Map<String, String> getExternalIdentifiers() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean hasAffiliationOfType(String affiliationTypeCode) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public List<String> getCampusCodesForAffiliationOfType(String affiliationTypeCode) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getEmployeeStatusCode() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getEmployeeTypeCode() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public KualiDecimal getBaseSalaryAmount() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getExternalId(String externalIdentifierTypeCode) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getPrimaryDepartmentCode() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getEmployeeId() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isActive() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void refresh() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }

    @Override
    public List<Person> getPersonByExternalIdentifier(String s, String s1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Person getPersonByPrincipalName(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Person getPersonByEmployeeId(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Person> findPeople(Map<String, String> stringStringMap) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Person> findPeople(Map<String, String> stringStringMap, boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Class<? extends Person> getPersonImplementationClass() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> resolvePrincipalNamesToPrincipalIds(BusinessObject businessObject, Map<String, String> stringStringMap) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Person updatePersonIfNecessary(String s, Person person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
