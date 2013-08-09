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
package org.kuali.student.enrollment.courseofferingset.infc;

import java.util.List;
import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.RichText;

/**
 * Read only object that holds the results of a rollover process
 * 
 * @author nwright
 */
public interface SocRolloverResultItem
        extends IdNamelessEntity {

    /**
     * The id of the rollover result to which this item belongs
     * 
     * @name Soc Rollover Result Id
     * @readOnly
     * @required
     * @impl set during the #rolloverSoc operation
     */
    public String getSocRolloverResultId();

    /**
     * The id of the source course offering 
     * 
     * @name Source Course Offering Id
     * @readOnly
     * @required
     * @impl set during the #rolloverSoc operation
     */
    public String getSourceCourseOfferingId();

    /**
     * The id of the target course offering 
     * 
     * Can be null if the rollover did not rollover the source course offering.
     * 
     * @name Source Course Offering Id
     * @readOnly
     * @impl set during the #rolloverSoc operation
     */
    public String getTargetCourseOfferingId();

    /**
     * Messages describing details of the status.
     * 
     * Often left null if the status is that it is complete.
     * 
     * @name Message
     * @readOnly
     * @impl set during the #rolloverSoc operation
     */
    public RichText getMessage();
}
