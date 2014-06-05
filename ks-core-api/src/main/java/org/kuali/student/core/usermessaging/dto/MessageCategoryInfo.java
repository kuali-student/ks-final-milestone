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


import org.kuali.student.core.usermessaging.infc.MessageCategory;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageCategoryInfo", propOrder = {"key", "typeKey", "stateKey","name","descr","addressType",
         "meta", "attributes", "_futureElements" })
public class MessageCategoryInfo extends KeyEntityInfo implements MessageCategory {

    @XmlElement
    private List<String> addressType;
    @XmlAnyElement
    private List<Object> _futureElements;

    public MessageCategoryInfo(){

    }
    public MessageCategoryInfo(MessageCategory messageCategory) throws OperationFailedException {
        super(messageCategory);

        if (messageCategory != null){

            if (messageCategory.getAddressType().size() > 0){
                addressType = new ArrayList<String>(messageCategory.getAddressType());
            }
        }

    }

    @Override
    public List<String> getAddressType() {
        if (addressType == null){
            addressType = new ArrayList<String>();
        }
        return addressType;
    }

    public void setAddressType(List<String> addressType) {
        this.addressType = addressType;
    }

    public List<Object> get_futureElements() {
        return _futureElements;
    }

    public void set_futureElements(List<Object> _futureElements) {
        this._futureElements = _futureElements;
    }
}
