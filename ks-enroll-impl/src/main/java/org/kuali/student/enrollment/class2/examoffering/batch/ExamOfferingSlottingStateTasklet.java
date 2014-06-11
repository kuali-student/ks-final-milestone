package org.kuali.student.enrollment.class2.examoffering.batch;

import org.kuali.student.enrollment.batch.util.BatchSchedulerConstants;
import org.kuali.student.enrollment.class2.courseofferingset.model.SocAttributeEntity;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by SW Genis on 2014/03/24.
 */
public class ExamOfferingSlottingStateTasklet implements Tasklet {

    private ContextInfo context;

    private String socId;
    private String stateKey;
    private CourseOfferingSetService socService;

    public RepeatStatus execute(StepContribution arg0, ChunkContext chunkContext) {
        try {
            SocInfo soc = this.getSocService().getSoc(socId, context);
            AttributeInfo attr = new AttributeInfo(stateKey, DateFormatters.SERVER_DATE_PARSER_FORMATTER.format(new Date()));
            soc.getAttributes().add(attr);
            this.getSocService().updateSoc(socId, soc, context);

        } catch (Exception e) {
            // Don't know what to do yet.
        }

        // Return status as finished.
        return RepeatStatus.FINISHED;
    }

    public ContextInfo getContext() {
        return context;
    }

    public void setContext(ContextInfo context) {
        this.context = context;
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }

    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public CourseOfferingSetService getSocService() {
        return socService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }
}
