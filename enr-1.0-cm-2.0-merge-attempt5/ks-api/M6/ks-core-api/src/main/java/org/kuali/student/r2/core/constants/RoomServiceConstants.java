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

package org.kuali.student.r2.core.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.infc.RoomResponsibleOrg;

/**
 * This class holds the constants used by the Room service
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public class RoomServiceConstants {
    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "room";
    public static final String SERVICE_NAME_LOCAL_PART = "RoomService";
    public static final String REF_OBJECT_URI_ROOM = NAMESPACE + "/" + RoomInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_BUILDING = NAMESPACE + "/" + BuildingInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ROOM_RESPONSIBLE_ORG = NAMESPACE + "/" + RoomResponsibleOrg.class.getSimpleName();

    // *-*-*-*-*-*-*-*-*-* Building *-*-*-*-*-*-*-*-*-*
    /**
     * Building States (KSENROLL-4281)
     */
    public static final String ROOM_BUILDING_ONLINE_STATE_KEY = "kuali.room.building.state.online";
    public static final String ROOM_BUILDING_OFFLINE_STATE_KEY = "kuali.room.building.state.offline";

    /**
     * Building lifecycle (KSENROLL-4281)
     */
    public static final String ROOM_BUILDING_LIFECYCLE_KEY = "kuali.room.building.lifecycle";

    // *-*-*-*-*-*-*-*-*-* Room *-*-*-*-*-*-*-*-*-*
    /**
     * Room types (KSENROLL-4281)
     */
    public static final String ROOM_TYPE_CLASSROOM_KEY = "kuali.room.type.classroom";
    public static final String ROOM_TYPE_GENERAL_CLASSROOM_KEY = "kuali.room.type.classroom.general";

    /**
     * Room States (KSENROLL-4281)
     */
    public static final String ROOM_ROOM_ONLINE_STATE_KEY = "kuali.room.room.state.online";
    public static final String ROOM_ROOM_OFFLINE_STATE_KEY = "kuali.room.room.state.offline";

    /**
     * Room lifecycle (KSENROLL-4281)
     */
    public static final String ROOM_ROOM_LIFECYCLE_KEY = "kuali.room.room.lifecycle";

    // *-*-*-*-*-*-*-*-*-* Org Room Responsibility *-*-*-*-*-*-*-*-*-*
    /**
     * Org Room Responsibility types (KSENROLL-4281)
     */
    public static final String ORG_ROOM_RESPONSIBILITY_TYPE_SCHEDULING_KEY = "kuali.room.org.responsibility.scheduling";

    // *-*-*-*-*-*-*-*-*-* Facility *-*-*-*-*-*-*-*-*-*
    /**
     * Facility types (KSENROLL-4281)
     */
    public static final String ROOM_FACILITY_TYPE_BUILDING_KEY = "kuali.room.facility.type.building";
    public static final String ROOM_FACILITY_TYPE_REGION_KEY = "kuali.room.facility.type.region";
    public static final String ROOM_FACILITY_TYPE_PARTITION_KEY = "kuali.room.facility.type.partition";

    /**
     * Facility States (KSENROLL-4281)
     */
    public static final String ROOM_FACILITY_ONLINE_STATE_KEY = "kuali.room.facility.state.online";
    public static final String ROOM_FACILITY_OFFLINE_STATE_KEY = "kuali.room.facility.state.offline";

    /**
     * Facility lifecycle (KSENROLL-4281)
     */
    public static final String ROOM_FACILITY_LIFECYCLE_KEY = "kuali.room.facility.lifecycle";

    // *-*-*-*-*-*-*-*-*-* Room Responsible Org *-*-*-*-*-*-*-*-*-*
    /**
     * Room Responsible Org States (KSENROLL-4281)
     */
    public static final String ROOM_RESPONSIBLE_ORG_ACTIVE_STATE_KEY = "kuali.room.responsible.org.state.active";
    public static final String ROOM_RESPONSIBLE_ORG_INACTIVE_STATE_KEY = "kuali.room.responsible.org.state.inactive";

    /**
     * Room Responsible Org Lifecycle (KSENROLL-4281)
     */
    public static final String ROOM_RESPONSIBLE_ORG_LIFECYCLE_KEY = "kuali.room.responsible.org.lifecycle";
}
