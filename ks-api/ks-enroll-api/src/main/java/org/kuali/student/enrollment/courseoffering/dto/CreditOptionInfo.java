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
 * Created by vgadiyak on 6/15/12
 */
package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditOptions", propOrder = {"id", "typeKey", "credits", "minCredits", "maxCredits"})
//TODO Refactor this out of the API, it belongs in enroll ui dto
public class CreditOptionInfo extends IdEntityInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String typeKey;

    @XmlElement
    List<String> credits;

    List<String> allowedCredits;

    @XmlElement
    String minCredits;

    @XmlElement
    String maxCredits;

    String fixedCredit;

    String courseMinCredits;
    String courseMaxCredits;
    String courseFixedCredits;

    /**
     * Constructs a new CreditOptions.
     */
    public CreditOptionInfo() {
    }

    public String getTypeKey() {
        return this.typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public List<String> getCredits() {
        if (null == credits) {
            credits = new ArrayList<String>();
        }
        return credits;
    }

    public List<String> getAllowedCredits() {
        if (null == allowedCredits) {
            allowedCredits = new ArrayList<String>();
        }
        return allowedCredits;
    }

    public void setAllowedCredits(List<String> allowedCredits) {
        this.allowedCredits = allowedCredits;
    }

    public void setCredits(List<String> credits) {
        this.credits = credits;
    }

    public String getMinCredits() {
        return this.minCredits;
    }

    public void setMinCredits(String minCredits) {
        this.minCredits = minCredits;
    }

    public String getMaxCredits() {
        return this.maxCredits;
    }

    public void setMaxCredits(String maxCredits) {
        this.maxCredits = maxCredits;
    }

    public String getFixedCredit() {
        return fixedCredit;
    }

    public void setFixedCredit(String fixedCredit) {
        this.fixedCredit = fixedCredit;
    }


    public String getCourseFixedCredits() {
        return courseFixedCredits;
    }

    public void setCourseFixedCredits(String courseFixedCredits) {
        this.courseFixedCredits = courseFixedCredits;
    }

    public String getCourseMaxCredits() {
        return courseMaxCredits;
    }

    public void setCourseMaxCredits(String courseMaxCredits) {
        this.courseMaxCredits = courseMaxCredits;
    }

    public String getCourseMinCredits() {
        return courseMinCredits;
    }

    public void setCourseMinCredits(String courseMinCredits) {
        this.courseMinCredits = courseMinCredits;
    }
}
