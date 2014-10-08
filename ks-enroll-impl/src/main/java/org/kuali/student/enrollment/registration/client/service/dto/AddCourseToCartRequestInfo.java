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
 * Created by jmorris on 10/7/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class is a DTO representing a Cart Item used by the REST service
 *
 * @author Kuali Student Team
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CartItemResult", propOrder = {
        "termId", "cartItemId", "courseCode", "regGroupCode", "regGroupId", "credits", "gradingOptionId"})
public class AddCourseToCartRequestInfo {
    private String cartItemId;
    private String courseCode;
    private String credits;
    private String gradingOptionId;
    private String regGroupCode;
    private String regGroupId;
    private String termId;

    public AddCourseToCartRequestInfo() {
    }

    public AddCourseToCartRequestInfo(String cartItemId, String courseCode, String credits, String gradingOptionId, String regGroupCode, String regGroupId, String termId) {
        this.cartItemId = cartItemId;
        this.courseCode = courseCode;
        this.credits = credits;
        this.gradingOptionId = gradingOptionId;
        this.regGroupCode = regGroupCode;
        this.regGroupId = regGroupId;
        this.termId = termId;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getGradingOptionId() {
        return gradingOptionId;
    }

    public void setGradingOptionId(String gradingOptionId) {
        this.gradingOptionId = gradingOptionId;
    }

    public String getRegGroupCode() {
        return regGroupCode;
    }

    public void setRegGroupCode(String regGroupCode) {
        this.regGroupCode = regGroupCode;
    }

    public String getRegGroupId() {
        return regGroupId;
    }

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }
}
