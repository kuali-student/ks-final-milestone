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

import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.RichText;

/**
 * Object that holds individual item results of a batch job
 * 
 * @author nwright
 */
public interface BatchJobResultItem
        extends IdNamelessEntity {
  /**
     * The id of the batch job result to which this is attached
     * 
     * @name Batch Job Result Id
     */
    public String getBatchJobResultId();

    /**
     * The id of the source object being processed
     * 
     * The kind of object being processed depends on the type of the batch job.
     * 
     * @name Source Id
     */
    public String getSourceId();

    /**
     * The id of the target object being created or processed 
     * 
     * TODO: WORRY ABOUT SITUATIONS THAT ARE NOT ONE FOR ONE
     * 
     * @name Target Id
     */
    public String getTargetId();

    /**
     * Messages describing details of the status.
     * 
     * Often left null if the status is that it is complete or successful.
     * 
     * @name Message
     */
    public RichText getMessage();
}
