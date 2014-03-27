package org.kuali.student.enrollment.batch;

import org.kuali.student.enrollment.batch.dto.BatchJobDefinition;
import org.kuali.student.enrollment.batch.dto.BatchParameter;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by SW Genis on 2014/03/25.
 */
public interface BatchScheduler {

    ScheduledFuture schedule(String key, List<BatchParameter> parameters, Date startTime);

    List<BatchJobDefinition> getBatchJobDefinitions();

    BatchJobDefinition getBatchJobDefinitionForKey(String key);

}
