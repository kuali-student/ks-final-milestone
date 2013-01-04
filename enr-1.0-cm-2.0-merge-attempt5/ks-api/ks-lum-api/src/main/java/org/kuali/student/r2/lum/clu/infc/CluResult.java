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

package org.kuali.student.r2.lum.clu.infc;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.List;

/**
 * Information about a CLU result.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface CluResult extends IdNamelessEntity, HasEffectiveDates {
    /**
     * The cluId to which the CLU Result is linked. Unique identifier for a
     * Canonical Learning Unit (CLU).
     *
     * @name Clu Id
     * @readOnly
     * @required
     */
    String getCluId();

    /**
     * Narrative description of the CLU Result
     *
     * @name Descr
     */
    RichTextInfo getDescr();

    /**
     * List of learning result option information.
     *
     * @name Result Options
     */
    List<? extends ResultOption> getResultOptions();

}
