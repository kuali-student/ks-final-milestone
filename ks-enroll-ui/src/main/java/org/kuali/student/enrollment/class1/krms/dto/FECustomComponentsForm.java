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
package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krad.web.form.UifFormBase;

/**
 * @author Kuali Student Team
 */
public class FECustomComponentsForm extends UifFormBase {

    private FERuleEditor ruleEditor;
    private FERuleManagementWrapper ruleManagementWrapper;


    public FERuleEditor getRuleEditor() {
        return ruleEditor;
    }

    public void setRuleEditor(FERuleEditor ruleEditor) {
        this.ruleEditor = ruleEditor;
    }

    public FERuleManagementWrapper getRuleManagementWrapper() {
        return ruleManagementWrapper;
    }

    public void setRuleManagementWrapper(FERuleManagementWrapper ruleManagementWrapper) {
        this.ruleManagementWrapper = ruleManagementWrapper;
    }

}
