/**
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
 *
 * Created by aliabad4 on 3/27/14
 */
package org.kuali.student.enrollment.class2.courseoffering.json;

import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExamOfferingWrapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class RSIJSONResponseData {
    MessageMap messageMap;
    private ExamOfferingWrapper examOfferingWrapper;
    private boolean hasErrors;
    private boolean isCOOverridable;
    private boolean isAOOverridable;

    private Map<String, List<String>> translatedErrorMessages =
            new LinkedHashMap<String, List<String>>();

    public Map<String, List<String>> getTranslatedErrorMessages() {
        return translatedErrorMessages;
    }

    public void setTranslatedErrorMessages(Map<String, List<String>> translatedErrorMessages) {
        this.translatedErrorMessages = translatedErrorMessages;
    }

    public MessageMap getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(MessageMap messageMap) {
        this.messageMap = messageMap;
        if(messageMap != null){
            for(Map.Entry<String, List<ErrorMessage>> entry : messageMap.getErrorMessages().entrySet()) {
                List<String> messageList = new ArrayList<String>();
                for(ErrorMessage errorMesage : entry.getValue()) {
                    String message = KRADServiceLocatorWeb.getMessageService().getMessageText(errorMesage.getErrorKey());
                    messageList.add(message);
                }
                translatedErrorMessages.put(entry.getKey(), messageList);
            }
        }
    }

    public ExamOfferingWrapper getExamOfferingWrapper() {
        return examOfferingWrapper;
    }

    public void setExamOfferingWrapper(ExamOfferingWrapper examOfferingWrapper) {
        this.examOfferingWrapper = examOfferingWrapper;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public boolean isCOOverridable() {
        return isCOOverridable;
    }

    public void setCOOverridable(boolean COOverridable) {
        isCOOverridable = COOverridable;
    }

    public boolean isAOOverridable() {
        return isAOOverridable;
    }

    public void setAOOverridable(boolean AOOverridable) {
        isAOOverridable = AOOverridable;
    }
}
