/*
 * Copyright 2009 The Kuali Foundation
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

package org.kuali.student.r2.core.atp.infc;

import org.kuali.student.r2.common.infc.Relationship;


/**
 *  Information about a mapping between Academic Time Periods.
 *
 *  @Author tom
 *  @Since Tue Apr 05 14:22:34 EDT 2011
 */

public interface AtpAtpRelation extends Relationship {

    /**
     *  Name: ATP Key
     *  A unique identifier for the first ATP in the relation.
     * 
     *  @return the ATP key
     */
    public String getAtpKey();
    public void setAtpKey(String atpKey);

    /**
     *  Name: Related ATP Key
     *  A unique identifier for the second ATP in the relation.
     *
     *  @return the ATP key
     */
    public String getRelatedAtpKey();
    public void setRelatedAtpKey(String relatedAtpKey);
}
