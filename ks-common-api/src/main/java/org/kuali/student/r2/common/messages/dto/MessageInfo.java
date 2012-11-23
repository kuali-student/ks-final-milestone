/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.common.messages.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.messages.infc.Message;
//import org.w3c.dom.Element;

/**
 *
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageInfo", propOrder = { 
                "key", "locale", "groupName", "value" , "_futureElements" }) 

public class MessageInfo 
    implements Message, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String key;

    @XmlElement
    private LocaleInfo locale;

    @XmlElement
    private String groupName;

    @XmlElement
    private String value;

    @XmlAnyElement
    private List<Object> _futureElements;  


    /**
     * Constructs a new MessageInfo.
     */
    public MessageInfo() {
    }

    /**
     * Constructs a new MessageInfo from a Message.
     *
     * @param message the Message to copy
     */
    public MessageInfo(Message message) {
        if(message != null) {
            if (message.getLocale() != null) {
                this.locale = new LocaleInfo(message.getLocale());
            }

            this.groupName = message.getGroupName();
            this.value = message.getValue();
        }
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public LocaleInfo getLocale() {
        return this.locale;
    }

    public void setLocale(LocaleInfo locale) {
        this.locale = locale;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
