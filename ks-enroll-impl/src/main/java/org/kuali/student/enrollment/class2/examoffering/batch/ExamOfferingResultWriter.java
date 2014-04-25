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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingResult;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.mail.DefaultMailErrorHandler;
import org.springframework.batch.item.mail.MailErrorHandler;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.Assert;

public class ExamOfferingResultWriter implements ItemWriter<ExamOfferingResult> {

    private MailSender mailSender;

    private MailErrorHandler mailErrorHandler = new DefaultMailErrorHandler();

    /**
     * A {@link MailSender} to be used to send messages in {@link #write(List)}.
     *
     * @param mailSender
     */
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * The handler for failed messages. Defaults to a
     * {@link DefaultMailErrorHandler}.
     *
     * @param mailErrorHandler the mail error handler to set
     */
    public void setMailErrorHandler(MailErrorHandler mailErrorHandler) {
        this.mailErrorHandler = mailErrorHandler;
    }

    /**
     * @param items the items to send
     * @see ItemWriter#write(List)
     */
    @Override
    public void write(List<? extends ExamOfferingResult> items) throws MailException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo( "sw@kuali.org" );
        message.setFrom( "communications@thecompany.com" );
        message.setSubject( "SW's Account Info" );
        message.setSentDate( new Date() );
        message.setText( "Hello SW" );
        return;

        /*try {
            mailSender.send(message);
        }
        catch (MailSendException e) {
            @SuppressWarnings("unchecked")
            Map<Object, Exception> failedMessages = e.getFailedMessages();
            for (Map.Entry<Object, Exception> entry : failedMessages.entrySet()) {
                mailErrorHandler.handle((SimpleMailMessage) entry.getKey(), entry.getValue());
            }
        }*/
    }

}
