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

import java.util.Date;
import java.util.List;
import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.RichText;

/**
 * Read only object that holds the results of a rollover process
 *
 * @author nwright
 */
public interface SocRolloverResult
        extends IdNamelessEntity {

    /**
     * The id of the source soc
     *
     * @name Source Soc Id
     * @readOnly
     * @required
     * @impl set during the #rolloverSoc operation
     */
    public String getSourceSocId();

    /**
     * Academic target term of the courses that were rolled over
     *
     * @name Target Term Id
     * @readOnly
     * @required
     * @impl set during the #rolloverSoc operation
     */
    public String getTargetTermId();

    /**
     * Option Keys specified to control the rollover process
     *
     * @name Option Keys
     * @readOnly
     * @required
     * @impl set during the #rolloverSoc operation
     */
    public List<String> getOptionKeys();

    /**
     * The id of the target Soc
     *
     * Note: this Soc could have already existed or it may have been created 
     * based on the source soc but for the new term.
     *
     * @name Target Soc Id
     * @readOnly
     * @required
     * @impl set during the #rolloverSoc operation
     */
    public String getTargetSocId();

    /**
     * Number of items processed
     *
     * @name Items Processed
     * @impl set during the #rolloverSoc operation
     */
    public Integer getItemsProcessed();

    /**
     * Number of items expected to be processed
     *
     * @name Items Expected
     * @impl set during the #rolloverSoc operation
     */
    public Integer getItemsExpected();

    /**
     * Messages describing details of the status.
     *
     * Often left null if the status is that it is complete.
     *
     * @name Message
     * @impl set during the #rolloverSoc operation
     */
    public RichText getMessage();

    /**
     * Number of course offerings created 
     *     
     * @name Course Offerings Created
     * @readOnly 
     * @impl set during the #rolloverSoc operation
     */
    public Integer getCourseOfferingsCreated();

    /**
     * Number of course offerings skipped (not rolled over)
     *     
     * @name Course Offerings Skipped
     * @readOnly 
     * @impl set during the #rolloverSoc operation
     */
    public Integer getCourseOfferingsSkipped();

    /**
     * Number of activity offerings created 
     *     
     * @name Activity Offerings Created
     * @readOnly 
     * @impl set during the #rolloverSoc operation
     */
    public Integer getActivityOfferingsCreated();

    /**
     * Number of activity offerings skipped (not rolled over)
     *     
     * @name Activity Offerings Skipped
     * @readOnly 
     * @impl set during the #rolloverSoc operation
     */
    public Integer getActivityOfferingsSkipped();

    /**
     * Get the target term Id
     *
     * @name Source Term Id
     * @readOnly 
     * @impl set during the #rolloverSoc operation
     */
    public String getSourceTermId();

    /**
     * Get date that the rollover started on.  Date includes hours/minutes/seconds.
     */
    public Date getDateInitiated();

    /**
     * Get the date that the rollover completed.  Date includes hours/minutes/seconds.
     */
    public Date getDateCompleted();
}
