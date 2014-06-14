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

import java.util.List;

import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingResult;
import org.springframework.batch.item.ItemWriter;

public class ExamOfferingResultWriter implements ItemWriter<ExamOfferingResult> {

    /**
     * @param items the items to send
     * @see ItemWriter#write(List)
     */
    @Override
    public void write(List<? extends ExamOfferingResult> items) {
        // Not needed at the moment but could be used to send email to user if required.
    }

}
