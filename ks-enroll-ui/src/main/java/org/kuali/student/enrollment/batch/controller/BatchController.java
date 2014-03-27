/**
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package org.kuali.student.enrollment.batch.controller;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.batch.BatchScheduler;
import org.kuali.student.enrollment.batch.util.BatchSchedulerConstants;
import org.kuali.student.enrollment.batch.form.BatchForm;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.Date;

@Controller
@RequestMapping(value = "/batch")
public class BatchController extends UifControllerBase {

    private BatchScheduler batchScheduler;

    public UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new BatchForm();
    }

    /**
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=scheduleBatch")
    public ModelAndView scheduleBatch(@ModelAttribute("KualiForm") BatchForm form, @SuppressWarnings("unused") BindingResult result,
                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        KSDateTimeFormatter dateFormatter = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER;
        String date = dateFormatter.format(form.getStartDate());

        //03/26/2014 02:14 PM
        KSDateTimeFormatter dateTimeFormatter = DateFormatters.MONTH_DAY_YEAR_TIME_DATE_FORMATTER;
        Date dateAndTime = dateTimeFormatter.parse(date + " " + form.getStartTime() + " " + form.getStartTimeAmPm());

        this.getBatchScheduler().schedule("kuali.batch.job.examOffering.slotting", null, dateAndTime);
        return super.navigate(form, result, request, response);
    }

    private BatchScheduler getBatchScheduler() {
        if (batchScheduler == null) {
            batchScheduler = GlobalResourceLoader.getService(new QName(BatchSchedulerConstants.NAMESPACE, BatchSchedulerConstants.SERVICE_NAME_LOCAL_PART));
        }
        return batchScheduler;
    }
}
