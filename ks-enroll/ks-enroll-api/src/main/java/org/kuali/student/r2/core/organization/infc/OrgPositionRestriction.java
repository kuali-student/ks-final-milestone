/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.organization.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.TimeAmount;


/**
 * Information which constrains/describes organization to person
 * relationships of a particular type for an organization. These
 * constraints/descriptions typically involve active relationships.
 *
 * Changes: getTitle() -> getName()
 *          MaxNumRelations String->Integer
 *
 * @author tom
 */ 

public interface OrgPositionRestriction
    extends IdEntity {

    /**
     * Organization the restriction applies to.
     *
     * @name Org Id
     * @reqired
     * @readOnly
     */
    public String getOrgId();

    /**
     * Organization to person relationship type the restriction
     * applies to.
     *
     * @name Org Person Relation Type Key
     */
    public String getOrgPersonRelationTypeKey();

    /**
     * Describes the standard duration of relationships of this type.
     *
     * @name Std Duration
     */
    public TimeAmount getStdDuration();

    /**
     * Acts as a lower bound on the number of relationships of this
     * type expected for the organization. If specified, this should
     * be less than or equal to the value of maxNumRelations. This
     * number must be greater than or equal to zero.
     *
     * @name Min Num relations
     * @required
     */
    public Integer getMinNumRelations();

    /**
     * Tests if there is a maximum number of relations of this type.
     * 
     * @name Has Max Num Relations
     * @required
     */
    public Boolean getHasMaxNumRelations();

    /**
     * If hasMaxNumRelations() is true then this method returns the an
     * upper bound on the number of relationships of this type
     * expected for the organization. The values of this field are
     * restricted to integer values. If specified, this should be
     * greater than or equal to the value of minNumRelations.
     *
     * @name Max Num Relations
     */
    public Integer getMaxNumRelations();
}
