/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.organization.infc;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * Detailed information about a single organization to organization
 * relationship.
 *
 * @uthor tom
 */ 

public interface OrgOrgRelation 
    extends Relationship {
    
    /**
     * Identifies the "parent" organization in the relation. The
     * relationship is from this organization to the other
     * organization.
     *
     * @name Org Id
     * @required
     * @readOnly
     */
    public String getOrgId();

    /**
     * This identifies the "child" organization in the relation. The
     * relationship is from the other organization to this
     * organization.
     *
     * @name Related Org Id
     * @required
     * @readOnly
     */
    public String getRelatedOrgId();
}
