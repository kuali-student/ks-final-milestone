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
 * Holds information about what happened to an event after being processed by a handler.
 *
 * @author Kuali Student Team
 */
public class KSHandlerResult {
    public static final String SUCCESS = "success";
    public static final String FAIL_CONSTRAINT_NOT_SATISFIED = "fail:constraintNotSatisified";
    public static final String FAIL_STATE_UNCHANGED = "fail:stateUnchanged";
    public static final String FAIL_HANDLER_WONT_PROCESS = "fail:handlerWontProcess";

    private String resultCode;
    private String handlerClassName;

    private boolean success = true;
    private List<String> errorMessages = new ArrayList<String>();
    private List<KSHandlerResult> downstreamResults = new ArrayList<KSHandlerResult>();

    /**
     * @param resultCode Uses code in static strings above
     * @param handlerClass The handler class that produced this result
     */
    public KSHandlerResult(String resultCode, Class handlerClass) {
        this.resultCode = resultCode;
        handlerClassName = handlerClass.getSimpleName();
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

    public void addDownstreamResult(KSHandlerResult downstreamResult) {
        this.downstreamResults.add(downstreamResult);
    }

    public void addDownstreamResultList(List<KSHandlerResult> downstreamResultList) {
        this.downstreamResults.addAll(downstreamResultList);
    }

    public List<KSHandlerResult> getDownstreamResults() {
        return Collections.unmodifiableList(downstreamResults);
    }

    public String getHandlerClassName() {
        return handlerClassName;
    }
}
