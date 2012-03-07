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
    public static final String REF_OBJECT_URI_ROOM = NAMESPACE + "/" + RoomInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_BUILDING = NAMESPACE + "/" + BuildingInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ROOM_RESPONSIBLE_ORG = NAMESPACE + "/" + RoomResponsibleOrg.class.getSimpleName();
}
