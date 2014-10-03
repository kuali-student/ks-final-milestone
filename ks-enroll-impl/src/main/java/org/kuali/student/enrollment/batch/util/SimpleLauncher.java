package org.kuali.student.enrollment.batch.util;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.HashMap;
import java.util.Map;

class SimpleLauncher {

    private Job job;
    private JobLauncher jobLauncher;

    private static int counter = 0;

    public void launch() {

        // Create parameters for job.
        JobParameters jobParams = createJobParameters();

        // Run job.
        JobExecution result = null;
        try {
            result = getJobLauncher().run(getJob(), jobParams);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }


        // After job execution show details.
        System.out.print("Job status is " + result.getStatus() + ". ");
        System.out.print("Job has been started : " + result.getStartTime() + " and finished: "
                + result.getEndTime() + ".\n");
    }

    private JobParameters createJobParameters() {
        Map<String, JobParameter> parametersMap = new HashMap<String, JobParameter>();
        parametersMap.put("JobName", new JobParameter("JobName_" + ++counter));
        JobParameters jobParameters = new JobParameters(parametersMap);
        return jobParameters;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }
}
