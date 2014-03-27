package org.kuali.student.enrollment.class2.examoffering.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created by SW Genis on 2014/03/24.
 */
public class ExamOfferingSlottingTasklet implements Tasklet {

    private String message;

    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) {
        // Show message on console.
        System.out.println("Message: " + getMessage());


        // Return status as finished.
        return RepeatStatus.FINISHED;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
