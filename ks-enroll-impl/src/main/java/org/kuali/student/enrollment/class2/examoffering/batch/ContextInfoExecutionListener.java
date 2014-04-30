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

package org.kuali.student.enrollment.class2.examoffering.batch;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Locale;

import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.enrollment.batch.util.BatchSchedulerConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.file.FlatFileFooterCallback;

/**
 * Writes summary info in the footer of a file.
 */
public class ContextInfoExecutionListener extends StepExecutionListenerSupport {

	private String userId;
    private String language;
    private String country;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().put(BatchSchedulerConstants.BATCH_PARAMETER_CONTEXT, this.createContextInfo());
	}

    public ContextInfo createContextInfo(){

        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(this.getLanguage());
        localeInfo.setLocaleRegion(this.getCountry());

        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(this.getUserId());
        contextInfo.setPrincipalId(this.getUserId());
        contextInfo.setCurrentDate(new Date());
        contextInfo.setLocale(localeInfo);

        return contextInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
