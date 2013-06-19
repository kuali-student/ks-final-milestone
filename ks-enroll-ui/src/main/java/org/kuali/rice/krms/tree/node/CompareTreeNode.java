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

    private String original;
    private List<String> originalItems;
    private String compared;
    private List<String> comparedItems;

    public CompareTreeNode(){
    }

    public CompareTreeNode(String original, String compared){
        this.original = original;
        this.compared = compared;
    }

    public String getOriginal() {
        if((this.original == null) || (this.original.isEmpty())){
            return org.apache.commons.lang.StringEscapeUtils.escapeHtml(" ");
        }
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public List<String> getOriginalItems() {
        return originalItems;
    }

    public void setOriginalItems(List<String> originalItems) {
        this.originalItems = originalItems;
    }

    public String getCompared() {
        if((this.compared == null) || (this.compared.isEmpty())){
            return org.apache.commons.lang.StringEscapeUtils.escapeHtml(" ");
        }
        return compared;
    }

    public void setCompared(String compared) {
        this.compared = compared;
    }

    public List<String> getComparedItems() {
        return comparedItems;
    }

    public void setComparedItems(List<String> comparedItems) {
        this.comparedItems = comparedItems;
    }
}
