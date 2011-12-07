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

import org.kuali.student.r2.common.messages.infc.MessageGroupKeys;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageGroupKeysInfo", propOrder = {"messageGroupKeys", "_futureElements"})
public class MessageGroupKeysInfo implements MessageGroupKeys, java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> messageGroupKeys;

    @XmlAnyElement
    private List<Element> _futureElements;

    public MessageGroupKeysInfo() {
    }

    public MessageGroupKeysInfo(MessageGroupKeys messageGroupKeys) {
        if (null != messageGroupKeys) {
            this.messageGroupKeys = (null != messageGroupKeys.getMessageGroupKeys()) ? new ArrayList<String>(messageGroupKeys.getMessageGroupKeys()) : null;
        }
    }

    @Override
    public List<String> getMessageGroupKeys() {
        if (messageGroupKeys == null) {
            this.messageGroupKeys = new ArrayList<String>();
        }
        return this.messageGroupKeys;
    }

    public void setMessageGroupKeys(List<String> messageGroupKeys) {
        this.messageGroupKeys = messageGroupKeys;
    }
}
