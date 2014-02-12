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
 * Created by cmuller on 1/7/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

/**
 * This class is a data structure that holds term information
 * for the view terms REST service
 *
 * @author Kuali Student Team
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TermSearchResult", propOrder = {
        "termName", "termId", "termCode"})
public class TermSearchResult {
    private String termName;
    private String termId;
    private String termCode;
    private Date startDate = null;
    private Date endDate = null;

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * we want to be able to determine if the term is current. This helps with us defaulting dropdowns
     * to the current term.
     *
     * @return startDate <= currentDate < endDate
     */
    public boolean isCurrentTerm() {
        boolean bRet = false;
        if(startDate != null && endDate != null){
            long current = System.currentTimeMillis();
            bRet = (startDate.getTime() <= current) && (current  < endDate.getTime());
        }
        return bRet;
    }

}
