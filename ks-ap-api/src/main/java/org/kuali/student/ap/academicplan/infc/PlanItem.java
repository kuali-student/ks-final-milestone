/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
package org.kuali.student.ap.academicplan.infc;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * A single plan item. Plan items can link to different entities in the system based on the refObjectType.
 *
 * @author Kuali Student Team  (ks-collab@kuali.org)
 * @version 1.0 (Dev)
 */
public interface PlanItem extends IdEntity, TypedObjectReference {

    /**
     * Time Periods for which the item is planned for
     * @name  Time Periods ATPIds
     */
    public List<String> getPlanTermIds();

    /**
     * Containing learning plan
     * @name Learning Plan Id
     * @required
     */
    public String getLearningPlanId();

    /**
    * The plan credits
    * @name The number of credits
    */
    public BigDecimal getCredits();

    /**
     * Category of item:  BOOKMARKED, BACKUP, PLANNED, CART,...
     * @required
     * @name Item Category
     */
    public AcademicPlanServiceConstants.ItemCategory getCategory();

}
