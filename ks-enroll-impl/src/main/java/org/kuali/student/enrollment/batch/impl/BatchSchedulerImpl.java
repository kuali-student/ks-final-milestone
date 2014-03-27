package org.kuali.student.enrollment.batch.impl;

import org.kuali.student.enrollment.batch.BatchScheduler;
import org.kuali.student.enrollment.batch.dto.BatchJobDefinition;
import org.kuali.student.enrollment.batch.dto.BatchParameter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.TaskScheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by SW Genis on 2014/03/25.
 */
public class BatchSchedulerImpl implements BatchScheduler {

    private TaskScheduler scheduler;
    private JobLauncher jobLauncher;

    private Map<String, Job> jobMap;
    private Map<String, BatchJobDefinition> jobDefinitionMap;

    @Override
    public ScheduledFuture schedule(final String key, final List<BatchParameter> parameters, final Date startTime) {
        return scheduler.schedule(new Runnable() {

            @Override
            public void run() {
                Job job = jobMap.get(key);

                Map<String, JobParameter> parameterMap = new HashMap<String, JobParameter>();
                if(parameters!=null){
                    for(BatchParameter parameter : parameters){
                        parameterMap.put(parameter.getKey(), new JobParameter(parameter.getValue()));
                    }
                }

                parameterMap.put("kuali.batch.startTime", new JobParameter(startTime.getTime()));
                JobParameters jobParameters = new JobParameters(parameterMap);

                try {

                    JobExecution execution = jobLauncher.run(job, jobParameters);
                    System.out.println("Exit Status : " + execution.getStatus());

                } catch (Exception e) {
                    //should probably send a failure email to the user.
                    e.printStackTrace();
                }
            }
        }, startTime);
    }

    @Override
    public List<BatchJobDefinition> getBatchJobDefinitions() {
        return null;
    }

    @Override
    public BatchJobDefinition getBatchJobDefinitionForKey(String key) {
        return null;
    }

    public TaskScheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public Map<String, Job> getJobMap() {
        return jobMap;
    }

    public void setJobMap(Map<String, Job> jobMap) {
        this.jobMap = jobMap;
    }

    public Map<String, BatchJobDefinition> getJobDefinitionMap() {
        return jobDefinitionMap;
    }

    public void setJobDefinitionMap(Map<String, BatchJobDefinition> jobDefinitionMap) {
        this.jobDefinitionMap = jobDefinitionMap;
    }
}
