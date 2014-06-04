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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.batch.util.BatchSchedulerConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;

/**
 * Retrieve list of course offerings for soc.
 * 
 * @author SW Genis
 */
public class CourseOfferingItemReader extends AbstractItemStreamItemReader<CourseOfferingInfo> implements InitializingBean {

    private static final Log logger = LogFactory.getLog(CourseOfferingItemReader.class);

    private ContextInfo context;

    private String socId;
    private Iterator<String> courseOfferingIds;

    private CourseOfferingService courseOfferingService;
    private CourseOfferingSetService socService;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public CourseOfferingInfo read() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        if (courseOfferingIds.hasNext()) {
            String coId = courseOfferingIds.next();
            return courseOfferingService.getCourseOffering(coId, ContextUtils.createDefaultContextInfo());
        } else {
            return null; // end of data
        }
    }

    /**
     * No-op.
     * @see org.springframework.batch.item.ItemStream#close()
     */
    @Override
    public void close() {
    }

    /**
     * No-op.
     * @see org.springframework.batch.item.ItemStream#open(org.springframework.batch.item.ExecutionContext)
     */
    @Override
    public void open(ExecutionContext executionContext) {
        try {
            List<String> coIdList = socService.getCourseOfferingIdsBySoc(socId, context);
            courseOfferingIds = coIdList.iterator();
        } catch (Exception e) {
            //Don't know what do to yet.
        }
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

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public CourseOfferingSetService getSocService() {
        return socService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

}
