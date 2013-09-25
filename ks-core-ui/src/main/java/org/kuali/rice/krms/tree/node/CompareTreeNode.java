/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.tree.node;

import groovy.json.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class CompareTreeNode implements Serializable {

    private String firstElement;
    private List<String> firstElementItems;
    private String secondElement;
    private List<String> secondElementItems;
    private String thirdElement;
    private List<String> thirdElementItems;
    private String fourthElement;
    private List<String> fourthElementItems;
    private String fithElement;
    private List<String> fithElementItems;

    public CompareTreeNode(){
    }

    public CompareTreeNode(String... elements){
        for (int i = 0; i < elements.length; i++) {
            switch (i) {
                case 0:
                    this.firstElement = elements[0];
                    break;
                case 1:
                    this.secondElement = elements[1];
                    break;
                case 2:
                    this.thirdElement = elements[2];
                    break;
                case 3:
                    this.fourthElement= elements[3];
                    break;
                case 4:
                    this.fithElement = elements[4];
                    break;
            }
        }
    }

    public String getFirstElement() {
        if((this.firstElement == null) || (this.firstElement.isEmpty())){
            return org.apache.commons.lang.StringEscapeUtils.escapeHtml(" ");
        }
        return firstElement;
    }

    public void setFirstElement(String firstElement) {
        this.firstElement = firstElement;
    }

    public List<String> getFirstElementItems() {
        return firstElementItems;
    }

    public void setFirstElementItems(List<String> firstElementItems) {
        this.firstElementItems = firstElementItems;
    }

    public String getSecondElement() {
        if((this.secondElement == null) || (this.secondElement.isEmpty())){
            return org.apache.commons.lang.StringEscapeUtils.escapeHtml(" ");
        }
        return secondElement;
    }

    public void setSecondElement(String secondElement) {
        this.secondElement = secondElement;
    }

    public List<String> getSecondElementItems() {
        return secondElementItems;
    }

    public void setSecondElementItems(List<String> secondElementItems) {
        this.secondElementItems = secondElementItems;
    }

    public String getThirdElement() {
        if((this.thirdElement == null) || (this.thirdElement.isEmpty())){
            return org.apache.commons.lang.StringEscapeUtils.escapeHtml(" ");
        }
        return thirdElement;
    }

    public void setThirdElement(String thirdElement) {
        this.thirdElement = thirdElement;
    }

    public List<String> getThirdElementItems() {
        return thirdElementItems;
    }

    public void setThirdElementItems(List<String> thirdElementItems) {
        this.thirdElementItems = thirdElementItems;
    }

    public String getFourthElement() {
        if((this.fourthElement == null) || (this.fourthElement.isEmpty())){
            return org.apache.commons.lang.StringEscapeUtils.escapeHtml(" ");
        }
        return fourthElement;
    }

    public void setFourthElement(String fourthElement) {
        this.fourthElement = fourthElement;
    }

    public List<String> getFourthElementItems() {
        return fourthElementItems;
    }

    public void setFourthElementItems(List<String> fourthElementItems) {
        this.fourthElementItems = fourthElementItems;
    }

    public String getFithElement() {
        if((this.fithElement == null) || (this.fithElement.isEmpty())){
            return org.apache.commons.lang.StringEscapeUtils.escapeHtml(" ");
        }
        return fithElement;
    }

    public void setFithElement(String fithElement) {
        this.fithElement = fithElement;
    }

    public List<String> getFithElementItems() {
        return fithElementItems;
    }

    public void setFithElementItems(List<String> fithElementItems) {
        this.fithElementItems = fithElementItems;
    }
}
