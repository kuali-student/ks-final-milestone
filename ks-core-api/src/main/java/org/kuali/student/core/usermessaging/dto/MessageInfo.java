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
package org.kuali.student.core.usermessaging.dto;


import org.kuali.student.core.usermessaging.infc.Message;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageInfo", propOrder = {"key", "typeKey", "stateKey","name","descr","messageCategoryId",
        "sendingPrincipalId","receiverPersonId","sendDate","contentType","messageContent","messageSubject","meta", "attributes", "_futureElements" })
public class MessageInfo extends IdNamelessEntityInfo implements Message {


    @XmlAnyElement
    private String messageCategoryId;
    @XmlAnyElement
    private String sendingPrincipalId;
    @XmlAnyElement
    private List<String> receiverPersonId;
    @XmlAnyElement
    private Date sendDate;
    @XmlAnyElement
    private String contentType;
    @XmlAnyElement
    private String messageContent;
    @XmlAnyElement
    private String messageSubject;
    @XmlAnyElement
    private List<Object> _futureElements;

    public MessageInfo(){

    }
    public MessageInfo(Message message){
        super(message);

        if(message != null){
            messageCategoryId = message.getMessageCategoryId();
            sendingPrincipalId = message.getSendingPrincipalId();
            if (message.getReceiverPersonId().size() > 0){
                receiverPersonId = new ArrayList<String>(message.getReceiverPersonId());
            }
            if (message.getSendDate() != null){
                sendDate = new Date(message.getSendDate().getTime());
            }
            contentType = message.getContentType();
            messageContent = message.getMessageContent();
            messageSubject = message.getMessageSubject();

        }
    }
    @Override
    public String getMessageCategoryId() {
        return messageCategoryId;
    }

    public void setMessageCategoryId(String messageCategoryId) {
        this.messageCategoryId = messageCategoryId;
    }
    @Override
    public String getSendingPrincipalId() {
        return sendingPrincipalId;
    }

    public void setSendingPrincipalId(String sendingPrincipalId) {
        this.sendingPrincipalId = sendingPrincipalId;
    }
    @Override
    public List<String> getReceiverPersonId() {
        if (receiverPersonId == null){
            receiverPersonId = new ArrayList<String>();
        }
        return receiverPersonId;
    }

    public void setReceiverPersonId(List<String> receiverPersonId) {
        this.receiverPersonId = receiverPersonId;
    }
    @Override
    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    @Override
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    @Override
    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public List<Object> get_futureElements() {
        return _futureElements;
    }

    public void set_futureElements(List<Object> _futureElements) {
        this._futureElements = _futureElements;
    }
}
