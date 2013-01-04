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

/*
 * OrgTreeInfo is not defined in the service contract. This is currently
 * an implementation-specific class.
 */

package org.kuali.student.r2.core.organization.infc;

/**
 * This is used to retrieve a flattened organization hierarchy from a
 * reference organization node down a specified number of levels.
 *
 * @author tom
 */
public interface OrgTree {

    /**
     * Gets the display name for this tree.
     *
     * @name Display Name
     * @required
     * @readOnly
     */   
    public String getDisplayName();

    /**
     * Gets the Id of the Organization Hierarchy.
     *
     * @name Org Hierarchy Id
     * @required
     * @readOnly
     */   
    public String getOrgHierarchyId();

    /**
     * Gets the Id of the reference or starting Organization node in
     * this tree.
     *
     * @name Org Id
     * @required
     * @readOnly
     */   
    public String getOrgId();

    /**
     * Gets the Id of the parent to the Organization node.
     *
     * @name Parent Id
     * @readOnly
     */   
    public String getParentId();

    /**
     * Gets the total number of positions in this organization
     * (assuming).
     *
     * @name Positions
     * @required
     * @readOnly
     */               
    public Long getPositions();

    /**
     * Gets the Id of the current position (for person?). 
     *
     * @name Position Id
     * @readOnly
     */   
    public String getPositionId();

    /**
     * Gets the Id of the person at the current position.
     *
     * @name Person Id
     * @readOnly
     */       
    public String getPersonId();

    /**
     * Gets the type key of the relation. (or person relation???)
     *
     * @name Relation Type Key
     * @readOnly
     */           
    public String getRelationTypeKey();
}
