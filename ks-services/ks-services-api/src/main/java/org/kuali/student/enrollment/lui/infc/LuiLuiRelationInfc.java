/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lui.infc;

import org.kuali.student.common.infc.HasAttributesInfc;
import org.kuali.student.common.infc.HasEffectiveDatesInfc;
import org.kuali.student.common.infc.HasIdInfc;
import org.kuali.student.common.infc.HasMetaInfc;
import org.kuali.student.common.infc.HasStateInfc;
import org.kuali.student.common.infc.HasTypeInfc;

public interface LuiLuiRelationInfc extends HasAttributesInfc,
        HasMetaInfc,
        HasIdInfc,
        HasTypeInfc,
        HasStateInfc,
        HasEffectiveDatesInfc {

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for a Learning Unit Instance (LUI).
     */
    public void setLuiId(String luiId);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for a Learning Unit Instance (LUI).
     */
    public String getLuiId();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for a Learning Unit Instance (LUI).
     */
    public void setRelatedLuiId(String relatedLuiId);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for a Learning Unit Instance (LUI).
     */
    public String getRelatedLuiId();

}

