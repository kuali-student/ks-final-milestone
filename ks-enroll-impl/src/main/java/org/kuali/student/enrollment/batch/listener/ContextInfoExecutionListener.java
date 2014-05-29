/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.enrollment.batch.listener;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Locale;

import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.enrollment.batch.util.BatchSchedulerConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.file.FlatFileFooterCallback;

/**
 * Writes summary info in the footer of a file.
 */
public class ContextInfoExecutionListener extends JobExecutionListenerSupport {

	@Override
	public void beforeJob(JobExecution jobExecution) {

        //We retrieve these values directly form jobparameters as scope="job" does not exist.
        String userId = jobExecution.getJobParameters().getString(BatchSchedulerConstants.BATCH_PARAMETER_USER_ID);
        String language = jobExecution.getJobParameters().getString(BatchSchedulerConstants.BATCH_PARAMETER_USER_ID);
        String country = jobExecution.getJobParameters().getString(BatchSchedulerConstants.BATCH_PARAMETER_USER_ID);

        ContextInfo contextInfo = this.createContextInfo(userId, language, country);

        jobExecution.getExecutionContext().put(BatchSchedulerConstants.BATCH_PARAMETER_CONTEXT, contextInfo);
	}

    public ContextInfo createContextInfo(String userId, String language, String country){

        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(language);
        localeInfo.setLocaleRegion(country);

        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(userId);
        contextInfo.setPrincipalId(userId);
        contextInfo.setCurrentDate(new Date());
        contextInfo.setLocale(localeInfo);

        return contextInfo;
    }

}
