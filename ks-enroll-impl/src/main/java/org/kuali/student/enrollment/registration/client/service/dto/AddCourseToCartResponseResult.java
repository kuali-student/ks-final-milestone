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
 * Created by jmorris on 10/6/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientServiceConstants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddCourseToCartResponseResult", propOrder = {
        "cartItem", "messages", "state"})
public class AddCourseToCartResponseResult {
    private CartItemResult cartItem;
    private List<UserMessageResult> messages;
    private String state;

    public AddCourseToCartResponseResult() {
    }

    public AddCourseToCartResponseResult(CartItemResult cartItem) {
        this.cartItem = cartItem;
    }

    public CartItemResult getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItemResult cartItem) {
        this.cartItem = cartItem;
    }

    public void setError() {
        this.setState(CourseRegistrationCartClientServiceConstants.AddToCartStates.ERROR);
    }

    public void addMessage(UserMessageResult message) {
        getMessages().add(message);
    }

    public List<UserMessageResult> getMessages() {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        return messages;
    }

    public void setMessages(List<UserMessageResult> messages) {
        this.messages = messages;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setSuccess() {
        this.setState(CourseRegistrationCartClientServiceConstants.AddToCartStates.SUCCESS);
    }
}
