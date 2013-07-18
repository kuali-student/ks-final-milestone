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
    private String forthElement;
    private List<String> forthElementItems;
    private String firthElement;
    private List<String> firthElementItems;

    public CompareTreeNode(){
    }

    public CompareTreeNode(String firstElement, String secondElement, String thirdElement, String forthElement, String firthElement){
        this.firstElement = firstElement;
        this.secondElement = secondElement;
        this.thirdElement = thirdElement;
        this.forthElement = forthElement;
        this.firthElement = firthElement;
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

    public String getForthElement() {
        if((this.forthElement == null) || (this.forthElement.isEmpty())){
            return org.apache.commons.lang.StringEscapeUtils.escapeHtml(" ");
        }
        return forthElement;
    }

    public void setForthElement(String forthElement) {
        this.forthElement = forthElement;
    }

    public List<String> getForthElementItems() {
        return forthElementItems;
    }

    public void setForthElementItems(List<String> forthElementItems) {
        this.forthElementItems = forthElementItems;
    }

    public String getFirthElement() {
        if((this.firthElement == null) || (this.firthElement.isEmpty())){
            return org.apache.commons.lang.StringEscapeUtils.escapeHtml(" ");
        }
        return firthElement;
    }

    public void setFirthElement(String firthElement) {
        this.firthElement = firthElement;
    }

    public List<String> getFirthElementItems() {
        return firthElementItems;
    }

    public void setFirthElementItems(List<String> firthElementItems) {
        this.firthElementItems = firthElementItems;
    }
}
