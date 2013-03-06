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
 * Created by christoff on 2013/01/11
 */
package org.kuali.rice.krms.dto;

import org.kuali.rice.krad.uif.container.Group;

/**
 * @author Christoff
 * Date: 2013/01/10
 */
public class TemplateInfo {

    private String termSpecName;
    private String operator;
    private String value;

    private String componentId;
    private String constantComponentId;
    private String componentBuilderClass;

    public String getTermSpecName() {
        return termSpecName;
    }

    public void setTermSpecName(String name) {
        this.termSpecName = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getConstantComponentId() {
        return constantComponentId;
    }

    public void setConstantComponentId(String constantComponentId) {
        this.constantComponentId = constantComponentId;
    }

    public String getComponentBuilderClass() {
        return componentBuilderClass;
    }

    public void setComponentBuilderClass(String componentBuilderClass) {
        this.componentBuilderClass = componentBuilderClass;
    }
}
