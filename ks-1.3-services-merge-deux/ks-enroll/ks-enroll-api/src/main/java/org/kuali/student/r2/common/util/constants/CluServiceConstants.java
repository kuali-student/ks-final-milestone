/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.lum.clu.dto.CluInfo;

/**
 * Constants used by CluService
 * 
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class CluServiceConstants {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "clu";
    public static final String REF_OBJECT_URI_CLU = NAMESPACE + "/" + CluInfo.class.getSimpleName();
}
