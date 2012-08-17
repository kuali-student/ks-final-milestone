/**
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the * Educational Community License, Version 2.0
 * (the "License"); you may * not use this file except in compliance
 * with the License. You may * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;


/**
 * This class holds the constants used by the type service
 *
 * @author tom
 */
public class TypeServiceConstants {

    /**
     * Reference Object URIs
     */
    public static final String SERVICE_NAME_LOCAL_PART = "TypeService";
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "type";
    public static final String REF_OBJECT_URI_DEFAULT = NAMESPACE + "COMMON/DEFAULT";
    public static final String REF_OBJECT_URI_TYPE = NAMESPACE + "/" + TypeInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_TYPE_TYPE_RELATION = NAMESPACE + "/" + TypeTypeRelationInfo.class.getSimpleName();
    
    /**
     * Type type relationship types
     */
    public static final String TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY = "kuali.type.type.relation.type.allowed";
    public static final String TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY = "kuali.type.type.relation.type.contains";
    public static final String TYPE_TYPE_RELATION_GROUP_TYPE_KEY = "kuali.type.type.relation.type.group";
    /**
     * states
     */
    public static final String TYPE_TYPE_RELATION_ACTIVE_STATE_KEY = "kuali.type.type.relation.state.active";
    public static final String TYPE_TYPE_RELATION_INACTIVE_STATE_KEY = "kuali.type.type.relation.state.inactive";
}
