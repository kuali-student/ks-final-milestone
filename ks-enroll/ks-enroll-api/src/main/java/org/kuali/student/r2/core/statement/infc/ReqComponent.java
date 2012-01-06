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

package org.kuali.student.r2.core.statement.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface ReqComponent extends IdNamelessEntity {
    /**
     * Detailed information about a requirement component field value.
     *
     * @name Req Comp Fields
     * @required
     */
    List<? extends ReqCompField> getReqCompFields();

    /**
     * Date and time that this requirement component became effective. This is a
     * similar concept to the effective date on enumerated values. When an
     * expiration date has been specified, this field must be less than or equal
     * to the expiration date.
     *
     * @name Effective Date
     */
    Date getEffectiveDate();

    /**
     * Date and time that this requirement component expires. This is a similar
     * concept to the expiration date on enumerated values. If specified, this
     * must be greater than or equal to the effective date. If this field is not
     * specified, then no expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     *
     * @name Expiration Date
     */
    Date getExpirationDate();

    /**
     * <code>naturalLanguageTranslation</code> attribute is generated on-the-fly
     * and should not be persisted.
     *
     * @name Natural Language Translation
     * @readOnly
     * @required
     */
    String getNaturalLanguageTranslation();
}
