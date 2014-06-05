/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.core.usermessaging.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.Date;
import java.util.List;

public interface Message extends IdNamelessEntity {

    /**
     * The message category.
     * @name Message Category Id
     */
    String getMessageCategoryId();
    /**
     * The principal sending the messages.
     * @name Sending Principal Id
     */
    String getSendingPrincipalId();
    /**
     * The person receiving the message.
     * @name Receiver Person Id
     */
    List<String> getReceiverPersonId();
    /**
     * When the message was sent.
     * @name Sent Date
     */
    Date getSendDate();
    /**
     * The complexity type of the message.
     * @name Content Type
     */
    String getContentType();
    /**
     * The Content of the message.
     * @name Message Content
     */
    String getMessageContent();
    /**
     * The message subject.
     * @name Message Subject
     */
    String getMessageSubject();

}
