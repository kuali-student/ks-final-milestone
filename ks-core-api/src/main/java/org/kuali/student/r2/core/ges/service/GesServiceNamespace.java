/*
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
 */
package org.kuali.student.r2.core.ges.service;


import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.ges.dto.ParameterInfo;
import org.kuali.student.r2.core.ges.dto.ValueInfo;

/**
 * This class holds the constants used by the GES service.
 *
 */
public class GesServiceNamespace {
    public static final String SERVICE_NAME_LOCAL_PART = GesService.class.getSimpleName();
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "ges";
    public static final String REF_OBJECT_URI_PARAMETER = NAMESPACE + "/" + ParameterInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_VALUE = NAMESPACE + "/" + ValueInfo.class.getSimpleName();
}
