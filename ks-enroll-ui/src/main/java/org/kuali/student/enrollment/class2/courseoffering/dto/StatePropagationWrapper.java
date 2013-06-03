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
 * Created by cmuller on 5/22/13
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

/**
 * This class is a wrapper for a State Propagation Test table item
 *
 * @author Kuali Student Team
 */
public class StatePropagationWrapper {
    private String socState;
    private String aoFrom;
    private String aoTo;
    private String expected;
    private String actual;
    private String status;

    public StatePropagationWrapper(String socState, String aoFrom, String aoTo, String expected, String actual, String status) {
        this.socState = socState;
        this.aoFrom = aoFrom;
        this.aoTo = aoTo;
        this.expected = expected;
        this.actual = actual;
        this.status= status;
    }

    public StatePropagationWrapper() {
        this.socState = "";
        this.aoFrom = "";
        this.aoTo = "";
        this.expected = "";
        this.actual = "";
        if(expected.equals(actual)){
            this.status="Pass";
        } else {
            this.status="Fail";
        }
    }

    public String getSocState() {
        return socState;
    }

    public void setSocState(String socState) {
        this.socState = socState;
    }

    public String getAoFrom() {
        return aoFrom;
    }

    public void setAoFrom(String aoFrom) {
        this.aoFrom = aoFrom;
    }

    public String getAoTo() {
        return aoTo;
    }

    public void setAoTo(String aoTo) {
        this.aoTo = aoTo;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
        if(expected.equals(actual)){
            this.status="Pass";
        } else {
            this.status="Fail";
        }
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
        if(expected.equals(actual)){
            this.status="Pass";
        } else {
            this.status="Fail";
        }
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
