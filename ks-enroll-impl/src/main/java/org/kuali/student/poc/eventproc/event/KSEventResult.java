/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by Charles on 9/13/13
 */
package org.kuali.student.poc.eventproc.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds information about what happened to an event.
 *
 * @author Kuali Student Team
 */
public class KSEventResult {
    public static final String SUCCESS = "success";
    public static final String FAIL_CONSTRAINT_NOT_SATISFIED = "fail:constraintNotSatisified";
    public static final String FAIL_STATE_UNCHANGED = "fail:stateUnchanged";
    public static final String FAIL_INCORRECT_HANDLER = "fail:incorrectHandler";

    private String resultCode;
    private boolean success = true;
    private List<String> errorMessages = new ArrayList<String>();
    private List<KSEventResult> downstreamResults = new ArrayList<KSEventResult>();
    private String handlerName = null;

    public KSEventResult(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setSuccess(boolean value) {
        success = value;
    }

    public boolean isSuccessful() {
        return success;
    }

    public void addErrorMessage(String error) {
        success = false;
        errorMessages.add(error);
    }

    public List<String> getErrorMessages() {
        return Collections.unmodifiableList(errorMessages);
    }

    public void addDownstreamResult(KSEventResult downstreamResult) {
        this.downstreamResults.add(downstreamResult);
    }

    public void addDownstreamResultList(List<KSEventResult> downstreamResultList) {
        this.downstreamResults.addAll(downstreamResultList);
    }

    public List<KSEventResult> getDownstreamResults() {
        return Collections.unmodifiableList(downstreamResults);
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName){
        this.handlerName = handlerName;
    }
}
