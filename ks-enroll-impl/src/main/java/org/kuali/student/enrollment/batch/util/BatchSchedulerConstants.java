package org.kuali.student.enrollment.batch.util;

import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * Created by SW Genis on 2014/03/26.
 */
public class BatchSchedulerConstants {

    /**
     * Reference Object URIs
     */
    public static final String SERVICE_NAME_LOCAL_PART = "BatchScheduler";
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "batch";

    public static final String BATCH_PARAMETER_START_TIME = "kuali.batch.startTime";

    //Exam Offering slotting batch.
    public static final String BATCH_JOB_EXAM_OFFERING_SLOTTING = "kuali.batch.job.examOffering.slotting";
    public static final String BATCH_PARAMETER_SOC_ID = "kuali.batch.socId";

}
