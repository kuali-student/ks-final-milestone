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
package org.kuali.rice.krms.dto;

/**
 * This class contains information required by the ui render components based on the
 * linked rule statement (proposition) type.
 *
 * @author Kuali Student Team
 */
public class TemplateInfo {

    /**
     * Used to create/update the term.
     */
    private String termSpecName;

    /**
     * Default operator value for specific rule statement type.
     */
    private String operator;

    /**
     * Default constant value for specific rule statement type.
     */
    private String value;

    /**
     * UI component (bean) id to be displayed in the ui.
     */
    private String componentId;

    @Deprecated
    private String constantComponentId;

    /**
     * Java class name responsible for resolving and building of term paramters for the specific rule
     * statement(proposition) type.
     */
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
