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

import org.kuali.rice.kim.api.KimConstants;
import org.kuali.student.core.person.service.PersonServiceNamespace;

public class PersonServiceConstants extends PersonServiceNamespace {

    public static final String PERSON_TYPE_KIM_BACKED_KEY = "kuali.person.type.kim.backed";

    public static final String PERSON_STATE_ACTIVE_KEY = "kuali.person.state.active";
    public static final String PERSON_STATE_INACTIVE_KEY = "kuali.person.state.inactive";

    public static final String PERSON_NAME_STATE_ACTIVE_KEY = "kuali.person.name.state.active";
    public static final String PERSON_NAME_STATE_INACTIVE_KEY = "kuali.person.name.state.inactive";

    public static final String PERSON_IDENTIFIER_STATE_ACTIVE_KEY = "kuali.person.identifier.state.active";
    public static final String PERSON_IDENTIFIER_STATE_INACTIVE_KEY = "kuali.person.identifier.state.inactive";

    public static final String PERSON_BIO_DEMOGRAPHICS_STATE_ACTIVE_KEY = "kuali.person.bio.demographics.state.active";
    public static final String PERSON_BIO_DEMOGRAPHICS_STATE_INACTIVE_KEY = "kuali.person.bio.demographics.state.inactive";

    public static final String PERSON_AFFILIATION_STATE_ACTIVE_KEY = "kuali.person.identifier.state.active";
    public static final String PERSON_AFFILIATION_STATE_INACTIVE_KEY = "kuali.person.identifier.state.inactive";

    public static final String PERSON_NAME_TYPE_PREFIX = "kuali.person.name.type.";
    public static final String PERSON_NAME_PRIMARY_TYPE_KEY = PERSON_NAME_TYPE_PREFIX + KimConstants.NameTypes.PRIMARY;
    public static final String PERSON_NAME_PREFERRED_TYPE_KEY = PERSON_NAME_TYPE_PREFIX + KimConstants.NameTypes.PREFERRED;
    public static final String PERSON_NAME_OTHER_TYPE_KEY = PERSON_NAME_TYPE_PREFIX + KimConstants.NameTypes.OTHER;

    public static final String PERSON_IDENTIFIER_TYPE_PREFIX = "kuali.person.identifier.type.";
    public static final String PERSON_IDENTIFIER_TAX_TYPE_KEY = PERSON_IDENTIFIER_TYPE_PREFIX + KimConstants.PersonExternalIdentifierTypes.TAX;
    
    public static final String PERSON_AFFILIATION_TYPE_PREFIX = "kuali.person.affiliation.type.";
    public static final String PERSON_AFFILIATION_STUDENT_TYPE_KEY = PERSON_AFFILIATION_TYPE_PREFIX + "STDNT";
    // apparently the KimConstants don't match what is actually returned STUDENT expected but STDNT is what is actually returned
//    public static final String PERSON_AFFILIATION_STUDENT_TYPE_KEY = PERSON_AFFILIATION_TYPE_PREFIX + KimConstants.PersonAffiliationTypes.STUDENT_AFFILIATION_TYPE;
    public static final String PERSON_AFFILIATION_STAFF_TYPE_KEY = PERSON_AFFILIATION_TYPE_PREFIX + KimConstants.PersonAffiliationTypes.STAFF_AFFILIATION_TYPE;
    public static final String PERSON_AFFILIATION_FACULTY_TYPE_KEY = PERSON_AFFILIATION_TYPE_PREFIX + KimConstants.PersonAffiliationTypes.FACULTY_AFFILIATION_TYPE;
    public static final String PERSON_AFFILIATION_AFFILIATE_TYPE_KEY = PERSON_AFFILIATION_TYPE_PREFIX + KimConstants.PersonAffiliationTypes.AFFILIATE_AFFILIATION_TYPE;

    public static final String PERSON_BIO_DEMOGRAPHICS_TYPE_KEY = "kuali.person.bio.demographics.type";
}
