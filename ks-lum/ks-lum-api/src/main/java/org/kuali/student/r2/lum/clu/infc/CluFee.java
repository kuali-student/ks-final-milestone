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
import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.HasId;

import java.util.List;

/**
 * Detailed information associated with this CLU related to the calculation of
 * fees.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface CluFee extends HasAttributesAndMeta, HasId {

    /**
     * List of Fee Records
     *
     * @name Clu Fee Records
     */
    List<? extends CluFeeRecord> getCluFeeRecords();

    /**
     * Narrative description of the CLU Fee
     *
     * @name Descr
     */
    RichTextInfo getDescr();
}
