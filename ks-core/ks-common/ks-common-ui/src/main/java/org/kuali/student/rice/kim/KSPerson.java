/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by venkat on 7/13/13
 */
package org.kuali.student.rice.kim;

import org.kuali.rice.kim.api.identity.Person;

public interface KSPerson {

    public String getPersonAffiliationType();

    public void setPersonAffiliationType(String personAffiliationType);

    public Person getPerson();

    public String getKsNameSearch();

    public void setKsNameSearch(String ksNameSearch);

}
