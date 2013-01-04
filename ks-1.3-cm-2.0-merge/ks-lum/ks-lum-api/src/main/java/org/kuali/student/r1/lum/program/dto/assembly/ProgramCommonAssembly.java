/*
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r1.lum.program.dto.assembly;

import org.kuali.student.r2.common.dto.MetaInfo;

import java.util.Map;

public interface ProgramCommonAssembly {

        /**
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public Map<String, String> getAttributes() ;
    public void setAttributes(Map<String, String> attributes) ;


    /**
     * Create and last update info for the structure. This is optional and treated as read only since the data is set by the internals of the service during maintenance operations.
     */
    public MetaInfo getMetaInfo();
    public void setMetaInfo(MetaInfo metaInfo);

    /**
     * Unique identifier for a learning unit type. Once set at create time, this field may not be updated.
     */

    public String getType();
    public void setType(String type);

    /**
     * The current status of the  program. The values for this field are constrained to those in the luState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */

    public String getState() ;
    public void setState(String state);

        /**
     * Unique identifier for a program. This is optional, due to the identifier being set at the time of creation. Once the Program has been created, this should be seen as required.
     */
    public String getId() ;
    public void setId(String id);

}
