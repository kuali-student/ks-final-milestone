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

package org.kuali.student.r2.core.tag.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.Date;

/**
 * Information about a Tag
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 * @impl Tag is under review, please verify status with Service Team
 */
public interface Tag extends IdNamelessEntity {

    /**
     * Namespace of the tag.
     *
     * @name Namespace
     */
    public String getNamespace();

    /**
     * Predicate of the tag.
     *
     * @name Predicate
     */
    public String getPredicate();

    /**
     * Value of the tag.
     *
     * @name Value
     * @required
     */
    public String getValue();

    /**
     * Unique identifier for a reference type.
     *
     * @name Reference Type Key
     * @readOnly
     * @required
     */
    public String getReferenceTypeKey();

    /**
     * Identifier component for a reference. This is an external identifier and such may not uniquely identify
     * a particular reference unless combined with the type. A referenceId could be a cluId, a luiId, an orgId,
     * a documentId, etc.
     *
     * @name Reference Id
     * @readOnly
     * @required
     */
    public String getReferenceId();

    /**
     * Date and time that this tag became effective. This is a similar concept to the effective date on
     * enumerated values. When an expiration date has been specified, this field must be less than or equal
     * to the expiration date.
     *
     * @name Effective Date
     */
    public Date getEffectiveDate();

    /**
     * Date and time that this tag expires. This is a similar concept to the expiration date on enumerated values.
     * If specified, this should be greater than or equal to the effective date. If this field is not specified,
     * then no expiration date has been currently defined and should automatically be considered greater than
     * the effective date.
     *
     * @name Expiration Date
     */
    public Date getExpirationDate();
}
