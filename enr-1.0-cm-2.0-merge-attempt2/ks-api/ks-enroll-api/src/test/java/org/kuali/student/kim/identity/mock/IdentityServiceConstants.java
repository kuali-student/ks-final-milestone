/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.kim.identity.mock;

/**
 * @author nwright
 */
public class IdentityServiceConstants {

    /**
     * Affiliation Types
     */
    public static final String STUDENT_AFFILIATION_TYPE_KEY = AffiliationTypeEnum.STUDENT.getCode();
    public static final String FACULTY_AFFILIATION_TYPE_KEY = AffiliationTypeEnum.FACULTY.getCode();
    public static final String STAFF_AFFILIATION_TYPE_KEY = AffiliationTypeEnum.STAFF.getCode();
    public static final String AFFILIATE_AFFILIATION_TYPE_KEY = AffiliationTypeEnum.AFFILIATE.getCode();
    /**
     * Address Types
     */
    public static final String HOME_ADDRESS_TYPE_KEY = AddressTypeEnum.HOME.getCode();
    public static final String WORK_ADDRESS_TYPE_KEY = AddressTypeEnum.WORK.getCode();
    public static final String OTHER_ADDRESS_TYPE_KEY = AddressTypeEnum.OTHER.getCode();
    /**
     * Email Types
     */
    public static final String HOME_EMAIL_TYPE_KEY = EmailTypeEnum.HOME.getCode();
    public static final String WORK_EMAIL_TYPE_KEY = EmailTypeEnum.WORK.getCode();
    public static final String OTHER_EMAIL_TYPE_KEY = EmailTypeEnum.OTHER.getCode();
    /**
     * Phone Types
     */
    public static final String HOME_PHONE_TYPE_KEY = PhoneTypeEnum.HOME.getCode();
    public static final String WORK_PHONE_TYPE_KEY = PhoneTypeEnum.WORK.getCode();
    public static final String MOBILE_PHONE_TYPE_KEY = PhoneTypeEnum.MOBILE.getCode();
    public static final String OTHER_PHONE_TYPE_KEY = PhoneTypeEnum.OTHER.getCode();
    /**
     * Search constants
     */
    public static final int BOUNDED_SEARCH_MAX_RESULTS = 100;
    public static final String KIM_PERSON_FIRST_NAME = "names.firstName";
    public static final String KIM_PERSON_MIDDLE_NAME = "names.middleName";
    public static final String KIM_PERSON_LAST_NAME = "names.lastName";
    public static final String KIM_PRINCIPALS_PRINCIPALNAME = "principals.principalName";
    public static final String KIM_PRINCIPALS_PRINCIPALID = "principals.principalId";
    public static final String KIM_PERSON_AFFILIATION_TYPE_CODE = "affiliationTypeCode";
    public static final String[] SUPPORTED_SEARCH_FIELD_KEYS = {
        KIM_PRINCIPALS_PRINCIPALNAME,
        KIM_PRINCIPALS_PRINCIPALID,
        KIM_PERSON_FIRST_NAME,
        KIM_PERSON_MIDDLE_NAME,
        KIM_PERSON_LAST_NAME,
        KIM_PERSON_AFFILIATION_TYPE_CODE
    };
}

