/*
 * Copyright 2012 The Kuali Foundation Licensed under the
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
package org.kuali.student.enrollment.lui.infc;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Creator: mahtabme (Mezba Mahtab)
 * Date: 10/4/12
 * Time: 10:28 AM
 *
 * Detailed information about a single LUI Set.
 *
 * @Version 2.0
 * @author Kuali Student Team (ks.collab@kuali.org)
 */

public interface LuiSet extends IdEntity, HasEffectiveDates {

    /**
     * List of identifiers of contained LUIs. Present for enumerated
     * LUI Sets.
     *
     * @readOnly
     */
    public List<String> getLuiIds();
}
