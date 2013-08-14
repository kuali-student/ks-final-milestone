/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import java.util.Date;

/**
 * This is the model class which holds the SOC status and the dates.
 * Note this class implemented with <code>Comparable</code> which allows the status collection
 * to sort based on the ascending date for display purpose
 *
 * @author Kuali Student Team
 */
public class ManageSOCStatusHistory implements Comparable<ManageSOCStatusHistory>{

    private String state;
    private String stateKey;
    private String date;
    private Date dateObject;
    private boolean highlightUI;
    private boolean greyText;

    public ManageSOCStatusHistory(){
    }

    public ManageSOCStatusHistory(String stateName,String stateKey,String date,Date dateObject){
        this.state = stateName;
        this.stateKey = stateKey;
        this.date = date;
        this.dateObject = dateObject;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    public void setHighlightUI(boolean highlightUI) {
        this.highlightUI = highlightUI;
    }

    public boolean isHighlightUI() {
        return highlightUI;
    }

    public Date getDateObject() {
        return dateObject;
    }

    public boolean isGreyText() {
        return greyText;
    }

    public void setGreyText(boolean greyText) {
        this.greyText = greyText;
    }

    public String getStateKey() {
        return stateKey;
    }

    @Override
    public int compareTo(ManageSOCStatusHistory manageSOCStatusHistory) {
        //FindBugs - it is fine as is
        if (this.getDateObject() != null && manageSOCStatusHistory.getDateObject() != null){
            return getDateObject().compareTo(manageSOCStatusHistory.getDateObject());
        }else if (manageSOCStatusHistory.getDateObject() == null){
            return -1;
        }else{
            return 1;
        }
    }

}
