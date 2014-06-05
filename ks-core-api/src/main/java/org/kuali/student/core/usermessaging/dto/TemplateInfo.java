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


import org.kuali.student.core.usermessaging.infc.Template;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TemplateInfo", propOrder = {"key", "typeKey", "stateKey","name","descr","messageCategoryId",
        "templateMessageContent","addressType","meta", "attributes", "_futureElements" })
public class TemplateInfo extends IdNamelessEntityInfo implements Template {

    @XmlElement
    private String messageCategoryId;
    @XmlElement
    private String templateMessageContent;
    @XmlElement
    private String addressType;
    @XmlAnyElement
    private List<Object> _futureElements;

    public TemplateInfo(){

    }

    public TemplateInfo(Template template){
        super(template);

        if (template != null){

            messageCategoryId = template.getMessageCategoryId();
            templateMessageContent = template.getTemplateMessageContent();
            addressType = template.getAddressType();
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
    public String getTemplateMessageContent() {
        return templateMessageContent;
    }

    public void setTemplateMessageContent(String templateMessageContent) {
        this.templateMessageContent = templateMessageContent;
    }
    @Override
    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public List<Object> get_futureElements() {
        return _futureElements;
    }

    public void set_futureElements(List<Object> _futureElements) {
        this._futureElements = _futureElements;
    }
}
