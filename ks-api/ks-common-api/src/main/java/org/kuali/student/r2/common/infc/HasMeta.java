/**
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

package org.kuali.student.r2.common.infc;

/**
 * A common service pattern for entities. This interface is applied to
 * entities the hold Meta information. Typically, any entity that is
 * created or updated through service operations.
 *
 * @author nwright
 */

public interface HasMeta {

    /**
     * Create and last update info for the structure. This is optional
     * and treated as read only since the data is set by the internals
     * of the service during maintenance operations.
     *
     * Contains audit trail information about the creation and last
     * update of this object Also contains the version ind used for
     * optimistic locking.
     *
     * Attempts to set or update should result in a ReadOnlyException
     * being thrown.
     *
     * @name Meta
     * @readOnly
     * @required on updates
     */
    public Meta getMeta();
}
