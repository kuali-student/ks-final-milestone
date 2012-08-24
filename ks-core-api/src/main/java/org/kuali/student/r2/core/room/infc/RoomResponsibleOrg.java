/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.kuali.student.r2.core.room.infc;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * Room Responsible Org
 * Note that multiple orgs can be responsible for a room in different roles
 * like maintenance, scheduling etc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public interface RoomResponsibleOrg extends Relationship {

    /**
     * Unique id for the organization
     *
     * @name Org Id
     * @readOnly
     * @required
     */
    public String getOrgId();

    /**
     * Unique id for the room
     *
     * @name Room Id
     * @readOnly
     * @required
     */
    String getRoomId();
}
