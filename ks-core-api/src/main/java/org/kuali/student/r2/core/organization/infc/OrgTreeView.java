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
 * OrgTreeViewInfo is not defined in the service contract. This is currently
 * an implementation-specific class.
 */

package org.kuali.student.r2.core.organization.infc;

import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.util.List;

/**
 * This is used to retrieve a view of the organization hierarchy from a
 * reference organization node down or up a specified number of levels.
 *
 * @author tom
 */
public interface OrgTreeView {


    /**
     * Gets the reference or starting Organization node in
     * this tree.
     *
     * @name Org
     * @required
     * @readOnly
     */
    public OrgInfo getOrg();

    /**
     * Gets the parents for the current Organization node in this tree.
     */
    public List<? extends OrgTreeView> getParents();

    /**
     * Gets the children for the current Organization node in this tree.
     */
    public List<? extends OrgTreeView> getChildren();
}
