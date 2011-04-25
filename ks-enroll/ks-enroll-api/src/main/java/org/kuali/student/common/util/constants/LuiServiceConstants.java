/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.util.constants;

import org.kuali.student.enrollment.classI.lui.dto.LuiInfo;
import org.kuali.student.enrollment.classI.lui.dto.LuiLuiRelationInfo;


/**
 * This is a description of what this class does - Kamal don't forget
 * to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */

public class LuiServiceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX +  "lui";
    public static final String REF_OBJECT_URI_LUI = NAMESPACE + "/" + LuiInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_LUI_LUI_RELATION = NAMESPACE + "/" + LuiLuiRelationInfo.class.getSimpleName();
}
