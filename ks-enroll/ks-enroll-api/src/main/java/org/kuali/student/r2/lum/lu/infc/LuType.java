/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.lu.infc;

import org.kuali.student.r2.core.type.infc.Type;

/**
 * Detailed information about a single learning unit type.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface LuType extends Type {

    /**
     * Unique identifier for an instructional format type. Examples of potential
     * instructional formats for courses are lab, lecture, etc.
     *
     * @name Instructional Format
     */
    String getInstructionalFormat();

    /**
     * Unique identifier for a delivery method type. In other avenues, this
     * would be described as channels or media. Examples of delivery method are
     * face to face, online, correspondence, etc.
     *
     * @name Delivery Method
     */
    String getDeliveryMethod();
}
