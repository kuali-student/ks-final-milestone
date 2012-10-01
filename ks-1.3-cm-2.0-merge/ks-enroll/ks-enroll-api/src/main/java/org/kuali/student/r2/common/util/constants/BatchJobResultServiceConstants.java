/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.batchjobresults.dto.BatchJobResultItemInfo;
import org.kuali.student.enrollment.batchjobresults.dto.BatchJobResultInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * Batch Job Results Service Constants
 *
 * @author nwright
 */
public class BatchJobResultServiceConstants {

   
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "batchJobResults";
    public static final String SERVICE_NAME_LOCAL_PART = "batchJobResultsService";
    public static final String REF_OBJECT_URI_BATCH_JOB_RESULTS = NAMESPACE + "/" + BatchJobResultInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_BATCH_JOB_RESULT_ITEM = NAMESPACE + "/" + BatchJobResultItemInfo.class.getSimpleName();
    

}
