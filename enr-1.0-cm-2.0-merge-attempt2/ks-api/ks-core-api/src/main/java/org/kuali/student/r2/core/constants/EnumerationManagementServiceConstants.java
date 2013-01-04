/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;

/**
 * This class holds the constants used by the Enumeration Management service
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class EnumerationManagementServiceConstants {
    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "enumerationmanagement";
    public static final String REF_OBJECT_URI_ENUMERATION_MANAGEMENT = NAMESPACE + "/" + EnumerationInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ENUMERATION_MANAGEMENT_VALUE = NAMESPACE + "/" + EnumeratedValueInfo.class.getSimpleName();

}
