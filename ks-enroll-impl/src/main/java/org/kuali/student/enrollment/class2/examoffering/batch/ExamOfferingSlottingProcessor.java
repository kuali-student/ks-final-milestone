/*
 * Copyright 2006-2010 the original author or authors.
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

import java.util.ArrayList;

import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingResult;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingServiceFacade;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.batch.item.ItemProcessor;

/**
 * This processor is used for the exam offering bulk slotting process.
 */
public class ExamOfferingSlottingProcessor implements
        ItemProcessor<CourseOfferingInfo, ExamOfferingResult> {

    private ContextInfo context;

    private ExamOfferingServiceFacade examOfferingServiceFacade;

    /**
     * @see org.springframework.batch.item.ItemProcessor#process(Object)
     */
    public ExamOfferingResult process(CourseOfferingInfo courseOfferingInfo) throws Exception {
        return examOfferingServiceFacade.generateFinalExamOffering(courseOfferingInfo, new ArrayList<String>(), context);
    }

    public ContextInfo getContext() {
        return context;
    }

    public void setContext(ContextInfo context) {
        this.context = context;
    }

    public ExamOfferingServiceFacade getExamOfferingServiceFacade() {
        return examOfferingServiceFacade;
    }

    public void setExamOfferingServiceFacade(ExamOfferingServiceFacade examOfferingServiceFacade) {
        this.examOfferingServiceFacade = examOfferingServiceFacade;
    }
}
