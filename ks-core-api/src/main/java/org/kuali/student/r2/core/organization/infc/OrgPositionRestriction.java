/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.organization.infc;

import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TimeAmount;


/**
 * Information which constrains/describes organization to person
 * relationships of a particular type for an organization. These
 * constraints/descriptions typically involve active relationships.
 *
 * @author tom
 */ 

public interface OrgPositionRestriction
    extends HasAttributesAndMeta, HasId {
  
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
     * @name Organization Person Relation Type Key
     * @required
     * @readOnly
     */
    public String getOrgPersonRelationTypeKey();

    /**
     * Title of organization person relationships of this type. This allows for
     * distinction from the name of the relationship type itself, specific for 
     * the given organization.
     *
     * @name Title
     */
    public String getTitle();


    /**
     * Description of the restrictions and use of the relationship type within
     * this particular organization. This should primarily focus on deviations
     * from the standard use of the relationship type.
     *
     * @name Description
     */
    public RichText getDescr();
    
    
    /**
     * Describes the standard duration of relationships of this type.
     *
     * @name Standard Duration
     */
    public TimeAmount getStdDuration();

    /**
     * Acts as a lower bound on the number of relationships of this
     * type expected for the organization. If specified, this should
     * be less than or equal to the value of maxNumRelations. This
     * number must be greater than or equal to zero.
     *
     * @name Minimum Number of Relations
     */
    public Integer getMinNumRelations();

    /**
     * Acts as an upper bound on the number of relationships of this type 
     * expected for the organization. The values of this field are restricted 
     * to integer values and the string "unbounded". If specified, 
     * this should be greater than or equal to the value of minNumRelations, 
     * with the value "unbounded" being automatically assumed to be greater.
     *
     * @name Maximum Number of Relations
     */
    public String getMaxNumRelations();
}
