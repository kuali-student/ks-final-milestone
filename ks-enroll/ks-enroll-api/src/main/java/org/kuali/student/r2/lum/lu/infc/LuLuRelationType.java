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
 * Information about a LU to LU relationship type.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface LuLuRelationType extends Type {
    /**
     * Name for the reverse LU to LU relationship type. This is primarily to be
     * used by developers and may end up translated in the end system.
     *
     * @name Rev Name
     */
    String getRevName();

    /**
     * Description of the reverse of the LU to LU relationship type
     *
     * @name Rev Descr
     */
    String getRevDescr();
}
