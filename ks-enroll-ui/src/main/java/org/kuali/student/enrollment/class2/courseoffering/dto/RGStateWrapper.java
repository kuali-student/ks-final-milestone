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
 * Created by cmuller on 6/5/13
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

/**
 * This class is a wrapper for an RG State Propagation Test row
 *
 * @author Kuali Student Team
 */
public class RGStateWrapper {

    private String ao1;
    private String ao2;
    private String ao3;
    private String rgFrom;
    private String rgTo;
    private String expected;
    private String actual;

    public RGStateWrapper(String aoList, String expected, String actual) {
        ao1 = aoList.substring(0,1);
        if(ao1.equals("O")){
            ao1="Offered";
        } else if(ao1.equals("X")) {
            ao1="Not Offered";
        }
        ao2 = aoList.substring(1,2);
        if(ao2.equals("O")){
            ao2="Offered";
        } else if(ao2.equals("X")) {
            ao2="Not Offered";
        }
        ao3 = aoList.substring(2,3);
        if(ao3.equals("O")){
            ao3="Offered";
        } else if(ao3.equals("X")) {
            ao3="Not Offered";
        }
        this.expected = expected;
        this.actual = actual;
        if(this.expected.equals(this.actual)){
            this.status="pass";
        } else {
            this.status="fail";
        }
        if(this.status.equals("pass")){
            this.green=true;
        } else if(this.status.equals("fail")){
            this.red=true;
        }
    }

    public RGStateWrapper(String rgFrom, String rgTo, String expected, String actual) {
        this.rgFrom=rgFrom;
        this.rgTo=rgTo;
        this.expected = expected;
        this.actual = actual;
        if(this.expected.equals(this.actual)){
            this.status="pass";
        } else {
            this.status="fail";
        }
        if(this.status.equals("pass")){
            this.green=true;
        } else if(this.status.equals("fail")){
            this.red=true;
        }
    }

    public RGStateWrapper() {
        this.ao1 = "";
        this.ao2 = "";
        this.ao3 = "";
        this.rgFrom="";
        this.rgTo="";
        this.expected = "";
        this.actual = "";
        if(expected.equals(actual)){
            this.status="Pass";
        } else {
            this.status="Fail";
        }
    }

    public String getAo1() {
        return ao1;
    }

    public void setAo1(String ao1) {
        this.ao1 = ao1;
    }

    public String getAo2() {
        return ao2;
    }

    public void setAo2(String ao2) {
        this.ao2 = ao2;
    }

    public String getAo3() {
        return ao3;
    }

    public void setAo3(String ao3) {
        this.ao3 = ao3;
    }

    public String getRgFrom() {
        return rgFrom;
    }

    public void setRgFrom(String rgFrom) {
        this.rgFrom = rgFrom;
    }

    public String getRgTo() {
        return rgTo;
    }

    public void setRgTo(String rgTo) {
        this.rgTo = rgTo;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    private String status;
    private boolean green;
    private boolean red;




}
