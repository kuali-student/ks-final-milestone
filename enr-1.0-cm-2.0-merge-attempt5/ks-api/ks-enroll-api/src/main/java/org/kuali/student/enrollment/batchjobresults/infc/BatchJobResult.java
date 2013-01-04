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
package org.kuali.student.enrollment.batchjobresults.infc;

import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.RichText;

/**
 * Object that holds the results of a batch job
 * 
 * @author nwright
 */
public interface BatchJobResult
        extends IdEntity {

    /**
     * The parameters or options specified when the batch job was run
     * 
     * Note the list may be empty but it cannot be null
     * 
     * @name Parameters
     * @readOnly
     * @required
     * @impl set during the createBatchJobResult operation
     */
    public List<? extends Attribute> getParameters();

    /**
     * The context under which this batch job was run
     * 
     * @name Context
     * @readOnly
     * @required
     * @impl set during the createBatchJobResult operation
     */
    public ContextInfo getContext();

    /**
     * Any global results of running the batch job
     * 
     * This may be a number or a count or a newly created id.
     * 
     * This is in addition to individual Result Items that may be created.
     * 
     * Note the list may be empty but like other lists it must not be null 
     * @name Parameters
     */
    public List<? extends Attribute> getGlobalResults();

    /**
     * Number of items processed
     *     
     * Used to indicate the progress of the batch job
     *
     * The batch job does not have to update this field every time it processes
     * a job it may decide to update it every 10 or 100 or 1000 items processed.
     * 
     * This can be null if the job has not started yet or there are no items 
     * This may or may not correspond with the corresponding Result Items that get created
     * 
     * @name Items Processed
     */
    public Integer getItemsProcessed();

    /**
     * Number of items expected to be processed
     *     
     * @name Items Expected
     * @readOnly
     * @impl set during the #rolloverSoc operation
     */
    public Integer getItemsExpected();

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
