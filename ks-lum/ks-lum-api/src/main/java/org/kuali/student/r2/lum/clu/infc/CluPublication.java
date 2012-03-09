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

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.List;

/**
 * Detailed information about publishing a clu.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface CluPublication extends IdNamelessEntity, HasEffectiveDates {
    /**
     * The identifier for the canonical learning unit which is described by this
     * publication information.
     *
     * @name Clu Id
     * @readOnly
     * @required
     */
    public String getCluId();

    /**
     * Fields in cluInfo whose values are overridden as part of this
     * publication.
     *
     * @name Variants
     */
    public List<? extends Field> getVariants();

    /**
     * The start academic time period for when the CLU should be published in
     * this type of usage. Should be less than or equal to endCycle.
     *
     * @name Start Cycle
     */
    public String getStartCycle();

    /**
     * The end academic time period for when the CLU should be published in this
     * type of usage. If specified, should be greater than or equal to
     * startCycle.
     *
     * @name End Cycle
     */
    public String getEndCycle();

}
